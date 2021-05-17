use daletou;
CREATE TABLE `daletou_forecast_vt` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`daletou_id` BIGINT NOT NULL DEFAULT '0' COMMENT '大乐透id',
	`daletou` VARCHAR(100) NOT NULL DEFAULT '0' COMMENT '大乐透号码',
	`used` INT NOT NULL DEFAULT '0',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
ENGINE=InnoDB
;
