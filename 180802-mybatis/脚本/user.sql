-- 创建mybatisdb数据库，设置utf8字符集。

-- 创建user表：
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(30) DEFAULT NULL,
  `BIRTHDAY` datetime DEFAULT NULL,
  `ADDRESS` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 增加数据
insert  into `user`(`ID`,`NAME`,`BIRTHDAY`,`ADDRESS`) values (1,'夏言','1573-01-01 00:00:00','桂州村'),(2,'严嵩','1587-01-01 00:00:00','分宜县城介桥村'),(3,'徐阶','1580-01-01 00:00:00','明松江府华亭县'),(4,'高拱','1566-01-01 00:00:00','河南省新郑市高老庄村'),(5,'张居正','1558-01-01 00:00:00','江陵');
