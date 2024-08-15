# CREATE TABLE `sys_sequence` (
#     `name` varchar(20) NOT NULL COMMENT '序列名称',
#     `cur_value` bigint(20) unsigned NOT NULL DEFAULT '100' COMMENT '当前序列值',
#     `increment` smallint(5) unsigned NOT NULL COMMENT '递增步长',
#     `update_time` datetime NOT NULL COMMENT '更新时间',
#     PRIMARY KEY (`name`)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='自增序列';

CREATE TABLE `id_plus` (
   `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
   `gmt_create` datetime NOT NULL,
   `gmt_modified` datetime NOT NULL,
   `biz_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '业务代码',
   `cur_value` bigint unsigned NOT NULL COMMENT '当前序列值',
   `step` mediumint unsigned NOT NULL COMMENT '递增步长(每次获取的ID数量)',
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_biz_code` (`biz_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='ID信息';


# 使用前，先插入对应的业务ID信息，示例如下：
INSERT INTO id_plus
(gmt_create, gmt_modified, biz_code, cur_value, step)
VALUES(now(), now(), 'id_plus_test', 10000, 1000);


