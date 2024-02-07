/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib_project.mainOps.validateOps;

import java.util.Scanner;
import java.util.regex.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * //
 *
 * @author ikush
 */
/**
 * The Validations class provides static methods for input validations.
 */
public class Validations {

    /**
     * Validates if the length of the input string is less than or equal to the
     * given length.
     *
     * @param userInput The input string to be validated.
     * @param length The maximum allowed length of the input string.
     * @return True if the input is valid, false otherwise.
     */
    public static boolean isAllString(String userInput, int length) {
        try {
            return userInput.length() <= length;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates if the length of the input string is less than or equal to the
     * given length and if all characters are integers.
     *
     * @param userInput The input string to be validated.
     * @param length The maximum allowed length of the input string.
     * @return True if the input is valid, false otherwise.
     */
    public static boolean isAllInt(String userInput, int length) {
        try {
            if (userInput.length() <= length) {
                // Attempt to convert the input string to an integer using Scanner
                java.util.Scanner scanner = new java.util.Scanner(userInput);
                if (scanner.hasNextInt()) {
                    scanner.close();
                    return true;
                }
                scanner.close();
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Validates if the input string represents a valid email address.
     *
     * @param userInput The input string to be validated.
     * @return True if the input is a valid email address, false otherwise.
     */
    public static boolean isValidEmail(String userInput) {
        try {
            if (userInput.length() <= 150) {
                // Basic email validation using a simplified regular expression
                String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
                return userInput.matches(emailRegex);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // If an exception occurs during validation
        }
        return false;
    }

    /**
     * Validates a telephone number based on specific rules.
     *
     * @param user_input The input string representing a telephone number.
     * @return True if the input is a valid telephone number, false otherwise.
     */
    public static boolean isValidTele(String user_input) {
        try {
            // Rule for Type 1: Can be a number beginning with 0 and then nine numbers
            if (user_input.matches("0\\d{9}")) {
                return true;
            }

            // Rule for Type 2: Can be a number beginning with +94 and then nine numbers
            if (user_input.matches("\\+94\\d{9}")) {
                return true;
            }

            return false; // If the input does not match any of the specified rules
        } catch (Exception e) {
            e.printStackTrace();
            return false; // If an exception occurs during validation
        }
    }

    /**
     * Validates a National Identity Card (NIC) number based on specific rules.
     *
     * @param user_input The input string representing a NIC number.
     * @return True if the input is a valid NIC number, false otherwise.
     */
    public static boolean isValidNIC(String user_input) {
        try {
            // Rule for Type 1: Can be a number beginning with 9 numbers and then the letter 'v' (or 'V'),
            // first two digits must be equal to or greater than the calculated range
            if (user_input.matches("\\d{9}[vV]")) {
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int range = currentYear - 60;  // Subtract 60 years from the current year
                int lastTwoDigitsRange = range % 100;  // Extract the last two digits of the calculated range
                int nicYear = Integer.parseInt(user_input.substring(0, 2));

                // Validate that the first two digits are equal to or greater than the calculated range
                if (nicYear >= lastTwoDigitsRange) {
                    return true;
                }
            }

            // Rule for Type 2: Can be a number with 12 digits, first 4 digits <= current year - 6
            if (user_input.matches("\\d{12}")) {
                int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                int inputYear = Integer.parseInt(user_input.substring(0, 4));

                // Validate that the first 4 digits are equal to or lower than the current year minus 6
                if (inputYear <= currentYear - 15) {
                    return true;
                }
            }

            return false; // If the input does not match any of the specified rules
        } catch (Exception e) {
            e.printStackTrace();
            return false; // If an exception occurs during validation
        }
    }

    /**
     * Validates a name based on specific rules.
     *
     * @param user_input The input string representing a name.
     * @return True if the input is a valid name, false otherwise.
     */
    public static boolean isValidName(String user_input) {
        try {
            if (user_input.length() <= 255) {
                // Rule: Allows hyphen (-), apostrophe ('), period (.), space, umlauts, accents, and any alphabetical character
                if (user_input.matches("[\\p{L}\\s.'-]+")) {
                    return true;
                }
            }
            return false; // If the input does not match the specified rule
        } catch (Exception e) {
            e.printStackTrace();
            return false; // If an exception occurs during validation
        }
    }

    /**
     * Validates an International Standard Book Number (ISBN) based on specific
     * rules.
     *
     * @param user_input The input string representing an ISBN.
     * @return True if the input is a valid ISBN, false otherwise.
     */
    public static boolean isValidISBN(String user_input) {
        try {
            // Rule: ISBN must be either 10 or 13 digits
            if (user_input.matches("\\d{10}|\\d{13}")) {
                // Validate data type (numeric)
                Long.parseLong(user_input);
                return true;
            }

            return false; // If the input does not match the specified rule
        } catch (NumberFormatException e) {
            // If the conversion to a Long fails, it means the input is not numeric
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // If an exception occurs during validation
        }
    }

    /**
     * Validates a Barcode based on specific rules.
     *
     * @param user_input The input string representing a barcode.
     * @return True if the input is a valid barcode, false otherwise.
     */
    public static boolean isValidBarcode(String user_input) {
        try {
            // Rule: Barcode must be a string of 12 digits
            if (user_input.matches("\\d{12}")) {
                // Validate data type (numeric)
                Long.parseLong(user_input);
                return true;
            }

            return false; // If the input does not match the specified rule
        } catch (NumberFormatException e) {
            // If the conversion to a Long fails, it means the input is not numeric
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false; // If an exception occurs during validation
        }
    }

    public static boolean isValidMemberID(String user_input) {
        try {
            // Rule: MemberID must be 7 characters long
            if (user_input.length() == 7) {
                // For the first type: ST00074
                if (user_input.matches("[sS][tT]\\d{5}")) {
                    return true;
                }

                // For the second type: STF0024
                if (user_input.matches("[sS][tT][fF]\\d{4}")) {
                    return true;
                }
            }

            return false; // If the input does not match the specified rules
        } catch (Exception e) {
            e.printStackTrace();
            return false; // If an exception occurs during validation
        }
    }

    /**
     * Checks if the given Book ID is valid.
     *
     * @param bookID The Book ID to check.
     * @return true if the Book ID is valid, false otherwise.
     */
    public static boolean isValidBookID(String bookID) {
        // Check if bookID is not null and matches the specified pattern
        // Return true if the Book ID is valid
        // Default if bookID is null or does not match the pattern

        return bookID != null && bookID.matches("^[A-Z]{3}\\d{5}$");
    }

    /**
     * Checks if the given BookCopy ID is valid.
     *
     * @param bookCopyID The BookCopy ID to check.
     * @return true if the BookCopy ID is valid, false otherwise.
     */
    public static boolean isValidBookCopyID(String bookCopyID) {
        // Check if bookCopyID is not null and matches the specified pattern
        // Return true if the BookCopy ID is valid
        // Default if bookCopyID is null or does not match the pattern

        return bookCopyID != null && bookCopyID.matches("^[A-Z]{3}\\d{5}_C\\d{2}$");
    }

    /**
     * Checks if the given adminID is in the specified format (LA_ + 2 numbers).
     *
     * @param adminID The adminID to check.
     * @return true if the adminID is in the correct format, false otherwise.
     */
    public static boolean isValidFormattedAdminID(String adminID) {
        // Define the regex pattern for the specified format (LA_ + 2 numbers)
        String regexPattern = "ADM_\\d{2}";

        // Use Pattern class to compile the regex pattern
        Pattern pattern = Pattern.compile(regexPattern);

        // Use Matcher to match the adminID against the pattern
        return pattern.matcher(adminID).matches();
    }

}
