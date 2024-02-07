/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project.sqlOperations;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import lib_project.sqlOperations.CRUD_Ops;
import lib_project.sqlOperations.SqlConnection;

/**
 * The DatabaseBuilder class is used to create the initial database and populate
 * it with dummy data from an SQL file.
 *
 * Note: Ensure that the necessary database and tables are already created
 * before using this class.
 *
 * @author ikush
 */
public class DatabaseBuilder {

    /**
     * Main method to create the database and add dummy data.
     *
     * @param args Command line arguments (not used in this case).
     */
    public static void main(String[] args) {
        // Create the database and add dummy data
        buildDatabase();
    }

    /**
     * Creates the initial database and adds dummy data from an SQL file.
     */
    public static void buildDatabase() {
        System.out.println("Creating the database and adding dummy data...");

        // Execute SQL queries from the specified file
        executeSQLFromFile("queries.sql");

        System.out.println("Database created and dummy data added successfully.");
    }

    /**
     * Executes SQL queries from a file.
     *
     * @param filePath The path to the SQL file containing queries.
     */
    private static void executeSQLFromFile(String filePath) {
        // Ensure the SqlConnection is configured properly
        Connection connection = SqlConnection.getSQLConnection();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            StringBuilder sqlQuery = new StringBuilder();

            while ((line = br.readLine()) != null) {
                // Skip empty lines and comments
                if (!line.trim().isEmpty() && !line.trim().startsWith("--")) {
                    // Append the line to the SQL query
                    sqlQuery.append(line);

                    // If the line ends with a semicolon, execute the query
                    if (line.trim().endsWith(";")) {
                        String query = sqlQuery.toString();
                        System.out.println("Executing query: " + query);

                        // Execute the query using CRUD_Ops.create()
                        CRUD_Ops.create(query);

                        // Clear the StringBuilder for the next query
                        sqlQuery.setLength(0);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
