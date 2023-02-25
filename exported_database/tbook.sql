-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 25, 2023 at 01:28 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Library`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbook`
--

CREATE TABLE `tbook` (
  `id` int(11) NOT NULL,
  `title` varchar(60) NOT NULL,
  `author` varchar(30) NOT NULL,
  `ISBN` varchar(30) NOT NULL,
  `rent` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tbook`
--

INSERT INTO `tbook` (`id`, `title`, `author`, `ISBN`, `rent`) VALUES
(1, 'To Kill a Mockingbird', 'Harper Lee', '0446310786', 0),
(2, 'The Catcher in the Rye', 'J.D. Salinger', '0316769487', 1),
(3, '1984', 'George Orwell', '0451524934', 1),
(4, 'The Great Gatsby', 'F. Scott Fitzgerald', '0743273567', 0),
(5, 'Pride and Prejudice', 'Jane Austen', '0141439513', 0),
(6, 'The Hobbit', 'J.R.R. Tolkien', '054792822X', 1),
(7, 'The Lord of the Rings', 'J.R.R. Tolkien', '0544003411', 0),
(8, 'The Da Vinci Code', 'Dan Brown', '0307474275', 0),
(9, 'Harry Potter and the Philosopher\'s Stone', 'J.K. Rowling', '0747532699', 1),
(10, 'The Hunger Games', 'Suzanne Collins', '0439023483', 1),
(12, 'test', 'test', '122222', 0),
(14, 'sdkhskd', 'sdlsjd', '34983843', 0),
(15, 'Test Book', 'Test Author', '1234567890', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbook`
--
ALTER TABLE `tbook`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `ISBN` (`ISBN`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbook`
--
ALTER TABLE `tbook`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
