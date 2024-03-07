DROP TABLE BOARD_T;
CREATE TABLE BOARD_T (
    BOARD_NO NUMBER NOT NULL,
    TITLE VARCHAR2(1000 BYTE) NOT NULL,
    CONTENRS CLOB,
    MODIFIED_AT DATE,
    CREATED_AT DATE,
    CONSTRAINT PK_BOARD PRIMARY KEY(BOARD_NO)
);

DROP SEQUENCE BOARD_SEQ;
CREATE SEQUENCE BOARD_SEQ NOCACHE;