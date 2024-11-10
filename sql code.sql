-- Create the database
CREATE DATABASE HomeApplianceDB;

-- Use the database
USE HomeApplianceDB;

-- Create the commands table to store voice commands
CREATE TABLE Commands (
    id INT AUTO_INCREMENT PRIMARY KEY,
    command VARCHAR(255) NOT NULL,
    appliance_name VARCHAR(255) NOT NULL,
    action VARCHAR(10) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
