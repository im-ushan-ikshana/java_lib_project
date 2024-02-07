/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project.mainOps;

import java.sql.ResultSet;
import lib_project.sqlOperations.CRUD_Ops;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 * @author ikush
 */
public class Member {

    /**
     * Registers a new member in the system with the provided information.
     *
     * @param memberID The ID of the member
     * @param name The name of the member
     * @param contactDetails The contact details of the member
     * @param grade The grade of the member
     * @param className The class of the member
     * @param nic The NIC of the member
     * @param email The email of the member
     * @param tel The telephone number of the member
     */
    public static void regMember(String memberID, String name, String contactDetails, String grade, String className,
            String nic, String email, String tel) {
        // Construct the SQL query for registering a new member
        String query = "INSERT INTO Members_table (MemberID, Name, ContactDetails, Grade, Class, Nic, Email, Tel) VALUES "
                + "('" + memberID + "', '" + name + "', '" + contactDetails + "', '" + grade + "', '" + className + "', '"
                + nic + "', '" + email + "', '" + tel + "')";

        // Call the executeSql method from your CRUD_Ops class
        CRUD_Ops.create(query);
    }

    /**
     * Updates the information of an existing member in the system.
     *
     * @param memberID The ID of the member
     * @param name The new name of the member
     * @param contactDetails The new contact details of the member
     * @param grade The new grade of the member
     * @param className The new class of the member
     * @param nic The new NIC of the member
     * @param email The new email of the member
     * @param tel The new telephone number of the member
     */
    public static void updateMember(String memberID, String name, String contactDetails, String grade, String className,
            String nic, String email, String tel) {
        // Construct the SQL query for updating a member
        String updateQuery = "UPDATE Members_table SET "
                + "Name = '" + name + "', "
                + "ContactDetails = '" + contactDetails + "', "
                + "Grade = '" + grade + "', "
                + "Class = '" + className + "', "
                + "Nic = '" + nic + "', "
                + "Email = '" + email + "', "
                + "Tel = '" + tel + "' "
                + "WHERE MemberID = '" + memberID + "'";

        // Call the update method from your CRUD_Ops class
        CRUD_Ops.update(updateQuery);
    }

    /**
     * Removes a member from the system based on the provided member ID.
     *
     * @param memberID The ID of the member to be removed
     */
    public static void removeMember(String memberID) {
        // Construct the SQL query for removing a member
        String deleteQuery = "DELETE FROM Members_table WHERE MemberID = '" + memberID + "'";

        // Call the delete method from your CRUD_Ops class
        CRUD_Ops.delete(deleteQuery);
    }

    /**
     * Searches for a member in the system based on the provided keyword.
     *
     * @param keyword The keyword to search for (searches for MemberID, Name,
     * and Grade) Limited to 50 results.
     * @return A ResultSet containing the details of the member(s) if found, or
     * an empty ResultSet if not found
     */
    public static ResultSet searchMember(String keyword) {
        try {
            // Construct the SQL query for searching a member using the provided keyword with a LIMIT of 50
            String searchQuery = "SELECT MemberID,Name,Grade,Class FROM Members_table WHERE MemberID LIKE '%" + keyword + "%' OR Name LIKE '%" + keyword + "%' OR Grade LIKE '%" + keyword + "%' ORDER BY Grade,Class LIMIT 50";

            // Call the read method from your CRUD_Ops class to execute the search query
            return CRUD_Ops.read(searchQuery);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null in case of an exception
        }
    }

    /**
     * Retrieves the full details of a member, including late fees information,
     * based on the provided member ID. The information is fetched through a SQL
     * query using INNER JOIN on related tables. The method processes the
     * ResultSet to construct a String array containing the member's details,
     * transaction information, and late fees details, which is then returned.
     *
     * @param memberID The ID of the member for whom full details are requested
     * @return A String array containing the full details of the member,
     * including late fees information. Each element in the array represents a
     * specific piece of information, organized for display. If the member or
     * related information is not found, the method returns null.
     */
    public static String[] memberFullDetails(String memberID) {
        try {
            // Construct the SQL query for retrieving full details of a member with late fees information
            String fullDetailsQuery = "SELECT M.MemberID, M.Name, M.ContactDetails, M.Grade, M.Class, "
                    + "T.TransactionID, T.CheckOutDate, T.DueDate, T.CheckInDate, "
                    + "LF.LateFeeAmount, LF.Paid "
                    + "FROM Members_table M "
                    + "LEFT JOIN Transactions_table T ON M.MemberID = T.MemberID "
                    + "LEFT JOIN MemberLateFees_table LF ON M.MemberID = LF.MemberID "
                    + "WHERE M.MemberID = '" + memberID + "'";

            // Call the read method from your CRUD_Ops class to execute the query
            ResultSet resultSet = CRUD_Ops.read(fullDetailsQuery);

            // Process the ResultSet to extract information
            List<String> detailsList = new ArrayList<>();

            while (resultSet.next()) {
                // Extract member details
                detailsList.add("" + resultSet.getString("MemberID"));
                detailsList.add("" + resultSet.getString("Name"));
                detailsList.add("" + resultSet.getString("ContactDetails"));
                detailsList.add("" + resultSet.getString("Grade"));
                detailsList.add("" + resultSet.getString("Class"));

                // Extract transaction details
                String transactionID = resultSet.getString("TransactionID");
                String checkOutDate = resultSet.getString("CheckOutDate");
                String dueDate = resultSet.getString("DueDate");
                String checkInDate = resultSet.getString("CheckInDate");

                detailsList.add("" + (transactionID != null ? transactionID : "N/A"));
                detailsList.add("" + (checkOutDate != null ? checkOutDate : "N/A"));
                detailsList.add("" + (dueDate != null ? dueDate : "N/A"));
                detailsList.add(": " + (checkInDate != null ? checkInDate : "N/A"));

                // Extract late fees details
                String lateFeeAmount = resultSet.getString("LateFeeAmount");
                String paidStatus = resultSet.getString("Paid");

                detailsList.add("" + (lateFeeAmount != null ? lateFeeAmount : "N/A"));
                detailsList.add("" + (paidStatus != null ? paidStatus : "N/A"));
            }

            // Convert the list to an array
            String[] detailsArray = new String[detailsList.size()];
            detailsArray = detailsList.toArray(detailsArray);

            // Order of information in the returned String array:
            // Index 0: Member ID
            // Index 1: Name
            // Index 2: Contact Details
            // Index 3: Grade
            // Index 4: Class
            // Index 5: Transaction ID
            // Index 6: Check Out Date
            // Index 7: Due Date
            // Index 8: Check In Date
            // Index 9: Late Fee Amount
            // Index 10: Paid Status
            return detailsArray;

        } catch (Exception e) {
            e.printStackTrace();
            return null; // Return null in case of an exception
        }
    }

