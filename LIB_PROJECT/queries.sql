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

-- Insert data into Books_table
INSERT INTO Books_table (BookID, Title, Author, ISBN, Barcode, Price, Genre) 
VALUES 
('ABC00001', 'The Book of Truth 2nd Edition', 'John Samuell', '9874561230', NULL, 2100.00, 'Religion and Spirituality'),
('ABC00002', 'Book Of Eternal Truth', 'Maxwell Linc', '9874561231', NULL, 5100.00, 'Religion and Spirituality'),
('ABC00003', 'Sherlock Holmes', 'John Wattson', '9874561233', NULL, 700.00, 'Thriller'),
('ABC00004', 'Hello Guru', 'King Rawana', '9874561234', '9874561230', 12000.00, 'History'),
('ABC10000', 'Realm Of Enchantment', 'Serna Rose', '9874567496', NULL, 1000.00, 'Fantasy'),
('ALX74512', 'The Alexander IV', 'Xiao Yan', '9874561111', NULL, 8540.00, 'Philosophy'),
('BCD25001', 'Epic Fantasy', 'Lucas Jackson', '9874569641415', NULL, 8950.00, 'Thriller'),
('COO12541', 'Guidance to Modern Era Mastery Of Cooking', 'Hell Hamb', '9874561874', NULL, 8900.00, 'Cookbooks'),
('GBS89654', 'Home Harmony', 'Sophia Traveler', '9874568741', NULL, 1200.00, 'Thriller'),
('GRT74125', 'The Great Wall Of China', 'Xian Kun Jun', '9874563214', NULL, 7850.00, 'Fantasy'),
('IUY14567', '10 Methods to Master the Art of Drawing', 'Yull Kim Zen', '9874567854', NULL, 7450.00, 'Art'),
('JKL85400', 'Thriller Whispers', 'Mia Thriller', '9874568545', NULL, 9600.00, 'Thriller'),
('QTM12121', 'Quantum Odyssey', 'Xander Quantum', '9874568794', NULL, 2100.00, 'Other'),
('QWE23260', 'How To Master the Art Of Coding', 'Kezara Kulathunga', '9874561239', NULL, 20000.00, 'Other'),
('QWE23261', 'Introduction to Modern Cyber Security 2023', 'Ushan Ikshana', '9874561235', '9874561654', 75000.00, 'Other'),
('QWE89564', 'Python Programming', 'Ushan Ikshana', '9874561240', NULL, 5400.00, 'Other'),
('WER85412', 'The Will Eternal 2021', 'Juggy Michel Chu', '9874561241', NULL, 8520.00, 'Fairy Tale'),
('YHB89247', '2023 Mythology of Greek', 'Grafield Shadder', '9874567777', '9874561200', 960.00, 'Philosophy'),
('YTX78541', 'Ushan The Beggar', 'Daniel S. Log', '9874568541', NULL, 4500.00, 'Home & Garden'),
('YUT85214', 'Battle Through the Heavens', 'Kim John Wun', '9874561274', '9874561999', 3650.00, 'Fairy Tale'),
('ZSE74521', 'How To Build Java Application Individually', 'Ushan Ikshana', '1212121212', '3131313131', 9900.00, 'Health & Wellness');

