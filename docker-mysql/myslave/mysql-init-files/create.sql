CREATE DATABASE mystore;

CREATE TABLE mystore.temp(id VARCHAR(100) PRIMARY KEY);

create user slaveuser@'%' identified by 'slavepassword';
grant all privileges on mystore.* to slaveuser@'%' identified by 'slavepassword';
