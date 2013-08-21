-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.32-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  8.0.0.4396
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 chrome_note 的数据库结构
DROP DATABASE IF EXISTS `chrome_note`;
CREATE DATABASE IF NOT EXISTS `chrome_note` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `chrome_note`;


-- 导出  表 chrome_note.notes 结构
DROP TABLE IF EXISTS `notes`;
CREATE TABLE IF NOT EXISTS `notes` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) DEFAULT NULL,
  `title` varchar(500) DEFAULT NULL,
  `desc` text,
  `url` varchar(500) DEFAULT NULL,
  `list` text,
  `tag` varchar(50) DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  `created` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- 正在导出表  chrome_note.notes 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `notes` DISABLE KEYS */;
INSERT INTO `notes` (`id`, `user_id`, `title`, `desc`, `url`, `list`, `tag`, `updated`, `created`) VALUES
	(15, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2013-08-21 17:08:59');
/*!40000 ALTER TABLE `notes` ENABLE KEYS */;


-- 导出 webapp 的数据库结构
DROP DATABASE IF EXISTS `webapp`;
CREATE DATABASE IF NOT EXISTS `webapp` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `webapp`;


-- 导出  表 webapp.test 结构
DROP TABLE IF EXISTS `test`;
CREATE TABLE IF NOT EXISTS `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  webapp.test 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
INSERT INTO `test` (`id`, `name`) VALUES
	(1, 'fdafd'),
	(2, 'dfefe');
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
