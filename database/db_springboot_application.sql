-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- 主机： 127.0.0.1:3306
-- 生成日期： 2024-10-16 12:41:24
-- 服务器版本： 5.7.30-log
-- PHP 版本： 7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `db_springboot_application`
--

-- --------------------------------------------------------

--
-- 表的结构 `tb_admin`
--

CREATE TABLE `tb_admin` (
  `ad_id` bigint(20) NOT NULL,
  `ad_name` varchar(24) DEFAULT NULL COMMENT '姓名',
  `ad_role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `ad_header_img` varchar(256) DEFAULT NULL COMMENT '头像',
  `ad_account` varchar(64) DEFAULT NULL COMMENT '登录名',
  `ad_password` varchar(64) DEFAULT NULL COMMENT '密码',
  `ad_city` varchar(64) DEFAULT NULL COMMENT '城市',
  `ad_phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `ad_status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1启用0禁用',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='管理员表';

--
-- 转存表中的数据 `tb_admin`
--

INSERT INTO `tb_admin` (`ad_id`, `ad_name`, `ad_role_id`, `ad_header_img`, `ad_account`, `ad_password`, `ad_city`, `ad_phone`, `ad_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '1874', 0, 'c948011cc08b46959128c1bc4bbd1441.png', 'administrator', 'c27b5aaeb30fb9cec259af9d6bfcec2c38587854f4b26fef423c9773163ef62f', '51,5105,510524', '18183382135', '1', NULL, '2024-08-12 16:50:32', 1, '2024-10-12 14:16:08'),
(2, '平台管理员', 1, '429788dc25a14fe7a75b743b22a5b220.jpg', 'guanliyuan', 'c27b5aaeb30fb9cec259af9d6bfcec2c38587854f4b26fef423c9773163ef62f', '51,5105,510503', '13557997777', '1', 1, '2024-08-12 16:50:32', NULL, '2024-08-12 16:59:19'),
(8, '张三', 2, 'c948011cc08b46959128c1bc4bbd1441.png', 'zhangsan', 'c27b5aaeb30fb9cec259af9d6bfcec2c38587854f4b26fef423c9773163ef62f', '51,5105,510521', '13557998855', '1', 1, '2024-08-12 16:50:32', NULL, '2024-08-12 16:59:21'),
(10, '李四', 4, NULL, 'lisi', 'c27b5aaeb30fb9cec259af9d6bfcec2c38587854f4b26fef423c9773163ef62f', '13,1303,130304', '13557978946', '1', 1, '2024-08-12 16:50:32', NULL, '2024-08-12 16:59:22'),
(11, '王五', 10, NULL, 'wangwu', 'c27b5aaeb30fb9cec259af9d6bfcec2c38587854f4b26fef423c9773163ef62f', '51,5103,510304', '13551737897', '1', 1, '2024-08-12 16:50:32', 1, '2024-10-11 20:45:27'),
(12, '吴六', 3, NULL, 'wuliu', 'c27b5aaeb30fb9cec259af9d6bfcec2c38587854f4b26fef423c9773163ef62f', '51,5106,510604', '13551737598', '1', 1, '2024-08-12 16:50:32', 1, '2024-10-12 14:26:10');

-- --------------------------------------------------------

--
-- 表的结构 `tb_auth_rule`
--

