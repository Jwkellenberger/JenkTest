--insert into author(id, firstname, lastname, aboutblurb)
--    values(1, 'J.K.', 'Rowling', 'One of the richest people in the world.');
--

--insert into employee (id, f_name, l_name, title, login,pw,super,dept)
--values(10, "Joe", "Moe", "Bossman", "Boss","pw",1,1);

--insert into employee(id, f_name, l_name, title, login, pw, super, dept) 
--                values(1, 'Brad', 'Cooper', 'Boss', 'Boss', 'pw', 0, 1);
commit;
insert into employee(f_name, l_name, title, login, pw, super) 
                values('Brad', 'Cooper', 'Dept. Head', 'bossm', 'pw', 0);
insert into employee(f_name, l_name, title, login, pw, super) 
                values('Alicein', 'Chains', 'Dept. Head', 'bosshr', 'pw', 0);
insert into employee(f_name, l_name, title, login, pw, super) 
                values('Beck', 'Hansen', 'Dept. Head', 'bossr', 'pw', 0);

select * from employee;
-- select * from (employee e1 left join employee e2 on (e1.super = e2.id)) join deptartment dept on (e1.dept = dept.id) where e1.login = 'jk' AND e1.pw='pw';
-- select e1.id loginId, e1.f_name loginFn, e1.l_name loginLn, e1.title loginTitle, e1.login loginLogin, e1.super loginSuper, e1.dept loginDept, e1.rtotal loginRtotal, dept.name DeptName, dept.head DeptHead, e2.id bossId, e2.f_name bossFn, e2.l_name bossLn, e2.title bossTitle, e2.login bossLogin, e2.super bossSuper, e2.dept bossDept, e2.rtotal bossRtotal, e3.id headId, e3.f_name headFn, e3.l_name headLn, e3.title headTitle, e3.login headLogin, e3.super headSuper, e3.dept headDept, e2.rtotal bossRtotal from ((employee e1 left join employee e2 on (e1.super = e2.id)) join deptartment dept on (e1.dept = dept.id)) join employee e3 on (dept.head=e3.id) where e1.login = 'jk' AND e1.pw='pw';

commit;

insert into deptartment (id, name, head)
                    values(1,'Management', 1);
insert into deptartment (id, name, head)
                    values(1,'Human Resources', 2);
insert into deptartment (id, name, head)
                    values(1,'Research', 3);

insert into gradingTypes (id, name)
                    values(1,'GradeSubmission');
insert into gradingTypes (id, name)
                    values(2,'Presentation');
                    
                    
--update flights set flight_number = 'AA986'  where  flight_id = 7;
update employee set dept = 1 where id =1;
update employee set dept = 2 where id =2;
update employee set dept = 3 where id =3;

select * from deptartment;

insert into employee(f_name, l_name, title, login, pw, super, dept) 
                values('Corbin', 'Dallas', 'Trainer', 'corbin', 'pw', 3, 3);
insert into employee(f_name, l_name, title, login, pw, super, dept) 
                values('James', 'Kellenberger', 'Associate', 'jk', 'pw', 4, 3);
insert into employee(f_name, l_name, title, login, pw, super, dept) 
                values('Jesse', 'Ventura', 'Benco', 'jess', 'pw', 2, 2);
           
           

insert into submissiontypes(name,returnPercent)
values('University Course', 80);
insert into submissiontypes(name,returnPercent)
values('Seminar', 60);
insert into submissiontypes(name,returnPercent)
values('Cert. Prep. Classes', 75);
insert into submissiontypes(name,returnPercent)
values('Certification', 100);
insert into submissiontypes(name,returnPercent)
values('Technical Training', 90);
insert into submissiontypes(name,returnPercent)
values('Other', 30);

select * from submissiontypes;


--insert into employee(f_name, l_name, title, login, pw, super, dept) 
--                values('James', 'Kellenberger', 'Associate', 'jk', 'pw', 4, 3);

           
insert into submission(name, locationSite, gradingType, workJust, timeMissed, typeId, amount, datetime, submitter, firstManager, secondManager, benCo, status,info)
    values('Class Reimbursement', 'UNF', 1, 'Training to be a better capable programmer.', 10, 1, 440, '02/22/2019  15:00', 5,4,3,2, 'accepted', 'I did very well: A!');
    
insert into submission(name, locationSite, gradingType, workJust, timeMissed, typeId, amount, datetime, submitter, firstManager, secondManager, info)
    values('Class Reimbursement', 'WVU', 1, 'Training to apply and deploy microservices.', 5, 1, 440, '06/22/2019  16:30', 5,4,3, 'I am waiting to get my final grade!');
    
select * from submission;

--select e1.id e1Id, e1.f_name e1Fn, e1.l_name e1Ln, e1.title e1Title, e1.login e1Login, e1.super e1Super, e1.dept e1Dept, e1.rtotal e1Rtotal, dept.name DeptName, dept.head DeptHead, e2.id bossId, e2.f_name bossFn, e2.l_name bossLn, e2.title bossTitle, e2.login bossLogin, e2.super bossSuper, e2.dept bossDept, e2.rtotal bossRtotal, e3.id headId, e3.f_name headFn, e3.l_name headLn, e3.title headTitle, e3.login headLogin, e3.super headSuper, e3.dept headDept, e2.rtotal bossRtotal from ((employee e1 left join employee e2 on (e1.super = e2.id)) join deptartment dept on (e1.dept = dept.id)) join employee e3 on (dept.head=e3.id) where e1.login = 'jk' AND e1.pw='pw';
--e1.id e1Id, e1.f_name e1Fn, e1.l_name e1Ln, e1.title e1Title

