
INSERT INTO Author(`name`,last_name)
VALUES('Lucy','Mount Montgomery');

INSERT INTO Author(`name`,last_name)
VALUES('Elżbieta','Cherezińska');

INSERT INTO Author(`name`,last_name)
VALUES('Henryk','Sienkiewicz');


INSERT INTO Publisher(`name`)
VALUES('Zysk i SKa');

INSERT INTO Publisher(`name`)
VALUES('Zielona sowa');

INSERT INTO Publisher(`name`)
VALUES('PWN');


INSERT INTO Language(`name`)
VALUES('polish');

INSERT INTO Language(`name`)
VALUES('english');

INSERT INTO Language(`name`)
VALUES('german');

INSERT INTO Language(`name`)
VALUES('french');


INSERT INTO Book(isbn, title, page_count, author_ID, publisher_ID, language_ID)
VALUES(111, 'W pustyni i w puszczy', 250, 3, 3, 1);

INSERT INTO Book(isbn, title, page_count, author_ID, publisher_ID, language_ID)
VALUES(222, 'Saga Sigrun', 200, 2, 3, 3);

INSERT INTO Book(isbn, title, page_count, author_ID, publisher_ID, language_ID)
VALUES(333, 'Ania z Avonlea', 300, 1, 1, 4);


INSERT INTO Book_item(isbn, rfid_tag)
VALUES(111,'aaa');

INSERT INTO Book_item(isbn, rfid_tag)
VALUES(111,'bbb');

INSERT INTO Book_item(isbn, rfid_tag)
VALUES(222,'ccc');

INSERT INTO Book_item(isbn, rfid_tag)
VALUES(333,'eee');


INSERT INTO Account(`name`, active)
VALUES('Jon Snow', 1);

INSERT INTO Account(`name`, active)
VALUES('Tyrion Lannister', 1);

INSERT INTO Account(`name`, active)
VALUES('Little Finger', 0);


INSERT INTO Lent_book_info(`rfid_tag`, borrower_account_ID, borrow_date, due_date)
VALUES('aaa', 1, '2020-01-01', '2020-01-31');

INSERT INTO Reserved_book_info(`rfid_tag`, borrower_account_ID, reservation_date, due_date)
VALUES('aaa', 2, '2020-01-05', '2020-01-04');