CREATE TABLE `tb_auth_rule` (
  `ar_id` bigint(20) NOT NULL,
  `ar_pid` bigint(20) NOT NULL DEFAULT '0' COMMENT '父id',
  `ar_method` varchar(128) DEFAULT NULL COMMENT '方法名',
  `ar_name` varchar(128) DEFAULT NULL COMMENT '权限名称',
  `ar_status` enum('1','0') DEFAULT '1' COMMENT '状态1启用0禁用',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

--
-- 转存表中的数据 `tb_auth_rule`
--

INSERT INTO `tb_auth_rule` (`ar_id`, `ar_pid`, `ar_method`, `ar_name`, `ar_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, 33, 'adminPage', '管理员列表', '1', NULL, '2024-08-12 17:04:42', 1, '2024-10-16 09:44:00'),
(2, 33, 'adminAdd', '管理员新增', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:44:02'),
(3, 33, 'adminUpdate', '管理员更新', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:44:03'),
(4, 33, 'adminDelete', '管理员删除', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:44:04'),
(5, 33, 'adminPasswordUpdate', '管理员密码更新', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:44:06'),
(6, 33, 'adminBatchUpdateRole', '管理员角色迁移', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:44:07'),
(7, 34, 'myAdminData', '管理员个人信息查询', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:47:04'),
(8, 34, 'myAdminUpdate', '管理员个人信息更新', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:47:07'),
(9, 34, 'adminMyPasswordUpdate', '管理员个人密码更新', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:47:09'),
(10, 35, 'authRuleList', '权限列表', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 10:02:41'),
(11, 35, 'authRuleEmpowerList', '角色权限授权列表', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 10:02:48'),
(12, 35, 'authRuleAdd', '权限新增', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:48:08'),
(13, 35, 'authRuleUpdate', '权限更新', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:48:10'),
(14, 35, 'authRuleDelete', '权限删除', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:48:13'),
(15, 36, 'backMenuList', '后台菜单列表', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:48:57'),
(16, 36, 'backMenuEmpowerList', '角色菜单授权列表', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:48:59'),
(17, 36, 'backMenuAdd', '后台菜单新增', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:49:01'),
(18, 36, 'backMenuUpdate', '后台菜单更新', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:49:04'),
(19, 36, 'backMenuDelete', '后台菜单删除', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:49:06'),
(20, 37, 'roleList', '角色列表', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:49:38'),
(22, 37, 'roleAdd', '角色新增', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:49:46'),
(23, 37, 'roleUpdate', '角色更新', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:49:44'),
(24, 37, 'roleDelete', '角色删除', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:49:47'),
(25, 37, 'updateRoleBackMenu', '角色菜单授权更新', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:49:50'),
(26, 37, 'updateRoleAuthRule', '角色权限授权更新', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:49:51'),
(27, 38, 'logPage', '日志列表', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:50:13'),
(28, 38, 'logBatchDelete', '日志批量删除', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:50:15'),
(29, 39, 'createTableList', '创建CURD列表', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:50:55'),
(30, 39, 'createTableInfo', '创建CURD数据表详情', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:50:56'),
(31, 39, 'createCurd', '创建CURD发起创建', '1', NULL, '2024-08-12 17:04:42', NULL, '2024-10-16 09:50:57'),
(32, 39, 'createFileDownload', '创建CURD文件下载', '1', NULL, '2024-08-12 17:04:42', 1, '2024-10-16 09:50:57'),
(33, 0, 'adminManage', '管理员管理', '1', NULL, '2024-10-16 09:43:46', NULL, '2024-10-16 09:51:05'),
(34, 0, 'personManage', '个人管理', '1', NULL, '2024-10-16 09:43:46', NULL, '2024-10-16 09:43:46'),
(35, 0, 'authRuleManage', '权限管理', '1', NULL, '2024-10-16 09:43:46', NULL, '2024-10-16 09:43:46'),
(36, 0, 'backMenuManage', '菜单管理', '1', NULL, '2024-10-16 09:43:46', NULL, '2024-10-16 09:43:46'),
(37, 0, 'roleManage', '角色管理', '1', NULL, '2024-10-16 09:43:46', NULL, '2024-10-16 09:43:46'),
(38, 0, 'logManage', '日志管理', '1', NULL, '2024-10-16 09:43:46', NULL, '2024-10-16 09:43:46'),
(39, 0, 'CURDManage', '创建CURD管理', '1', NULL, '2024-10-16 09:43:46', NULL, '2024-10-16 09:43:46');

-- --------------------------------------------------------

--
-- 表的结构 `tb_back_menu`
--

CREATE TABLE `tb_back_menu` (
  `bm_id` bigint(20) NOT NULL COMMENT '后台菜单id',
  `bm_pids` varchar(512) NOT NULL DEFAULT '0' COMMENT '父id',
  `bm_title` varchar(32) DEFAULT NULL COMMENT '标题',
  `bm_icon` varchar(32) DEFAULT NULL COMMENT '图标',
  `bm_path` varchar(128) DEFAULT NULL COMMENT '跳转路由地址',
  `bm_component` varchar(128) DEFAULT NULL COMMENT '组件地址',
  `bm_sort` tinyint(4) NOT NULL DEFAULT '99' COMMENT '排序',
  `bm_status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '1启用0禁用',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台菜单表';

--
-- 转存表中的数据 `tb_back_menu`
--

INSERT INTO `tb_back_menu` (`bm_id`, `bm_pids`, `bm_title`, `bm_icon`, `bm_path`, `bm_component`, `bm_sort`, `bm_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '0', '系统管理', 'SettingOutlined', '/system', NULL, 1, '1', NULL, '2024-08-12 17:06:24', 1, '2024-10-11 11:50:06'),
(2, '0,1', '菜单列表', NULL, '/system/backMenuList', './System/BackMenu', 2, '1', NULL, '2024-08-12 17:06:24', NULL, '2024-08-12 17:06:24'),
(3, '0,1', '权限列表', NULL, '/system/authRuleList', './System/AuthRule', 3, '1', NULL, '2024-08-12 17:06:24', NULL, '2024-08-12 17:06:24'),
(4, '0,1', '角色列表', NULL, '/system/roleList', './System/Role', 4, '1', NULL, '2024-08-12 17:06:24', NULL, '2024-08-12 17:06:24'),
(5, '0,1', '管理员列表', NULL, '/system/adminList', './System/Admin', 5, '1', NULL, '2024-08-12 17:06:24', NULL, '2024-08-12 17:06:24'),
(6, '0,1', '日志列表', NULL, '/system/logList', './System/Log', 6, '1', NULL, '2024-08-12 17:06:24', NULL, '2024-08-12 17:06:24'),
(7, '0,1', '创建CURD', NULL, '/system/createList', './System/Create', 1, '1', NULL, '2024-08-12 17:06:24', 1, '2024-10-11 11:50:24');

-- --------------------------------------------------------

--
-- 表的结构 `tb_back_msg`
--

CREATE TABLE `tb_back_msg` (
  `bm_id` bigint(20) NOT NULL COMMENT '后台消息id',
  `bm_uid` bigint(20) NOT NULL COMMENT '管理员id',
  `bm_title` varchar(128) DEFAULT NULL COMMENT '标题',
  `bm_content` varchar(512) DEFAULT NULL COMMENT '消息内容',
  `bm_url` varchar(512) NOT NULL DEFAULT '#' COMMENT '跳转链接',
  `bm_isSend` enum('1','0') NOT NULL DEFAULT '0' COMMENT '是否发送1是0否',
  `bm_isRead` enum('1','0') NOT NULL DEFAULT '0' COMMENT '是否阅读1是0否',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台消息表';

-- --------------------------------------------------------

--
-- 表的结构 `tb_log`
--

CREATE TABLE `tb_log` (
  `lo_id` bigint(20) NOT NULL,
  `lo_content` varchar(256) NOT NULL COMMENT '操作内容',
  `lo_method` varchar(32) DEFAULT NULL COMMENT '操作方法',
  `lo_params` varchar(256) DEFAULT NULL COMMENT '执行参数',
  `lo_result` enum('1','0') NOT NULL COMMENT '执行结果(1成功0失败)',
  `lo_reason` text COMMENT '原因',
  `lo_address` varchar(64) DEFAULT NULL COMMENT 'ip地址',
  `lo_ip` varchar(24) DEFAULT NULL COMMENT 'ip',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

--
-- 转存表中的数据 `tb_log`
--

INSERT INTO `tb_log` (`lo_id`, `lo_content`, `lo_method`, `lo_params`, `lo_result`, `lo_reason`, `lo_address`, `lo_ip`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1846378319843680257, '登录后台系统', 'handleLoginCheck', NULL, '1', NULL, '未知', '127.0.0.1', 1, '2024-10-16 10:31:10', NULL, NULL),
(1846380166050471938, '新增权限', 'insertAuthRule', '[{\"arMethod\":\"test\",\"arName\":\"测试权限\",\"arPid\":\"0\",\"arStatus\":\"0\"}]', '0', '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'ar_id\' at row 1\r\n### The error may exist in luohao/application/mapper/authRule/AuthRuleMapper.java (best guess)\r\n### The error may involve luohao.application.mapper.authRule.AuthRuleMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tb_auth_rule  ( ar_id, ar_pid, ar_method, ar_name, ar_status, create_time, update_time, creator, updater )  VALUES (  ?, ?, ?, ?, ?, ?, ?, ?, ?  )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'ar_id\' at row 1\n; Data truncation: Out of range value for column \'ar_id\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'ar_id\' at row 1', '未知', '127.0.0.1', 1, '2024-10-16 10:38:30', NULL, NULL),
(1846380200045305858, '新增权限', 'insertAuthRule', '[{\"arMethod\":\"test\",\"arName\":\"测试权限\",\"arPid\":\"0\",\"arStatus\":\"0\"}]', '0', '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'ar_id\' at row 1\r\n### The error may exist in luohao/application/mapper/authRule/AuthRuleMapper.java (best guess)\r\n### The error may involve luohao.application.mapper.authRule.AuthRuleMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tb_auth_rule  ( ar_id, ar_pid, ar_method, ar_name, ar_status, create_time, update_time, creator, updater )  VALUES (  ?, ?, ?, ?, ?, ?, ?, ?, ?  )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'ar_id\' at row 1\n; Data truncation: Out of range value for column \'ar_id\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'ar_id\' at row 1', '未知', '127.0.0.1', 1, '2024-10-16 10:38:38', NULL, NULL),
(1846382202158571522, '新增角色', 'insertRole', '[{\"roName\":\"测试角色\",\"roPids\":\"0,1\",\"roStatus\":\"1\"}]', '1', NULL, '未知', '127.0.0.1', 1, '2024-10-16 10:46:36', NULL, NULL),
(1846382493759168514, '新增权限', 'insertAuthRule', '[{\"arMethod\":\"test\",\"arName\":\"测试权限\",\"arPid\":\"0\",\"arStatus\":\"0\"}]', '0', '\r\n### Error updating database.  Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'ar_id\' at row 1\r\n### The error may exist in luohao/application/mapper/authRule/AuthRuleMapper.java (best guess)\r\n### The error may involve luohao.application.mapper.authRule.AuthRuleMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: INSERT INTO tb_auth_rule  ( ar_id, ar_pid, ar_method, ar_name, ar_status, create_time, update_time, creator, updater )  VALUES (  ?, ?, ?, ?, ?, ?, ?, ?, ?  )\r\n### Cause: com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'ar_id\' at row 1\n; Data truncation: Out of range value for column \'ar_id\' at row 1; nested exception is com.mysql.cj.jdbc.exceptions.MysqlDataTruncation: Data truncation: Out of range value for column \'ar_id\' at row 1', '未知', '127.0.0.1', 1, '2024-10-16 10:47:45', NULL, NULL),
(1846391226983772161, '新增权限', 'insertAuthRule', '[{\"arMethod\":\"test\",\"arName\":\"测试权限\",\"arPid\":\"0\",\"arStatus\":\"0\"}]', '1', NULL, '未知', '127.0.0.1', 1, '2024-10-16 11:22:27', NULL, NULL),
(1846392396980563970, '新增权限', 'insertAuthRule', '[{\"arMethod\":\"test\",\"arName\":\"测试\",\"arPid\":\"33\",\"arStatus\":\"1\"}]', '1', NULL, '未知', '127.0.0.1', 1, '2024-10-16 11:27:06', NULL, NULL),
(1846393784720556034, '新增权限', 'insertAuthRule', '[{\"arMethod\":\"1\",\"arName\":\"1\",\"arPid\":\"0\",\"arStatus\":\"0\"}]', '1', NULL, '未知', '127.0.0.1', 1, '2024-10-16 11:32:37', NULL, NULL),
(1846394351056453633, '新增权限', 'insertAuthRule', '[{\"arMethod\":\"test\",\"arName\":\"测试\",\"arPid\":\"0\",\"arStatus\":\"0\"}]', '1', NULL, '未知', '127.0.0.1', 1, '2024-10-16 11:34:52', NULL, NULL),
(1846396082150682626, '新增权限', 'insertAuthRule', '[{\"arMethod\":\"ddd\",\"arName\":\"ddd\",\"arPid\":\"33\",\"arStatus\":\"0\"}]', '1', NULL, '未知', '127.0.0.1', 1, '2024-10-16 11:41:45', NULL, NULL),
(1846396162500964354, '修改权限', 'updateAuthRule', '[{\"arId\":\"41\",\"arMethod\":\"ddd\",\"arName\":\"ddd\",\"arPid\":\"1\",\"arStatus\":\"0\"}]', '1', NULL, '未知', '127.0.0.1', 1, '2024-10-16 11:42:04', NULL, NULL),
(1846396510607228929, '删除权限', 'deleteBackMenu', '[\"41\"]', '1', NULL, '未知', '127.0.0.1', 1, '2024-10-16 11:43:27', NULL, NULL),
(1846396510607228930, '登录后台系统', 'handleLoginCheck', NULL, '1', NULL, '未知', '127.0.0.1', 1, '2024-10-16 12:32:14', NULL, NULL),
(1846396510607228931, '更新角色权限授权', 'updateRoleAuthRule', '[\"1\",\"1,2,3,4,5,6,7,8,9,26,25,24,23,22,21,20,19,18,17,16,15,11,10,31,30,29,28,27,32,33,34,35,12,13,14,36,37,38,39\"]', '1', NULL, '未知', '127.0.0.1', 1, '2024-10-16 12:37:17', NULL, NULL),
(1846396510607228932, '更新角色权限授权', 'updateRoleAuthRule', '[\"1\",\"1,2,3,4,5,6,7,8,9,26,25,24,23,22,21,20,19,18,17,16,15,11,10,33,34,35,12,13,14,36,37\"]', '1', NULL, '未知', '127.0.0.1', 1, '2024-10-16 12:38:01', NULL, NULL),
(1846396510607228933, '更新角色权限授权', 'updateRoleAuthRule', '[\"1\",\"1,2,3,4,5,6,7,8,9,26,25,24,23,22,21,20,19,18,17,16,15,11,10,33,34,35,12,13,14,36,37,38,27,28,39,29,30,31,32\"]', '1', NULL, '未知', '127.0.0.1', 1, '2024-10-16 12:38:11', NULL, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `tb_role`
--

CREATE TABLE `tb_role` (
  `ro_id` bigint(20) NOT NULL,
  `ro_name` varchar(64) NOT NULL COMMENT '角色名称',
  `ro_pids` varchar(512) DEFAULT '0' COMMENT '父id(逗号分隔)',
  `ro_rule_ids` text COMMENT '权限id(逗号分隔)',
  `ro_back_menu_ids` varchar(512) DEFAULT NULL COMMENT '后台菜单id(逗号分隔)',
  `ro_status` enum('1','0') NOT NULL DEFAULT '1' COMMENT '状态1启用0禁用',
  `creator` bigint(20) DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` bigint(20) DEFAULT NULL COMMENT '更新人id',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

--
-- 转存表中的数据 `tb_role`
--

INSERT INTO `tb_role` (`ro_id`, `ro_name`, `ro_pids`, `ro_rule_ids`, `ro_back_menu_ids`, `ro_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES
(1, '平台管理', '0', '1,2,3,4,5,6,7,8,9,26,25,24,23,22,21,20,19,18,17,16,15,11,10,33,34,35,12,13,14,36,37,38,27,28,39,29,30,31,32', '1,2,3,4,5,7,6', '1', NULL, '2024-08-12 17:12:06', 1, '2024-10-16 12:38:10'),
(2, '菜单管理员', '0,1', '19,18,17,15', '1,2', '1', NULL, '2024-08-12 17:12:06', NULL, '2024-08-12 17:12:06'),
(3, '企划一部', '0,1,4', '', NULL, '1', NULL, '2024-08-12 17:12:06', NULL, '2024-08-12 17:12:06'),
(4, '角色管理员', '0,1', '24,23,22,20,11,26,25,16', '1,4', '1', NULL, '2024-08-12 17:12:06', NULL, '2024-08-12 17:12:06'),
(6, '财务管理', '0,1', NULL, NULL, '1', NULL, '2024-08-12 17:12:06', NULL, '2024-08-12 17:12:06'),
(9, '部门管理2', '0,1', NULL, NULL, '0', NULL, '2024-08-12 17:12:06', NULL, '2024-08-12 17:12:06'),
(10, '企划二部', '0,1,4', NULL, NULL, '1', NULL, '2024-08-12 17:12:06', NULL, '2024-08-12 17:12:06'),
(11, '部门管理-copy', '0,1,6', NULL, NULL, '1', NULL, '2024-08-12 17:12:06', NULL, '2024-08-12 17:12:06'),
(1846382202158571521, '测试角色', '0,1', NULL, NULL, '1', 1, '2024-10-16 10:46:36', NULL, NULL);

--
-- 转储表的索引
--

--
-- 表的索引 `tb_admin`
--
ALTER TABLE `tb_admin`
  ADD PRIMARY KEY (`ad_id`),
  ADD KEY `ad_userNum` (`ad_account`,`ad_password`);

--
-- 表的索引 `tb_auth_rule`
--
ALTER TABLE `tb_auth_rule`
  ADD PRIMARY KEY (`ar_id`),
  ADD KEY `ar_method` (`ar_method`);

--
-- 表的索引 `tb_back_menu`
--
ALTER TABLE `tb_back_menu`
  ADD PRIMARY KEY (`bm_id`);

--
-- 表的索引 `tb_back_msg`
--
ALTER TABLE `tb_back_msg`
  ADD PRIMARY KEY (`bm_id`);

--
-- 表的索引 `tb_log`
--
ALTER TABLE `tb_log`
  ADD PRIMARY KEY (`lo_id`);

--
-- 表的索引 `tb_role`
--
ALTER TABLE `tb_role`
  ADD PRIMARY KEY (`ro_id`),
  ADD KEY `ag_pid` (`ro_pids`);
ALTER TABLE `tb_role` ADD FULLTEXT KEY `ag_rules` (`ro_rule_ids`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `tb_admin`
--
ALTER TABLE `tb_admin`
  MODIFY `ad_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- 使用表AUTO_INCREMENT `tb_auth_rule`
--
ALTER TABLE `tb_auth_rule`
  MODIFY `ar_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- 使用表AUTO_INCREMENT `tb_back_menu`
--
ALTER TABLE `tb_back_menu`
  MODIFY `bm_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '后台菜单id', AUTO_INCREMENT=8;

--
-- 使用表AUTO_INCREMENT `tb_back_msg`
--
ALTER TABLE `tb_back_msg`
  MODIFY `bm_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '后台消息id';

--
-- 使用表AUTO_INCREMENT `tb_log`
--
ALTER TABLE `tb_log`
  MODIFY `lo_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2147483647;

--
-- 使用表AUTO_INCREMENT `tb_role`
--
ALTER TABLE `tb_role`
  MODIFY `ro_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2147483647;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
