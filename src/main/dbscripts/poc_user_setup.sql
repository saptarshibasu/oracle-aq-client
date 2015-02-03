-- This schema will host all the tables for PoCs (Proof of Concepts) 
CREATE USER POC IDENTIFIED BY POC;

GRANT CONNECT, RESOURCE TO POC;

-- The following grants are mandatory only for Oracle AQ related PoCs or projects
GRANT EXECUTE ON dbms_aq TO POC;
GRANT aq_administrator_role TO POC;

CREATE TABLE STATUS 
(status varchar2(15));

insert into status values('abc');

commit;
