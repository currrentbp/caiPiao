-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.22-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出 caipiao 的数据库结构
CREATE DATABASE IF NOT EXISTS `caipiao` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `caipiao`;


-- 导出  表 caipiao.daletou 结构
CREATE TABLE IF NOT EXISTS `daletou` (
  `id` int(11) NOT NULL COMMENT '大乐透期数',
  `red1` int(11) NOT NULL COMMENT '红球1',
  `red2` int(11) NOT NULL COMMENT '红球2',
  `red3` int(11) NOT NULL COMMENT '红球3',
  `red4` int(11) NOT NULL COMMENT '红球4',
  `red5` int(11) NOT NULL COMMENT '红球5',
  `blue1` int(11) NOT NULL COMMENT '蓝球1',
  `blue2` int(11) NOT NULL COMMENT '蓝球2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='大乐透';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

---批量删除预测数据的表
select concat
('drop table  ' , table_name,';')
from
information_schema.tables
where table_name like 'daletou_forecast_%';