-- Insert data into BookCopies_table
INSERT INTO BookCopies_table (CopyID, BookID, BookCondition, AcquisitionDate, LocationOnShelf, Availability) 
VALUES 
('ABC00001_C1', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C10', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C11', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C12', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C13', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C14', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C15', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C16', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C17', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C18', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C19', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C2', 'ABC00001', 'Good', '2024-02-07', 'B-4', 0),
('ABC00001_C20', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C21', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C22', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C23', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C24', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C25', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C3', 'ABC00001', 'Good', '2024-02-07', 'B-4', 0),
('ABC00001_C4', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C5', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C6', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C7', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C8', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00001_C9', 'ABC00001', 'Good', '2024-02-07', 'B-4', 1),
('ABC00002_C1', 'ABC00002', 'Good', '2024-02-07', 'C-4', 1),
('ABC00002_C2', 'ABC00002', 'Good', '2024-02-07', 'C-4', 0),
('ABC00002_C3', 'ABC00002', 'Good', '2024-02-07', 'C-4', 0),
('ABC00002_C4', 'ABC00002', 'Good', '2024-02-07', 'C-4', 1),
('ABC00002_C5', 'ABC00002', 'Good', '2024-02-07', 'C-4', 1),
('ABC00003_C1', 'ABC00003', 'Good', '2024-02-07', 'A-1', 1),
('ABC00003_C10', 'ABC00003', 'Good', '2024-02-07', 'A-1', 1),
('ABC00003_C11', 'ABC00003', 'Good', '2024-02-07', 'A-1', 1),
('ABC00003_C12', 'ABC00003', 'Good', '2024-02-07', 'A-1', 1),
('ABC00003_C2', 'ABC00003', 'Good', '2024-02-07', 'A-1', 0),
('ABC00003_C3', 'ABC00003', 'Good', '2024-02-07', 'A-1', 0),
('ABC00003_C4', 'ABC00003', 'Good', '2024-02-07', 'A-1', 1),
('ABC00003_C5', 'ABC00003', 'Good', '2024-02-07', 'A-1', 1),
('ABC00003_C6', 'ABC00003', 'Good', '2024-02-07', 'A-1', 1),
('ABC00003_C7', 'ABC00003', 'Good', '2024-02-07', 'A-1', 1),
('ABC00003_C8', 'ABC00003', 'Good', '2024-02-07', 'A-1', 1),
('ABC00003_C9', 'ABC00003', 'Good', '2024-02-07', 'A-1', 1),
('ABC00004_C1', 'ABC00004', 'Good', '2024-02-07', 'C-1', 1),
('ABC00004_C10', 'ABC00004', 'Good', '2024-02-07', 'C-1', 1),
('ABC00004_C11', 'ABC00004', 'Good', '2024-02-07', 'C-1', 1),
('ABC00004_C12', 'ABC00004', 'Good', '2024-02-07', 'C-1', 1),
('ABC00004_C2', 'ABC00004', 'Good', '2024-02-07', 'C-1', 0),
('ABC00004_C3', 'ABC00004', 'Good', '2024-02-07', 'C-1', 0),
('ABC00004_C4', 'ABC00004', 'Good', '2024-02-07', 'C-1', 1),
('ABC00004_C5', 'ABC00004', 'Good', '2024-02-07', 'C-1', 1),
('ABC00004_C6', 'ABC00004', 'Good', '2024-02-07', 'C-1', 1),
('ABC00004_C7', 'ABC00004', 'Good', '2024-02-07', 'C-1', 1),
('ABC00004_C8', 'ABC00004', 'Good', '2024-02-07', 'C-1', 1),
('ABC00004_C9', 'ABC00004', 'Good', '2024-02-07', 'C-1', 1),
('ABC10000_C1', 'ABC10000', 'Good', '2024-02-07', 'C-3', 1),
('ABC10000_C10', 'ABC10000', 'Good', '2024-02-07', 'C-3', 1),
('ABC10000_C11', 'ABC10000', 'Good', '2024-02-07', 'C-3', 1),
('ABC10000_C12', 'ABC10000', 'Good', '2024-02-07', 'C-3', 1),
('ABC10000_C2', 'ABC10000', 'Good', '2024-02-07', 'C-3', 0),
('ABC10000_C3', 'ABC10000', 'Good', '2024-02-07', 'C-3', 0),
('ABC10000_C4', 'ABC10000', 'Good', '2024-02-07', 'C-3', 1),
('ABC10000_C5', 'ABC10000', 'Good', '2024-02-07', 'C-3', 1),
('ABC10000_C6', 'ABC10000', 'Good', '2024-02-07', 'C-3', 1),
('ABC10000_C7', 'ABC10000', 'Good', '2024-02-07', 'C-3', 1),
('ABC10000_C8', 'ABC10000', 'Good', '2024-02-07', 'C-3', 1),
('ABC10000_C9', 'ABC10000', 'Good', '2024-02-07', 'C-3', 1),
('ALX74512_C1', 'ALX74512', 'Good', '2024-02-07', 'D-2', 1),
('ALX74512_C2', 'ALX74512', 'Good', '2024-02-07', 'D-2', 1),
('BCD25001_C1', 'BCD25001', 'Good', '2024-02-07', 'A-7', 1),
('COO12541_C1', 'COO12541', 'Good', '2024-02-07', 'D-1', 1),
('COO12541_C2', 'COO12541', 'Good', '2024-02-07', 'D-1', 1),
('GBS89654_C1', 'GBS89654', 'Good', '2024-02-07', 'B-5', 1),
('GBS89654_C2', 'GBS89654', 'Good', '2024-02-07', 'B-5', 1),
('GBS89654_C3', 'GBS89654', 'Good', '2024-02-07', 'B-5', 1),
('GBS89654_C4', 'GBS89654', 'Good', '2024-02-07', 'B-5', 1),
('GRT74125_C1', 'GRT74125', 'Good', '2024-02-07', 'A-1', 1),
('GRT74125_C2', 'GRT74125', 'Good', '2024-02-07', 'A-1', 1),
('GRT74125_C3', 'GRT74125', 'Good', '2024-02-07', 'A-1', 1),
('GRT74125_C4', 'GRT74125', 'Good', '2024-02-07', 'A-1', 1),
('GRT74125_C5', 'GRT74125', 'Good', '2024-02-07', 'A-1', 1),
('GRT74125_C6', 'GRT74125', 'Good', '2024-02-07', 'A-1', 1),
('GRT74125_C7', 'GRT74125', 'Good', '2024-02-07', 'A-1', 1),
('GRT74125_C8', 'GRT74125', 'Good', '2024-02-07', 'A-1', 1),
('IUY14567_C1', 'IUY14567', 'Good', '2024-02-07', 'B-2', 1),
('IUY14567_C2', 'IUY14567', 'Good', '2024-02-07', 'B-2', 1),
('IUY14567_C3', 'IUY14567', 'Good', '2024-02-07', 'B-2', 1),
('IUY14567_C4', 'IUY14567', 'Good', '2024-02-07', 'B-2', 1),
('IUY14567_C5', 'IUY14567', 'Good', '2024-02-07', 'B-2', 1),
('IUY14567_C6', 'IUY14567', 'Good', '2024-02-07', 'B-2', 1),
('JKL85400_C1', 'JKL85400', 'Good', '2024-02-07', 'D-5', 1),
('JKL85400_C2', 'JKL85400', 'Good', '2024-02-07', 'D-5', 1),
('JKL85400_C3', 'JKL85400', 'Good', '2024-02-07', 'D-5', 1),
('JKL85400_C4', 'JKL85400', 'Good', '2024-02-07', 'D-5', 1),
('QTM12121_C1', 'QTM12121', 'Good', '2024-02-07', 'B-2', 1),
('QTM12121_C2', 'QTM12121', 'Good', '2024-02-07', 'B-2', 1),
('QTM12121_C3', 'QTM12121', 'Good', '2024-02-07', 'B-2', 1),
('QTM12121_C4', 'QTM12121', 'Good', '2024-02-07', 'B-2', 1),
('QWE23260_C1', 'QWE23260', 'Good', '2024-02-07', 'C-6', 1),
('QWE23260_C2', 'QWE23260', 'Good', '2024-02-07', 'C-6', 1),
('QWE23260_C3', 'QWE23260', 'Good', '2024-02-07', 'C-6', 1),
('QWE23260_C4', 'QWE23260', 'Good', '2024-02-07', 'C-6', 1),
('QWE23260_C5', 'QWE23260', 'Good', '2024-02-07', 'C-6', 1),
('QWE23261_C1', 'QWE23261', 'Good', '2024-02-07', 'A-4', 1),
('QWE23261_C2', 'QWE23261', 'Good', '2024-02-07', 'A-4', 1),
('QWE89564_C1', 'QWE89564', 'Good', '2024-02-07', 'B-6', 1),
('QWE89564_C10', 'QWE89564', 'Good', '2024-02-07', 'B-6', 1),
('QWE89564_C2', 'QWE89564', 'Good', '2024-02-07', 'B-6', 1),
('QWE89564_C3', 'QWE89564', 'Good', '2024-02-07', 'B-6', 1),
('QWE89564_C4', 'QWE89564', 'Good', '2024-02-07', 'B-6', 1),
('QWE89564_C5', 'QWE89564', 'Good', '2024-02-07', 'B-6', 1),
('QWE89564_C6', 'QWE89564', 'Good', '2024-02-07', 'B-6', 1),
('QWE89564_C7', 'QWE89564', 'Good', '2024-02-07', 'B-6', 1),
('QWE89564_C8', 'QWE89564', 'Good', '2024-02-07', 'B-6', 1),
('QWE89564_C9', 'QWE89564', 'Good', '2024-02-07', 'B-6', 1),
('WER85412_C1', 'WER85412', 'Good', '2024-02-07', 'A-3', 1),
('WER85412_C2', 'WER85412', 'Good', '2024-02-07', 'A-3', 1),
('WER85412_C3', 'WER85412', 'Good', '2024-02-07', 'A-3', 1),
('WER85412_C4', 'WER85412', 'Good', '2024-02-07', 'A-3', 1),
('WER85412_C5', 'WER85412', 'Good', '2024-02-07', 'A-3', 1),
('WER85412_C6', 'WER85412', 'Good', '2024-02-07', 'A-3', 1),
('WER85412_C7', 'WER85412', 'Good', '2024-02-07', 'A-3', 1),
('WER85412_C8', 'WER85412', 'Good', '2024-02-07', 'A-3', 1),
('YHB89247_C1', 'YHB89247', 'Good', '2024-02-07', 'B-2', 1),
('YHB89247_C2', 'YHB89247', 'Good', '2024-02-07', 'B-2', 1),
('YHB89247_C3', 'YHB89247', 'Good', '2024-02-07', 'B-2', 1),
('YHB89247_C4', 'YHB89247', 'Good', '2024-02-07', 'B-2', 1),
('YTX78541_C1', 'YTX78541', 'Good', '2024-02-07', 'D-2', 1),
('YTX78541_C2', 'YTX78541', 'Good', '2024-02-07', 'D-2', 1),
('YTX78541_C3', 'YTX78541', 'Good', '2024-02-07', 'D-2', 1),
('YTX78541_C4', 'YTX78541', 'Good', '2024-02-07', 'D-2', 1),
('YTX78541_C5', 'YTX78541', 'Good', '2024-02-07', 'D-2', 1),
('YTX78541_C6', 'YTX78541', 'Good', '2024-02-07', 'D-2', 1),
('YTX78541_C7', 'YTX78541', 'Good', '2024-02-07', 'D-2', 1),
('YTX78541_C8', 'YTX78541', 'Good', '2024-02-07', 'D-2', 1),
('YTX78541_C9', 'YTX78541', 'Good', '2024-02-07', 'D-2', 1),
('YUT85214_C1', 'YUT85214', 'Good', '2024-02-07', 'D-2', 1),
('YUT85214_C2', 'YUT85214', 'Good', '2024-02-07', 'D-2', 1),
('ZSE74521_C1', 'ZSE74521', 'Good', '2024-02-07', 'C-5', 1),
('ZSE74521_C2', 'ZSE74521', 'Good', '2024-02-07', 'C-5', 1),
('ZSE74521_C3', 'ZSE74521', 'Good', '2024-02-07', 'C-5', 1),
('ZSE74521_C4', 'ZSE74521', 'Good', '2024-02-07', 'C-5', 1);



INSERT INTO Members_table (MemberID, Name, ContactDetails, Grade, Class, Nic, Email, Tel) VALUES
('ST00001', 'John Loggy', '57 th Street, Kandy', 'Grade 6', 'A', NULL, 'john@gmail.com', '+94777777777'),
('ST00002', 'Xiao Yan', '8965 th Street , NY', 'Grade 12', 'B', '200010000000', 'xiao@gmail.com', '0701111111'),
('ST00003', 'Alexander Torch', '87 th Lane, Lake Round, Moon', 'Grade 9', 'C', NULL, 'alex@gmail.com', '+94858585858'),
('ST00004', 'Amal Kadana Mal', 'Nayana watta , Dehiwala Zoo', 'Grade 8', 'A', NULL, 'amal@gmail.com', '0796655444'),
('ST00005', 'Kiri Haamu', 'Mahakandarawa, India', 'Grade 13', 'C', NULL, 'kiri@outlook.com', '0869665454'),
('ST00006', 'Alex Pandiyan', '784 th Lane , New Dhilli', 'STAFF', 'STAFF', NULL, 'pandiyan.alex@guru.lk', '0788528528'),
('STF8585', 'Im Ur Sir', 'at your class,school', 'STAFF', 'STAFF', '792542695v', 'sir.ur.scl@buduammo.lk', '+94101000000'),
('STF9696', 'My Three Pala', 'Polonnaruwa', 'STAFF', 'STAFF', '678524716v', 'my3@pala.c.lk', '0798989898'),
('STF0007', 'Lara Croft', '123 Jungle Avenue, Amazon', 'STAFF', 'STAFF', '695121475v', 'lara@gmail.com', '+94771234567'),
('STF0008', 'Bruce Wayne', 'Wayne Manor, Gotham City', 'STAFF', 'STAFF', '857452545v', 'bruce@gmail.com', '+94876543210'),
('STF0009', 'Hermione Granger', '4 Privet Drive, Little Whinging', 'STAFF', 'STAFF', '746523656v', 'hermione@gmail.com', '+94771234567'),
('STF0010', 'Tony Stark', '10880 Malibu Point, Malibu', 'STAFF', 'STAFF', '784525252v', 'tony@gmail.com', '+94876543210'),
('ST00011', 'Arya Stark', 'Winterfell, Westeros', 'Grade 8', 'A', NULL , 'arya@gmail.com', '+94771234567');


-- Type 1: Book is checked out and returned before today
INSERT INTO Transactions_table (MemberID, CopyID, CheckOutDate, DueDate, CheckInDate) VALUES
('ST00001', 'ABC00001_C1', '2024-01-01', '2024-01-15', '2024-02-06'),
('ST00002', 'ABC00002_C1', '2023-11-02', '2023-11-16', '2024-02-08'),
('ST00003', 'ABC00003_C1', '2024-02-03', '2024-02-17', '2024-02-18'),
('ST00004', 'ABC00004_C1', '2024-01-04', '2024-01-18', '2024-02-20'),
('ST00005', 'ABC10000_C1', '2024-01-05', '2024-01-19', '2024-02-08'),
('ST00001', 'YHB89247_C4', '2024-01-01', '2024-01-15', '2024-02-06'),
('ST00002', 'WER85412_C7', '2023-11-02', '2023-11-16', '2024-02-08'),
('ST00001', 'IUY14567_C6', '2024-01-01', '2024-01-15', '2024-02-06'),
('ST00002', 'GBS89654_C2', '2023-11-02', '2023-11-16', '2024-02-08');

-- Type 2: Book is checked out before, and due date is ahead but not yet received
INSERT INTO Transactions_table (MemberID, CopyID, CheckOutDate, DueDate) VALUES
('ST00006', 'ABC00001_C2', '2024-02-06', '2024-02-20'),
('STF8585', 'ABC00002_C2', '2024-02-07', '2024-02-21'),
('STF9696', 'ABC00003_C2', '2024-02-08', '2024-02-22'),
('ST00001', 'ABC00004_C2', '2024-02-09', '2024-02-23'),
('ST00002', 'ABC10000_C2', '2024-02-10', '2024-02-24');

-- Type 3: Check out is today, and book is due in 14 days
INSERT INTO Transactions_table (MemberID, CopyID, CheckOutDate, DueDate) VALUES
('ST00003', 'ABC00001_C3', '2024-02-07', '2024-02-21'),
('ST00004', 'ABC00002_C3', '2024-02-07', '2024-02-21'),
('ST00005', 'ABC00003_C3', '2024-02-07', '2024-02-21'),
('ST00006', 'ABC00004_C3', '2024-02-07', '2024-02-21'),
('STF8585', 'ABC10000_C3', '2024-02-07', '2024-02-21');


-- Insert data into MemberLateFees_table
INSERT INTO MemberLateFees_table (MemberLateFeeID, MemberID, TransactionID, LateFeeAmount, Paid)
VALUES
('6e559159-0831-4d23-bc2b-9abdefc3a501', 'ST00001', 1, 110.00, FALSE),
('4c9c6879-0f09-4122-87f1-6b7e2f1d524f', 'ST00002', 2, 420.00, FALSE),
('d06f14c2-81da-4f58-9aeb-8937e87f778a', 'ST00003', 3, 5.00, FALSE),
('5d8e4601-10c8-4d9c-a60a-dbe95b05ebf6', 'ST00004', 4, 10.00, FALSE),
('e6a45dd5-3c88-4a5d-8a22-69e31b74ec2e', 'ST00005', 5, 100.00, FALSE),
('be8e0f88-e4cb-4b07-ba9f-2e550f90b715', 'ST00001', 6, 110.00, FALSE),
('3bfe9241-4ae8-4c33-a17d-0a8bfa92729f', 'ST00002', 7, 420.00, TRUE),
('c5f5d9bf-10e6-44fe-b0e8-864a98763c02', 'ST00001', 8, 110.00, FALSE),
('96f21c8e-5cc8-4c8f-8442-913e63143d0a', 'ST00002', 9, 420.00, FALSE);


