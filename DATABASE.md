# Database Design Report 🛠️

## 1. Introduction 📚

This database design is created for a hypothetical scenario based on a Sri Lankan school library. It aims to efficiently manage information related to library administration, members, books, transactions, and late fees.

## 2. Tables and Schema 🗃️

### 2.1. `adminInfo_table` 🧑‍💼

- **Columns:**
  - `adminID` (Primary Key): Unique identifier for administrators.
  - `adminUser`: Username for administrator login.
  - `pass`: Password for administrator login.
  - `fName`: First name of the administrator.
  - `lName`: Last name of the administrator.
  - `nic`: National Identity Card number of the administrator.
  - `tel`: Telephone number of the administrator.
  - `joined`: Date when the administrator joined.

### 2.2. `Members_table` 👥

- **Columns:**
  - `MemberID` (Primary Key): Unique identifier for members.
  - `Name`: Name of the member.
  - `ContactDetails`: Contact details of the member.
  - `Grade`: Grade of the member.
  - `Class`: Class of the member.
  - `Nic`: National Identity Card number of the member.
  - `Email`: Email address of the member.
  - `Tel`: Telephone number of the member.

### 2.3. `Books_table` 📖

- **Columns:**
  - `BookID` (Primary Key): Unique identifier for books.
  - `Title`: Title of the book.
  - `Author`: Author of the book.
  - `ISBN`: International Standard Book Number of the book.
  - `Barcode`: Barcode of the book.
  - `Price`: Price of the book.
  - `Genre`: Genre of the book.

### 2.4. `BookCopies_table` 📚✂️

- **Columns:**
  - `CopyID` (Primary Key): Unique identifier for book copies.
  - `BookID`: Foreign key referencing `Books_table`.
  - `BookCondition`: Condition of the book copy.
  - `AcquisitionDate`: Date when the copy was acquired.
  - `LocationOnShelf`: Location of the copy on the shelf.
  - `Availability`: Availability status of the copy.

### 2.5. `Transactions_table` 🔄

- **Columns:**
  - `TransactionID` (Primary Key): Auto-incremented unique identifier for transactions.
  - `MemberID`: Foreign key referencing `Members_table`.
  - `CopyID`: Foreign key referencing `BookCopies_table`.
  - `CheckOutDate`: Date when the book copy was checked out.
  - `DueDate`: Due date for returning the book copy.
  - `CheckInDate`: Date when the book copy was checked in.

### 2.6. `MemberLateFees_table` 💸

- **Columns:**
  - `MemberLateFeeID` (Primary Key): Unique identifier for late fees.
  - `MemberID`: Foreign key referencing `Members_table`.
  - `TransactionID`: Foreign key referencing `Transactions_table`.
  - `LateFeeAmount`: Amount of the late fee.
  - `Paid`: Status indicating whether the late fee is paid.

## 3. Processes Overview 🔄

### 3.1. Library Administration

The `adminInfo_table` stores information about administrators, including usernames, passwords, contact details, and joining dates.

### 3.2. Member Management

The `Members_table` manages member details, such as names, contact information, grades, and classes.

### 3.3. Book Management

The `Books_table` and `BookCopies_table` work together to organize information about books, including titles, authors, ISBNs, barcodes, prices, and availability.

### 3.4. Transaction Handling

The `Transactions_table` tracks member transactions, recording check-out and check-in dates for book copies.

### 3.5. Late Fee Management

Late fees for library transactions are handled by the `MemberLateFees_table`, which includes information about late fees, member IDs, transaction IDs, amounts, and payment status.

## 4. Hypothetical Scenario 🌐

This database is designed for a hypothetical scenario based on a Sri Lankan school library, providing an organized and efficient system for library administration, book management, and member transactions.

