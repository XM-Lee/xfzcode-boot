/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : xfzcode-boot

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 27/06/2023 17:51:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `detail` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `action` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `entity` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `time` datetime(0) NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1673630074391240706 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------
INSERT INTO `sys_operation_log` VALUES (1673629573595537409, '2023-06-27 17:49:20', '2023-06-27 17:49:20', '[]', '127.0.0.1', NULL, '获取验证码', '', '请求成功', '2023-06-27 17:49:20', '/api/v1/auth/getVerCode', '200', NULL);
INSERT INTO `sys_operation_log` VALUES (1673630074391240705, '2023-06-27 17:51:19', '2023-06-27 17:51:19', '[]', '127.0.0.1', NULL, '获取验证码', '', '请求成功', '2023-06-27 17:51:19', '/api/v1/auth/getVerCode', '200', NULL);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'NULL' COMMENT '菜单名',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `create_by` bigint(20) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_by` bigint(20) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `del_flag` int(11) NULL DEFAULT 0 COMMENT '是否删除（0未删除 1已删除）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `parent_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '终端管理', NULL, NULL, '0', '0', 'terminal', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '零信任一级菜单', 0);
INSERT INTO `sys_permission` VALUES (2, '终端列表', NULL, NULL, '0', '0', 'terminal:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端管理(2)', 1);
INSERT INTO `sys_permission` VALUES (3, '新增终端', NULL, NULL, '0', '0', 'terminal:list:add', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端列表(3)', 2);
INSERT INTO `sys_permission` VALUES (4, '批量导入终端', NULL, NULL, '0', '0', 'terminal:list:batchAdd', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端列表(3)', 2);
INSERT INTO `sys_permission` VALUES (5, '批量删除', NULL, NULL, '0', '0', 'terminal:list:batchDelete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端列表(3)', 2);
INSERT INTO `sys_permission` VALUES (6, '删除', NULL, NULL, '0', '0', 'terminal:list:delete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端列表(3)', 2);
INSERT INTO `sys_permission` VALUES (7, '修改', NULL, NULL, '0', '0', 'terminal:list:update', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端列表(3)', 2);
INSERT INTO `sys_permission` VALUES (8, '查询列表', NULL, NULL, '0', '0', 'terminal:list:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端列表(3)', 2);
INSERT INTO `sys_permission` VALUES (9, '软件/插件库', NULL, NULL, '0', '0', 'terminal:soft', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端管理(2)', 1);
INSERT INTO `sys_permission` VALUES (10, '新增软件/插件', NULL, NULL, '0', '0', 'terminal:soft:add', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '软件插件库(3)', 9);
INSERT INTO `sys_permission` VALUES (11, '删除', NULL, NULL, '0', '0', 'terminal:soft:delete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '软件插件库(3)', 9);
INSERT INTO `sys_permission` VALUES (12, '批量删除', NULL, NULL, '0', '0', 'terminal:soft:batchDelete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '软件插件库(3)', 9);
INSERT INTO `sys_permission` VALUES (13, '编辑', NULL, NULL, '0', '0', 'terminal:soft:update', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '软件插件库(3)', 9);
INSERT INTO `sys_permission` VALUES (14, '查询列表', NULL, NULL, '0', '0', 'terminal:soft:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '软件插件库(3)', 9);
INSERT INTO `sys_permission` VALUES (15, '详情', NULL, NULL, '0', '0', 'terminal:soft:detail', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '软件插件库(3)', 9);
INSERT INTO `sys_permission` VALUES (16, '通知/公告', NULL, NULL, '0', '0', 'terminal:notice', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端管理(2)', 1);
INSERT INTO `sys_permission` VALUES (17, '培训文件管理', NULL, NULL, '0', '0', 'terminal:notice:train', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '通知/公告(3)', 16);
INSERT INTO `sys_permission` VALUES (18, '上传培训文件', NULL, NULL, '0', '0', 'terminal:notice:train:upload', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '培训文件管理(4)', 17);
INSERT INTO `sys_permission` VALUES (19, '删除', NULL, NULL, '0', '0', 'terminal:notice:train:delete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '培训文件管理(4)', 17);
INSERT INTO `sys_permission` VALUES (20, '批量删除', NULL, NULL, '0', '0', 'terminal:notice:train:batchDelete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '培训文件管理(4)', 17);
INSERT INTO `sys_permission` VALUES (21, '编辑', NULL, NULL, '0', '0', 'terminal:notice:train:update', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '培训文件管理(4)', 17);
INSERT INTO `sys_permission` VALUES (22, '查询列表', NULL, NULL, '0', '0', 'terminal:notice:train:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '培训文件管理(4)', 17);
INSERT INTO `sys_permission` VALUES (23, '公告管理', NULL, NULL, '0', '0', 'terminal:notice:proclamation', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '通知/公告(3)', 16);
INSERT INTO `sys_permission` VALUES (24, '发布公告', NULL, NULL, '0', '0', 'terminal:notice:proclamation:publish', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '公告管理(4)', 23);
INSERT INTO `sys_permission` VALUES (25, '删除', NULL, NULL, '0', '0', 'terminal:notice:proclamation:delete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '公告管理(4)', 23);
INSERT INTO `sys_permission` VALUES (26, '批量删除', NULL, NULL, '0', '0', 'terminal:notice:proclamation:batchDelete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '公告管理(4)', 23);
INSERT INTO `sys_permission` VALUES (27, '查看', NULL, NULL, '0', '0', 'terminal:notice:proclamation:detail', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '公告管理(4)', 23);
INSERT INTO `sys_permission` VALUES (28, '查询列表', NULL, NULL, '0', '0', 'terminal:notice:proclamation:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '公告管理(4)', 23);
INSERT INTO `sys_permission` VALUES (29, '终端应用配置', NULL, NULL, '0', '0', 'terminal:config', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端管理(2)', 1);
INSERT INTO `sys_permission` VALUES (30, '新增', NULL, NULL, '0', '0', 'terminal:config:add', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端应用配置(3)', 29);
INSERT INTO `sys_permission` VALUES (31, '删除', NULL, NULL, '0', '0', 'terminal:config:delete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端应用配置(3)', 29);
INSERT INTO `sys_permission` VALUES (32, '批量删除', NULL, NULL, '0', '0', 'terminal:config:batchDelete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端应用配置(3)', 29);
INSERT INTO `sys_permission` VALUES (33, '查询列表', NULL, NULL, '0', '0', 'terminal:config:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端应用配置(3)', 29);
INSERT INTO `sys_permission` VALUES (34, '终端日志', NULL, NULL, '0', '0', 'terminal:log', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端管理(2)', 1);
INSERT INTO `sys_permission` VALUES (35, '删除', NULL, NULL, '0', '0', 'terminal:log:delete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端日志(3)', 34);
INSERT INTO `sys_permission` VALUES (36, '查询列表', NULL, NULL, '0', '0', 'terminal:log:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端日志(3)', 34);
INSERT INTO `sys_permission` VALUES (37, '导出Excel', NULL, NULL, '0', '0', 'terminal:log:export', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '终端日志(3)', 34);
INSERT INTO `sys_permission` VALUES (38, '系统管理', NULL, NULL, '0', '0', 'system', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '零信任一级菜单', 0);
INSERT INTO `sys_permission` VALUES (39, '角色管理', NULL, NULL, '0', '0', 'system:role', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '系统管理(2)', 38);
INSERT INTO `sys_permission` VALUES (40, '新增角色', NULL, NULL, '0', '0', 'system:role:add', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '角色管理(3)', 39);
INSERT INTO `sys_permission` VALUES (41, '删除', NULL, NULL, '0', '0', 'system:role:delete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '角色管理(3)', 39);
INSERT INTO `sys_permission` VALUES (42, '修改', NULL, NULL, '0', '0', 'system:role:update', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '角色管理(3)', 39);
INSERT INTO `sys_permission` VALUES (43, '查询列表', NULL, NULL, '0', '0', 'system:role:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '角色管理(3)', 39);
INSERT INTO `sys_permission` VALUES (44, '用户管理', NULL, NULL, '0', '0', 'system:user', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '系统管理(2)', 38);
INSERT INTO `sys_permission` VALUES (45, '新增平台用户', NULL, NULL, '0', '0', 'system:user:add', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '用户管理(3)', 44);
INSERT INTO `sys_permission` VALUES (46, '人员主数据列表', NULL, NULL, '0', '0', 'system:user:mainUser', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '用户管理(3)', 44);
INSERT INTO `sys_permission` VALUES (47, '删除', NULL, NULL, '0', '0', 'system:user:delete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '用户管理(3)', 44);
INSERT INTO `sys_permission` VALUES (48, '修改', NULL, NULL, '0', '0', 'system:user:update', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '用户管理(3)', 44);
INSERT INTO `sys_permission` VALUES (49, '启用|禁用', NULL, NULL, '0', '0', 'system:user:enable', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '用户管理(3)', 44);
INSERT INTO `sys_permission` VALUES (50, '查询列表', NULL, NULL, '0', '0', 'system:user:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '用户管理(3)', 44);
INSERT INTO `sys_permission` VALUES (51, '平台操作日志', NULL, NULL, '0', '0', 'system:log', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '系统管理(2)', 38);
INSERT INTO `sys_permission` VALUES (52, '查询列表', NULL, NULL, '0', '0', 'system:log:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '平台操作日志(3)', 51);
INSERT INTO `sys_permission` VALUES (53, '智能网关', NULL, NULL, '0', '0', 'gateway', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '零信任一级菜单', 0);
INSERT INTO `sys_permission` VALUES (54, '会话管理', NULL, NULL, '0', '0', 'gateway:session', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '智能网关(2)', 53);
INSERT INTO `sys_permission` VALUES (55, '断开', NULL, NULL, '0', '0', 'gateway:session:close', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '会话管理(3)', 54);
INSERT INTO `sys_permission` VALUES (56, '查询列表', NULL, NULL, '0', '0', 'gateway:session:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '会话管理(3)', 54);
INSERT INTO `sys_permission` VALUES (57, '隧道应用', NULL, NULL, '0', '0', 'gateway:tunnel', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '智能网关(2)', 53);
INSERT INTO `sys_permission` VALUES (58, '新增隧道应用', NULL, NULL, '0', '0', 'gateway:tunnel:add', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '隧道应用(4)', 57);
INSERT INTO `sys_permission` VALUES (59, '删除', NULL, NULL, '0', '0', 'gateway:tunnel:delete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '隧道应用(4)', 57);
INSERT INTO `sys_permission` VALUES (60, '修改', NULL, NULL, '0', '0', 'gateway:tunnel:update', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '隧道应用(4)', 57);
INSERT INTO `sys_permission` VALUES (61, '查询列表', NULL, NULL, '0', '0', 'gateway:tunnel:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '隧道应用(4)', 57);
INSERT INTO `sys_permission` VALUES (62, 'HTTP应用', NULL, NULL, '0', '0', 'gateway:http', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '智能网关(2)', 53);
INSERT INTO `sys_permission` VALUES (63, '执行日志', NULL, NULL, '0', '0', 'gateway:http:log', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, 'HTTP应用(3)', 62);
INSERT INTO `sys_permission` VALUES (64, '查询列表', NULL, NULL, '0', '0', 'gateway:http:log:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '执行日志(4)', 63);
INSERT INTO `sys_permission` VALUES (65, '应用站点', NULL, NULL, '0', '0', 'gateway:http:site', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, 'HTTP应用(3)', 62);
INSERT INTO `sys_permission` VALUES (66, '新增应用站点', NULL, NULL, '0', '0', 'gateway:http:site:add', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用站点(4)', 65);
INSERT INTO `sys_permission` VALUES (67, '删除', NULL, NULL, '0', '0', 'gateway:http:site:delete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用站点(4)', 65);
INSERT INTO `sys_permission` VALUES (68, '发布', NULL, NULL, '0', '0', 'gateway:http:site:publish', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用站点(4)', 65);
INSERT INTO `sys_permission` VALUES (69, '查询列表', NULL, NULL, '0', '0', 'gateway:http:site:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用站点(4)', 65);
INSERT INTO `sys_permission` VALUES (70, '查看配置', NULL, NULL, '0', '0', 'gateway:http:site:configDetail', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用站点(4)', 65);
INSERT INTO `sys_permission` VALUES (71, '查看监控', NULL, NULL, '0', '0', 'gateway:http:site:monitor', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用站点(4)', 65);
INSERT INTO `sys_permission` VALUES (72, '查看视图', NULL, NULL, '0', '0', 'gateway:http:site:view', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用站点(4)', 65);
INSERT INTO `sys_permission` VALUES (73, '资源列表', NULL, NULL, '0', '0', 'gateway:http:site:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用站点(4)', 65);
INSERT INTO `sys_permission` VALUES (74, 'Todo++++', NULL, NULL, '0', '0', 'gateway:http:site:todo', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用站点(4)', 65);
INSERT INTO `sys_permission` VALUES (75, '应用负载组', NULL, NULL, '0', '0', 'gateway:http:group', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, 'HTTP应用(3)', 62);
INSERT INTO `sys_permission` VALUES (76, '新增负载', NULL, NULL, '0', '0', 'gateway:http:group:addGroup', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用负载组(4)', 75);
INSERT INTO `sys_permission` VALUES (77, '添加server', NULL, NULL, '0', '0', 'gateway:http:group:addServer', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用负载组(4)', 75);
INSERT INTO `sys_permission` VALUES (78, '修改', NULL, NULL, '0', '0', 'gateway:http:group:update', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用负载组(4)', 75);
INSERT INTO `sys_permission` VALUES (79, '发布', NULL, NULL, '0', '0', 'gateway:http:group:publish', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用负载组(4)', 75);
INSERT INTO `sys_permission` VALUES (80, '删除', NULL, NULL, '0', '0', 'gateway:http:group:delete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用负载组(4)', 75);
INSERT INTO `sys_permission` VALUES (81, '查询列表', NULL, NULL, '0', '0', 'gateway:http:group:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用负载组(4)', 75);
INSERT INTO `sys_permission` VALUES (82, '查询配置', NULL, NULL, '0', '0', 'gateway:http:group:detail', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用负载组(4)', 75);
INSERT INTO `sys_permission` VALUES (83, '网关配置', NULL, NULL, '0', '0', 'gateway:config', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '智能网关(2)', 53);
INSERT INTO `sys_permission` VALUES (84, '隧道配置', NULL, NULL, '0', '0', 'gateway:config:tunnel', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '网关配置(3)', 83);
INSERT INTO `sys_permission` VALUES (85, '启动|禁用', NULL, NULL, '0', '0', 'gateway:config:tunnel:enable', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '隧道配置(4)', 84);
INSERT INTO `sys_permission` VALUES (86, '详情', NULL, NULL, '0', '0', 'gateway:config:tunnel:detail', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '隧道配置(4)', 84);
INSERT INTO `sys_permission` VALUES (87, '查询列表', NULL, NULL, '0', '0', 'gateway:config:tunnel:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '隧道配置(4)', 84);
INSERT INTO `sys_permission` VALUES (88, '应用配置', NULL, NULL, '0', '0', 'gateway:config:app', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '网关配置(3)', 83);
INSERT INTO `sys_permission` VALUES (89, '新增集群', NULL, NULL, '0', '0', 'gateway:config:app:addCluster', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用配置(4)', 88);
INSERT INTO `sys_permission` VALUES (90, '新增节点', NULL, NULL, '0', '0', 'gateway:config:app:node', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用配置(4)', 88);
INSERT INTO `sys_permission` VALUES (91, '删除', NULL, NULL, '0', '0', 'gateway:config:app:delete', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用配置(4)', 88);
INSERT INTO `sys_permission` VALUES (92, '修改', NULL, NULL, '0', '0', 'gateway:config:app:update', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用配置(4)', 88);
INSERT INTO `sys_permission` VALUES (93, '查询列表', NULL, NULL, '0', '0', 'gateway:config:app:list', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '应用配置(4)', 88);
INSERT INTO `sys_permission` VALUES (94, '访问控制策略', NULL, NULL, '0', '0', 'gateway:strategy', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '智能网关(2)', 53);
INSERT INTO `sys_permission` VALUES (95, '策略中心', NULL, NULL, '0', '0', 'strategy', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '零信任一级菜单', 0);
INSERT INTO `sys_permission` VALUES (96, '视图中心', NULL, NULL, '0', '0', 'view', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '零信任一级菜单', 0);
INSERT INTO `sys_permission` VALUES (97, '评估中心', NULL, NULL, '0', '0', 'evaluation', 1, '2023-05-19 18:45:06', 1, '2023-05-19 18:45:06', 0, '零信任一级菜单', 0);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `role_id` bigint(200) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `permission_id` bigint(200) NOT NULL DEFAULT 0 COMMENT '菜单id',
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (1, 2);
INSERT INTO `sys_role_permission` VALUES (1, 3);
INSERT INTO `sys_role_permission` VALUES (1, 4);
INSERT INTO `sys_role_permission` VALUES (1, 5);
INSERT INTO `sys_role_permission` VALUES (1, 6);
INSERT INTO `sys_role_permission` VALUES (1, 7);
INSERT INTO `sys_role_permission` VALUES (1, 8);
INSERT INTO `sys_role_permission` VALUES (1, 9);
INSERT INTO `sys_role_permission` VALUES (1, 10);
INSERT INTO `sys_role_permission` VALUES (1, 11);
INSERT INTO `sys_role_permission` VALUES (1, 12);
INSERT INTO `sys_role_permission` VALUES (1, 13);
INSERT INTO `sys_role_permission` VALUES (1, 14);
INSERT INTO `sys_role_permission` VALUES (1, 15);
INSERT INTO `sys_role_permission` VALUES (1, 16);
INSERT INTO `sys_role_permission` VALUES (1, 17);
INSERT INTO `sys_role_permission` VALUES (1, 18);
INSERT INTO `sys_role_permission` VALUES (1, 19);
INSERT INTO `sys_role_permission` VALUES (1, 20);
INSERT INTO `sys_role_permission` VALUES (1, 21);
INSERT INTO `sys_role_permission` VALUES (1, 22);
INSERT INTO `sys_role_permission` VALUES (1, 23);
INSERT INTO `sys_role_permission` VALUES (1, 24);
INSERT INTO `sys_role_permission` VALUES (1, 25);
INSERT INTO `sys_role_permission` VALUES (1, 26);
INSERT INTO `sys_role_permission` VALUES (1, 27);
INSERT INTO `sys_role_permission` VALUES (1, 28);
INSERT INTO `sys_role_permission` VALUES (1, 29);
INSERT INTO `sys_role_permission` VALUES (1, 30);
INSERT INTO `sys_role_permission` VALUES (1, 31);
INSERT INTO `sys_role_permission` VALUES (1, 32);
INSERT INTO `sys_role_permission` VALUES (1, 33);
INSERT INTO `sys_role_permission` VALUES (1, 34);
INSERT INTO `sys_role_permission` VALUES (1, 35);
INSERT INTO `sys_role_permission` VALUES (1, 36);
INSERT INTO `sys_role_permission` VALUES (1, 37);
INSERT INTO `sys_role_permission` VALUES (1, 38);
INSERT INTO `sys_role_permission` VALUES (1, 39);
INSERT INTO `sys_role_permission` VALUES (1, 40);
INSERT INTO `sys_role_permission` VALUES (1, 41);
INSERT INTO `sys_role_permission` VALUES (1, 42);
INSERT INTO `sys_role_permission` VALUES (1, 43);
INSERT INTO `sys_role_permission` VALUES (1, 44);
INSERT INTO `sys_role_permission` VALUES (1, 45);
INSERT INTO `sys_role_permission` VALUES (1, 46);
INSERT INTO `sys_role_permission` VALUES (1, 47);
INSERT INTO `sys_role_permission` VALUES (1, 48);
INSERT INTO `sys_role_permission` VALUES (1, 49);
INSERT INTO `sys_role_permission` VALUES (1, 50);
INSERT INTO `sys_role_permission` VALUES (1, 51);
INSERT INTO `sys_role_permission` VALUES (1, 52);
INSERT INTO `sys_role_permission` VALUES (1, 53);
INSERT INTO `sys_role_permission` VALUES (1, 54);
INSERT INTO `sys_role_permission` VALUES (1, 55);
INSERT INTO `sys_role_permission` VALUES (1, 56);
INSERT INTO `sys_role_permission` VALUES (1, 57);
INSERT INTO `sys_role_permission` VALUES (1, 58);
INSERT INTO `sys_role_permission` VALUES (1, 59);
INSERT INTO `sys_role_permission` VALUES (1, 60);
INSERT INTO `sys_role_permission` VALUES (1, 61);
INSERT INTO `sys_role_permission` VALUES (1, 62);
INSERT INTO `sys_role_permission` VALUES (1, 63);
INSERT INTO `sys_role_permission` VALUES (1, 64);
INSERT INTO `sys_role_permission` VALUES (1, 65);
INSERT INTO `sys_role_permission` VALUES (1, 66);
INSERT INTO `sys_role_permission` VALUES (1, 67);
INSERT INTO `sys_role_permission` VALUES (1, 68);
INSERT INTO `sys_role_permission` VALUES (1, 69);
INSERT INTO `sys_role_permission` VALUES (1, 70);
INSERT INTO `sys_role_permission` VALUES (1, 71);
INSERT INTO `sys_role_permission` VALUES (1, 72);
INSERT INTO `sys_role_permission` VALUES (1, 73);
INSERT INTO `sys_role_permission` VALUES (1, 74);
INSERT INTO `sys_role_permission` VALUES (1, 75);
INSERT INTO `sys_role_permission` VALUES (1, 76);
INSERT INTO `sys_role_permission` VALUES (1, 77);
INSERT INTO `sys_role_permission` VALUES (1, 78);
INSERT INTO `sys_role_permission` VALUES (1, 79);
INSERT INTO `sys_role_permission` VALUES (1, 80);
INSERT INTO `sys_role_permission` VALUES (1, 81);
INSERT INTO `sys_role_permission` VALUES (1, 82);
INSERT INTO `sys_role_permission` VALUES (1, 83);
INSERT INTO `sys_role_permission` VALUES (1, 84);
INSERT INTO `sys_role_permission` VALUES (1, 85);
INSERT INTO `sys_role_permission` VALUES (1, 86);
INSERT INTO `sys_role_permission` VALUES (1, 87);
INSERT INTO `sys_role_permission` VALUES (1, 88);
INSERT INTO `sys_role_permission` VALUES (1, 89);
INSERT INTO `sys_role_permission` VALUES (1, 90);
INSERT INTO `sys_role_permission` VALUES (1, 91);
INSERT INTO `sys_role_permission` VALUES (1, 92);
INSERT INTO `sys_role_permission` VALUES (1, 93);
INSERT INTO `sys_role_permission` VALUES (1, 94);
INSERT INTO `sys_role_permission` VALUES (1, 95);
INSERT INTO `sys_role_permission` VALUES (1, 96);

-- ----------------------------
-- Table structure for sys_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles`;
CREATE TABLE `sys_roles`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `authority` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_ccwsk2180xxig731jlcidpi7p`(`authority`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_roles
-- ----------------------------
INSERT INTO `sys_roles` VALUES (1, '2023-04-10 17:15:57', '2023-05-17 09:57:52', 'SYSTEM_ADMIN', '所有的权限都有', 'asdasd');

-- ----------------------------
-- Table structure for sys_users
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `enabled` bit(1) NOT NULL COMMENT '账号是否启用：1正常',
  `account_non_locked` bit(1) NOT NULL COMMENT '账号是否锁定：1正常',
  `update_time` datetime(0) NOT NULL COMMENT '更新时间',
  `account_expire_date` datetime(0) NULL DEFAULT NULL,
  `credential_expire_date` datetime(0) NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UK_lgllb14jd9kcm09b9enkkbh0q`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_users
-- ----------------------------
INSERT INTO `sys_users` VALUES (1, 'admin', '$2a$10$v3Dr2TGCt0AjmJJjxiQbnew5kFeRELUZVHSp1TPc9APmKwG1khaF2', '2023-04-10 17:15:57', b'1', b'1', '2023-05-18 14:34:54', '2100-01-30 00:00:01', '2100-01-30 00:00:01', '17783202205', '邓诗杰');

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
INSERT INTO `sys_users_roles` VALUES (1, '2023-06-27 17:33:16', '2023-06-27 17:33:18', 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
