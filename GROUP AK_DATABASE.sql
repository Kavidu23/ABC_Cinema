-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 19, 2024 at 03:21 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cinema`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'admin', '223b55002f7472f3339346e6be760a0ddddc8d6202772991c2720859414aa497');

-- --------------------------------------------------------

--
-- Table structure for table `complexes`
--

CREATE TABLE `complexes` (
  `complex_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `location` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `complexes`
--

INSERT INTO `complexes` (`complex_id`, `name`, `location`) VALUES
(1, 'BBC Complex', 'Colombo 4'),
(2, 'CSN Complex', 'Colombo 6');

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `feedback` text NOT NULL,
  `rating` int(11) NOT NULL,
  `date_submitted` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`id`, `name`, `email`, `feedback`, `rating`, `date_submitted`) VALUES
(1, 'kevin lakshna', 'kashmika123@outlook.com', 'ghghhgshghu', 4, '2024-12-13 16:56:40'),
(2, 'John Doe', 'john.doe@example.com', 'Great service!', 5, '2024-12-13 17:21:54'),
(3, 'kevin lakshna', 'kashmika123@outlook.com', 'Good experience.Thank you ', 5, '2024-12-16 12:06:01'),
(4, 'kashmika', 'kashmika123@outlook.com', 'It is a good movie', 5, '2024-12-17 08:42:40');

