/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project.mainOps;

import java.util.Arrays;
import lib_project.sqlOperations.CRUD_Ops;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ikush
 */
public class Book {

    /**
     * Adds a new book to the system with the provided information and creates
     * the specified number of book copies.
     *
     * @param bookID The book ID
     * @param title The title of the book
     * @param author The author of the book
     * @param isbn The ISBN (International Standard Book Number) of the book
     * @param barcode The barcode of the book
     * @param price The price of the book
     * @param genre The genre of the book
     * @param numberOfCopies The number of book copies to create
     * @param locationOnShelf The location on the shelf for the book copies
     */
    public static void addBook(String bookID, String title, String author, String isbn, String barcode, String price, String genre, int numberOfCopies, String locationOnShelf) {
        // Construct the SQL query for adding a new book
        String insertBookQuery = "INSERT INTO Books_table (BookID, Title, Author, ISBN, Barcode, Price, Genre) VALUES ('"
                + bookID + "', '" + title + "', '" + author + "', '" + isbn + "', '" + barcode + "', " + price + ", '" + genre + "')";
        CRUD_Ops.create(insertBookQuery);

        // Create book copies
        for (int i = 1; i <= numberOfCopies; i++) {
            // Generate unique CopyID for each book copy
            String copyID = bookID + "_C" + i;

            // Construct the SQL query for adding a new book copy
            String insertCopyQuery = "INSERT INTO BookCopies_table (CopyID, BookID, BookCondition, AcquisitionDate, LocationOnShelf, Availability) VALUES ('"
                    + copyID + "', '" + bookID + "', 'Good', CURDATE(), '" + locationOnShelf + "', true)";
            CRUD_Ops.create(insertCopyQuery);
        }
    }

