/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project.main.system;

/**
 * Utility class for displaying messages in JTextField or JLabel with specified
 * color and border. Provides methods to show messages, clear messages, and
 * handle borders.
 *
 * @author ikush
 */
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.table.DefaultTableModel;
import lib_project.sql.crudSql;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class sysUtils {

    /**
     * Shows a message in a JTextField or JLabel with specified color and
     * border.
     *
     * @param msg The message to be displayed
     * @param component The JTextField or JLabel to display the message
     * @param isError If true, sets the text color to red and updates the
     * border; otherwise, sets it to green
     */
    public static void showMessage(String msg, JComponent component, boolean isError) {
        if (isError) {
            component.setForeground(Color.RED);
            if (component instanceof JTextField) {
                setErrorMessageBorder((JTextField) component);
            }
        } else {
            component.setForeground(Color.GREEN);
            if (component instanceof JTextField) {
                setDefaultBorder((JTextField) component);
            }
        }

        if (component instanceof JTextField) {
            ((JTextField) component).setText(msg);
        } else if (component instanceof JLabel) {
            ((JLabel) component).setText(msg);
        }

        // Set a timer to clear the message and reset the border after 5 seconds
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                clearMessage(component, msg);
                timer.cancel(); // Terminate the timer thread
            }
        }, 5000);
    }

    /**
     * Clears the message in the JTextField or JLabel.
     *
     * @param component The JTextField or JLabel to be cleared
     */
    private static void clearMessage(JComponent component, String msg) {
        if (component instanceof JTextField) {
            if (((JTextField) component).getText().equals(msg)) {
                ((JTextField) component).setText("");
            }
            setDefaultBorder((JTextField) component);
        } else if (component instanceof JLabel) {
            ((JLabel) component).setText("");
        }
        component.setForeground(Color.BLACK); // Reset text color to black
    }

    /**
     * Sets the border of the JTextField for error messages.
     *
     * @param textField The JTextField for error messages
     */
    private static void setErrorMessageBorder(JTextField textField) {
        Border redBorder = BorderFactory.createLineBorder(Color.RED, 2);
        textField.setBorder(redBorder);
    }

    /**
     * Sets the default border of the JTextField.
     *
     * @param textField The JTextField with the default border
     */
    private static void setDefaultBorder(JTextField textField) {
        textField.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
    }

    /**
     * Fills the given table model with data from the provided result set.
     *
     * @param current_table The DefaultTableModel to be filled with data
     * @param current_result The ResultSet containing the data to be displayed
     */
    public static void fillTheTable(DefaultTableModel current_table, ResultSet current_result) {
        // Clear existing rows in the table model
        current_table.setRowCount(0);

        try {
            // Get metadata to determine the number of columns
            ResultSetMetaData metaData = current_result.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Get column names
            String[] columnNames = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                columnNames[i - 1] = metaData.getColumnName(i);
            }

            // Set the column names for the table model
            current_table.setColumnIdentifiers(columnNames);

            while (current_result.next()) {
                // Create an array to store data for each row
                Object[] rowData = new Object[columnCount];

                // Populate the array with data from the result set
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = current_result.getString(i);
                }

                // Add the row data to the table model
                current_table.addRow(rowData);
            }

            // Close the result set
            current_result.close();
        } catch (SQLException ex) {
            // Handle SQL exception by showing an error message
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public static String returnColomunValue(String columnName, JTable table, JLabel systemMsg) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Get the index of the selected row
        int rowIndex = table.getSelectedRow();

        if (rowIndex != -1) { // Check if a row is selected
            try {
                // Get the value of the specified column in the selected row
                Object columnValue = model.getValueAt(rowIndex, model.findColumn(columnName));

                if (columnValue != null) {
                    // Convert the column value to a string and return
                    return columnValue.toString();
                } else {
                    showMessage("Selected column value is null.", systemMsg, true);
                }
            } catch (IllegalArgumentException e) {
                showMessage("Column not found: " + columnName, systemMsg, true);
            }
        } else {
            showMessage("No row is selected.", systemMsg, true);
        }
        // Return an empty string if an issue occurs
        return "";
    }

    public static void makeReceipt(String memberID, String memberName, String telephone,
            String email, String memberClass, String totalLateFees,
            String paidAmount, String change, String adminName) {
        try {
            // Create a unique file name based on the current time and Member ID
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String timeStamp = dateFormat.format(new Date());
            String uniqueFileName = "Receipts/receipt_" + memberID + "_" + timeStamp + ".pdf";

            // Create the "Receipts" folder if it doesn't exist
            createReceiptsFolder();

            // Create a Document instance
            Document document = new Document(PageSize.A6);
            PdfWriter.getInstance(document, new FileOutputStream(uniqueFileName));

            // Open the document for writing
            document.open();

            // Set font for the title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);

            // Add content to the document
            document.add(new Phrase("               Payment Receipt\n------------------------------------------------\n", titleFont));

            document.add(createParagraph("Member ID: " + memberID));
            document.add(createParagraph("Member Name: " + memberName));
            document.add(createParagraph("Telephone: " + telephone));
            document.add(createParagraph("Email: " + email));
            document.add(createParagraph("Class: " + memberClass));
            String transactionIDs = returnMemberLatefeeIDString(memberID);

            //custom font for transactions
            Font transFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            document.add(createParagraph("Transaction IDs: \n" + transactionIDs + "\n-----------------------------------------------------", transFont));

            // Set font for bold text
            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            document.add(createParagraph("Total Late Fees: " + totalLateFees, boldFont));
            document.add(createParagraph("Paid Amount: " + paidAmount));
            document.add(createParagraph("Change: " + change + "\n", boldFont));

            // Note: Auto-generated receipt from Library System (Font size: 8)
            Font noteFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
            document.add(createParagraph("Admin Name: " + adminName, noteFont));
            document.add(createParagraph("Date and Time: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), noteFont));

            document.add(createParagraph("\nNote: Auto-generated receipt from Library System", noteFont));
            document.add(createParagraph("\n                                ***"));
            // Close the document
            document.close();

            System.out.println("PDF created successfully: " + uniqueFileName);

            // Open the created file
            openFile(uniqueFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createReceiptsFolder() throws IOException {
        Path folderPath = Paths.get("Receipts");
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }
    }

    private static Phrase createParagraph(String text) {
        return new Phrase(text + "\n");
    }

    private static Phrase createParagraph(String text, Font font) {
        return new Phrase(text + "\n", font);
    }

    private static void openFile(String filePath) {
        try {
            Desktop.getDesktop().open(new java.io.File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns concatenated MemberLatefeeIDs for a given MemberID.
     *
     * @param memberID The ID of the member.
     * @return Concatenated MemberLatefeeIDs as a String.
     */
    private static String returnMemberLatefeeIDString(String memberID) {
        // Construct the SQL query to retrieve MemberLatefeeIDs for the given MemberID
        String query = "SELECT MemberLateFeeID FROM MemberLateFees_table WHERE MemberID = '" + memberID + "' AND paid = false";

        try {
            // Execute the query and get the ResultSet
            ResultSet resultSet = crudSql.b(query);

            // List to store MemberLatefeeIDs
            java.util.List<String> memberLatefeeIDs = new ArrayList<>();

            // Iterate through the ResultSet and add MemberLatefeeIDs to the list
            while (resultSet.next()) {
                memberLatefeeIDs.add(resultSet.getString("MemberLateFeeID"));
            }

            // Return concatenated MemberLatefeeIDs as a String
            return String.join("\n\t", memberLatefeeIDs);

        } catch (SQLException e) {
            e.printStackTrace(); // Log or handle the exception appropriately
        }

        // Return an empty String if there are no MemberLatefeeIDs or an error occurs
        return "";
    }

    public static void main(String[] args) {
        System.out.println(returnMemberLatefeeIDString("ST00002"));
    }

    public static void setPlaceHolder(JComponent component, String placeString) {
        if (component instanceof JTextField) {
            JTextField textField = (JTextField) component;
            textField.setText(placeString);
            java.awt.Font font = textField.getFont().deriveFont(java.awt.Font.ITALIC);
            textField.setFont(font);
            textField.setForeground(Color.GRAY);
        } else if (component instanceof JPasswordField) {
            JPasswordField passwordField = (JPasswordField) component;
            passwordField.setText(placeString);
            passwordField.setEchoChar('\u0000'); // Set echo char to zero to show plain text
            java.awt.Font font = passwordField.getFont().deriveFont(java.awt.Font.ITALIC);
            passwordField.setFont(font);
            passwordField.setForeground(Color.GRAY);
        }
    }

    public static void removePlaceHolder(JComponent component, String placeString) {
        if (component instanceof JTextField) {
            JTextField textField = (JTextField) component;
            if (textField.getText().equals(placeString)) {
                textField.setText("");
                java.awt.Font font = textField.getFont().deriveFont(java.awt.Font.PLAIN); // Restore font to plain
                textField.setFont(font);
                textField.setForeground(Color.BLACK); // Restore text color to black
            }
        } else if (component instanceof JPasswordField) {
            JPasswordField passwordField = (JPasswordField) component;
            if (passwordField.getText().equals(placeString)) {
                passwordField.setText("");
                passwordField.setEchoChar('\u2022'); // Restore echo char to default (bullet)
                java.awt.Font font = passwordField.getFont().deriveFont(java.awt.Font.PLAIN); // Restore font to plain
                passwordField.setFont(font);
                passwordField.setForeground(Color.BLACK); // Restore text color to black
            }
        }
    }

}
