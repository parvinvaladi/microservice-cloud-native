create table TBL_BOOK (
    PID                   int           NOT NULL,
    NAME                  VARCHAR(100)         NULL,
    PUBLISHER_NAME        VARCHAR(100)         NULL,
    PUBLISH_DATE          TIMESTAMP            NULL,
    AUTHOR_NAME           VARCHAR(100)         NULL,
    DESCRIPTION           VARCHAR(250)         NULL,
    PRICE                 int           NOT NULL,
    CONSTRAINT PK_BOOK PRIMARY KEY (PID)
)