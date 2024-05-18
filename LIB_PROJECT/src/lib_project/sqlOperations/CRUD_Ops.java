/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project.sqlOperations;

import java.sql.*;

/**
 * This class provides CRUD (Create, Read, Update, Delete) operations for
 * interacting with a SQL database. It extends SqlConnection to establish and
 * maintain a database connection.
 *
 * Note: The connection is static to ensure it is shared among all instances of
 * this class. It assumes that the SqlConnection class contains the necessary
 * methods to establish a SQL connection.
 *
 * @author ikush
 */
public class CRUD_Ops extends SqlConnection {

    // The static connection shared among all instances of the class
    private static Connection connection = getSQLConnection();

    /**
     * Executes an SQL query that does not return a result set.
     *
     * @param sqlQuery The SQL query to be executed
     */
    private static void executeSql(String sqlQuery) {
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    /**
     * Executes an SQL query that returns a result set.
     *
     * @param sqlQuery The SQL query to be executed
     * @return The result set obtained from the query, or null if an error
     * occurs
     */
    private static ResultSet executeSqlWithResult(String sqlQuery) {
        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            return statement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error executing SQL query: " + e.getMessage());
            return null;
        } finally {
            closeConnection();
        }
    }

    /**
     * Performs a create operation in the database.
     *
     * @param query The SQL query for the create operation
     */
    public static void create(String query) {
        executeSql(query);
    }

    /**
     * Performs a read operation in the database.
     *
     * @param query The SQL query for the read operation
     * @return The result set obtained from the read operation
     */
    public static ResultSet read(String query) {
        return executeSqlWithResult(query);
    }

    /**
     * Performs an update operation in the database.
     *
     * @param query The SQL query for the update operation
     */
    public static void update(String query) {
        executeSql(query);

    }

    /**
     * Performs a delete operation in the database.
     *
     * @param query The SQL query for the delete operation
     */
    public static void delete(String query) {
        executeSql(query);
    }

    /**
     * Closes the database connection.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}