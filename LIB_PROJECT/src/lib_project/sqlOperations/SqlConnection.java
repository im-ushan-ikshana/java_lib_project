/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project.sqlOperations;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * The SqlConnection class provides a method to establish a connection to a
 * MySQL database using configuration details from an external file.
 * <p>
 * It includes a main method for testing the database connection.
 * </p>
 *
 * @author ikush
 */
public class SqlConnection {

    /**
     * Establishes a connection to a MySQL database using configuration details
     * from an external file.
     *
     * @return A Connection object representing the database connection.
     */
    public static Connection getSQLConnection() {
        createDatabaseIfNotExists();  // Create the database if it doesn't exist
        Properties properties = loadProperties();

        if (properties != null) {
            try {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the database connection using properties from the configuration file
                Connection sqlConnection = DriverManager.getConnection(
                        properties.getProperty("url"),
                        properties.getProperty("user"),
                        properties.getProperty("password")
                );
                return sqlConnection;
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("The connection could not be established.");
                System.out.println("Please check the configuration details in the properties file.");
                System.out.println("Make sure the MySQL server is running.");
                // Print stack trace for debugging purposes
                e.printStackTrace();
            }
        }

        return null; // Return null if the connection could not be established
    }

    private static void createDatabaseIfNotExists() {
        Properties properties = loadProperties();

        if (properties != null) {
            try {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establish the database connection using root user (or any user with sufficient privileges)
                Connection rootConnection = DriverManager.getConnection(
                        properties.getProperty("rootUrl"),
                        properties.getProperty("rootUser"),
                        properties.getProperty("rootPassword")
                );

                // Check if the database exists
                String dbName = properties.getProperty("dbName");
                String checkDBQuery = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?";
                try (PreparedStatement statement = rootConnection.prepareStatement(checkDBQuery)) {
                    statement.setString(1, dbName);
                    ResultSet resultSet = statement.executeQuery();

                    if (!resultSet.next()) {
                        // Create the database if it doesn't exist
                        String createDBQuery = "CREATE DATABASE " + dbName;
                        try (PreparedStatement createStatement = rootConnection.prepareStatement(createDBQuery)) {
                            createStatement.executeUpdate();
                            DatabaseBuilder.buildDatabase();
                        }
                    }
                }

                // Close the root connection
                rootConnection.close();
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("The database could not be created.");
                System.out.println("Please check the configuration details in the properties file.");
                System.out.println("Make sure the MySQL server is running.");
                // Print stack trace for debugging purposes
                e.printStackTrace();
            }
        }
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("The properties file is not found.");
            System.out.println("Please make sure the file is in the project root directory.");
            // Print stack trace for debugging purposes
            e.printStackTrace();
        }
        return properties;
    }

}
