/*
Navicat MySQL Data Transfer

Source Server         : 本地mysql
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : boot-mybatis

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2019-04-26 18:04:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for t_address
-- ----------------------------
DROP TABLE IF EXISTS `t_address`;
CREATE TABLE `t_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `province` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '省',
  `city` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '市',
  `street` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '街道',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '用户名',
  `pass_word` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '密码',
  `email` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '别名',
  `reg_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for t_user_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_user_detail`;
CREATE TABLE `t_user_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `real_name` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '真实名字',
  `status` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '状态',
  `hobby` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '爱好',
  `introduction` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '简介',
  `last_login_ip` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '最后登录ip',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
