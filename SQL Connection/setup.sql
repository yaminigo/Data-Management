CREATE USER 'inf551'@'localhost' IDENTIFIED BY 'inf551';

GRANT ALL PRIVILEGES ON * . * TO 'inf551'@'localhost';

create database inf551;

USE inf551;

create table Company(cname varchar(100), stockPrice decimal(15,3), country varchar(50), primary key(cname));

create table Person(name varchar(100), phoneNumber varchar(50), city varchar(50), primary key(name));

create table Product(name varchar(100), price decimal(15,3), category varchar(50), maker varchar(50) references Company(cname), primary key(name));

ALTER TABLE Product 
ADD CONSTRAINT Product_FK
  FOREIGN KEY (maker)
  REFERENCES Company(cname)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;
  
create table Purchase(buyer varchar(100) references Person(name), seller varchar(100) references Person(name), store varchar(100), product varchar(100), primary key(buyer, seller, store, product));
  
ALTER TABLE Purchase 
ADD CONSTRAINT Purchase_buyer_FK
  FOREIGN KEY (buyer)
  REFERENCES Person(name)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;

ALTER TABLE Purchase 
ADD CONSTRAINT Purchase_seller_FK
  FOREIGN KEY (seller)
  REFERENCES Person(name)
  ON DELETE RESTRICT
  ON UPDATE RESTRICT;