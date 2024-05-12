create table book_service_category (
        PID                   BIGINT           NOT NULL,
        CATEGORY_NAME         VARCHAR(100)         NULL,
        PERSIAN_NAME          VARCHAR(100)         NULL,
        DESCRIPTION           tinytext                 NULL,
        VERSION               int                  NOT NULL,
        created_date           TIMESTAMP           NULL,
        created_by             VARCHAR(100)        NULL,
        last_modified_date      TIMESTAMP          NULL,
        last_modified_by        VARCHAR(100)       NULL,
        CONSTRAINT PK_CATEGORY PRIMARY KEY (PID)
)