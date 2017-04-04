

-- --------------------------------------------------------

--
-- Table structure for table `company`
--

CREATE TABLE `company` (
  `company_id` int(11) NOT NULL,
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
  `ar_description` text,
  `deleted` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `company`
--

INSERT INTO `company` (`company_id`, `en_name`, `ar_name`, `phone`, `en_address`, `ar_address`, `longitude`, `latitude`, `area_id`, `logo`, `image`, `en_description`, `ar_description`, `deleted`) VALUES
(1, 'IFA', '', '8768867', 'Dubai', '', 55.1758914, 25.0981826, 1, '', '', '', '', 1),
(2, 'IFA Sports Central 2', 'IFA Sports Central 2', '+9714451683', 'Al Barsha Pond Park Jogging Trail', 'Al Barsha Pond Park Jogging Trail', 55.202404260635376, 25.103065497197335, 1, '0_image35.png', '1_image6.png', 'IFA is the largest grass roots football academy in the UAE. Our training programmes, for children and young adults from 18 months to 18 years of age are delivered by FA qualified coaches.', 'IFA is the largest grass roots football academy in the UAE. Our training programmes, for children and young adults from 18 months to 18 years of age are delivered by FA qualified coaches.', 0),
(3, 'IFA Meadows Football Centre', '', '+ 971 4 454 1683 ', 'Sheikh Zayed Road', '', 55.155359, 25.066424, 1, '9a0a0-meadows.png', '91907-maxresdefault.jpg', 'IFA Meadows Football Centre is located in the Emirates International School - The Meadows. Comprising of two 7 a side pitches. Available 5pm to 10pm Sunday to Thursday and from 8am to 10pm Friday and Saturdays.', '', 0),
(4, 'Ahdaf', NULL, '+971 800 243223', 'cairo street', NULL, 55.344932, 25.300782, 1, 'd1008-ahdaaf_logo.png', '9877b-about-us_bg.jpg', 'Formed in 2008 through Dubai SME. Ahdaaf Sports Club aims to promote a healthy lifestyle and bridge the gap between different communities through sports.', NULL, 0),
(5, 'Ahdaf', NULL, '+971 800 243223', 'cairo street', NULL, 55.299678, 25.249724, 1, 'd1008-ahdaaf_logo.png', '9877b-about-us_bg.jpg', 'Formed in 2008 through Dubai SME. Ahdaaf Sports Club aims to promote a healthy lifestyle and bridge the gap between different communities through sports.', NULL, 1),
(6, 'Muzaffar International', NULL, '0063944687774', 'Dubai', NULL, 55.299678, 25.249724, 1, NULL, NULL, 'this field is in the middle of the desert', NULL, 1),
(7, 'fieldium', 'fieldium', '23424', '2', '2', 1, 1, 1, 'amal2', 'amal', '0', '0', 1),
(8, 'test company', NULL, '0843987358', 'test kdj,', NULL, 55.299678, 25.249724, 1, NULL, NULL, 'test', NULL, 1),
(9, 'Aktiv Nation', 'Aktiv Nation', '0097143284779', 'Al-Quoz 3', 'Al-Quoz 3', 55.299678, 25.249724, 1, '19dbf-logo.png', '', 'We offer everything', 'We offer everything', 0),
(10, 'United Pro Sports', NULL, '0097143401122', 'Al Quoz 2', NULL, 55.299678, 25.249724, 1, '60203-logo-2.png', NULL, 'United Pro Sports', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `field`
--

CREATE TABLE `field` (
  `field_id` int(11) NOT NULL,
  `en_name` varchar(45) DEFAULT NULL,
  `ar_name` varchar(40) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `hour_rate` double DEFAULT NULL,
  `open_time` time DEFAULT NULL,
  `close_time` time DEFAULT NULL,
  `en_description` text NOT NULL,
  `ar_description` text,
  `area_x` double DEFAULT NULL,
  `area_y` double DEFAULT NULL,
  `max_capacity` int(11) DEFAULT NULL,
  `featured_place` tinyint(1) NOT NULL DEFAULT '0',
  `auto_confirm` tinyint(1) NOT NULL DEFAULT '0',
  `deleted` int(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `field`
--

INSERT INTO `field` (`field_id`, `en_name`, `ar_name`, `phone`, `company_id`, `hour_rate`, `open_time`, `close_time`, `en_description`, `ar_description`, `area_x`, `area_y`, `max_capacity`, `featured_place`, `auto_confirm`, `deleted`) VALUES
(1, 'IFA Sports - Meadows', 'IFA Sports - Meadows', '231364456', 2, 200, '10:00:00', '04:00:00', '0', '0', 10, 10, 12, 1, 1, 0),
(2, 'Ahdaf field', 'Field 2', '0980980980', 4, 10, '03:00:00', '15:00:00', '', '', 11, 15, 10, 0, 0, 0),
(3, 'IFA Sports - Al Safa', 'IFA Sports - Al Safa', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 10, 1, 0, 0),
(4, 'IFA Sports - Al Safa', NULL, '0958202814', 3, 50, '06:00:00', '22:00:00', '', NULL, 100, 200, 10, 1, 1, 0),
(5, 'IFA Sports - Meadows', NULL, '0958282814', 3, 20, '07:00:00', '20:00:00', '', NULL, 100, 100, 5, 0, 0, 0),
(6, 'Field 6', NULL, '123456988', 3, 85, '05:00:00', '23:00:00', '', NULL, 200, 400, 22, 0, 0, 1),
(7, '000000', '000000', '25588878797', 2, 10, '06:00:00', '20:00:00', 'hggh', 'hggh', 100, 200, 10, 0, 0, 1),
(8, 'Filed 4', NULL, '5453456988', 2, 60, '08:00:00', '21:00:00', '', NULL, 100, 100, 5, 0, 0, 1),
(9, 'Filed 5', NULL, '699893333', 2, 90, '05:00:00', '16:00:00', '', NULL, 200, 300, 12, 0, 0, 1),
(10, 'Filed 6', NULL, '33333333', 2, 200, '10:00:00', '02:00:00', '', NULL, 100, 100, 4, 0, 0, 1),
(11, 'Field 7', NULL, '54564545', 2, 50, '07:00:00', '21:00:00', '', NULL, 100, 150, 8, 0, 0, 1),
(12, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 0, 1),
(13, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 0, 1),
(14, '324', NULL, '3244', 7, 3, '03:00:00', '10:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(15, '324', NULL, '3244', 6, 3, '03:00:00', '10:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(16, '324', NULL, '3244', 6, 3, '03:00:00', '10:00:00', '', NULL, 3, 3, 3, 0, 0, 1),
(17, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1, 1),
(18, 'new field', 'new field', '456789', 2, 200, '10:00:00', '22:00:00', '', '', 5, 5, 20, 0, 0, 1),
(19, 'bbbbbbbb', 'bbbbbbbb', '456789', 2, 700, '10:00:00', '22:00:00', '', '', 6, 6, 5, 0, 0, 1),
(20, 'ggggggg', 'ggggggg', '455', 2, 5, '00:00:00', '22:00:00', '', '', 5, 5, 5, 0, 0, 1),
(21, 'gggghj', 'gggghj', '455', 2, 6, '10:00:00', '22:00:00', '', '', 5, 5, 5, 0, 0, 1),
(22, 'new fied', 'new fied', '245553666', 2, 666, '09:00:00', '23:00:00', 'ee', 'ee', 100, 100, 5, 0, 1, 1),
(23, 'new fied', 'new fied', '245553666', 2, 666, '09:00:00', '23:00:00', 'ee', 'ee', 100, 100, 5, 0, 1, 1),
(24, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1, 1),
(25, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1, 1),
(26, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1, 1),
(27, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1, 1),
(28, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1, 1),
(29, 'new field', 'new field', '069855322', 2, 200, '07:00:00', '16:00:00', 'rrdd', 'rrdd', 100, 100, 5, 0, 1, 1),
(30, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1, 1),
(31, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1, 1),
(32, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1, 1),
(33, 'new fieldd', 'new fieldd', '223333', 2, 100, '07:00:00', '16:00:00', 'gggh', 'gggh', 100, 100, 25, 0, 1, 1),
(34, 'qqqqqqq', 'qqqqqqq', '456', 2, 2, '10:00:00', '22:00:00', 'wer', 'wer', 5, 5, 5, 0, 0, 1),
(35, 'mmmmmm', 'mmmmmm', '123456789', 2, 6, '10:00:00', '22:00:00', 'errrr', 'errrr', 4, 8, 7, 0, 0, 1),
(36, 'new field', 'new field', '456789', 2, 6, '10:00:00', '22:00:00', 'dff\nhggh', 'dff\nhggh', 8, 2, 5, 0, 1, 1),
(37, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1, 1),
(38, 'fieldium', 'fieldium', '23424', 2, 100, '10:00:00', '23:00:00', '0', '0', 10, 10, 20, 0, 1, 1),
(39, 'new field', 'new field', '456789', 2, 6, '10:00:00', '22:00:00', 'dff\nhggh', 'dff\nhggh', 8, 2, 5, 0, 1, 1),
(40, 'test field', 'test field', '09856', 2, 55, '21:00:00', '10:00:00', '', '', 0, 0, 0, 0, 0, 1),
(41, 'new field', 'new field', '0936110314', 2, 5, '10:00:00', '22:00:00', 'nwote', 'nwote', 5, 5, 10, 0, 0, 1),
(42, ' zzzzzzzz', ' zzzzzzzz', '04123456', 9, 500, '07:00:00', '21:00:00', 'good ', 'good ', 5, 5, 50, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `field_amenity`
--

CREATE TABLE `field_amenity` (
  `field_amenity_id` int(11) NOT NULL,
  `amenity_id` int(11) DEFAULT NULL,
  `field_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `field_amenity`
--

INSERT INTO `field_amenity` (`field_amenity_id`, `amenity_id`, `field_id`) VALUES
(13, 3, 8),
(14, 4, 9),
(79, 12, 4),
(80, 7, 4),
(81, 3, 4),
(82, 5, 4),
(83, 10, 4),
(84, 4, 4),
(85, 1, 4),
(127, 1, 18),
(138, 1, 29),
(139, 3, 29),
(140, 4, 29),
(141, 4, 33),
(142, 3, 33),
(143, 1, 33),
(144, 7, 33),
(145, 1, 34),
(146, 3, 34),
(147, 4, 34),
(170, 1, 35),
(171, 3, 35),
(172, 4, 35),
(243, 1, 38),
(244, 7, 39),
(245, 10, 39),
(259, 1, 40),
(260, 12, 40),
(282, 1, 41),
(283, 3, 41),
(284, 4, 41),
(307, 5, 42),
(308, 7, 42),
(309, 10, 42),
(310, 12, 42),
(317, 1, 1),
(318, 3, 1),
(319, 4, 1),
(320, 1, 3),
(321, 3, 3),
(322, 4, 3);

-- --------------------------------------------------------

--
-- Table structure for table `field_game_type`
--

CREATE TABLE `field_game_type` (
  `field_game_type_id` int(11) NOT NULL,
  `field_id` int(11) DEFAULT NULL,
  `game_type_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `field_game_type`
--

INSERT INTO `field_game_type` (`field_game_type_id`, `field_id`, `game_type_id`) VALUES
(7, 2, 1),
(8, 4, 1),
(9, 4, 2),
(10, 5, 1),
(11, 6, 1),
(13, 2, 2),
(15, 8, 1),
(16, 9, 1),
(17, 10, 2),
(18, 11, 1),
(32, 14, 1),
(33, 15, 1),
(34, 16, 1),
(94, 18, 1),
(95, 19, 1),
(96, 20, 1),
(100, 21, 1),
(103, 30, 1),
(106, 29, 1),
(107, 29, 2),
(108, 33, 1),
(109, 33, 2),
(110, 34, 1),
(122, 35, 1),
(138, 38, 1),
(139, 38, 2),
(140, 39, 1),
(157, 41, 1),
(166, 42, 1),
(171, 1, 1),
(172, 1, 2),
(173, 3, 2);

-- --------------------------------------------------------

--
-- Table structure for table `game_type`
--

CREATE TABLE `game_type` (
  `game_type_id` int(11) NOT NULL,
  `en_name` varchar(45) NOT NULL,
  `ar_name` varchar(40) DEFAULT NULL,
  `image` varchar(200) NOT NULL,
  `en_description` text NOT NULL,
  `ar_description` text,
  `minimum_duration` int(11) NOT NULL DEFAULT '60',
  `increament_factor` int(11) NOT NULL DEFAULT '30'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `game_type`
--

INSERT INTO `game_type` (`game_type_id`, `en_name`, `ar_name`, `image`, `en_description`, `ar_description`, `minimum_duration`, `increament_factor`) VALUES
(1, 'Soccer', '', '8b3ad-soccer.png', '', '', 60, 30),
(2, 'Tennis', NULL, 'f3f8a-tennis.png', '', NULL, 60, 15);

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE `image` (
  `image_id` int(11) NOT NULL,
  `field_id` int(11) DEFAULT NULL,
  `name` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `image`
--

INSERT INTO `image` (`image_id`, `field_id`, `name`) VALUES
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
(12, NULL, 'e1941-field2.jpg'),
(13, 3, '76e85-field3.jpg'),
(14, NULL, '7b45d-field4.jpg'),
(15, NULL, '0_image3.jpg'),
(16, NULL, '0_image4.jpg'),
(17, NULL, '0_image5.jpg'),
(18, NULL, '1_image.jpg'),
(19, NULL, '1_image1.jpg'),
(20, NULL, '2_image.jpg'),
(21, NULL, '2_image1.jpg'),
(22, NULL, '2_image2.jpg'),
(25, NULL, '2_image3.jpg'),
(26, 4, 'a82f3-field1.jpg'),
(27, NULL, '54685-76e85-field3.jpg'),
(28, NULL, 'ui-icons_2e83ff_256x240.png'),
(29, NULL, '1_image2.jpg'),
(30, NULL, '1_image3.jpg'),
(31, NULL, '1_image4.jpg'),
(32, NULL, '1_image5.jpg'),
(33, NULL, '1_image6.jpg'),
(34, NULL, '0_image.jpg'),
(35, NULL, '0_image1.jpg'),
(36, 7, '0_image8.jpg'),
(37, NULL, '0_image9.jpg'),
(38, NULL, '0_image10.jpg'),
(39, NULL, '0_image11.jpg'),
(40, NULL, '0_image12.jpg'),
(41, NULL, '0_image13.jpg'),
(42, NULL, '0_image14.jpg'),
(43, 1, '0_image15.jpg'),
(44, 8, '4c4bb-0_image8.jpg'),
(45, 2, '1884f-0_image8.jpg'),
(46, 6, '40c64-a82f3-field1.jpg'),
(47, 5, 'baa19-0_image8.jpg'),
(48, 5, '8160c-a82f3-field1.jpg'),
(49, NULL, '0_image7.png'),
(52, 18, '0_image10.png'),
(53, 19, '0_image11.png'),
(54, 20, '0_image12.png'),
(55, 21, '0_image13.png'),
(56, NULL, '0_image14.png'),
(57, NULL, '0_image15.png'),
(58, 23, '0_image16.png'),
(59, 29, '0_image17.png'),
(60, 33, '0_image18.png'),
(61, 34, '0_image19.png'),
(63, 35, 'd6cdb-76e85-field3.jpg'),
(64, 7, '85612-upload.png'),
(65, NULL, '0_image21.png'),
(67, 39, '59426-76e85-field3.jpg'),
(68, 40, '0_image25.png'),
(69, NULL, '0_image26.png'),
(70, NULL, '0_image27.png'),
(71, 40, '0_image28.png'),
(72, NULL, '0_image29.png'),
(73, 40, '0_image30.png'),
(74, NULL, '0_image31.png'),
(75, NULL, '1_image4.png'),
(76, NULL, '2_image.png'),
(77, 41, '0_image32.png'),
(78, NULL, '0_image33.png'),
(79, NULL, '0_image34.png'),
(80, 42, '0_image36.png');

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `notification_id` int(11) NOT NULL,
  `booking_id` int(11) NOT NULL,
  `player_id` int(11) DEFAULT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `content` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`notification_id`, `booking_id`, `player_id`, `date`, `content`) VALUES
(4, 73, 20, '2017-01-16 11:18:36', 'Your booking No.73 has been approved. '),
(5, 129, 20, '2017-01-16 11:21:08', 'Your booking No.129 has been declined. '),
(6, 81, 20, '2017-01-17 12:33:03', 'Your booking No.81 has been declined. '),
(7, 82, 20, '2017-01-17 12:33:08', 'Your booking No.82 has been approved. '),
(8, 100, 20, '2017-01-17 12:33:12', 'Your booking No.100 has been declined. '),
(9, 78, 20, '2017-01-17 12:33:16', 'Your booking No.78 has been declined. '),
(10, 110, 20, '2017-01-17 12:33:19', 'Your booking No.110 has been declined. '),
(11, 79, 20, '2017-01-17 12:33:23', 'Your booking No.79 has been declined. '),
(12, 86, 20, '2017-01-17 12:33:26', 'Your booking No.86 has been declined. '),
(13, 87, 20, '2017-01-17 12:33:29', 'Your booking No.87 has been declined. '),
(14, 88, 20, '2017-01-17 12:33:32', 'Your booking No.88 has been declined. '),
(15, 173, 26, '2017-01-17 14:14:25', 'Your booking No.173 has been declined. '),
(16, 174, 26, '2017-01-17 14:16:28', 'Your booking No.174 has been declined. '),
(17, 175, 26, '2017-01-17 14:18:52', 'Your booking No.175 has been approved. '),
(18, 180, 26, '2017-01-17 14:39:24', 'Your booking No.180 has been declined. '),
(19, 179, 26, '2017-01-17 14:39:26', 'Your booking No.179 has been declined. '),
(20, 178, 26, '2017-01-17 14:39:28', 'Your booking No.178 has been declined. '),
(21, 181, 26, '2017-01-17 14:39:42', 'Your booking No.181 has been approved. '),
(22, 183, 26, '2017-01-17 14:50:49', 'Your booking No.183 has been approved. '),
(23, 177, 26, '2017-01-17 14:50:55', 'Your booking No.177 has been approved. '),
(24, 73, 20, '2017-01-17 16:32:42', 'Your booking No.73 has been approved. '),
(25, 77, 20, '2017-01-18 09:38:17', 'Your booking No.77 has been declined. '),
(26, 139, 99, '2017-01-18 09:39:18', 'Your booking No.139 has been approved. '),
(27, 188, 20, '2017-01-18 10:00:14', 'Your booking No.188 has been approved. '),
(28, 73, 20, '2017-01-18 10:38:20', 'Your booking No.73 has been approved. '),
(29, 73, 20, '2017-01-18 12:15:48', 'Your booking No.73 has been approved. '),
(30, 73, 20, '2017-01-18 12:25:30', 'Your booking No.73 has been declined. '),
(31, 194, 20, '2017-01-18 12:25:32', 'Your booking No.194 has been declined. '),
(32, 196, 20, '2017-01-18 12:25:41', 'Your booking No.196 has been declined. '),
(33, 113, 26, '2017-01-18 13:46:31', 'Your booking No.113 has been approved. '),
(34, 79, 20, '2017-01-19 14:31:30', 'Your booking No.79 has been approved. '),
(35, 79, 20, '2017-01-19 14:34:00', 'Your booking No.79 has been approved. '),
(36, 79, 20, '2017-01-19 14:34:23', 'Your booking No.79 has been approved. '),
(37, 79, 20, '2017-01-19 14:34:41', 'Your booking No.79 has been approved. '),
(38, 207, 26, '2017-01-19 14:35:40', 'Your booking No.207 has been approved. '),
(39, 79, 20, '2017-01-19 14:38:00', 'Your booking No.79 has been approved. '),
(40, 79, 20, '2017-01-19 14:38:24', 'Your booking No.79 has been approved. '),
(41, 79, 20, '2017-01-19 14:38:37', 'Your booking No.79 has been approved. '),
(42, 207, 26, '2017-01-19 14:39:33', 'Your booking No.207 has been approved. '),
(43, 207, 26, '2017-01-19 14:39:53', 'Your booking No.207 has been approved. '),
(44, 207, 26, '2017-01-19 14:42:46', 'Your booking No.207 has been approved. '),
(45, 207, 26, '2017-01-19 14:43:01', 'Your booking No.207 has been approved. '),
(46, 207, 26, '2017-01-19 14:43:42', 'Your booking No.207 has been approved. '),
(47, 207, 26, '2017-01-19 14:50:01', 'Your booking No.207 has been approved. '),
(48, 207, 26, '2017-01-19 14:51:07', 'Your booking No.207 has been approved. '),
(49, 225, 1, '2017-01-19 15:32:20', 'Your booking No.225 has been declined. '),
(50, 226, 1, '2017-01-19 15:32:22', 'Your booking No.226 has been declined. '),
(51, 227, 1, '2017-01-19 15:32:24', 'Your booking No.227 has been declined. '),
(52, 229, 1, '2017-01-19 15:32:26', 'Your booking No.229 has been declined. '),
(53, 230, 1, '2017-01-19 15:32:27', 'Your booking No.230 has been declined. '),
(54, 231, 1, '2017-01-19 15:32:29', 'Your booking No.231 has been declined. '),
(55, 233, 1, '2017-01-19 15:33:02', 'Your booking No.233 has been declined. '),
(56, 234, 1, '2017-01-19 15:34:56', 'Your booking No.234 has been declined. '),
(57, 235, 1, '2017-01-19 15:36:00', 'Your booking No.235 has been declined. '),
(58, 79, 20, '2017-01-19 15:41:07', 'Your booking No.79 has been approved. '),
(59, 79, 20, '2017-01-19 15:41:32', 'Your booking No.79 has been approved. '),
(60, 238, 1, '2017-01-19 15:50:10', 'Your booking No.238 has been approved. '),
(61, 79, 20, '2017-01-19 17:03:05', 'Your booking No.79 has been approved. '),
(62, 79, 20, '2017-01-19 17:05:04', 'Your booking No.79 has been approved. '),
(63, 76, 20, '2017-01-23 13:58:00', 'Your booking No.76 has been approved. '),
(64, 79, 20, '2017-01-23 13:58:31', 'Your booking No.79 has been approved. '),
(65, 79, 20, '2017-01-23 13:59:01', 'Your booking No.79 has been approved. '),
(66, 79, 20, '2017-01-23 13:59:02', 'Your booking No.79 has been approved. '),
(67, 79, 20, '2017-01-23 13:59:04', 'Your booking No.79 has been approved. '),
(68, 251, 116, '2017-01-23 14:08:54', 'Your booking No.251 has been approved. '),
(69, 251, 116, '2017-01-23 14:11:23', 'Your booking No.251 has been approved. '),
(70, 251, 116, '2017-01-23 14:37:38', 'Your booking No.251 has been approved. '),
(71, 251, 116, '2017-01-23 14:39:03', 'Your booking No.251 has been approved. '),
(72, 251, 116, '2017-01-23 14:40:42', 'Your booking No.251 has been approved. '),
(73, 251, 116, '2017-01-23 14:42:48', 'Your booking No.251 has been approved. '),
(74, 251, 116, '2017-01-23 14:44:29', 'Your booking No.251 has been approved. '),
(75, 251, 116, '2017-01-23 14:45:56', 'Your booking No.251 has been approved. '),
(76, 251, 116, '2017-01-23 14:46:17', 'Your booking No.251 has been approved. '),
(77, 251, 116, '2017-01-23 14:47:08', 'Your booking No.251 has been approved. '),
(78, 251, 116, '2017-01-23 14:52:17', 'Your booking No.251 has been approved. '),
(79, 251, 116, '2017-01-23 15:03:54', 'Your booking No.251 has been approved. '),
(80, 251, 116, '2017-01-23 15:09:17', 'Your booking No.251 has been approved. '),
(81, 251, 116, '2017-01-23 15:12:14', 'Your booking No.251 has been approved. '),
(82, 260, 120, '2017-01-24 15:35:05', 'Your booking No.260 has been approved. '),
(83, 260, 120, '2017-01-24 15:35:26', 'Your booking No.260 has been approved. '),
(84, 79, 20, '2017-01-24 15:42:47', 'Your booking No.79 has been approved. '),
(85, 271, 121, '2017-01-24 16:29:11', 'Your booking No.271 has been approved. '),
(86, 240, 26, '2017-01-24 17:04:33', 'Your booking No.240 has been declined. '),
(87, 241, 26, '2017-01-24 17:04:35', 'Your booking No.241 has been declined. '),
(88, 242, 26, '2017-01-24 17:04:39', 'Your booking No.242 has been declined. '),
(89, 243, 1, '2017-01-24 17:04:41', 'Your booking No.243 has been declined. '),
(90, 244, 26, '2017-01-24 17:04:44', 'Your booking No.244 has been declined. '),
(91, 245, 1, '2017-01-24 17:04:45', 'Your booking No.245 has been declined. '),
(92, 246, 1, '2017-01-24 17:04:48', 'Your booking No.246 has been declined. '),
(93, 247, 103, '2017-01-24 17:04:50', 'Your booking No.247 has been declined. '),
(94, 253, 61, '2017-01-24 17:04:53', 'Your booking No.253 has been declined. '),
(95, 255, 61, '2017-01-24 17:04:55', 'Your booking No.255 has been declined. '),
(96, 256, 116, '2017-01-24 17:04:57', 'Your booking No.256 has been declined. '),
(97, 257, 116, '2017-01-24 17:04:59', 'Your booking No.257 has been declined. '),
(98, 258, 116, '2017-01-24 17:05:01', 'Your booking No.258 has been declined. '),
(99, 259, 52, '2017-01-24 17:05:02', 'Your booking No.259 has been declined. '),
(100, 261, 120, '2017-01-24 17:05:04', 'Your booking No.261 has been declined. '),
(101, 263, 120, '2017-01-24 17:05:07', 'Your booking No.263 has been declined. '),
(102, 264, 120, '2017-01-24 17:05:09', 'Your booking No.264 has been declined. '),
(103, 265, 120, '2017-01-24 17:09:53', 'Your booking No.265 has been declined. '),
(104, 267, 120, '2017-01-24 17:09:55', 'Your booking No.267 has been declined. '),
(105, 270, 20, '2017-01-24 17:09:58', 'Your booking No.270 has been declined. '),
(106, 273, 121, '2017-01-24 17:10:00', 'Your booking No.273 has been declined. '),
(107, 274, 121, '2017-01-24 17:10:02', 'Your booking No.274 has been declined. '),
(108, 276, 20, '2017-01-24 17:18:56', 'Your booking No.276 has been approved. '),
(109, 275, 20, '2017-01-24 17:19:00', 'Your booking No.275 has been declined. '),
(110, 284, 20, '2017-01-25 10:05:24', 'Your booking No.284 has been declined. '),
(111, 285, 20, '2017-01-25 10:06:11', 'Your booking No.285 has been declined. '),
(112, 287, 20, '2017-01-25 10:14:09', 'Your booking No.287 has been declined. '),
(113, 288, 20, '2017-01-25 10:14:11', 'Your booking No.288 has been declined. '),
(114, 319, 126, '2017-01-25 11:51:48', 'Your booking No.319 has been declined. '),
(115, 319, 126, '2017-01-25 11:51:57', 'Your booking No.319 has been declined. '),
(116, 304, 20, '2017-01-25 12:07:21', 'Your booking No.304 has been declined. '),
(117, 324, 20, '2017-01-25 12:12:00', 'Your booking No.324 has been declined. '),
(118, 326, 20, '2017-01-25 12:13:21', 'Your booking No.326 has been declined. '),
(119, 327, 20, '2017-01-25 12:16:06', 'Your booking No.327 has been declined. '),
(120, 329, 103, '2017-01-25 12:55:44', 'Your booking No.329 has been approved. '),
(121, 322, 20, '2017-01-25 12:55:56', 'Your booking No.322 has been approved. '),
(122, 289, 20, '2017-01-25 12:56:08', 'Your booking No.289 has been approved. '),
(123, 340, 1, '2017-01-25 13:22:27', 'Your booking No.340 has been approved. '),
(124, 339, 1, '2017-01-25 13:26:02', 'Your booking No.339 has been declined. '),
(125, 342, 1, '2017-01-25 13:29:23', 'Your booking No.342 has been declined. '),
(126, 344, 1, '2017-01-25 13:56:07', 'Your booking No.344 has been approved. '),
(127, 343, 1, '2017-01-25 13:56:30', 'Your booking No.343 has been declined. '),
(128, 338, 1, '2017-01-25 13:56:35', 'Your booking No.338 has been declined. '),
(129, 323, 20, '2017-01-25 13:56:40', 'Your booking No.323 has been declined. '),
(130, 345, 1, '2017-01-25 13:57:20', 'Your booking No.345 has been approved. '),
(131, 347, 1, '2017-01-25 13:59:04', 'Your booking No.347 has been declined. '),
(132, 347, 1, '2017-01-25 16:24:04', 'Your booking No.347 has been approved. '),
(133, 354, 130, '2017-01-25 16:24:25', 'Your booking No.354 has been approved. '),
(134, 354, 130, '2017-01-25 16:24:40', 'Your booking No.354 has been approved. '),
(135, 354, 130, '2017-01-25 16:24:51', 'Your booking No.354 has been approved. '),
(136, 354, 130, '2017-01-25 16:24:58', 'Your booking No.354 has been approved. '),
(137, 354, 130, '2017-01-25 16:25:15', 'Your booking No.354 has been approved. '),
(138, 354, 130, '2017-01-25 16:25:26', 'Your booking No.354 has been approved. '),
(139, 354, 130, '2017-01-25 16:30:25', 'Your booking No.354 has been approved. '),
(140, 290, 103, '2017-01-25 16:55:43', 'Your booking No.290 has been approved. '),
(141, 290, 103, '2017-01-25 17:00:13', 'Your booking No.290 has been approved. '),
(142, 290, 103, '2017-01-25 17:04:10', 'Your booking No.290 has been approved. '),
(143, 290, 103, '2017-01-25 17:04:31', 'Your booking No.290 has been approved. '),
(144, 290, 103, '2017-01-25 17:04:48', 'Your booking No.290 has been approved. '),
(145, 290, 103, '2017-01-25 17:06:19', 'Your booking No.290 has been approved. '),
(146, 290, 103, '2017-01-25 17:06:29', 'Your booking No.290 has been approved. '),
(147, 290, 103, '2017-01-26 09:51:02', 'Your booking No.290 has been approved. '),
(148, 290, 103, '2017-01-26 09:57:37', 'Your booking No.290 has been approved. '),
(149, 290, 103, '2017-01-26 09:58:21', 'Your booking No.290 has been approved. '),
(150, 290, 103, '2017-01-26 10:00:16', 'Your booking No.290 has been approved. '),
(151, 290, 103, '2017-01-26 10:02:04', 'Your booking No.290 has been approved. '),
(152, 290, 103, '2017-01-26 10:03:07', 'Your booking No.290 has been approved. '),
(153, 290, 103, '2017-01-26 10:05:37', 'Your booking No.290 has been approved. '),
(154, 290, 103, '2017-01-26 10:06:01', 'Your booking No.290 has been approved. '),
(155, 290, 103, '2017-01-26 10:09:02', 'Your booking No.290 has been approved. '),
(156, 290, 103, '2017-01-26 10:12:38', 'Your booking No.290 has been approved. '),
(157, 290, 103, '2017-01-26 10:14:05', 'Your booking No.290 has been approved. '),
(158, 290, 103, '2017-01-26 10:15:03', 'Your booking No.290 has been approved. '),
(159, 290, 103, '2017-01-26 10:15:49', 'Your booking No.290 has been approved. '),
(160, 290, 103, '2017-01-26 10:16:41', 'Your booking No.290 has been approved. '),
(161, 290, 103, '2017-01-26 10:18:41', 'Your booking No.290 has been approved. '),
(162, 290, 103, '2017-01-26 10:21:47', 'Your booking No.290 has been approved. '),
(163, 290, 103, '2017-01-26 10:21:58', 'Your booking No.290 has been approved. '),
(164, 290, 103, '2017-01-26 10:22:18', 'Your booking No.290 has been approved. '),
(165, 290, 103, '2017-01-26 10:24:01', 'Your booking No.290 has been approved. '),
(166, 290, 103, '2017-01-26 10:25:02', 'Your booking No.290 has been approved. '),
(167, 290, 103, '2017-01-26 10:26:34', 'Your booking No.290 has been approved. '),
(168, 290, 103, '2017-01-26 10:28:02', 'Your booking No.290 has been approved. '),
(169, 290, 103, '2017-01-26 10:29:34', 'Your booking No.290 has been approved. '),
(170, 290, 103, '2017-01-26 10:30:26', 'Your booking No.290 has been approved. '),
(171, 290, 103, '2017-01-26 10:33:24', 'Your booking No.290 has been approved. '),
(172, 290, 103, '2017-01-26 10:34:50', 'Your booking No.290 has been approved. '),
(173, 290, 103, '2017-01-26 10:36:32', 'Your booking No.290 has been approved. '),
(174, 290, 103, '2017-01-26 10:39:38', 'Your booking No.290 has been approved. '),
(175, 290, 103, '2017-01-26 10:43:43', 'Your booking No.290 has been approved. '),
(176, 290, 103, '2017-01-26 10:51:01', 'Your booking No.290 has been approved. '),
(177, 290, 103, '2017-01-26 10:51:50', 'Your booking No.290 has been approved. '),
(178, 290, 103, '2017-01-26 10:53:13', 'Your booking No.290 has been approved. '),
(179, 290, 103, '2017-01-26 10:54:06', 'Your booking No.290 has been approved. '),
(180, 290, 103, '2017-01-26 10:56:43', 'Your booking No.290 has been approved. '),
(181, 290, 103, '2017-01-26 10:58:54', 'Your booking No.290 has been approved. '),
(182, 290, 103, '2017-01-26 11:04:47', 'Your booking No.290 has been approved. '),
(183, 290, 103, '2017-01-26 11:06:28', 'Your booking No.290 has been approved. '),
(184, 290, 103, '2017-01-26 11:07:03', 'Your booking No.290 has been approved. '),
(185, 290, 103, '2017-01-26 11:14:50', 'Your booking No.290 has been approved. '),
(186, 290, 103, '2017-01-26 11:15:06', 'Your booking No.290 has been approved. '),
(187, 290, 103, '2017-01-26 11:15:16', 'Your booking No.290 has been approved. '),
(188, 290, 103, '2017-01-26 11:16:30', 'Your booking No.290 has been approved. '),
(189, 290, 103, '2017-01-26 11:17:17', 'Your booking No.290 has been approved. '),
(190, 290, 103, '2017-01-26 11:19:33', 'Your booking No.290 has been approved. '),
(191, 290, 103, '2017-01-26 11:23:03', 'Your booking No.290 has been approved. '),
(192, 290, 103, '2017-01-26 11:23:27', 'Your booking No.290 has been approved. '),
(193, 290, 103, '2017-01-26 12:08:30', 'Your booking No.290 has been approved. '),
(194, 290, 103, '2017-01-26 12:08:49', 'Your booking No.290 has been approved. '),
(195, 290, 103, '2017-01-26 12:09:12', 'Your booking No.290 has been approved. '),
(196, 290, 103, '2017-01-26 12:10:20', 'Your booking No.290 has been approved. '),
(197, 375, 136, '2017-01-26 14:49:22', 'Your booking No.375 has been approved. '),
(198, 388, 103, '2017-01-29 15:00:00', 'Your booking No.388 has been approved. '),
(199, 388, 103, '2017-01-29 15:00:29', 'Your booking No.388 has been approved. '),
(200, 388, 103, '2017-01-29 15:00:53', 'Your booking No.388 has been approved. '),
(201, 388, 103, '2017-01-29 15:01:08', 'Your booking No.388 has been approved. '),
(202, 388, 103, '2017-01-29 15:01:30', 'Your booking No.388 has been approved. '),
(203, 388, 103, '2017-01-29 15:01:38', 'Your booking No.388 has been approved. '),
(204, 388, 103, '2017-01-29 15:05:07', 'Your booking No.388 has been approved. '),
(205, 388, 103, '2017-01-29 15:05:15', 'Your booking No.388 has been approved. '),
(206, 388, 103, '2017-01-29 15:05:43', 'Your booking No.388 has been approved. '),
(207, 388, 103, '2017-01-29 15:07:59', 'Your booking No.388 has been approved. '),
(208, 388, 103, '2017-01-29 16:46:41', 'Your booking No.388 has been approved. '),
(209, 393, 103, '2017-01-30 10:01:08', 'Your booking No.393 has been approved. '),
(210, 393, 103, '2017-01-30 10:01:57', 'Your booking No.393 has been approved. '),
(211, 393, 103, '2017-01-30 10:02:10', 'Your booking No.393 has been approved. '),
(212, 393, 103, '2017-01-30 10:05:44', 'Your booking No.393 has been approved. '),
(213, 393, 103, '2017-01-30 10:14:23', 'Your booking No.393 has been approved. '),
(214, 393, 103, '2017-01-30 10:44:59', 'Your booking No.393 has been approved. '),
(215, 393, 103, '2017-01-30 10:49:13', 'Your booking No.393 has been approved. '),
(216, 393, 103, '2017-01-30 10:56:50', 'Your booking No.393 has been approved. '),
(217, 393, 103, '2017-01-30 14:05:55', 'Your booking No.393 has been approved. '),
(218, 393, 103, '2017-01-30 14:13:15', 'Your booking No.393 has been approved. '),
(219, 393, 103, '2017-01-30 14:14:47', 'Your booking No.393 has been approved. '),
(220, 351, 20, '2017-01-31 13:00:06', 'Your booking No.351 has been approved. '),
(221, 352, 103, '2017-01-31 13:00:09', 'Your booking No.352 has been declined. '),
(222, 353, 103, '2017-01-31 14:20:28', 'Your booking No.353 has been approved. '),
(223, 407, 61, '2017-01-31 14:20:35', 'Your booking No.407 has been approved. '),
(224, 389, 103, '2017-02-01 17:05:10', 'Your booking No.389 has been approved. '),
(225, 355, 130, '2017-02-02 11:24:42', 'Your booking No.355 has been declined. '),
(226, 357, 103, '2017-02-02 11:25:07', 'Your booking No.357 has been declined. '),
(227, 370, 20, '2017-02-02 11:25:10', 'Your booking No.370 has been declined. '),
(228, 378, 20, '2017-02-02 11:25:12', 'Your booking No.378 has been declined. '),
(229, 391, 103, '2017-02-02 11:25:14', 'Your booking No.391 has been declined. '),
(230, 392, 103, '2017-02-02 11:25:16', 'Your booking No.392 has been declined. '),
(231, 394, 103, '2017-02-02 11:25:18', 'Your booking No.394 has been declined. '),
(232, 403, 52, '2017-02-02 11:25:20', 'Your booking No.403 has been declined. '),
(233, 404, 50, '2017-02-02 11:25:22', 'Your booking No.404 has been declined. '),
(234, 409, 50, '2017-02-02 11:25:24', 'Your booking No.409 has been declined. '),
(235, 424, 132, '2017-02-02 11:26:58', 'Your booking No.424 has been approved. '),
(236, 425, 132, '2017-02-02 11:27:26', 'Your booking No.425 has been approved. '),
(237, 441, 132, '2017-02-02 12:16:28', 'Your booking No.441 has been approved. '),
(238, 443, 40, '2017-02-02 12:16:37', 'Your booking No.443 has been approved. '),
(239, 445, 40, '2017-02-02 12:25:32', 'Your booking No.445 has been declined. '),
(240, 446, 132, '2017-02-02 12:25:41', 'Your booking No.446 has been approved. '),
(241, 447, 132, '2017-02-02 12:26:02', 'Your booking No.447 has been declined. '),
(242, 430, 141, '2017-02-02 12:36:42', 'Your booking No.430 has been approved. '),
(243, 431, 141, '2017-02-02 12:36:45', 'Your booking No.431 has been declined. '),
(244, 465, 103, '2017-02-04 00:35:27', 'Your booking No.465 has been approved. '),
(245, 465, 103, '2017-02-04 00:35:47', 'Your booking No.465 has been approved. '),
(246, 465, 103, '2017-02-04 01:42:45', 'Your booking No.465 has been approved. '),
(247, 479, 151, '2017-02-06 13:56:53', 'Your booking No.479 has been declined. '),
(248, 482, 151, '2017-02-06 14:01:18', 'Your booking No.482 has been approved. '),
(249, 485, 151, '2017-02-06 14:12:01', 'Your booking No.485 has been approved. '),
(250, 486, 151, '2017-02-06 14:14:02', 'Your booking No.486 has been approved. '),
(251, 487, 151, '2017-02-06 14:15:30', 'Your booking No.487 has been approved. '),
(252, 488, 116, '2017-02-06 14:16:33', 'Your booking No.488 has been approved. '),
(253, 481, 151, '2017-02-06 14:18:59', 'Your booking No.481 has been approved. '),
(254, 491, 151, '2017-02-06 14:36:20', 'Your booking No.491 has been approved. '),
(255, 492, 151, '2017-02-06 14:37:48', 'Your booking No.492 has been declined. '),
(256, 493, 151, '2017-02-06 14:38:47', 'Your booking No.493 has been declined. '),
(257, 494, 151, '2017-02-06 14:43:44', 'Your booking No.494 has been approved. '),
(258, 495, 151, '2017-02-06 14:47:49', 'Your booking No.495 has been approved. '),
(259, 495, 151, '2017-02-06 14:48:29', 'Your booking No.495 has been approved. '),
(260, 465, 103, '2017-02-06 14:55:43', 'Your booking No.465 has been approved. '),
(261, 465, 103, '2017-02-06 14:56:20', 'Your booking No.465 has been approved. '),
(262, 465, 103, '2017-02-06 15:06:22', 'Your booking No.465 has been approved. '),
(263, 465, 103, '2017-02-06 15:08:12', 'Your booking No.465 has been approved. '),
(264, 465, 103, '2017-02-06 15:14:32', 'Your booking No.465 has been approved. '),
(265, 499, 151, '2017-02-06 15:24:04', 'Your booking No.499 has been approved. '),
(266, 500, 151, '2017-02-06 15:24:47', 'Your booking No.500 has been declined. '),
(267, 502, 151, '2017-02-06 15:25:09', 'Your booking No.502 has been approved. '),
(268, 498, 103, '2017-02-06 16:39:27', 'Your booking No.498 has been approved. '),
(269, 508, 52, '2017-02-07 09:53:52', 'Your booking No.508 has been approved. '),
(270, 516, 132, '2017-02-07 10:59:10', 'Your booking No.516 has been approved. '),
(271, 521, 132, '2017-02-07 13:36:55', 'Your booking No.521 has been approved. '),
(272, 520, 40, '2017-02-07 15:01:55', 'Your booking No.520 has been approved. '),
(273, 520, 40, '2017-02-07 15:02:32', 'Your booking No.520 has been approved. '),
(274, 498, 103, '2017-02-09 10:49:43', 'Your booking No.498 has been approved. '),
(275, 498, 103, '2017-02-09 10:49:57', 'Your booking No.498 has been approved. '),
(276, 509, 116, '2017-02-09 12:57:09', 'Your booking No.509 has been declined. '),
(277, 547, 151, '2017-02-09 12:57:13', 'Your booking No.547 has been declined. '),
(278, 518, 132, '2017-02-09 13:04:51', 'Your booking No.518 has been declined. '),
(279, 539, 116, '2017-02-09 13:07:01', 'Your booking No.539 has been approved. '),
(280, 554, 105, '2017-02-10 12:19:09', 'Your booking No.554 has been approved. '),
(281, 562, 137, '2017-02-12 14:53:54', 'Your booking No.562 has been declined. '),
(282, 568, 151, '2017-02-14 10:08:48', 'Your booking No.568 has been approved. '),
(283, 569, 20, '2017-02-14 10:08:54', 'Your booking No.569 has been declined. '),
(284, 578, 116, '2017-02-16 09:14:33', 'Your booking No.578 has been approved. '),
(285, 579, 116, '2017-02-16 09:14:55', 'Your booking No.579 has been approved. '),
(286, 581, 116, '2017-02-16 09:15:19', 'Your booking No.581 has been approved. '),
(287, 585, 116, '2017-02-16 09:18:41', 'Your booking No.585 has been approved. '),
(288, 577, 151, '2017-02-16 09:37:08', 'Your booking No.577 has been declined. '),
(289, 580, 151, '2017-02-16 09:37:10', 'Your booking No.580 has been declined. '),
(290, 584, 26, '2017-02-16 09:41:14', 'Your booking No.584 has been declined. '),
(291, 567, 132, '2017-02-16 09:41:18', 'Your booking No.567 has been declined. '),
(292, 589, 116, '2017-02-16 10:15:49', 'Your booking No.589 has been approved. '),
(293, 590, 163, '2017-02-16 10:16:45', 'Your booking No.590 has been approved. '),
(294, 592, 116, '2017-02-16 10:26:43', 'Your booking No.592 has been approved. '),
(295, 593, 116, '2017-02-16 10:31:10', 'Your booking No.593 has been approved. '),
(296, 553, 132, '2017-02-16 13:22:30', 'Your booking No.553 has been declined. '),
(297, 603, 26, '2017-02-16 14:56:39', 'Your booking No.603 has been approved. '),
(298, 595, 26, '2017-02-17 18:42:26', 'Your booking No.595 has been approved. '),
(299, 597, 26, '2017-02-17 18:43:07', 'Your booking No.597 has been approved. '),
(300, 613, 105, '2017-02-17 18:55:02', 'Your booking No.613 has been approved. '),
(301, 616, 105, '2017-02-17 19:17:31', 'Your booking No.616 has been approved. '),
(302, 596, 26, '2017-02-19 10:09:46', 'Your booking No.596 has been declined. '),
(303, 598, 26, '2017-02-19 10:10:27', 'Your booking No.598 has been declined. '),
(304, 639, 172, '2017-02-23 14:31:33', 'Your booking No.639 has been declined. '),
(305, 637, 172, '2017-02-23 14:31:36', 'Your booking No.637 has been declined. '),
(306, 636, 12, '2017-02-23 14:31:38', 'Your booking No.636 has been declined. '),
(307, 634, 12, '2017-02-23 14:31:40', 'Your booking No.634 has been declined. '),
(308, 632, 12, '2017-02-23 14:31:42', 'Your booking No.632 has been declined. '),
(309, 631, 12, '2017-02-23 14:31:44', 'Your booking No.631 has been declined. '),
(310, 633, 12, '2017-02-23 14:31:47', 'Your booking No.633 has been declined. '),
(311, 426, 132, '2017-02-23 15:26:51', 'Your booking No.426 has been declined. '),
(312, 426, 132, '2017-02-23 15:27:26', 'Your booking No.426 has been approved. '),
(313, 621, 163, '2017-02-25 17:31:05', 'Your booking No.621 has been approved. '),
(314, 599, 26, '2017-02-25 17:31:19', 'Your booking No.599 has been declined. '),
(315, 647, 167, '2017-02-25 17:33:01', 'Your booking No.647 has been declined. '),
(316, 647, 167, '2017-02-25 17:33:05', 'Your booking No.647 has been approved. '),
(317, 650, 167, '2017-02-25 17:35:31', 'Your booking No.650 has been approved. '),
(318, 618, 26, '2017-02-25 17:39:14', 'Your booking No.618 has been declined. '),
(319, 619, 26, '2017-02-25 17:39:16', 'Your booking No.619 has been declined. '),
(320, 605, 163, '2017-02-25 17:39:19', 'Your booking No.605 has been declined. '),
(321, 604, 26, '2017-02-25 17:39:36', 'Your booking No.604 has been declined. '),
(322, 617, 26, '2017-02-25 17:39:39', 'Your booking No.617 has been declined. '),
(323, 638, 172, '2017-02-25 17:39:42', 'Your booking No.638 has been declined. '),
(324, 602, 26, '2017-02-25 17:39:46', 'Your booking No.602 has been declined. '),
(325, 654, 105, '2017-02-25 17:40:43', 'Your booking No.654 has been approved. '),
(326, 655, 167, '2017-02-25 17:42:36', 'Your booking No.655 has been approved. '),
(327, 658, 167, '2017-02-25 17:45:50', 'Your booking No.658 has been approved. '),
(328, 662, 132, '2017-02-25 18:11:47', 'Your booking No.662 has been approved. '),
(329, 665, 132, '2017-02-26 09:42:40', 'Your booking No.665 has been approved. '),
(330, 666, 179, '2017-02-26 09:49:47', 'Your booking No.666 has been approved. '),
(331, 426, 132, '2017-02-26 10:04:09', 'Your booking No.426 has been declined. '),
(332, 427, 132, '2017-02-26 10:04:12', 'Your booking No.427 has been declined. '),
(333, 432, 40, '2017-02-26 10:04:16', 'Your booking No.432 has been declined. '),
(334, 437, 132, '2017-02-26 10:04:19', 'Your booking No.437 has been declined. '),
(335, 438, 40, '2017-02-26 10:04:21', 'Your booking No.438 has been declined. '),
(336, 440, 132, '2017-02-26 10:04:23', 'Your booking No.440 has been declined. '),
(337, 442, 141, '2017-02-26 10:04:25', 'Your booking No.442 has been declined. '),
(338, 444, 141, '2017-02-26 10:04:27', 'Your booking No.444 has been declined. '),
(339, 448, 132, '2017-02-26 10:04:30', 'Your booking No.448 has been declined. '),
(340, 450, 132, '2017-02-26 10:04:32', 'Your booking No.450 has been declined. '),
(341, 454, 144, '2017-02-26 10:04:34', 'Your booking No.454 has been declined. '),
(342, 455, 144, '2017-02-26 10:04:36', 'Your booking No.455 has been declined. '),
(343, 456, 144, '2017-02-26 10:04:38', 'Your booking No.456 has been declined. '),
(344, 458, 144, '2017-02-26 10:04:40', 'Your booking No.458 has been declined. '),
(345, 459, 116, '2017-02-26 10:04:41', 'Your booking No.459 has been declined. '),
(346, 460, 116, '2017-02-26 10:04:43', 'Your booking No.460 has been declined. '),
(347, 462, 113, '2017-02-26 10:04:45', 'Your booking No.462 has been declined. '),
(348, 463, 52, '2017-02-26 10:04:47', 'Your booking No.463 has been declined. '),
(349, 676, 132, '2017-02-26 10:59:17', 'Your booking No.676 has been approved. '),
(350, 428, 132, '2017-02-26 11:04:32', 'Your booking No.428 has been declined. '),
(351, 683, 132, '2017-02-26 11:21:58', 'Your booking No.683 has been approved. '),
(352, 685, 181, '2017-02-26 11:22:23', 'Your booking No.685 has been declined. '),
(353, 686, 181, '2017-02-26 11:25:34', 'Your booking No.686 has been declined. '),
(354, 690, 181, '2017-02-26 11:39:28', 'Your booking No.690 has been approved. '),
(355, 681, 132, '2017-02-26 13:26:05', 'Your booking No.681 has been approved. '),
(356, 663, 132, '2017-02-26 13:28:38', 'Your booking No.663 has been approved. '),
(357, 670, 179, '2017-02-26 13:32:20', 'Your booking No.670 has been approved. '),
(358, 694, 179, '2017-02-26 13:33:25', 'Your booking No.694 has been approved. '),
(359, 694, 179, '2017-02-26 13:35:45', 'Your booking No.694 has been approved. '),
(360, 600, 26, '2017-02-27 17:40:42', 'Your booking No.600 has been declined. '),
(361, 659, 104, '2017-02-27 17:41:00', 'Your booking No.659 has been approved. '),
(362, 426, 132, '2017-03-11 23:50:05', 'Your booking No.426 has been cancelled. '),
(363, 433, 141, '2017-03-11 23:50:48', 'Your booking No.433 has been cancelled. '),
(364, 428, 132, '2017-03-11 23:51:14', 'Your booking No.428 has been cancelled. '),
(365, 428, 132, '2017-03-11 23:52:32', 'Your booking No.428 has been cancelled. '),
(366, 429, 132, '2017-03-11 23:52:53', 'Your booking No.429 has been cancelled. '),
(367, 429, 132, '2017-03-11 23:53:29', 'Your booking No.429 has been cancelled. '),
(368, 431, 141, '2017-03-11 23:55:29', 'Your booking No.431 has been cancelled. '),
(369, 431, 141, '2017-03-12 00:17:38', '0'),
(370, 431, 141, '2017-03-12 00:18:07', 'Booking canceled upon your request. Hope to see you again.'),
(371, 431, 141, '2017-03-12 00:18:40', 'Booking canceled upon your request. Hope to see you again.'),
(372, 431, 141, '2017-03-12 00:18:59', '0'),
(373, 431, 141, '2017-03-12 00:19:15', 'hey'),
(374, 431, 141, '2017-03-12 00:19:44', 'Booking canceled upon your request. Hope to see you again.'),
(375, 431, 141, '2017-03-12 00:20:00', 'hey'),
(376, 431, 141, '2017-03-12 00:23:44', 'hey'),
(377, 431, 141, '2017-03-12 00:23:50', 'Your booking has been canceled. Sorry for the inconvienve but the field is undergoing maintenance.');

-- --------------------------------------------------------

--
-- Table structure for table `player`
--

CREATE TABLE `player` (
  `player_id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `profile_picture` text,
  `address` varchar(45) DEFAULT NULL,
  `server_id` varchar(50) NOT NULL,
  `os` varchar(20) NOT NULL DEFAULT 'android',
  `token` varchar(300) NOT NULL,
  `verification_code` varchar(45) DEFAULT NULL,
  `active` int(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `player`
--

INSERT INTO `player` (`player_id`, `name`, `phone`, `email`, `profile_picture`, `address`, `server_id`, `os`, `token`, `verification_code`, `active`) VALUES
(1, ' امل', '0936110314', '', '', '2', '', 'ios', 'cEVGwnQ8f58:APA91bHlQpPiG4BgN10hRiGAPB_IXY7GgdQir1KJcCSAfkVUka-2pSJ8C9lXT0kxXH4y6_N6y-vftOJBi3aXnVgdngqmsR6YV_2yoty924u_acaHPX4xNYfT-43JdjDLYHcVr7GpwcGG', '939378', 0),
(2, 'amal', '0321456987', '0', '', '2', '123123', 'android', '', NULL, 1),
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
(15, 'g', '56565', NULL, NULL, '', '', 'android', '', '250778', 0),
(16, 'ttt', '2552', NULL, NULL, '', '', 'android', '', NULL, 0),
(17, 'lara', '555', NULL, NULL, '', 'cc6aa7601bae71f78f057d44d9f5844d', 'ios', '', '294768', 0),
(18, 'rabii', '095282141', NULL, NULL, '', '', 'android', '', NULL, 0),
(19, 'Amal', '0936110314', NULL, NULL, NULL, 'c985d6f3965c56afdb592b0d866bbaf7', 'android', '', '020994', 1),
(20, 'Yahya', '994729458', 'yahia@gmail.com', '', 'DAMASCUS', '', 'ios', 'cEVGwnQ8f58:APA91bGzMJfiQ73FBAkGVOaVmfBpbYlf5-SCdxbaPr-5BbEtaKpxe51Vs82KACXRa_ZDFI5mAv_jhU2k5EBAPHs1yoledtousnG-3JhfeXBPIo3cw7-xWxV9VmzTka2qIZfN5auefWkN', '707529', 0),
(21, 'nc', '88', NULL, NULL, NULL, '', 'android', '', '219424', 0),
(22, NULL, 'd', NULL, NULL, NULL, '44449dd03c895899d6fc3f3bd72208d0', 'ios', '', '709278', 0),
(23, 'amal', '333', NULL, NULL, NULL, '7865f1e54a87f65c7e07c720dfba2357', 'ios', '', '123999', 0),
(24, NULL, '00987654321', NULL, NULL, NULL, '2cd86c0cbb5a18720d545e2c870903cc', 'android', '', '796626', 0),
(25, 'test', '123', NULL, NULL, NULL, '', 'android', 'dPtOI9ncdLA:APA91bHuRn_-ONex7rikFD7KZdogEh416QQwLGV15_WAPMb_YnXmqtYLl73GLMAqkL6_JOzvM248CgVJnZaJDAkPuOEl6y4DwL-7A_HtpVdvM38VE9fbrXp3BOnQEmgJ6xhnjAzFLxHh', '139392', 0),
(26, 'bas', '934610552', '', '', '', '2219b3d16e7577ae26fb862b03176614', 'android', 'fwJ74CYZ5Vc:APA91bGZwSc2ISyszb10b3CznXyd2VYeHWjUY3T_zJ677I9DobDw9RR5lfgBeqtwGSqj4QkbxXL5u1pWapD9clII_eJNXoeDTjeMw2blwuu3rvsTqeJ4FsYHm9Do0RMrN1fx0fKJwYR3', '250699', 1),
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
(40, 'yy', '222', '', '', '', '98b7a2d1e8fac9382a14271a1302ce06', 'ios', 'f74Bza4MX1s:APA91bHnnI34Sh9EojPElxX6_Z9IXA-teuC9HowIqxVqRERBX-bSPYXp6MM_-GSHUGaE6MPFCryqHTOoU9fkdF7ArKxR5NB4Tsx17O00vKr7ZL9UOMSpjVe7hCdbHEwQnPoGkeGCdOyi', '964125', 1),
(41, 'hhgf', '225', NULL, NULL, NULL, '2221f5da8fbaaab27d02dda6ea5b32c0', 'ios', '', '105755', 0),
(42, 'yygg', '332', NULL, NULL, NULL, '9c9179973f346f69a42c07494a0f6240', 'ios', '', '075551', 0),
(43, 'yahya', '99472', NULL, NULL, NULL, '2938cfc1c0bf62def22d8f28235b422b', 'ios', '', '003349', 0),
(44, 'gggg', '588', NULL, NULL, NULL, '64f653e373cb24d3efee42d551c0a280', 'ios', '', '945402', 0),
(45, 'ggg', '888', NULL, NULL, NULL, '36fa1211e5b60d9dc6b21725226d4648', 'ios', '', '268793', 0),
(46, 'bbdb', '5754', NULL, NULL, NULL, 'fec297417671699158e4642cf8b6dc58', 'ios', '', '707633', 0),
(47, 'ggg', '558', NULL, NULL, NULL, '36e74e7260ad45303191f1ee39efd6fa', 'ios', '', '831210', 0),
(48, 'yaha', '55', NULL, NULL, NULL, '80e05175f40f31f610bd26e84ac63367', 'ios', '', '434353', 0),
(49, 'dfs', '1234567', NULL, NULL, NULL, 'b9f8956d47f974f08918d4e1325e2da6', 'android', '', '087502', 0),
(50, 'test', '2', '', '', '', '', 'ios', 'eiN6-8mDhVk:APA91bEoC9XOAIIk8NAoV6f60TwvVfpZE1fP4oniVBb_NkYBBI_fwUeXw-bHyBbxeI_L_WYQV_FYVXNBqLD5TE477e7AgaArucfa3DrQpzgfUrN7s21lZDal20-wnHPsAffKaRDj08UO', '845607', 0),
(51, 'vvg', '258', NULL, NULL, NULL, '744773826532713fef3294f7607f9b2b', 'ios', '', '749941', 0),
(52, '22', '22', 'yahya@gmail.com', '', 'DUBAI', '0ce552a5e4bcedc27978dd111fa7ff31', 'ios', 'fRREG5o58EM:APA91bE-HbjYuodi9vQLp2oAZ2QxJ0U8XGx_2tsFZ8eU6sycGhxGHkfUNhJ3WBAM_qU2oXe5RwNVRoqlVR50Z-vO4Rlcbk00eVuVPMHzCEbAelEnr0GtpcCrGrhZFr-6XGm6X1s9ISy-', '167604', 1),
(53, 'uu', '00', NULL, NULL, NULL, 'b2ad63f3a33f8b2d9d83e8c1037b1567', 'ios', '', '967356', 0),
(54, 'ff', '885', NULL, NULL, NULL, '12d149c87d5fc5423d88f0bf80fdc5eb', 'ios', '', '950507', 0),
(55, 'اتته', '٥٥٨', NULL, NULL, NULL, '9ba8cce1c036452f3f4428a1ef3cdbf0', 'ios', '', '380443', 0),
(56, 'rabiii', '0958282814', NULL, NULL, '', '', 'android', '', NULL, 0),
(57, 'amal', '0936532568', NULL, NULL, '', '', 'android', '', NULL, 0),
(58, 'hamdii', '095863221', NULL, NULL, '', '', 'android', '', NULL, 0),
(59, 'sameer', '44532267', NULL, NULL, '', '', 'android', '', NULL, 0),
(60, 'y', '99', NULL, NULL, NULL, 'fdc0c871d1f29cc017a5bfa3e739fb49', 'ios', '', '513359', 1),
(61, 'amal', '506356563', NULL, NULL, NULL, 'e7c87d9b31552879d30141c7b5c074bb', 'android', '', '559930', 0),
(62, 'moz', '1231567', NULL, NULL, NULL, '98aaa78c4d9d495e0c707385c296899b', 'ios', '', '567746', 0),
(63, 'g', '7777', NULL, NULL, NULL, '', 'android', '', '500989', 0),
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
(77, 'gt', '455223', NULL, NULL, '', '', 'android', '', NULL, 0),
(78, 'yahya', '994729488', NULL, NULL, NULL, '3fd05db0fec74ea9d6754232f7af204c', 'ios', '', '853636', 1),
(79, 'yy', '1234', NULL, NULL, NULL, '5bf66792b9506594ebb33ff3f054200b', 'ios', '', '398310', 1),
(80, 'yy', '12345', NULL, NULL, NULL, 'a24bca5ea7cccd6648765ead8df289c2', 'ios', '', '104582', 1),
(81, 'Rabii', '42536888', NULL, NULL, '', '', 'android', '', NULL, 0),
(82, 'hamdii', '55523', NULL, NULL, '', '', 'android', '', NULL, 0),
(83, 'aa', '455', NULL, NULL, '', '', 'android', '', NULL, 0),
(84, 'amal', '45556663', NULL, NULL, '', '', 'android', '', NULL, 0),
(85, 'player', '12258', NULL, NULL, '', '', 'android', '', NULL, 0),
(86, 'player', '528639741', NULL, NULL, '', '', 'android', '', NULL, 0),
(87, 'player', '55899', NULL, NULL, '', '', 'android', '', NULL, 0),
(88, 'player2', '3225588865', NULL, NULL, '', '', 'android', '', NULL, 0),
(89, 'Rabii', '21453687', NULL, NULL, '', '', 'android', '', NULL, 0),
(90, 'rabii', '175288399', NULL, NULL, '', '', 'android', '', NULL, 0),
(91, 'hussam', '147822699', NULL, NULL, '', '', 'android', '', NULL, 0),
(92, 'yahya', '999', '', '', 'DAMAS', '75920aed16c61971bf799850733d854e', 'ios', '', '806617', 1),
(93, 'Yahya', '9947294580', NULL, NULL, NULL, '991be9b2d4b8566431af896627e933bd', 'ios', 'cbSJ3ZKGpUs:APA91bGH68WcB3j_1-gQgVTSqPDdUm68M44R4uSw6MsD00Daj-V6N_IFVOtVXxd_g6xE900Wynz3kpjzCeUHBr0qU34iGv-xXG0Jsf2zlRrriPovi4vGPUj24vgziOJ-wTI7lkzwA-JU', '335707', 1),
(94, 'Yahya', '99472945800', NULL, NULL, NULL, 'a1f6523776e1a330aa5acccda94a2476', 'ios', '', '217604', 1),
(95, 'Yahya', '99472945899', NULL, NULL, NULL, '29a18baa39e1fc5fb2d2c732ca40be90', 'ios', '', '208037', 1),
(96, 'Yahya', '9947294582', NULL, NULL, NULL, '8418837ab684566283d9d1ff00e62823', 'ios', '', '083951', 1),
(97, 'mo', '٥٠٦٣٥٦٥٦٣', NULL, NULL, NULL, 'e201d6e7d9c2dd74490adad2b1b4fee1', 'ios', '', '819758', 0),
(98, 'basma', '9346', NULL, NULL, NULL, '1ec50ecab12be3aa3acddf007945b506', 'android', '', '124652', 1),
(99, 'Yahyattty', '994729455', 'HHdd@hhh.hh', 'Yahyattty.png', 'DUBAI', '4f734a2417f587c6d82b19d8507f6ab7', 'ios', 'd1NmFYLW_6s:APA91bHECGpZB7EutyMQdWBKYjnCTv0RQDwdTchu8W9kI0xnXXFpYKX0L1ekveu_CHxcKdOjZDksYN5-X0r9CphaTUoPdv_kDwBXS7YUAPj3tviPu3x-S8pm9WpIO3SXx_HISFOw-o3o', '533371', 1),
(100, 'YAH', '994729466', NULL, NULL, NULL, '', 'ios', '', '686684', 1),
(101, 'dvj', '12', 'yah@df.com', 'YAH1.png', '', '', 'android', '', '212559', 0),
(102, 'test', '9', '', 'image23.jpeg', '', '1278ad91bb0f34007bef96c3798c4ee6', 'android', 'cF6QU6ZkHAQ:APA91bHxkIy064q1adUfnRtnIQgLyXRU8vSzS0t_VTqjlmET6QOLHXlq6gxDj7IzJbZzkh9uno3fh5hKdxUS0e2HZNEdLZiH4Q8U40bpBu8t9uGwCIdbh71diVA_p5T0GJMwq31Ha218', '054723', 1),
(103, 'Yahya', '11', 'yahyatabba@gmail.com', '', '', '', 'ios', '', '250831', 0),
(104, 'zakaria khiami', '502799496', NULL, NULL, NULL, '8fd0e9a6622ef87f5e6e603821e32a7f', 'ios', 'fFZcYAGmo6U:APA91bEvf8qv1goDWDE1UgxQyWeTiUaLHf4Ocy3ytdMvUNskfJB8xSKwaqsfTYWQWfM7vFJX_8qJzBWpm86rndVv4G0nNFgKThzwwxDHRrwd1xq0GnM5TIlkRWY03ZoZ-a1TW5vWb6wt', '036353', 1),
(105, 'zak', '00971502799496', 'zak8686@hotmail.com', '', 'Dubai', '41f0e954eb470a9b4aec6a1c77be8278', 'ios', 'eTbqnpXLElQ:APA91bEphStmDbkWMvVFUSEIyBn_onAAYLuOo8dYToj5ddJkqF8sX0iMVGtJTOQF3ygSRMW2uL9hIr0CU41T0ejJY06yZmrNuzYCjLxWDfeTNfD8xJV7NyUSl9bG8TBmC30ZlDJZaqXy', '147381', 1),
(106, 'amal', '89654123', NULL, NULL, '', '', 'android', '', NULL, 0),
(107, 'gab', '21348', 'yahya@gg.com', 'gab.png', 'Dubai', 'a60f1e4cc31e44d79b6ac1fb1987438e', 'ios', '', '045565', 1),
(108, 'tala', '4569878', NULL, NULL, '', '', 'android', '', NULL, 0),
(109, 'g', '654789', NULL, NULL, '', '', 'android', '', NULL, 0),
(110, 'rtytr', '456987', NULL, NULL, '', '', 'android', '', NULL, 0),
(111, 'meow', '0507068995', NULL, NULL, NULL, '8c5eba23e05342a334cb12a873de584f', 'ios', '', '675402', 0),
(112, 'meow', '507068995', NULL, NULL, NULL, 'c1cdbd595f3b87fed3383e7a99acf35f', 'ios', '', '149146', 0),
(113, 'yy', '1', NULL, NULL, NULL, '', 'ios', 'eiN6-8mDhVk:APA91bEoC9XOAIIk8NAoV6f60TwvVfpZE1fP4oniVBb_NkYBBI_fwUeXw-bHyBbxeI_L_WYQV_FYVXNBqLD5TE477e7AgaArucfa3DrQpzgfUrN7s21lZDal20-wnHPsAffKaRDj08UO', '343634', 0),
(114, 'amal', '456789887', NULL, NULL, '', '', 'android', '', NULL, 0),
(115, '933445566', '23654478990', NULL, NULL, NULL, 'f769d2d13e83f4207ad3dd33bbbf00f0', 'android', '', '193515', 1),
(116, 'basma', '9933', '', '', 'test ', 'f8f552c50da2a636ff1e304bec582e30', 'android', 'dnGoBnNoPwA:APA91bFVE-EMlhFwb6S5ivS9k2U_gnxnDruvGUIN3Gxlv8sPJuxEy6hLcYlJwDynSuBelno5Wvv6hAT-3s9rkQR7dnIEw6do402s1eaMFzC3_FvbuzaTctj51lKGuKIbFogGQozLNkbK', '115334', 1),
(117, 'amal', '1234567800700', NULL, NULL, NULL, '4ee687b4ce625dd01e49cfeb3336bfbf', 'android', '', '567826', 1),
(118, 'new user', '556897', NULL, NULL, NULL, 'd64a2b47116ca636c6e480a653300ff4', 'ios', '', '203466', 0),
(119, 'new', '5688996', NULL, NULL, NULL, 'ddd921abe310841762f116b84aeca58f', 'ios', '', '378604', 0),
(120, 'amal', '222333111', 'amal@gmail.com', 'amal.png', 'damas', '107424c7966bccc1e8f5b56b6c5755ee', 'ios', '', '999128', 1),
(121, 'amal', '000666333', 'gave@gmaim.com', '', '', '', 'android', 'evTb_iJMVaQ:APA91bGTRTgNbCGfmfW_KURfUYABKsYT6o7CjfK5OJNOJ9AfIbdvbcKQzlvmyrXyobWMwc6jWORPRLLYqHUi07UP2XuyaBYVqq5FK5cS8RvswGbOSKni8_WieZ1MvXCc1D8p9Qb4UaiD', '117724', 0),
(122, 'y', '006', NULL, NULL, NULL, '', 'android', '', '508183', 0),
(123, 'y', '5555', NULL, NULL, NULL, '', 'android', '', '030251', 0),
(124, 'g', '789789', '', '', '', '', 'android', 'evTb_iJMVaQ:APA91bGTRTgNbCGfmfW_KURfUYABKsYT6o7CjfK5OJNOJ9AfIbdvbcKQzlvmyrXyobWMwc6jWORPRLLYqHUi07UP2XuyaBYVqq5FK5cS8RvswGbOSKni8_WieZ1MvXCc1D8p9Qb4UaiD', '787450', 0),
(125, 'gh', '23', NULL, NULL, NULL, '', 'android', '', '264038', 0),
(126, 'amal', '123123', NULL, NULL, NULL, '', 'android', 'cirLVGu8mws:APA91bFsHmZNSmwOYZvey-MNaVZbUopnBuWnwIZ8hyL8Oh1kcqOp_RNMKkLcwHmHY3uWqdeX84rAWrR4ax53--scI_kKZH86dLIRKpwMF0otNIPUJyNstx9wad2oPl0g2gdnOBtMt6e1', '981235', 0),
(127, 'ba', '9966', NULL, NULL, NULL, '', 'android', 'evTb_iJMVaQ:APA91bGTRTgNbCGfmfW_KURfUYABKsYT6o7CjfK5OJNOJ9AfIbdvbcKQzlvmyrXyobWMwc6jWORPRLLYqHUi07UP2XuyaBYVqq5FK5cS8RvswGbOSKni8_WieZ1MvXCc1D8p9Qb4UaiD', '819912', 0),
(128, 'amal', '654478', NULL, NULL, '', '', 'android', '', NULL, 0),
(129, 'bb', '456', NULL, NULL, '', '', 'android', '', NULL, 0),
(130, 'lara', '8888', 'amal@gmail.com', 'lara.png', '', 'c7d0c89037de992770e584aff31fbd0e', 'ios', 'eiN6-8mDhVk:APA91bEoC9XOAIIk8NAoV6f60TwvVfpZE1fP4oniVBb_NkYBBI_fwUeXw-bHyBbxeI_L_WYQV_FYVXNBqLD5TE477e7AgaArucfa3DrQpzgfUrN7s21lZDal20-wnHPsAffKaRDj08UO', '100191', 1),
(131, 'new1212', '1212', 'yahya@gg.com', '', '', 'd34a67045515fc63698ca470d3b77e1e', 'ios', 'dAAsJYJoTio:APA91bEwpZojlxvf8EA_jAaHyirQX9ORzSt79Dli24b86rjbBeAjm5RwRwZxs8oAb3DDBRxK5frBxbEmAH_ThwULael5u_QQhG1n2-gbCAeClwyGcmUPSrq3Wo2ESZuHwDPsLHnz5k1R', '208650', 1),
(132, 'amal', '936110314', 'amal@gmail.com', 'fieldium_home_115.jpg', 'jarmana', '617a14dd5b197beaa3c4cbc89a9fe722', 'android', 'dvzumLfF4J4:APA91bHoLnrUYWbl2VIfP1T9Dc-Yfb36J2b9pUbbKNoWw7MSw-YccFDAMRN3CyF1Tnm6U245IqkxC5092iuTIrbsOU3Y8WFyxt1IaG_eAcjYD6QKt0JpYhmIeTG5mGjVNDTezpkRx4nJ', '756199', 1),
(133, 'fieldium', '6543129870', '0', 'upload10.png', '2', 'aee5ea1605193e769d332723a8598c29', 'ios', 'eiN6-8mDhVk:APA91bEoC9XOAIIk8NAoV6f60TwvVfpZE1fP4oniVBb_NkYBBI_fwUeXw-bHyBbxeI_L_WYQV_FYVXNBqLD5TE477e7AgaArucfa3DrQpzgfUrN7s21lZDal20-wnHPsAffKaRDj08UO', '161090', 1),
(134, 'g', '32', NULL, NULL, NULL, '', 'android', '', '355467', 0),
(135, 'f', '25', NULL, NULL, NULL, '1dd77d8e3ecc869e98496c9f346bb1c4', 'android', 'evTb_iJMVaQ:APA91bGTRTgNbCGfmfW_KURfUYABKsYT6o7CjfK5OJNOJ9AfIbdvbcKQzlvmyrXyobWMwc6jWORPRLLYqHUi07UP2XuyaBYVqq5FK5cS8RvswGbOSKni8_WieZ1MvXCc1D8p9Qb4UaiD', '673777', 1),
(136, 'amal', '0396110314', NULL, NULL, NULL, '4993d543c48536320e88c0e47f5df9a2', 'ios', 'eiN6-8mDhVk:APA91bEoC9XOAIIk8NAoV6f60TwvVfpZE1fP4oniVBb_NkYBBI_fwUeXw-bHyBbxeI_L_WYQV_FYVXNBqLD5TE477e7AgaArucfa3DrQpzgfUrN7s21lZDal20-wnHPsAffKaRDj08UO', '177037', 1),
(137, 'malek', '944687774', NULL, NULL, NULL, '8115c76108613eebed212196bf8ff210', 'android', 'fgxepQQS6ro:APA91bH-R91DeKFdnRLbA8EUNX8SZVtUzX52PjD5lEOoa7htKXBkGRxASfNkYvgZfhZzB4iMFamEfdvGQt6JTZMX-po-ERtv1yNmOCyIE0bvGJFq0VD0IZyzbc-aPdjVWA8KIrzQHG0q', '660959', 1),
(138, 'yy33', '33', NULL, NULL, NULL, '', 'ios', 'f74Bza4MX1s:APA91bHnnI34Sh9EojPElxX6_Z9IXA-teuC9HowIqxVqRERBX-bSPYXp6MM_-GSHUGaE6MPFCryqHTOoU9fkdF7ArKxR5NB4Tsx17O00vKr7ZL9UOMSpjVe7hCdbHEwQnPoGkeGCdOyi', '400797', 0),
(139, 'gg', '2222', NULL, NULL, NULL, '', 'ios', 'eiN6-8mDhVk:APA91bEoC9XOAIIk8NAoV6f60TwvVfpZE1fP4oniVBb_NkYBBI_fwUeXw-bHyBbxeI_L_WYQV_FYVXNBqLD5TE477e7AgaArucfa3DrQpzgfUrN7s21lZDal20-wnHPsAffKaRDj08UO', '985417', 0),
(140, 'bbb', '18', NULL, NULL, NULL, 'cf3bdd889137df4b4077f643085b3302', 'ios', '', '965068', 0),
(141, 'Rabii Desouki', '95820814', '', '', '', '', 'ios', 'eiN6-8mDhVk:APA91bEoC9XOAIIk8NAoV6f60TwvVfpZE1fP4oniVBb_NkYBBI_fwUeXw-bHyBbxeI_L_WYQV_FYVXNBqLD5TE477e7AgaArucfa3DrQpzgfUrN7s21lZDal20-wnHPsAffKaRDj08UO', '870472', 0),
(142, 'basma', '0934610552', NULL, NULL, '', '', 'android', '', NULL, 0),
(143, 'test', '2525', NULL, NULL, '', '', 'android', '', NULL, 0),
(144, 'basma', '0976458123', NULL, NULL, NULL, '', 'android', 'dPtOI9ncdLA:APA91bHuRn_-ONex7rikFD7KZdogEh416QQwLGV15_WAPMb_YnXmqtYLl73GLMAqkL6_JOzvM248CgVJnZaJDAkPuOEl6y4DwL-7A_HtpVdvM38VE9fbrXp3BOnQEmgJ6xhnjAzFLxHh', '887041', 0),
(145, 'test', '9936', NULL, NULL, NULL, '', 'ios', 'eiN6-8mDhVk:APA91bEoC9XOAIIk8NAoV6f60TwvVfpZE1fP4oniVBb_NkYBBI_fwUeXw-bHyBbxeI_L_WYQV_FYVXNBqLD5TE477e7AgaArucfa3DrQpzgfUrN7s21lZDal20-wnHPsAffKaRDj08UO', '553231', 0),
(146, 'test', '963', NULL, NULL, NULL, '', 'ios', 'eiN6-8mDhVk:APA91bEoC9XOAIIk8NAoV6f60TwvVfpZE1fP4oniVBb_NkYBBI_fwUeXw-bHyBbxeI_L_WYQV_FYVXNBqLD5TE477e7AgaArucfa3DrQpzgfUrN7s21lZDal20-wnHPsAffKaRDj08UO', '868169', 0),
(147, 'test', '993', '', '', '', '45fad075e0735070bd8ac2fbc42c5e22', 'android', '', '903085', 1),
(148, 'moz', '0506356563', NULL, NULL, '', '', 'android', '', NULL, 0),
(149, 'test', '9999', '', '', '', '77a980c5a98e163fc0a18a17d60751a9', 'ios', 'eiN6-8mDhVk:APA91bEoC9XOAIIk8NAoV6f60TwvVfpZE1fP4oniVBb_NkYBBI_fwUeXw-bHyBbxeI_L_WYQV_FYVXNBqLD5TE477e7AgaArucfa3DrQpzgfUrN7s21lZDal20-wnHPsAffKaRDj08UO', '226109', 1),
(150, 'بسمة سرور', '8585', '', 'image49.jpg', '', '', 'android', '', '924396', 0),
(151, 'test', '9898', '', '', 'عنوان', '', 'ios', 'cEVGwnQ8f58:APA91bGzMJfiQ73FBAkGVOaVmfBpbYlf5-SCdxbaPr-5BbEtaKpxe51Vs82KACXRa_ZDFI5mAv_jhU2k5EBAPHs1yoledtousnG-3JhfeXBPIo3cw7-xWxV9VmzTka2qIZfN5auefWkN', '914065', 0),
(152, 'yy', '111', '', '', '', '', 'ios', '', '044309', 0),
(153, 'amal', '03654789', NULL, NULL, '', '', 'android', '', NULL, 0),
(154, 'aaa', '564', NULL, NULL, '', '', 'android', '', NULL, 0),
(155, 'Hussain', '0564887568', NULL, NULL, '', '', 'android', '', NULL, 0),
(156, 'Adam ', '0529066017', NULL, NULL, '', '', 'android', '', NULL, 0),
(157, 'Michael ', '0566681365', NULL, NULL, '', '', 'android', '', NULL, 0),
(158, 'ahmad  kutet', '097155787451', NULL, NULL, '', '', 'android', '', NULL, 0),
(159, 'saees al shamri', '00971546328', NULL, NULL, '', '', 'android', '', NULL, 0),
(160, 'بسمة', '963963', '', 'null', '', '', 'android', '', '556761', 0),
(161, 'yahya', '111111111', '', '', '', '28296a1dbfae040571f0b3b27255c88a', 'ios', '', '770197', 1),
(162, 'bas', '996608080', NULL, NULL, NULL, '62fdd5c1968794eacc0ebdaf5a445484', 'ios', '', '350415', 0),
(163, 'Rabii', '555555555', '', 'Rabii2.png', '', '', 'ios', 'cEVGwnQ8f58:APA91bGzMJfiQ73FBAkGVOaVmfBpbYlf5-SCdxbaPr-5BbEtaKpxe51Vs82KACXRa_ZDFI5mAv_jhU2k5EBAPHs1yoledtousnG-3JhfeXBPIo3cw7-xWxV9VmzTka2qIZfN5auefWkN', '048528', 0),
(164, 'YY', '111111112', '', '', '', '', 'ios', '', '078130', 0),
(165, 'aka ', '0502799499', NULL, NULL, '', '', 'android', '', NULL, 0),
(166, 'zakaria', '0502799496', NULL, NULL, '', '', 'android', '', NULL, 0),
(167, 'Hussain Badri', '564887568', NULL, NULL, NULL, 'bddae6d7df36726ba9305babff7c2f44', 'ios', 'evtbkn6AIeY:APA91bFXEjdS-HTMlgxQJ0NJr7GdlbrlCiR-9gIF78mH7xKy0LAFN2d57PbHdqb8-9P1OKHkoJt-LRIxKgcwpvLktHGMGMZ5vYdF1c249pi7VZrI5HgEMvIbfh5BZDY7-bw7hQdDRETf', '955796', 1),
(168, 'zzzz', '0585886588', NULL, NULL, '', '', 'android', '', NULL, 0),
(169, 'cd', '85857576', NULL, NULL, '', '', 'android', '', NULL, 0),
(170, 'hg', '0587584558', NULL, NULL, '', '', 'android', '', NULL, 0),
(171, 'zak', '050279589', NULL, NULL, '', '', 'android', '', NULL, 1),
(172, 'someone2', '123456780', '', 'someone2.png', '', '0fdd449eec8866b5c792c44191f4ffec', 'ios', 'cYib0mTEZg4:APA91bFf4EjAt1e81HSoBqGaewf8lncTNipQae1ufIBhwrDeQcyfT8Pf_ZxpI2nfo4wYm62SODKrENnuPqs_izpHy0L3_BVYDnsERX-wTW-tD8lia5YV4xG_DfuMnjF5_5wIXLinycjB', '671235', 1),
(173, 'amal', '222222222', '', 'yy7.png', '', '9d51efa88368fab5e12eed25cef47c55', 'ios', 'cEVGwnQ8f58:APA91bGzMJfiQ73FBAkGVOaVmfBpbYlf5-SCdxbaPr-5BbEtaKpxe51Vs82KACXRa_ZDFI5mAv_jhU2k5EBAPHs1yoledtousnG-3JhfeXBPIo3cw7-xWxV9VmzTka2qIZfN5auefWkN', '282725', 1),
(174, 'yy', '444444444', NULL, NULL, NULL, '', 'ios', '', '611202', 0),
(175, 'amal', '23424', NULL, NULL, NULL, '30eef1131865dc6ddb835641404585d3', 'android', '', '731714', 0),
(176, 'mark ', '0554959677', NULL, NULL, '', '', 'android', '', NULL, 0),
(177, 'someone', '123456789', NULL, NULL, NULL, 'd220d396e99486b6f8d832289eb751bb', 'ios', 'cNELFYNKIQA:APA91bGYWEG04RFPjr2wEpm1x469T7FfDu9T2Zxf1XsmKPFtZI4Xhyo_fsqe_R2LvLFQZLWNO7QgqgBc9AEFDMEqw5_2Q-RuayR8VtDEkRFll7GNEc2lf-YZ21BnHfNQizAwYfgd5UF8', '751093', 1),
(178, 'Khaled', '055685968', NULL, NULL, '', '', 'android', '', NULL, 0),
(179, 'amal', '123698745', '', '', '', '', 'ios', 'cyquZFJA0zs:APA91bElM2TPwN2ZaA28JGCDwPTVsPBAZxUhrkyeVlcHcz9tjI5UW1np9yAV8iTdplw6XEB_9CEpGGoui6MuPESICgGKEm-Y-AAlVgwviXXFWWHPtPg24ya3wPtma0jhAg1X2-T-yfCg', '363762', 0),
(180, 'yy', '112233445', NULL, NULL, NULL, '4c78ee1edff0b5d937ed05d479965cdd', 'ios', '', '651059', 0),
(181, 'rabii', '958202814', NULL, NULL, NULL, 'fad560eb1f6db2ce01f47151ad93fc7b', 'android', 'dezJ0S00GBc:APA91bGndhIEoW60Z_sBOFNJ30dNs-sBSp4bJwgFF-wnFB9yn8GklvsrqgF5QsBZlHdcUVcGwvnq0r2AfZAQZD0wCwOicfJynLnkBPqramgmU7RIbokP6V-oYqXhP9wdbz4UBOCv7Joo', '865072', 1),
(182, 'aml', '999966663', NULL, NULL, NULL, 'e9b4716b153c64bda74ab0fbade22268', 'android', 'dkxJLddJnzE:APA91bG_eR5AZNY_wzEpdKdMvblp_lP0msXFyczHUGa9ODCM2D8TgTavEu3Na0ocPVzVre2_S0YCMk9AaXkJk5tPIh9X9S8zSkzaDFQI9bBKuCoiXv9JzeOC9mTdXD_vzvPsicBa0Gxp', '061132', 1);

-- --------------------------------------------------------

--
-- Table structure for table `prefered_game`
--

CREATE TABLE `prefered_game` (
  `prefered_game_id` int(11) NOT NULL,
  `player_id` int(11) DEFAULT NULL,
  `game_type_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `prefered_game`
--

INSERT INTO `prefered_game` (`prefered_game_id`, `player_id`, `game_type_id`) VALUES
(36, 92, 2),
(84, 101, 1),
(102, 99, 1),
(113, 120, 1),
(114, 120, 2),
(182, 130, 1),
(212, NULL, 2),
(215, 133, 2),
(247, 102, 1),
(259, 40, 2),
(319, 147, 1),
(320, 147, 2),
(321, 147, 2),
(382, 20, 1),
(383, 20, 2),
(425, 1, 2),
(440, 152, 2),
(482, 103, 1),
(483, 103, 2),
(484, 52, 1),
(487, 151, 1),
(488, 151, 2),
(489, 116, 2),
(491, 164, 1),
(494, 161, 1),
(497, 2, 2),
(500, 132, 1),
(501, 132, 2),
(502, 179, 1),
(503, 179, 2),
(506, 26, 1);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE `review` (
  `review_id` int(11) NOT NULL,
  `field_id` int(11) DEFAULT NULL,
  `player_id` int(11) DEFAULT NULL,
  `text` text,
  `emoji` int(11) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE `role` (
  `role_id` int(11) NOT NULL,
  `en_name` varchar(45) DEFAULT NULL,
  `ar_name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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

CREATE TABLE `search` (
  `search_record_id` int(11) NOT NULL,
  `player_id` int(11) DEFAULT NULL,
  `token` varchar(300) NOT NULL,
  `area_id` int(11) DEFAULT NULL,
  `game_type_id` int(11) DEFAULT NULL,
  `start` time DEFAULT NULL,
  `date` date DEFAULT NULL,
  `duration` time DEFAULT NULL,
  `search_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `text` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
(165, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-03 15:54:08', ''),
(166, NULL, '', 2, 2, NULL, NULL, NULL, '2017-01-04 00:26:16', ''),
(167, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-04 00:26:20', ''),
(168, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-04 00:26:43', 'gg'),
(169, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '06:00:00', '2017-01-04', '00:00:04', '2017-01-04 09:25:11', ''),
(170, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '08:00:00', '2017-01-04', '00:00:04', '2017-01-04 09:49:55', ''),
(171, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '10:00:00', '2017-01-04', '00:00:02', '2017-01-04 09:59:21', ''),
(172, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-04 10:00:07', 'h'),
(173, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-04 10:00:11', 'hh'),
(174, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '05:00:00', '2017-01-04', '00:00:01', '2017-01-04 11:08:56', ''),
(175, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-04', '00:00:06', '2017-01-04 11:19:36', ''),
(176, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-04', '00:00:03', '2017-01-04 11:56:09', ''),
(177, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '06:00:00', '2017-01-17', '00:00:03', '2017-01-04 12:26:57', ''),
(178, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-04 12:32:31', ''),
(179, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-04 12:34:13', ''),
(180, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-04 12:34:46', ''),
(181, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-12', '00:00:01', '2017-01-04 12:35:02', ''),
(182, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 1, '01:00:00', '2017-01-12', '00:00:01', '2017-01-04 12:35:06', ''),
(183, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 2, '01:00:00', '2017-01-12', '00:00:01', '2017-01-04 12:35:16', ''),
(184, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 2, '01:00:00', '2017-01-12', '00:00:01', '2017-01-04 12:35:19', ''),
(185, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 2, '01:00:00', '2017-01-12', '00:00:01', '2017-01-04 12:35:27', ''),
(186, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 2, '01:00:00', '2017-01-04', '00:00:02', '2017-01-04 12:35:32', ''),
(187, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 2, '01:00:00', '2017-01-04', '00:00:02', '2017-01-04 12:35:42', ''),
(188, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-04 12:35:48', ''),
(189, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-04', '00:00:01', '2017-01-04 12:35:55', ''),
(190, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 1, '01:00:00', '2017-01-04', '00:00:01', '2017-01-04 12:36:34', ''),
(191, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 2, '01:00:00', '2017-01-04', '00:00:01', '2017-01-04 12:36:39', ''),
(192, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-04 12:36:56', ''),
(193, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-04 12:36:58', ''),
(194, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '01:00:00', '2017-01-04', '00:00:01', '2017-01-04 12:37:03', ''),
(195, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 2, '01:00:00', '2017-01-04', '00:00:01', '2017-01-04 12:37:07', ''),
(196, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 2, '01:00:00', '2017-01-04', '00:00:01', '2017-01-04 12:37:10', ''),
(197, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 2, '10:00:00', '2017-01-04', '00:00:01', '2017-01-04 12:37:19', ''),
(198, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 2, '10:00:00', '2017-01-04', '00:00:01', '2017-01-04 12:37:26', ''),
(199, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 2, '10:00:00', '2017-01-04', '00:00:01', '2017-01-04 12:37:40', ''),
(200, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-04 13:01:26', ''),
(201, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-04 13:04:34', ''),
(202, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-12', '00:00:01', '2017-01-04 13:05:12', ''),
(203, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '10:00:00', '2017-01-12', '00:00:01', '2017-01-04 13:05:19', ''),
(204, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-04 13:22:20', ''),
(205, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-04 14:23:35', ''),
(206, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-04', '00:00:01', '2017-01-04 14:23:46', ''),
(207, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '10:00:00', '2017-01-04', '00:00:01', '2017-01-04 14:23:53', ''),
(208, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '10:00:00', '2017-01-04', '00:00:01', '2017-01-04 14:28:08', ''),
(209, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-04', '00:00:01', '2017-01-04 15:35:42', ''),
(210, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '10:00:00', '2017-01-04', '00:00:01', '2017-01-04 15:35:50', ''),
(211, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-04 15:37:36', ''),
(212, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 1, NULL, NULL, NULL, '2017-01-04 15:37:43', ''),
(213, NULL, '0', 1, 1, '11:00:00', '2017-12-25', '00:00:02', '2017-01-04 16:30:46', ''),
(214, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 2, NULL, NULL, NULL, '2017-01-04 19:16:32', ''),
(215, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 2, '01:00:00', '2017-01-05', '00:00:01', '2017-01-04 19:17:33', ''),
(216, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 2, '09:00:00', '2017-01-05', '00:00:01', '2017-01-04 19:17:43', ''),
(217, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-04 23:50:23', ''),
(218, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 12:13:06', ''),
(219, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 12:24:43', ''),
(220, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '10:00:00', '2017-01-05', '00:00:01', '2017-01-05 12:24:57', ''),
(221, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 12:33:06', ''),
(222, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 12:35:24', ''),
(223, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '10:00:00', '2017-01-05', '00:00:01', '2017-01-05 12:35:36', ''),
(224, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '10:00:00', '2017-01-05', '00:00:01', '2017-01-05 12:38:43', ''),
(225, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 12:40:27', ''),
(226, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 12:42:36', ''),
(227, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 12:44:41', ''),
(228, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 12:45:53', ''),
(229, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 12:47:09', ''),
(230, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 12:48:51', ''),
(231, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 12:49:33', ''),
(232, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 12:50:50', ''),
(233, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 14:48:25', ''),
(234, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 1, NULL, NULL, NULL, '2017-01-05 14:49:07', ''),
(235, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-05 19:59:03', ''),
(236, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 2, NULL, NULL, NULL, '2017-01-05 19:59:35', ''),
(237, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 2, '10:00:00', '2017-01-20', '00:00:04', '2017-01-05 20:00:32', ''),
(238, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, NULL, NULL, NULL, '2017-01-06 07:51:36', ''),
(239, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, '07:00:00', '2017-01-10', '00:00:01', '2017-01-06 07:52:45', ''),
(240, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, '01:00:00', '2017-01-06', '00:00:01', '2017-01-06 08:50:41', ''),
(241, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, '01:00:00', '2017-01-10', '00:00:01', '2017-01-06 08:50:52', ''),
(242, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, '01:00:00', '2017-01-06', '00:00:01', '2017-01-06 08:51:03', ''),
(243, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, NULL, NULL, NULL, '2017-01-06 08:51:18', ''),
(244, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, NULL, NULL, NULL, '2017-01-06 08:51:36', ''),
(245, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, '01:00:00', '2017-01-06', '00:00:01', '2017-01-06 08:52:17', ''),
(246, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, '06:00:00', '2017-01-06', '00:00:01', '2017-01-06 08:52:23', ''),
(247, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-06 15:19:58', ''),
(248, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 2, NULL, NULL, NULL, '2017-01-06 15:21:06', ''),
(249, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-08 09:37:06', ''),
(250, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-08 11:01:05', ''),
(251, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '10:00:00', '2017-01-08', '00:00:01', '2017-01-08 11:01:23', ''),
(252, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '10:00:00', '2017-01-08', '00:00:01', '2017-01-08 11:01:59', ''),
(253, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 2, NULL, NULL, NULL, '2017-01-09 16:47:21', ''),
(254, NULL, '123', 1, 1, NULL, NULL, NULL, '2017-01-10 09:31:26', ''),
(255, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 2, NULL, NULL, NULL, '2017-01-10 09:32:40', ''),
(256, NULL, '123', 1, 1, '10:00:00', '2017-01-04', '00:00:01', '2017-01-10 09:35:49', ''),
(257, NULL, '123', 1, 1, '10:00:00', '2017-01-04', '00:00:01', '2017-01-10 09:37:35', ''),
(258, NULL, '123', 1, 1, '10:00:00', '2017-01-10', '00:00:01', '2017-01-10 09:41:58', ''),
(259, NULL, '123', 1, 1, '10:00:00', '2017-01-10', '00:00:01', '2017-01-10 09:43:46', ''),
(260, NULL, '123', 1, 1, '10:00:00', '2017-01-10', '00:00:01', '2017-01-10 09:51:55', ''),
(261, NULL, '123', 1, 1, '10:00:00', '2017-01-10', '00:00:01', '2017-01-10 10:01:45', ''),
(262, NULL, '123', 1, 1, NULL, NULL, NULL, '2017-01-10 10:08:55', ''),
(263, NULL, '123', 1, 1, '11:00:00', '2017-01-10', '00:00:01', '2017-01-10 10:09:32', ''),
(264, NULL, '123', 1, 1, '11:00:00', '2017-01-10', '00:00:01', '2017-01-10 10:11:30', ''),
(265, NULL, '123', 1, 1, '11:00:00', '2017-01-10', '00:00:01', '2017-01-10 10:15:21', ''),
(266, NULL, '123', 1, 1, '11:00:00', '2017-01-10', '00:00:01', '2017-01-10 10:19:59', ''),
(267, NULL, '123', 1, 1, '11:00:00', '2017-01-10', '00:00:02', '2017-01-10 10:30:15', ''),
(268, NULL, '123', 1, 1, '11:00:00', '2017-01-10', '00:00:02', '2017-01-10 10:30:18', ''),
(269, NULL, '123', 1, 1, '11:00:00', '2017-01-10', '00:00:01', '2017-01-10 10:40:02', ''),
(270, NULL, '123', 1, 1, '11:00:00', '2017-01-10', '00:00:01', '2017-01-10 10:55:55', ''),
(271, NULL, '123', 1, 1, '11:00:00', '2017-01-10', '00:00:01', '2017-01-10 10:57:42', ''),
(272, NULL, 'null', 1, 1, '12:00:00', '2017-01-10', '00:00:01', '2017-01-10 11:03:27', ''),
(273, NULL, 'null', 1, 1, '12:00:00', '2017-01-10', '00:00:01', '2017-01-10 11:14:25', ''),
(274, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '01:00:00', '2017-01-10', '00:00:01', '2017-01-10 11:16:27', ''),
(275, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '20:00:00', '2017-01-10', '00:00:01', '2017-01-10 11:17:28', ''),
(276, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-10 11:21:32', ''),
(277, NULL, 'null', 1, 1, '12:00:00', '2017-01-11', '00:00:01', '2017-01-10 11:30:43', ''),
(278, NULL, 'null', 1, 1, '12:00:00', '2017-01-11', '00:00:01', '2017-01-10 11:57:00', ''),
(279, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-10 11:57:05', ''),
(280, NULL, 'null', 1, 1, '13:00:00', '2017-01-10', '00:00:01', '2017-01-10 12:53:53', ''),
(281, NULL, 'null', 1, 1, '13:00:00', '2017-01-10', '00:00:01', '2017-01-10 12:56:59', ''),
(282, NULL, 'null', 1, 1, '13:00:00', '2017-01-10', '00:00:01', '2017-01-10 12:57:01', ''),
(283, NULL, 'null', 1, 1, '13:00:00', '2017-01-10', '00:00:01', '2017-01-10 12:59:33', ''),
(284, NULL, 'null', 1, 1, '14:00:00', '2017-01-10', '00:00:01', '2017-01-10 13:04:39', ''),
(285, NULL, 'null', 1, 1, '14:00:00', '2017-01-10', '00:00:01', '2017-01-10 13:07:05', ''),
(286, NULL, 'null', 1, 1, '14:00:00', '2017-01-10', '00:00:01', '2017-01-10 13:13:02', ''),
(287, NULL, 'null', 1, 1, '14:00:00', '2017-01-10', '00:00:01', '2017-01-10 13:14:32', ''),
(288, NULL, 'null', 1, 1, '14:00:00', '2017-01-10', '00:00:01', '2017-01-10 13:16:02', ''),
(289, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-10 13:59:13', ''),
(290, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-10 14:47:32', ''),
(291, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-10 14:50:20', ''),
(292, NULL, 'null', 1, 1, '15:00:00', '2017-01-10', '00:00:01', '2017-01-10 14:51:56', ''),
(293, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-10 14:55:39', ''),
(294, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 2, 1, NULL, NULL, NULL, '2017-01-10 14:55:53', ''),
(295, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-10 14:57:21', ''),
(296, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-10 15:00:03', ''),
(297, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-10 15:00:56', ''),
(298, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 2, 1, NULL, NULL, NULL, '2017-01-10 15:01:01', ''),
(299, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 2, 1, NULL, NULL, NULL, '2017-01-10 15:01:07', ''),
(300, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-10 15:29:48', ''),
(301, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-10 15:29:50', ''),
(302, NULL, 'null', 1, 1, '16:00:00', '2017-01-10', '00:00:01', '2017-01-10 15:30:09', ''),
(303, NULL, 'null', 1, 1, '16:00:00', '2017-01-10', '00:00:01', '2017-01-10 15:30:13', ''),
(304, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-10 15:55:28', ''),
(305, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-10 15:55:45', ''),
(306, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-10 15:58:51', ''),
(307, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-10 16:04:53', ''),
(308, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-10 16:13:16', ''),
(309, NULL, 'null', 1, 1, '17:00:00', '2017-01-10', '00:00:01', '2017-01-10 16:20:21', ''),
(310, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-10 16:20:30', ''),
(311, NULL, 'null', 1, 1, '17:00:00', '2017-01-10', '00:00:01', '2017-01-10 16:24:34', ''),
(312, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-10 16:24:46', ''),
(313, NULL, 'null', 1, 1, '17:00:00', '2017-01-10', '00:00:01', '2017-01-10 16:24:54', ''),
(314, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-10 16:33:11', ''),
(315, NULL, 'null', 1, 1, '17:00:00', '2017-01-25', '00:00:01', '2017-01-10 16:34:40', ''),
(316, NULL, 'null', 1, 1, '17:00:00', '2017-01-10', '00:00:01', '2017-01-10 16:37:48', ''),
(317, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-10 16:38:19', ''),
(318, NULL, 'null', 1, 1, '17:00:00', '2017-01-10', '00:00:01', '2017-01-10 16:38:27', ''),
(319, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-10 16:52:41', ''),
(320, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 10:22:18', ''),
(321, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 11:04:33', ''),
(322, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 11:23:57', ''),
(323, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 11:29:50', ''),
(324, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 11:32:19', ''),
(325, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 11:44:29', ''),
(326, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 11:56:25', ''),
(327, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 11:58:10', ''),
(328, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 12:58:58', ''),
(329, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 13:08:42', ''),
(330, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-11 13:14:50', ''),
(331, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:26:02', ''),
(332, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:28:19', ''),
(333, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:32:15', ''),
(334, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:41:11', ''),
(335, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:44:57', ''),
(336, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:45:55', ''),
(337, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:47:23', ''),
(338, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:48:51', ''),
(339, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:49:40', ''),
(340, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:50:32', ''),
(341, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:52:20', ''),
(342, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:54:05', ''),
(343, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:55:17', ''),
(344, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 13:58:52', ''),
(345, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 14:29:41', ''),
(346, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 14:33:37', ''),
(347, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 14:35:18', ''),
(348, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 14:41:40', ''),
(349, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-06', '00:00:01', '2017-01-11 14:48:47', ''),
(350, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-06', '00:00:01', '2017-01-11 14:48:52', ''),
(351, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 14:49:33', ''),
(352, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 14:49:37', ''),
(353, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 14:49:55', ''),
(354, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 14:49:56', ''),
(355, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-05', '00:00:01', '2017-01-11 14:50:01', ''),
(356, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '10:00:00', '2017-01-05', '00:00:01', '2017-01-11 14:50:12', ''),
(357, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '08:00:00', '2017-01-05', '00:00:01', '2017-01-11 14:50:38', ''),
(358, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 14:50:43', ''),
(359, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-11', '00:00:01', '2017-01-11 14:50:49', ''),
(360, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-11', '00:00:01', '2017-01-11 14:50:58', ''),
(361, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-11', '00:00:01', '2017-01-11 14:51:14', ''),
(362, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 14:53:18', ''),
(363, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-11', '00:00:01', '2017-01-11 14:58:44', ''),
(364, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 14:58:48', ''),
(365, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 15:31:16', ''),
(366, NULL, 'null', 1, 1, '16:00:00', '2017-01-11', '00:00:01', '2017-01-11 15:31:35', ''),
(367, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 15:57:30', ''),
(368, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 15:59:04', ''),
(369, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-11 16:05:44', ''),
(370, NULL, 'null', 1, 1, '17:00:00', '2017-01-11', '00:00:01', '2017-01-11 16:06:28', ''),
(371, NULL, 'null', 1, 1, '17:00:00', '2017-01-11', '00:00:01', '2017-01-11 16:07:29', ''),
(372, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 17:01:07', ''),
(373, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 17:01:49', ''),
(374, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-11 17:03:42', ''),
(375, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '07:00:00', '2017-01-25', '00:00:01', '2017-01-11 17:05:06', ''),
(376, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '07:00:00', '2017-01-25', '00:00:01', '2017-01-11 17:09:58', ''),
(377, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 09:50:27', ''),
(378, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-12 09:57:05', ''),
(379, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-12 09:59:48', ''),
(380, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-12 10:00:33', ''),
(381, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-12 10:11:19', ''),
(382, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-12 10:14:29', ''),
(383, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-12 10:16:57', ''),
(384, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-12 10:19:10', ''),
(385, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-12 10:19:14', ''),
(386, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, '01:00:00', '2017-01-16', '00:00:01', '2017-01-12 10:27:19', ''),
(387, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, '01:00:00', '2017-01-27', '00:00:01', '2017-01-12 10:33:24', ''),
(388, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-12 10:59:41', ''),
(389, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 11:01:32', ''),
(390, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 11:09:09', ''),
(391, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 11:10:33', ''),
(392, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 11:13:08', ''),
(393, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '01:00:00', '2016-12-14', '00:00:01', '2017-01-12 11:18:46', ''),
(394, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 11:19:15', ''),
(395, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 11:21:55', ''),
(396, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 11:28:24', ''),
(397, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 11:30:32', ''),
(398, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 11:38:55', ''),
(399, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 12:01:21', ''),
(400, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 12:04:17', ''),
(401, NULL, 'null', 1, 1, '10:00:00', '2017-01-12', '00:00:01', '2017-01-12 12:05:17', ''),
(402, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 12:05:29', ''),
(403, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 12:08:00', ''),
(404, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 12:12:25', ''),
(405, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 12:16:53', ''),
(406, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-12 12:19:51', ''),
(407, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 12:30:35', ''),
(408, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 12:33:03', ''),
(409, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 12:34:27', ''),
(410, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 12:36:58', ''),
(411, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 12:46:58', ''),
(412, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 12:48:35', ''),
(413, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 12:57:45', ''),
(414, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-12 13:00:57', ''),
(415, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 13:03:29', ''),
(416, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '03:00:00', '2017-01-24', '00:00:01', '2017-01-12 13:08:26', ''),
(417, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '03:00:00', '2017-01-24', '00:00:01', '2017-01-12 13:08:35', ''),
(418, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-12 13:08:40', ''),
(419, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-12 13:09:30', ''),
(420, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-12 13:11:32', ''),
(421, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 13:11:54', ''),
(422, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-12 13:12:09', ''),
(423, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 13:16:38', ''),
(424, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-12 13:16:45', ''),
(425, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-12 13:18:15', ''),
(426, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 13:19:34', ''),
(427, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 13:21:24', ''),
(428, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 13:28:34', ''),
(429, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 13:34:16', ''),
(430, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 13:34:18', ''),
(431, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 13:43:34', ''),
(432, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 13:46:56', ''),
(433, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 13:49:13', ''),
(434, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 13:49:16', ''),
(435, NULL, '31427F08-1307-46CF-89A6-B40395270B2A', 1, 1, NULL, NULL, NULL, '2017-01-12 13:52:49', ''),
(436, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 13:53:07', ''),
(437, NULL, 'D43A1CC1-0830-43AB-8B32-391C0B67DBF6', 1, 1, NULL, NULL, NULL, '2017-01-12 13:59:23', ''),
(438, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 14:02:28', ''),
(439, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 14:03:37', ''),
(440, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-12 14:12:25', ''),
(441, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '06:00:00', '2017-01-12', '00:00:01', '2017-01-12 14:13:16', ''),
(442, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 14:15:24', ''),
(443, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 14:22:17', ''),
(444, NULL, 'D43A1CC1-0830-43AB-8B32-391C0B67DBF6', 1, 1, NULL, NULL, NULL, '2017-01-12 14:31:04', ''),
(445, NULL, '123', 1, 1, NULL, NULL, NULL, '2017-01-12 14:40:02', ''),
(446, NULL, '123', 1, 1, NULL, NULL, NULL, '2017-01-12 14:59:20', ''),
(447, NULL, '123', 1, 1, NULL, NULL, NULL, '2017-01-12 15:09:54', ''),
(448, NULL, '123', 1, 1, NULL, NULL, NULL, '2017-01-12 15:13:26', ''),
(449, NULL, '123', 1, 1, NULL, NULL, NULL, '2017-01-12 15:13:35', ''),
(450, NULL, '123', 1, 1, NULL, NULL, NULL, '2017-01-12 15:20:49', ''),
(451, NULL, '123', 1, 1, NULL, NULL, NULL, '2017-01-12 15:21:14', ''),
(452, NULL, '123', 1, 1, NULL, NULL, NULL, '2017-01-12 15:23:07', ''),
(453, NULL, '123', 1, 1, NULL, NULL, NULL, '2017-01-12 15:27:16', ''),
(454, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 15:39:37', ''),
(455, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 15:45:27', ''),
(456, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 15:45:59', ''),
(457, NULL, 'null', 1, 1, '17:00:00', '2017-01-12', '00:00:05', '2017-01-12 15:47:35', ''),
(458, NULL, 'null', 1, 1, '16:00:00', '2017-01-12', '00:00:01', '2017-01-12 15:52:50', ''),
(459, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 15:54:08', ''),
(460, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 15:55:19', ''),
(461, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 16:12:52', 'hhjnv'),
(462, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 16:13:10', 'hhjnv'),
(463, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 16:13:52', ''),
(464, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 16:14:05', ''),
(465, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-12 16:17:52', ''),
(466, NULL, 'null', 1, 1, '10:00:00', '2017-01-12', '00:00:01', '2017-01-12 16:21:20', ''),
(467, NULL, '2E5443EC-D00E-445E-BB4A-75C62E61E750', 1, 1, NULL, NULL, NULL, '2017-01-12 20:41:23', ''),
(468, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-12 21:02:23', ''),
(469, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-13', '00:00:01', '2017-01-12 21:11:42', ''),
(470, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '06:00:00', '2017-01-13', '00:00:01', '2017-01-12 21:11:50', ''),
(471, NULL, '7C6936D9-65C0-4552-A313-ED1B6F6C070E', 1, 1, NULL, NULL, NULL, '2017-01-12 22:37:15', ''),
(472, NULL, '7C6936D9-65C0-4552-A313-ED1B6F6C070E', 1, 1, NULL, NULL, NULL, '2017-01-12 22:39:07', ''),
(473, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-13 13:43:44', ''),
(474, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-13 14:33:12', ''),
(475, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-13 15:20:55', ''),
(476, NULL, 'FDA04B2B-570C-479C-B97B-D880797FD94A', 1, 1, NULL, NULL, NULL, '2017-01-13 15:38:47', ''),
(477, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-13 19:16:26', ''),
(478, NULL, 'null', 2, 1, NULL, NULL, NULL, '2017-01-13 19:17:45', ''),
(479, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, NULL, NULL, NULL, '2017-01-14 08:43:23', ''),
(480, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, '16:00:00', '2017-01-16', '00:00:01', '2017-01-14 08:43:56', ''),
(481, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 1, NULL, NULL, NULL, '2017-01-14 21:42:22', ''),
(482, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-14 21:42:29', ''),
(483, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 2, NULL, NULL, NULL, '2017-01-14 21:42:35', ''),
(484, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-15 10:56:18', ''),
(485, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-15 11:08:46', ''),
(486, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-15 11:10:44', ''),
(487, NULL, 'null', 1, 1, '12:00:00', '2017-01-15', '00:00:01', '2017-01-15 11:22:37', ''),
(488, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-15 11:38:03', ''),
(489, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-15 11:40:05', ''),
(490, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-15 13:04:40', ''),
(491, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 2, NULL, NULL, NULL, '2017-01-15 13:04:44', ''),
(492, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 2, NULL, NULL, NULL, '2017-01-15 13:04:48', ''),
(493, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-15 13:15:55', ''),
(494, NULL, '0F0377FA-DFFD-497B-8E2C-A550368AFA75', 1, 1, NULL, NULL, NULL, '2017-01-15 13:54:08', ''),
(495, NULL, '0F0377FA-DFFD-497B-8E2C-A550368AFA75', 1, 1, NULL, NULL, NULL, '2017-01-15 13:54:50', ''),
(496, NULL, '0F0377FA-DFFD-497B-8E2C-A550368AFA75', 1, 1, NULL, NULL, NULL, '2017-01-15 14:15:01', ''),
(497, NULL, '0F0377FA-DFFD-497B-8E2C-A550368AFA75', 1, 1, NULL, NULL, NULL, '2017-01-15 14:29:36', ''),
(498, NULL, '0F0377FA-DFFD-497B-8E2C-A550368AFA75', 1, 2, NULL, NULL, NULL, '2017-01-15 14:29:41', ''),
(499, NULL, '0F0377FA-DFFD-497B-8E2C-A550368AFA75', 2, 2, NULL, NULL, NULL, '2017-01-15 14:29:44', ''),
(500, NULL, '0F0377FA-DFFD-497B-8E2C-A550368AFA75', 2, 2, NULL, NULL, NULL, '2017-01-15 14:32:27', ''),
(501, NULL, '0F0377FA-DFFD-497B-8E2C-A550368AFA75', 1, 1, NULL, NULL, NULL, '2017-01-15 14:34:51', ''),
(502, NULL, '0F0377FA-DFFD-497B-8E2C-A550368AFA75', 1, 1, NULL, NULL, NULL, '2017-01-15 14:36:20', ''),
(503, NULL, '0F0377FA-DFFD-497B-8E2C-A550368AFA75', 1, 1, NULL, NULL, NULL, '2017-01-15 14:37:13', ''),
(504, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-15 15:27:06', ''),
(505, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-15 15:43:36', ''),
(506, NULL, 'null', 1, 1, '16:00:00', '2017-01-15', '00:00:01', '2017-01-15 15:44:52', ''),
(507, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-15 15:45:51', ''),
(508, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-15 15:50:00', ''),
(509, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '09:00:00', '2017-01-15', '00:00:01', '2017-01-15 15:51:47', ''),
(510, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '09:00:00', '2017-01-24', '00:00:01', '2017-01-15 15:51:59', ''),
(511, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-15 15:52:09', ''),
(512, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '01:00:00', '2017-01-15', '00:00:01', '2017-01-15 15:52:26', ''),
(513, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '01:00:00', '2017-01-15', '00:00:01', '2017-01-15 15:52:34', ''),
(514, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-15 15:52:46', ''),
(515, NULL, 'null', 1, 1, '16:00:00', '2017-01-15', '00:00:01', '2017-01-15 15:53:06', ''),
(516, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-15 15:55:05', ''),
(517, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-15 15:56:02', ''),
(518, NULL, 'null', 1, 1, '16:00:00', '2017-01-26', '00:00:01', '2017-01-15 15:56:12', ''),
(519, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-15 16:07:54', ''),
(520, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-14', '00:00:01', '2017-01-15 16:08:02', ''),
(521, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-14', '00:00:05', '2017-01-15 16:08:10', ''),
(522, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '01:00:00', '2017-01-14', '00:00:01', '2017-01-15 16:08:19', ''),
(523, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '06:00:00', '2017-01-14', '00:00:01', '2017-01-15 16:08:26', ''),
(524, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, '06:00:00', '2017-01-13', '00:00:01', '2017-01-15 16:09:11', ''),
(525, NULL, 'null', 1, 1, '17:00:00', '2017-01-16', '00:00:01', '2017-01-15 16:36:36', ''),
(526, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-15 16:48:06', ''),
(527, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, '15:00:00', '2017-01-15', '00:00:01', '2017-01-15 20:19:53', ''),
(528, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-16 09:20:13', ''),
(529, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 09:33:27', ''),
(530, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 09:35:39', ''),
(531, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 09:35:44', ''),
(532, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 09:37:43', ''),
(533, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 09:38:39', 'rgggtgtgtthby'),
(534, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 09:44:47', 'rggyhhggtv'),
(535, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 09:47:25', 'gdfbbgnbb'),
(536, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 09:47:32', 'gdfbbgnbb'),
(537, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '01:00:00', '2017-01-17', '00:00:01', '2017-01-16 09:49:56', ''),
(538, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '01:00:00', '2017-01-17', '00:00:01', '2017-01-16 09:50:12', ''),
(539, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '06:00:00', '2017-01-17', '00:00:01', '2017-01-16 09:50:22', ''),
(540, NULL, 'null', 1, 1, '10:00:00', '2017-01-18', '00:00:01', '2017-01-16 09:51:09', ''),
(541, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-16 09:52:12', ''),
(542, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, NULL, NULL, NULL, '2017-01-16 09:52:20', ''),
(543, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-16 09:54:12', 'cfghhhhh'),
(544, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-16 09:54:34', 'cfghhhhh'),
(545, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 09:55:35', 'cfghhhhh'),
(546, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 09:56:38', 'cfghhhhh'),
(547, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 09:56:45', 'cfghhhhh'),
(548, NULL, '33A3EB0D-9648-471A-93A2-FF1AE0779499', 1, 1, NULL, NULL, NULL, '2017-01-16 10:02:08', ''),
(549, NULL, 'null', 1, 1, '10:00:00', '2017-01-18', '00:00:01', '2017-01-16 10:03:09', ''),
(550, NULL, '33A3EB0D-9648-471A-93A2-FF1AE0779499', 1, 1, '07:00:00', '2017-01-05', '00:00:01', '2017-01-16 10:03:21', ''),
(551, NULL, 'null', 1, 1, '11:00:00', '2017-04-29', '00:00:01', '2017-01-16 10:03:30', ''),
(552, NULL, 'null', 1, 1, '22:00:00', '2017-01-16', '00:00:12', '2017-01-16 10:03:50', ''),
(553, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 10:08:12', 'cde'),
(554, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 10:08:19', ''),
(555, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 10:10:29', ''),
(556, NULL, '33A3EB0D-9648-471A-93A2-FF1AE0779499', 1, 1, NULL, NULL, NULL, '2017-01-16 10:11:26', ''),
(557, NULL, '33A3EB0D-9648-471A-93A2-FF1AE0779499', 1, 1, NULL, NULL, NULL, '2017-01-16 10:11:37', ''),
(558, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 10:13:03', ''),
(559, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 10:15:43', ''),
(560, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 10:17:46', ''),
(561, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 10:17:50', ''),
(562, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:25:22', ''),
(563, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:27:57', ''),
(564, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:28:03', ''),
(565, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:36:36', ''),
(566, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:45:50', ''),
(567, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:46:16', ''),
(568, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:48:23', ''),
(569, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-16 10:53:04', 'if'),
(570, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:53:05', 'Ff'),
(571, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:54:21', 'Ff'),
(572, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:54:33', '');
INSERT INTO `search` (`search_record_id`, `player_id`, `token`, `area_id`, `game_type_id`, `start`, `date`, `duration`, `search_date`, `text`) VALUES
(573, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:54:38', 'Ff'),
(574, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:54:41', ''),
(575, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:54:46', 'Ffff'),
(576, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 10:54:49', 'Fff'),
(577, 1, '0', 1, 1, NULL, NULL, NULL, '2017-01-16 10:55:10', 'field'),
(578, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, '01:00:00', '2017-01-17', '00:00:01', '2017-01-16 10:58:36', ''),
(579, 1, '0', 1, 1, '00:00:00', '2017-12-25', '00:00:01', '2017-01-16 11:03:50', 'field'),
(580, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 11:04:50', ''),
(581, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, NULL, NULL, NULL, '2017-01-16 11:04:54', 'Sds'),
(582, NULL, 'CC1258CF-984D-4FE0-9687-8F1DABA7A3FF', 1, 1, '01:00:00', '2017-01-05', '00:00:01', '2017-01-16 11:05:04', 'Sd'),
(583, NULL, 'null', 1, 1, '11:00:00', '2017-01-16', '00:00:01', '2017-01-16 11:14:51', 'if'),
(584, NULL, 'null', 1, 1, '12:00:00', '2016-12-04', '00:00:12', '2017-01-16 11:15:14', 'ifudbs'),
(585, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-16 14:34:11', ''),
(586, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-16 14:34:18', ''),
(587, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-16 14:43:46', ''),
(588, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '01:00:00', '2017-01-12', '00:00:01', '2017-01-16 14:43:55', ''),
(589, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, '01:00:00', '2017-01-12', '00:00:01', '2017-01-16 14:44:02', ''),
(590, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, '01:00:00', '2017-01-16', '00:00:01', '2017-01-16 15:37:38', ''),
(591, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, '01:00:00', '2017-01-16', '00:00:01', '2017-01-16 15:37:42', ''),
(592, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 2, '01:00:00', '2017-01-16', '00:00:01', '2017-01-16 15:37:46', ''),
(593, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 2, '01:00:00', '2017-01-16', '00:00:01', '2017-01-16 15:37:49', ''),
(594, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 2, '01:00:00', '2017-01-05', '00:00:01', '2017-01-16 15:37:54', ''),
(595, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 2, '06:00:00', '2017-01-05', '00:00:01', '2017-01-16 15:38:02', ''),
(596, NULL, 'D4BD6583-67EF-4393-BCBF-E30FE8843C92', 1, 1, NULL, NULL, NULL, '2017-01-16 15:38:32', ''),
(597, NULL, 'D4BD6583-67EF-4393-BCBF-E30FE8843C92', 2, 1, '06:00:00', '2017-01-18', '00:00:01', '2017-01-16 15:39:21', ''),
(598, NULL, 'D4BD6583-67EF-4393-BCBF-E30FE8843C92', 1, 1, '06:00:00', '2017-01-18', '00:00:01', '2017-01-16 15:39:44', ''),
(599, NULL, 'D4BD6583-67EF-4393-BCBF-E30FE8843C92', 1, 1, NULL, NULL, NULL, '2017-01-16 15:39:49', ''),
(600, NULL, 'D4BD6583-67EF-4393-BCBF-E30FE8843C92', 2, 1, NULL, NULL, NULL, '2017-01-16 15:39:58', ''),
(601, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 2, '06:00:00', '2017-01-05', '00:00:01', '2017-01-16 15:40:52', ''),
(602, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-16 16:36:38', ''),
(603, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-16 16:37:55', ''),
(604, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-16 16:39:19', ''),
(605, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, NULL, NULL, NULL, '2017-01-16 16:39:35', ''),
(606, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-16 16:51:06', ''),
(607, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-16 16:52:43', ''),
(608, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-16 16:58:01', ''),
(609, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, NULL, NULL, NULL, '2017-01-16 16:58:08', ''),
(610, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, NULL, NULL, NULL, '2017-01-16 16:58:14', ''),
(611, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, NULL, NULL, NULL, '2017-01-16 16:58:28', ''),
(612, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, NULL, NULL, NULL, '2017-01-16 16:58:36', ''),
(613, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, NULL, NULL, NULL, '2017-01-16 17:03:06', ''),
(614, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, NULL, NULL, NULL, '2017-01-16 17:04:00', ''),
(615, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-17 13:00:36', ''),
(616, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 1, NULL, NULL, NULL, '2017-01-17 13:00:42', ''),
(617, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 2, 1, '06:00:00', '2017-01-17', '00:00:01', '2017-01-17 13:00:59', ''),
(618, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-17 13:05:43', ''),
(619, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, NULL, NULL, NULL, '2017-01-17 13:05:47', ''),
(620, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, NULL, NULL, NULL, '2017-01-17 13:06:07', ''),
(621, NULL, 'D4BD6583-67EF-4393-BCBF-E30FE8843C92', 1, 1, NULL, NULL, NULL, '2017-01-17 13:06:45', ''),
(622, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, NULL, NULL, NULL, '2017-01-17 13:08:23', ''),
(623, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 2, 1, NULL, NULL, NULL, '2017-01-17 13:08:31', ''),
(624, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-17 13:43:33', ''),
(625, NULL, 'null', 1, 1, '14:00:00', '2017-01-26', '00:00:01', '2017-01-17 13:45:55', ''),
(626, NULL, 'D4BD6583-67EF-4393-BCBF-E30FE8843C92', 1, 1, NULL, NULL, NULL, '2017-01-17 14:13:19', ''),
(627, NULL, 'D4BD6583-67EF-4393-BCBF-E30FE8843C92', 1, 1, NULL, NULL, NULL, '2017-01-17 14:14:39', ''),
(628, NULL, 'D4BD6583-67EF-4393-BCBF-E30FE8843C92', 1, 1, '12:00:00', '2017-01-19', '00:00:01', '2017-01-17 14:15:07', ''),
(629, NULL, '0A8215ED-1F95-4F94-ADAB-A5B501879D1D', 1, 1, NULL, NULL, NULL, '2017-01-17 14:40:32', ''),
(630, NULL, 'D4BD6583-67EF-4393-BCBF-E30FE8843C92', 1, 1, NULL, NULL, NULL, '2017-01-17 14:49:35', ''),
(631, NULL, 'D4BD6583-67EF-4393-BCBF-E30FE8843C92', 1, 1, '07:00:00', '2017-01-18', '00:00:01', '2017-01-17 14:50:06', ''),
(632, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-17 15:07:18', ''),
(633, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-17 15:10:42', ''),
(634, NULL, 'F0154A62-4024-4194-ADD7-ED4F41BF41FB', 1, 1, NULL, NULL, NULL, '2017-01-17 15:11:00', ''),
(635, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-17 15:12:04', ''),
(636, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-17 15:14:02', ''),
(637, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-17 15:15:36', ''),
(638, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-17 15:16:31', ''),
(639, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-17 15:18:40', ''),
(640, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-17 15:24:42', ''),
(641, NULL, 'D4BD6583-67EF-4393-BCBF-E30FE8843C92', 1, 1, NULL, NULL, NULL, '2017-01-17 15:46:44', ''),
(642, NULL, 'D4BD6583-67EF-4393-BCBF-E30FE8843C92', 1, 1, NULL, NULL, NULL, '2017-01-17 15:50:52', ''),
(643, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-17 16:46:49', ''),
(644, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, NULL, NULL, NULL, '2017-01-17 17:27:03', ''),
(645, NULL, '1FBA2998-6EB4-4F04-BEC4-281412290F37', 1, 1, '20:00:00', '2017-01-19', '00:00:01', '2017-01-17 21:02:24', ''),
(646, NULL, '394936E9-05D8-46B6-8001-26BF3D43FDE2', 1, 1, NULL, NULL, NULL, '2017-01-18 01:56:16', ''),
(647, NULL, 'f64CA7z0gRo:APA91bEAwEJZ59dv9FmiHqi2Jcx_s860-wW2lfOJdFI7DDeZ9uXXB7yKVbco0wcoN9NzKXt5Vjfyxot8v8mpmJkthN-B2bQ0DKOw_tl4QtNz_W4wZq3MgTyUL2ujfTzcz1bwkar-JSZq', 1, 1, NULL, NULL, NULL, '2017-01-18 09:40:25', ''),
(648, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 09:41:37', ''),
(649, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 09:42:33', ''),
(650, NULL, '', 1, 1, '07:00:00', '2017-01-26', '00:00:05', '2017-01-18 09:48:03', ''),
(651, NULL, 'f64CA7z0gRo:APA91bEAwEJZ59dv9FmiHqi2Jcx_s860-wW2lfOJdFI7DDeZ9uXXB7yKVbco0wcoN9NzKXt5Vjfyxot8v8mpmJkthN-B2bQ0DKOw_tl4QtNz_W4wZq3MgTyUL2ujfTzcz1bwkar-JSZq', 1, 1, '10:00:00', '2017-01-18', '00:00:01', '2017-01-18 09:59:55', ''),
(652, NULL, 'f64CA7z0gRo:APA91bEAwEJZ59dv9FmiHqi2Jcx_s860-wW2lfOJdFI7DDeZ9uXXB7yKVbco0wcoN9NzKXt5Vjfyxot8v8mpmJkthN-B2bQ0DKOw_tl4QtNz_W4wZq3MgTyUL2ujfTzcz1bwkar-JSZq', 1, 1, '07:00:00', '2017-01-18', '00:00:01', '2017-01-18 10:02:53', ''),
(653, NULL, 'f64CA7z0gRo:APA91bEAwEJZ59dv9FmiHqi2Jcx_s860-wW2lfOJdFI7DDeZ9uXXB7yKVbco0wcoN9NzKXt5Vjfyxot8v8mpmJkthN-B2bQ0DKOw_tl4QtNz_W4wZq3MgTyUL2ujfTzcz1bwkar-JSZq', 1, 1, '07:00:00', '2017-01-18', '00:00:01', '2017-01-18 10:03:00', ''),
(654, NULL, 'f64CA7z0gRo:APA91bEAwEJZ59dv9FmiHqi2Jcx_s860-wW2lfOJdFI7DDeZ9uXXB7yKVbco0wcoN9NzKXt5Vjfyxot8v8mpmJkthN-B2bQ0DKOw_tl4QtNz_W4wZq3MgTyUL2ujfTzcz1bwkar-JSZq', 1, 1, NULL, NULL, NULL, '2017-01-18 10:03:33', ''),
(655, NULL, 'f64CA7z0gRo:APA91bEAwEJZ59dv9FmiHqi2Jcx_s860-wW2lfOJdFI7DDeZ9uXXB7yKVbco0wcoN9NzKXt5Vjfyxot8v8mpmJkthN-B2bQ0DKOw_tl4QtNz_W4wZq3MgTyUL2ujfTzcz1bwkar-JSZq', 1, 1, '08:00:00', '2017-01-18', '00:00:01', '2017-01-18 10:04:17', ''),
(656, NULL, 'f64CA7z0gRo:APA91bEAwEJZ59dv9FmiHqi2Jcx_s860-wW2lfOJdFI7DDeZ9uXXB7yKVbco0wcoN9NzKXt5Vjfyxot8v8mpmJkthN-B2bQ0DKOw_tl4QtNz_W4wZq3MgTyUL2ujfTzcz1bwkar-JSZq', 1, 1, NULL, NULL, NULL, '2017-01-18 10:05:04', ''),
(657, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-18 10:16:18', ''),
(658, NULL, '', 1, 2, '01:00:00', '2017-01-27', '00:00:01', '2017-01-18 10:28:53', ''),
(659, NULL, '', 1, 2, '01:00:00', '2017-01-27', '00:00:01', '2017-01-18 10:29:04', ''),
(660, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-18 10:29:25', ''),
(661, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 10:29:48', ''),
(662, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 10:29:54', ''),
(663, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 10:29:57', ''),
(664, NULL, '', 2, 1, NULL, NULL, NULL, '2017-01-18 10:30:01', ''),
(665, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 10:30:10', ''),
(666, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 10:30:33', ''),
(667, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 10:30:56', ''),
(668, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 10:31:26', ''),
(669, NULL, '', 2, 1, NULL, NULL, NULL, '2017-01-18 10:31:29', ''),
(670, NULL, '', 2, 1, NULL, NULL, NULL, '2017-01-18 10:45:22', ''),
(671, NULL, '', 2, 2, NULL, NULL, NULL, '2017-01-18 10:45:30', ''),
(672, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-18 10:46:06', ''),
(673, NULL, '', 2, 2, NULL, NULL, NULL, '2017-01-18 10:47:17', ''),
(674, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-18 11:01:19', ''),
(675, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 11:09:07', ''),
(676, NULL, '', 1, 2, '10:00:00', '2017-01-25', '00:00:01', '2017-01-18 11:09:49', ''),
(677, NULL, '', 1, 2, '14:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:10:35', ''),
(678, NULL, '', 2, 2, '14:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:11:10', ''),
(679, NULL, '', 2, 2, '14:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:11:37', ''),
(680, NULL, '', 2, 2, '14:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:13:06', ''),
(681, NULL, '', 2, 2, '14:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:13:10', ''),
(682, NULL, '', 2, 2, '14:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:13:25', ''),
(683, NULL, '', 2, 2, '14:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:13:31', ''),
(684, NULL, '', 1, 1, '18:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:13:48', ''),
(685, NULL, '', 2, 1, '18:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:13:52', ''),
(686, NULL, '', 2, 2, '18:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:13:57', ''),
(687, NULL, '', 2, 1, '18:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:14:05', ''),
(688, NULL, '', 2, 1, '14:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:16:12', ''),
(689, NULL, '', 2, 1, '18:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:16:53', ''),
(690, NULL, '', 1, 1, '18:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:17:20', ''),
(691, NULL, '', 1, 1, '02:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:17:46', ''),
(692, NULL, '', 1, 1, '15:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:17:58', ''),
(693, NULL, '', 1, 1, '15:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:19:16', ''),
(694, NULL, '', 1, 1, '03:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:19:24', ''),
(695, NULL, '', 1, 1, '15:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:19:32', ''),
(696, NULL, '', 1, 1, '15:00:00', '2017-01-26', '00:00:02', '2017-01-18 11:19:46', ''),
(697, NULL, '', 1, 1, '16:00:00', '2017-01-26', '00:00:02', '2017-01-18 11:20:11', ''),
(698, NULL, '', 1, 1, '16:00:00', '2017-01-26', '00:00:02', '2017-01-18 11:20:25', ''),
(699, NULL, '', 1, 1, '16:00:00', '2017-01-26', '00:00:03', '2017-01-18 11:20:37', ''),
(700, NULL, '', 1, 1, '15:00:00', '2017-01-26', '00:00:03', '2017-01-18 11:21:12', ''),
(701, NULL, '', 1, 1, '16:00:00', '2017-01-26', '00:00:03', '2017-01-18 11:21:23', ''),
(702, NULL, '', 1, 1, '16:00:00', '2017-01-26', '00:00:04', '2017-01-18 11:21:33', ''),
(703, 26, '0', 1, 1, NULL, NULL, NULL, '2017-01-18 11:22:59', '0'),
(704, NULL, '', 1, 1, '16:00:00', '2017-01-26', '00:00:03', '2017-01-18 11:23:14', ''),
(705, 26, '0', 1, 1, '16:00:00', '2017-01-26', '00:00:03', '2017-01-18 11:24:08', '0'),
(706, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 11:25:04', 'ahdaf2'),
(707, NULL, '', 2, 1, NULL, NULL, NULL, '2017-01-18 11:25:09', 'ahdaf2'),
(708, NULL, '', 2, 2, NULL, NULL, NULL, '2017-01-18 11:25:12', 'ahdaf2'),
(709, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 11:25:56', ''),
(710, 26, '0', 1, 1, '16:00:00', '2017-01-26', '00:00:03', '2017-01-18 11:28:58', '0'),
(711, NULL, '', 1, 1, '16:00:00', '2017-01-26', '00:00:03', '2017-01-18 11:29:05', ''),
(712, NULL, '', 1, 1, '17:00:00', '2017-01-26', '00:00:03', '2017-01-18 11:34:19', ''),
(713, NULL, '', 1, 1, '17:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:34:43', ''),
(714, NULL, '', 1, 1, '13:00:00', '2017-01-26', '00:00:01', '2017-01-18 11:35:05', ''),
(715, NULL, '', 1, 1, '13:00:00', '2017-01-26', '00:00:03', '2017-01-18 11:35:13', ''),
(716, NULL, '', 1, 1, '13:00:00', '2017-01-26', '00:00:03', '2017-01-18 11:41:25', 'Ahdaff'),
(717, NULL, '', 2, 1, '13:00:00', '2017-01-26', '00:00:03', '2017-01-18 11:41:29', 'Ahdaff'),
(718, NULL, '', 2, 1, '13:00:00', '2017-01-26', '00:00:03', '2017-01-18 11:41:55', 'Ahdaff'),
(719, NULL, '', 2, 1, '18:00:00', '2017-01-26', '00:00:03', '2017-01-18 11:42:06', 'Ahdaff'),
(720, NULL, '', 2, 1, NULL, NULL, NULL, '2017-01-18 11:42:30', 'Ahdaff'),
(721, NULL, '', 2, 1, NULL, NULL, NULL, '2017-01-18 11:45:54', 'Ahdaff'),
(722, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 11:58:38', 'ahd'),
(723, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 11:58:44', 'ahd'),
(724, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:06:01', ''),
(725, NULL, '', 2, 2, NULL, NULL, NULL, '2017-01-18 12:08:53', ''),
(726, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-18 12:09:05', ''),
(727, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:12:40', 'tt'),
(728, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:12:47', 'ahd'),
(729, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:14:08', ''),
(730, NULL, '', 1, 1, '01:00:00', '2017-01-13', '00:00:01', '2017-01-18 12:14:16', ''),
(731, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:14:21', ''),
(732, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-18 12:14:22', ''),
(733, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:16:18', ''),
(734, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:16:21', ''),
(735, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:16:24', ''),
(736, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:16:27', ''),
(737, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:16:33', ''),
(738, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:29:29', ''),
(739, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 12:39:15', ''),
(740, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 12:41:36', ''),
(741, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 12:49:59', ''),
(742, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:51:08', ''),
(743, NULL, 'null', 1, 1, '13:00:00', '2017-01-26', '00:00:01', '2017-01-18 12:53:30', ''),
(744, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 12:53:39', ''),
(745, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 12:53:53', ''),
(746, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:54:12', ''),
(747, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:55:35', ''),
(748, NULL, '', 1, 1, '10:00:00', '2017-01-18', '00:00:01', '2017-01-18 12:57:33', ''),
(749, NULL, '', 1, 1, '10:00:00', '2017-01-18', '00:00:01', '2017-01-18 12:57:44', ''),
(750, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:57:44', ''),
(751, NULL, '', 1, 1, '10:00:00', '2017-01-18', '00:00:01', '2017-01-18 12:58:23', ''),
(752, NULL, '', 1, 1, '10:00:00', '2017-01-18', '00:00:01', '2017-01-18 12:58:38', ''),
(753, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:58:47', ''),
(754, NULL, '', 1, 1, '01:00:00', '2017-01-18', '00:00:01', '2017-01-18 12:58:53', ''),
(755, NULL, '', 1, 1, '01:00:00', '2017-01-18', '00:00:01', '2017-01-18 12:59:29', ''),
(756, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 12:59:48', ''),
(757, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-18 12:59:56', ''),
(758, NULL, '', 1, 1, '05:00:00', '2017-01-19', '00:00:01', '2017-01-18 13:01:13', ''),
(759, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:01:23', ''),
(760, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:07:04', ''),
(761, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:09:15', ''),
(762, NULL, '', 1, 1, '01:00:00', '2017-01-04', '00:00:01', '2017-01-18 13:17:11', ''),
(763, NULL, '', 1, 1, '01:00:00', '2017-01-04', '00:00:01', '2017-01-18 13:17:48', ''),
(764, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:17:52', ''),
(765, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:20:15', ''),
(766, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:20:37', ''),
(767, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:23:40', ''),
(768, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:24:34', ''),
(769, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:28:28', ''),
(770, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:29:20', ''),
(771, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:31:31', ''),
(772, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:32:12', ''),
(773, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 13:34:31', ''),
(774, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 13:34:51', ''),
(775, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:35:33', ''),
(776, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:35:49', ''),
(777, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:37:34', ''),
(778, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:46:22', ''),
(779, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:46:39', ''),
(780, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:47:09', ''),
(781, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:51:22', ''),
(782, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:55:42', ''),
(783, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:56:15', ''),
(784, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:56:52', ''),
(785, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:57:55', ''),
(786, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:58:54', ''),
(787, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 13:59:36', ''),
(788, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:00:00', ''),
(789, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:00:46', ''),
(790, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:03:23', ''),
(791, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:04:58', ''),
(792, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:05:37', ''),
(793, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:09:27', ''),
(794, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:09:57', ''),
(795, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 14:11:22', ''),
(796, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:11:38', ''),
(797, NULL, 'null', 1, 1, '02:00:00', '2017-01-18', '00:00:01', '2017-01-18 14:11:46', ''),
(798, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:12:54', ''),
(799, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:13:24', ''),
(800, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:13:30', ''),
(801, NULL, '', 1, 1, '02:00:00', '2017-01-18', '00:00:01', '2017-01-18 14:15:15', ''),
(802, NULL, '', 1, 1, '06:00:00', '2017-01-19', '00:00:01', '2017-01-18 14:15:39', ''),
(803, NULL, 'null', 1, 1, '06:00:00', '2017-01-19', '00:00:01', '2017-01-18 14:15:52', ''),
(804, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:16:39', ''),
(805, NULL, 'null', 1, 1, '02:00:00', '2017-01-18', '00:00:01', '2017-01-18 14:17:28', ''),
(806, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:18:45', ''),
(807, NULL, 'null', 1, 1, '02:00:00', '2017-01-18', '00:00:01', '2017-01-18 14:18:52', ''),
(808, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:20:10', ''),
(809, NULL, 'null', 1, 1, '02:00:00', '2017-01-18', '00:00:01', '2017-01-18 14:21:20', ''),
(810, NULL, '', 1, 1, '01:00:00', '2017-02-16', '00:00:01', '2017-01-18 14:21:53', ''),
(811, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 14:22:38', ''),
(812, NULL, 'null', 1, 1, '02:00:00', '2017-01-18', '00:00:01', '2017-01-18 14:22:48', ''),
(813, NULL, 'null', 1, 1, '02:00:00', '2017-01-18', '00:00:01', '2017-01-18 14:23:12', ''),
(814, NULL, 'null', 1, 1, '02:00:00', '2017-01-18', '00:00:01', '2017-01-18 14:26:36', ''),
(815, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 14:26:45', ''),
(816, NULL, 'null', 1, 1, '02:00:00', '2017-01-18', '00:00:01', '2017-01-18 14:27:58', ''),
(817, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 14:28:15', ''),
(818, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:28:16', ''),
(819, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:29:23', ''),
(820, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:31:24', ''),
(821, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 14:31:50', ''),
(822, NULL, 'null', 1, 1, '03:00:00', '2017-01-18', '00:00:01', '2017-01-18 14:34:00', ''),
(823, NULL, 'null', 1, 1, '06:00:00', '2017-01-19', '00:00:01', '2017-01-18 14:34:12', ''),
(824, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:34:12', ''),
(825, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:47:24', ''),
(826, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 14:51:12', ''),
(827, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-18 14:55:09', ''),
(828, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 14:56:18', ''),
(829, NULL, 'null', 1, 1, '07:00:00', '2017-01-18', '00:00:01', '2017-01-18 14:57:03', ''),
(830, NULL, 'null', 1, 1, '01:00:00', '2017-01-18', '00:00:01', '2017-01-18 14:59:34', ''),
(831, NULL, 'null', 1, 1, '08:00:00', '2017-01-18', '00:00:01', '2017-01-18 14:59:43', ''),
(832, NULL, 'null', 1, 1, '07:00:00', '2017-01-27', '00:00:01', '2017-01-18 15:00:06', ''),
(833, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 15:00:55', ''),
(834, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 15:06:21', ''),
(835, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 15:07:59', ''),
(836, NULL, '', 1, 1, '01:00:00', '2017-01-19', '00:00:01', '2017-01-18 15:12:47', ''),
(837, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 15:12:50', ''),
(838, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 15:17:06', ''),
(839, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 15:52:46', ''),
(840, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 16:02:24', 'Ff'),
(841, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 16:02:28', ''),
(842, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 16:02:33', 'Ff'),
(843, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 16:02:37', ''),
(844, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 16:06:05', ''),
(845, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 16:19:58', ''),
(846, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 16:20:56', ''),
(847, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 16:26:01', ''),
(848, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-18 16:26:13', ''),
(849, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 05:49:05', 'test'),
(850, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 05:50:22', 'test'),
(851, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 05:50:27', ''),
(852, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 05:50:48', ''),
(853, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 05:51:32', ''),
(854, NULL, 'e0EMJ1VQQ4k:APA91bHpPygeJzUgt-i8nbdrO81NNs1x35Xrbo-Ec0v3wqTtVHYMj6JBf8DZN3TJdETSk6Hrlrk63tDU7np2-ccJlMnaQZ1R5GX8bK62ehaIgutHWyQd3V2oKp-99ja1lVN-xCOZOX4b', 1, 1, '18:00:00', '2017-01-24', '00:00:01', '2017-01-19 08:24:24', ''),
(855, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-19 08:57:48', ''),
(856, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-19 08:59:35', ''),
(857, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-19 09:48:31', ''),
(858, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-19 09:49:47', ''),
(859, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-19 09:54:51', ''),
(860, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 2, 1, NULL, NULL, NULL, '2017-01-19 09:54:59', ''),
(861, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-19 09:55:22', ''),
(862, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-19 09:55:47', ''),
(863, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-19 09:55:53', ''),
(864, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-19 09:57:18', ''),
(865, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 10:06:57', ''),
(866, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-19 10:07:51', ''),
(867, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-19 10:34:08', ''),
(868, NULL, '47F89EA3-8DD0-4F4F-9462-C440268058E5', 1, 1, NULL, NULL, NULL, '2017-01-19 10:34:51', ''),
(869, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 10:42:52', ''),
(870, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 10:43:30', ''),
(871, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-19 10:43:37', ''),
(872, NULL, '', 1, 2, '01:00:00', '2017-01-27', '00:00:01', '2017-01-19 10:43:45', ''),
(873, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 10:47:38', ''),
(874, NULL, '', 1, 1, '01:00:00', '2017-01-27', '00:00:01', '2017-01-19 10:47:44', ''),
(875, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 10:47:55', ''),
(876, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 10:48:00', ''),
(877, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 10:48:05', ''),
(878, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 13:07:12', ''),
(879, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 13:18:13', ''),
(880, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 13:22:29', ''),
(881, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 13:29:17', ''),
(882, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:29:22', ''),
(883, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 13:33:18', ''),
(884, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:49:28', ''),
(885, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:50:27', ''),
(886, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:50:57', ''),
(887, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:51:42', ''),
(888, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:52:46', ''),
(889, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:53:48', ''),
(890, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 13:53:59', ''),
(891, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:54:16', ''),
(892, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:55:17', ''),
(893, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:56:00', ''),
(894, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:56:29', ''),
(895, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:57:32', ''),
(896, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 13:58:41', ''),
(897, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:58:44', ''),
(898, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 13:59:22', ''),
(899, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 14:06:28', ''),
(900, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 14:07:12', ''),
(901, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 14:08:09', ''),
(902, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 14:09:23', ''),
(903, NULL, 'null', 1, 1, '01:00:00', '2016-11-01', '00:00:01', '2017-01-19 14:12:49', ''),
(904, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 14:14:50', ''),
(905, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 14:16:09', ''),
(906, NULL, '', 1, 1, '01:00:00', '2017-01-25', '00:00:01', '2017-01-19 14:16:21', ''),
(907, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 14:16:28', ''),
(908, NULL, '', 2, 1, NULL, NULL, NULL, '2017-01-19 14:28:28', ''),
(909, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 14:31:57', ''),
(910, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 14:33:33', ''),
(911, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 14:42:17', ''),
(912, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 14:43:40', ''),
(913, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 14:44:34', ''),
(914, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 14:50:10', ''),
(915, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 14:50:32', ''),
(916, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 15:00:39', ''),
(917, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 15:01:12', ''),
(918, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 15:02:08', ''),
(919, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 15:02:38', ''),
(920, NULL, '', 1, 1, '01:00:00', '2017-01-19', '00:00:01', '2017-01-19 15:06:27', ''),
(921, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 15:09:18', 'Hhg'),
(922, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 15:41:58', ''),
(923, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 15:42:44', ''),
(924, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 15:45:33', ''),
(925, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 16:08:48', ''),
(926, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 16:09:26', ''),
(927, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 16:15:13', ''),
(928, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 16:16:05', ''),
(929, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 16:16:36', ''),
(930, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 16:18:29', ''),
(931, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 16:19:57', ''),
(932, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 16:53:30', ''),
(933, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 17:08:47', ''),
(934, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 17:09:05', ''),
(935, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-19 17:09:20', ''),
(936, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 17:09:33', ''),
(937, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-19 17:09:47', ''),
(938, NULL, 'fTMD7ggDGqk:APA91bGdpSf2uQVEJ4bMTU4_Aar1MLng4dwgL0B-QHuLJRDsPJEMlC5mvDQ6XaZ6z34HPyEvbR5nHaQD7cqNUXRT9LjTXVhe7LBZmmweDmk5ijHPsjSuWY4r4m7JXIEUU01lvIBA-53o', 1, 1, NULL, NULL, NULL, '2017-01-20 07:14:45', ''),
(939, NULL, 'fTMD7ggDGqk:APA91bGdpSf2uQVEJ4bMTU4_Aar1MLng4dwgL0B-QHuLJRDsPJEMlC5mvDQ6XaZ6z34HPyEvbR5nHaQD7cqNUXRT9LjTXVhe7LBZmmweDmk5ijHPsjSuWY4r4m7JXIEUU01lvIBA-53o', 1, 1, '07:00:00', '2017-01-20', '00:00:01', '2017-01-20 07:17:09', ''),
(940, NULL, '123', 1, 1, NULL, NULL, NULL, '2017-01-20 12:44:23', ''),
(941, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-20 12:57:55', ''),
(942, NULL, 'null', 2, 1, NULL, NULL, NULL, '2017-01-20 13:22:25', ''),
(943, NULL, 'null', 2, 2, NULL, NULL, NULL, '2017-01-20 13:22:29', ''),
(944, NULL, 'null', 2, 2, '01:00:00', '2017-01-20', '00:00:01', '2017-01-20 13:22:55', ''),
(945, NULL, 'null', 2, 2, '01:00:00', '1900-04-25', '00:00:01', '2017-01-20 13:23:11', ''),
(946, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-20 13:24:10', ''),
(947, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-20 16:29:20', ''),
(948, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-20 17:39:08', ''),
(949, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-20 17:40:41', ''),
(950, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-21 07:01:56', ''),
(951, NULL, 'null', 1, 1, '01:00:00', '2017-01-21', '00:00:01', '2017-01-21 07:02:39', ''),
(952, NULL, 'null', 1, 1, '07:00:00', '2017-01-21', '00:00:01', '2017-01-21 07:02:56', ''),
(953, NULL, 'eTbqnpXLElQ:APA91bEphStmDbkWMvVFUSEIyBn_onAAYLuOo8dYToj5ddJkqF8sX0iMVGtJTOQF3ygSRMW2uL9hIr0CU41T0ejJY06yZmrNuzYCjLxWDfeTNfD8xJV7NyUSl9bG8TBmC30ZlDJZaqXy', 1, 1, NULL, NULL, NULL, '2017-01-21 16:08:45', ''),
(954, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, NULL, NULL, NULL, '2017-01-22 10:23:27', ''),
(955, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 10:30:32', 'ahdaf'),
(956, NULL, 'null', 1, 1, '01:00:00', '2017-01-22', '00:00:01', '2017-01-22 10:49:03', ''),
(957, NULL, 'null', 1, 1, '01:00:00', '2017-01-25', '00:00:01', '2017-01-22 10:49:10', ''),
(958, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 10:49:14', ''),
(959, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 10:53:19', ''),
(960, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 10:53:27', ''),
(961, NULL, 'enqXVtkTjjM:APA91bEHABSPWcx-r7Rirf5K6Mp4Gu-XseT6KB-SgoiZ5rSNIcbi28nTcQvvpseAgRQYC9BwnJcbyiJF4C_9BNca1RLz3YTB3v8zUNB4L0DS3NgDmzrO33BHYZE1u9tqY1zFCt1PTE3h', 1, 1, '01:00:00', '2017-01-22', '00:00:01', '2017-01-22 11:00:37', ''),
(962, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 11:00:43', ''),
(963, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 11:08:28', ''),
(964, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 11:12:59', ''),
(965, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 11:15:52', ''),
(966, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 11:21:00', ''),
(967, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 11:26:23', ''),
(968, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 11:39:35', ''),
(969, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 11:39:43', ''),
(970, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 11:45:34', ''),
(971, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 11:47:41', ''),
(972, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 11:49:34', ''),
(973, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 11:50:07', ''),
(974, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 11:51:07', ''),
(975, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 12:04:42', 'Ahead'),
(976, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 12:04:54', 'Ahdaff'),
(977, NULL, '', 1, 1, '01:00:00', '2017-01-24', '00:00:01', '2017-01-22 12:14:14', 'Ahdaff'),
(978, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 12:14:17', 'Ahdaff'),
(979, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 12:15:00', ''),
(980, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 12:18:57', ''),
(981, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 12:49:37', ''),
(982, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, '01:00:00', '2017-01-22', '00:00:01', '2017-01-22 13:02:12', ''),
(983, NULL, 'null', 1, 1, '09:00:00', '2017-01-24', '00:00:01', '2017-01-22 13:02:56', ''),
(984, NULL, '', 1, 1, '09:00:00', '2017-01-24', '00:00:01', '2017-01-22 13:02:56', ''),
(985, NULL, 'null', 1, 1, '09:00:00', '2017-01-24', '00:00:01', '2017-01-22 13:06:42', ''),
(986, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 13:10:57', ''),
(987, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 13:14:15', ''),
(988, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 13:24:05', ''),
(989, NULL, '', 1, 1, '09:00:00', '2017-01-24', '00:00:01', '2017-01-22 13:24:34', ''),
(990, NULL, '', 1, 1, '09:00:00', '2017-01-24', '00:00:01', '2017-01-22 13:24:38', ''),
(991, NULL, '', 1, 1, '09:00:00', '2017-01-24', '00:00:01', '2017-01-22 13:27:53', ''),
(992, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 13:29:36', ''),
(993, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 13:30:34', ''),
(994, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 13:31:39', ''),
(995, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 13:38:47', ''),
(996, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 13:43:50', 'ahdaf'),
(997, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 13:46:14', ''),
(998, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 13:57:34', ''),
(999, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 13:58:42', ''),
(1000, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 14:00:31', ''),
(1001, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 14:01:52', ''),
(1002, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 14:09:07', ''),
(1003, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 14:16:36', ''),
(1004, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 14:18:18', ''),
(1005, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 14:24:09', ''),
(1006, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 14:26:30', ''),
(1007, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 14:54:26', 'ahdaf'),
(1008, NULL, '0', 0, 0, NULL, NULL, NULL, '2017-01-22 14:54:58', 'ahdaf'),
(1009, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 15:02:57', ''),
(1010, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 15:08:00', ''),
(1011, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 15:12:28', ''),
(1012, NULL, '', 1, 1, '01:00:00', '2017-01-26', '00:00:01', '2017-01-22 15:51:58', ''),
(1013, NULL, '', 1, 1, '01:00:00', '2017-01-26', '00:00:01', '2017-01-22 15:52:02', ''),
(1014, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 15:52:05', ''),
(1015, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 15:53:08', ''),
(1016, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 15:53:45', ''),
(1017, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, NULL, NULL, NULL, '2017-01-22 15:56:31', ''),
(1018, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, '01:00:00', '2017-01-22', '00:00:01', '2017-01-22 15:57:11', ''),
(1019, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, NULL, NULL, NULL, '2017-01-22 15:59:37', ''),
(1020, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, NULL, NULL, NULL, '2017-01-22 16:02:25', ''),
(1021, NULL, 'dbDSgMPkbT8:APA91bGrPfFVgHXfR0NUrtM2Gh5bk6CJaDSFrt8Kt9SJAAx0BHaGneu6sIANIz-ydbtDBHmU7HegByfsxJ19HLeL-oSSXdOYl1qGq3fFKuz8OBkDzceuWBpvICuNSJxvrvb-UD3nZMI8', 1, 1, NULL, NULL, NULL, '2017-01-22 16:12:23', ''),
(1022, NULL, 'null', 0, 0, NULL, NULL, NULL, '2017-01-22 16:41:19', 'mm'),
(1023, NULL, 'null', 0, 0, NULL, NULL, NULL, '2017-01-22 16:41:25', 'mm'),
(1024, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, NULL, NULL, NULL, '2017-01-22 16:47:35', 'mm'),
(1025, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 16:53:27', 'Hj'),
(1026, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, NULL, NULL, NULL, '2017-01-22 16:54:13', ''),
(1027, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 16:59:03', 'Yy'),
(1028, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 16:59:16', 'Yy'),
(1029, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, NULL, NULL, NULL, '2017-01-22 17:04:29', ''),
(1030, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, NULL, NULL, NULL, '2017-01-22 17:04:55', ''),
(1031, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 17:08:33', 'F'),
(1032, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 17:15:25', ''),
(1033, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 17:16:06', ''),
(1034, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-22 17:16:39', ''),
(1035, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, NULL, NULL, NULL, '2017-01-22 17:16:51', ''),
(1036, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-22 17:20:51', ''),
(1037, NULL, 'e0EMJ1VQQ4k:APA91bHpPygeJzUgt-i8nbdrO81NNs1x35Xrbo-Ec0v3wqTtVHYMj6JBf8DZN3TJdETSk6Hrlrk63tDU7np2-ccJlMnaQZ1R5GX8bK62ehaIgutHWyQd3V2oKp-99ja1lVN-xCOZOX4b', 1, 1, '18:00:00', '2017-01-26', '00:00:01', '2017-01-22 19:10:35', ''),
(1038, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-23 09:34:21', ''),
(1039, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 09:40:27', ''),
(1040, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 09:52:28', ''),
(1041, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 10:02:58', ''),
(1042, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 11:16:47', ''),
(1043, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 12:04:16', ''),
(1044, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 13:25:30', ''),
(1045, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 13:29:40', ''),
(1046, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 13:42:17', ''),
(1047, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 13:42:28', ''),
(1048, NULL, 'null', 1, 1, '21:00:00', '2017-01-24', '00:00:01', '2017-01-23 13:52:31', ''),
(1049, NULL, 'null', 1, 1, '21:00:00', '2017-01-24', '00:00:01', '2017-01-23 13:52:56', ''),
(1050, NULL, 'null', 1, 1, '21:00:00', '2017-01-24', '00:00:01', '2017-01-23 13:54:36', ''),
(1051, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 15:23:03', ''),
(1052, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 15:24:35', ''),
(1053, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 15:30:46', ''),
(1054, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-23 15:35:43', ''),
(1055, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 15:41:49', ''),
(1056, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 15:44:02', ''),
(1057, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 15:53:28', ''),
(1058, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-23 16:05:26', ''),
(1059, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, NULL, NULL, NULL, '2017-01-23 16:07:10', ''),
(1060, NULL, 'e0EMJ1VQQ4k:APA91bHpPygeJzUgt-i8nbdrO81NNs1x35Xrbo-Ec0v3wqTtVHYMj6JBf8DZN3TJdETSk6Hrlrk63tDU7np2-ccJlMnaQZ1R5GX8bK62ehaIgutHWyQd3V2oKp-99ja1lVN-xCOZOX4b', 1, 1, '19:00:00', '2017-01-25', '00:00:01', '2017-01-23 20:43:20', ''),
(1061, NULL, 'e0EMJ1VQQ4k:APA91bHpPygeJzUgt-i8nbdrO81NNs1x35Xrbo-Ec0v3wqTtVHYMj6JBf8DZN3TJdETSk6Hrlrk63tDU7np2-ccJlMnaQZ1R5GX8bK62ehaIgutHWyQd3V2oKp-99ja1lVN-xCOZOX4b', 1, 1, NULL, NULL, NULL, '2017-01-23 20:43:34', ''),
(1062, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, NULL, NULL, NULL, '2017-01-24 09:15:12', ''),
(1063, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 09:18:00', ''),
(1064, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 09:22:07', ''),
(1065, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 09:32:05', ''),
(1066, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 09:47:00', ''),
(1067, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 09:54:25', ''),
(1068, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 09:55:08', ''),
(1069, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 10:01:35', ''),
(1070, NULL, 'ej81qNa8KZk:APA91bGZBTDNOBhwYvYXAI1GY6XONbjvB_vf4-Ns72QIEo2hXSSLJg5Dt6jqUWraRMrlq4_jDN4uGlhbv3rnW3NzZDtdawh7BAhlfeODou6290ZceMG46_OU1Wfx3bCyT-TZ-ASS6Wbs', 1, 1, NULL, NULL, NULL, '2017-01-24 10:24:00', ''),
(1071, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, '00:00:00', '2017-01-24', '00:00:00', '2017-01-24 10:54:22', ''),
(1072, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, '00:00:00', '2017-01-24', '00:00:00', '2017-01-24 10:56:04', ''),
(1073, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, '00:00:00', '2017-01-24', '00:00:00', '2017-01-24 10:56:45', ''),
(1074, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 10:58:33', ''),
(1075, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, '00:00:00', '2017-01-24', '00:00:00', '2017-01-24 10:58:59', ''),
(1076, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 10:59:30', ''),
(1077, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:00:55', ''),
(1078, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:01:43', ''),
(1079, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, NULL, NULL, NULL, '2017-01-24 11:02:36', ''),
(1080, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:02:45', ''),
(1081, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:02:50', ''),
(1082, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, NULL, NULL, NULL, '2017-01-24 11:03:57', ''),
(1083, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:04:22', ''),
(1084, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:04:38', ''),
(1085, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, NULL, NULL, NULL, '2017-01-24 11:06:35', ''),
(1086, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:08:00', ''),
(1087, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:08:23', ''),
(1088, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:08:31', ''),
(1089, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, NULL, NULL, NULL, '2017-01-24 11:10:05', ''),
(1090, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, NULL, NULL, NULL, '2017-01-24 11:13:16', ''),
(1091, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:15:08', ''),
(1092, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:15:15', ''),
(1093, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, NULL, NULL, NULL, '2017-01-24 11:24:14', ''),
(1094, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:24:45', ''),
(1095, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, NULL, NULL, NULL, '2017-01-24 11:28:50', ''),
(1096, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:29:05', ''),
(1097, NULL, 'cmVPJfGcsd0:APA91bGFKhUCyxuoIsnZUE03Pjtoe_xAMRsaBSWry9uUf2iYclsW87e1YulraLQyObL56Er2dK8Jz87KhB-hCB1QN2BW5JRsl10uvta78VLSm046hig410rtf913gvi_N-yO-QGxTlgX', 1, 1, NULL, NULL, NULL, '2017-01-24 11:31:04', ''),
(1098, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:36:08', ''),
(1099, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:40:38', ''),
(1100, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, NULL, NULL, NULL, '2017-01-24 11:40:53', ''),
(1101, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:44:14', ''),
(1102, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:47:10', ''),
(1103, NULL, 'fwUXXnNmKJQ:APA91bHEGrWu17SqkZUl_wuKfK4ityPlG7g1Dgln526rA4vPlGyH-scbbEcGXFSnwedxlxO0ABzd_7ZO81xoUuluQq3gs9c1EOp0OCZlIUH1OLpPPjhv_irMgK8Zr1RmCWA46zOI__c7', 1, 1, NULL, NULL, NULL, '2017-01-24 11:48:58', ''),
(1104, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, NULL, NULL, NULL, '2017-01-24 11:53:13', ''),
(1105, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 11:58:48', ''),
(1106, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 12:09:24', ''),
(1107, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 12:10:31', ''),
(1108, NULL, 'e0KIe93kKwY:APA91bFjzogpAsRQrlSrwLECWXp9C4klgkSHIUEmkMvGbayIvcYFQNQ4rnm7bdPMfywj4Rorc276CN3PlaTpNG_GGUMifO7YK-mQXYHmz2zjJmLsZb9rDtbFxu5ceG9VSZILqW81YgGL', 1, 1, NULL, NULL, NULL, '2017-01-24 12:35:59', ''),
(1109, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 12:39:02', ''),
(1110, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 12:40:07', ''),
(1111, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 12:40:39', ''),
(1112, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 12:41:46', ''),
(1113, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 12:47:06', ''),
(1114, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 12:50:13', ''),
(1115, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 12:56:26', ''),
(1116, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:09:35', ''),
(1117, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:10:17', ''),
(1118, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:10:31', ''),
(1119, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:10:39', ''),
(1120, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:10:45', ''),
(1121, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:10:51', ''),
(1122, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:10:54', ''),
(1123, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:11:10', ''),
(1124, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 13:12:11', ''),
(1125, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 13:12:28', ''),
(1126, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:17:06', ''),
(1127, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:22:35', ''),
(1128, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:31:15', '');
INSERT INTO `search` (`search_record_id`, `player_id`, `token`, `area_id`, `game_type_id`, `start`, `date`, `duration`, `search_date`, `text`) VALUES
(1129, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:43:57', ''),
(1130, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:48:12', ''),
(1131, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:50:36', ''),
(1132, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-24 13:52:28', ''),
(1133, NULL, '', 2, 1, NULL, NULL, NULL, '2017-01-24 13:52:53', ''),
(1134, NULL, '', 2, 2, NULL, NULL, NULL, '2017-01-24 13:53:05', ''),
(1135, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-24 13:53:10', ''),
(1136, NULL, '', 1, 2, NULL, NULL, NULL, '2017-01-24 13:53:45', ''),
(1137, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:54:32', 'The'),
(1138, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:54:56', 'The'),
(1139, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:55:02', 'Te'),
(1140, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:56:40', ''),
(1141, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:56:50', 'Te'),
(1142, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 13:58:30', ''),
(1143, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 13:58:48', ''),
(1144, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 13:59:12', 'Hgj'),
(1145, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:00:18', 'Jj'),
(1146, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:02:54', 'test'),
(1147, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:04:19', 'ggg'),
(1148, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:05:36', 'yy'),
(1149, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 14:08:29', ''),
(1150, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:15:31', ''),
(1151, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:17:23', ''),
(1152, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:18:49', ''),
(1153, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:21:03', ''),
(1154, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:21:17', ''),
(1155, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:22:35', ''),
(1156, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 14:23:10', ''),
(1157, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 14:23:26', ''),
(1158, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:23:41', ''),
(1159, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:23:54', ''),
(1160, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:24:16', ''),
(1161, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:24:31', ''),
(1162, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:26:33', ''),
(1163, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:26:36', ''),
(1164, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 14:28:07', ''),
(1165, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 14:31:45', ''),
(1166, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:32:50', ''),
(1167, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 14:47:37', ''),
(1168, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 14:50:46', ''),
(1169, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 14:51:09', ''),
(1170, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 14:51:44', ''),
(1171, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:53:34', ''),
(1172, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:54:59', ''),
(1173, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:55:04', ''),
(1174, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:55:40', ''),
(1175, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:56:12', ''),
(1176, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:56:18', ''),
(1177, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 14:56:40', ''),
(1178, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 14:56:50', ''),
(1179, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 14:56:59', ''),
(1180, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 14:57:07', ''),
(1181, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 14:57:37', ''),
(1182, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 15:02:21', ''),
(1183, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-24 15:02:59', ''),
(1184, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-24 15:03:05', ''),
(1185, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-24 15:03:12', ''),
(1186, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:03:26', ''),
(1187, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 15:13:26', ''),
(1188, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:23:04', ''),
(1189, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:23:17', ''),
(1190, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:23:29', ''),
(1191, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:23:45', ''),
(1192, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:23:47', ''),
(1193, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:24:25', ''),
(1194, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:24:36', ''),
(1195, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:26:07', ''),
(1196, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:26:20', ''),
(1197, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:26:28', ''),
(1198, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:27:14', ''),
(1199, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:28:05', ''),
(1200, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:28:11', ''),
(1201, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:28:41', ''),
(1202, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:28:57', ''),
(1203, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 15:29:39', ''),
(1204, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:30:54', ''),
(1205, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:32:08', ''),
(1206, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:33:17', ''),
(1207, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:36:22', ''),
(1208, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:38:06', ''),
(1209, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:38:16', ''),
(1210, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:39:10', ''),
(1211, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 15:39:18', ''),
(1212, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:41:58', ''),
(1213, NULL, 'cujhUfuTvj8:APA91bHUC1i7pJ9VPSxRaEITj2tkBjTe0pQ2pEGS8RHBD2FN5HbrkwIPB2lmfCcRm4fD9tGVKiLGyJTP7sMMVWfvaxgUoSrU_nBVXa5oRUOK1TdVh51G590Suxw3dubpqnguApbmrNYF', 1, 1, NULL, NULL, NULL, '2017-01-24 15:48:34', ''),
(1214, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:50:50', ''),
(1215, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:51:05', ''),
(1216, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:51:09', ''),
(1217, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:52:55', ''),
(1218, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:53:17', ''),
(1219, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:54:02', ''),
(1220, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:54:22', ''),
(1221, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:57:13', ''),
(1222, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:57:34', ''),
(1223, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:58:46', ''),
(1224, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:58:50', ''),
(1225, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:59:00', ''),
(1226, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 15:59:12', ''),
(1227, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 16:02:52', ''),
(1228, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 16:03:18', ''),
(1229, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-24 16:09:32', 'y'),
(1230, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 16:15:58', ''),
(1231, NULL, 'evTb_iJMVaQ:APA91bGTRTgNbCGfmfW_KURfUYABKsYT6o7CjfK5OJNOJ9AfIbdvbcKQzlvmyrXyobWMwc6jWORPRLLYqHUi07UP2XuyaBYVqq5FK5cS8RvswGbOSKni8_WieZ1MvXCc1D8p9Qb4UaiD', 1, 1, NULL, NULL, NULL, '2017-01-24 16:17:54', ''),
(1232, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-24 16:28:14', ''),
(1233, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 16:34:33', ''),
(1234, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 16:35:47', ''),
(1235, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 16:36:15', ''),
(1236, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 16:36:39', ''),
(1237, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-24 16:36:49', ''),
(1238, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 16:37:05', ''),
(1239, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 16:37:15', ''),
(1240, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 16:37:27', ''),
(1241, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 16:37:35', ''),
(1242, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-24 16:38:15', ''),
(1243, NULL, 'evTb_iJMVaQ:APA91bGTRTgNbCGfmfW_KURfUYABKsYT6o7CjfK5OJNOJ9AfIbdvbcKQzlvmyrXyobWMwc6jWORPRLLYqHUi07UP2XuyaBYVqq5FK5cS8RvswGbOSKni8_WieZ1MvXCc1D8p9Qb4UaiD', 1, 1, NULL, NULL, NULL, '2017-01-24 16:38:46', ''),
(1244, NULL, 'evTb_iJMVaQ:APA91bGTRTgNbCGfmfW_KURfUYABKsYT6o7CjfK5OJNOJ9AfIbdvbcKQzlvmyrXyobWMwc6jWORPRLLYqHUi07UP2XuyaBYVqq5FK5cS8RvswGbOSKni8_WieZ1MvXCc1D8p9Qb4UaiD', 1, 1, NULL, NULL, NULL, '2017-01-24 16:40:03', ''),
(1245, NULL, 'evTb_iJMVaQ:APA91bGTRTgNbCGfmfW_KURfUYABKsYT6o7CjfK5OJNOJ9AfIbdvbcKQzlvmyrXyobWMwc6jWORPRLLYqHUi07UP2XuyaBYVqq5FK5cS8RvswGbOSKni8_WieZ1MvXCc1D8p9Qb4UaiD', 1, 1, NULL, NULL, NULL, '2017-01-24 16:42:06', ''),
(1246, NULL, 'evTb_iJMVaQ:APA91bGTRTgNbCGfmfW_KURfUYABKsYT6o7CjfK5OJNOJ9AfIbdvbcKQzlvmyrXyobWMwc6jWORPRLLYqHUi07UP2XuyaBYVqq5FK5cS8RvswGbOSKni8_WieZ1MvXCc1D8p9Qb4UaiD', 1, 1, NULL, NULL, NULL, '2017-01-24 16:42:42', ''),
(1247, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-24 16:42:57', ''),
(1248, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-24 16:45:14', ''),
(1249, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-24 16:46:26', ''),
(1250, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-24 16:47:26', ''),
(1251, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-24 16:48:24', ''),
(1252, NULL, 'evTb_iJMVaQ:APA91bGTRTgNbCGfmfW_KURfUYABKsYT6o7CjfK5OJNOJ9AfIbdvbcKQzlvmyrXyobWMwc6jWORPRLLYqHUi07UP2XuyaBYVqq5FK5cS8RvswGbOSKni8_WieZ1MvXCc1D8p9Qb4UaiD', 1, 1, NULL, NULL, NULL, '2017-01-24 16:49:16', ''),
(1253, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-24 16:50:37', ''),
(1254, NULL, 'evTb_iJMVaQ:APA91bGTRTgNbCGfmfW_KURfUYABKsYT6o7CjfK5OJNOJ9AfIbdvbcKQzlvmyrXyobWMwc6jWORPRLLYqHUi07UP2XuyaBYVqq5FK5cS8RvswGbOSKni8_WieZ1MvXCc1D8p9Qb4UaiD', 1, 1, NULL, NULL, NULL, '2017-01-24 16:53:05', ''),
(1255, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 16:54:43', ''),
(1256, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-24 17:16:10', ''),
(1257, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-25 09:22:10', ''),
(1258, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-25 09:37:37', ''),
(1259, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-25 09:42:28', ''),
(1260, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-25 09:48:20', ''),
(1261, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 2, 1, NULL, NULL, NULL, '2017-01-25 09:48:28', ''),
(1262, NULL, '', 1, 1, NULL, NULL, NULL, '2017-01-25 09:51:06', ''),
(1263, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-25 09:56:47', ''),
(1264, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-25 09:56:57', ''),
(1265, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-25 09:57:00', ''),
(1266, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-25 09:57:09', ''),
(1267, NULL, 'evTb_iJMVaQ:APA91bGTRTgNbCGfmfW_KURfUYABKsYT6o7CjfK5OJNOJ9AfIbdvbcKQzlvmyrXyobWMwc6jWORPRLLYqHUi07UP2XuyaBYVqq5FK5cS8RvswGbOSKni8_WieZ1MvXCc1D8p9Qb4UaiD', 1, 1, NULL, NULL, NULL, '2017-01-25 09:59:26', ''),
(1268, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-25 10:01:44', ''),
(1269, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-25 10:01:53', ''),
(1270, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-25 10:02:00', ''),
(1271, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-25 10:02:08', ''),
(1272, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-25 10:02:17', ''),
(1273, NULL, 'null', 1, 1, NULL, NULL, NULL, '2017-01-25 10:05:26', 'ahdaff'),
(1274, NULL, 'null', 0, 0, NULL, NULL, NULL, '2017-01-25 10:05:31', 'ahdaff'),
(1275, NULL, '0', 1, 1, NULL, NULL, NULL, '2017-01-25 10:15:09', ''),
(1276, NULL, 'fWHGubdYV6c:APA91bGmtmTf4ZnMXD_sn-HrlL-wAI_TpUsLaRvCkGNI4BpFLUbBgoObjnHC_Hz5Ou5ic4IxVQoVbN2nE6JMbT-gHmTg8-qrAh01d1lclftb78OAAfbNWeshinnpgDF_93rMrr2ksPjN', 1, 1, NULL, NULL, NULL, '2017-01-25 10:15:55', '');

-- --------------------------------------------------------

--
-- Table structure for table `settings`
--

CREATE TABLE `settings` (
  `id` int(11) NOT NULL,
  `about` text CHARACTER SET latin1 NOT NULL,
  `phone` varchar(50) CHARACTER SET latin1 NOT NULL,
  `mobile` varchar(50) CHARACTER SET latin1 NOT NULL,
  `email` varchar(50) CHARACTER SET latin1 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `settings`
--

INSERT INTO `settings` (`id`, `about`, `phone`, `mobile`, `email`) VALUES
(1, '', '', '00491735750491', 'info@fieldium.com');

-- --------------------------------------------------------

--
-- Table structure for table `state`
--

CREATE TABLE `state` (
  `state_id` int(11) NOT NULL,
  `en_name` varchar(45) DEFAULT NULL,
  `ar_name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `state`
--

INSERT INTO `state` (`state_id`, `en_name`, `ar_name`) VALUES
(1, 'pending', ''),
(2, 'approved', ''),
(3, 'declined', ''),
(4, 'cancelled', '');

-- --------------------------------------------------------

--
-- Table structure for table `tokens`
--

CREATE TABLE `tokens` (
  `token_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `os` varchar(30) NOT NULL DEFAULT 'android',
  `token` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tokens`
--

INSERT INTO `tokens` (`token_id`, `user_id`, `os`, `token`) VALUES
(3, 1, 'android', 'dW5HEMuOv68:APA91bFP6Zaig2YCLVonzS_dZYt74UxAZVGluoMyGPUbPZUq6P_oqoDQMJIcjMkOeqf6HvbwO8HjuVgXci86xnO9qiSQCLReliU4Z5jEQQ8ecD91TDLgiTXz0WbeDwm8pXP8WCKgnuFs'),
(5, 1, 'android', 'dmBOD3qPxCM:APA91bHEN91ISD60uI8lnHIQftTV-vOC2jV243dw2PHmihRODflIKTGVQORlud52BbdlZlvCx82yTyBZamhlk_uxj0wHgyxUVTOSq5XqryhsshhElg1jkG5s10IQaHQ_IVtMZdQzdECU'),
(6, 1, 'android', '222222'),
(7, 1, 'android', 'null'),
(8, 1, 'android', '1'),
(9, 1, 'android', 'e8m5Cpt0uxs:APA91bHZQlBWtc8S5xdvudLrjaEicB0xb4ldXAJEXvG_K7vMLC_hoeCUdCt2-mVNL0L3pdWy5p1D0rXAv1oKQlS62axGaFohX6vMH2gS8PxGBIFv_80DLYM4vzSCINquO87rtLqyBXhm'),
(10, 1, 'android', 'cnPf25lkWd0:APA91bF1SpInPjCH109Dn6zCtLTSf6yPg1LnDGZf1vEG6mwOPuCuOP-55HOD0DOL9E_RwvNbgvgCgZ6Rho209IWmAUG8hke6vImtjsmm0DVvilY5JFVgeMLZaL4nLHpnx1gzNXJLGFrx'),
(11, 1, 'android', 'f8k1Hg4a30c:APA91bG6faSAz5-KEsmM8Vfmgm2-Ft0V3sZW-ynapKloy051S8JHlWmFPMSlZHUj7eUDg6qBooF6s5UnjSMlR-5ZPy92AMgzn2gEBn2dUHpygS4BhgMn87sTrFnvAHdBuRf5acDi9anq'),
(13, 1, 'android', 'ec8i7R0LXa8:APA91bE2sqxDlJQ0DdVMpvIld5uFZQ9tlgUtUl2RZyMfCGLZdYR-4-dqMdam6ItqZ9z6G6G59iY6fk4dpkBmh1PN2tnY6loXb5ijemT1KRdFJu_3QbjZ1vSkjJZ6JTCkt3agwHhTbgi4'),
(14, 1, 'android', 'ejNLENYOMho:APA91bE0lrjwomzJtSm6HwrM8k7kF-Z6vdWrH13rERymDUp9a0El1fxkS4d-VGwYTvuyZ0q2XsXMEEuq79K20kQQ-wH7lzEhKsxiZ2VOr2pUMK3gOyGZ0ZY-5YCqokzgLrhr2bVRwQhd'),
(16, 1, 'android', 'c7SEJxcYJIw:APA91bGSdTrutAbNrl3o7REo-zUuAmWJ_wJabUiZFrzYR5eyFVujuRZ9xfAqVWCaZBPKWSJCdZW5gX-JWjnx_Ojcs5flUBG0cMCVAKyPfK-gzLE8fz-thC2e73gtiN7gueW5yBBEO-TC'),
(17, 1, 'android', 'flWx_9gxB1Y:APA91bHm8aNmG3oGUTVNLZhB_QJGE_m314m3Zl41JxA1u3HPVlxvWNh-NWTGDh1UnoZXacbsokEFpc_2Ml_PFKz_yvzddqkAU0D07DJqfSQXJhBVM60wWXxEfzX2c4fzHvpWlskSdKW2'),
(18, 1, 'android', 'fRG5oH0UDrw:APA91bH2RH1ZwlNC-_BtHlZ5xtF-YeDO_kHz4772PLPjMJO6j-xRdslXgfVrImYSqKUlLGn99k7a4crWpRpsCHwy8j6PL83IWh-o8dKtlgq3d7vqnxDuQD-z3fadRTmVAcOPuIn_9JUy'),
(19, 1, 'android', 'eeoUpOXK9e4:APA91bEFvBWfCuHAHrZzRoeKAkW76d70khic8pgeWUVi7F8w_cJmUxoLgSm2_GNA1CySgVmOa8xJIujZ04CT511xoEJ1MstMX-Fz3h5xiOjeqKPRPheEticJb9yhzkFLkiXZoxU0WMlV'),
(20, 1, 'android', 'cIMji1Mx8EM:APA91bEYu-LDmslA_wTH9OX2SsAz8JMw9p7DLRpnkPw28QL5BFj2py777WFGA0HQlvnMAaklutiSqZEzaPCsvyMEGjlEPNUS8-BJ5aSZ7DcDaXaoTZtgQZEOARtnlZuPjEA6cQ-pw5uy'),
(21, 1, 'android', 'ctAeQwGMfI4:APA91bHRWgRL4RitS6clPXCIW7xikUKObh22cMzmRdYgQvOX5AiOEyl0DwONTo6BcQsR32eW_3r8gsHc8d4NGqE4VjG7Y7CE-pCBD1nzZISNuEY9_mkwMEpwWtkrXmhxsDrNl3HzYHcR'),
(25, 3, 'android', 'fLQAjhOzukA:APA91bFTCxMLpbxTlN-Vcn6LEoQvYHfrOGgKkY20vRB6CD6I2Hln4hP-fWMpaQ2qgyFZsgD1jMOfeSftKikXDjpZxocuSLs_z2uqfyhFQujZn4H1DnJIHhQB1-eLrhRnw8M6Y0TN0vHP'),
(26, 1, 'android', 'dhPa1CcGT6Q:APA91bGxqpOQ28awVsrhdBvHFbB3Lk-pWsBx7_4PFDD5E3dGxJ0NnYHVnj0wKWnkR6TenGohOU4Zglz6h_BjFd-8phZ60N_IZOuRkPysUHmqtL_kEbkocn38y9cLsMM96wOqeareRci8'),
(27, 1, 'android', 'dZz09_mWYlQ:APA91bGoz8DI215f_SzvPnIaRbBr7m8lFt_TXMNn0nOU6I52eSkZGfhtZLK5gCvI51vuhiGO1au2IKyUdD8rucH5y4Jfa9nobPs3nf9yJP1K9At7slus4MlSIKV8DPCl7Gnn1WwItEGs'),
(30, 4, 'android', 'frBIUXkPNtw:APA91bH1uiSkxhG8YvaFo2u4gdfWTXqbD0RenxVniPCj3tRTur3sKN06pCSoh29sLQ34_NAT4C9Kquc_um34XGHjtCXNPyjPzPC8yXTqXRIIwinin5U6QPdHL9Jbxmj8pU2DM8RcLvTK'),
(31, 1, 'android', 'frBIUXkPNtw:APA91bH1uiSkxhG8YvaFo2u4gdfWTXqbD0RenxVniPCj3tRTur3sKN06pCSoh29sLQ34_NAT4C9Kquc_um34XGHjtCXNPyjPzPC8yXTqXRIIwinin5U6QPdHL9Jbxmj8pU2DM8RcLvTK'),
(32, 1, 'android', 'eCkFR8ni42E:APA91bGNufh1EZCR8xaywajzohvoZkc7LelmFWjQ0d_viHubqFD5pj9lIOizW9X6EZMplgg0D_G0Wm8j_ESY0hskjmVjJrs6mgvwJCSxPAG4kBGvCMyf7Zfb1Q3noRmUzf9LKF4EpnlA'),
(34, 1, 'android', 'cclo3LWPirM:APA91bHIcUulQ8pe7jgZZJ-1znyIvJDUDDUi_5YTtr6tV42vCCEonbhdzMv1sI8pG8JQDCWBcRA6RcnrQl2P6kj6F6shUrvmnM0io5J8uuP5GiQ50VJQeLZmpiqQNrJapRLjZjoDSv8O'),
(35, 2, 'android', 'fVyhjTHjUyE:APA91bFQFitRMKucgakViI6jEERB9390mLWsanjsz8Aj6p_aAw0BJ_-DeuPDn2ZUE4VAhnRjDke97xwdnxR2Yh85nAftOEUxDBl7rrJge5wSAITSNqTnrSN7qJ__Tt-CTrZjWTUTlRAD'),
(37, 1, 'android', 'c72IWVy_wI0:APA91bErj8DqOMnbja70kYKOYkhzxSZv5_rpTO3cpZK1gLjtlKM_0_RPbsAyl8OvMf9xMZjZlQEKmK6AaOiORbdW8e1AYbWGm3HZfH7pnKLdd216Fm4EZThIOuBJUc7gQ3UgKjZdVNp5'),
(38, 1, 'android', 'dmHXfgxRLwU:APA91bEPOkNGFNcepzGDzyc9VjTLLlApAaFr8pyDOsSHOAR_9ZHEeJc8sLP7xAawjP1B2JePHHHGxTLKnwgE2nLnin7dHgEYq4-A97L7EHURFJ_t0Qttb5fCQ8IuPwQAg9RsnTAg-wR6'),
(40, 2, 'android', 'cRa2o_vEhJ0:APA91bHBnn5MLkFme1baCbQEJSzBvqEQvWh8_xpTW9UxqxwhI--JFA0GIPysO1J0EmKvHcTaqhpXoyucskyOClXcNejR-_3zmzyCJ-IRCBVXQAJFG36EOxg9ilEuY2oDCOPkUlH7i2Vx'),
(41, 1, 'android', 'fKTKz25hY-s:APA91bH1zWaw4GHWWWJYulf35quMsGAUROe0_JOd6yMP_NBmRV1wtqISSAyBK7tvqPMLiRi2cgBXIBWAjWcr-hscvZsernEBQH6rsaE5Fh14SaySGkp8QMyk3v4f5VeQ8ORGW5br-fBZ'),
(47, 2, 'android', 'dcdaqRoduas:APA91bFJojxECApBIblAWsw5MrCrehX63DSLMFbSJeHi4nFtq5ry2RWPmyl4gYadbttmnVnl7MQ59CQf76haFaoUf2kcbCAQM2xsGmvPFFObrluETHxrFGDfJapjBLEdjbXPxqtojk1e'),
(50, 1, 'android', 'd7BjKRHbqnY:APA91bHae5g45GTeT5UtpQaTfQpNxNQExaIi5lu2yvAUux_6TZSSp6H106rVF9NU3ysx_vrwCLvgUtZtwZ_553-Xu8X1H4LLUG_62KSRZ963Ol0c09OoNq3cpYIRYYjtGaMVDZovtUWd'),
(53, 1, 'android', 'cjZGn59FpnE:APA91bEJbCpAURNzBVqNzuz436cCk9aufhEp4YjwLqwxnvXDA9_PkcsKDAbmJfS7bYJkXnFfeIalzBE6b-3FX3weZhwmQsIdQYkFdWa8QUELPhRkxNSGOIbyUfEukre3hpt27crqRhhW'),
(54, 2, 'android', 'dXCTaC8UWfk:APA91bGracJ8MIAsfhycTTg7SesU0BY32QSdVjp5I9sEr86D0NKY_wcKrWZfET2U-y4V8R7CjnwQ6DkSa7cEYWYrSCDI_k2RqRsDvdoZOtyBFKUCfHKe8V5Zy-qMHOP_ZZkVwbOgQsc1'),
(56, 2, 'android', 'chAQkFBojew:APA91bF0ujOR6zuP-zaTAqVvaPgFMzg7fsgyxKwrm8jFkR_mtn7tOGXp5wLgGuAY3ED96nc0RfdCNgfRCTi7--VWfQQZ1yww9vM9PvjlPnE3_6LpsqQ4OYPEgiVr21EUJdgbRCxQrFXR'),
(57, 1, 'android', 'd5qFrKwnCHE:APA91bEsrpe7oAb6tFYGwq1z_F5Q01kBCTfIimrAqT1db0iAPIJv6-2EuZ01LoK9y29E8iCwbEzwf1UPjtb3j9OjqrTlxusvgVW9Xxyth7yKbPRJB7XdSPWZlcugEeVbnojxjtqhH_uv'),
(58, 1, 'android', 'dXCTaC8UWfk:APA91bGracJ8MIAsfhycTTg7SesU0BY32QSdVjp5I9sEr86D0NKY_wcKrWZfET2U-y4V8R7CjnwQ6DkSa7cEYWYrSCDI_k2RqRsDvdoZOtyBFKUCfHKe8V5Zy-qMHOP_ZZkVwbOgQsc1');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `username` varchar(40) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `profile_picture` varchar(45) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `active` tinyint(1) DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`user_id`, `name`, `username`, `password`, `phone`, `email`, `profile_picture`, `company_id`, `role_id`, `active`) VALUES
(1, 'admin1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, 2, 1, 1),
(2, 'support', 'support_admin', 'e10adc3949ba59abbe56e057f20f883e', NULL, NULL, NULL, 1, 2, 1),
(4, 'Aktive ', 'Aktive-admin', '96e79218965eb72c92a549dd5a330112', NULL, NULL, NULL, 9, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `website_messages`
--

CREATE TABLE `website_messages` (
  `id` int(11) NOT NULL,
  `name` varchar(45) CHARACTER SET utf8 NOT NULL,
  `phone` varchar(45) NOT NULL,
  `email` varchar(60) NOT NULL,
  `subject` varchar(45) CHARACTER SET utf8 NOT NULL,
  `text` text CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `website_messages`
--

INSERT INTO `website_messages` (`id`, `name`, `phone`, `email`, `subject`, `text`) VALUES
(1, 'Amal abd', '', 'amalabdulraouf@gmail.com', 'test test', 'hello it is a test'),
(2, 'admin1', '', '0', '23424', 'hey'),
(3, 'admin1', '', '0', '23424', 'hey'),
(4, '', '', '0', '23424', 'hey'),
(5, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(6, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(7, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(8, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(9, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(10, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(11, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(12, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(13, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(14, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(15, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(16, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(17, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(18, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(19, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(20, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(21, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(22, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(23, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(24, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(25, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(26, '', '11', 'yahyatabba@gmail.com', 'Fieldium Contact us', 'Test\n'),
(27, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(28, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(29, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(30, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(31, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(32, '', '22', 'yahya@gmail.com', 'Fieldium Contact us', 'test\n'),
(33, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(34, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(35, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(36, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(37, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(38, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(39, '', '22', 'yahya@gmail.com', 'Fieldium Contact us', 'ggg'),
(40, '', '22', 'yahya@gmail.com', 'Fieldium Contact us', 'bb'),
(41, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(42, '', '22', 'yahya@gmail.com', 'Fieldium Contact us', 'hh'),
(43, '', '11', 'yahyatabba@gmail.com', 'Fieldium Contact us', 'Sdsd\n'),
(44, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(45, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(46, '', '22', 'yahya@gmail.com', 'Fieldium Contact us', 'gggg'),
(47, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(48, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(49, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(50, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(51, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(52, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(53, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(54, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(55, '', '11', 'yahyatabba@gmail.com', 'Fieldium Contact us', 'Test\n'),
(56, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(57, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(58, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(59, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(60, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(61, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(62, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(63, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(64, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(65, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(66, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(67, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(68, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(69, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(70, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(71, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(72, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(73, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(74, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(75, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(76, '', '23424', '0', 'Fieldium Contact us', 'hey'),
(77, '', '11', 'yahyatabba@gmail.com', 'Fieldium Contact us', 'Test Mail\n\n3rows\n'),
(78, '', '9898', '', 'Fieldium Contact us', 'Test'),
(79, '', '993399999', 'gggh@f.g', 'Fieldium Contact us', 'ggbbhv'),
(80, '', '993399999', 'gggh@f.g', 'Fieldium Contact us', 'ggbbhv'),
(81, '', '993308523', 'fb@tg.gf', 'Fieldium Contact us', 'cjf'),
(82, '', '963963555', 'ggs@fd.db', 'Fieldium Contact us', 'ببىى'),
(83, '', '993385858', '', 'Fieldium Contact us', 'ffhn'),
(84, '', '993352318', 'dg@d.g', 'Fieldium Contact us', 'gg'),
(85, '', '934610552', '', 'Fieldium Contact us', 'test'),
(86, '', '502799496', 'zak8686@hotmail.com', 'Fieldium Contact us', 'Hi'),
(87, '', '502799496', 'zak8686@hotmail.com', 'Fieldium Contact us', 'Hi');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `amenity`
--
ALTER TABLE `amenity`
  ADD PRIMARY KEY (`amenity_id`);

--
-- Indexes for table `area`
--
ALTER TABLE `area`
  ADD PRIMARY KEY (`area_id`);

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`booking_id`),
  ADD KEY `field_id_booking_fk_idx` (`field_id`),
  ADD KEY `state_id_booking_fk_idx` (`state_id`),
  ADD KEY `player_id_booking_fk_idx` (`player_id`),
  ADD KEY `user_id_booking_fk_idx` (`user_id`),
  ADD KEY `game_type_booking` (`game_type_id`);

--
-- Indexes for table `company`
--
ALTER TABLE `company`
  ADD PRIMARY KEY (`company_id`),
  ADD KEY `area_id_company_fk_idx` (`area_id`);

--
-- Indexes for table `field`
--
ALTER TABLE `field`
  ADD PRIMARY KEY (`field_id`),
  ADD KEY `company_id_field_fk_idx` (`company_id`);

--
-- Indexes for table `field_amenity`
--
ALTER TABLE `field_amenity`
  ADD PRIMARY KEY (`field_amenity_id`),
  ADD KEY `field_id_idx` (`field_id`),
  ADD KEY `amenity_id_idx` (`amenity_id`);

--
-- Indexes for table `field_game_type`
--
ALTER TABLE `field_game_type`
  ADD PRIMARY KEY (`field_game_type_id`),
  ADD KEY `field_game_type_id_fk_idx` (`game_type_id`),
  ADD KEY `field_game_type_field_fk_idx` (`field_id`);

--
-- Indexes for table `game_type`
--
ALTER TABLE `game_type`
  ADD PRIMARY KEY (`game_type_id`);

--
-- Indexes for table `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`image_id`),
  ADD KEY `field_id_image_fk_idx` (`field_id`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`notification_id`),
  ADD KEY `player_id_notification_fk_idx` (`player_id`);

--
-- Indexes for table `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`player_id`);

--
-- Indexes for table `prefered_game`
--
ALTER TABLE `prefered_game`
  ADD PRIMARY KEY (`prefered_game_id`),
  ADD KEY `player_id_game_fk_idx` (`player_id`),
  ADD KEY `player_game_id_fk_idx` (`game_type_id`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `field_id_review_fk_idx` (`field_id`),
  ADD KEY `player_id_review_fk_idx` (`player_id`);

--
-- Indexes for table `role`
--
ALTER TABLE `role`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `search`
--
ALTER TABLE `search`
  ADD PRIMARY KEY (`search_record_id`),
  ADD KEY `city_id_search_fk_idx` (`area_id`),
  ADD KEY `game_type_id_search_fk_idx` (`game_type_id`),
  ADD KEY `player_id_search_fk_idx` (`player_id`);

--
-- Indexes for table `settings`
--
ALTER TABLE `settings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `state`
--
ALTER TABLE `state`
  ADD PRIMARY KEY (`state_id`);

--
-- Indexes for table `tokens`
--
ALTER TABLE `tokens`
  ADD PRIMARY KEY (`token_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD KEY `role_id_user_fk_idx` (`role_id`),
  ADD KEY `comapny_id_user_fk_idx` (`company_id`);

--
-- Indexes for table `website_messages`
--
ALTER TABLE `website_messages`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `amenity`
--
ALTER TABLE `amenity`
  MODIFY `amenity_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `area`
--
ALTER TABLE `area`
  MODIFY `area_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `booking_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=714;
--
-- AUTO_INCREMENT for table `company`
--
ALTER TABLE `company`
  MODIFY `company_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `field`
--
ALTER TABLE `field`
  MODIFY `field_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;
--
-- AUTO_INCREMENT for table `field_amenity`
--
ALTER TABLE `field_amenity`
  MODIFY `field_amenity_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=323;
--
-- AUTO_INCREMENT for table `field_game_type`
--
ALTER TABLE `field_game_type`
  MODIFY `field_game_type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=174;
--
-- AUTO_INCREMENT for table `game_type`
--
ALTER TABLE `game_type`
  MODIFY `game_type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `image`
--
ALTER TABLE `image`
  MODIFY `image_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=81;
--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `notification_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=378;
--
-- AUTO_INCREMENT for table `player`
--
ALTER TABLE `player`
  MODIFY `player_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=183;
--
-- AUTO_INCREMENT for table `prefered_game`
--
ALTER TABLE `prefered_game`
  MODIFY `prefered_game_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=507;
--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
  MODIFY `review_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `role`
--
ALTER TABLE `role`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `search`
--
ALTER TABLE `search`
  MODIFY `search_record_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1277;
--
-- AUTO_INCREMENT for table `settings`
--
ALTER TABLE `settings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `state`
--
ALTER TABLE `state`
  MODIFY `state_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `tokens`
--
ALTER TABLE `tokens`
  MODIFY `token_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `website_messages`
--
ALTER TABLE `website_messages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=88;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `field_id_booking_fk` FOREIGN KEY (`field_id`) REFERENCES `field` (`field_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `game_type_booking_fk` FOREIGN KEY (`game_type_id`) REFERENCES `game_type` (`game_type_id`),
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

DELIMITER $$
--
-- Events
--
CREATE DEFINER=`root`@`localhost` EVENT `e_hourly` ON SCHEDULE EVERY 5 MINUTE STARTS '2017-03-19 18:48:24' ON COMPLETION NOT PRESERVE ENABLE DO UPDATE fieldium.booking 
set state_id = 4 
where 
    booking.date < curdate() and booking.state_id = 1$$

DELIMITER ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