    public static boolean isValidMember(String memberID) {
        // Check if memberID is not null and not empty
        if (memberID != null && !memberID.isEmpty()) {
            // Check if the memberID exists in the Members_table
            String query = "SELECT * FROM Members_table WHERE MemberID = '" + memberID + "'";
            ResultSet resultSet = CRUD_Ops.read(query);

            try {
                return resultSet.next(); // Return true if there is a record, otherwise false
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false; // Default if memberID is null or empty
    }

    /**
     * Checks if a member with the given ID already exists in the Members_table.
     *
     * @param memberID The ID of the member to check.
     * @return true if the member already exists, false otherwise.
     */
    public static boolean isMemberAlreadyExists(String memberID) {
        // Check if memberID is not null and not empty
        if (memberID != null && !memberID.isEmpty()) {
            // Check if the memberID exists in the Members_table
            String query = "SELECT * FROM Members_table WHERE MemberID = '" + memberID + "'";
            ResultSet resultSet = CRUD_Ops.read(query);

            try {
                return resultSet.next(); // Return true if there is a record, otherwise false
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false; // Default if memberID is null or empty
    }

    /**
     * Gets the count of members in the Members_table.
     *
     * @return The count of members
     */
    public static String getMemberCount() {
        // Construct the SQL query to get the count of members
        String query = "SELECT COUNT(*) FROM Members_table";
        ResultSet resultSet = CRUD_Ops.read(query);

        try {
            if (resultSet.next()) {
                return "" + resultSet.getInt(1); // Return the count of members
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "0:ERROR"; // Default if there is an error or no members found
    }

    /**
     * Retrieves details of a member from the Members_table based on the
     * provided MemberID.
     *
     * @param memberID The ID of the member
     * @return An array of strings containing member details, or null if the
     * member is not found
     */
    public static String[] getMemberDetails(String memberID) {
        // Array to store member details
        String[] memberDetails = new String[8]; // Assuming 8 fields in the Members_table

        try {
            // Construct the SQL query for retrieving member details
            String selectQuery = "SELECT * FROM Members_table WHERE MemberID = '" + memberID + "'";
            ResultSet result = CRUD_Ops.read(selectQuery);

            // Check if the result set has data
            if (result.next()) {
                // Populate the array with member details
                memberDetails[0] = result.getString("MemberID");
                memberDetails[1] = result.getString("Name");
                memberDetails[2] = result.getString("ContactDetails");
                memberDetails[3] = result.getString("Grade");
                memberDetails[4] = result.getString("Class");
                memberDetails[5] = result.getString("Nic");
                if (memberDetails[5] == null) {
                    memberDetails[5] = "";
                } else {
                    memberDetails[5] = (result.getString("Nic").equals("NULL")) ? memberDetails[5] = "" : result.getString("Nic");
                }

                memberDetails[6] = result.getString("Email");
                memberDetails[7] = result.getString("Tel");
            } else {
                // Set null for non-existing member
                Arrays.fill(memberDetails, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Set null in case of an exception
            Arrays.fill(memberDetails, null);
        }

        return memberDetails;
    }

    /**
     * Calculates and returns the total unpaid late fees for a given member ID.
     *
     * @param memberID The ID of the member.
     * @return Total unpaid late fees as a String.
     */
    public static String getTotalUnpaidLateFees(String memberID) {
        // Construct the SQL query to retrieve total unpaid late fees for the given MemberID
        String query = "SELECT SUM(LateFeeAmount) AS TotalUnpaidLateFees FROM MemberLateFees_table WHERE MemberID = '"
                + memberID + "' AND Paid = 0";

        try {
            // Execute the query and get the ResultSet
            ResultSet resultSet = CRUD_Ops.read(query);

            // Check if the ResultSet has a result
            if (resultSet.next()) {
                // Retrieve the total unpaid late fees and return as a String
                double totalUnpaidLateFees = resultSet.getDouble("TotalUnpaidLateFees");
                return "" + totalUnpaidLateFees;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the exception appropriately
        }

        // Return "Rs. 0" if there are no unpaid late fees or an error occurs
        return "0";
    }

}
