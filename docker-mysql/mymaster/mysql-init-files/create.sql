CREATE DATABASE mystore;

CREATE TABLE mystore.temp(id VARCHAR(100) PRIMARY KEY);

-- create masteruser and grant privileges
create user masteruser@'172.16.0.%' identified by 'masterpassword';
grant all privileges on *.* to masteruser@'172.16.0.%' identified by 'masterpassword';

-- replication
grant replication slave on *.* to slaveuser@'172.16.0.%' identified by 'slavepassword';
