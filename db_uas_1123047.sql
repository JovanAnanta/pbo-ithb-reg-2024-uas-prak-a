-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 09, 2025 at 01:27 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_uas_1123047`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `password` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`id`, `password`, `name`, `address`, `phone`) VALUES
(1, 'Jovan', 'Cianjur', '0877', 'wijaya'),
(2, 'Wijaya', 'Jovan', 'Cianjur', '0877');

-- --------------------------------------------------------

--
-- Table structure for table `shipmentdetails`
--

CREATE TABLE `shipmentdetails` (
  `id` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL,
  `status` enum('pending','transit','delivered') NOT NULL,
  `current_position` varchar(255) DEFAULT NULL,
  `evidence` varchar(200) DEFAULT NULL,
  `date` date NOT NULL,
  `updated_by` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `shipmentdetails`
--

INSERT INTO `shipmentdetails` (`id`, `transaction_id`, `status`, `current_position`, `evidence`, `date`, `updated_by`) VALUES
(1, 1, 'pending', 'dfgh', 'C:\\Users\\jovan\\OneDrive\\Dokumen\\Screenshot 2024-03-30 190401.png', '2025-01-09', 'gh');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `package_type` varchar(255) NOT NULL,
  `package_weight` double NOT NULL,
  `total_cost` int(11) NOT NULL,
  `created_at` date NOT NULL,
  `receipt_name` varchar(255) NOT NULL,
  `receipt_address` varchar(255) NOT NULL,
  `receipt_phone` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`id`, `customer_id`, `package_type`, `package_weight`, `total_cost`, `created_at`, `receipt_name`, `receipt_address`, `receipt_phone`) VALUES
(1, 2, 'REGULAR', 1.1, 1, '2025-01-09', 'Ujang', 'Bandung', '0555');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `shipmentdetails`
--
ALTER TABLE `shipmentdetails`
  ADD PRIMARY KEY (`id`),
  ADD KEY `transaction_id` (`transaction_id`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `customer_id` (`customer_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `shipmentdetails`
--
ALTER TABLE `shipmentdetails`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `shipmentdetails`
--
ALTER TABLE `shipmentdetails`
  ADD CONSTRAINT `shipmentdetails_ibfk_1` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`);

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
