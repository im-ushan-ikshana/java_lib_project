/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class provides CRUD (Create, Read, Update, Delete) operations for
 * interacting with a SQL database. It extends SqlConnection to establish and
 * maintain a database connection.
 *
 * @author ikush -> ushan-ikshana
 */
public class crudSql extends connSql {
    // Create operation

    protected static void createRecord(String sql){
        Connection conn = null;
        try {
            conn = connSql.getSQLConnection();
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.executeUpdate();
                conn.commit();
            } 
        } catch (SQLException e) {
            handleException(e, conn);
        } finally {
            connSql.closeConnection(conn);
        }
    }
    
    protected static ResultSet readRecord(String sql){
        Connection conn = null;
        ResultSet resultSet = null;
        try {
            conn = connSql.getSQLConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet result = stmt.executeQuery()) {
                resultSet = result;
            } 
        } catch (SQLException e) {
            System.out.println("Error has been occured : "+ e.getMessage());
        } finally {
            connSql.closeConnection(conn);
        }
        return resultSet;
    }
    
    protected static void updateRecord(String sql){
        Connection conn = null;
        try {
            conn = connSql.getSQLConnection();
            conn.setAutoCommit(false);
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.executeUpdate();
                conn.commit();
            }
        } catch (SQLException e) {
            handleException(e, conn);
        }
        finally{
            connSql.closeConnection(conn);
        }
    }
    
    protected static void deleteRecord(String sql){
        Connection conn = null;
        try {
            conn = connSql.getSQLConnection();
            conn.setAutoCommit(false);
            try(PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.executeUpdate();
                conn.commit();
            }
        } catch (SQLException e) {
            handleException(e, conn);
        }
        finally{
            connSql.closeConnection(conn);
        }
    }

    private static void handleException(SQLException e,Connection connection){
        System.out.println("Error has been occured : " + e.getMessage());
        try {
            if(connection != null){
                connection.rollback();
            }
        } catch (SQLException rollBackError) {
            System.out.println("error has been occured when rollbacking changes : " + rollBackError.getMessage());
            rollBackError.printStackTrace();
        }
    }

}
