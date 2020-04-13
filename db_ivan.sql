/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1_3306
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : 127.0.0.1:3306
 Source Schema         : db_ivan

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 13/04/2020 20:06:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_merchant
-- ----------------------------
DROP TABLE IF EXISTS `t_merchant`;
CREATE TABLE `t_merchant`  (
  `mId` int(11) NOT NULL AUTO_INCREMENT,
  `merAccount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '后台登录账号',
  `merPassword` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '后台登录密码',
  `merAddress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商家地址',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `wxCode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信二维码img地址',
  `merPhone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系方式',
  `merInfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`mId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_merchant
-- ----------------------------
INSERT INTO `t_merchant` VALUES (1, 'ivan123', '1021', '广东省广州市白云区太和镇兴太三路638号校内小凡摄影馆', 'ivan123@qq.com', NULL, '18898341048', '我们是一家怀有梦想的摄影馆，我叫张小凡，谢谢。');

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `oId` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单主键',
  `pId` int(11) NOT NULL COMMENT '套餐id',
  `openId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户唯一openId',
  `speName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '规格名称',
  `makeDate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '预约时间',
  `photoState` int(1) NOT NULL COMMENT '摄影状态，0未拍摄，1已拍摄，2过期（已取消）',
  `postState` int(1) NOT NULL COMMENT '取(寄)状态，0自取，1寄',
  `tfetch` int(1) NOT NULL COMMENT '取片状态，0未取片，1已取片，2过期异常',
  `makeName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '预约人姓名',
  `makePhone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '预约人电话',
  `makeSex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '性别',
  `payPrice` decimal(10, 2) NOT NULL COMMENT '支付金额',
  `sendSite` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮寄地址',
  `evaluate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单评价',
  PRIMARY KEY (`oId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (6, 1, 'opMzX5bltao560kWx467UgCguoqQ', '双色背景', '2020-04-08/18:31', 0, 1, 0, '张小凡', '18898341048', '男', 89.00, '广东省广州市天河区天河学院', '拍的很好，摄影师张小凡是个美女');
INSERT INTO `t_order` VALUES (7, 2, 'opMzX5bltao560kWx467UgCguoqQ', '不同姿势*3张', '2020-04-07/20:00', 1, 1, 0, '张小凡', '18898374676', '男', 288.00, '广东省广州市从化区天河学院', '很好');

-- ----------------------------
-- Table structure for t_photo
-- ----------------------------
DROP TABLE IF EXISTS `t_photo`;
CREATE TABLE `t_photo`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '套餐id',
  `photoName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '套餐名称',
  `coverImg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面图片地址',
  `serviceInfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '服务说明',
  `productInfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产品简介',
  `photoType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `photoSize` int(2) NULL DEFAULT NULL COMMENT '图片尺寸，方便渲染 0:竖 1:横',
  `minPrice` decimal(10, 2) NULL DEFAULT NULL COMMENT '最低价格',
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_photo
-- ----------------------------
INSERT INTO `t_photo` VALUES (1, '证件/签证照', '/images/cover/1.png', '含化妆、服装、拍摄、精修', '-用于官方证件形象的标准照\r\n-用途包含且不限于护照/签证/报名/面试/求职\r\n-专业造型，商业布光，精致后期，三管齐下\r\n-每一张照片都精雕细琢\r\n-适用：4岁以上人类\r\n', '证件照', 0, 38.00);
INSERT INTO `t_photo` VALUES (2, '职业形象', '/images/cover/2.jpg', '含化妆、服装、拍摄、精修', '-用于职场第一面的专业展示\r\n-用于覆盖普通求职、个人形象展示、模卡、员工资料存储等\r\n-更自然真实的个人形象展示\r\n-生动诠释更加自信的第一面\r\n-适用：渴望从事梦想工作的职场新鲜人\r\n', '职业照', 0, 68.00);
INSERT INTO `t_photo` VALUES (3, '结婚照', '/images/cover/3.jpg', '含化妆、服装、拍摄、精修', '-结婚领证小红本上最有仪式感的红底情侣合照\r\n-从情侣变为夫妻最具仪式感的影像时刻\r\n-适用:决定携手此生的情侣\r\n', '结婚照', 1, 128.00);
INSERT INTO `t_photo` VALUES (4, '毕业照', '/images/cover/4.jpg', '含化妆、服装、拍摄、精修', '-结婚领证小红本上最有仪式感的红底情侣合照\r\n-从情侣变为夫妻最具仪式感的影像时刻\r\n-适用:决定携手此生的情侣\r\n', '毕业照', 1, 228.00);
INSERT INTO `t_photo` VALUES (5, '个人写真', '/images/cover/5.jpg', '含化妆、服装、拍摄、精修', '-无论是自己还是带上拥有重要回忆的伙伴\r\n-更自在真切的情感流露定格\r\n-轻搭配、轻配色，更真实自然的个人写真\r\n-当下的就是最好的\r\n-适用：任何人群\r\n', '个人照', 1, 338.00);
INSERT INTO `t_photo` VALUES (6, '全家福', '/images/cover/6.jpg', '含化妆、服装、拍摄、精修', '-让此刻停留定格的全家福合影纪念照，记录亲人相处的仪式感\r\n-钱夹里、办公桌上、床头柜上的一张远程陪伴\r\n-适用：一家人\r\n', '全家照', 1, 368.00);
INSERT INTO `t_photo` VALUES (7, '团队照', '/images/cover/7.jpg', '含化妆、服装、拍摄、精修', '-人才、团队是最有市场竞争力的财富\r\n-展示个人形象和富有朝气活力的团队形象\r\n-适用：公司团队照\r\n', '团队照', 1, 688.00);

-- ----------------------------
-- Table structure for t_servicepar
-- ----------------------------
DROP TABLE IF EXISTS `t_servicepar`;
CREATE TABLE `t_servicepar`  (
  `sId` int(11) NOT NULL AUTO_INCREMENT COMMENT '服务详情',
  `pId` int(11) NOT NULL COMMENT '套餐id',
  `modelling` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '造型',
  `shoot` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '拍摄',
  `anaphase` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '后期',
  `plot` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出图',
  `afterSale` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据管理与售后',
  `explain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (`sId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_servicepar
-- ----------------------------
INSERT INTO `t_servicepar` VALUES (1, 1, '专业级化妆用品基本款服饰可选精致面妆+发型', '专业85mm人像镜头拍摄专业定制灯光超高清格式保留全部色彩细节', '精修一张广告级高清照片成片及剪裁尺寸超高清电子版拷贝', '高光泽防水不褪色相纸立等可取数据管理与售后丢失可电邮取回加印快递服务', '成片云端后台储存，会保存很久很久丢失可电邮取回加印快递服务', '标准服务全程耗费预计约1小时，因个人情况不同会提前完成或延时；因开放的成人环境会对婴儿的健康造成影响，本店暂不接受太小的儿童拍摄；推荐自带中意，符合个人气质形象的服装；');
INSERT INTO `t_servicepar` VALUES (2, 2, '专业级化妆用品\r\n基本款服饰可选\r\n精致面妆+发型\r\n', '专业85mm人像镜头拍摄\r\n专业定制灯光\r\n超高清格式保留全部色彩细节\r\n', '精修一张广告级高清照片\r\n成片及剪裁尺寸超高清电子版拷贝\r\n', '高光泽防水不褪色相纸\r\n立等可取\r\n数据管理与售后\r\n丢失可电邮取回\r\n加印快递服务\r\n', '成片云端后台储存，会保存很久很久\r\n丢失可电邮取回\r\n加印快递服务\r\n', '标准服务全程耗费预计约1小时，因个人情况不同会提前完成或延时；\r\n因开放的成人环境会对婴儿的健康造成影响，本店暂不接受太小的儿童拍摄；\r\n推荐自带中意，符合个人气质形象的服装；\r\n');
INSERT INTO `t_servicepar` VALUES (3, 3, '专业级化妆用品\r\n基本款服饰可选\r\n精致面妆+发型\r\n', '专业85mm人像镜头拍摄\r\n专业定制灯光\r\n超高清格式保留全部色彩细节\r\n', '精修一张广告级高清照片\r\n成片及剪裁尺寸超高清电子版拷贝\r\n', '高光泽防水不褪色相纸\r\n立等可取\r\n数据管理与售后\r\n丢失可电邮取回\r\n加印快递服务\r\n', '成片云端后台储存，会保存很久很久\r\n丢失可电邮取回\r\n加印快递服务\r\n', '标准服务全程耗费预计约1小时，因个人情况不同会提前完成或延时；\r\n因开放的成人环境会对婴儿的健康造成影响，本店暂不接受太小的儿童拍摄；\r\n推荐自带中意，符合个人气质形象的服装；\r\n');
INSERT INTO `t_servicepar` VALUES (4, 4, '专业级化妆用品\r\n基本款服饰可选\r\n精致面妆+发型\r\n', '专业85mm人像镜头拍摄\r\n专业定制灯光\r\n超高清格式保留全部色彩细节\r\n', '精修一张广告级高清照片\r\n成片及剪裁尺寸超高清电子版拷贝\r\n', '高光泽防水不褪色相纸\r\n立等可取\r\n数据管理与售后\r\n丢失可电邮取回\r\n加印快递服务\r\n', '成片云端后台储存，会保存很久很久\r\n丢失可电邮取回\r\n加印快递服务\r\n', '标准服务全程耗费预计约1小时，因个人情况不同会提前完成或延时；\r\n因开放的成人环境会对婴儿的健康造成影响，本店暂不接受太小的儿童拍摄；\r\n推荐自带中意，符合个人气质形象的服装；\r\n');
INSERT INTO `t_servicepar` VALUES (5, 5, '专业级化妆用品\r\n基本款服饰可选\r\n精致面妆+发型\r\n', '专业85mm人像镜头拍摄\r\n专业定制灯光\r\n超高清格式保留全部色彩细节\r\n', '精修一张广告级高清照片\r\n成片及剪裁尺寸超高清电子版拷贝\r\n', '服务详情plot高光泽防水不褪色相纸\r\n立等可取\r\n数据管理与售后\r\n丢失可电邮取回\r\n加印快递服务\r\n', '成片云端后台储存，会保存很久很久\r\n丢失可电邮取回\r\n加印快递服务\r\n', '标准服务全程耗费预计约1小时，因个人情况不同会提前完成或延时；\r\n因开放的成人环境会对婴儿的健康造成影响，本店暂不接受太小的儿童拍摄；\r\n推荐自带中意，符合个人气质形象的服装；\r\n');
INSERT INTO `t_servicepar` VALUES (6, 6, '专业级化妆用品\r\n基本款服饰可选\r\n精致面妆+发型\r\n', '专业85mm人像镜头拍摄\r\n专业定制灯光\r\n超高清格式保留全部色彩细节\r\n', '精修一张广告级高清照片\r\n成片及剪裁尺寸超高清电子版拷贝\r\n', '高光泽防水不褪色相纸\r\n立等可取\r\n数据管理与售后\r\n丢失可电邮取回\r\n加印快递服务\r\n', '成片云端后台储存，会保存很久很久\r\n丢失可电邮取回\r\n加印快递服务\r\n', '标准服务全程耗费预计约1小时，因个人情况不同会提前完成或延时；\r\n因开放的成人环境会对婴儿的健康造成影响，本店暂不接受太小的儿童拍摄；\r\n推荐自带中意，符合个人气质形象的服装；\r\n');
INSERT INTO `t_servicepar` VALUES (7, 7, '专业级化妆用品\r\n基本款服饰可选\r\n精致面妆+发型\r\n', '专业85mm人像镜头拍摄\r\n专业定制灯光\r\n超高清格式保留全部色彩细节\r\n', '精修一张广告级高清照片\r\n成片及剪裁尺寸超高清电子版拷贝\r\n', '服务详情plot高光泽防水不褪色相纸\r\n立等可取\r\n数据管理与售后\r\n丢失可电邮取回\r\n加印快递服务\r\n', '成片云端后台储存，会保存很久很久\r\n丢失可电邮取回\r\n加印快递服务\r\n', '标准服务全程耗费预计约1小时，因个人情况不同会提前完成或延时；\r\n因开放的成人环境会对婴儿的健康造成影响，本店暂不接受太小的儿童拍摄；\r\n推荐自带中意，符合个人气质形象的服装；\r\n');

-- ----------------------------
-- Table structure for t_show
-- ----------------------------
DROP TABLE IF EXISTS `t_show`;
CREATE TABLE `t_show`  (
  `sId` int(11) NOT NULL AUTO_INCREMENT COMMENT '展示图片id',
  `pId` int(11) NOT NULL COMMENT '套餐id',
  `imgUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  PRIMARY KEY (`sId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_show
-- ----------------------------
INSERT INTO `t_show` VALUES (15, 4, '/images/lists/4/1.jpg');
INSERT INTO `t_show` VALUES (16, 4, '/images/lists/4/2.jpg');
INSERT INTO `t_show` VALUES (17, 4, '/images/lists/4/3.jpg');
INSERT INTO `t_show` VALUES (18, 5, '/images/lists/5/1.jpg');
INSERT INTO `t_show` VALUES (19, 5, '/images/lists/5/2.jpg');
INSERT INTO `t_show` VALUES (20, 5, '/images/lists/5/3.jpg');
INSERT INTO `t_show` VALUES (21, 5, '/images/lists/5/4.jpg');
INSERT INTO `t_show` VALUES (22, 6, '/images/lists/6/1.jpg');
INSERT INTO `t_show` VALUES (23, 6, '/images/lists/6/2.jpg');
INSERT INTO `t_show` VALUES (24, 7, '/images/lists/7/1.jpg');
INSERT INTO `t_show` VALUES (25, 7, '/images/lists/7/2.jpg');
INSERT INTO `t_show` VALUES (31, 1, '/images/lists/1/9e16ea68ec114221a9b725c6935f2c90.jpg');
INSERT INTO `t_show` VALUES (32, 1, '/images/lists/1/0f716e47bf2f47a79dde15b4d248548b.jpg');
INSERT INTO `t_show` VALUES (33, 1, '/images/lists/1/98cb92f0849048fda81f52ce09e15497.jpg');
INSERT INTO `t_show` VALUES (37, 2, '/images/lists/2/9c8df72f6dab485abd98f528de912a36.jpg');
INSERT INTO `t_show` VALUES (38, 2, '/images/lists/2/8e9508ebfa4f42a5981799c8500355c1.jpg');
INSERT INTO `t_show` VALUES (43, 3, '/images/lists/3/c8887d3cd12a420aa6ede5864c9af62d.jpg');
INSERT INTO `t_show` VALUES (44, 3, '/images/lists/3/112047a1438f4963966e98ac278b94fe.jpg');
INSERT INTO `t_show` VALUES (45, 3, '/images/lists/3/ac3f2414f0534d229a629a19097f6db1.jpg');
INSERT INTO `t_show` VALUES (46, 3, '/images/lists/3/1e2e5a686416466a8b1dbac3e7b87c04.jpg');

-- ----------------------------
-- Table structure for t_specification
-- ----------------------------
DROP TABLE IF EXISTS `t_specification`;
CREATE TABLE `t_specification`  (
  `speId` int(11) NOT NULL AUTO_INCREMENT COMMENT '摄影规格Id',
  `pId` int(11) NOT NULL COMMENT '套餐id',
  `speName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '规格名称',
  `price` decimal(10, 2) NOT NULL COMMENT '价格，保留两位小数',
  PRIMARY KEY (`speId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_specification
-- ----------------------------
INSERT INTO `t_specification` VALUES (1, 1, '单色背景', 38.00);
INSERT INTO `t_specification` VALUES (2, 1, '双色背景', 89.00);
INSERT INTO `t_specification` VALUES (4, 2, '形象照', 108.00);
INSERT INTO `t_specification` VALUES (5, 2, '不同姿势*3张', 288.00);
INSERT INTO `t_specification` VALUES (6, 2, '不同姿势*9张', 520.00);
INSERT INTO `t_specification` VALUES (7, 3, '结婚证件照*1张', 115.00);
INSERT INTO `t_specification` VALUES (8, 3, '搞怪结婚照*5张', 388.00);
INSERT INTO `t_specification` VALUES (9, 3, '搞怪结婚照*8张', 520.00);
INSERT INTO `t_specification` VALUES (10, 4, '文艺毕业照*1张', 108.00);
INSERT INTO `t_specification` VALUES (11, 4, '文艺毕业照*3张', 288.00);
INSERT INTO `t_specification` VALUES (12, 4, '文艺毕业照*9张', 480.00);
INSERT INTO `t_specification` VALUES (13, 5, '单人写真*1张', 118.00);
INSERT INTO `t_specification` VALUES (14, 5, '不同姿势*3张', 260.00);
INSERT INTO `t_specification` VALUES (15, 5, '不同姿势*9张', 480.00);
INSERT INTO `t_specification` VALUES (16, 6, '全家福（1-3人）*1', 220.00);
INSERT INTO `t_specification` VALUES (17, 6, '全家福（4-6人）*1', 320.00);
INSERT INTO `t_specification` VALUES (18, 6, '全家福（7-9人）*1', 420.00);
INSERT INTO `t_specification` VALUES (19, 7, '团队照（1-5）*1', 299.00);
INSERT INTO `t_specification` VALUES (20, 7, '团队照（6-10）*1', 399.00);
INSERT INTO `t_specification` VALUES (21, 7, '团队照（11-15）*1', 499.00);
INSERT INTO `t_specification` VALUES (22, 7, '团队照（15人以上）*1', 720.00);
INSERT INTO `t_specification` VALUES (23, 1, '三色背景', 119.00);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `uId` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `openId` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '微信openId',
  `userName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户姓名，默认为微信昵称',
  `sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `birthday` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生日',
  PRIMARY KEY (`uId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (2, 'opMzX5bltao560kWx467UgCguoqQ', 'Sinder丶', '男', '-1');

SET FOREIGN_KEY_CHECKS = 1;
