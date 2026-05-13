-- ============================================
-- Student Record Management System
-- Database Schema
-- MySQL
-- ============================================

CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

CREATE TABLE IF NOT EXISTS students (
    student_id    INT AUTO_INCREMENT PRIMARY KEY,
    first_name    VARCHAR(50)  NOT NULL,
    last_name     VARCHAR(50)  NOT NULL,
    email         VARCHAR(100) NOT NULL UNIQUE,
    course        VARCHAR(100) NOT NULL,
    year_level    INT          NOT NULL CHECK (year_level BETWEEN 1 AND 5),
    gpa           DECIMAL(3,2)          CHECK (gpa BETWEEN 0.00 AND 4.00),
    created_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);

-- Sample data
INSERT INTO students (first_name, last_name, email, course, year_level, gpa) VALUES
('Juan',  'Dela Cruz',  'juan.delacruz@school.edu',  'BSIT', 2, 1.75),
('Maria', 'Santos',     'maria.santos@school.edu',   'BSCS', 3, 1.50),
('Pedro', 'Reyes',      'pedro.reyes@school.edu',    'BSIT', 1, 2.00);