--date,time,location,description,cost, (grading format, type of event),typeId, work-related justification, work time that will be missed 

select sub.id subId, sub.name subName, sub.typeid subTypeId, sub.amount subAmount, sub.datetime subDT,
sub.submitter subSub, sub.firstmanager subFirstMan, sub.secondmanager subSecondMan, sub.benco subBen,
sub.status subSta, sub.info subInfo, dept.name deptName, subtypet.name subTName, subtypet.returnpercent subTReturnPerc,
e1.id e1Id, e1.f_name e1Fn, e1.l_name e1Ln, e1.title e1Title,
e2.id e2Id, e2.f_name e1Fn, e2.l_name e2Ln, e2.title e2Title,
e3.id e3Id, e3.f_name e3Fn, e3.l_name e3Ln, e3.title e3Title,
e4.id e4Id, e4.f_name e4Fn, e4.l_name e4Ln, e4.title e4Title
from (((((submission sub join employee e1 on (sub.submitter = e1.id) join deptartment dept on (e1.dept = dept.id))
Left Outer Join employee e2 on (sub.firstmanager = e2.id))Left Outer Join employee e3 on (sub.secondmanager = e3.id))
Left Outer Join employee e4 on (sub.benco = e4.id)) Left Outer Join submissiontypes subTypeT on (sub.typeid = subTypeT.id))
where (sub.firstmanager=4) OR (sub.secondmanager=4) OR (sub.benco=4);

commit;
--create table submission (
--    id number(14) primary key,
--    name varchar(40) default 'Tuition Reimbursement Req' not null,
--    typeId number(4) not null,
--    amount number(6,2) not null,
--    submitter number(10) not null,
--    firstManager number(10),
--    secondManager number(10),
--    benCo number(10),
--    status varchar2(10) default 'pending',
--    info varchar2(256) default 'Submitting this request for reimbursement approval!',
         
--create table submissionTypes (
--    id number (4) primary key,
--    name varchar2(20) not null,
--    returnPercent number(2) not null
--);

--insert into USER_T(status, login, password) 
--values(1,'dealer','password');
--
--insert into USER_T(status, login, password) 
--values(0,'james','pw');
--
--insert into USER_T(status, login, password) 
--values(0,'andy','pw');
--
--insert into USER_T(status, login, password) 
--values(0,'kenneth','pw');
--
--insert into USER_T(status, login, password) 
--values(0,'richard','pw');
--
--insert into USER_T(status, login, password) 
--values(0,'daniel','pw');
--
--Select * from user_t;
--
--
--insert into CAR_T(user_id,make,model,monthPrice,monthToPay,miles,info)
--values(1,'Honda', 'Civic Ex', 200,50,13000,'A comfortable compact car to explore the town!');
--insert into CAR_T(user_id,make,model,monthPrice,monthToPay,miles,info)
--values(1,'Toyota', 'Highlander', 300,60,23120,'A stylish SUV for exploring!');
--insert into CAR_T(user_id,make,model,monthPrice,monthToPay,miles,info)
--values(1,'Toyota', 'Corola', 260,60,13240,'A polished sedan!');
--insert into CAR_T(user_id,make,model,monthPrice,monthToPay,miles,info)
--values(1,'Toyota', 'Rav4', 270,60,41234,'A crossover to win you over!');
--insert into CAR_T(user_id,make,model,monthPrice,monthToPay,miles,info)
--values(1,'VW', 'Jetta', 275,60,305,'A high top cruiser!');
--insert into CAR_T(user_id,make,model,monthPrice,monthToPay,miles,info)
--values(1,'Toyota', 'Camry', 230,60,240,'Perfectly new perfect car!');
--insert into CAR_T(user_id,make,model,monthPrice,monthToPay,miles,info)
--values(1,'Ford', 'Fiesta', 150,60,40234,'A cat-cat-car!');
--insert into CAR_T(user_id,make,model,monthPrice,monthToPay,miles,info)
--values(3,'Ford', 'Explorer', 350,56,63275,'Big, Rough, and Tough!');
--
--
--insert into CAR_T(user_id,make,model,monthPrice,monthToPay,miles,info)
--values(3,'Ford', 'Escape', 190,60,26533,'A decent affordable sedan!');
--
--Select * from car_t;
--
--insert into OFFER_T(user_id,car_id,monthPrice)
--values(2,1,227);
--insert into OFFER_T(user_id,car_id,monthPrice)
--values(6,1,226);
--insert into OFFER_T(user_id,car_id,monthPrice)
--values(5,1,225);
--insert into OFFER_T(user_id,car_id,monthPrice)
--values(4,1,220);
--insert into OFFER_T(user_id,car_id,monthPrice)
--values(6,2,310);
--insert into OFFER_T(user_id,car_id,monthPrice)
--values(3,2,315);
--
--Select * from OFFER_T;
--
--insert into PAYMENT_T(user_id,car_id,monthPrice,monthToPay)
--values(3,3,190, 58);
--insert into PAYMENT_T(user_id,car_id,monthPrice,monthToPay)
--values(3,3,190, 59);
--insert into PAYMENT_T(user_id,car_id,monthPrice,monthToPay)
--values(3,8,350, 56);
--insert into PAYMENT_T(user_id,car_id,monthPrice,monthToPay)
--values(3,8,350, 57);
--insert into PAYMENT_T(user_id,car_id,monthPrice,monthToPay)
--values(3,8,350, 58);
--insert into PAYMENT_T(user_id,car_id,monthPrice,monthToPay)
--values(3,8,350, 59);


--select * from payment_t;

commit;

--SELECT * FROM offer_t;