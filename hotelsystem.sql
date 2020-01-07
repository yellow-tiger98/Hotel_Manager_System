/*
Navicat MySQL Data Transfer

Source Server         : 1
Source Server Version : 50720
Source Host           : localhost:3306
Source Database       : hotelsystem

Target Server Type    : MYSQL
Target Server Version : 50720
File Encoding         : 65001

Date: 2020-01-07 13:08:36
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for clean
-- ----------------------------
DROP TABLE IF EXISTS `clean`;
CREATE TABLE `clean` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roomid` int(11) DEFAULT NULL,
  `floor` int(11) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `opid` varchar(30) NOT NULL,
  `opname` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of clean
-- ----------------------------
INSERT INTO `clean` VALUES ('1', '1001', '1', '测试', '2019-10-06 21:42:53', 'c001', '王小花');
INSERT INTO `clean` VALUES ('2', '1003', '1', '测试', '2019-10-06 21:43:16', 'c001', '王小花');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` varchar(32) NOT NULL,
  `account` varchar(32) DEFAULT NULL,
  `name` varchar(1) DEFAULT NULL,
  `pass` varchar(32) DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for lodger
-- ----------------------------
DROP TABLE IF EXISTS `lodger`;
CREATE TABLE `lodger` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `lodgingInfoId` varchar(32) DEFAULT NULL,
  `certificate` varchar(32) DEFAULT NULL,
  `certificateNo` varchar(20) NOT NULL,
  `lodgerName` varchar(32) DEFAULT NULL,
  `sex` varchar(1) DEFAULT NULL,
  `isRegister` int(1) DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL,
  `note` varchar(32) DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lodger
-- ----------------------------

-- ----------------------------
-- Table structure for lodginginfo
-- ----------------------------
DROP TABLE IF EXISTS `lodginginfo`;
CREATE TABLE `lodginginfo` (
  `id` varchar(32) NOT NULL,
  `entryDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `leaveDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `days` varchar(11) DEFAULT NULL,
  `roomId` varchar(32) DEFAULT NULL,
  `deposit` int(11) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `reservationId` varchar(32) DEFAULT NULL,
  `operatorId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lodginginfo
-- ----------------------------

-- ----------------------------
-- Table structure for maintain
-- ----------------------------
DROP TABLE IF EXISTS `maintain`;
CREATE TABLE `maintain` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roomId` varchar(20) DEFAULT NULL,
  `floor` int(20) DEFAULT NULL,
  `info` varchar(100) DEFAULT NULL,
  `time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `operator` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `roomId` (`roomId`),
  KEY `floor` (`floor`),
  CONSTRAINT `maintain_ibfk_1` FOREIGN KEY (`roomId`) REFERENCES `room` (`id`),
  CONSTRAINT `maintain_ibfk_2` FOREIGN KEY (`floor`) REFERENCES `room` (`floor`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of maintain
-- ----------------------------
INSERT INTO `maintain` VALUES ('1', '1001', '1', '更换了左侧台灯灯泡', '2019-10-06 16:49:15', '肯师傅');
INSERT INTO `maintain` VALUES ('2', '1001', '1', '修复浴室天花板漏水状况', '2019-10-06 16:49:43', '肯师傅');
INSERT INTO `maintain` VALUES ('3', '1001', '1', null, '2019-10-06 17:35:10', '肯师傅');
INSERT INTO `maintain` VALUES ('4', '1002', '1', '修复电视没有信号的问题', '2019-10-06 17:36:49', '鲍师傅');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` varchar(32) NOT NULL,
  `menuname` varchar(32) DEFAULT NULL,
  `locked` varchar(1) DEFAULT NULL,
  `descript` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '用户管理', '1', '管理员使用');
INSERT INTO `menu` VALUES ('10', '营业数据报表', '0', '工作人员使用');
INSERT INTO `menu` VALUES ('11', '会员信息管理', '0', '工作人员使用');
INSERT INTO `menu` VALUES ('12', '客人信息查询', '0', '工作人员使用');
INSERT INTO `menu` VALUES ('2', '角色管理', '1', '管理员使用');
INSERT INTO `menu` VALUES ('3', '权限管理', '1', '管理员使用');
INSERT INTO `menu` VALUES ('4', '菜单管理', '1', '管理员使用');
INSERT INTO `menu` VALUES ('5', '房间管理', '0', '酒店经理使用');
INSERT INTO `menu` VALUES ('6', '房间类型管理', '0', '酒店经理使用');
INSERT INTO `menu` VALUES ('7', '入住管理', '0', '工作人员使用');
INSERT INTO `menu` VALUES ('8', '结帐管理', '0', '工作人员使用');
INSERT INTO `menu` VALUES ('9', '房态信息查询', '0', '工作人员使用');

-- ----------------------------
-- Table structure for menuitem
-- ----------------------------
DROP TABLE IF EXISTS `menuitem`;
CREATE TABLE `menuitem` (
  `id` varchar(32) NOT NULL,
  `itemname` varchar(32) DEFAULT NULL,
  `menuId` varchar(32) DEFAULT NULL,
  `urlLink` varchar(64) DEFAULT NULL,
  `visible` varchar(1) DEFAULT NULL,
  `descript` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menuitem
-- ----------------------------
INSERT INTO `menuitem` VALUES ('1', '用户列表', '1', '/Admin/viewUserList', '1', '用户管理');
INSERT INTO `menuitem` VALUES ('10', '保存角色', '2', '/Admin/saveRole', '0', '角色管理');
INSERT INTO `menuitem` VALUES ('11', '角色权限指派', '3', '/Admin/rolePermission', '1', '权限管理');
INSERT INTO `menuitem` VALUES ('2', '新增用户', '1', '/Admin/viewAddUser', '1', '用户管理');
INSERT INTO `menuitem` VALUES ('21', '房间列表', '5', '/Room/viewRoomList', '1', '房间管理');
INSERT INTO `menuitem` VALUES ('22', '新增房间', '5', '/Room/viewAddRoom', '1', '房间管理');
INSERT INTO `menuitem` VALUES ('23', '编辑房间', '5', '/Room/viewEditRoom', '0', '房间管理');
INSERT INTO `menuitem` VALUES ('24', '删除房间', '5', '/Room/delRoom', '0', '房间管理');
INSERT INTO `menuitem` VALUES ('25', '保存房间', '5', '/Room/saveRoom', '0', '房间管理');
INSERT INTO `menuitem` VALUES ('26', '房间类型列表', '6', '/RoomType/viewTypeList', '1', null);
INSERT INTO `menuitem` VALUES ('27', '新增房间类型', '6', '/RoomType/viewAddType', '1', null);
INSERT INTO `menuitem` VALUES ('28', '编辑房间类型', '6', '/RoomType/viewEditType', '0', null);
INSERT INTO `menuitem` VALUES ('29', '删除房间类型', '6', '/RoomType/delType', '0', null);
INSERT INTO `menuitem` VALUES ('3', '编辑用户', '1', '/Admin/viewEditUser', '0', '用户管理');
INSERT INTO `menuitem` VALUES ('30', '保存房间类型', '6', '/RoomType/saveType', '0', null);
INSERT INTO `menuitem` VALUES ('31', '登记入住', '7', '/LodgingInfo/registerEntry', '1', '入住管理(6)');
INSERT INTO `menuitem` VALUES ('33', '已结帐宾客', '8', '/Reception/viewHadPayList', '1', '结帐管理');
INSERT INTO `menuitem` VALUES ('34', '未结帐宾客', '8', '/Reception/viewNoPayList', '1', '结帐管理');
INSERT INTO `menuitem` VALUES ('36', '实时房态', '9', '/Reception/viewRoomRS', '1', '房态信息查询');
INSERT INTO `menuitem` VALUES ('37', '房态设置', '9', '/Reception/setRoomStatus', '1', '房态信息查询');
INSERT INTO `menuitem` VALUES ('38', '维修记录', '9', '/Reception/viewRepairList', '1', '房态信息查询');
INSERT INTO `menuitem` VALUES ('39', '房扫查询', '9', '/Reception/viewCleanList', '1', '房态信息查询');
INSERT INTO `menuitem` VALUES ('4', '删除用户', '1', '/Admin/delUser', '0', '用户管理');
INSERT INTO `menuitem` VALUES ('5', '保存用户', '1', '/Admin/saveUser', '0', '用户管理');
INSERT INTO `menuitem` VALUES ('6', '角色列表', '2', '/Admin/viewRoleList', '1', '角色管理');
INSERT INTO `menuitem` VALUES ('7', '新增角色', '2', '/Admin/viewAddRole', '1', '角色管理');
INSERT INTO `menuitem` VALUES ('8', '编辑角色', '2', '/Admin/viewEditRole', '0', '角色管理');
INSERT INTO `menuitem` VALUES ('9', '删除角色', '2', '/Admin/delRole', '0', '角色管理');

-- ----------------------------
-- Table structure for reckoning
-- ----------------------------
DROP TABLE IF EXISTS `reckoning`;
CREATE TABLE `reckoning` (
  `id` int(32) NOT NULL AUTO_INCREMENT,
  `lodgingInfoId` varchar(32) DEFAULT NULL,
  `amount` int(11) NOT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `operatorId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reckoning
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` varchar(32) NOT NULL,
  `roleName` varchar(32) DEFAULT NULL,
  `descript` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('*', '系统管理员', '用于管理此系统用户信息和权限分配');
INSERT INTO `role` VALUES ('0', '酒店经理', null);
INSERT INTO `role` VALUES ('1', '酒店前台', null);
INSERT INTO `role` VALUES ('2', '清洁人员', null);
INSERT INTO `role` VALUES ('3', '维修人员', null);

-- ----------------------------
-- Table structure for roleitem
-- ----------------------------
DROP TABLE IF EXISTS `roleitem`;
CREATE TABLE `roleitem` (
  `id` varchar(32) NOT NULL,
  `roleId` varchar(32) DEFAULT NULL,
  `itemId` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roleitem
-- ----------------------------
INSERT INTO `roleitem` VALUES ('a01', '*', '1');
INSERT INTO `roleitem` VALUES ('a02', '*', '2');
INSERT INTO `roleitem` VALUES ('a03', '*', '3');
INSERT INTO `roleitem` VALUES ('a04', '*', '4');
INSERT INTO `roleitem` VALUES ('a05', '*', '5');
INSERT INTO `roleitem` VALUES ('a06', '*', '6');
INSERT INTO `roleitem` VALUES ('a07', '*', '7');
INSERT INTO `roleitem` VALUES ('a08', '*', '8');
INSERT INTO `roleitem` VALUES ('a09', '*', '9');
INSERT INTO `roleitem` VALUES ('a10', '*', '10');
INSERT INTO `roleitem` VALUES ('a11', '*', '11');
INSERT INTO `roleitem` VALUES ('m01', '0', '21');
INSERT INTO `roleitem` VALUES ('m02', '0', '22');
INSERT INTO `roleitem` VALUES ('m03', '0', '23');
INSERT INTO `roleitem` VALUES ('m04', '0', '24');
INSERT INTO `roleitem` VALUES ('m05', '0', '25');
INSERT INTO `roleitem` VALUES ('m06', '0', '26');
INSERT INTO `roleitem` VALUES ('m07', '0', '27');
INSERT INTO `roleitem` VALUES ('m08', '0', '28');
INSERT INTO `roleitem` VALUES ('m09', '0', '29');
INSERT INTO `roleitem` VALUES ('m10', '0', '30');
INSERT INTO `roleitem` VALUES ('s01', '1', '31');
INSERT INTO `roleitem` VALUES ('s02', '1', '32');
INSERT INTO `roleitem` VALUES ('s03', '1', '33');
INSERT INTO `roleitem` VALUES ('s04', '1', '34');
INSERT INTO `roleitem` VALUES ('s05', '1', '35');
INSERT INTO `roleitem` VALUES ('s06', '1', '36');
INSERT INTO `roleitem` VALUES ('s07', '1', '37');
INSERT INTO `roleitem` VALUES ('s08', '1', '38');
INSERT INTO `roleitem` VALUES ('s09', '1', '39');
INSERT INTO `roleitem` VALUES ('s10', '1', '40');

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` varchar(32) NOT NULL,
  `typeId` varchar(32) DEFAULT NULL,
  `floor` int(11) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `floor` (`floor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of room
-- ----------------------------
INSERT INTO `room` VALUES ('1001', '03', '1', '清洁中');
INSERT INTO `room` VALUES ('1002', '01', '1', '维修中');
INSERT INTO `room` VALUES ('1003', '01', '1', '清洁中');
INSERT INTO `room` VALUES ('1004', '02', '1', '空闲');
INSERT INTO `room` VALUES ('1005', '01', '1', '空闲');
INSERT INTO `room` VALUES ('1006', '01', '1', '空闲');
INSERT INTO `room` VALUES ('1007', '01', '1', '空闲');
INSERT INTO `room` VALUES ('1008', '02', '1', '空闲');
INSERT INTO `room` VALUES ('1009', '02', '1', '空闲');
INSERT INTO `room` VALUES ('1010', '02', '1', '空闲');
INSERT INTO `room` VALUES ('2001', '03', '2', '空闲');
INSERT INTO `room` VALUES ('2002', '03', '2', '清洁中');
INSERT INTO `room` VALUES ('2003', '03', '2', '空闲');
INSERT INTO `room` VALUES ('2004', '04', '2', '空闲');
INSERT INTO `room` VALUES ('2005', '04', '2', '空闲');
INSERT INTO `room` VALUES ('2006', '04', '2', '空闲');
INSERT INTO `room` VALUES ('2007', '03', '2', '空闲');
INSERT INTO `room` VALUES ('2008', '03', '2', '空闲');
INSERT INTO `room` VALUES ('2009', '04', '2', '空闲');
INSERT INTO `room` VALUES ('2010', '04', '2', '空闲');
INSERT INTO `room` VALUES ('3001', '01', '3', '空闲');
INSERT INTO `room` VALUES ('3003', '01', '3', '空闲');
INSERT INTO `room` VALUES ('3004', '01', '3', '空闲');
INSERT INTO `room` VALUES ('3005', '02', '3', '空闲');
INSERT INTO `room` VALUES ('3006', '02', '3', '空闲');
INSERT INTO `room` VALUES ('3007', '02', '3', '空闲');
INSERT INTO `room` VALUES ('3008', '01', '3', '空闲');
INSERT INTO `room` VALUES ('3009', '01', '3', '空闲');
INSERT INTO `room` VALUES ('3010', '02', '3', '空闲');
INSERT INTO `room` VALUES ('4001', '01', '4', '空闲');
INSERT INTO `room` VALUES ('4002', '02', '4', '空闲');
INSERT INTO `room` VALUES ('4003', '03', '4', '空闲');
INSERT INTO `room` VALUES ('4004', '04', '4', '空闲');
INSERT INTO `room` VALUES ('4005', '04', '4', '空闲');
INSERT INTO `room` VALUES ('4006', '03', '4', '空闲');
INSERT INTO `room` VALUES ('4007', '03', '4', '空闲');
INSERT INTO `room` VALUES ('4008', '01', '4', '空闲');
INSERT INTO `room` VALUES ('4009', '01', '4', '空闲');
INSERT INTO `room` VALUES ('4010', '02', '4', '空闲');
INSERT INTO `room` VALUES ('5001', '03', '5', '空闲');
INSERT INTO `room` VALUES ('5002', '03', '5', '空闲');
INSERT INTO `room` VALUES ('5003', '03', '5', '空闲');
INSERT INTO `room` VALUES ('5004', '04', '5', '空闲');
INSERT INTO `room` VALUES ('5005', '03', '5', '空闲');
INSERT INTO `room` VALUES ('5006', '03', '5', '空闲');
INSERT INTO `room` VALUES ('5007', '04', '5', '空闲');
INSERT INTO `room` VALUES ('5008', '04', '5', '空闲');
INSERT INTO `room` VALUES ('5009', '03', '5', '空闲');
INSERT INTO `room` VALUES ('5010', '03', '5', '空闲');
INSERT INTO `room` VALUES ('6666', '05', '6', '空闲');
INSERT INTO `room` VALUES ('6667', '05', '6', '空闲');
INSERT INTO `room` VALUES ('6668', '05', '6', '入住中');

-- ----------------------------
-- Table structure for roomtype
-- ----------------------------
DROP TABLE IF EXISTS `roomtype`;
CREATE TABLE `roomtype` (
  `id` varchar(32) NOT NULL,
  `typename` varchar(32) DEFAULT NULL,
  `dayPrice` int(11) DEFAULT NULL,
  `hourPrice` int(11) DEFAULT NULL,
  `residence` int(11) DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of roomtype
-- ----------------------------
INSERT INTO `roomtype` VALUES ('01', '标准单人间', '128', '38', '128', '2019-09-05 10:56:17');
INSERT INTO `roomtype` VALUES ('02', '标准双人间', '158', '48', '158', '2019-09-02 11:20:33');
INSERT INTO `roomtype` VALUES ('03', '豪华单人间', '168', null, '168', '2019-09-02 11:22:26');
INSERT INTO `roomtype` VALUES ('04', '豪华双人间', '198', null, '198', '2019-09-02 11:22:33');
INSERT INTO `roomtype` VALUES ('05', '行政套房', '268', null, '268', '2019-09-02 11:22:24');
INSERT INTO `roomtype` VALUES ('06', 'test', '111', '0', '111', '2019-09-03 10:41:38');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL,
  `account` varchar(32) DEFAULT NULL,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `sex` varchar(32) DEFAULT NULL,
  `roleId` varchar(32) DEFAULT NULL,
  `createDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '系统管理员', 'admin', null, '*', '2019-09-01 16:05:04');
INSERT INTO `user` VALUES ('3', 'candy', '肯迪', '456', '女', '1', '2019-08-28 17:35:00');
INSERT INTO `user` VALUES ('a001', 'andy01', '安迪1', '123', '女', '0', '2019-09-01 12:21:43');
INSERT INTO `user` VALUES ('a002', 'andy02', '安迪2', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('a003', 'andy03', '安迪3', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('a004', 'andy04', '安迪4', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('a005', 'andy05', '安迪5', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('a006', 'andy06', '安迪6', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('a007', 'andy07', '安迪7', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('a008', 'andy08', '安迪8', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('a009', 'andy09', '安迪9', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('a010', 'andy10', '安迪10', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('a011', 'andy11', '安迪11', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('a012', 'andy12', '安迪12', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('a013', 'andy13', '安迪13', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('a014', 'andy14', '安迪14', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('a015', 'andy15', '安迪15', '123', '男', '0', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('b001', 'candy01', '肯迪01', '123', '女', '1', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('b002', 'candy02', '肯迪02', '123', '女', '1', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('b003', 'candy03', '肯迪03', '123', '男', '1', '2019-09-01 12:19:02');
INSERT INTO `user` VALUES ('b004', 'candy04', '肯迪04', '123', '女', '1', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('b005', 'candy05', '肯迪05', '123', '女', '1', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('b006', 'candy06', '肯迪06', '123', '女', '1', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('b007', 'candy07', '肯迪07', '123', '女', '1', '2019-08-30 14:29:15');
INSERT INTO `user` VALUES ('b008', 'candy08', '肯迪08', '123', '女', '1', '2019-08-30 14:29:16');
INSERT INTO `user` VALUES ('b009', 'candy09', '肯迪09', '123', '女', '1', '2019-08-30 14:29:16');
INSERT INTO `user` VALUES ('b010', 'candy10', '肯迪10', '123', '女', '1', '2019-08-30 14:29:16');
INSERT INTO `user` VALUES ('b011', 'add_test', 'test', '13', '男', '1', '2019-09-01 23:07:52');
INSERT INTO `user` VALUES ('c001', 'wangxiaohua', '王小花', '123', '女', '2', '2019-10-06 21:07:07');
INSERT INTO `user` VALUES ('d001', 'ken', '肯师傅', '123', '男', '3', '2019-09-16 19:34:24');
INSERT INTO `user` VALUES ('d002', 'Bob', '鲍师傅', '123', '男', '3', '2019-09-22 20:33:18');

-- ----------------------------
-- View structure for view_detail_list
-- ----------------------------
DROP VIEW IF EXISTS `view_detail_list`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_detail_list` AS select `ld`.`lodgingInfoId` AS `lodgingInfoId`,`ld`.`lodgerName` AS `lodgerName`,`ld`.`isRegister` AS `isRegister`,`lf`.`roomId` AS `roomId`,`lf`.`entryDate` AS `entryDate`,`lf`.`leaveDate` AS `leaveDate`,`lf`.`days` AS `days`,`lf`.`deposit` AS `deposit` from (`lodger` `ld` left join `lodginginfo` `lf` on((`ld`.`lodgingInfoId` = `lf`.`id`))) ;

-- ----------------------------
-- View structure for view_hadpay_list
-- ----------------------------
DROP VIEW IF EXISTS `view_hadpay_list`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_hadpay_list` AS select `ld`.`lodgingInfoId` AS `lodgingInfoId`,`ld`.`lodgerName` AS `lodgerName`,`ld`.`sex` AS `sex`,`ld`.`phone` AS `phone`,`ld`.`certificate` AS `certificate`,`ld`.`certificateNo` AS `certificateNo`,`ld`.`note` AS `note`,`lf`.`entryDate` AS `entryDate`,`lf`.`leaveDate` AS `leaveDate`,`lf`.`days` AS `days`,`lf`.`roomId` AS `roomId`,`lf`.`deposit` AS `deposit` from (`lodger` `ld` left join `lodginginfo` `lf` on((`ld`.`lodgingInfoId` = `lf`.`id`))) where ((`ld`.`isRegister` = 1) and (`lf`.`status` = '已离店')) ;

-- ----------------------------
-- View structure for view_nopay_list
-- ----------------------------
DROP VIEW IF EXISTS `view_nopay_list`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_nopay_list` AS select `ld`.`lodgingInfoId` AS `lodgingInfoId`,`ld`.`certificate` AS `certificate`,`ld`.`certificateNo` AS `certificateNo`,`ld`.`sex` AS `sex`,`ld`.`phone` AS `phone`,`ld`.`note` AS `note`,`ld`.`lodgerName` AS `lodgerName`,`lf`.`roomId` AS `roomId`,`lf`.`entryDate` AS `entryDate`,`lf`.`leaveDate` AS `leaveDate`,`lf`.`days` AS `days`,`lf`.`deposit` AS `deposit` from (`lodger` `ld` left join `lodginginfo` `lf` on((`ld`.`lodgingInfoId` = `lf`.`id`))) where ((`lf`.`status` = '入住中') and (`ld`.`isRegister` = 1)) ;

-- ----------------------------
-- View structure for view_pay_list
-- ----------------------------
DROP VIEW IF EXISTS `view_pay_list`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_pay_list` AS select `vl`.`lodgingInfoId` AS `lodgingInfoId`,`vl`.`lodgerName` AS `lodgerName`,`vl`.`sex` AS `sex`,`vl`.`phone` AS `phone`,`vl`.`certificate` AS `certificate`,`vl`.`certificateNo` AS `certificateNo`,`vl`.`note` AS `note`,`vl`.`entryDate` AS `entryDate`,`vl`.`leaveDate` AS `leaveDate`,`vl`.`days` AS `days`,`vl`.`roomId` AS `roomId`,`vl`.`deposit` AS `deposit`,`rk`.`amount` AS `amount`,`rk`.`createDate` AS `createDate`,`rk`.`operatorId` AS `operatorId` from (`view_hadpay_list` `vl` left join `reckoning` `rk` on((`vl`.`lodgingInfoId` = `rk`.`lodgingInfoId`))) ;

-- ----------------------------
-- View structure for view_realrs_list
-- ----------------------------
DROP VIEW IF EXISTS `view_realrs_list`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_realrs_list` AS select `d1`.`id` AS `id`,`d1`.`floor` AS `floor`,`d1`.`status` AS `status`,`d1`.`typename` AS `typename`,ifnull(`d2`.`status`,'无') AS `ldstatus`,ifnull(`d2`.`roomId`,'无') AS `ldroomid`,ifnull(`d2`.`lodgerName`,'无') AS `ldname` from (((select `rm`.`id` AS `id`,`rm`.`floor` AS `floor`,`rm`.`status` AS `status`,`rt`.`typename` AS `typename` from (`hotelsystem`.`room` `rm` left join `hotelsystem`.`roomtype` `rt` on((`rm`.`typeId` = `rt`.`id`))))) `d1` left join (select `lf`.`status` AS `status`,`lf`.`roomId` AS `roomId`,`ld`.`lodgerName` AS `lodgerName` from (`hotelsystem`.`lodginginfo` `lf` left join `hotelsystem`.`lodger` `ld` on(((`lf`.`id` = `ld`.`lodgingInfoId`) and (`ld`.`isRegister` = 1)))) where (`lf`.`status` = '入住中')) `d2` on((`d1`.`id` = `d2`.`roomId`))) ;

-- ----------------------------
-- View structure for view_role_menu
-- ----------------------------
DROP VIEW IF EXISTS `view_role_menu`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_role_menu` AS select `ri`.`roleId` AS `roleid`,`ri`.`itemId` AS `itemid`,`mi`.`itemname` AS `itemname`,`mi`.`menuId` AS `menuId`,`mi`.`urlLink` AS `urlLink`,`mi`.`visible` AS `visible`,`m`.`menuname` AS `menuname`,`m`.`locked` AS `locked` from ((`roleitem` `ri` join `menuitem` `mi`) join `menu` `m`) where ((`ri`.`itemId` = `mi`.`id`) and (`mi`.`menuId` = `m`.`id`)) ;

-- ----------------------------
-- View structure for view_roomprice_list
-- ----------------------------
DROP VIEW IF EXISTS `view_roomprice_list`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_roomprice_list` AS select `rm`.`id` AS `id`,`rm`.`floor` AS `floor`,`rt`.`dayPrice` AS `dayPrice`,`rt`.`hourPrice` AS `hourPrice`,`rt`.`residence` AS `residence` from (`room` `rm` left join `roomtype` `rt` on((`rm`.`typeId` = `rt`.`id`))) ;

-- ----------------------------
-- View structure for view_room_list
-- ----------------------------
DROP VIEW IF EXISTS `view_room_list`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view_room_list` AS select `r`.`id` AS `id`,`r`.`typeId` AS `typeId`,`r`.`floor` AS `floor`,`r`.`status` AS `status`,`rt`.`typename` AS `typename`,`rt`.`dayPrice` AS `dayprice`,`rt`.`hourPrice` AS `hourprice`,`rt`.`residence` AS `residence` from (`room` `r` left join `roomtype` `rt` on((`r`.`typeId` = `rt`.`id`))) order by `r`.`id` ;
