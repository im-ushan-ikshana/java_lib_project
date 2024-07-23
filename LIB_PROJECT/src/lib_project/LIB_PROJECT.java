/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lib_project;

import lib_project.guiFiles.loginPage;
import lib_project.sql.connSql;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author ikush
 */
public class LIB_PROJECT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        * Create a tempory connection to trigger the auto database creating(if not exsits) method
        */
        Connection tempCon = connSql.getSQLConnection();
        try {
            tempCon.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        new loginPage();
    }
}
