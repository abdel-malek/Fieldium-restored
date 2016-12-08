-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Dec 08, 2016 at 11:42 AM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `fieldium`
--

-- --------------------------------------------------------

--
-- Table structure for table `amenity`
--

CREATE TABLE IF NOT EXISTS `amenity` (
  `amenity_id` int(11) NOT NULL AUTO_INCREMENT,
  `en_name` varchar(45) DEFAULT NULL,
  `ar_name` varchar(40) NOT NULL,
  `image` text,
  PRIMARY KEY (`amenity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `amenity`
--

INSERT INTO `amenity` (`amenity_id`, `en_name`, `ar_name`, `image`) VALUES
(1, 'shower', '', NULL),
(2, 'lights', '', NULL),
(3, 'new', '', '71774-14322331_264636960602451_6186070065967074441_n.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `area`
--

CREATE TABLE IF NOT EXISTS `area` (
  `area_id` int(11) NOT NULL AUTO_INCREMENT,
  `en_name` varchar(45) DEFAULT NULL,
  `ar_name` varchar(40) NOT NULL,
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `area`
--

INSERT INTO `area` (`area_id`, `en_name`, `ar_name`) VALUES
(1, 'Dubai', ''),
(2, 'area2', 'area2');

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE IF NOT EXISTS `booking` (
  `booking_id` int(11) NOT NULL AUTO_INCREMENT,
  `field_id` int(11) DEFAULT NULL,
  `player_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `state_id` int(11) DEFAULT '1',
  `creation_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `date` date DEFAULT NULL,
  `start` time DEFAULT NULL,
  `duration` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `notes` text,
  `manually` int(11) DEFAULT '0',
  `deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`booking_id`),
  KEY `field_id_booking_fk_idx` (`field_id`),
  KEY `state_id_booking_fk_idx` (`state_id`),
  KEY `player_id_booking_fk_idx` (`player_id`),
  KEY `user_id_booking_fk_idx` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=21 ;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`booking_id`, `field_id`, `player_id`, `user_id`, `state_id`, `creation_date`, `date`, `start`, `duration`, `total`, `notes`, `manually`, `deleted`) VALUES
