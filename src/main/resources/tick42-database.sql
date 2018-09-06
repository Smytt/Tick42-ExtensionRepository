-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.8-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for tick42-quicksilver
CREATE DATABASE IF NOT EXISTS `tick42-quicksilver` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tick42-quicksilver`;

-- Dumping structure for table tick42-quicksilver.authorities
CREATE TABLE IF NOT EXISTS `authorities` (
  `username` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  UNIQUE KEY `username_authority` (`username`,`authority`),
  CONSTRAINT `FK__users` FOREIGN KEY (`username`) REFERENCES `users` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tick42-quicksilver.authorities: ~6 rows (approximately)
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` (`username`, `authority`) VALUES
	('go', 'ROLE_USER'),
	('gosho', 'ROLE_USER'),
	('kiril', 'ROLE_USER'),
	('misho', 'ROLE_USER'),
	('pesho', 'ROLE_ADMIN'),
	('pesho', 'ROLE_USER');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;

-- Dumping structure for table tick42-quicksilver.extensions
CREATE TABLE IF NOT EXISTS `extensions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rating` int(1) NOT NULL DEFAULT 0,
  `times_rated` int(11) NOT NULL DEFAULT 0,
  `name` char(255) NOT NULL,
  `version` varchar(50) NOT NULL,
  `times_downloaded` int(11) DEFAULT 0,
  `owner` int(11) NOT NULL,
  `github_id` int(11) DEFAULT NULL,
  `file_id` int(11) DEFAULT NULL,
  `image_id` int(11) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `is_pending` tinyint(4) NOT NULL DEFAULT 1,
  `is_featured` tinyint(4) NOT NULL DEFAULT 0,
  `upload_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_extensions_users` (`owner`),
  KEY `FK_extensions_files` (`file_id`),
  KEY `FK_extensions_files_2` (`image_id`),
  KEY `FK_extensions_github` (`github_id`),
  CONSTRAINT `FK_extensions_files` FOREIGN KEY (`file_id`) REFERENCES `files` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_extensions_files_2` FOREIGN KEY (`image_id`) REFERENCES `files` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_extensions_github` FOREIGN KEY (`github_id`) REFERENCES `github` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_extensions_users` FOREIGN KEY (`owner`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=362 DEFAULT CHARSET=utf8;

-- Dumping data for table tick42-quicksilver.extensions: ~13 rows (approximately)
/*!40000 ALTER TABLE `extensions` DISABLE KEYS */;
INSERT INTO `extensions` (`id`, `rating`, `times_rated`, `name`, `version`, `times_downloaded`, `owner`, `github_id`, `file_id`, `image_id`, `description`, `is_pending`, `is_featured`, `upload_date`) VALUES
	(190, 2, 1, 'postman6', '1.009', 5, 3, 24, 1, NULL, 'once upon a time', 0, 1, '2018-08-22 03:58:56'),
	(191, 5, 1, 'postman6', '1.009', 2, 3, 24, 2, NULL, 'once upon a time', 0, 0, '2018-08-12 03:59:16'),
	(192, 5, 1, 'postman6', '1.009', 0, 3, 24, NULL, NULL, 'once upon a time', 0, 0, '2018-08-22 12:12:43'),
	(193, 5, 1, 'postman6', '0', 0, 3, 24, NULL, NULL, NULL, 0, 1, '2018-08-22 12:13:38'),
	(194, 2, 1, 'postman6', '0', 0, 3, 24, NULL, NULL, NULL, 0, 1, '2018-08-22 12:14:34'),
	(195, 1, 1, 'postman6', '0', 0, 3, 24, NULL, NULL, 'once upon a time', 0, 0, '2018-08-22 12:15:14'),
	(196, 2, 1, 'postman6', '1.009', 0, 3, 24, 2, NULL, 'once upon a time', 0, 0, '2018-08-22 12:15:20'),
	(197, 4, 1, 'postman6', '1.009', 0, 3, 24, NULL, NULL, 'once upon a time', 0, 1, '2018-08-22 12:15:25'),
	(198, 4, 1, 'postman6', '1.009', 0, 3, 24, NULL, NULL, 'once upon a time', 0, 1, '2018-08-22 12:17:43'),
	(199, 7, 1, 'postman6', '1.009', 0, 3, 24, NULL, NULL, 'once upon a time', 0, 0, '2018-08-22 12:19:10'),
	(200, 2, 1, 'postman6', '1.009', 0, 3, 24, NULL, NULL, 'once upon a time', 1, 0, '2018-08-22 12:20:16'),
	(201, 3, 1, 'postman 234', '1.009', 0, 3, 24, NULL, NULL, 'once upon a time', 0, 0, '2018-08-22 12:20:57'),
	(202, 2, 1, '231', '1.009', 0, 3, 24, 23, NULL, 'once upon a time', 0, 1, '2018-08-22 12:41:28');
/*!40000 ALTER TABLE `extensions` ENABLE KEYS */;

-- Dumping structure for table tick42-quicksilver.extension_tags
CREATE TABLE IF NOT EXISTS `extension_tags` (
  `tag_id` int(11) NOT NULL,
  `extension_id` int(11) NOT NULL,
  UNIQUE KEY `tag_id_extension_id` (`tag_id`,`extension_id`),
  KEY `FK_extension_tags_extensions` (`extension_id`),
  CONSTRAINT `FK_extension_tags_extensions` FOREIGN KEY (`extension_id`) REFERENCES `extensions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_extension_tags_tags` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tick42-quicksilver.extension_tags: ~5 rows (approximately)
/*!40000 ALTER TABLE `extension_tags` DISABLE KEYS */;
INSERT INTO `extension_tags` (`tag_id`, `extension_id`) VALUES
	(152, 190),
	(152, 191),
	(152, 192),
	(152, 195),
	(152, 196);
/*!40000 ALTER TABLE `extension_tags` ENABLE KEYS */;

-- Dumping structure for table tick42-quicksilver.files
CREATE TABLE IF NOT EXISTS `files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(512) NOT NULL,
  `type` varchar(512) NOT NULL,
  `size` double NOT NULL,
  `name` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- Dumping data for table tick42-quicksilver.files: ~45 rows (approximately)
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
INSERT INTO `files` (`id`, `location`, `type`, `size`, `name`) VALUES
	(1, 'http://localhost:8080/download/c18fe5_1d90170b2894432a91216c722b339b1e_mv2.png', 'image/png', 11737, 'c18fe5_1d90170b2894432a91216c722b339b1e_mv2.png'),
	(2, 'http://localhost:8080/download/academy-logo-new.png', 'image/png', 3496, 'academy-logo-new.png'),
	(4, 'http://localhost:8080/download/IMG_0946.JPG', 'image/jpeg', 1299724, 'IMG_0946.JPG'),
	(5, 'http://localhost:8080/download/IMG_0946.JPG', 'image/jpeg', 1299724, 'IMG_0946.JPG'),
	(6, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(7, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(8, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(9, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(10, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(11, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(12, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(13, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(14, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(15, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(16, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(17, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(18, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(19, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(20, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(21, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(22, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(23, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(24, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(25, 'http://localhost:8080/download/Capture.PNG', 'image/png', 105930, 'Capture.PNG'),
	(26, 'http://localhost:8080/download/261_file.txt', 'text/plain', 3053, '261_file.txt'),
	(27, 'http://localhost:8080/download/261_file.txt', 'text/plain', 3053, '261_file.txt'),
	(28, 'http://localhost:8080/download/261_image.png', 'image/png', 14052, '261_image.png'),
	(29, 'http://localhost:8080/api/download/261_image.png', 'image/png', 14052, '261_image.png'),
	(30, 'http://localhost:8080/api/download/262_file.pdf', 'application/pdf', 1549897, '262_file.pdf'),
	(31, 'http://localhost:8080/api/download/262_image.png', 'image/png', 14052, '262_image.png'),
	(32, 'http://localhost:8080/api/download/284_image.png', 'image/png', 14052, '284_image.png'),
	(33, 'http://localhost:8080/api/download/312_image.png', 'image/png', 10920, '312_image.png'),
	(34, 'http://localhost:8080/api/download/313_image.png', 'image/png', 10749, '313_image.png'),
	(35, 'http://localhost:8080/api/download/340_file.png', 'image/png', 5409, '340_file.png'),
	(36, 'http://localhost:8080/api/download/341_file.png', 'image/png', 14051, '341_file.png'),
	(37, 'http://localhost:8080/api/download/342_file.png', 'image/png', 14051, '342_file.png'),
	(38, 'http://localhost:8080/api/download/342_image.png', 'image/png', 14051, '342_image.png'),
	(39, 'http://localhost:8080/api/download/341_file.png', 'image/png', 5409, '341_file.png'),
	(40, 'http://localhost:8080/api/download/341_image.png', 'image/png', 5409, '341_image.png'),
	(41, 'http://localhost:8080/api/download/343_file.png', 'image/png', 5519, '343_file.png'),
	(42, 'http://localhost:8080/api/download/344_file.png', 'image/png', 5519, '344_file.png'),
	(43, 'http://localhost:8080/api/download/345_file.png', 'image/png', 5519, '345_file.png'),
	(44, 'http://localhost:8080/api/download/346_file.png', 'image/png', 5519, '346_file.png'),
	(45, 'http://localhost:8080/api/download/341_file.png', 'image/png', 5519, '341_file.png'),
	(46, 'http://localhost:8080/api/download/341_image.png', 'image/png', 5519, '341_image.png');
/*!40000 ALTER TABLE `files` ENABLE KEYS */;

-- Dumping structure for table tick42-quicksilver.github
CREATE TABLE IF NOT EXISTS `github` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `link` varchar(512) DEFAULT NULL,
  `user` varchar(512) DEFAULT NULL,
  `repo` varchar(512) DEFAULT NULL,
  `last_commit` datetime DEFAULT NULL,
  `open_issues` int(11) DEFAULT NULL,
  `pull_requests` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8;

-- Dumping data for table tick42-quicksilver.github: ~136 rows (approximately)
/*!40000 ALTER TABLE `github` DISABLE KEYS */;
INSERT INTO `github` (`id`, `link`, `user`, `repo`, `last_commit`, `open_issues`, `pull_requests`) VALUES
	(1, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(2, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(3, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(4, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(5, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(6, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(7, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(8, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(9, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(10, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(11, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(12, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(13, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(14, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(15, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(16, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(17, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(18, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(19, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(20, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(21, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 23:11:57', 0, 0),
	(22, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(24, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(25, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(26, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(27, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(28, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(29, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(30, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(31, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(32, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(33, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(34, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(35, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(37, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(38, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(40, 'https://github.com/asdas/asd', 'asdas', 'asd', NULL, 0, 0),
	(41, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(45, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(52, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(55, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(59, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(60, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(61, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(64, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(65, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(68, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(69, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(70, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(71, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(72, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(73, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(74, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(75, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(76, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(77, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(78, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(79, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(80, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(81, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(82, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(83, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(84, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(85, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(86, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(87, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(88, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(89, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(90, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(91, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(92, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(93, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-02 22:56:05', 0, 0),
	(95, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-03 13:37:46', 0, 0),
	(96, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-03 13:37:46', 0, 0),
	(97, 'https://github.com/Petroslav/telerik-alpha-final-project', 'Petroslav', 'telerik-alpha-final-project', '2018-09-03 16:26:24', 0, 0),
	(98, 'https://github.com/Petroslav/telerik-alpha-final-project', 'Petroslav', 'telerik-alpha-final-project', '2018-09-03 16:26:24', 0, 0),
	(99, 'https://github.com/Petroslav/telerik-alpha-final-project', 'Petroslav', 'telerik-alpha-final-project', '2018-09-03 16:26:24', 0, 0),
	(101, 'https://github.com/Petroslav/telerik-alpha-final-project', 'Petroslav', 'telerik-alpha-final-project', '2018-09-03 16:26:24', 0, 0),
	(102, 'https://github.com/Petroslav/telerik-alpha-final-project', 'Petroslav', 'telerik-alpha-final-project', '2018-09-03 16:26:24', 0, 0),
	(103, 'https://github.com/Petroslav/telerik-alpha-final-project', 'Petroslav', 'telerik-alpha-final-project', '2018-09-03 16:26:24', 0, 0),
	(104, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(105, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(106, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(107, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(108, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 12:07:49', 0, 0),
	(109, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 12:07:49', 0, 0),
	(110, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 13:00:27', 0, 0),
	(111, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 21:33:25', 0, 0),
	(112, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 21:33:25', 0, 0),
	(113, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 21:33:25', 0, 0),
	(114, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 21:33:25', 0, 0),
	(115, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(116, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(117, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(118, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(119, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(120, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(121, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(122, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(123, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(124, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(125, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(126, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(127, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(128, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(129, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(130, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(131, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(132, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(133, 'https://github.com/octocat/Hello-World', 'octocat', 'Hello-World', '2012-03-07 01:06:50', 136, 146),
	(134, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(135, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(136, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(137, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(138, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(139, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(140, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(141, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(142, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(143, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(144, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(145, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(146, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(147, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(148, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(149, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(150, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(152, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 0, 0),
	(153, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(154, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(155, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(156, 'https://github.com/clintbellanger/flare-game', 'clintbellanger', 'flare-game', '2018-08-31 19:57:07', 50, 0),
	(157, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-04 23:26:05', 2, 4),
	(158, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-05 03:14:19', 0, 0),
	(159, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-06 15:28:09', 0, 0),
	(160, 'https://github.com/Smytt/Tick42-ExtensionRepository', 'Smytt', 'Tick42-ExtensionRepository', '2018-09-06 17:12:24', 0, 0);
/*!40000 ALTER TABLE `github` ENABLE KEYS */;

-- Dumping structure for table tick42-quicksilver.info
CREATE TABLE IF NOT EXISTS `info` (
  `t1ID` int(11) NOT NULL,
  `t2ID` int(11) NOT NULL,
  PRIMARY KEY (`t1ID`,`t2ID`),
  UNIQUE KEY `t1IDa` (`t1ID`,`t2ID`),
  KEY `t1ID` (`t1ID`),
  KEY `t2ID` (`t2ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tick42-quicksilver.info: ~0 rows (approximately)
/*!40000 ALTER TABLE `info` DISABLE KEYS */;
/*!40000 ALTER TABLE `info` ENABLE KEYS */;

-- Dumping structure for table tick42-quicksilver.ratings
CREATE TABLE IF NOT EXISTS `ratings` (
  `rating` int(1) NOT NULL DEFAULT 0,
  `extension` int(11) NOT NULL,
  `user` int(11) NOT NULL,
  PRIMARY KEY (`extension`,`user`),
  UNIQUE KEY `extension` (`extension`,`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table tick42-quicksilver.ratings: ~13 rows (approximately)
/*!40000 ALTER TABLE `ratings` DISABLE KEYS */;
INSERT INTO `ratings` (`rating`, `extension`, `user`) VALUES
	(2, 190, 6),
	(5, 191, 6),
	(5, 192, 6),
	(5, 193, 6),
	(2, 194, 6),
	(1, 195, 6),
	(2, 196, 6),
	(4, 197, 6),
	(4, 198, 6),
	(7, 199, 6),
	(2, 200, 6),
	(3, 201, 6),
	(2, 202, 6);
/*!40000 ALTER TABLE `ratings` ENABLE KEYS */;

-- Dumping structure for table tick42-quicksilver.tags
CREATE TABLE IF NOT EXISTS `tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8;

-- Dumping data for table tick42-quicksilver.tags: ~63 rows (approximately)
/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
INSERT INTO `tags` (`id`, `name`) VALUES
	(190, ''),
	(181, '11'),
	(205, '12'),
	(214, '123123'),
	(170, '21312'),
	(182, '22'),
	(213, '2385643252755878'),
	(183, '33'),
	(184, '44'),
	(194, '44212'),
	(195, '4423312'),
	(196, '4423312342342'),
	(226, 'aads'),
	(212, 'adf'),
	(200, 'adqweqw'),
	(206, 'asd'),
	(187, 'batcat'),
	(185, 'batman'),
	(203, 'chetiri'),
	(210, 'das'),
	(224, 'dasdasdsa'),
	(231, 'ddddddddddd'),
	(220, 'df'),
	(228, 'dsa'),
	(225, 'dsadsadsa'),
	(209, 'dsf'),
	(211, 'f'),
	(223, 'fadsf'),
	(192, 'hskjhgdkjfgadkjsgf'),
	(173, 'kkk'),
	(230, 'kkkka'),
	(155, 'lelzzzz'),
	(174, 'lll'),
	(171, 'lo123123lw'),
	(153, 'lolw'),
	(175, 'mmm'),
	(172, 'new'),
	(176, 'nnn'),
	(152, 'omg'),
	(169, 'omg2'),
	(156, 'omgrewrew'),
	(204, 'pet'),
	(186, 'robim'),
	(154, 'roflmao'),
	(222, 'sad'),
	(221, 'sadf'),
	(219, 'sd'),
	(208, 'sdf'),
	(207, 'shest'),
	(198, 'site'),
	(199, 'submit'),
	(168, 'taggggg'),
	(191, 'tagyy'),
	(189, 'the joker'),
	(188, 'the joker,'),
	(202, 'tri'),
	(197, 'web'),
	(201, 'web submit 3'),
	(215, 'yhjjjjj7'),
	(179, 'ерер'),
	(177, 'ооо'),
	(178, 'ппп'),
	(180, 'уъуъу');
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;

-- Dumping structure for table tick42-quicksilver.users
CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `enabled` tinyint(4) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(50) DEFAULT 'user',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;

-- Dumping data for table tick42-quicksilver.users: ~30 rows (approximately)
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`username`, `password`, `enabled`, `id`, `role`) VALUES
	('go', '{bcrypt}$2a$10$fPcD/wcIXiET13WevWuMhuA0TqST0jEAd2HNsp6GDciaACyGdM29i', 1, 1, 'user'),
	('gosho', '{bcrypt}$2a$10$gEN6oRI0rZxDSEATeuGWwOX6x7un5a1B5pcWJnH4SIdf7n4NmSuxS', 0, 2, 'user'),
	('kiril', '123', 1, 3, 'ROLE_USER'),
	('misho', '{noop}pass3', 0, 4, 'user'),
	('pesho', '{noop}pass1', 0, 5, 'user'),
	('k', 'p', 1, 6, 'ROLE_ADMIN'),
	('ksdf', 'p', 1, 8, 'user'),
	('kk', 'pp', 1, 9, 'user'),
	('koko', '$2a$10$il4j.6RfxHaQorCxc271w.BLtbhF1U8NBXktIbZVtOFxQkqP4FBIK', 1, 10, 'USER'),
	('p', '$2a$10$Mexdj9vR.0EzsT2qkd9uJeQl/n4u0nmOfh5i8rJYjKJlUyYYp5NlS', 1, 11, 'USER'),
	('m', '$2a$10$X9RtFKBl8/v1ufjtqLTNsubceKwvvco8oxqyT5.g7a7XNWdEjMuDO', 1, 12, 'USER'),
	('l', '$2a$10$gsUw0Dwc.4l9OxU4eBT/A.KdJcxtxP3SvxpxDrRwf.23bU9EqEsOm', 0, 13, 'USER'),
	('ml', '$2a$10$InuYAj6w3fom/BeZmGPpXuyP7HhFtEWUwZFv/5BzWqPQCFuSMFyoi', 1, 14, 'USER'),
	('y', '$2a$10$5JTnpV4frF58QjQliIPVpu1urD9xUSXjDDWiFEEyFE7aw8OmoprVy', 0, 15, 'USER'),
	('ty', '$2a$10$dd5B35/SjnyN1qpamfdi9u7PX256rOoPqnyEd7UmifE3vhf1OMNay', 0, 16, 'USER'),
	('dasdsa', 'a', 1, 17, 'USER'),
	('dasddsadsa', 'aaaaaaaa', 0, 18, 'USER'),
	('kdsaaaaaaaa', 'aaaaaaaaaaaaaaaa', 1, 19, 'USER'),
	('kzzzzzzzzz', 'zzzzzzzz', 1, 20, 'ROLE_USER'),
	('kzzzzzzzzzzzzz', 'zzzzzzzzzzz', 1, 21, 'ROLE_USER'),
	('kaaaaaaaaaaaaaazz', 'zzzzzzzzzzz', 1, 22, 'ROLE_USER'),
	('kaaaaaaaaaaazzz', 'kkkkkkkkk', 1, 23, 'ROLE_USER'),
	('zzzzzzzzzzzzzzzzzz', 'zzzzzzzzz', 1, 25, 'ROLE_ADMIN'),
	('zzzzzzq', 'zzzzzzz', 1, 26, 'ROLE_ADMIN'),
	('testadminregistration', 'aaaaaaaa', 1, 27, 'ROLE_ADMIN'),
	('testuserregistration', 'test12345', 1, 28, 'ROLE_USER'),
	('kffffqqeee', 'kkkkkkkkkkk', 1, 29, 'ROLE_ADMIN'),
	('kaaaaaa', 'aaaaaaaa', 1, 30, 'ROLE_USER'),
	('zzzzzzzzzz', 'aaaaaaaaa', 1, 31, 'ROLE_USER'),
	('kzzzzzzzzzz', 'zzzzzzzzzzzzzz', 1, 32, 'ROLE_USER');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