-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE `movie` (
  `id` int(11) NOT NULL,
  `trailer` varchar(255) NOT NULL,
  `banner_image` varchar(255) NOT NULL,
  `movie_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `movie`
--

INSERT INTO `movie` (`id`, `trailer`, `banner_image`, `movie_name`) VALUES
(1, 'https://www.youtube.com/watch?v=hDZ7y8RP5HE&pp=ygUObW9uYSAyIHRyYWlsZXI%3D', 'moana-2-imax-web.jpg', 'MOANA 2'),
(2, 'https://www.youtube.com/watch?v=U8XH3W0cMss&pp=ygUPcmVkIG9uZSB0cmFpbGVy', 'red-one-movie-3840x2160-19152.jpg', 'RED ONE'),
(3, 'https://www.youtube.com/watch?v=6COmYeLsz4c&pp=ygUNd2lrZWQgdHJhaWxlcg%3D%3D', 'wicked-movie-poster-3840x2160-18391.jpeg', 'WIKED'),
(6, 'https://www.youtube.com/watch?v=YkFaDqi2ueM&pp=ygUNYW1yYW4gdHJhaWxlcg%3D%3D', 'Amaran-poster2.jpg', 'AMARAN');

-- --------------------------------------------------------

--
-- Table structure for table `now_showing`
--

CREATE TABLE `now_showing` (
  `id` int(11) NOT NULL,
  `movie_name` varchar(100) NOT NULL,
  `movie_image` varchar(255) NOT NULL,
  `trailer` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `now_showing`
--

INSERT INTO `now_showing` (`id`, `movie_name`, `movie_image`, `trailer`) VALUES
(1, 'WIKED', 'poster1.jpg', 'https://www.youtube.com/watch?v=6COmYeLsz4c&pp=ygUNV0lLRUQgVFJBSUxFUg%3D%3D'),
(2, 'GLADIATOR', 'gladiator.jpg', 'https://www.youtube.com/watch?v=4rgYUipGJNo&pp=ygUTZ2xhZGlhdG9yIDIgdHJhaWxlcg%3D%3D'),
(3, 'MOANA 2', '1383741.jpg', 'https://www.youtube.com/watch?v=hDZ7y8RP5HE&pp=ygUPbW9hbmEgMiB0cmFpbGVy'),
(4, 'RED ONE', 'red-one-movie-3840x2160-19152.jpg', 'https://www.youtube.com/watch?v=7l3hfD74X-4&pp=ygURcmVkIG9uZSAyIHRyYWlsZXI%3D');

-- --------------------------------------------------------

--
-- Table structure for table `seat_selections`
--

CREATE TABLE `seat_selections` (
  `selection_id` int(11) NOT NULL,
  `now_showing_id` int(11) NOT NULL,
  `showtime` varchar(255) DEFAULT NULL,
  `seat_number` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `seat_selections`
--

INSERT INTO `seat_selections` (`selection_id`, `now_showing_id`, `showtime`, `seat_number`) VALUES
(1, 1, '9:00 PM', 'D1'),
(2, 1, '9:00 PM', 'D3'),
(4, 1, '9:00 PM', 'E2'),
(5, 1, '9:00 PM', 'F1'),
(6, 1, '9:00 PM', 'B3'),
(7, 1, '9:00 PM', 'E4'),
(8, 2, '9:00 PM', 'A1'),
(9, 2, '9:00 PM', 'F2'),
(10, 2, '9:00 PM', 'F3'),
(11, 2, '9:00 PM', 'F4'),
(12, 2, '9:00 PM', 'F5'),
(13, 1, '9:00 PM', 'A1'),
(14, 1, '9:00 PM', 'A2');

-- --------------------------------------------------------

--
-- Table structure for table `showtime`
--

CREATE TABLE `showtime` (
  `id` int(11) NOT NULL,
  `movie_id` int(11) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `format` varchar(10) DEFAULT NULL,
  `showtime` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `language` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `showtime`
--

INSERT INTO `showtime` (`id`, `movie_id`, `duration`, `format`, `showtime`, `language`) VALUES
(2, 1, 270, '3D', '2024-12-10 17:30:00', 'ENGLISH'),
(4, 2, 155, '3D', '2024-12-17 04:30:00', 'ENGLISH');

-- --------------------------------------------------------

--
-- Table structure for table `showtime_details`
--

CREATE TABLE `showtime_details` (
  `detail_id` int(11) NOT NULL,
  `showtime_id` int(11) NOT NULL,
  `complex_id` int(11) NOT NULL,
  `available_date` date NOT NULL,
  `price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `showtime_details`
--

INSERT INTO `showtime_details` (`detail_id`, `showtime_id`, `complex_id`, `available_date`, `price`) VALUES
(2, 2, 2, '2024-12-10', 3000.00);

-- --------------------------------------------------------

--
-- Table structure for table `upcoming`
--

CREATE TABLE `upcoming` (
  `id` int(11) NOT NULL,
  `movie_name` varchar(100) NOT NULL,
  `trailer` varchar(255) NOT NULL,
  `movie_image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `upcoming`
--

INSERT INTO `upcoming` (`id`, `movie_name`, `trailer`, `movie_image`) VALUES
(4, 'KARVEN THE HUNTER', 'https://www.youtube.com/watch?v=rze8QYwWGMs&pp=ygUZS0FSVkVOIFRIRSBIVU5URVIgVFJBSUxFUg%3D%3D', 'karvan.jpeg'),
(6, 'MUFASA - THE LION KING', 'https://www.youtube.com/watch?v=lMXh6vjiZrI&pp=ygUZbXVmYXNhIHRoZSBsaW9uIGtpbmcgMjAyNA%3D%3D', 'mufasa.jpeg'),
(7, ' MISSION: IMPOSSIBLE  THE FINAL RECKONING', 'https://www.youtube.com/watch?v=NOhDyUmT9z0&pp=ygUcMjAyNCB1cGNvbWluZyBtb3ZpZXMgdHJhaWxlcg%3D%3D', 'mission.jpg'),
(8, 'PUSHPA 2 - THE RULE', 'https://www.youtube.com/watch?v=1kVK0MZlbI4&pp=ygUcMjAyNCB1cGNvbWluZyBtb3ZpZXMgdHJhaWxlcg%3D%3D', 'pushpa.jpeg');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `pnumber` varchar(15) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `name`, `email`, `pnumber`, `password`) VALUES
(1, 'kavidu', 'hasithakashmika@gmail.com', '0761963620', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3'),
(2, 'kashmik', 'kavindulakthi@gmail.com', '0785896833', 'a665a45920422f9d417e4867efdc4fb8a04a1f3fff1fa07e998e86f7f7a27ae3');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `complexes`
--
ALTER TABLE `complexes`
  ADD PRIMARY KEY (`complex_id`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `movie`
--
ALTER TABLE `movie`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `now_showing`
--
ALTER TABLE `now_showing`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `seat_selections`
--
ALTER TABLE `seat_selections`
  ADD PRIMARY KEY (`selection_id`),
  ADD KEY `now_showing_id` (`now_showing_id`);

--
-- Indexes for table `showtime`
--
ALTER TABLE `showtime`
  ADD PRIMARY KEY (`id`),
  ADD KEY `movie_id` (`movie_id`);

--
-- Indexes for table `showtime_details`
--
ALTER TABLE `showtime_details`
  ADD PRIMARY KEY (`detail_id`),
  ADD KEY `showtime_id` (`showtime_id`),
  ADD KEY `complex_id` (`complex_id`);

--
-- Indexes for table `upcoming`
--
ALTER TABLE `upcoming`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `email_2` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `complexes`
--
ALTER TABLE `complexes`
  MODIFY `complex_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `feedback`
--
ALTER TABLE `feedback`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `movie`
--
ALTER TABLE `movie`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `now_showing`
--
ALTER TABLE `now_showing`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `seat_selections`
--
ALTER TABLE `seat_selections`
  MODIFY `selection_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `showtime`
--
ALTER TABLE `showtime`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `showtime_details`
--
ALTER TABLE `showtime_details`
  MODIFY `detail_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `upcoming`
--
ALTER TABLE `upcoming`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `seat_selections`
--
ALTER TABLE `seat_selections`
  ADD CONSTRAINT `seat_selections_ibfk_1` FOREIGN KEY (`now_showing_id`) REFERENCES `now_showing` (`id`);

--
-- Constraints for table `showtime`
--
ALTER TABLE `showtime`
  ADD CONSTRAINT `showtime_ibfk_1` FOREIGN KEY (`movie_id`) REFERENCES `now_showing` (`id`);

--
-- Constraints for table `showtime_details`
--
ALTER TABLE `showtime_details`
  ADD CONSTRAINT `showtime_details_ibfk_1` FOREIGN KEY (`showtime_id`) REFERENCES `showtime` (`id`),
  ADD CONSTRAINT `showtime_details_ibfk_2` FOREIGN KEY (`complex_id`) REFERENCES `complexes` (`complex_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
