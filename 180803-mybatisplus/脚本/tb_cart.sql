-- ----------------------------
-- Table structure for tb_cart
-- ----------------------------
DROP TABLE IF EXISTS `tb_cart`;
CREATE TABLE `tb_cart` (
  `id` int(50) NOT NULL AUTO_INCREMENT,
  `user_id` int(50) DEFAULT NULL,
  `item_id` int(50) DEFAULT NULL,
  `item_title` varchar(100) DEFAULT NULL,
  `item_image` varchar(100) DEFAULT NULL,
  `item_price` double(100,2) DEFAULT NULL,
  `num` int(100) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `updated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cart
-- ----------------------------
BEGIN;
INSERT INTO `tb_cart` VALUES (51, 1, 31, '华为 白色 大屏', NULL, 2000.00, 13, NULL, NULL);
INSERT INTO `tb_cart` VALUES (52, 2, 32, '三星 黑色 双卡双待', NULL, 3000.00, 2, NULL, NULL);
INSERT INTO `tb_cart` VALUES (53, 3, 562379, 'title', 'image', 100.00, 3, '2018-08-07 00:00:00', '2018-08-07 00:00:00');
INSERT INTO `tb_cart` VALUES (54, 4, 562379, 'title', 'image', 100.00, 3, '2018-08-07 15:15:32', '2018-08-07 15:15:32');
INSERT INTO `tb_cart` VALUES (55, 5, 12313, 'title', 'image', 100.00, 1, '2018-08-07 15:16:27', '2018-08-07 15:16:27');
COMMIT;

