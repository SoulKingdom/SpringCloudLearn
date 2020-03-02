/*
Navicat MySQL Data Transfer

Source Server         : 本地连接
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : log

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2020-03-02 10:14:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for operation_log
-- ----------------------------
DROP TABLE IF EXISTS `operation_log`;
CREATE TABLE `operation_log` (
  `id` varchar(256) NOT NULL,
  `opera_code` varchar(255) DEFAULT NULL,
  `app_id` varchar(36) DEFAULT NULL,
  `method` varchar(64) DEFAULT NULL,
  `operationTime` date DEFAULT NULL,
  `station` varchar(0) DEFAULT NULL,
  `station_Code` varchar(0) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(64) DEFAULT NULL,
  `user_id` varchar(4000) DEFAULT NULL,
  `user_name` varchar(4000) DEFAULT NULL,
  `user_acnt` varchar(4000) DEFAULT NULL,
  `source_type` varchar(255) DEFAULT NULL,
  `source_application` varchar(255) DEFAULT NULL,
  `source_public_ip` varchar(255) DEFAULT NULL,
  `source_private_ip` varchar(255) DEFAULT NULL,
  `source_vpn_ip` varchar(255) DEFAULT NULL,
  `source_mac` varchar(255) DEFAULT NULL,
  `target_application` varchar(255) DEFAULT NULL,
  `target_public_ip` varchar(255) DEFAULT NULL,
  `target_private_ip` varchar(255) DEFAULT NULL,
  `operation_type` varchar(255) DEFAULT NULL,
  `result` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `mail_no` varchar(255) DEFAULT NULL,
  `order_id` varchar(255) DEFAULT NULL,
  `association_number` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `feature` varchar(255) DEFAULT NULL,
  `delFlag` varchar(255) DEFAULT NULL,
  `sortNo` varchar(255) DEFAULT NULL,
  `gmt_create` varchar(255) DEFAULT NULL,
  `create_by` varchar(255) DEFAULT NULL,
  `gmt_modified` varchar(255) DEFAULT NULL,
  `last_modified_by` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