    /**
     * Updates data for a book in both Books_table and BookCopies_table.
     *
     * @param bookCopyID The ID of the book copy
     * @param title The title of the book
     * @param author The author of the book
     * @param isbn The ISBN of the book
     * @param barcode The barcode of the book
     * @param price The price of the book
     * @param genre The genre of the book
     * @param locationOnShelf The location of the book copy on the shelf
     * @param condition The condition of the book copy
     */
    public static void updateBookData(String bookCopyID, String title, String author,
            String isbn, String barcode, String price,
            String genre, String locationOnShelf,
            String condition) {
        try {
            // Update data in Books_table
            String updateBooksQuery = "UPDATE Books_table SET Title = '" + title + "', Author = '" + author
                    + "', ISBN = '" + isbn + "', Barcode = '" + barcode + "', Price = " + price
                    + ", Genre = '" + genre + "' WHERE BookID = (SELECT BookID FROM BookCopies_table WHERE CopyID = '"
                    + bookCopyID + "')";

            CRUD_Ops.update(updateBooksQuery);

            // Update data in BookCopies_table
            String updateBookCopiesQuery = "UPDATE BookCopies_table SET LocationOnShelf = '" + locationOnShelf
                    + "', BookCondition = '" + condition + "' WHERE CopyID = '" + bookCopyID + "'";

            CRUD_Ops.update(updateBookCopiesQuery);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
     * Removes a book copy from the system based on the provided copy ID.
     *
     * @param copyID The copy ID
     */
    public static void removeBookCopy(String copyID) {
        // Construct the SQL query for removing a book copy
        String deleteQuery = "DELETE FROM BookCopies_table WHERE CopyID = '" + copyID + "'";
        CRUD_Ops.delete(deleteQuery);
    }

    /**
     * Retrieves full details about a book based on the provided book ID.
     *
     * @param bookID The book ID
     * @return String array containing book details at specific indices: Index
     * 0: Book ID Index 1: Title Index 2: Author Index 3: ISBN (International
     * Standard Book Number) Index 4: Barcode Index 5: Price Index 6: Genre
     */
    public static String[] getBookInfo(String bookID) {
        String[] bookDetails = new String[7];

        try {
            // Construct the SQL query for retrieving book details
            String selectQuery = "SELECT * FROM Books_table WHERE BookID = '" + bookID + "'";
            ResultSet result = CRUD_Ops.read(selectQuery);

            // Check if the result set has data
            if (result.next()) {
                // Populate the array with book details
                bookDetails[0] = String.valueOf(result.getString("BookID"));
                bookDetails[1] = result.getString("Title");
                bookDetails[2] = result.getString("Author");
                bookDetails[3] = result.getString("ISBN");
                bookDetails[4] = result.getString("Barcode");
                bookDetails[5] = String.valueOf(result.getDouble("Price"));
                bookDetails[6] = result.getString("Genre");
            } else {
                // Set null for non-existing book
                Arrays.fill(bookDetails, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Set null in case of an exception
            Arrays.fill(bookDetails, null);
        }

        return bookDetails;
    }

    /**
     * Retrieves information about a book copy based on the provided copy ID.
     *
     * @param copyID The copy ID
     * @return String array containing book copy details at specific indices:
     * Index 0: Copy ID Index 1: Book ID Index 2: Availability (true if
     * available, false if checked out) Index 3 : Location on the Shelf
     */
    public static String[] getBookCopyInfo(String copyID) {
        String[] copyDetails = new String[5];

        try {
            // Construct the SQL query for retrieving book copy details
            String selectQuery = "SELECT * FROM BookCopies_table WHERE CopyID = '" + copyID + "'";
            ResultSet result = CRUD_Ops.read(selectQuery);

            // Check if the result set has data
            if (result.next()) {
                // Populate the array with book copy details
                copyDetails[0] = String.valueOf(result.getString("CopyID"));
                copyDetails[1] = String.valueOf(result.getString("BookID"));
                copyDetails[2] = String.valueOf(result.getBoolean("Availability"));
                copyDetails[3] = result.getString("LocationOnShelf");
                copyDetails[4] = result.getString("BookCondition");
            } else {
                // Set null for non-existing copy
                Arrays.fill(copyDetails, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Set null in case of an exception
            Arrays.fill(copyDetails, null);
        }

        return copyDetails;
    }

    /**
     * Checks if a given book copy ID is valid (not empty and exists in the
     * BookCopies_table).
     *
     * @param copyID The book copy ID to be validated.
     * @return true if the copyID is valid, false otherwise.
     */
    public static boolean isValidBookCopy(String copyID) {
        // Check if copyID is valid (not empty)
        if (!copyID.isEmpty()) {
            // Check if the copyID exists in the BookCopies_table
            String query = "SELECT * FROM BookCopies_table WHERE CopyID = '" + copyID + "'";
            ResultSet resultSet = CRUD_Ops.read(query);

            try {
                return resultSet.next(); // Return true if there is a record, otherwise false
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false; // Default if copyID is not valid
    }

    /**
     * Checks the availability status of a given book copy.
     *
     * @param copyID The book copy ID for which availability is checked.
     * @return true if the book copy is available, false otherwise.
     */
    public static boolean isBookAvailable(String copyID) {
        try {
            // Construct the SQL query to check the availability of a book copy
            String availabilityQuery = "SELECT Availability FROM BookCopies_table WHERE CopyID = '" + copyID + "'";
            ResultSet result = CRUD_Ops.read(availabilityQuery);

            // Check if the result set has data
            if (result.next()) {
                // Return true if the book copy is available, false otherwise
                return result.getBoolean("Availability");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return false in case of an exception or if the copy doesn't exist
        return false;
    }

    /**
     * Checks if a given ISBN already exists in the Books_table.
     *
     * @param isbn The ISBN to check
     * @return true if the ISBN already exists, false otherwise
     */
    public static boolean isIsbnAlreadyExists(String isbn) {
        // Check if isbn is not null and not empty
        if (isbn != null && !isbn.isEmpty()) {
            // Construct the SQL query to check if the ISBN exists
            String query = "SELECT * FROM Books_table WHERE ISBN = '" + isbn + "'";
            ResultSet resultSet = CRUD_Ops.read(query);

            try {
                return resultSet.next(); // Return true if there is a record, otherwise false
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false; // Default if isbn is null or empty
    }

    /**
     * Checks if a given Book ID already exists in the Books_table.
     *
     * @param bookID The Book ID to check
     * @return true if the Book ID already exists, false otherwise
     */
    public static boolean isBookIDAlreadyExists(String bookID) {
        // Check if bookID is not null and not empty
        if (bookID != null && !bookID.isEmpty()) {
            // Construct the SQL query to check if the Book ID exists
            String query = "SELECT * FROM Books_table WHERE BookID = '" + bookID + "'";
            ResultSet resultSet = CRUD_Ops.read(query);

            try {
                return resultSet.next(); // Return true if there is a record, otherwise false
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false; // Default if bookID is null or empty
    }

    /**
     * Checks if a given Book Copy ID already exists in the BookCopies_table.
     *
     * @param copyID The Book Copy ID to check
     * @return true if the Book Copy ID already exists, false otherwise
     */
    public static boolean isBookCopyIDAlreadyExists(String copyID) {
        // Check if copyID is not null and not empty
        if (copyID != null && !copyID.isEmpty()) {
            // Construct the SQL query to check if the Book Copy ID exists
            String query = "SELECT * FROM BookCopies_table WHERE CopyID = '" + copyID + "'";
            ResultSet resultSet = CRUD_Ops.read(query);

            try {
                return resultSet.next(); // Return true if there is a record, otherwise false
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false; // Default if copyID is null or empty
    }

    /**
     * Gets the count of books in the Books_table.
     *
     * @return The count of books
     */
    public static String getBookCount() {
        // Construct the SQL query to get the count of books
        String query = "SELECT COUNT(*) FROM Books_table";
        ResultSet resultSet = CRUD_Ops.read(query);

        try {
            if (resultSet.next()) {
                int bookCount = resultSet.getInt(1); // Return the count of books
                return ""+bookCount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "0:Error"; // Default if there is an error or no books found
    }

    /**
     * Gets the total count of books from the BookCopies_table.
     *
     * @return The total count of books
     */
    public static int getTotalBooksCount() {
        // Construct the SQL query to get the total count of books
        String query = "SELECT COUNT(DISTINCT BookID) FROM BookCopies_table";
        ResultSet resultSet = CRUD_Ops.read(query);

        try {
            if (resultSet.next()) {
                return resultSet.getInt(1); // Return the total count of books
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Default if there is an error or no books found
    }

}
