/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project.mainOps;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import lib_project.mainOps.validateOps.Validations;
import lib_project.SystemVariables;
import lib_project.sqlOperations.CRUD_Ops;
import java.sql.ResultSet;

/**
 *
 * @author ikush
 */
/**
 * This class represents the operations related to the admin in the library
 * management system. It includes methods for user login, adding, removing, and
 * updating admin information.
 *
 * @author ikush
 */
public class Admin {

    /**
     * Validates the provided admin username and password for login.
     *
     * @param user_name The admin username
     * @param password The admin password
     * @return True if login is successful, otherwise false
     */
    public static boolean userLogIn(String user_name, String password) {
        try {
            ResultSet result = CRUD_Ops.read("SELECT adminUser FROM adminInfo_table");
            while (result.next()) {
                if (result.getString("adminUser").equals(user_name)) {
                    ResultSet result_2 = CRUD_Ops.read("SELECT pass FROM adminInfo_table WHERE adminUser LIKE '" + user_name + "'");
                    while (result_2.next()) {
                        if (result_2.getString("pass").equals(password)) {
                            SystemVariables.setAdminUsername(user_name);
                            return true;
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Password is Wrong");
                    return false;
                }
            }
            JOptionPane.showMessageDialog(null, "Username not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Adds a new admin to the system with the provided information.
     *
     * @param adminID The admin ID
     * @param user_name The admin username
     * @param password The admin password
     * @param fName The first name of the admin
     * @param lName The last name of the admin
     * @param nic The NIC (National Identification Card) of the admin
     * @param tel The telephone number of the admin
     */
    public static void addNewAdmin(String adminID, String user_name, String password, String fName, String lName, String nic, String tel) {
        try {
            // Handle null values for tel and format the nic for SQL query
            String telValue = tel.isEmpty() ? "NULL" : "'" + tel + "'";
            String nicValue = "'" + nic + "'";

            // Construct the SQL query for adding a new admin
            String insertQuery = "INSERT INTO adminInfo_table (adminID, adminUser, pass, fName, lName, nic, tel, joined) VALUES ('"
                    + adminID + "', '" + user_name + "', '" + password + "', '" + fName + "', '" + lName + "', " + nicValue + ", " + telValue + ", CURRENT_DATE)";
            CRUD_Ops.create(insertQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes an admin from the system based on the provided admin ID and
     * username.
     *
     * @param adminID The admin ID
     * @param user_name The admin username
     */
    public static void removeAdmin(String adminID, String user_name) {
        try {
            // Construct the SQL query for removing an admin
            String query = "DELETE FROM adminInfo_table WHERE adminID = '" + adminID + "' AND adminUser = '" + user_name + "'";
            CRUD_Ops.delete(query);
            JOptionPane.showMessageDialog(null, "Admin User :  " + adminID + " | " + user_name + " is removed.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the information of an existing admin in the system.
     *
     * @param adminID The admin ID
     * @param user_name The admin username
     * @param password The new admin password
     * @param fName The new first name of the admin
     * @param lName The new last name of the admin
     * @param nic The new NIC (National Identification Card) of the admin
     * @param tel The new telephone number of the admin
     */
    public static void updateAdmin(String adminID, String user_name, String password, String fName, String lName, String nic, String tel) {
        try {
            // Check if nic and tel are empty, set them to NULL in the SQL query
            String nicValue = nic.isEmpty() ? "NULL" : "'" + nic + "'";
            String telValue = tel.isEmpty() ? "NULL" : "'" + tel + "'";

            // Use string concatenation for the update SQL query
            String updateQuery = "UPDATE adminInfo_table SET pass = '" + password + "', fName = '" + fName + "', lName = '" + lName + "', nic = " + nicValue + ", tel = " + telValue
                    + " WHERE adminID = '" + adminID + "' AND adminUser = '" + user_name + "'";
            CRUD_Ops.update(updateQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Verifies the admin's identity based on the provided username and NIC.
     *
     * @param username The username of the admin
     * @param nic The NIC (National Identity Card) of the admin
     * @return True if the provided username exists and the provided NIC matches
     * the stored NIC, otherwise false
     */
    public static boolean verifyAdmin(String username, String nic) {
        try {
            // Construct the SQL query to check if the username exists in the admin table
            String query = "SELECT * FROM adminInfo_table WHERE adminUser = '" + username + "'";
            ResultSet resultSet = CRUD_Ops.read(query);

            if (resultSet.next()) {
                // If the username exists, retrieve the NIC from the result set
                String storedNIC = resultSet.getString("nic");

                // Check if the provided NIC matches the stored NIC
                return nic.equals(storedNIC);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Return false if an error occurs or the username doesn't exist
    }

    /**
     * Changes the password of the admin based on the provided username.
     *
     * @param username The username of the admin
     * @param new_password The new password for the admin
     * @param confirm_password The confirmation of the new password
     * @return True if the password is successfully changed, otherwise false
     */
    public static boolean changeAdminPass(String username, String new_password, String confirm_password) {
        try {
            // Check if the new password and confirm password match
            if (new_password.equals(confirm_password) && Validations.isAllString(new_password, 20) && Validations.isAllString(confirm_password, 20)) {
                // Construct the SQL query to update the admin's password
                String updateQuery = "UPDATE adminInfo_table SET pass = '" + new_password + "' WHERE adminUser = '" + username + "'";

                // Attempt to execute the update query
                CRUD_Ops.update(updateQuery);

                // Check if the password was successfully updated by verifying the new password
                String verifyQuery = "SELECT * FROM adminInfo_table WHERE adminUser = '" + username + "' AND pass = '" + new_password + "'";
                ResultSet resultSet = CRUD_Ops.read(verifyQuery);

                // Return true if there is a record in the result set (password successfully updated), otherwise false
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(null, "Password Chnaged Succuess.");
                    return true;
                }
            } else if (!Validations.isAllString(new_password, 20) || !Validations.isAllString(confirm_password, 20)) {
                JOptionPane.showMessageDialog(null, "Password Length exceeds 20.");
            } else {
                JOptionPane.showMessageDialog(null, "Confirmation Password and New Password doesnt Match.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false; // Return false if an error occurs or passwords don't match
    }

    /**
     * Validates the process of searching for an admin based on AdminID or
     * UserName and matching it with the provided password or NIC.
     *
     * @param user_id_or_name The AdminID or UserName to search.
     * @param user_pass_or_nic The password or NIC to match.
     * @return true if the admin is found and matches the password or NIC, false
     * otherwise.
     */
    public static Boolean searchAdmin(String user_id_or_name, String user_pass_or_nic) {
        // Construct the SQL query to retrieve AdminID, UserName, Password, and NIC
        String query = "SELECT adminID, adminUser , pass, nic FROM AdminInfo_table";

        try {
            // Execute the query and get the ResultSet
            ResultSet resultSet = CRUD_Ops.read(query);

            // Iterate through the ResultSet to find a match
            while (resultSet.next()) {
                String adminID = resultSet.getString("adminID");
                String userName = resultSet.getString("adminUser");
                String password = resultSet.getString("pass");
                String nic = resultSet.getString("nic");

                // Check if the AdminID or UserName matches the provided user_id_or_name
                if (user_id_or_name.equals(adminID) || user_id_or_name.equals(userName)) {
                    // Check if the provided password or NIC matches
                    if (user_pass_or_nic.equals(password) || user_pass_or_nic.equals(nic)) {
                        return true; // Match found, return true
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the exception appropriately
        }

        return false; // No match found, return false
    }

    /**
     * Retrieves all admin information from the adminInfo_table based on the
     * provided adminID or adminUser and returns it as a String Array.
     *
     * @param user_name_or_adminID The adminID or adminUser to search for.
     * @return String Array containing admin information. Each index represents
     * a column.
     */
    public static String[] getAllAdminInfo(String user_name_or_adminID) {
        // Construct the SQL query to retrieve all admin information
        String query = "SELECT * FROM adminInfo_table WHERE adminID='" + user_name_or_adminID + "' OR adminUser = '" + user_name_or_adminID + "'";

        try {
            // Execute the query and get the ResultSet with scroll sensitivity
            ResultSet resultSet = CRUD_Ops.read(query);

            // Check if the ResultSet is not empty
            if (resultSet.next()) {
                // Get the column count in the ResultSet
                int columnCount = resultSet.getMetaData().getColumnCount();

                // Create a String Array with columnCount as the size
                String[] adminInfoArray = new String[columnCount];

                // Iterate through the ResultSet and populate the adminInfoArray
                for (int i = 1; i <= columnCount; i++) {
                    // Add the column value to the array
                    adminInfoArray[i - 1] = resultSet.getString(i);
                }

                // Return the populated String Array
                return adminInfoArray;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the exception appropriately
        }

        // Return an empty array if an error occurs or no result is found
        return new String[0];
    }

    /**
     * Checks if the given admin username exists in the adminInfo_table.
     *
     * @param username The admin username to check.
     * @return true if the admin username exists, false otherwise.
     */
    public static boolean isUsernameExists(String username) {
        // Check if username is not null and not empty
        if (username != null && !username.isEmpty()) {
            // Construct the SQL query to check if the admin username exists
            String query = "SELECT * FROM adminInfo_table WHERE adminUser = '" + username + "'";

            try {
                // Execute the query and get the ResultSet
                ResultSet resultSet = CRUD_Ops.read(query);

                return resultSet.next(); // Return true if there is a record, otherwise false
            } catch (SQLException e) {
                e.printStackTrace(); // Log or handle the exception appropriately
            }
        }

        return false; // Default if username is null or empty
    }

}
