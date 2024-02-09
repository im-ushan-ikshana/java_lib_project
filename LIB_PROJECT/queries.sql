-- Create Database
USE LibDB;

-- Create adminInfo_table
CREATE TABLE adminInfo_table (
    adminID VARCHAR(6) PRIMARY KEY,
    adminUser VARCHAR(50) NOT NULL UNIQUE,
    pass VARCHAR(20) NOT NULL,
    fName VARCHAR(30) NOT NULL,
    lName VARCHAR(30) NOT NULL,
    nic VARCHAR(13) NOT NULL,
    tel VARCHAR(12) NOT NULL,
    joined DATE
);

-- Create Members_table with NIC
CREATE TABLE Members_table (
    MemberID VARCHAR(7) PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    ContactDetails VARCHAR(255) NOT NULL,
    Grade VARCHAR(10) NOT NULL,
    Class VARCHAR(10) NOT NULL,
    Nic VARCHAR(12),
    Email VARCHAR(150) NOT NULL,
    Tel VARCHAR(12) NOT NULL
);


-- Create Books_table
CREATE TABLE Books_table (
    BookID VARCHAR(8) PRIMARY KEY,
    Title VARCHAR(255) NOT NULL,
    Author VARCHAR(255) NOT NULL,
    ISBN VARCHAR(20) UNIQUE NOT NULL,
    Barcode VARCHAR(20) NULL,
    Price DECIMAL(10, 2) NOT NULL,
    Genre VARCHAR(50) NOT NULL
);

-- Create BookCopies_table
CREATE TABLE BookCopies_table (
    CopyID VARCHAR(12) PRIMARY KEY NOT NULL,
    BookID VARCHAR(8) NOT NULL,
    BookCondition VARCHAR(255) NOT NULL,
    AcquisitionDate DATE NOT NULL,
    LocationOnShelf VARCHAR(50) NOT NULL,
    Availability BOOLEAN NOT NULL,
    FOREIGN KEY (BookID) REFERENCES Books_table(BookID) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Create Transactions_table
CREATE TABLE Transactions_table (
    TransactionID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    MemberID VARCHAR(7) NOT NULL,
    CopyID VARCHAR(12) NOT NULL,
    CheckOutDate DATE NOT NULL,
    DueDate DATE NOT NULL,
    CheckInDate DATE NULL,
    FOREIGN KEY (MemberID) REFERENCES Members_table(MemberID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (CopyID) REFERENCES BookCopies_table(CopyID) ON DELETE CASCADE ON UPDATE CASCADE
);


-- Create MemberLateFees_table
CREATE TABLE MemberLateFees_table (
    MemberLateFeeID CHAR(36) PRIMARY KEY NOT NULL,
    MemberID VARCHAR(7) NOT NULL,
    TransactionID INT NOT NULL,
    LateFeeAmount DECIMAL(10, 2) NOT NULL,
    Paid BOOLEAN NOT NULL,
    FOREIGN KEY (MemberID) REFERENCES Members_table(MemberID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (TransactionID) REFERENCES Transactions_table(TransactionID) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Drop Foreign Key Constraint
ALTER TABLE MemberLateFees_table DROP FOREIGN KEY memberlatefees_table_ibfk_2;

-- Modify Transactions_table to make TransactionID auto-increment
ALTER TABLE Transactions_table MODIFY COLUMN TransactionID INT AUTO_INCREMENT;

-- Recreate Foreign Key Constraint
ALTER TABLE MemberLateFees_table ADD CONSTRAINT fk_transaction_id FOREIGN KEY (TransactionID) REFERENCES Transactions_table(TransactionID) ON DELETE CASCADE ON UPDATE CASCADE;


 -- Insert data into adminInfo_table
INSERT INTO adminInfo_table (adminID, adminUser, pass, fName, lName, nic, tel, joined)
VALUES 
    ('ADM_01', 'admin', 'admin', 'FirstName', 'LastName', '200148521456', '+94700000000', '2023-12-01'),
    ('ADM_02', 'im-ushan-ikshana', '54321', 'Ushan', 'Ikshana', '200212345678', '0790000000', '2024-01-27');
