-- create table Passengers(
-- Fname varchar(30),
-- Mname varchar(30),
-- Lname varchar(30),
-- nationality varchar(50),users
-- nationalID bigint,
-- yearOfBirth year,
-- job varchar(30)	
-- );
-- insert into USERS
-- values();

-- Set SQL_SAFE_UPDATES=0;

-- DELETE FROM USERS WHERE Fname="Mohamed";
-- select * from USERS;

-- Set SQL_SAFE_UPDATES=0;

-- select * from USERS;



-- Set SQL_SAFE_UPDATES=0; DELETE FROM USERS WHERE Fname="Mark"; select * from USERS; Set SQL_SAFE_UPDATES=0;

-- Set SQL_SAFE_UPDATES=0;
-- Alter table USERS 
-- modify id int
-- first;
-- select * from USERS;
-- Set SQL_SAFE_UPDATES=1;

-- use db;

-- create table flights(
-- 	initialpoint varchar(70),
--     destinypoint varchar(70),
--     price decimal(5,1) Not Null
-- ); 

-- Alter Table flights 
-- 	Modify initialpoint varchar(70) Not null;

-- insert into flights
-- values("London","paris",450),("Cairo","Riyadah",250);
-- select * from flights;


--  Set SQL_SAFE_UPDATES=0;
--  Alter table flights 
--  Add id int
--  first;
--  select * from flights;
--  Set SQL_SAFE_UPDATES=1;

--  Set SQL_SAFE_UPDATES=0;
-- update flights
-- Set id="2"
-- where initialpoint="Cairo";
--  Set SQL_SAFE_UPDATES=1;

-- select * from flights;


-- create table flights(
-- 	id int,
-- 	initialpoint varchar(70),
--     destinypoint varchar(70),
--     price decimal(5,1) Not Null
--     
-- --     check (price>=200)                  -- minimum price for all flights . without name
-- constraint checkPrice check (price>=200)   -- We can give our check Constraint  a name ,so if we want to modify it/Drop it later    
-- );

-- Alter table flights
-- Add constraint checkPrice check (price>=200);


-- insert into flights
-- values(3,"Buda","Cape-town",225); -- successful Row addition 
-- select * from flights;  

-- Set SQL_SAFE_UPDATES=0;
-- Alter table flights
-- Drop Check checkPrice;

-- Set SQL_SAFE_UPDATES=1;




-- use fbms;
-- create table USERS(
-- customer_id int primary key auto_increment,
-- Fname varchar(30) Not Null,
-- Mname varchar(30) Not Null,
-- Lname varchar(30) Not Null,
-- nationality varchar(50) Not Null,
-- nationalID bigint Not Null,
-- yearOfBirth year Not Null,
-- job varchar(30),
-- MomentOfRegester datetime

-- );



-- Insert Into users(Fname,Mname,Lname,nationality,nationalID,yearOfBirth,job) values();



-- Set SQL_SAFE_UPDATES=0;
-- Alter table USERS
-- Add PhoneNumber int;
-- Set SQL_SAFE_UPDATES=1;

-- ALTER TABLE USERS
-- modify column MomentOfRegester DateTime DEFAULT NOW();

SET SQL_SAFE_UPDATES=0;

ALTER TABLE USERS
modify email varchar(200) after yearOfBirth;

SET SQL_SAFE_UPDATES=1;

select * from USERS;





