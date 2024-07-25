CREATE TABLE book (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    isbn VARCHAR(13),
    description VARCHAR(100),
    publisher VARCHAR(100),
    price INTEGER,
    publication_date Date,
    version INTEGER,
    created_user VARCHAR(20),
    created_at Date,
    updated_user VARCHAR(20),
    updated_at Date,
    deleted_user VARCHAR(20),
    deleted_at Date
);


SELECT
    * 
FROM
    book_test 
ORDER BY
    publication_date DESC,
    isbn DESC;

CREATE INDEX idx_publicationDateAndIsbn ON book_test (publication_date DESC, isbn DESC);


