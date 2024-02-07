/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project.mainOps;

import java.sql.ResultSet;
import lib_project.sqlOperations.CRUD_Ops;

/**
 *
 * @author ikush
 */
public class LibrarySystem {

    /**
     * Searches for books in the library based on the provided keyword.
     *
     * @param keyword The keyword to search for in book titles, authors, or copy
     * IDs
     * @return A ResultSet containing information about matching book copies
     */
    public static ResultSet searchBooks(String keyword) {
        // Construct the SQL query for searching books with a limit of 100 results
        String searchQuery = "SELECT BC.CopyID AS 'Book(copyID)', B.Title, B.Genre, BC.LocationOnShelf AS Location, BC.Availability "
                + "FROM LibDB.BookCopies_table BC "
                + "INNER JOIN LibDB.Books_table B ON BC.BookID = B.BookID "
                + "WHERE B.Title LIKE '%" + keyword + "%' OR B.Author LIKE '%" + keyword + "%' OR BC.CopyID LIKE '%" + keyword + "%' OR B.Genre LIKE '%"+keyword+"%'"
                + "LIMIT 50";

        // Return the result of the search query
        return CRUD_Ops.read(searchQuery);
    }

    /**
     * Checks the availability status of a book copy based on the provided copy
     * ID.
     *
     * @param copyID The ID of the book copy
     * @return True if the book copy is available, otherwise false
     */
    public static boolean checkBookStatus(int copyID) {
        // Construct the SQL query for checking book status
        String checkStatusQuery = "SELECT Availability FROM BookCopies_table WHERE CopyID = '" + copyID + "'";
        ResultSet resultSet = CRUD_Ops.read(checkStatusQuery);
        try {
            // Check if the result set has a next record and return the availability status
            if (resultSet.next()) {
                return resultSet.getBoolean("Availability");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Default if an error occurs
    }

    /**
     * Locates the shelf location of a book copy based on the provided copy ID.
     *
     * @param copyID The ID of the book copy
     * @return The shelf location of the book copy or "Location not found" if an
     * error occurs
     */
    public static String locateBook(int copyID) {
        // Construct the SQL query for locating a book
        String locateBookQuery = "SELECT LocationOnShelf FROM BookCopies_table WHERE CopyID = " + copyID;
        ResultSet resultSet = CRUD_Ops.read(locateBookQuery);
        try {
            // Check if the result set has a next record and return the shelf location
            if (resultSet.next()) {
                return resultSet.getString("LocationOnShelf");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Location not found"; // Default if an error occurs
    }
}
