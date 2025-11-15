CREATE DATABASE  IF NOT EXISTS `dbapp` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `dbapp`;

-- employee table
DROP TABLE IF EXISTS employee;
CREATE TABLE employee (
	employee_id int(10) NOT NULL,
	last_name varchar(45) NOT NULL,
    first_name varchar(45) NOT NULL,
    middle_name varchar(45) NOT NULL,
    date_of_birth date NOT NULL,
    committee enum("Administrative", "Finance", "Rescue", "Evacuation") NOT NULL,
    position enum("Administrative Staff", "Tresurer", "Secretary", "Response Officer", "Captain", "Medic") NOT NULL,
    contact_number varchar(20) NOT NULL,
    available enum("Y", "N") NOT NULL,
    PRIMARY KEY (employee_id));

-- drop data for employees
INSERT INTO employee VALUES
(1, "Dela Cruz", "Juan", "Jimenez", "2000-01-02", "Administrative", "Administrative Staff", 09123456789, "N"),
(2, "Rivera", "Francesca", "Carlos", "1990-03-04", "Rescue", "Response Officer", 09987654321, "Y");
    
-- resident table
DROP TABLE IF EXISTS resident;
CREATE TABLE resident (
	resident_id	int(10) NOT NULL,
	last_name varchar(45) NOT NULL,
    first_name varchar(45) NOT NULL,
    middle_name varchar(45) NOT NULL,
    gender enum("M", "F") NOT NULL,
    date_of_birth date NOT NULL,
    civil_status enum("SI", "M", "SE", "W", "O") NOT NULL,
    mobile_number varchar(20) NOT NULL,
    street_address varchar(100) NOT NULL,
    PRIMARY KEY (resident_id));
    
-- drop data for resident
INSERT INTO resident VALUES
(1, "Arroyo", "Kyle", "Cordero", "M", "2003-03-21", "SI", 09143728492, "27 Road 1234 Street"),
(2, "Espiritu", "Leah", "Rubio", "F", "2002-11-17", "M", 09937502853, "12 Road 4567 Street");

-- shelter table
DROP TABLE IF EXISTS shelter;
CREATE TABLE shelter (
	shelter_id int(10) NOT NULL,
    shelter_name varchar(45) NOT NULL,
    address varchar(200) NOT NULL,
    capacity int NOT NULL,
    status enum("Open", "Closed") NOT NULL,
    PRIMARY KEY (shelter_id));
    
-- drop data for shelter
INSERT INTO shelter VALUES
(1, "Basketball Court", "Somewhere st. somewhere road", 20, "Open"),
(2, "City Hall", "Somewhere st. somewhere road", 10, "Closed");

-- equipment table
DROP TABLE IF EXISTS equipment;
CREATE TABLE equipment (
	equipment_id int(10) NOT NULL,
    equipment_name varchar(45) NOT NULL,
    quantity_per_name int NOT NULL,
    availability enum("Available", "Not Available"),
    PRIMARY KEY (equipment_id));

-- drop data for equipment
INSERT INTO equipment VALUES
(1, "ambulance", 2, "Available"),
(2, "medical kit", 5, "Not Available");




