/*
I have three options for my ddl script
1. connecting directly to my user and then running the ddl
2. connect to another user, use the connect command to connect to my user
    and then run the ddl.
3. Connect to another user and then explicitly run my create command on
    the target user.
*/
-- Direct connection
/*create table book (
id number primary key
);*/
-- Connect command
/*connect bookapp/p4ssw0rd;
create table book ( id number primary key);
exit; */
-- Explicit
/*
create table bookapp.book( id number primary key);
*/

-- drop all tables to start with a clean slate
--select 'drop table ' || table_name || ' cascade constraints;' from user_tables;
drop table employee cascade constraints;
drop table deptartment cascade constraints;
drop table submission cascade constraints;
drop table attachment cascade constraints;
drop table submissionTypes cascade constraints;
drop table gradingTypes cascade constraints;

-- drop all my sequences to start with a clean slate
--select 'drop sequence ' || sequence_name || ';' from user_sequences;

drop sequence Employee_SEQ;
drop sequence Deptartment_SEQ;
drop sequence Submission_SEQ;
drop sequence Attachment_SEQ;
drop sequence SubmissionTypes_SEQ;
drop sequence GradingTypes_SEQ;

-- build my schema
 -- employee = 1, customer = 0
create table employee (
    id number(10) primary key,
    f_name varchar2(20) not null,
    l_name varchar2(20) not null,
    title varchar2(20) not null check(length(title)>=2),
    login varchar2(20) unique not null check(length(login)>=2),
    pw varchar2(20) not null check(length(pw)>=2),
    super number(10),
    dept number(10),
    rTotal number(6,2) default 1000
--    constraint fk_emp_emp foreign key (super) references employee(id)
);

create table deptartment (
    id number(5) primary key,
    name varchar2(20) not null,
    head number(10) not null,
    constraint fk_deptartment_employee foreign key (head) references employee(id)
);

alter table employee add constraint fk_employee_deptartment
    foreign key (dept) references deptartment(id);

create table submissionTypes (
    id number(4) primary key,
    name varchar2(20) not null,
    returnPercent number(3) not null
);

create table gradingTypes (
    id number(4) primary key,
    name varchar2(20) not null
);

create table submission (
    id number(14) primary key,
    name varchar2(40) default 'Tuition Reimbursement Req' not null,
    typeId number(4) not null,
    amount number(6,2) not null,
    datetime varchar2(20) not null,
    submitter number(10) not null,
    firstManager number(10),
    secondManager number(10),
    benCo number(10),
    status varchar2(10) default 'pending',----need location, grading format, work-related just, worktime missed
    locationSite varchar2(30) not null,
    gradingType number(4) default 1 not null,
    workJust varchar2(256) not null,
    timeMissed number(2) default 0 not null,
    info varchar2(256) default 'Submitting this request for reimbursement approval!',
    constraint fk_sub_sub_employee foreign key (submitter) references employee(id),
    constraint fk_sub_1st_employee foreign key (firstManager) references employee(id),
    constraint fk_sub_2nd_employee foreign key (secondManager) references employee(id),
    constraint fk_sub_ben_employee foreign key (benCo) references employee(id),
    constraint fk_sub_type_subtype foreign key (typeId) references submissionTypes(id),
    constraint fk_sub_gradetype_gradetype foreign key (gradingType) references gradingTypes(id)
);--date,time,location,description,cost, (grading format, type of event),typeId, work-related justification, work time that will be missed 
----name,date,time, info = description, amount (cost*event reimb), 
----need location, grading format, work-related just, worktime missed

create table attachment (
    id number (20) primary key,
    submissionid number (14) not null,
    type varchar2(10) not null,
    target number(10),
    message varchar2(256),
    constraint fk_attach_sub foreign key (submissionid) references submission(id),
    constraint fk_attach_emp foreign key (target) references employee(id)
);



---- url for a picture of the cover. We'll deal with this later.
--create table CAR_T (
--    id number(20) primary key,
--    user_id number(20) default 1 not null,
--    make varchar2(10) not null,
--    model varchar2(14) not null,
--    monthPrice number(7,2) not null check(monthPrice>=0),
--    monthToPay number(3) not null,
--    miles number(7) not null,
--    info varchar2(1000), 
--    constraint fk_car_user foreign key (user_id) references USER_T(id)
--);
--
---- Accepting an offer changes price on car and remaining months -- 
--create table OFFER_T ( 
--    id number(20) primary key,
--    user_id number(10) not null,
--    car_id number(20) not null,
--    monthPrice number(7,2) not null,
--    status varchar2(7) default 'pending',
--    constraint fk_offer_user foreign key (user_id) references USER_T(id),
--    constraint fk_offer_car foreign key (car_id) references CAR_T(id)
--);
--
--create table PAYMENT_T (
--    id number(20) primary key,
--    user_id number(10) not null,
--    car_id number(20) not null,
--    monthPrice number(7,2) not null check(monthPrice>=0),
--    monthToPay number(3) not null,
--    constraint fk_payment_user foreign key (user_id) references USER_T(id),
--    constraint fk_payment_car foreign key (car_id) references CAR_T(id)
--);

-- start all my sequences
create sequence Employee_SEQ;
create sequence Deptartment_SEQ;
create sequence Submission_SEQ;
create sequence Attachment_SEQ;
create sequence SubmissionTypes_SEQ;
create sequence GradingTypes_SEQ;


-- create insert and update trigger for all tables:
create or replace trigger Employee_pk_trig
before insert or update on employee
for each row
begin
    if INSERTING then
        select Employee_SEQ.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger Deptartment_pk_trig
before insert or update on deptartment
for each row
begin
    if INSERTING then
        select Deptartment_SEQ.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger Submission_pk_trig
before insert or update on submission
for each row
begin
    if INSERTING then
        select Submission_SEQ.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger Attachment_pk_trig
before insert or update on attachment
for each row
begin
    if INSERTING then
        select Attachment_SEQ.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger SubmissionTypes_pk_trig
before insert or update on submissionTypes
for each row
begin
    if INSERTING then
        select SubmissionTypes_SEQ.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger GradingTypes_pk_trig
before insert or update on gradingTypes
for each row
begin
    if INSERTING then
        select GradingTypes_SEQ.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

--create or replace procedure acceptOffer(stat in varchar, offrid in number, idcar in number) 
--as 
--begin     
--    if stat = 'accept' then                 
--    update offer_t set status = 'Reject' where car_id = idcar and id != offrid;       
--    end if; 
--end; 
--/
--
--SELECT * FROM offer_t;
commit;