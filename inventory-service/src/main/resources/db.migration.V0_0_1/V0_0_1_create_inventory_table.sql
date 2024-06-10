CREATE TABLE inventory_service_inventory
(
    PID                    NUMBER(17)                   NOT NULL,
    BOOK_NAME              VARCHAR2(100)                NOT NULL,
    QUANTITY               NUMBER(17)                   NULL,
    CONSTRAINT PK_INVENTORY PRIMARY KEY (PID)
);
