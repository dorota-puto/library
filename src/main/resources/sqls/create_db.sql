use library;



CREATE TABLE IF NOT EXISTS Author (
    author_ID INT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (author_ID)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;


CREATE TABLE IF NOT EXISTS Publisher (
    publisher_ID INT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (publisher_ID)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;


CREATE TABLE IF NOT EXISTS `Language` (
    language_ID INT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (language_ID)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;


CREATE TABLE IF NOT EXISTS Book (
    isbn BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    page_count INT NOT NULL,
    author_ID INT NOT NULL,
    publisher_ID INT NOT NULL,
    language_ID INT NOT NULL,
    PRIMARY KEY (isbn),
    FOREIGN KEY (author_Id)
        REFERENCES Author (author_Id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (publisher_Id)
        REFERENCES Publisher (publisher_Id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (language_Id)
        REFERENCES `Language` (language_Id)
        ON UPDATE CASCADE ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;


CREATE TABLE IF NOT EXISTS Book_item (
    rfid_tag VARCHAR(255) NOT NULL,
    isbn BIGINT NOT NULL,
    PRIMARY KEY (rfid_tag),
    FOREIGN KEY (isbn)
        REFERENCES Book (isbn)
        ON UPDATE CASCADE ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;


CREATE TABLE IF NOT EXISTS Account (
    account_ID INT NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    active BIT NOT NULL,
    PRIMARY KEY (account_ID)
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;


CREATE TABLE IF NOT EXISTS Lent_book_info (
    ID INT NOT NULL,
    rfid_tag VARCHAR(255) NOT NULL,
    borrower_account_ID INT NOT NULL,
    borrow_date DATE NOT NULL,
    due_date DATE NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (rfid_tag)
        REFERENCES Book_item (rfid_tag)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (borrower_account_ID)
        REFERENCES Account (account_ID)
        ON UPDATE CASCADE ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;


CREATE TABLE IF NOT EXISTS Reserved_book_info (
    ID INT NOT NULL,
    rfid_tag VARCHAR(255) NOT NULL,
    borrower_account_ID INT NOT NULL,
    reservation_date DATE NOT NULL,
    due_date DATE NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (rfid_tag)
        REFERENCES Book_item (rfid_tag)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (borrower_account_ID)
        REFERENCES Account (account_ID)
        ON UPDATE CASCADE ON DELETE CASCADE
)  ENGINE=INNODB DEFAULT CHARSET=UTF8;
