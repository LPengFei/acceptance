-- 加入施工单位和监理单位  2016-11-04   by zlong
ALTER TABLE `card`
	ADD COLUMN `showjldw` INT(11) NULL DEFAULT '0' COMMENT '是否展示监理单位。1展示，0不展示' AFTER `showsbmcbh`,
	ADD COLUMN `showsgdw` INT(11) NULL DEFAULT '0' COMMENT '是否展示施工单位。1展示，0不展示' AFTER `showjldw`;
ALTER TABLE `task_card`
	ADD COLUMN `jldw` VARCHAR(100) NULL DEFAULT NULL COMMENT '监理单位' AFTER `sbmcbh`,
	ADD COLUMN `sgdw` VARCHAR(100) NULL DEFAULT NULL COMMENT '施工单位' AFTER `jldw`;
	

-- 采集资料的照片  2016-11-10  by zlong
ALTER TABLE `result_item`
	ADD COLUMN `images` TEXT NULL DEFAULT NULL COMMENT '资料采集的照片，多个用，好分开' AFTER `result`;


-- 添加选择大项的存放字段  2016-11-16  by zlong
ALTER TABLE `task_card`
	ADD COLUMN `citids` VARCHAR(1000) NULL DEFAULT NULL COMMENT '隐蔽工程选择验收大项的ids,逗号分隔' AFTER `sgdw`;
	
	
-- 问题添加状态：工程遗留问题  2016-11-17 by zlong
ALTER TABLE `result_issue`
	CHANGE COLUMN `status` `status` VARCHAR(100) NULL DEFAULT 'normal' COMMENT '任务状态：normal、cleared：已整改的问题,remain：工程遗留问题' COLLATE 'utf8_bin' AFTER `csname`;

-- 问题添加整改照片和整改说明  2016-11-17 by zlong
ALTER TABLE `result_issue`
	ADD COLUMN `fixremark` TEXT NULL DEFAULT NULL COMMENT '整改说明' AFTER `fixtime`,
	ADD COLUMN `fiximgs` VARCHAR(500) NULL DEFAULT NULL COMMENT '整改照片，多个用逗号分割' AFTER `fixremark`;
	

-- 重新设计工程遗留问题的存储  2016-11-18 by zlong
ALTER TABLE `result_issue`
	CHANGE COLUMN `status` `status` VARCHAR(100) NULL DEFAULT 'normal' COMMENT '任务状态：normal、cleared：已整改的问题' COLLATE 'utf8_bin' AFTER `csname`;	
ALTER TABLE `result_issue`
	ADD COLUMN `isremain` INT(11) NULL DEFAULT '0' COMMENT '是否是遗留问题：1是，0不是' AFTER `tid`,
	ADD COLUMN `remainer`  VARCHAR(50) NULL DEFAULT NULL COMMENT '标记为遗留问题的人员' AFTER `isremain`,
	ADD COLUMN `remaintime` TIMESTAMP NULL DEFAULT NULL COMMENT '标记为遗留问题的时间' AFTER `remainer`;
	
	

-- 添加到货清点任务类型 2016-11-21 by zlong
SET @@global.foreign_key_checks = 0 ;
SET foreign_key_checks=0;
INSERT INTO `card_type` (`id`, `key`, `name`, `flow`) VALUES ('100', 'arrive_check-count', '到货清点', 'arrive_check');
UPDATE `card_type` SET `key`='arrive_check-open' WHERE  `key`='arrive_check';
update card set `type` = 'arrive_check-open' where type = 'arrive_check';



-- 设备类型添加sort字段 2016-11-22 by zlong
ALTER TABLE `device_type`
	ADD COLUMN `sort` INT NULL DEFAULT '0' COMMENT '排序' AFTER `remark`;、
	
-- 到货清点表设计 2016-11-22 by zlong
DROP TABLE IF EXISTS `count_device`;
CREATE TABLE IF NOT EXISTS `count_device` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'ID',
  `tid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '任务ID',
  `ctid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '到货清点任务ID',
  `pid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '项目ID',
  `dtid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '设别大类的ID',
  `name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '设备名称',
  `deviceno` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '设备型号',
  `amount` int(11) DEFAULT '0' COMMENT '数量',
  `time` date DEFAULT NULL COMMENT '到货时间',
  `place` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '存放地点',
  `counter` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '清点人员',
  `remark` text COLLATE utf8_bin COMMENT '备注',
  `dlt` int(11) DEFAULT '0' COMMENT '是否删除：1删除、0不删除',
  `enabled` int(11) DEFAULT '0' COMMENT '是否删除：1删除、0不删除',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `tid` (`tid`) USING BTREE,
  KEY `ctid` (`ctid`) USING BTREE,
  KEY `pid` (`pid`) USING BTREE,
  KEY `FK_count_device_device_type` (`dtid`),
  CONSTRAINT `FK_count_device_device_type` FOREIGN KEY (`dtid`) REFERENCES `device_type` (`id`),
  CONSTRAINT `count_device_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `task` (`id`),
  CONSTRAINT `count_device_ibfk_2` FOREIGN KEY (`ctid`) REFERENCES `count_task` (`id`),
  CONSTRAINT `count_device_ibfk_3` FOREIGN KEY (`pid`) REFERENCES `pro_project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='到货的设备';

DROP TABLE IF EXISTS `count_task`;
CREATE TABLE IF NOT EXISTS `count_task` (
  `id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT 'ID',
  `tid` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '任务ID',
  `flow` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '验收流程key，数据来源config表',
  `type` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '验收类型的key',
  `imgs` varchar(500) COLLATE utf8_bin DEFAULT NULL COMMENT '到货清单，多个用逗号分开',
  `remark` text COLLATE utf8_bin COMMENT '备注',
  `dlt` int(11) DEFAULT '0' COMMENT '是否删除：1删除、0不删除',
  `enabled` int(11) DEFAULT '0' COMMENT '是否删除：1删除、0不删除',
  `createtime` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `last_modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  KEY `tid` (`tid`) USING BTREE,
  CONSTRAINT `count_task_ibfk_1` FOREIGN KEY (`tid`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='到货清点任务';


-- 任务合并表设计 2016-11-22 by zlong
ALTER TABLE `task`
	ADD COLUMN `oids` VARCHAR(500) NOT NULL COMMENT '被合并的任务ID，多个ID用逗号分割' AFTER `id`;
	

-- 自动提示分设备类型 2016-11-23 by zlong
ALTER TABLE `base_tips`
	ADD COLUMN `dtid` VARCHAR(50) NULL DEFAULT NULL COMMENT '设备类型ID' AFTER `type`;

-- 添加工程附件字段 2016-11-24 by gz
ALTER TABLE `pro_project`
    ADD COLUMN `ysjh` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '验收计划' AFTER `yxdw`,
    ADD COLUMN `ysfa`  varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '验收方案' AFTER `ysjh`,
    ADD COLUMN `jlbg` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '监理报告' AFTER `ysfa`;

