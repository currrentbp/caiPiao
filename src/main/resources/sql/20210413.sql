CREATE TABLE `user_forecast_result` (
	`id` BIGINT NOT NULL AUTO_INCREMENT,
	`user_forecast_id` BIGINT NOT NULL DEFAULT '0' COMMENT '用户预测的id，即user_daletou的id',
	`caipiao_type` INT NOT NULL DEFAULT '1' COMMENT '彩票类型，1：大乐透，2：双色球',
	`win` INT NOT NULL DEFAULT '0' COMMENT '中奖结果，0：初始化，1：中奖，2：未中奖',
	`bonus` BIGINT NOT NULL DEFAULT '0' COMMENT '中奖后的奖金',
	`win_level` INT NOT NULL DEFAULT '0' COMMENT '中奖级别，0：没有中奖',
	`create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (`id`)
)
ENGINE=InnoDB
;
