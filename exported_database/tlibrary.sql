-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Feb 25, 2023 at 01:26 PM
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
-- Table structure for table `tlibrary`
--

CREATE TABLE `tlibrary` (
  `id` int(11) NOT NULL,
  `login` varchar(30) NOT NULL,
  `ISBN` varchar(30) NOT NULL,
  `returnDate` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `tlibrary`
--

INSERT INTO `tlibrary` (`id`, `login`, `ISBN`, `returnDate`) VALUES
(1, 'admin', '0439023483', '2023-02-13'),
(2, 'janusz123', '0747532699', '2023-02-13'),
(3, 'sdutka123', '0451524934', '2023-03-01'),
(4, 'wojtek', '0316769487', '2023-03-01'),
(5, 'wojtek', '054792822X', '2023-03-01'),
(6, 'admin', '1234567890', '2023-03-08');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tlibrary`
--
ALTER TABLE `tlibrary`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tlibrary`
--
ALTER TABLE `tlibrary`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
