-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 31, 2017 at 07:25 AM
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
  `ar_name` varchar(40) DEFAULT NULL,
  `image` text,
  PRIMARY KEY (`amenity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `amenity`
--

INSERT INTO `amenity` (`amenity_id`, `en_name`, `ar_name`, `image`) VALUES
(1, 'shower', NULL, NULL),
(2, 'shower', NULL, NULL),
(3, 'lights', NULL, NULL),
(4, 'parking', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `area`
--

CREATE TABLE IF NOT EXISTS `area` (
  `area_id` int(11) NOT NULL AUTO_INCREMENT,
  `en_name` varchar(45) DEFAULT NULL,
  `ar_name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `area`
--

INSERT INTO `area` (`area_id`, `en_name`, `ar_name`) VALUES
(1, 'Dubai', ''),
(2, 'Jumerieh', NULL);

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
  `notes` text,
  `manually` int(11) DEFAULT '0',
  `deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`booking_id`),
  KEY `field_id_booking_fk_idx` (`field_id`),
  KEY `state_id_booking_fk_idx` (`state_id`),
  KEY `player_id_booking_fk_idx` (`player_id`),
  KEY `user_id_booking_fk_idx` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=104 ;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`booking_id`, `field_id`, `player_id`, `user_id`, `state_id`, `creation_date`, `date`, `start`, `duration`, `notes`, `manually`, `deleted`) VALUES
(1, 1, 1, NULL, 2, '2016-12-14 15:53:07', '2016-12-14', '08:00:00', 2, '0', 0, 1),
(2, 1, 1, NULL, 2, '2016-12-14 15:53:21', '2016-12-14', '12:00:00', 1, '0', 0, 1),
(3, 1, 1, NULL, 3, '2016-12-15 09:47:24', '2016-12-14', '16:00:00', 1, '0', 0, 1),
(4, 1, 1, NULL, 3, '2016-12-15 09:57:48', '2016-12-15', '16:00:00', 1, '0', 0, 1),
(5, 1, 1, NULL, 2, '2016-12-15 09:58:04', '2016-12-15', '10:00:00', 1, '0', 0, 1),
(6, 1, 2, 1, 2, '2016-12-15 14:09:42', '2017-11-11', '10:00:00', 1, '0', 1, 1),
(7, 1, 2, 1, 2, '2016-12-15 14:09:57', '2017-11-11', '12:00:00', 1, '0', 1, 1),
(8, 1, 3, 1, 2, '2016-12-15 14:10:06', '2017-11-11', '17:00:00', 1, '0', 1, 1),
(9, 1, 3, 1, 3, '2016-12-15 14:57:15', '2016-11-29', '17:00:00', 1, '0', 1, 1),
(10, 1, 3, 1, 3, '2016-12-15 14:57:29', '2016-11-29', '11:00:00', 3, '0', 1, 1),
(11, 1, 5, 1, 1, '2016-12-15 15:15:34', '2016-12-15', '10:00:00', -1, '', 1, 1),
(12, 1, 5, 1, 3, '2016-12-15 15:15:43', '2016-12-15', '10:00:00', -1, '', 1, 1),
(13, 1, 3, 1, 1, '2016-12-15 15:27:21', '2016-12-06', '10:00:00', 3, '0', 1, 1),
(14, 1, 3, 1, 3, '2016-12-15 15:28:22', '2016-11-29', '07:00:00', 3, '0', 1, 1),
(15, 1, 3, 1, 3, '2016-12-15 15:29:42', '2016-11-29', '10:00:00', 1, '0', 1, 1),
(16, 1, 3, 1, 2, '2016-12-15 15:29:50', '2016-11-30', '10:00:00', 1, '0', 1, 1),
(17, 1, 3, 1, 1, '2016-12-15 15:30:00', '2016-11-30', '11:00:00', 1, '0', 1, 1),
(18, 1, 3, 1, 1, '2016-12-15 15:30:17', '2016-11-30', '12:00:00', 4, '0', 1, 1),
(19, 1, 5, 1, 1, '2016-12-15 15:32:01', '2016-12-15', '10:00:00', -1, '', 1, 1),
(20, 1, 5, 1, 1, '2016-12-15 15:32:11', '2016-12-15', '10:00:00', -1, '', 1, 1),
(21, 1, 6, 1, 1, '2016-12-15 15:37:35', '2016-12-15', '11:00:00', 2, '', 1, 1),
(22, 1, 9, 1, 1, '2016-12-15 16:24:44', '2016-12-16', '10:00:00', 3, '', 1, 1),
(23, 1, 11, 1, 1, '2016-12-15 16:26:45', '2016-12-16', '09:00:00', 1, '0', 1, 1),
(24, 1, 12, 1, 1, '2016-12-15 16:30:58', '2016-12-16', '08:00:00', 1, '0', 1, 1),
(25, 1, 12, 1, 1, '2016-12-15 16:31:43', '2016-12-16', '07:00:00', 1, '0', 1, 1),
(26, 1, 12, 1, 1, '2016-12-15 16:42:18', '2016-12-16', '06:00:00', 1, '0', 1, 1),
(28, 1, 12, 1, 1, '2016-12-15 16:56:05', '2016-12-16', '615:00:00', 1, '0', 1, 1),
(29, 1, 12, 1, 1, '2016-12-15 16:56:22', '2016-12-16', '15:00:00', 1, '0', 1, 1),
(30, 1, 14, 1, 1, '2016-12-15 16:57:54', '2016-12-16', '04:00:00', 1, '', 1, 1),
(31, 1, 12, 1, 1, '2016-12-15 17:00:14', '2016-12-16', '15:00:00', 1, '0', 1, 1),
(32, 1, 12, 1, 1, '2016-12-15 17:00:36', '2016-12-16', '15:00:00', 1, '0', 1, 1),
(33, 1, 12, 1, 1, '2016-12-15 17:00:41', '2016-12-16', '15:00:00', 1, '0', 1, 1),
(34, 1, 12, 1, 1, '2016-12-15 17:00:47', '2016-12-16', '15:00:00', 1, '0', 1, 1),
(35, 1, 12, 1, 2, '2016-12-15 17:01:19', '2016-12-16', '15:00:00', 1, '0', 1, 1),
(36, 1, NULL, NULL, 1, '2016-12-15 17:01:27', '2016-12-16', '15:00:00', 1, '0', 0, 1),
(37, 1, 15, 1, 2, '2016-12-15 17:01:42', '2016-12-16', '05:00:00', 1, '', 1, 1),
(38, 1, NULL, NULL, 1, '2016-12-15 17:02:32', '2016-12-16', '16:00:00', 1, '0', 0, 1),
(39, 1, 16, 1, 2, '2016-12-15 17:02:48', '2016-12-15', '05:00:00', 1, '', 1, 1),
(40, 1, 17, 1, 2, '2016-12-15 17:03:21', '2016-12-16', '10:00:00', 1, '', 1, 1),
(41, 1, NULL, NULL, 1, '2016-12-15 17:03:21', '2016-12-16', '16:00:00', 1, '0', 0, 1),
(42, 1, NULL, NULL, 1, '2016-12-15 17:03:55', '2016-12-16', '16:00:00', 1, '0', 0, 1),
(43, 1, 12, 1, 2, '2016-12-15 17:04:41', '2016-12-16', '16:00:00', 1, '0', 1, 1),
(44, 1, 12, 1, 2, '2016-12-15 17:04:44', '2016-12-16', '16:00:00', 1, '0', 1, 1),
(45, 1, 18, 1, 2, '2016-12-17 21:13:04', '2016-12-18', '10:00:00', 3, '', 1, 1),
(46, 1, 56, 1, 2, '2016-12-22 12:01:35', '2016-12-22', '10:00:00', 1, '', 1, 1),
(47, 2, 56, 1, 2, '2016-12-22 12:01:59', '2016-12-22', '10:00:00', 1, '', 1, 1),
(48, 3, 56, 1, 2, '2016-12-22 12:02:26', '2016-12-22', '10:00:00', 1, '', 1, 1),
(49, 2, 57, 1, 2, '2016-12-22 12:05:50', '2016-12-22', '12:00:00', 1, '', 1, 1),
(50, 4, 58, 1, 3, '2016-12-22 13:52:08', '2016-12-22', '10:00:00', 1, 'today will pay', 1, 1),
(51, 5, 59, 1, 2, '2016-12-22 13:54:01', '2016-12-22', '10:00:00', 1, '', 1, 1),
(52, 1, 66, 1, 2, '2016-12-29 14:42:37', '2016-12-29', '10:00:00', 1, '', 1, 1),
(53, 2, 67, 1, 2, '2016-12-29 14:43:19', '2016-12-29', '10:00:00', 3, '', 1, 0),
(54, 4, 12, 1, 2, '2016-12-29 14:51:44', '2017-12-26', '10:00:00', 2, '0', 1, 0),
(55, 4, 12, 1, 2, '2016-12-29 14:52:25', '2016-12-29', '08:00:00', 3, '0', 1, 0),
(56, 4, 12, 1, 2, '2016-12-29 14:56:10', '2016-12-29', '06:00:00', 1, '0', 1, 0),
(57, 5, 1, NULL, 2, '2016-12-29 14:56:54', '2016-12-29', '08:00:00', 1, '0', 0, 0),
(58, 5, 1, NULL, 2, '2016-12-29 14:57:26', '2016-12-29', '13:00:00', 1, '0', 0, 0),
(59, 3, 1, NULL, 3, '2016-12-29 14:57:34', '2016-12-30', '13:00:00', 1, '0', 0, 0),
(60, 3, 1, NULL, 3, '2016-12-29 15:11:16', '2016-12-29', '14:00:00', 1, '0', 0, 0),
(61, 1, 70, 1, 2, '2016-12-29 15:13:53', '2016-12-30', '12:00:00', 2, 'what ever you want', 1, 0),
(62, 1, 71, 1, 2, '2016-12-29 16:05:48', '2016-12-30', '10:00:00', 1, '', 1, 0),
(63, 3, 72, 1, 2, '2016-12-29 16:07:48', '2016-12-29', '10:00:00', 1, '', 1, 0),
(64, 1, 73, 1, 2, '2016-12-29 16:09:21', '2016-12-31', '10:00:00', 1, '', 1, 0),
(65, 1, 1, NULL, 2, '2017-01-02 15:00:06', '2017-01-01', '14:00:00', 1, '0', 0, 0),
(66, 1, 74, 1, 2, '2017-01-02 15:03:47', '2017-01-02', '14:00:00', 1, '', 1, 0),
(67, 3, 12, 1, 2, '2017-01-02 15:30:34', '2017-01-02', '14:00:00', 1, '0', 1, 0),
(68, 1, 75, 1, 2, '2017-01-02 15:30:49', '2017-01-02', '11:00:00', 2, '', 1, 0),
(69, 3, 12, 1, 2, '2017-01-02 15:31:37', '2017-01-02', '08:00:00', 1, '0', 1, 0),
(70, 1, 76, 1, 2, '2017-01-02 15:34:42', '2017-01-02', '07:00:00', 2, '', 1, 0),
(71, 1, 12, 1, 2, '2017-01-02 15:35:02', '2017-01-02', '08:00:00', 1, '0', 1, 0),
(72, 1, 77, 1, 2, '2017-01-02 15:37:31', '2017-01-02', '06:00:00', 1, '', 1, 0),
(73, 1, 12, 1, 2, '2017-01-07 23:13:45', '2017-01-06', '07:00:00', 1, '0', 1, 0),
(74, 1, 12, 1, 2, '2017-01-07 23:14:11', '2017-01-08', '07:00:00', 1, '0', 1, 0),
(75, 1, 12, 1, 2, '2017-01-07 23:14:38', '2017-01-09', '07:00:00', 1, '0', 1, 0),
(76, 1, 12, 1, 2, '2017-01-07 23:15:16', '2017-01-10', '07:00:00', 1, '0', 1, 0),
(77, 1, 12, 1, 2, '2017-01-07 23:16:00', '2017-01-11', '07:00:00', 1, '0', 1, 0),
(78, 15, 1, NULL, 2, '2017-01-11 01:01:05', '2017-01-20', '01:00:00', 1, '0', 0, 0),
(79, 15, 1, NULL, 2, '2017-01-11 01:01:27', '2017-01-21', '01:00:00', 1, '0', 0, 0),
(80, 15, 1, NULL, 2, '2017-01-11 01:01:37', '2017-01-22', '01:00:00', 1, '0', 0, 0),
(81, 15, 1, NULL, 2, '2017-01-11 04:47:28', '2017-01-20', '05:00:00', 1, '0', 0, 0),
(82, 15, 1, NULL, 2, '2017-01-11 04:47:42', '2017-01-20', '00:00:00', 1, '0', 0, 0),
(83, 1, 1, NULL, 1, '2017-01-23 01:04:23', '2017-03-05', '23:00:00', 3, '0', 0, 0),
(92, 1, 1, NULL, 1, '2017-01-23 01:08:53', '2017-01-23', '15:00:00', 3, '0', 0, 0),
(94, 1, 1, NULL, 1, '2017-01-23 03:43:29', '2017-01-23', '12:00:00', 3, '0', 0, 0),
(95, 1, 1, NULL, 1, '2017-01-23 03:44:04', '2017-01-24', '12:00:00', 2, '0', 0, 1),
(96, 1, 1, NULL, 1, '2017-01-23 03:44:24', '2017-01-24', '11:00:00', 1, '0', 0, 1),
(97, 1, 1, NULL, 1, '2017-01-23 04:28:45', '2017-01-24', '00:00:00', 1, '0', 0, 0),
(98, 1, 1, NULL, 1, '2017-01-24 22:44:40', '2017-03-23', '01:00:00', 1, '0', 0, 0),
(99, 1, 1, NULL, 1, '2017-01-24 22:45:20', '2017-03-24', '01:00:00', 1, '0', 0, 0),
(100, 1, 1, NULL, 1, '2017-01-24 22:55:22', '2017-03-25', '01:00:00', 1, '0', 0, 0),
(101, 1, 1, NULL, 1, '2017-01-24 22:55:41', '2017-03-26', '01:00:00', 1, '0', 0, 0),
(102, 1, 1, NULL, 1, '2017-01-24 22:56:42', '2017-03-27', '01:00:00', 1, '0', 0, 0),
(103, 1, 1, NULL, 1, '2017-01-24 22:57:23', '2017-03-28', '01:00:00', 1, '0', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE IF NOT EXISTS `company` (
  `company_id` int(11) NOT NULL AUTO_INCREMENT,
  `en_name` varchar(45) DEFAULT NULL,
  `ar_name` varchar(40) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `en_address` varchar(300) NOT NULL,
  `ar_address` varchar(40) DEFAULT NULL,
  `longitude` double DEFAULT '55.299678',
  `latitude` double DEFAULT '25.249724',
  `area_id` int(11) DEFAULT NULL,
  `logo` text,
  `image` text,
  `en_description` text NOT NULL,
  `ar_description` varchar(40) DEFAULT NULL,
  `deleted` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`company_id`),
  KEY `area_id_company_fk_idx` (`area_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`company_id`, `en_name`, `ar_name`, `phone`, `en_address`, `ar_address`, `longitude`, `latitude`, `area_id`, `logo`, `image`, `en_description`, `ar_description`, `deleted`) VALUES
