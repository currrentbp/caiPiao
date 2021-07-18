-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.6.22-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

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

---批量删除预测数据的表
select concat
('drop table  ' , table_name,';')
from
information_schema.tables
where table_name like 'daletou_forecast_%';

CREATE TABLE `daletou_forecast_result` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`daletou_id` BIGINT NOT NULL DEFAULT '0' COMMENT '大乐透id',
	`daletou` VARCHAR(100) NOT NULL DEFAULT '0' COMMENT '大乐透号码',
	`forecast_version` INT NOT NULL DEFAULT '0' COMMENT '预测版本',
	`used` INT NOT NULL DEFAULT '0' COMMENT '是否使用',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
COMMENT='大乐透第二种预测的'
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

