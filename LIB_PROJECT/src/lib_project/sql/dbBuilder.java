/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project.sql;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import lib_project.sql.crudSql;
import lib_project.sql.connSql;

public class dbBuilder extends crudSql{

    private static Set<String> executedQueries = new HashSet<>();
    
    public static void main(String[] args) {
        buildDatabase();
    }

    public static void buildDatabase() {
        System.out.println("Creating the database and adding dummy data...");

        Connection connection = connSql.getSQLConnection();
        try {
            connection.setAutoCommit(false); // Start transaction

            executeSQLFromFile("queries.sql");

            connection.commit(); // Commit transaction
            System.out.println("Database created and dummy data added successfully.");
        } catch (SQLException e) {
            System.err.println("Error building database: " + e.getMessage());
            try {
                connection.rollback(); // Rollback transaction if an error occurs
            } catch (SQLException rollbackException) {
                System.err.println("Error rolling back transaction: " + rollbackException.getMessage());
            }
        } finally {
            connSql.closeConnection(connection);
        }
    }

    private static void executeSQLFromFile(String filePath) {
    // Ensure the SqlConnection is configured properly
    Connection connection = connSql.getSQLConnection();

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
                    if (!executedQueries.contains(query)) {
                        System.out.println("Executing query: " + query);

                        // Execute the query using CRUD_Ops.create()
                        crudSql.createRecord(query);

                        // Add the executed query to the set
                        executedQueries.add(query);
                    }

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
