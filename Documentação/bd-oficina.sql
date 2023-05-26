/* Sistema para Oficina Mecânica: */

CREATE SCHEMA `lps_oficina`;
USE `lps_oficina`;

CREATE TABLE person (
    id_person INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    name VARCHAR(100) not null, 
    cpf VARCHAR(11) not null UNIQUE, 
    tel VARCHAR(11) not null,
    address VARCHAR(300) not null, 
    email VARCHAR(50) not null
    /*dataNascimento DATE not null*/
);

CREATE TABLE employee (
    position VARCHAR(100) not null,
    salary DOUBLE not null, 
    workHours FLOAT not null, 
    contractDate DATE not null, 
	
	id_employee INT not null,
    FOREIGN KEY(id_employee) REFERENCES person(id_person)
);

CREATE TABLE client (
    /*dataCadastro DATE not null,*/
	
	id_client INT not null,
    FOREIGN KEY(id_client) REFERENCES person(id_person)
);

CREATE TABLE provider (
    product VARCHAR(100) not null, 
    cnpj VARCHAR(14) not null, 
    companyName VARCHAR(100) not null, 
	
	id_provider INT not null,
    FOREIGN KEY(id_provider) REFERENCES person(id_person)
);



CREATE TABLE service (
    id_service INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    serviceType VARCHAR(20) not null, 
    serviceDate DATE not null,
    totalPrice DOUBLE not null,
	
	fk_id_client INT not null,
    FOREIGN KEY(fk_id_client) REFERENCES client(id_client),
	fk_id_employee INT not null,
    FOREIGN KEY(fk_id_employee) REFERENCES employee(id_employee)	
);

CREATE TABLE purchase (
    fiscalNote VARCHAR(20) UNIQUE not null,
	
	id_purchase INT not null,
    FOREIGN KEY(id_purchase) REFERENCES service(id_service)
);

CREATE TABLE budget (
    serviceProvided VARCHAR(200),
	
	id_budget INT not null,
    FOREIGN KEY(id_budget) REFERENCES service(id_service)
);

CREATE TABLE product (
    id_product INT PRIMARY KEY UNIQUE,
    name VARCHAR(100) not null,
    qtyStock INT not null,
    price DOUBLE not null,
    cost DOUBLE not null,
	
	fk_id_provider INT not null,
    FOREIGN KEY(fk_id_provider) REFERENCES provider(id_provider)
);

CREATE TABLE service_item (
	id_service_item INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    qtyProduct INT not null,
    totalPrice DOUBLE not null,
    
    fk_id_service INT not null,
    FOREIGN KEY(fk_id_service) REFERENCES service(id_service),
	fk_id_productINT not null,
    FOREIGN KEY(fk_id_product) REFERENCES product(id_product)
);

CREATE TABLE vehicle (
    id_vehicle INT PRIMARY KEY UNIQUE,
    model VARCHAR(50) not null,
    year INT not null,
    brand VARCHAR(50) not null,
    color VARCHAR(50) not null,
    plaque VARCHAR(10) not null,
    category VARCHAR(50) not null,
	
	fk_id_client INT not null,
    FOREIGN KEY(fk_id_client) REFERENCES client(id_client)
);