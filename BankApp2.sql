/*******************************************************************************
   Drop database if it exists
********************************************************************************/
DROP USER bankapp CASCADE;


/*******************************************************************************
   Create database
********************************************************************************/
CREATE USER bankapp
IDENTIFIED BY p4ssw0rd
DEFAULT TABLESPACE users
TEMPORARY TABLESPACE temp
QUOTA 10M ON users;

GRANT connect to bankapp;
GRANT resource to bankapp;
GRANT create session TO bankapp;
GRANT create table TO bankapp;
GRANT create view TO bankapp;


conn bankapp/p4ssw0rd

/*******************************************************************************
   Create Tables
********************************************************************************/
CREATE TABLE UserAccount
(
    UserId NUMBER(5) NOT NULL,
    Username VARCHAR2(25) UNIQUE NOT NULL,
    Password VARCHAR2(25) NOT NULL,
    CONSTRAINT PK_UserAccount PRIMARY KEY  (UserId)
);

CREATE TABLE BankAccount
(
    AccountId NUMBER(5) NOT NULL,
    Balance NUMBER(14,2) NOT NULL,
    AccountType VARCHAR2(10),
    CONSTRAINT PK_BankAccount PRIMARY KEY  (AccountId)
);

CREATE TABLE Bank
(
    AccountId NUMBER(5) NOT NULL,
    UserId NUMBER(5) NOT NULL,
    CONSTRAINT PK_Bank PRIMARY KEY (AccountId, UserId)
);

/*******************************************************************************
   Create Foreign Keys
********************************************************************************/
ALTER TABLE Bank ADD CONSTRAINT FK_BankUserId
    FOREIGN KEY (UserId) REFERENCES UserAccount (UserId)  ;

ALTER TABLE Bank ADD CONSTRAINT FK_BankAccountId
    FOREIGN KEY (AccountId) REFERENCES BankAccount (AccountId)  ;
    
/*******************************************************************************
   Create Sequences
********************************************************************************/    
CREATE SEQUENCE UserAccount_Seq
MINVALUE 1
MAXVALUE 99999999999999
INCREMENT BY 1
START WITH 10000;

CREATE SEQUENCE BankAccount_Seq
MINVALUE 1
MAXVALUE 99999999999999
INCREMENT BY 1
START WITH 10000;

/*******************************************************************************
   Create Triggers
********************************************************************************/
CREATE TRIGGER UserAccount_Trigger
BEFORE INSERT ON UserAccount
FOR EACH ROW

BEGIN
  SELECT UserAccount_Seq.NEXTVAL
  INTO :NEW.UserId
  FROM DUAL;
END;
/

CREATE TRIGGER BankAccount_Trigger
BEFORE INSERT ON BankAccount
FOR EACH ROW

BEGIN
  SELECT BankAccount_Seq.NEXTVAL
  INTO :NEW.AccountId
  FROM DUAL;
END;
/

/*******************************************************************************
   Create Procedures
********************************************************************************/
CREATE OR REPLACE PROCEDURE get_user_bankaccounts(
  in_user_id    IN  UserAccount.UserId%TYPE,
  out_cursor    OUT SYS_REFCURSOR
)
IS
BEGIN
  OPEN out_cursor FOR
  SELECT *
  FROM Bank
  WHERE UserId = in_user_id;
END;
/
commit;