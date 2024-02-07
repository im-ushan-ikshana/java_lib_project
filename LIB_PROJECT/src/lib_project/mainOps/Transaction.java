/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project.mainOps;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import lib_project.sqlOperations.CRUD_Ops;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author ikush
 */
public class Transaction {

    /**
     * Checks out a book for a member.
     *
     * @param memberID The ID of the member checking out the book
     * @param copyID The ID of the book copy being checked out
     */
    public static void checkOutBook(String memberID, String copyID) {
        try {
            // Construct the SQL query for checking out a book
            String checkOutQuery = "INSERT INTO Transactions_table (MemberID, CopyID, CheckOutDate, DueDate, CheckInDate) VALUES ('"
                    + memberID + "', '" + copyID + "', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY), NULL)";
            CRUD_Ops.create(checkOutQuery);

            // Update BookCopies_table to mark the book as unavailable
            String updateBookCopyQuery = "UPDATE BookCopies_table SET Availability = false WHERE CopyID = '" + copyID + "'";
            CRUD_Ops.update(updateBookCopyQuery);

            // Optionally, you can add additional logic or messages here
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    /**
     * Returns a book to the library.
     *
     * @param bookCopyID The ID of the book copy being returned
     * @param memberID The ID of the member returning the book
     */
    public static void returnBook(String bookCopyID, String memberID) {
        try {
            // Construct the SQL query for updating Transactions_table with CheckInDate
            String returnQuery = "UPDATE Transactions_table SET CheckInDate = CURDATE() WHERE CopyID = '" + bookCopyID + "' AND MemberID = '" + memberID + "'";

            // Construct the SQL query for updating BookCopies_table to mark the book as available
            String updateBookCopyQuery = "UPDATE BookCopies_table SET Availability = true WHERE CopyID = '" + bookCopyID + "'";

            // Execute the update queries
            CRUD_Ops.update(returnQuery);
            CRUD_Ops.update(updateBookCopyQuery);

            // Optionally, you can add additional logic or messages here
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exceptions as needed
        }
    }

    /**
     * Calculates and records late fees for a transaction.
     *
     * @param memberID The ID of the member for whom the late fee is calculated
     * @param transactionID The ID of the transaction for which late fees are
     * being calculated
     * @return A string message indicating the calculated late fee amount
     */
    public static String calculateLateFee(String memberID, String transactionID) {
        // Retrieve the due date of the transaction
        String dueDateQuery = "SELECT DueDate FROM Transactions_table WHERE TransactionID = '" + transactionID + "'";

        try {
            // Execute the query and get the ResultSet
            ResultSet resultSet = CRUD_Ops.read(dueDateQuery);

            // Check if the ResultSet is not null and has a result
            if (resultSet != null && resultSet.next()) {
                // Parse the due date and current date for late fee calculation
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date dueDate = sdf.parse(resultSet.getString("DueDate"));
                Date currentDate = new Date();

                // Calculate the number of days late
                long daysLate = (currentDate.getTime() - dueDate.getTime()) / (24 * 60 * 60 * 1000);

                if (daysLate > 0) {
                    // Calculate late fee amount
                    double lateFeeAmount = daysLate * 5.0;

                    // Generate a unique ID for MemberLateFeeID
                    String memberLateFeeID = UUID.randomUUID().toString();

                    // Insert a new row into MemberLateFees_table
                    String lateFeeInsertQuery = "INSERT INTO MemberLateFees_table (MemberLateFeeID, MemberID, TransactionID, LateFeeAmount, Paid) VALUES ('"
                            + memberLateFeeID + "', '" + memberID + "', '" + transactionID + "', '" + lateFeeAmount + "', FALSE)";

                    CRUD_Ops.create(lateFeeInsertQuery);
                    /*
                    // If late fee is greater than 0, update MemberLateFees_table
                    if (lateFeeAmount > 0) {
                        // Update query to set Paid as TRUE for the late fee
                        String updateLateFeeQuery = "UPDATE MemberLateFees_table SET Paid = TRUE WHERE MemberLateFeeID = '" + memberLateFeeID + "'";
                        CRUD_Ops.update(updateLateFeeQuery);
                    }
                     */
                    return "Rs. " + lateFeeAmount;
                }
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace(); // Log or handle the exception appropriately
        }

        return "Rs. 0"; // Default if an error occurs or no result is found
    }

    /**
     * Marks the payment status of a late fee.
     *
     * @param memberLateFeeID The ID of the member's late fee record
     * @param status The payment status (true if paid, false otherwise)
     */
    public static void payLateFee(String MemberID, boolean status) {
        // Construct the SQL query for updating the payment status of a late fee
        String payLateFeeQuery = "UPDATE MemberLateFees_table SET Paid = " + status + " WHERE MemberID = '" + MemberID + "'";
        CRUD_Ops.update(payLateFeeQuery);
    }

    /**
     * Retrieves transaction records for a specific member.
     *
     * @param memberID The ID of the member for whom transaction records are
     * retrieved
     * @return A String array containing transaction record[last-matching] for
     * the member
     */
    public static String[] getTransactionRecordForMember(String memberID) {
        try {
            // Construct the SQL query for retrieving the last transaction record for a member
            String query = "SELECT * FROM Transactions_table WHERE MemberID = '" + memberID + "' ORDER BY TransactionID DESC LIMIT 1";

            // Execute the query and get the ResultSet
            ResultSet resultSet = CRUD_Ops.read(query);

            // Assuming your ResultSet has columns like 'TransactionID', 'MemberID', etc.
            if (resultSet.next()) {
                // Extract data from ResultSet and store in a String array
                String[] transactionData = new String[5]; // Change the size based on your columns

                // Index 0: TransactionID
                transactionData[0] = resultSet.getString("TransactionID");
                // Index 1: MemberID
                transactionData[1] = resultSet.getString("MemberID");
                // Index 2: CopyID
                transactionData[2] = resultSet.getString("CopyID");
                // Index 3: CheckOutDate
                transactionData[3] = resultSet.getString("CheckOutDate");
                // Index 4: DueDate
                transactionData[4] = resultSet.getString("DueDate");

                return transactionData;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Return null if there are no records or an error occurs
        return null;
    }

    /**
     * Gets the count of transactions in the Transactions_table.
     *
     * @return The count of transactions
     */
    public static String getTransactionCount() {
        // Construct the SQL query to get the count of transactions
        String query = "SELECT COUNT(*) FROM Transactions_table";
        ResultSet resultSet = CRUD_Ops.read(query);

        try {
            if (resultSet.next()) {
                return "" + resultSet.getInt(1); // Return the count of transactions
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "0:ERROR"; // Default if there is an error or no transactions found
    }

    /**
     * Gets the total late fees where paid is true in the MemberLateFees_table.
     *
     * @return A String representing the total late fees.
     */
    public static String getPaidTotalLateFees() {
        // Construct the SQL query to get the total late fees
        String query = "SELECT SUM(LateFeeAmount) FROM MemberLateFees_table WHERE Paid = true";
        ResultSet resultSet = CRUD_Ops.read(query);

        try {
            if (resultSet.next()) {
                BigDecimal totalLateFees = resultSet.getBigDecimal(1);
                if (totalLateFees != null) {
                    return "Rs. " + totalLateFees.toString();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "0:Error"; // Default if there is an error or no late fees found
    }

    /**
     * Searches transactions based on a keyword in all columns of the
     * Transactions_table.
     *
     * @param keyWord The keyword to search for in transactions.
     * @return ResultSet containing the search results.
     */
    public static ResultSet searchTransactions(String keyWord) {
        try {
            // Construct the SQL query for searching transactions
            String query = "SELECT * FROM Transactions_table WHERE "
                    + "TransactionID LIKE '%" + keyWord + "%' OR "
                    + "MemberID LIKE '%" + keyWord + "%' OR "
                    + "CopyID LIKE '%" + keyWord + "%' OR "
                    + "CheckOutDate LIKE '%" + keyWord + "%' OR "
                    + "DueDate LIKE '%" + keyWord + "%' OR "
                    + "CheckInDate LIKE '%" + keyWord + "%' LIMIT 50";

            // Execute the query and return the ResultSet
            return CRUD_Ops.read(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; // Return null in case of an error
    }

    /**
     * Retrieves MemberLatefee records for a given MemberID and returns the
     * details in a ResultSet.
     *
     * @param memberID The ID of the member.
     * @return ResultSet containing late fee details.
     */
    public static ResultSet getMemberLatefeeReport(String memberID) {
        // Construct the SQL query to retrieve MemberLatefee records for the given MemberID
        String query = "SELECT * FROM MemberLateFees_table WHERE MemberID LIKE '%" + memberID + "%'";

        try {
            // Execute the query and get the ResultSet
            return CRUD_Ops.read(query);

        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception appropriately
        }

        // Return null if there are no records or an error occurs
        return null;
    }

    /**
     * Removes incorrect lending operations based on the provided TransactionID.
     *
     * @param transactionID The Transaction ID to identify the lending
     * operation.
     * @return A message indicating the result of the removal process.
     */
    public static String removeWrongLendingOps(String transactionID) {
        // Initialize temporary variables
        String bookCopyID = "";
        String memberID = "";

        // Construct the SQL query to retrieve associated records
        String query = "SELECT * FROM Transactions_table WHERE TransactionID = '" + transactionID + "'";

        try {
            // Execute the query and get the ResultSet
            ResultSet resultSet = CRUD_Ops.read(query);

            // Check if the ResultSet is not null and has a result
            if (resultSet != null && resultSet.next()) {
                // Retrieve BookCopyID and MemberID
                bookCopyID = resultSet.getString("CopyID");
                memberID = resultSet.getString("MemberID");

                // Update BookCopy availability to true
                String updateAvailabilityQuery = "UPDATE BookCopies_table SET Availability = true WHERE CopyID = '" + bookCopyID + "'";
                CRUD_Ops.update(updateAvailabilityQuery);

                // Remove the lending operation record
                String deleteQuery = "DELETE FROM Transactions_table WHERE TransactionID = '" + transactionID + "'";
                CRUD_Ops.delete(deleteQuery);

                return "Incorrect lending operation removed. BookCopyID: " + bookCopyID + ", MemberID: " + memberID;
            } else {
                return "No record found for the provided TransactionID: " + transactionID;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the exception appropriately
            return "Error occurred during the removal process.";
        }
    }

}
