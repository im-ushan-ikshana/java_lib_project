# System Report 📊

## 1. Data Validation Methods 🛠️

### NIC, Name, MemberID, BookID Validation:

- **NIC (National Identity Card) 🆔:**
  - Type 1: Nine digits followed by 'v' or 'V'. The first two digits must be equal to or greater than the calculated range.
  - Type 2: Twelve digits, and the first four digits must be less than or equal to the current year minus 15.

- **Name Validation 📛:**
  - Allows hyphen (-), apostrophe ('), period (.), space, umlauts, accents, and any alphabetical character.
  - Maximum length: 255 characters.

- **MemberID Validation 👥:**
  - Two types: 
    1. Format: ST + 5 digits (e.g., ST00074).
    2. Format: STF + 4 digits (e.g., STF0024).
  - Length: 7 characters.

- **BookID Validation 📚:**
  - Format: Three uppercase letters followed by five digits (e.g., ABC12345).

### Other Validations:

- **Email Validation ✉️:**
  - Basic email validation using a simplified regular expression.
  - Maximum length: 150 characters.

- **Telephone Number Validation ☎️:**
  - Two types allowed: 
    1. Number beginning with 0 and then nine digits.
    2. Number beginning with +94 and then nine digits.

### ISBN and Barcode Validation:

- **ISBN Validation 📖:**
  - Must be either 10 or 13 digits.

- **Barcode Validation 🏷️:**
  - Must be a string of 12 digits.

## 2. System Scenario 🌐

The system is designed based on a hypothetical scenario in a Sri Lankan school setting.

## 3. System Functions and Late Fee Calculation ⏰💸

### System Functions:

- **Member Management 👤:**
  - Creation, updating, and deletion of member records.
  - Validation of NIC, name, and MemberID.

- **Book Management 📕:**
  - Creation, updating, and deletion of book records.
  - Validation of ISBN and BookID.

- **Transaction Management 🔄:**
  - Handling book borrow and return transactions.
  - Validation of BookCopy ID and due dates.

- **Late Fee Calculation 💰:**
  - Late fees are calculated based on the number of days a book is overdue.
  - The system uses a rate of Rs. 5.0 per day for calculating late fees.

---

This system, created for a school library in a Sri Lankan context, encompasses various data validation methods and functions to manage members, books, and transactions. The late fee calculation mechanism adds a financial aspect to encourage timely returns.
