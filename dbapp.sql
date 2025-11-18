CREATE DATABASE  IF NOT EXISTS `dbapp` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `dbapp`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS response_employee;
DROP TABLE IF EXISTS response_resident;
DROP TABLE IF EXISTS response_equipment;
DROP TABLE IF EXISTS response;
DROP TABLE IF EXISTS disaster;
DROP TABLE IF EXISTS shelter;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS resident;
DROP TABLE IF EXISTS equipment;

SET FOREIGN_KEY_CHECKS = 1;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------------------
-- employee table
-- ----------------------------------------
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
    password varchar(45) NOT NULL,
    PRIMARY KEY (employee_id));

-- drop data into employee table
INSERT INTO employee VALUES
(1, "Dela Cruz", "Juan", "Jimenez", "2000-01-02", "Administrative", "Administrative Staff", 09123456789, "N", "1234567890"),
(2, "Rivera", "Francesca", "Carlos", "1990-03-04", "Rescue", "Response Officer", 09987654321, "Y", "password");


-- ----------------------------------------
-- resident table
-- ----------------------------------------
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
    
-- drop data into resident table
INSERT INTO resident VALUES
(1, "Arroyo", "Kyle", "Cordero", "M", "2003-03-21", "SI", 09143728492, "27 Road 1234 Street"),
(2, "Espiritu", "Leah", "Rubio", "F", "2002-11-17", "M", 09937502853, "12 Road 4567 Street");


-- ----------------------------------------
-- shelter table
-- ----------------------------------------
CREATE TABLE shelter (
	shelter_id int(10) NOT NULL,
    shelter_name varchar(45) NOT NULL,
    address varchar(200) NOT NULL,
    capacity int NOT NULL,
    status enum("Open", "Closed") NOT NULL,
    PRIMARY KEY (shelter_id));
    
-- drop data into shelter table
INSERT INTO shelter VALUES
(1, "Basketball Court", "Somewhere st. somewhere road", 20, "Open"),
(2, "City Hall", "Somewhere st. somewhere road", 10, "Closed");


-- ----------------------------------------
-- equipment table
-- ----------------------------------------
CREATE TABLE equipment (
	equipment_id int(10) NOT NULL,
    equipment_name varchar(45) NOT NULL,
    quantity_per_name int NOT NULL,
    availability enum("Available", "Not Available"),
    PRIMARY KEY (equipment_id));

-- drop data into equipment table
INSERT INTO equipment VALUES
(1, "Ambulance", 2, "Available"),
(2, "Medical Kit", 5, "Available"),
(3, "Life Vest", 0, "Not Available");


-- ----------------------------------------
-- response table
-- ----------------------------------------
CREATE TABLE response (
	response_id int(10) NOT NULL,
    disaster_id int(10) NOT NULL,
    shelter_id int(10) NOT NULL,
    employee_id int(10) NOT NULL,
	response_type enum("RS", "E", "MA", "RL", "RO") NOT NULL,
    response_start DateTime NOT NULL,
    response_end DateTime NOT NULL,
    PRIMARY KEY (response_id),
    FOREIGN KEY (disaster_id) REFERENCES disaster(disaster_id),
    FOREIGN KEY (shelter_id) REFERENCES shelter(shelter_id),
	FOREIGN KEY (employee_id) REFERENCES employee(employee_id));

-- drop data into response table
INSERT INTO response VALUES
(1, 1, 1, 1, "RS", "2025-01-01 12:12:12", "2025-01-02 12:12:12"),
(2, 2, 2, 2, "E", "2024-02-02 07:07:07", "2025-07-03 07:07:07");


-- ----------------------------------------
-- response_resident junction table
-- ----------------------------------------
CREATE TABLE response_resident (
	response_id int(10) NOT NULL,
    resident_id int(10) NOT NULL,
    role enum("rescued", "evacuated", "injured", "volunteer") NOT NULL,
    PRIMARY KEY (response_id, resident_id),
    FOREIGN KEY (response_id) REFERENCES response(response_id),
    FOREIGN KEY (resident_id) REFERENCES resident(resident_id));


-- ----------------------------------------
-- response_equipment junction table
-- ----------------------------------------
CREATE TABLE response_equipment (
	response_id int(10) NOT NULL,
    equipment_id int(10) NOT NULL,
    quantity_used int,
    date_lent Date,
    date_returned Date,
    user varchar(45),
    PRIMARY KEY (response_id, equipment_id),
    FOREIGN KEY (response_id) REFERENCES response(response_id),
    FOREIGN KEY (equipment_id) REFERENCES equipment(equipment_id));


-- ----------------------------------------
-- disaster table
-- ----------------------------------------
CREATE TABLE disaster (
	disaster_id int(10) NOT NULL,
    disaster_type varchar(45) NOT NULL,
    date_occurred Date NOT NULL,
    location varchar(45) NOT NULL,
	severity enum("L", "M", "H", "S") NOT NULL,
    casualties int NOT NULL,
    damages int NOT NULL,
    PRIMARY KEY (disaster_id));

-- drop data into disaster table
INSERT INTO disaster VALUES
(1, "Typhoon", "2025-02-03", "Some St.", "L", 5, 50000),
(2, "Fire", "2024-09-21", "Another St.", "S", 2, 3000);




