/*
Navicat MySQL Data Transfer

Source Server         : cte
Source Server Version : 50719
Source Host           : localhost:3308
Source Database       : secspike

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-04-22 22:00:49
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ss_goods
-- ----------------------------
DROP TABLE IF EXISTS `ss_goods`;
CREATE TABLE `ss_goods` (
  `id` bigint(20) NOT NULL,
  `goods_name` varchar(255) DEFAULT NULL COMMENT '商品名',
  `goods_code` varchar(255) DEFAULT NULL COMMENT '商品编号',
  `goods_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `goods_stock` int(11) DEFAULT NULL COMMENT '商品库存',
  `goods_img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ss_goods
-- ----------------------------
INSERT INTO `ss_goods` VALUES ('1', '印尼进口Tango咔咔脆巧克力', 'goods100000', '19.90', '1000', null);
INSERT INTO `ss_goods` VALUES ('2', '马来西亚马奇新新巧克力', 'goods100001', '9.90', '500', null);
INSERT INTO `ss_goods` VALUES ('3', 'Danisa皇冠进口送礼丹麦黄油曲奇饼干163g浓郁黄油原味休闲零食', 'goods100002', '13.80', '40000', null);
INSERT INTO `ss_goods` VALUES ('4', 'GEMEZ印尼小鸡面烧烤味干脆干吃面整箱进口零食礼盒16g*30大礼包', 'goods100002', '69.90', '12000', null);
INSERT INTO `ss_goods` VALUES ('5', 'Lindt瑞士莲巧克力瑞士进口缤纷盒装500g休闲零食白色情人节礼物', 'goods100002', '178.00', '1850', null);

-- ----------------------------
-- Table structure for ss_goods_spike
-- ----------------------------
DROP TABLE IF EXISTS `ss_goods_spike`;
CREATE TABLE `ss_goods_spike` (
  `id` bigint(20) NOT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  `stock_count` int(11) DEFAULT NULL COMMENT '秒杀活动库存数量',
  `spike_price` decimal(10,2) DEFAULT NULL COMMENT '秒杀价',
  `start_time` datetime DEFAULT NULL COMMENT '秒杀开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '秒杀结束时间',
  `version` int(11) DEFAULT NULL COMMENT '并发版本控制',
  `isactive` tinyint(4) DEFAULT NULL COMMENT '该秒杀商品目前是否还有效',
  `canKill` tinyint(4) DEFAULT NULL,
  `goods_img` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ss_goods_spike
-- ----------------------------
INSERT INTO `ss_goods_spike` VALUES ('1', '1', '印尼进口Tango咔咔脆巧克力', '26', '10.00', '2020-03-20 21:37:45', '2020-04-24 22:41:56', '152', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('2', '2', '马来西亚马奇新新巧克力', '78', '5.00', '2020-03-20 21:41:10', '2020-04-17 21:41:13', '123', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('3', '4', 'GEMEZ印尼小鸡面烧烤味干脆干吃面整箱进口零食礼盒16g*30大礼包', '6', '50.00', '2020-03-20 21:41:39', '2020-04-15 21:41:42', '97', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('4', '3', '太平鸟牛油果绿连衣裙2020新款夏收腰法式复古衬衫裙仙女裙中长款', '8', '80.00', '2020-04-05 16:00:34', '2020-04-24 16:00:37', '25', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('5', '5', '新款秋冬复古改良丝绒旗袍连衣裙女大码妈妈装时尚旗袍中长款', '15', '20.00', '2020-04-05 16:02:22', '2020-05-02 16:02:25', '59', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('6', '6', '2020春装新款修身连衣裙秋冬季大码女装200斤胖妹妹冬天打底裙子', '57', '13.00', '2020-04-05 16:04:04', '2020-05-01 16:04:07', '60', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('7', '7', '欧洲站夏装女2020新款欧货潮名媛v领收腰气质裙子显瘦洋气连衣裙', '34', '59.00', '2020-04-05 16:30:18', '2020-04-05 16:30:18', '77', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('8', '8', '恩纳斯桑蚕丝真丝连衣裙女2020夏季新款宽松A字中长款过膝碎花裙', '1', '85.00', '2020-04-05 16:30:18', '2020-04-05 16:30:18', '100', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('9', '9', '巴比熊半熟芝士小蛋糕零食小吃面包整箱早餐糕点点心网红休闲食品', '3', '86.00', '2020-04-05 16:30:18', '2020-04-05 16:30:18', '105', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('10', '10', '香辣手撕酱板鸭鸭肉湖南特产孕妇可食零食雅食荟休闲零食小吃', '15', '66.00', '2020-04-05 16:30:18', '2020-04-05 16:30:18', '93', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('11', '11', '菲律宾特产芒果干500g办公室零食网红小吃蜜饯果脯水果干休闲食品', '63', '94.00', '2020-04-05 16:30:18', '2020-04-05 16:30:18', '73', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('12', '12', '香草味小口袋蒸蛋糕500g 早餐速食充饥夜宵懒人零食小吃 休闲食品', '72', '85.00', '2020-04-05 16:30:18', '2020-05-01 16:30:18', '100', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('13', '13', '蒲议蛋苕酥248g*2袋办公室零食小吃休闲食品礼包四川特产传统糕点', '75', '73.00', '2020-04-05 16:30:18', '2020-04-05 16:30:18', '80', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('14', '14', '傻二哥锅往时光锅巴900克 散装锅巴零食小包装 休闲零食大礼包', '57', '26.00', '2020-04-05 16:30:18', '2020-05-01 16:30:18', '137', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('15', '15', '网红香酥小黄鱼干特产零食即食碳烤酥脆黄花鱼干货零食休闲小吃', '62', '50.00', '2020-04-05 16:30:18', '2020-04-05 16:30:18', '108', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('16', '16', '味了你山药小酥卷脆薯片网红山药脆片礼包散装整箱膨化休闲小零食', '40', '101.00', '2020-04-05 16:30:18', '2020-04-05 16:30:18', '93', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('17', '17', '大吃兄糯米锅巴400gX3盒 粮悦休闲零食小吃农家锅巴散装\r\n大吃兄糯米锅巴400gX3盒 粮悦休闲零食小吃农家锅巴散装\r\n', '13', '41.00', '2020-04-05 16:30:18', '2020-04-05 16:30:18', '68', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('18', '18', '超值30包绝艺麻辣鸭脖子特产零食10包卤味休闲小吃大礼包', '44', '99.00', '2020-04-05 16:30:18', '2020-04-05 16:30:18', '68', '1', null, '/img/peijian2.jpg');
INSERT INTO `ss_goods_spike` VALUES ('19', '19', '黄楝公社手工小麻花208gX3湖北襄阳特产网红休闲零食小吃麻辣海苔', '82', '21.00', '2020-04-05 16:30:18', '2020-04-05 16:30:18', '69', '1', null, '/img/peijian2.jpg');

-- ----------------------------
-- Table structure for ss_order
-- ----------------------------
DROP TABLE IF EXISTS `ss_order`;
CREATE TABLE `ss_order` (
  `order_id` varchar(20) NOT NULL,
  `goods_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ss_order
-- ----------------------------
INSERT INTO `ss_order` VALUES ('2914514634805248', '3', '684');
INSERT INTO `ss_order` VALUES ('2913929458094080', '2', '684');
INSERT INTO `ss_order` VALUES ('2913857269927936', '1', '684');
INSERT INTO `ss_order` VALUES ('2913323435692032', '4', '684');
INSERT INTO `ss_order` VALUES ('2914568951042048', '5', '684');
INSERT INTO `ss_order` VALUES ('2915968737087488', '12', '684');
INSERT INTO `ss_order` VALUES ('5402672958017536', '1', '1111');
INSERT INTO `ss_order` VALUES ('5402721200902144', '4', '1111');

-- ----------------------------
-- Table structure for ss_order_info
-- ----------------------------
DROP TABLE IF EXISTS `ss_order_info`;
CREATE TABLE `ss_order_info` (
  `order_id` varchar(255) NOT NULL,
  `goods_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `status` int(255) DEFAULT NULL COMMENT '订单状态，-1表示无效，0表示未付款，1表示已付款，2表示已取消',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `price` decimal(10,2) DEFAULT NULL,
  `goods_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ss_order_info
-- ----------------------------
INSERT INTO `ss_order_info` VALUES ('5402672958017536', '1', '1111', '0', '2020-04-14 12:04:31', null, '印尼进口Tango咔咔脆巧克力');
INSERT INTO `ss_order_info` VALUES ('5402721200902144', '4', '1111', '0', '2020-04-14 12:04:43', null, 'GEMEZ印尼小鸡面烧烤味干脆干吃面整箱进口零食礼盒16g*30大礼包');

-- ----------------------------
-- Table structure for ss_user
-- ----------------------------
DROP TABLE IF EXISTS `ss_user`;
CREATE TABLE `ss_user` (
  `id` bigint(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `register_date` datetime DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ss_user
-- ----------------------------
INSERT INTO `ss_user` VALUES ('1', '1111', '1111', null, null, null);
INSERT INTO `ss_user` VALUES ('2', '2222', '2222', null, null, null);
INSERT INTO `ss_user` VALUES ('3', '684', '1111', null, null, null);

-- ----------------------------
-- Procedure structure for proc_batch_insert
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_batch_insert`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_batch_insert`()
BEGIN
DECLARE i INT;
SET i=7;
WHILE i<20
DO
insert into ss_goods_spike(id,goods_id,stock_count,spike_price,start_time,end_time,version,isactive) values (i,i,FLOOR(RAND()*100), FLOOR(RAND()*100),NOW(),NOW() ,0,1);
SET i=i+1;
END WHILE ;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for proc_batch_update
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_batch_update`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `proc_batch_update`()
BEGIN
DECLARE i INT;
SET i=1;
WHILE i<20
DO
update ss_goods_spike set goods_img="/img/peijian2.jpg";
SET i=i+1;
END WHILE ;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for test_insert
-- ----------------------------
DROP PROCEDURE IF EXISTS `test_insert`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `test_insert`()
BEGIN
 
DECLARE i INT DEFAULT 7;
 
WHILE i<20
DO
insert into ss_goods_spike(id,goods_id,stock_count,spike_price,start_time,end_time,version,isactive) values (i,i,FLOOR(RAND()*100), FLOOR(RAND()*100),NOW(),DATE(‘2020-05-01’),0,1);
SET i=i+1;
END WHILE ;
commit;
 
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for test_insert1
-- ----------------------------
DROP PROCEDURE IF EXISTS `test_insert1`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `test_insert1`()
BEGIN
 
DECLARE i INT DEFAULT 7;
 
WHILE i<20
DO
insert into ss_goods_spike(id,goods_id,stock_count,spike_price,start_time,end_time,version,isactive) values (i,i,FLOOR(RAND()*100), FLOOR(RAND()*100),NOW(),DATE(‘2020-05-01’),0,1);
SET i=i+1;
END WHILE ;
commit;
 
END
;;
DELIMITER ;