(2, 19, 1, NULL, 1, '2016-11-29 22:59:12', '2016-12-04', '10:00:00', 2, 200, 'notes', 0, 0),
(3, 19, 2, NULL, 1, '2016-11-29 23:00:23', '2016-12-04', '09:00:00', 3, 200, 'notes', 0, 0),
(4, 8, 1, NULL, 1, '2016-11-29 23:02:22', '2016-12-04', '10:00:00', 2.5, 250, 'notes', 0, 0),
(5, 19, 1, NULL, 2, '2016-11-29 22:54:01', '2016-01-26', '10:00:00', 2.5, 250, 'notes', 0, 0),
(6, 6, 1, NULL, 2, '2016-11-29 22:54:14', '2016-11-29', '10:00:00', 2.5, 250, 'notes', 0, 1),
(7, 8, 1, NULL, 1, '2016-11-29 23:28:54', '2016-12-06', '16:00:00', 2.5, 250, 'notes', 0, 0),
(11, 19, 1, NULL, 1, '2016-12-01 03:00:57', '2016-12-04', '16:00:00', 2, 200, '0', 0, 0),
(12, 19, NULL, NULL, 1, '2016-12-07 02:33:41', '2016-12-05', '10:00:00', 2, 200, '0', 0, 0),
(13, 19, NULL, NULL, 1, '2016-12-07 02:33:47', '2016-12-06', '10:00:00', 2, 200, '0', 0, 0),
(14, 18, NULL, NULL, 1, '2016-12-07 02:33:56', '2016-12-06', '10:00:00', 2, 200, '0', 0, 0),
(15, 18, NULL, NULL, 1, '2016-12-07 02:35:39', '2016-12-06', '12:00:00', 2, 200, '0', 0, 0),
(16, 18, NULL, NULL, 1, '2016-12-07 02:36:22', '2016-12-06', '12:00:00', 2, 200, '0', 0, 0),
(17, 18, NULL, NULL, 1, '2016-12-07 02:36:31', '2016-12-06', '12:00:00', 2, 200, '0', 0, 0),
(18, 18, NULL, NULL, 1, '2016-12-07 02:40:28', '2016-12-06', '14:00:00', 2, 200, '0', 0, 0),
(19, 18, 7, NULL, 1, '2016-12-07 02:41:29', '2016-12-06', '20:00:00', 4, 400, '0', 0, 0),
(20, 18, 1, NULL, 1, '2016-12-07 04:05:29', '2016-12-06', '21:00:00', 2, 200, '0', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE IF NOT EXISTS `company` (
  `company_id` int(11) NOT NULL AUTO_INCREMENT,
  `en_name` varchar(45) DEFAULT NULL,
  `ar_name` varchar(40) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `en_address` varchar(45) NOT NULL,
  `ar_address` varchar(40) NOT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `area_id` int(11) DEFAULT NULL,
  `logo` text,
  `image` text,
  `en_description` varchar(45) NOT NULL,
  `ar_description` varchar(40) NOT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`company_id`),
  KEY `area_id_company_fk_idx` (`area_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`company_id`, `en_name`, `ar_name`, `phone`, `en_address`, `ar_address`, `longitude`, `latitude`, `area_id`, `logo`, `image`, `en_description`, `ar_description`, `deleted`) VALUES
(2, 'First Company', '', '0936110314', 'the company address', '', 55.277397, 25.199514, 1, 'a7a22-default_image.jpg', 'c6518-14677875_1144603905660654_845394342_o.png', '0', '', 0),
(3, 'Amallll', 'Amallll', '0936110314', 'address', 'address', 0, 0, 2, NULL, NULL, '0', '0', 1),
(4, 'second company', '', '523123212', 'address', '', 55.270557, 25.052221, 1, '53622-default_image.jpg', NULL, '0', '', 0),
(5, 'company', '', '523123212', 'address', '', 0, 0, 1, NULL, NULL, '0', '', 0),
(6, 'company', '', '523123212', 'address', '', 0, 0, 1, NULL, NULL, '0', '', 0),
(7, 'company', '', '523123212', 'address', '', 0, 0, 1, NULL, NULL, '0', '', 1),
(8, 'company', '', '523123212', 'address', '', 0, 0, 1, NULL, NULL, '0', '', 0),
(9, 'company new name', '', '0936110314', 'the company address', '', 0, 0, 1, 'Chart4.jpeg', 'Chart3.jpeg', '0', '', 0),
(10, 'Amallll', 'Amallll', '0936110314', 'address', 'address', 0, 0, 2, NULL, NULL, '0', '0', 1);

-- --------------------------------------------------------

--
-- Table structure for table `field`
--

CREATE TABLE IF NOT EXISTS `field` (
  `field_id` int(11) NOT NULL AUTO_INCREMENT,
  `en_name` varchar(45) DEFAULT NULL,
  `ar_name` varchar(40) NOT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `hour_rate` double DEFAULT NULL,
  `open_time` time DEFAULT NULL,
  `close_time` time DEFAULT NULL,
  `en_description` varchar(45) NOT NULL,
  `ar_description` varchar(40) NOT NULL,
  `area_x` double DEFAULT NULL,
  `area_y` double DEFAULT NULL,
  `max_capacity` int(11) DEFAULT NULL,
  `featured_place` tinyint(1) NOT NULL DEFAULT '0',
  `deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`field_id`),
  KEY `company_id_field_fk_idx` (`company_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=26 ;

--
-- Dumping data for table `field`
--

INSERT INTO `field` (`field_id`, `en_name`, `ar_name`, `phone`, `company_id`, `hour_rate`, `open_time`, `close_time`, `en_description`, `ar_description`, `area_x`, `area_y`, `max_capacity`, `featured_place`, `deleted`) VALUES
(1, 'company new name', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, NULL),
(2, 'company new name', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, NULL),
(3, 'company new name', '', '0936110314', 2, 100, '10:00:00', '03:00:00', '0', '', 10, 10, 20, 1, 0),
(4, 'company new name', '', '0936110314', 3, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 1, 1),
(5, 'company new name', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, 0),
(6, 'company new name', '', '0936110314', 3, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, 1),
(7, 'company new name', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, 0),
(8, 'company new name', '', '0936110314', 3, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 1, 1),
(9, 'company new name', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, 0),
(10, 'company new name', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 1, 0),
(11, 'company new name', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, 0),
(12, 'New Field', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, 0),
(13, 'New Field', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, 0),
(14, 'New Field', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, 0),
(15, 'New Field', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, 0),
(16, 'New Field', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, 0),
(17, 'New Field', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, 1),
(18, 'New Field', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, 0),
(19, 'fieldium', '', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '', 10, 10, 20, 0, 0),
(20, 'Amallll', 'Amallll', '0936110314', 3, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1),
(21, 'Amallll', 'Amallll', '0936110314', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1),
(22, NULL, '', NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, 0, 0),
(23, NULL, '', NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, 0, 0),
(24, 'new', '', NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, 0, 0),
(25, 'new', '', NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `field_amenity`
--

CREATE TABLE IF NOT EXISTS `field_amenity` (
  `field_amenity_id` int(11) NOT NULL AUTO_INCREMENT,
  `amenity_id` int(11) DEFAULT NULL,
  `field_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`field_amenity_id`),
  KEY `field_id_idx` (`field_id`),
  KEY `amenity_id_idx` (`amenity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

--
-- Dumping data for table `field_amenity`
--

INSERT INTO `field_amenity` (`field_amenity_id`, `amenity_id`, `field_id`) VALUES
(6, 2, 19),
(7, 1, 19),
(8, 2, 20),
(9, 1, 20),
(10, 2, 21),
(11, 1, 21),
(12, 3, 16),
(13, 1, 16);

-- --------------------------------------------------------

--
-- Table structure for table `field_game_type`
--

CREATE TABLE IF NOT EXISTS `field_game_type` (
  `field_game_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `field_id` int(11) DEFAULT NULL,
  `game_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`field_game_type_id`),
  KEY `field_game_type_id_fk_idx` (`game_type_id`),
  KEY `field_game_type_field_fk_idx` (`field_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `field_game_type`
--

INSERT INTO `field_game_type` (`field_game_type_id`, `field_id`, `game_type_id`) VALUES
(7, 19, 1),
(8, 18, 1),
(9, 20, 1),
(10, 21, 1),
(11, 16, 1);

-- --------------------------------------------------------

--
-- Table structure for table `game_type`
--

CREATE TABLE IF NOT EXISTS `game_type` (
  `game_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `en_name` varchar(45) NOT NULL,
  `ar_name` varchar(40) NOT NULL,
  `image` varchar(200) NOT NULL,
  PRIMARY KEY (`game_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `game_type`
--

INSERT INTO `game_type` (`game_type_id`, `en_name`, `ar_name`, `image`) VALUES
(1, 'soccer', '', 'd3eb1-amal-01.jpg'),
(2, '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE IF NOT EXISTS `image` (
  `image_id` int(11) NOT NULL AUTO_INCREMENT,
  `field_id` int(11) DEFAULT NULL,
  `name` text,
  PRIMARY KEY (`image_id`),
  KEY `field_id_image_fk_idx` (`field_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=26 ;

--
-- Dumping data for table `image`
--

INSERT INTO `image` (`image_id`, `field_id`, `name`) VALUES
(2, NULL, NULL),
(3, NULL, NULL),
(4, NULL, NULL),
(5, NULL, ''),
(7, NULL, ''),
(8, NULL, ''),
(9, NULL, ''),
(10, 17, 'd2079-14677875_1144603905660654_845394342_o.png'),
(11, 18, ''),
(13, 1, ''),
(14, 1, ''),
(18, NULL, NULL),
(19, NULL, NULL),
(20, NULL, NULL),
(21, NULL, NULL),
(22, 21, 'wzzzheader1.jpg'),
(23, NULL, 'wzzzheader2.jpg'),
(24, NULL, 'wzzzheader3.jpg'),
(25, 17, '7a070-14646851_1144603868993991_440846925_o.png');

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `content` text,
  PRIMARY KEY (`notification_id`),
  KEY `player_id_notification_fk_idx` (`player_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`notification_id`, `player_id`, `date`, `content`) VALUES
(1, 1, '2016-11-01 00:00:00', 'first notification'),
(2, 1, '2016-12-01 02:55:56', 'Your booking No.5 has been declined. '),
(3, 1, '2016-12-01 02:58:14', 'Your booking No.5 has been declined. '),
(4, 1, '2016-12-01 02:58:52', 'Your booking No.5 has been approved. ');

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE IF NOT EXISTS `player` (
  `player_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `profile_picture` text,
  `address` varchar(45) NOT NULL,
  `server_id` varchar(50) NOT NULL,
  `os` varchar(20) NOT NULL DEFAULT 'android',
  `token` varchar(300) NOT NULL,
  `verification_code` varchar(45) DEFAULT NULL,
  `active` int(1) DEFAULT '0',
  PRIMARY KEY (`player_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`player_id`, `name`, `phone`, `email`, `profile_picture`, `address`, `server_id`, `os`, `token`, `verification_code`, `active`) VALUES
(1, 'Amal', '0936110314', 'amal@gmail.com', NULL, '0', '975e068586f4ae77f1a63d70485fa216', 'android', '666666', '856366', 1),
(2, 'amalabd', '0936110314', 'amalabd@gmail.com', NULL, '', '', 'android', '123123', NULL, 0),
(3, NULL, '0936110310', NULL, NULL, '', '584406d93c3418.49014032', 'android', '', '139294', 1),
(4, NULL, '0936110312', NULL, NULL, '', '584407371c35e7.01918948', 'android', '', '555731', 1),
(5, NULL, '0936110315', NULL, NULL, '', '584407486735e5.07111099', 'android', '', '567920', 1),
(6, NULL, '0936110322', NULL, NULL, '', '', 'android', '', '752409', 1),
(7, NULL, '0936110323', NULL, NULL, '', '975e068586f4ae77f1a63d70485fa216', 'android', '123123', '238569', 1),
(8, NULL, '0936110324', NULL, NULL, '', 'e172744081f6416a6024b74abe40e45d', 'android', '', '608612', 0),
(9, NULL, '0936110545', NULL, NULL, '', '6d889514442c6ee3d1807b81485fa289', 'android', '', '221513', 0);

-- --------------------------------------------------------

--
-- Table structure for table `prefered_game`
--

CREATE TABLE IF NOT EXISTS `prefered_game` (
  `prefered_game_id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT NULL,
  `game_type_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`prefered_game_id`),
  KEY `player_id_game_fk_idx` (`player_id`),
  KEY `player_game_id_fk_idx` (`game_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=15 ;

--
-- Dumping data for table `prefered_game`
--

INSERT INTO `prefered_game` (`prefered_game_id`, `player_id`, `game_type_id`) VALUES
(1, NULL, 1),
(2, NULL, 1),
(14, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE IF NOT EXISTS `review` (
  `review_id` int(11) NOT NULL AUTO_INCREMENT,
  `field_id` int(11) DEFAULT NULL,
  `player_id` int(11) DEFAULT NULL,
  `text` text,
  `emoji` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`review_id`),
  KEY `field_id_review_fk_idx` (`field_id`),
  KEY `player_id_review_fk_idx` (`player_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `en_name` varchar(45) DEFAULT NULL,
  `ar_name` varchar(40) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `role`
--

INSERT INTO `role` (`role_id`, `en_name`, `ar_name`) VALUES
(1, 'admin', ''),
(2, 'support', ''),
(3, 'cashier', '');

-- --------------------------------------------------------

--
-- Table structure for table `search`
--

CREATE TABLE IF NOT EXISTS `search` (
  `search_record_id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT NULL,
  `token` varchar(300) NOT NULL,
  `area_id` int(11) DEFAULT NULL,
  `game_type_id` int(11) DEFAULT NULL,
  `start` time DEFAULT NULL,
  `date` date DEFAULT NULL,
  `duration` time DEFAULT NULL,
  `search_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `text` text,
  PRIMARY KEY (`search_record_id`),
  KEY `city_id_search_fk_idx` (`area_id`),
  KEY `game_type_id_search_fk_idx` (`game_type_id`),
  KEY `player_id_search_fk_idx` (`player_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=12 ;

--
-- Dumping data for table `search`
--

INSERT INTO `search` (`search_record_id`, `player_id`, `token`, `area_id`, `game_type_id`, `start`, `date`, `duration`, `search_date`, `text`) VALUES
(1, 7, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2, NULL, '123123', NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(3, NULL, '123123', 1, 1, '10:00:00', '2016-12-04', '00:00:01', '2016-12-07 02:20:41', 'Amal'),
(4, 7, '123123', 1, 1, '10:00:00', '2016-12-04', '00:00:01', '2016-12-07 02:21:03', 'Amal'),
(6, NULL, '123123', 1, 1, '00:00:00', '0000-00-00', '00:00:00', '2016-12-07 03:48:25', '0'),
(7, NULL, '123123', 1, 1, '13:00:00', '2016-12-06', '00:00:02', '2016-12-07 03:50:12', '0'),
(8, NULL, '123123', 1, 1, '13:00:00', '2016-12-06', '00:00:02', '2016-12-07 03:55:13', '0'),
(9, NULL, '123123', 1, 1, '24:00:00', '2016-12-06', '00:00:02', '2016-12-07 03:55:23', '0'),
(10, NULL, '123123', 1, 1, '24:00:00', '2016-12-06', '00:00:02', '2016-12-07 03:55:44', '0'),
(11, NULL, '123123', 1, 1, '24:00:00', '2016-12-06', '00:00:02', '2016-12-07 03:55:50', '0');

-- --------------------------------------------------------

--
-- Table structure for table `state`
--

CREATE TABLE IF NOT EXISTS `state` (
  `state_id` int(11) NOT NULL AUTO_INCREMENT,
  `en_name` varchar(45) DEFAULT NULL,
  `ar_name` varchar(40) NOT NULL,
  PRIMARY KEY (`state_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `state`
--

INSERT INTO `state` (`state_id`, `en_name`, `ar_name`) VALUES
(1, 'pending', ''),
(2, 'approved', ''),
(3, 'declined', '');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `username` varchar(40) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `profile_picture` varchar(45) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`user_id`),
  KEY `role_id_user_fk_idx` (`role_id`),
  KEY `comapny_id_user_fk_idx` (`company_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `name`, `username`, `password`, `phone`, `email`, `profile_picture`, `company_id`, `role_id`, `active`) VALUES
(1, 'Amal', 'amal', '1a100d2c0dab19c4430e7d73762b3423', '0936110314', 'amal@gmail.com', NULL, 3, 1, 1),
(2, 'new name', 'amalabd', '1a100d2c0dab19c4430e7d73762b3423', '09361103155', 'amal@gmail.com', NULL, 3, 2, 1),
(3, 'fieldium', 'basma', '1a100d2c0dab19c4430e7d73762b3423', '0936110314', '0', NULL, 2, 1, 0),
(4, 'fieldium', 'basma2', '1a100d2c0dab19c4430e7d73762b3423', '0936110314', '0', NULL, 2, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `website_messages`
--

CREATE TABLE IF NOT EXISTS `website_messages` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `subject` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  `text` text NOT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `website_messages`
--

INSERT INTO `website_messages` (`message_id`, `name`, `subject`, `email`, `text`) VALUES
(1, 'Amallll', 'new message', 'amal@gmail.com', 'hey you !!!'),
(2, 'Amallll', 'new message', 'amal@gmail.com', 'hey you !!!');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `field_id_booking_fk` FOREIGN KEY (`field_id`) REFERENCES `field` (`field_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `player_id_booking_fk` FOREIGN KEY (`player_id`) REFERENCES `player` (`player_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `state_id_booking_fk` FOREIGN KEY (`state_id`) REFERENCES `state` (`state_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `user_id_booking_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `company`
--
ALTER TABLE `company`
  ADD CONSTRAINT `area_id_company_fk` FOREIGN KEY (`area_id`) REFERENCES `area` (`area_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `field`
--
ALTER TABLE `field`
  ADD CONSTRAINT `company_id_field_fk` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `field_amenity`
--
ALTER TABLE `field_amenity`
  ADD CONSTRAINT `amenity_id_fk` FOREIGN KEY (`amenity_id`) REFERENCES `amenity` (`amenity_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `field_id_fk` FOREIGN KEY (`field_id`) REFERENCES `field` (`field_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `field_game_type`
--
ALTER TABLE `field_game_type`
  ADD CONSTRAINT `field_game_type_field_fk` FOREIGN KEY (`field_id`) REFERENCES `field` (`field_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `field_game_type_game_fk` FOREIGN KEY (`game_type_id`) REFERENCES `game_type` (`game_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `image`
--
ALTER TABLE `image`
  ADD CONSTRAINT `field_id_image_fk` FOREIGN KEY (`field_id`) REFERENCES `field` (`field_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `player_id_notification_fk` FOREIGN KEY (`player_id`) REFERENCES `player` (`player_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `prefered_game`
--
ALTER TABLE `prefered_game`
  ADD CONSTRAINT `player_game_id_fk` FOREIGN KEY (`game_type_id`) REFERENCES `game_type` (`game_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `player_id_game_fk` FOREIGN KEY (`player_id`) REFERENCES `player` (`player_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `review`
--
ALTER TABLE `review`
  ADD CONSTRAINT `field_id_review_fk` FOREIGN KEY (`field_id`) REFERENCES `field` (`field_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `player_id_review_fk` FOREIGN KEY (`player_id`) REFERENCES `player` (`player_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `search`
--
ALTER TABLE `search`
  ADD CONSTRAINT `area_id_search_fk` FOREIGN KEY (`area_id`) REFERENCES `area` (`area_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `game_type_id_search_fk` FOREIGN KEY (`game_type_id`) REFERENCES `game_type` (`game_type_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `player_id_search_fk` FOREIGN KEY (`player_id`) REFERENCES `player` (`player_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `comapny_id_user_fk` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `role_id_user_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
