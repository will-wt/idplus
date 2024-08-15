CREATE TABLE `sys_sequence` (
    `name` varchar(20) NOT NULL COMMENT '序列名称',
    `cur_value` bigint(20) unsigned NOT NULL DEFAULT '100' COMMENT '当前序列值',
    `increment` smallint(5) unsigned NOT NULL COMMENT '递增步长',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自增序列';