(1, 'IFA', '', '8768867', 'Dubai', '', 55.1758914, 25.0981826, 1, '', '', '', '', 1),
(2, ' IFA Sports Central', '', '+ 971 4 454 1683 ', 'Sheikh Zayed Road', '', 55.1758914, 25.0981826, 1, '2543f-sports-central.png', 'b37bc-ifa.jpg', 'IFA Sports Central is located in the Emirates English Speaking School (EESS) in the heart of Jumeirah near Safa Park just off Sheikh Zayed Road. Comprising of three 7 a side pitches or one full size pitch. Available 5pm to 10pm Sunday to Thursday and from 8am to 10pm Friday and Saturdays.', '', 0),
(3, 'IFA Meadows Football Centre', '', '+ 971 4 454 1683 ', 'Sheikh Zayed Road', '', 55.155359, 25.066424, 1, '9a0a0-meadows.png', '91907-maxresdefault.jpg', 'IFA Meadows Football Centre is located in the Emirates International School - The Meadows. Comprising of two 7 a side pitches. Available 5pm to 10pm Sunday to Thursday and from 8am to 10pm Friday and Saturdays.', '', 0),
(4, 'Ahdaf', NULL, '+971 800 243223', 'cairo street', NULL, 55.344932, 25.300782, 2, 'd1008-ahdaaf_logo.png', '9877b-about-us_bg.jpg', 'Formed in 2008 through Dubai SME. Ahdaaf Sports Club aims to promote a healthy lifestyle and bridge the gap between different communities through sports.', NULL, 0),
(5, 'Ahdaf', NULL, '+971 800 243223', 'cairo street', NULL, 55.299678, 25.249724, 1, 'd1008-ahdaaf_logo.png', '9877b-about-us_bg.jpg', 'Formed in 2008 through Dubai SME. Ahdaaf Sports Club aims to promote a healthy lifestyle and bridge the gap between different communities through sports.', NULL, 1),
(6, 'new', NULL, '2323423432432', 'fdsfdsf', NULL, 55.299678, 25.249724, 1, NULL, NULL, '', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `field`
--

CREATE TABLE IF NOT EXISTS `field` (
  `field_id` int(11) NOT NULL AUTO_INCREMENT,
  `en_name` varchar(45) DEFAULT NULL,
  `ar_name` varchar(40) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `hour_rate` double DEFAULT NULL,
  `open_time` time DEFAULT NULL,
  `close_time` time DEFAULT NULL,
  `en_description` varchar(45) NOT NULL,
  `ar_description` varchar(40) DEFAULT NULL,
  `area_x` double DEFAULT NULL,
  `area_y` double DEFAULT NULL,
  `max_capacity` int(11) DEFAULT NULL,
  `featured_place` tinyint(1) NOT NULL DEFAULT '0',
  `auto_confirm` tinyint(1) NOT NULL DEFAULT '0',
  `deleted` int(1) DEFAULT '0',
  PRIMARY KEY (`field_id`),
  KEY `company_id_field_fk_idx` (`company_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=43 ;

--
-- Dumping data for table `field`
--

INSERT INTO `field` (`field_id`, `en_name`, `ar_name`, `phone`, `company_id`, `hour_rate`, `open_time`, `close_time`, `en_description`, `ar_description`, `area_x`, `area_y`, `max_capacity`, `featured_place`, `auto_confirm`, `deleted`) VALUES
(1, 'Ahdaff', 'Ahdaff', '0936547895', 2, 100, '10:00:00', '02:00:00', '', '', 30, 20, 20, 1, 0, 0),
(2, 'Ahdaf 2', 'Field 2', '0980980980', 4, 100, '03:00:00', '15:00:00', '', '', 11, 15, 10, 0, 0, 1),
(3, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 1, 1, 1),
(4, 'Succer and Tennis', NULL, '0958202814', 3, 50, '06:00:00', '22:00:00', '', NULL, 100, 200, 10, 0, 0, 1),
(5, 'Main Field', NULL, '0958282814', 4, 20, '07:00:00', '20:00:00', '', NULL, 100, 100, 5, 0, 0, 1),
(6, 'Field 6', NULL, '123456988', 3, 85, '05:00:00', '23:00:00', '', NULL, 200, 400, 22, 0, 0, 1),
(7, 'ggggg', NULL, '433543', 5, 45, '02:00:00', '14:00:00', '', NULL, 5, 4, 4, 0, 0, 1),
(8, 'hhhh', NULL, '45345345', 5, 4, '06:00:00', '21:00:00', '', NULL, 4, 4, 4, 0, 0, 1),
(9, 'rrr', NULL, '432', 6, 4, '07:00:00', '20:00:00', '', NULL, 4, 4, 4, 0, 0, 1),
(10, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 0, 1),
(11, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 0, 1),
(12, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 0, 1),
(13, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1, 1),
(14, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 0, 1),
(15, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1, 1),
(16, 'new', NULL, '233231', 2, 2, '04:00:00', '18:00:00', '', NULL, 2, 2, 2, 0, 0, 1),
(17, 'new', NULL, '233231', 2, 2, '04:00:00', '18:00:00', '', NULL, 2, 2, 2, 0, 0, 1),
(18, 'new', NULL, '233231', 2, 2, '04:00:00', '18:00:00', '', NULL, 2, 2, 2, 0, 0, 1),
(19, 'd', NULL, '2312', 2, 2, '04:00:00', '14:00:00', '', NULL, 2, 2, 2, 0, 0, 1),
(20, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(21, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(22, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(23, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(24, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(25, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(26, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(27, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(28, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(29, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(30, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(31, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(32, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(33, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(34, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(35, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(36, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(37, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(38, '444', NULL, '34234', 2, 2, '04:00:00', '15:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(39, 'sad', NULL, '32', 2, 2, '03:00:00', '13:00:00', '', NULL, 2, 2, 2, 0, 0, 1),
(40, '2', NULL, '32', 2, 2, '05:00:00', '14:00:00', '', NULL, 2, 2, 2, 0, 0, 1),
(41, 'd', NULL, '342', 2, 3, '02:00:00', '17:00:00', '', NULL, 4, 4, 4, 0, 0, 1),
(42, 'er', NULL, '3432', 2, 3, '03:00:00', '13:00:00', '', NULL, 3, 3, 3, 0, 0, 1);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `field_amenity`
--

INSERT INTO `field_amenity` (`field_amenity_id`, `amenity_id`, `field_id`) VALUES
(1, 3, 1),
(2, 4, 1),
(3, 1, 1);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=48 ;

--
-- Dumping data for table `field_game_type`
--

INSERT INTO `field_game_type` (`field_game_type_id`, `field_id`, `game_type_id`) VALUES
(7, 2, 1),
(8, 4, 1),
(9, 4, 2),
(10, 5, 1),
(11, 6, 1),
(12, 1, 1),
(13, 2, 2),
(14, 7, 1),
(15, 8, 2),
(16, 9, 2),
(21, 16, 2),
(22, 17, 2),
(23, 18, 2),
(24, 19, 1),
(25, 20, 1),
(26, 21, 1),
(27, 22, 1),
(28, 23, 1),
(29, 24, 1),
(30, 25, 1),
(31, 26, 1),
(32, 27, 1),
(33, 28, 1),
(34, 29, 1),
(35, 30, 1),
(36, 31, 1),
(37, 32, 1),
(38, 33, 1),
(39, 34, 1),
(40, 35, 1),
(41, 36, 1),
(42, 37, 1),
(43, 38, 1),
(44, 39, 1),
(45, 40, 1),
(46, 41, 2),
(47, 42, 2);

-- --------------------------------------------------------

--
-- Table structure for table `game_type`
--

CREATE TABLE IF NOT EXISTS `game_type` (
  `game_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `en_name` varchar(45) NOT NULL,
  `ar_name` varchar(40) DEFAULT NULL,
  `image` varchar(200) NOT NULL,
  `en_description` text NOT NULL,
  `ar_description` text,
  PRIMARY KEY (`game_type_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `game_type`
--

INSERT INTO `game_type` (`game_type_id`, `en_name`, `ar_name`, `image`, `en_description`, `ar_description`) VALUES
(1, 'soccer', '', '8b3ad-soccer.png', '', ''),
(2, 'tennis', NULL, 'f3f8a-tennis.png', '', NULL);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=28 ;

--
-- Dumping data for table `image`
--

INSERT INTO `image` (`image_id`, `field_id`, `name`) VALUES
(1, 1, 'cafaa-field1.jpg'),
(2, NULL, NULL),
(3, NULL, NULL),
(4, NULL, 'close.png'),
(5, NULL, 'close1.png'),
(6, NULL, NULL),
(7, NULL, '2.PNG'),
(8, NULL, '21.PNG'),
(9, NULL, '13697201_275778369447677_7677883900742054208_n.jpg'),
(10, NULL, NULL),
(11, NULL, '0_image2.jpg'),
(12, 1, 'e1941-field2.jpg'),
(13, NULL, '76e85-field3.jpg'),
(14, NULL, '7b45d-field4.jpg'),
(15, NULL, '0_image3.jpg'),
(16, NULL, '0_image4.jpg'),
(17, NULL, '0_image5.jpg'),
(18, NULL, '1_image.jpg'),
(19, NULL, '1_image1.jpg'),
(20, NULL, '2_image.jpg'),
(21, NULL, '2_image1.jpg'),
(22, 3, '2_image2.jpg'),
(23, 2, '0_image6.jpg'),
(24, 2, '1_image2.jpg'),
(25, NULL, '2_image3.jpg'),
(26, 4, 'a82f3-field1.jpg'),
(27, NULL, 'ui-icons_2e83ff_256x2402.png');

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE IF NOT EXISTS `notification` (
  `notification_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) NOT NULL,
  `player_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `content` text,
  PRIMARY KEY (`notification_id`),
  KEY `player_id_notification_fk_idx` (`player_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`notification_id`, `booking_id`, `player_id`, `date`, `content`) VALUES
(1, 2, 66, '2017-01-07 22:37:37', 'Your booking No.52 has been approved. '),
(2, 2, 66, '2017-01-07 22:37:57', 'Your booking No.52 has been approved. '),
(3, 52, 66, '2017-01-07 22:40:50', 'Your booking No.52 has been approved. '),
(4, 52, 66, '2017-01-07 22:41:48', 'Your booking No.52 has been approved. '),
(5, 65, 1, '2017-01-07 22:44:35', 'Your booking No.65 has been approved. '),
(6, 59, 1, '2017-01-07 22:59:43', 'Your booking No.59 has been declined. '),
(7, 77, 1, '2017-01-07 23:16:01', 'You have received a new booking No.77'),
(8, 79, 1, '2017-01-23 01:57:32', 'Your booking No.79 has been approved. '),
(9, 79, 1, '2017-01-23 01:57:39', 'Your booking No.79 has been approved. '),
(10, 79, 1, '2017-01-23 01:57:47', 'Your booking No.79 has been approved. '),
(11, 76, 12, '2017-01-23 01:59:45', 'Your booking No.76 has been approved. '),
(12, 76, 12, '2017-01-23 02:00:32', 'Your booking No.76 has been approved. ');

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
  `address` varchar(45) DEFAULT NULL,
  `server_id` varchar(50) NOT NULL,
  `os` varchar(20) NOT NULL DEFAULT 'android',
  `token` varchar(300) NOT NULL,
  `verification_code` varchar(45) DEFAULT NULL,
  `active` int(1) DEFAULT '0',
  PRIMARY KEY (`player_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=78 ;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`player_id`, `name`, `phone`, `email`, `profile_picture`, `address`, `server_id`, `os`, `token`, `verification_code`, `active`) VALUES
(1, 'fieldium', '0936110314', '0', '', '0', '123123', 'android', '', '123123', 1),
(2, 'amal', '0321456987', NULL, NULL, '', '', 'android', '', NULL, 0),
(3, 'amool', '03214569187', NULL, NULL, '', '', 'android', '', NULL, 0),
(4, 'amalabd', '0987898789', NULL, NULL, '', '', 'android', '', NULL, 0),
(5, 'fff', '85757', NULL, NULL, '', '', 'android', '', NULL, 0),
(6, 'rabii', '85563', NULL, NULL, '', '', 'android', '', NULL, 0),
(7, 'rabii test fail', '55825', NULL, NULL, '', '', 'android', '', NULL, 0),
(8, 'rabii', '52533', NULL, NULL, '', '', 'android', '', NULL, 0),
(9, 'rabii', '555555', NULL, NULL, '', '', 'android', '', NULL, 0),
(10, 'rabii', '535222', NULL, NULL, '', '', 'android', '', NULL, 0),
(11, 'amool', '032187', NULL, NULL, '', '', 'android', '', NULL, 0),
(12, 'amool', '032165587', NULL, NULL, '', '', 'android', '', NULL, 0),
(13, 'rabii', '24556', NULL, NULL, '', '', 'android', '', NULL, 0),
(14, 'rabii', '4755', NULL, NULL, '', '', 'android', '', NULL, 0),
(15, 'tt', '56565', NULL, NULL, '', '', 'android', '', NULL, 0),
(16, 'ttt', '2552', NULL, NULL, '', '', 'android', '', NULL, 0),
(17, 'fff', '555', NULL, NULL, '', '41e7a2e44794223a50a294b61f3502e1', 'ios', '', '062740', 0),
(18, 'rabii', '095282141', NULL, NULL, '', '', 'android', '', NULL, 0),
(19, 'amal', '0936110314', NULL, NULL, NULL, '8fa9de62f976fcda3517d08bbae73923', 'ios', '', '523614', 0),
(20, 'yy', '994729458', NULL, NULL, NULL, 'e4331e082a057a20d48d9067f0596615', 'ios', '', '351949', 1),
(21, 'hbb', '88', NULL, NULL, NULL, '1dd362614a09edc0812d555fdca3855c', 'ios', '', '735466', 0),
(22, NULL, 'd', NULL, NULL, NULL, '44449dd03c895899d6fc3f3bd72208d0', 'ios', '', '709278', 0),
(23, 'fggg', '333', NULL, NULL, NULL, 'b48ae20fdb786b8c43c40c14a3ed0ced', 'ios', '', '459657', 0),
(24, NULL, '00987654321', NULL, NULL, NULL, '2cd86c0cbb5a18720d545e2c870903cc', 'android', '', '796626', 0),
(25, NULL, '123', NULL, NULL, NULL, 'fa30460f21446ff44021aa5ba0e852bf', 'android', '', '359868', 0),
(26, 'basma', '934610552', NULL, NULL, NULL, '135dbe784853e1f342e9a66048707886', 'android', '', '317922', 1),
(27, NULL, 'ffgg', NULL, NULL, NULL, 'a8ef49fe40aa94c77db83e6bd134d437', 'ios', '', '106374', 0),
(28, NULL, 'vhn', NULL, NULL, NULL, '643fcef3378704437447f44dd1821259', 'ios', '', '536593', 0),
(29, NULL, '45', NULL, NULL, NULL, '098f03be8c49015881b16ac3ebcd0829', 'android', '', '389833', 0),
(30, NULL, 'bbn', NULL, NULL, NULL, '562ccc8719f28690dc5bb23dd5c63adf', 'ios', '', '560266', 0),
(31, NULL, 'hhn', NULL, NULL, NULL, '4ef361292f4fc12a4e9961b2eadece3c', 'ios', '', '558500', 0),
(32, NULL, '342342', NULL, NULL, NULL, '8b99e9ea76cd9d8728bdd272267c7be7', 'ios', '', '138308', 0),
(33, NULL, 'hb', NULL, NULL, NULL, 'a706a00d4fef2688c49248f25a1f4b24', 'ios', '', '368514', 0),
(34, NULL, '222228865', NULL, NULL, NULL, '1a98c47dac5469ba6daf080ba51c82a5', 'android', '', '115132', 0),
(35, NULL, '09852', NULL, NULL, NULL, '1fc5825f2b4c39e1c124906641ed8aee', 'android', '', '320297', 0),
(36, 'amallll', '2342342344', NULL, NULL, NULL, '65bcbd29dc2777c52d7916ed135e6983', 'ios', '', '521445', 0),
(37, 'ggg', 'ggg', NULL, NULL, NULL, 'd8f72fcb68abe8f91c2ae6219b6fe8c9', 'ios', '', '530436', 0),
(38, 'chcbcn', 'chcbcn', NULL, NULL, NULL, 'b31fc6f1caa0f0ed736af7cab4a39d2c', 'ios', '', '487434', 0),
(39, 'vvv', 'vvv', NULL, NULL, NULL, '4f0bb9b6e506e2014b5df6da5cf51847', 'ios', '', '956573', 0),
(40, 'uuu', '222', NULL, NULL, NULL, '7ac6dd04ba17b38ca8dac83530d450df', 'ios', '', '564138', 1),
(41, 'hhgf', '225', NULL, NULL, NULL, '2221f5da8fbaaab27d02dda6ea5b32c0', 'ios', '', '105755', 0),
(42, 'yygg', '332', NULL, NULL, NULL, '9c9179973f346f69a42c07494a0f6240', 'ios', '', '075551', 0),
(43, 'yahya', '99472', NULL, NULL, NULL, '2938cfc1c0bf62def22d8f28235b422b', 'ios', '', '003349', 0),
(44, 'gggg', '588', NULL, NULL, NULL, '64f653e373cb24d3efee42d551c0a280', 'ios', '', '945402', 0),
(45, 'ggg', '888', NULL, NULL, NULL, '36fa1211e5b60d9dc6b21725226d4648', 'ios', '', '268793', 0),
(46, 'bbdb', '5754', NULL, NULL, NULL, 'fec297417671699158e4642cf8b6dc58', 'ios', '', '707633', 0),
(47, 'ggg', '558', NULL, NULL, NULL, '36e74e7260ad45303191f1ee39efd6fa', 'ios', '', '831210', 0),
(48, 'hhh', '55', NULL, NULL, NULL, 'd27002c138ae5d6343220e5e4ea66ce7', 'ios', '', '363993', 0),
(49, 'dfs', '1234567', NULL, NULL, NULL, 'b9f8956d47f974f08918d4e1325e2da6', 'android', '', '087502', 0),
(50, 'ggg', '2', NULL, NULL, NULL, 'd766b2b80654e3a3979098065f6dc3f2', 'ios', '', '169174', 0),
(51, 'vvg', '258', NULL, NULL, NULL, '744773826532713fef3294f7607f9b2b', 'ios', '', '749941', 0),
(52, 'yy', '22', NULL, NULL, NULL, '558d91a661cedfd6bc14d6cf1700c024', 'ios', '', '177089', 1),
(53, 'uu', '00', NULL, NULL, NULL, 'b2ad63f3a33f8b2d9d83e8c1037b1567', 'ios', '', '967356', 0),
(54, 'ff', '885', NULL, NULL, NULL, '12d149c87d5fc5423d88f0bf80fdc5eb', 'ios', '', '950507', 0),
(55, 'اتته', '٥٥٨', NULL, NULL, NULL, '9ba8cce1c036452f3f4428a1ef3cdbf0', 'ios', '', '380443', 0),
(56, 'rabiii', '0958282814', NULL, NULL, '', '', 'android', '', NULL, 0),
(57, 'amal', '0936532568', NULL, NULL, '', '', 'android', '', NULL, 0),
(58, 'hamdii', '095863221', NULL, NULL, '', '', 'android', '', NULL, 0),
(59, 'sameer', '44532267', NULL, NULL, '', '', 'android', '', NULL, 0),
(60, 'yy', '99', NULL, NULL, NULL, 'ff58dba33c73c0a2d7861a11a3e56fbe', 'ios', '', '940190', 1),
(61, 'mozi', '506356563', NULL, NULL, NULL, '2f5246b892592348205764bd4adf1e5d', 'ios', '', '972741', 0),
(62, 'moz', '1231567', NULL, NULL, NULL, '98aaa78c4d9d495e0c707385c296899b', 'ios', '', '567746', 0),
(63, 'yy', '7777', NULL, NULL, NULL, '3c4ac1b63856f1546c6dd36f9f54d40a', 'ios', '', '319920', 0),
(64, 'ggyh', '6666', NULL, NULL, NULL, '44da7699acc8104e7a681e749f97d053', 'ios', '', '090628', 0),
(65, 'Lama', '994724565', NULL, NULL, NULL, 'c5bb8a93b735b11ad8e0b963ca260078', 'ios', '', '280407', 1),
(66, 'Rabii', '0958202814', NULL, NULL, '', '', 'android', '', NULL, 0),
(67, 'Ahmad', '095863211', NULL, NULL, '', '', 'android', '', NULL, 0),
(68, 'Ramii', '87412566', NULL, NULL, '', '', 'android', '', NULL, 0),
(69, 'Rabii', '41253698', NULL, NULL, '', '', 'android', '', NULL, 0),
(70, 'Rami', '095828214', NULL, NULL, '', '', 'android', '', NULL, 0),
(71, 'wael', '541236987', NULL, NULL, '', '', 'android', '', NULL, 0),
(72, 'wael', '147258369', NULL, NULL, '', '', 'android', '', NULL, 0),
(73, 'Wael', '069851472', NULL, NULL, '', '', 'android', '', NULL, 0),
(74, 'amal', '156324', NULL, NULL, '', '', 'android', '', NULL, 0),
(75, 'amal', '15266', NULL, NULL, '', '', 'android', '', NULL, 0),
(76, 'gg', '1224', NULL, NULL, '', '', 'android', '', NULL, 0),
(77, 'gt', '455223', NULL, NULL, '', '', 'android', '', NULL, 0);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `prefered_game`
--

INSERT INTO `prefered_game` (`prefered_game_id`, `player_id`, `game_type_id`) VALUES
(1, 1, 2);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=267 ;

--
-- Dumping data for table `search`
--

INSERT INTO `search` (`search_record_id`, `player_id`, `token`, `area_id`, `game_type_id`, `start`, `date`, `duration`, `search_date`, `text`) VALUES
(1, 1, '0', 1, 1, '10:00:00', '2017-12-25', '00:00:02', '2017-01-02 15:06:54', '0'),
(2, 1, '0', 1, 1, '10:00:00', '2017-12-25', '00:00:00', '2017-01-02 15:09:58', '0'),
(3, 1, '0', 1, 1, '10:00:00', '2017-12-25', '00:00:02', '2017-01-02 15:10:07', '0'),
(4, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-02 15:19:31', '0'),
(5, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-02 15:20:16', '0'),
(6, 1, '0', 1, 1, '10:00:00', '2017-12-25', '00:00:02', '2017-01-02 15:20:32', '0'),
(7, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-02 15:20:45', '0'),
(8, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-02 15:40:32', '0'),
(9, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-02 15:40:40', '0'),
(10, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-02 15:40:44', '0'),
(11, NULL, '0', 1, 1, '10:00:00', '2017-12-25', '00:00:02', '2017-01-02 15:40:53', '0'),
(12, NULL, '0', 1, 1, '08:00:00', '2017-12-25', '00:00:02', '2017-01-02 15:41:08', '0'),
(13, NULL, '0', 1, 1, '08:00:00', '2017-12-25', '00:00:02', '2017-01-02 15:41:15', '0'),
(14, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-02 16:00:24', 'y'),
(15, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-02 16:01:09', ''),
(16, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-02 16:02:52', 'g'),
(17, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-02 16:03:33', 'tt'),
(18, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-02 16:06:21', ''),
(19, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-02 16:07:59', ''),
(20, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-02 16:09:30', '0'),
(21, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-02 16:09:43', 'yg'),
(22, NULL, '3', 1, 2, NULL, NULL, NULL, '2017-01-02 16:09:55', 'sds'),
(23, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-02 16:10:07', 'yg'),
(24, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-02 16:10:10', 'yg'),
(25, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-02 16:11:05', '0'),
(26, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-02 16:11:16', ''),
(27, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 10:17:23', ''),
(28, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 10:20:24', 'y'),
(29, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 11:00:12', 'gg'),
(30, NULL, '3', 1, 2, NULL, NULL, NULL, '2017-01-03 11:01:38', 'sds'),
(31, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 11:04:52', 'gg'),
(32, NULL, '3', 1, 2, NULL, NULL, NULL, '2017-01-03 11:05:22', 'sds'),
(33, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 11:10:36', ''),
(34, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 11:12:13', ''),
(35, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 11:12:15', ''),
(36, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 11:12:22', ''),
(37, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 11:53:19', ''),
(38, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 12:09:58', ''),
(39, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 12:12:43', ''),
(40, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 12:53:40', ''),
(41, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 13:17:00', ''),
(42, NULL, '3', 1, 2, NULL, NULL, NULL, '2017-01-03 13:18:57', 'sds'),
(43, NULL, '3', 1, 2, '10:00:00', '2016-12-04', '00:00:03', '2017-01-03 13:21:26', 'sds'),
(44, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 13:24:05', ''),
(45, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 13:24:19', ''),
(46, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 13:24:24', ''),
(47, NULL, '3', 1, 2, NULL, NULL, NULL, '2017-01-03 13:24:39', ''),
(48, NULL, '3', 1, 1, NULL, NULL, NULL, '2017-01-03 13:24:51', ''),
(50, NULL, '3', 1, 2, NULL, NULL, NULL, '2017-01-03 13:25:29', 'dfdfdf'),
(51, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 13:26:38', ''),
(52, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 13:29:07', ''),
(53, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-03 13:29:31', '0'),
(54, NULL, '0', 2, 1, NULL, NULL, NULL, '2017-01-03 13:32:06', '0'),
(55, NULL, '0', 2, 2, NULL, NULL, NULL, '2017-01-03 13:32:15', '0'),
(58, NULL, '0', 2, 3, NULL, NULL, NULL, '2017-01-03 13:36:23', '0'),
(59, NULL, '0', 2, 3, NULL, NULL, NULL, '2017-01-03 13:36:44', '0'),
(60, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:36:49', '0'),
(61, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:46:33', '0'),
(62, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:46:59', '0'),
(63, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:47:56', '0'),
(64, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:48:21', ''),
(65, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:48:25', 'f'),
(66, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:48:32', 's'),
(67, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:49:38', 's'),
(68, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:50:32', 'f'),
(69, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:51:19', 'f'),
(70, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:51:21', 'f'),
(71, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:51:22', 'f'),
(72, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:51:23', 'f'),
(73, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:51:41', 'f'),
(74, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:51:47', 's'),
(75, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:51:53', 'z'),
(76, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:52:06', ''),
(77, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:52:11', 'q'),
(78, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:52:16', 'w'),
(79, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 13:58:15', 'w'),
(80, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 14:00:40', 'w'),
(81, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 14:01:51', 'w'),
(82, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 14:02:14', 'u'),
(83, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 14:02:21', 'l'),
(84, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 14:02:32', '.'),
(85, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 14:02:38', 'x'),
(86, NULL, '0', 3, 3, NULL, NULL, NULL, '2017-01-03 14:02:41', ''),
(87, NULL, '0', 3, 2, NULL, NULL, NULL, '2017-01-03 14:02:46', ''),
(88, NULL, '0', 3, 2, NULL, NULL, NULL, '2017-01-03 14:04:03', ''),
(89, NULL, '0', 1, 2, NULL, NULL, NULL, '2017-01-03 14:04:06', ''),
(90, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-03 14:04:11', ''),
(91, NULL, '0', 2, 1, NULL, NULL, NULL, '2017-01-03 14:04:17', ''),
(92, NULL, '0', 2, 1, NULL, NULL, NULL, '2017-01-03 14:04:38', ''),
(93, NULL, '0', 2, 2, NULL, NULL, NULL, '2017-01-03 14:04:43', ''),
(94, NULL, '0', 2, 2, NULL, NULL, NULL, '2017-01-03 14:04:59', ''),
(95, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:30:45', ''),
(96, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:31:06', ''),
(97, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:41:26', ''),
(98, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:42:18', ''),
(99, NULL, '', 2, 1, NULL, NULL, NULL, '2017-01-03 14:42:21', ''),
(100, NULL, '3', 1, 2, '10:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:43:06', ''),
(101, NULL, '3', 1, 2, '10:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:43:32', ''),
(102, NULL, '3', 1, 2, '10:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:43:58', ''),
(103, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:44:42', ''),
(104, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:46:04', ''),
(105, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:48:29', ''),
(106, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:48:44', ''),
(107, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:48:52', ''),
(108, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:50:27', ''),
(109, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:51:05', ''),
(110, NULL, '', 1, 2, '10:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:52:00', ''),
(111, NULL, '', 1, 2, '01:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:52:12', ''),
(112, NULL, '', 1, 2, '01:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:52:16', ''),
(113, NULL, '', 1, 2, '02:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:52:22', ''),
(114, NULL, '', 1, 2, '10:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:52:28', ''),
(115, NULL, '', 1, 2, '01:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:52:39', ''),
(116, NULL, '', 1, 2, '10:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:52:44', ''),
(117, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:53:17', ''),
(118, NULL, '', 1, 2, '10:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:54:13', ''),
(119, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-03 14:54:21', ''),
(120, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-03 14:54:29', ''),
(121, NULL, '', 1, 2, '10:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:54:34', ''),
(122, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-03 14:54:40', ''),
(123, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-03 14:54:44', ''),
(124, NULL, '', 1, 2, '10:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:54:50', ''),
(125, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-03 14:55:00', ''),
(126, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-03 14:55:08', ''),
(127, NULL, '', 1, 2, '10:00:00', '2016-12-04', '00:00:03', '2017-01-03 14:55:14', ''),
(128, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:56:03', ''),
(129, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:57:29', ''),
(130, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 14:57:41', ''),
(131, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 15:02:22', ''),
(132, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 15:03:35', ''),
(133, NULL, '', 1, 1, '01:00:00', '2017-01-03', '00:00:01', '2017-01-03 15:03:45', ''),
(134, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 15:03:55', ''),
(135, NULL, '99', 1, 1, NULL, NULL, NULL, '2017-01-03 15:03:58', ''),
(136, NULL, '99', 1, 1, NULL, NULL, NULL, '2017-01-03 15:04:14', ''),
(137, NULL, '99', 1, 1, NULL, NULL, NULL, '2017-01-03 15:04:43', ''),
(138, NULL, '99', 1, 1, '01:00:00', '2017-01-03', '00:00:02', '2017-01-03 15:05:48', ''),
(139, NULL, '99', 1, 1, '01:00:00', '2017-01-03', '00:00:02', '2017-01-03 15:06:06', ''),
(140, NULL, '', 1, 2, '10:00:00', '2016-12-04', '00:00:03', '2017-01-03 15:06:17', ''),
(141, NULL, '', 1, 2, '01:00:00', '2016-12-04', '00:00:03', '2017-01-03 15:06:20', ''),
(142, NULL, '99', 1, 1, '10:00:00', '2017-01-03', '00:00:02', '2017-01-03 15:06:35', ''),
(143, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 15:06:55', ''),
(144, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 15:07:24', ''),
(145, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 15:07:31', ''),
(146, NULL, '', 1, 1, '10:00:00', '2017-01-03', '00:00:01', '2017-01-03 15:07:39', ''),
(147, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 15:08:14', ''),
(148, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 15:08:26', ''),
(149, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-03 15:08:58', ''),
(150, NULL, '', 1, 1, '10:00:00', '2017-01-03', '00:00:01', '2017-01-03 15:11:56', ''),
(151, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 15:12:24', ''),
(152, NULL, '', 1, 1, '01:00:00', '2017-01-03', '00:00:01', '2017-01-03 15:14:33', ''),
(153, NULL, '', 1, 1, '10:00:00', '2017-01-03', '00:00:01', '2017-01-03 15:14:39', ''),
(154, NULL, '', 1, 1, '01:00:00', '2017-01-03', '00:00:01', '2017-01-03 15:15:41', ''),
(155, NULL, '', 1, 1, '11:00:00', '2017-01-03', '00:00:01', '2017-01-03 15:15:49', ''),
(156, NULL, '', 1, 2, '01:00:00', '2016-12-04', '00:00:03', '2017-01-03 15:16:07', ''),
(157, NULL, '', 1, 2, '10:00:00', '2016-12-04', '00:00:03', '2017-01-03 15:16:13', ''),
(158, NULL, '', 2, 1, NULL, NULL, NULL, '2017-01-03 15:19:05', ''),
(159, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 15:20:06', ''),
(160, NULL, '0', 2, 2, '08:00:00', '2017-12-25', '00:00:02', '2017-01-03 15:23:17', ''),
(161, NULL, '0', 2, 2, '08:00:00', '2017-12-25', '00:00:02', '2017-01-03 15:23:58', ''),
(162, NULL, '', 2, 2, '10:00:00', '2017-01-03', '00:00:03', '2017-01-03 15:35:33', ''),
(163, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 15:35:39', ''),
(164, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 15:47:38', ''),
(165, NULL, '0', 1, 1, '11:00:00', '2017-12-25', '00:00:02', '2017-01-03 04:34:32', ''),
(166, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-21 22:35:59', 'ahdaf'),
(167, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-21 22:36:18', 'ahdaf'),
(168, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 03:02:08', 'ahdaf'),
(169, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 03:03:11', 'ahdaf'),
(170, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 03:04:07', 'ahdaf'),
(171, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 03:04:18', 'ahdaf'),
(172, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 03:04:28', 'ahdaf'),
(173, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 03:04:30', 'ahdaf'),
(174, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 03:04:42', 'ahdaf'),
(175, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 03:04:56', 'ahdaf'),
(176, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 04:40:05', 'ahdaf'),
(177, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 04:40:17', 'ahdaf'),
(178, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 04:42:57', 'ahdaf'),
(179, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-22 04:50:14', 'ahdaf'),
(180, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-22 04:52:03', 'ahdaf'),
(181, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-22 04:56:35', 'ahdaff'),
(182, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-22 04:56:57', 'ahdaff'),
(183, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-22 04:59:02', 'ahdaff'),
(184, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-22 04:59:33', 'ahdaff'),
(185, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-22 22:54:04', 'ahdaff'),
(186, NULL, '0', 0, 1, NULL, NULL, NULL, '2017-01-22 23:41:49', 'ahdaff'),
(187, NULL, '0', 0, 1, NULL, NULL, NULL, '2017-01-22 23:43:18', 'ahdaff'),
(188, NULL, '0', 1, 0, NULL, NULL, NULL, '2017-01-22 23:46:30', 'ahdaff'),
(189, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 23:53:17', 'ahdaff'),
(190, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 23:55:08', 'ahdaff'),
(191, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-23 01:01:11', 'ahdaff'),
(192, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:01:36', ''),
(193, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:02:09', ''),
(194, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:03:03', ''),
(195, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:06:19', ''),
(196, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:08:07', ''),
(197, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:09:16', ''),
(198, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:12:05', ''),
(199, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:14:01', ''),
(200, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:14:14', ''),
(201, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:14:19', ''),
(202, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:14:41', ''),
(203, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:14:56', ''),
(204, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:15:07', ''),
(205, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:15:14', ''),
(206, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:15:25', ''),
(207, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:15:31', ''),
(208, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:15:38', ''),
(209, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:16:09', ''),
(210, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:17:56', ''),
(211, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:19:01', ''),
(212, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:19:12', ''),
(213, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:19:40', ''),
(214, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:19:49', ''),
(215, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:19:55', ''),
(216, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:20:32', ''),
(217, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:21:31', ''),
(218, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:21:38', ''),
(219, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:21:48', ''),
(220, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:27:00', ''),
(221, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:29:31', ''),
(222, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:35:32', ''),
(223, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:42:37', ''),
(224, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:53:43', ''),
(225, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:54:19', ''),
(226, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 01:54:39', ''),
(227, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:34:09', ''),
(228, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:50:11', ''),
(229, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:51:17', ''),
(230, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:51:21', ''),
(231, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:51:30', ''),
(232, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:52:06', ''),
(233, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:52:10', ''),
(234, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:52:15', ''),
(235, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:52:21', ''),
(236, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:52:23', ''),
(237, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:52:48', ''),
(238, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:52:51', ''),
(239, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:52:53', ''),
(240, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:52:56', ''),
(241, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:53:03', ''),
(242, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:53:19', ''),
(243, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:53:37', ''),
(244, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:53:53', ''),
(245, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:54:21', ''),
(246, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:55:39', ''),
(247, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:55:41', ''),
(248, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:55:47', ''),
(249, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:57:06', ''),
(250, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 04:57:10', ''),
(251, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 05:02:06', ''),
(252, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 05:02:11', ''),
(253, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 05:02:15', ''),
(254, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 05:02:19', ''),
(255, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 05:02:22', ''),
(256, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 05:02:30', ''),
(257, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 05:02:32', ''),
(258, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 05:06:56', ''),
(259, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 05:07:03', ''),
(260, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 21:53:40', ''),
(261, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 21:54:02', ''),
(262, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 21:54:18', ''),
(263, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 21:59:25', ''),
(264, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 22:00:29', ''),
(265, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 22:58:57', ''),
(266, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-23 22:59:02', '');

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
-- Table structure for table `tokens`
--

CREATE TABLE IF NOT EXISTS `tokens` (
  `token_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `os` varchar(30) NOT NULL DEFAULT 'android',
  `token` text NOT NULL,
  PRIMARY KEY (`token_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `tokens`
--

INSERT INTO `tokens` (`token_id`, `user_id`, `os`, `token`) VALUES
(1, 1, 'android', '222222'),
(2, 1, 'android', '222222'),
(3, 1, 'android', '222223');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `name`, `username`, `password`, `phone`, `email`, `profile_picture`, `company_id`, `role_id`, `active`) VALUES
(1, 'admin1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, 2, 1, 1),
(2, 'support', 'support_admin', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, 1, 2, 1);

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
