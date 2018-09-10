/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE IF NOT EXISTS `tick42-quicksilver` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tick42-quicksilver`;

CREATE TABLE IF NOT EXISTS `extensions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `times_rated` int(11) NOT NULL DEFAULT 0,
  `rating` double NOT NULL DEFAULT 0,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `extensions` DISABLE KEYS */;
/*!40000 ALTER TABLE `extensions` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `extension_tags` (
  `tag_id` int(11) NOT NULL,
  `extension_id` int(11) NOT NULL,
  UNIQUE KEY `tag_id_extension_id` (`tag_id`,`extension_id`),
  KEY `FK_extension_tags_extensions` (`extension_id`),
  CONSTRAINT `FK_extension_tags_extensions` FOREIGN KEY (`extension_id`) REFERENCES `extensions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_extension_tags_tags` FOREIGN KEY (`tag_id`) REFERENCES `tags` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `extension_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `extension_tags` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `files` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location` varchar(512) NOT NULL,
  `type` varchar(512) NOT NULL,
  `size` double NOT NULL,
  `name` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `files` DISABLE KEYS */;
/*!40000 ALTER TABLE `files` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `github` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `link` varchar(512) DEFAULT NULL,
  `user` varchar(512) DEFAULT NULL,
  `repo` varchar(512) DEFAULT NULL,
  `last_commit` datetime DEFAULT NULL,
  `open_issues` int(11) DEFAULT NULL,
  `pull_requests` int(11) DEFAULT NULL,
  `last_success` datetime DEFAULT NULL,
  `last_fail` datetime DEFAULT NULL,
  `fail_msg` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `github` DISABLE KEYS */;
/*!40000 ALTER TABLE `github` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `ratings` (
  `user` int(11) NOT NULL,
  `rating` int(1) NOT NULL DEFAULT 0,
  `extension` int(11) NOT NULL,
  PRIMARY KEY (`user`,`extension`),
  KEY `extension` (`extension`),
  CONSTRAINT `FK1_extensions_extension_id` FOREIGN KEY (`extension`) REFERENCES `extensions` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `ratings` DISABLE KEYS */;
/*!40000 ALTER TABLE `ratings` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rate` int(11) DEFAULT NULL,
  `wait` int(11) DEFAULT NULL,
  `token` varchar(512) DEFAULT NULL,
  `username` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
INSERT INTO `settings` (`id`, `rate`, `wait`, `token`, `username`) VALUES
	(1, 5000, 5000, 'c7c731b5d388d28329bd01b95e48345a263b0b44', 'Smytt');
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `tags` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `users` (
  `username` varchar(50) NOT NULL,
  `password` varchar(68) NOT NULL,
  `enabled` tinyint(4) NOT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(50) DEFAULT 'user',
  `extensions_rated` double NOT NULL DEFAULT 0,
  `rating` double NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`username`, `password`, `enabled`, `id`, `role`, `extensions_rated`, `rating`) VALUES
	('SystemAdmin', '$2a$04$.Mph88sN16dJEB2N64TIl.9M9kNJY9xXptQj3NAEbiivUGxAOFZmG', 1, 6, 'ROLE_ADMIN', 2, 4);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
