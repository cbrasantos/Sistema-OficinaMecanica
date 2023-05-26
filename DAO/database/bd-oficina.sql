/* Sistema para Oficina Mecânica: */

CREATE SCHEMA `lps_oficina`;
USE `lps_oficina`;

CREATE TABLE person (
    id_person INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) not null, 
    cpf VARCHAR(14) not null UNIQUE, 
    tel VARCHAR(15) not null,
    address VARCHAR(300) not null, 
    email VARCHAR(50) not null
);

CREATE TABLE employee (
    position VARCHAR(100) not null,
    salary DOUBLE not null, 
    workHours FLOAT not null, 
    contractDate DATE not null, 
	
	id_employee INT AUTO_INCREMENT PRIMARY KEY,
    id_person INT
);

ALTER TABLE employee ADD CONSTRAINT `fk_employee_person` FOREIGN KEY (id_person) REFERENCES person(id_person) ON DELETE CASCADE;

CREATE TABLE client (	
	id_client INT AUTO_INCREMENT PRIMARY KEY,
	id_person INT
);

ALTER TABLE client ADD CONSTRAINT `fk_client_person` FOREIGN KEY (id_person) REFERENCES person(id_person) ON DELETE CASCADE;

CREATE TABLE provider (
    product VARCHAR(100) not null, 
    cnpj VARCHAR(14) not null, 
    companyName VARCHAR(100) not null, 
	
	id_provider INT AUTO_INCREMENT PRIMARY KEY,
    id_person INT
);

ALTER TABLE provider ADD CONSTRAINT `fk_provider_person` FOREIGN KEY (id_person) REFERENCES person(id_person) ON DELETE CASCADE;

CREATE TABLE service (
    serviceType VARCHAR(20) not null, 
    serviceDate DATE not null,
    totalPrice DOUBLE not null,
	
	id_service INT AUTO_INCREMENT PRIMARY KEY,
	id_client INT,
	id_employee INT	
);

ALTER TABLE service ADD CONSTRAINT `fk_service_client` FOREIGN KEY (id_client) REFERENCES client(id_client) ON DELETE CASCADE;
ALTER TABLE service ADD CONSTRAINT `fk_service_employee` FOREIGN KEY (id_employee) REFERENCES employee(id_employee) ON DELETE CASCADE;


CREATE TABLE purchase (
    fiscalNote VARCHAR(20) UNIQUE not null,
	
	id_purchase INT AUTO_INCREMENT PRIMARY KEY,
	id_service INT
);

ALTER TABLE purchase ADD CONSTRAINT `fk_purchase_service` FOREIGN KEY (id_service) REFERENCES service(id_service) ON DELETE CASCADE;

CREATE TABLE budget (
    serviceProvided VARCHAR(200),
	
	id_budget INT AUTO_INCREMENT PRIMARY KEY,
	id_service INT
);

ALTER TABLE budget ADD CONSTRAINT `fk_budget_service` FOREIGN KEY (id_service) REFERENCES service(id_service) ON DELETE CASCADE;

CREATE TABLE product (
    name VARCHAR(100) not null,
    qtyStock INT not null,
    price DOUBLE not null,
    cost DOUBLE not null,
	
	id_product INT PRIMARY KEY,
	id_provider INT
);

ALTER TABLE product ADD CONSTRAINT `fk_product_provider` FOREIGN KEY (id_provider) REFERENCES provider(id_provider) ON DELETE CASCADE;

CREATE TABLE service_item (
    qtyProduct INT not null,
    totalPrice DOUBLE not null,
    
	id_service_item INT AUTO_INCREMENT PRIMARY KEY,
    id_service INT,
	id_product INT
);

ALTER TABLE service_item ADD CONSTRAINT `fk_item_service` FOREIGN KEY (id_service) REFERENCES service(id_service) ON DELETE CASCADE;
ALTER TABLE service_item ADD CONSTRAINT `fk_item_product` FOREIGN KEY (id_product) REFERENCES product(id_product) ON DELETE CASCADE;

CREATE TABLE vehicle (
    model VARCHAR(50) not null,
    year INT not null,
    brand VARCHAR(50) not null,
    color VARCHAR(50) not null,
    plaque VARCHAR(10) not null,
    category VARCHAR(50) not null,
	
	id_vehicle INT PRIMARY KEY,
	id_client INT
);

ALTER TABLE vehicle ADD CONSTRAINT `fk_vehicle_client` FOREIGN KEY (id_client) REFERENCES client(id_client) ON DELETE CASCADE;