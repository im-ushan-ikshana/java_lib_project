/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project;

import java.time.LocalDate;

/**
 * This class represents variables used in the library management system. It
 * includes variables for storing the admin username and the current date.
 *
 * The admin username is a static variable that can be accessed and modified
 * using getter and setter methods. The current date is obtained using the
 * LocalDate class and is also a static variable.
 *
 * @author ikush
 */
public class SystemVariables {

    // Private static SystemVariables to store admin username and current date
    private static String adminUsername;
    private static String today = LocalDate.now().toString();

    /**
     * Gets the current admin username.
     *
     * @return The current admin username
     */
    public static String getAdminUsername() {
        return adminUsername;
    }

    /**
     * Sets the admin username.
     *
     * @param adminUsername The new admin username
     */
    public static void setAdminUsername(String adminUsername) {
        SystemVariables.adminUsername = adminUsername;
    }

    /**
     * Gets the current date.
     *
     * @return The current date in string format
     */
    public static String getToday() {
        return today;
    }
}
