-- User Creation Script for the bookApp Database
/*
    I like to separate my scripts by use case.
    This lets me run my scripts separately from each other.
        In other wordsl, I'll run user creation -> schema creation -> inserts
        
    Running this script creates a user and grants it priveleges
    */
    
-- Fresh start. Drop the user.
drop user TuitionManager cascade;

/* Drop can remove the user "object" or "entity" from the system.
    We want to run this script on the dba user, so SYSTEM or your ADMIN user
        We don't want our schema getting built on our dba user.
*/
-- create a user in the database
create user TuitionManager
identified by p4ssw0rd
default tablespace users
temporary tablespace temp
quota 10m on users;

-- we need to be able to connect to another user from bookapp
grant connect to TuitionManager;
-- we want the ability to create types
grant resource to TuitionManager;
-- we don't want the ability to alter and destroy types
-- grant dba to bookapp;
-- We do want the ability to create a session for transactions
grant create session to TuitionManager;
-- self explanatory
grant create table to TuitionManager;
grant create view to TuitionManager;

-- older version of Oracle SQL required this statement
-- grant select, insert, update, delete to bookapp;