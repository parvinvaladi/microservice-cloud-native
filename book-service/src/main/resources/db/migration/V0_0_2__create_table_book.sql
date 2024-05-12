create table book_service_book (
    PID                   BIGINT  AUTO_INCREMENT           NOT NULL,
    NAME                  VARCHAR(100)         NULL,
    PUBLISHER_NAME        VARCHAR(100)         NULL,
    PUBLISH_DATE          DATE            NULL,
    AUTHOR_NAME           VARCHAR(100)         NULL,
    DESCRIPTION           tinytext         NULL,
    PRICE                 NUMERIC                  NOT NULL,
    FK_BOOK_CATEGORY      BIGINT                  NOT NULL,
    VERSION               int                  NOT NULL,
    created_date           TIMESTAMP            NULL,
    created_by             VARCHAR(100)         NULL,
    last_modified_date      TIMESTAMP            NULL,
    last_modified_by        VARCHAR(100)         NULL,
    CONSTRAINT PK_BOOK PRIMARY KEY (PID),
 CONSTRAINT FK_BOOK_CATEGORY FOREIGN KEY (FK_BOOK_CATEGORY) REFERENCES `book-shop`.book_service_category (PID)
)