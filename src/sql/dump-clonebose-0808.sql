/*M!999999\- enable the sandbox mode */ 
-- MariaDB dump 10.19-11.4.5-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: clonebose
-- ------------------------------------------------------
-- Server version	11.4.5-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*M!100616 SET @OLD_NOTE_VERBOSITY=@@NOTE_VERBOSITY, NOTE_VERBOSITY=0 */;

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `admins` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '관리자 일련번호',
  `admin_email` varchar(320) NOT NULL COMMENT '관리자 로그인 ID',
  `admin_password` varchar(255) NOT NULL COMMENT '관리자 로그인 비밀번호 (암호화)',
  `admin_name` varchar(50) NOT NULL COMMENT '관리자 이름',
  `admin_gender` enum('남','여') NOT NULL COMMENT '관리자 성별',
  `admin_phone` varchar(20) NOT NULL COMMENT '관리자 연락처',
  `admin_birthdate` date NOT NULL COMMENT '관리자 생년월일',
  `is_out` tinyint(1) NOT NULL DEFAULT 0 COMMENT '탈퇴여부(Y/N)',
  `reg_date` datetime NOT NULL COMMENT '가입한 날짜 시간 정보',
  `edit_date` datetime NOT NULL COMMENT '가입 정보를 수정한 날짜 시간 정보',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `cart_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '장바구니 정보 일련번호',
  `user_id` int(11) NOT NULL COMMENT '회원 정보',
  `product_id` int(11) NOT NULL COMMENT '상품 정보',
  `color_id` int(11) NOT NULL COMMENT '색상 정보',
  `product_quantity` int(11) NOT NULL COMMENT '상품 수량',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '삭제여부(Y/N)',
  `reg_date` datetime NOT NULL COMMENT '장바구니에 추가한 날짜 시간 정보',
  `edit_date` varchar(100) NOT NULL COMMENT '장바구니 정보를 수정한 날짜 시간 정보',
  PRIMARY KEY (`cart_id`),
  KEY `carts_users_FK` (`user_id`),
  KEY `carts_products_FK` (`product_id`),
  KEY `carts_colors_FK` (`color_id`),
  CONSTRAINT `carts_colors_FK` FOREIGN KEY (`color_id`) REFERENCES `colors` (`color_id`),
  CONSTRAINT `carts_products_FK` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `carts_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '카테고리 일련번호',
  `category_name` varchar(100) NOT NULL COMMENT '상위 카테고리명',
  `title_img` varchar(255) NOT NULL COMMENT '상품 카테고리별 메인 이미지',
  `reg_date` datetime NOT NULL COMMENT '하위  카테고리 정보를 등록한 날짜 시간 정보',
  `edit_date` varchar(100) NOT NULL COMMENT '하위  카테고리 정보를 수정한 날짜 시간 정보',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '삭제여부(Y/N)',
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES
(1,'헤드폰','/products_images/headphones/headphones_main_img.png','2025-01-01 10:00:00','2025-06-15 10:00:00',0),
(2,'이어버드','/products_images/earbuds/earbuds_main_img.png','2025-01-01 10:00:00','2025-07-02 17:31:00',0),
(3,'스피커','/products_images/speaker/speaker_main_img.png','2025-01-01 10:00:00','2025-07-02 17:31:00',0),
(4,'사운드바','/products_images/soundbar/soundbar_main_img.png','2025-01-01 10:00:00','2025-07-02 17:31:00',0),
(5,'프로페셔널','/products_images/professional/professional_main_img.jpg','2025-01-01 10:00:00','2025-07-02 17:31:00',0);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `colors`
--

DROP TABLE IF EXISTS `colors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `colors` (
  `color_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '색상 일련번호',
  `color_name` varchar(50) NOT NULL COMMENT '색상명',
  `color_code` varchar(10) NOT NULL COMMENT '색상 코드',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '삭제여부(Y/N)',
  `reg_date` datetime NOT NULL COMMENT '색상 정보를 등록한 날짜 시간 정보',
  `edit_date` datetime NOT NULL COMMENT '색상 정보를 수정한 날짜 시간 정보',
  PRIMARY KEY (`color_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `colors`
--

LOCK TABLES `colors` WRITE;
/*!40000 ALTER TABLE `colors` DISABLE KEYS */;
INSERT INTO `colors` VALUES
(1,'화이트','#FFFFFF',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(2,'화이트 스모크','#FFFFFF',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(3,'소프스톤','#FFFFFF',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(4,'블랙','#000000',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(5,'트리플 블랙','#000000',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(6,'블루더스크','#436581',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(7,'칠드 라일락','#E6D2FE',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(8,'사이프러스 그린','#104018',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(9,'문스톤 블루','#AFE2F4',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(10,'샌드스톤','#C1BEB4',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(11,'트와일라잇 블루','#0F1D6A',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(12,'딥플럼','#AD0043',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(13,'루나 블루','#213360',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(14,'미드나잇 블루','#0A1054',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(15,'이클립스 그레이','#3f3939',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(16,'페탈 핑크','#F5B4EB',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(17,'선셋 이리디센트','#E8AF8C',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(18,'럭스실버','#F2F2F2',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(19,'스톤블루','#7CB1BC',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(20,'알파인세이지','#BFD9C2',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(21,'샌드스톤','#C1BEB4',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(22,'아크틱화이트','#FFFFFF',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(23,'블루&카본블루','#6981B4',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(24,'블랙&칠드라일락','#795EA3',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(25,'블루더스크&애프리콧','#F26464',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(26,'블루더스크&하이퍼시트론','#CCD885',0,'2025-06-15 10:00:00','2025-06-30 12:00:00'),
(27,'시트러스옐로우','#FFFBA0',0,'2025-06-15 10:00:00','2025-06-15 10:00:00'),
(28,'다이아몬드 60주년 에디션','#FFFFFF',0,'2025-06-15 10:00:00','2025-07-09 10:00:00');
/*!40000 ALTER TABLE `colors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notices`
--

DROP TABLE IF EXISTS `notices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `notices` (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '공지 일련번호',
  `admin_id` int(11) NOT NULL COMMENT '작성한 관리자 정보',
  `notice_title` varchar(200) NOT NULL COMMENT '공지 제목',
  `notice_content` text NOT NULL COMMENT '공지 내용',
  `notice_img` varchar(255) DEFAULT NULL COMMENT '공지 이미지',
  `is_out` tinyint(1) NOT NULL DEFAULT 0 COMMENT '탈퇴여부(Y/N)',
  `reg_date` datetime NOT NULL COMMENT '가입한 날짜 시간 정보',
  `edit_date` datetime NOT NULL COMMENT '가입 정보를 수정한 날짜 시간 정보',
  PRIMARY KEY (`notice_id`),
  KEY `notices_admins_FK` (`admin_id`),
  CONSTRAINT `notices_admins_FK` FOREIGN KEY (`admin_id`) REFERENCES `admins` (`admin_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notices`
--

LOCK TABLES `notices` WRITE;
/*!40000 ALTER TABLE `notices` DISABLE KEYS */;
/*!40000 ALTER TABLE `notices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '주문 정보 일련번호',
  `user_id` int(11) DEFAULT NULL COMMENT '회원 정보',
  `order_number` varchar(50) NOT NULL COMMENT '주문 번호',
  `receiver_name` varchar(50) DEFAULT NULL COMMENT '배송자 이름',
  `receiver_phone` varchar(20) DEFAULT NULL COMMENT '배송자 전화번호',
  `receiver_postcode` varchar(10) DEFAULT NULL COMMENT '배송자 우편번호',
  `receiver_address` varchar(255) DEFAULT NULL COMMENT '배송자 주소',
  `receiver_specific_address` varchar(255) DEFAULT NULL COMMENT '수취인 상세주소',
  `receiver_memo` varchar(255) DEFAULT NULL COMMENT '배송자 메모',
  `order_status` enum('배송중','배송준비중','결제대기중','배송완료','취소완료') NOT NULL COMMENT '주문 상태',
  `order_price` int(11) DEFAULT NULL COMMENT '주문 금액',
  `delivery_method` varchar(20) DEFAULT NULL COMMENT '배송 방법',
  `delivery_price` int(11) DEFAULT NULL COMMENT '배송 가격',
  `purchase_term` tinyint(1) DEFAULT 0 COMMENT '구매 약관 동의 여부',
  `purchase_method` enum('신용카드','가상계좌','실시간계좌이체','토스페이') DEFAULT NULL COMMENT '결제 수단',
  `reg_date` datetime NOT NULL COMMENT '주문 정보를 등록한 날짜 시간 정보',
  `edit_date` datetime NOT NULL COMMENT '주문 정보를 수정한 날짜 시간 정보',
  PRIMARY KEY (`order_id`),
  KEY `orders_users_FK` (`user_id`),
  CONSTRAINT `orders_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_products`
--

DROP TABLE IF EXISTS `orders_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders_products` (
  `order_product_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '주문 상품 정보 일련번호',
  `order_id` int(11) NOT NULL COMMENT '주문 정보',
  `sub_category_id` int(11) NOT NULL COMMENT '하위 카테고리 일련번호',
  `color_id` int(11) NOT NULL COMMENT '주문 상품 색상 정보',
  `order_product_name` varchar(200) NOT NULL COMMENT '주문 상품명',
  `order_quantity` int(11) NOT NULL COMMENT '주문 수량',
  `order_product_price` int(11) NOT NULL COMMENT '주문 상품 금액',
  `order_product_img` varchar(255) NOT NULL COMMENT '주문 상품 이미지',
  `order_product_origin` varchar(50) NOT NULL COMMENT '상품 원산지',
  `reg_date` datetime NOT NULL COMMENT '주문 상품 정보를 등록한 날짜 시간 정보',
  `edit_date` datetime NOT NULL COMMENT '주문 상품 정보를 수정한 날짜 시간 정보',
  PRIMARY KEY (`order_product_id`),
  KEY `orders_products_orders_FK` (`order_id`),
  KEY `orders_products_colors_FK` (`color_id`),
  KEY `orders_products_sub_categories_FK` (`sub_category_id`),
  CONSTRAINT `orders_products_colors_FK` FOREIGN KEY (`color_id`) REFERENCES `colors` (`color_id`),
  CONSTRAINT `orders_products_orders_FK` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `orders_products_sub_categories_FK` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_categories` (`sub_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_products`
--

LOCK TABLES `orders_products` WRITE;
/*!40000 ALTER TABLE `orders_products` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '상품 일련번호',
  `sub_category_id` int(11) NOT NULL COMMENT '하위 카테고리 일련번호',
  `product_name` varchar(200) NOT NULL COMMENT '상품명',
  `product_price` int(11) NOT NULL COMMENT '상품 금액',
  `product_img` varchar(255) NOT NULL COMMENT '상품 이미지',
  `product_description_img` varchar(255) NOT NULL COMMENT '상품 설명 이미지',
  `product_origin` varchar(50) NOT NULL COMMENT '상품 원산지',
  `product_stock` int(11) NOT NULL COMMENT '상품 재고',
  `reg_date` datetime NOT NULL COMMENT '상품 정보를 등록한 날짜 시간 정보',
  `edit_date` datetime NOT NULL COMMENT '상품 정보를 수정한 날짜 시간 정보',
  `is_best` tinyint(1) NOT NULL DEFAULT 0 COMMENT 'Best 상품 여부',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '삭제여부(Y/N)',
  PRIMARY KEY (`product_id`),
  KEY `products_sub_categories_FK` (`sub_category_id`),
  CONSTRAINT `products_sub_categories_FK` FOREIGN KEY (`sub_category_id`) REFERENCES `sub_categories` (`sub_category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=343435 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES
(1,101,'[BOSE] 보스 QC 헤드폰',349000,'/products_images/headphones/headphones/qc_headphones/qc_headphones_tripleblack.jpg','/detail_products_images/headphones/headphones/qc_headphones/detail_qc_headphones.png','태국',300,'2025-01-29 10:00:00','2025-06-30 10:00:00',1,0),
(2,101,'[BOSE] 보스 QC 울트라 헤드폰',479000,'/products_images/headphones/headphones/qc_ultra_headphones/qc_ultra_headphones_black.jpg','/detail_products_images/headphones/headphones/qc_ultra_headphones/detail_qc_ultra_headphones.png','태국,중국',240,'2025-01-30 10:00:00','2025-06-30 10:00:00',1,0),
(3,102,'[BOSE] 보스 QC 헤드폰 이어쿠션 키트',39000,'/products_images/headphones/headphones_acc/qc_headphones_acc/qc_headphones_acc_tripleblack.jpg','/detail_products_images/headphones/headphones_acc/qc_headphones_acc/detail_qc_headphones_acc.jpg','중국',300,'2025-01-27 10:00:00','2025-06-30 10:00:00',0,0),
(4,102,'[BOSE] 보스 QC 울트라 헤드폰 이어쿠션 키트',39000,'/products_images/headphones/headphones_acc/qc_ultra_headphones_acc/qc_ultra_headphones_acc_black.jpg','/detail_products_images/headphones/headphones_acc/qc_ultra_headphones_acc/detail_qc_ultra_headphones_acc.jpg','중국',240,'2025-01-28 10:00:00','2025-06-30 10:00:00',0,0),
(5,201,'[BOSE] 보스 울트라 오픈 이어버드',359000,'/products_images/earbuds/earbuds/ultra_open_earbuds/ultra_open_earbuds_black.jpg','/detail_products_images/earbuds/earbuds/ultra_open_earbuds/detail_ultra_open_earbuds.png','중국',1100,'2025-06-01 10:00:00','2025-06-30 10:00:00',1,0),
(6,201,'[BOSE] 보스 QC 이어버드',199000,'/products_images/earbuds/earbuds/qc_earbuds/qc_earbuds_black.jpg','/detail_products_images/earbuds/earbuds/qc_earbuds/detail_qc_earbuds_1.png','중국,베트남',700,'2025-07-02 10:00:00','2025-06-30 10:00:00',0,0),
(7,201,'[BOSE] [한정수량] 보스 울트라 오픈 이어버드 - 다이아몬드 60주년 에디션',359000,'/products_images/earbuds/earbuds/ultra_open_earbuds_60th_ed/ultra_open_earbuds_60th_ed_diamond.jpg','/detail_products_images/earbuds/earbuds/ultra_open_earbuds_60th_ed/detail_ultra_open_earbuds_60th_ed.png','한국',777,'2025-04-01 10:00:00','2025-07-03 17:07:00',0,0),
(8,201,'[BOSE] 보스 QC 울트라 이어버드',359000,'/products_images/earbuds/earbuds/qc_ultra_earbuds/qc_ultra_earbuds_black.jpg','/detail_products_images/earbuds/earbuds/qc_ultra_earbuds/detail_qc_ultra_earbuds.png','중국',200,'2025-05-01 10:00:00','2025-06-30 10:00:00',1,0),
(9,202,'[BOSE] 보스 울트라 오픈 이어버드 실리콘 커버 케이스',19000,'/products_images/earbuds/earbuds_acc/ultra_open_earbuds_silicone_cover_case/ultra_open_earbuds_silicone_cover_case_tripleblack.jpg','/detail_products_images/earbuds/earbuds_acc/ultra_open_earbuds_silicone_cover_case/detail_ultra_open_earbuds_silicone_cover_case.jpg','중국',100,'2025-03-01 10:00:00','2025-06-30 10:00:00',0,0),
(10,202,'[BOSE] 보스 QC 이어버드 실리콘 커버 케이스',29000,'/products_images/earbuds/earbuds_acc/qc_earbuds_silicone_cover_case/qc_earbuds_silicone_cover_case_tripleblack.jpg','/detail_products_images/earbuds/earbuds_acc/qc_earbuds_silicone_cover_case/detail_qc_earbuds_silicone_cover_case.jpg','중국',8000,'2025-02-01 10:00:00','2025-06-30 10:00:00',0,0),
(11,202,'[BOSE] 보스 무선 충전 케이스 커버',59000,'/products_images/earbuds/earbuds_acc/wireless_charger_case_cover/wireless_charger_case_cover_black.jpg','/detail_products_images/earbuds/earbuds_acc/wireless_charger_case_cover/detail_wireless_charger_case_cover.jpg','베트남',9000,'2025-01-01 10:00:00','2025-06-30 10:00:00',0,0),
(12,301,'[BOSE] 보스 사운드링크 플렉스 스피커(2세대)',179000,'/products_images/speaker/bluetooth_speaker/sound_link_plex_speaker/sound_link_plex_speaker_black.jpg','/detail_products_images/speaker/bluetooth_speaker/sound_link_plex_speaker/detail_sound_link_plex_speaker.png','말레이시아',700,'2025-09-01 10:00:00','2025-06-30 10:00:00',1,0),
(13,301,'[BOSE] 보스 사운드링크 맥스 포터블 스피커',519000,'/products_images/speaker/bluetooth_speaker/sound_link_max_portable_speaker/sound_link_max_portable_speaker_black.jpg','/detail_products_images/speaker/bluetooth_speaker/sound_link_max_portable_speaker/detail_sound_link_max_portable_speaker.png','말레이시아',630,'2025-08-01 10:00:00','2025-06-30 10:00:00',1,0),
(14,301,'[BOSE] 보스 사운드링크 마이크로 스피커',139000,'/products_images/speaker/bluetooth_speaker/sound_link_micro_speaker/sound_link_micro_speaker_stoneblue.jpg','/detail_products_images/speaker/bluetooth_speaker/sound_link_micro_speaker/detail_sound_link_micro_speaker.png','말레이시아',800,'2025-07-01 10:00:00','2025-06-30 10:00:00',0,0),
(15,302,'[BOSE] 보스 포터블 홈 스피커',519000,'/products_images/speaker/bluetooth_speaker/portable_home_speaker/portable_home_speaker_luxsilver.jpg','/detail_products_images/speaker/bluetooth_speaker/portable_home_speaker/detail_portable_home_speaker.jpg','중국',3831,'2025-06-01 10:00:00','2025-06-30 10:00:00',0,0),
(16,301,'[BOSE] 보스 사운드링크 리볼브 플러스 2 스피커',399000,'/products_images/speaker/bluetooth_speaker/sound_link_revolve_plus_speaker/sound_link_revolve_plus_speaker_tripleblack.jpg','/detail_products_images/speaker/bluetooth_speaker/sound_link_revolve_plus_speaker/detail_sound_link_revolve_plus_speaker.png','말레이시아',700,'2025-05-01 10:00:00','2025-06-30 10:00:00',0,0),
(17,303,'[BOSE] 보스 포터블 홈 스피커 충전 크래들',38500,'/products_images/speaker/speaker_acc/portable_home_speaker_charging_cradle/portable_home_speaker_charging_cradle_black.jpg','/detail_products_images/speaker/speaker_acc/portable_home_speaker_charging_cradle/detail_portable_home_speaker_charging_cradle.jpg','중국',631,'2025-02-01 10:00:00','2025-06-30 10:00:00',0,0),
(18,303,'[BOSE] 보스 사운드링크 맥스 로프 휴대용 스트랩',63000,'/products_images/speaker/speaker_acc/sound_link_max_rope_carrying_strap/sound_link_max_rope_carrying_strap_black.jpg','/detail_products_images/speaker/speaker_acc/sound_link_max_rope_carrying_strap/detail_sound_link_max_rope_carrying_strap.jpg','중국',1370,'2025-03-01 10:00:00','2025-06-30 10:00:00',0,0),
(19,303,'[BOSE] 보스 사운드링크 리볼브 충전 크래들',39000,'/products_images/speaker/speaker_acc/sound_link_revolve_charging_cradle/sound_link_revolve_charging_cradle_black.jpg','/detail_products_images/speaker/speaker_acc/sound_link_revolve_charging_cradle/detail_sound_link_revolve_charging_cradle.jpg','중국',371,'2025-01-01 10:00:00','2025-06-30 10:00:00',0,0),
(20,303,'[BOSE] 보스 사운드링크 맥스 로프 핸들',35000,'/products_images/speaker/speaker_acc/sound_link_max_rope_handle/sound_link_max_rope_handle_purple.jpg','/detail_products_images/speaker/speaker_acc/sound_link_max_rope_handle/detail_sound_link_max_rope_handle.jpg','중국',398,'2025-04-01 10:00:00','2025-06-30 10:00:00',0,0),
(21,401,'[BOSE] 보스 스마트 울트라 사운드바',1190000,'/products_images/soundbar/soundbar/smart_ultra_soundbar/smart_ultra_soundbar_arcticwhite.jpg','/detail_products_images/soundbar/soundbar/smart_ultra_soundbar/detail_smart_ultra_soundbar.png','말레이시아',313,'2025-04-28 10:00:00','2025-06-30 10:00:00',0,0),
(22,401,'[BOSE] 보스 TV 스피커 사운드바',329000,'/products_images/soundbar/soundbar/tv_speaker/tv_speaker_black.jpg','/detail_products_images/soundbar/soundbar/tv_speaker/detail_tv_speaker.jpg','말레이시아',3333,'2025-04-30 10:00:00','2025-06-30 10:00:00',1,0),
(23,401,'[BOSE] 보스 스마트 사운드바',699000,'/products_images/soundbar/soundbar/smart_soundbar/smart_soundbar_black.jpg','/detail_products_images/soundbar/soundbar/smart_soundbar/detail_smart_soundbar.png','말레이시아',4611,'2025-04-29 10:00:00','2025-06-30 10:00:00',0,0),
(24,401,'[BOSE] 보스 베이스 모듈 500',549000,'/products_images/soundbar/soundbar/base_module_500/base_module_500_black.jpg','/detail_products_images/soundbar/soundbar/base_module_500/detail_base_module_500.jpg','말레이시아',4514,'2025-04-26 10:00:00','2025-06-30 10:00:00',0,0),
(25,401,'[BOSE] 보스 서라운드 스피커 700',679000,'/products_images/soundbar/soundbar/surround_speaker_700/surround_speaker_700_black.jpg','/detail_products_images/soundbar/soundbar/surround_speaker_700/detail_surround_speaker_700.jpg','말레이시아',1000,'2025-04-25 10:00:00','2025-06-30 10:00:00',0,0),
(26,401,'[BOSE] 보스 베이스 모듈 700',839000,'/products_images/soundbar/soundbar/base_module_700/base_module_700_black.jpg','/detail_products_images/soundbar/soundbar/base_module_700/detail_base_module_700.jpg','중국',600,'2025-04-27 10:00:00','2025-06-30 10:00:00',0,0),
(27,401,'[BOSE] 보스 서라운드 스피커',390000,'/products_images/soundbar/soundbar/surround_speaker/surround_speaker_black.jpg','/detail_products_images/soundbar/soundbar/surround_speaker/detail_surround_speaker.jpg','중국',2221,'2025-04-24 10:00:00','2025-06-30 10:00:00',0,0),
(28,403,'[BOSE] 보스 사운드바 윌브라켓',55000,'/products_images/soundbar/soundbar_acc/soundbar_wall_bracket/soundbar_wall_bracket_black.jpg','/detail_products_images/soundbar/soundbar_acc/soundbar_wall_bracket/detail_soundbar_wall_bracket.jpg','중국',1135,'2025-04-23 10:00:00','2025-06-30 10:00:00',0,0),
(29,403,'[BOSE] 보스 서라운드 플로어 스탠드 UFS-20 ||',143000,'/products_images/soundbar/soundbar_acc/surround_floor_stand_ufs20/surround_floor_stand_ufs20_black.jpg','/detail_products_images/soundbar/soundbar_acc/surround_floor_stand_ufs20/detail_surround_floor_stand_ufs20.jpg','중국',3590,'2025-04-20 10:00:00','2025-06-30 10:00:00',0,0),
(30,403,'[BOSE] 보스 서라운드 실링(천장) 브라켓 UB-20',44000,'/products_images/soundbar/soundbar_acc/surround_ceiling_bracket_ub20/surround_ceiling_bracket_ub20_black.jpg','/detail_products_images/soundbar/soundbar_acc/surround_ceiling_bracket_ub20/detail_surround_ceiling_bracket_ub20.jpg','중국',3333,'2025-04-22 10:00:00','2025-06-30 10:00:00',0,0),
(31,403,'[BOSE] 보스 서라운드 테이블 스탠드 UTS-20 ll',44000,'/products_images/soundbar/soundbar_acc/surround_table_stand_uts20/surround_table_stand_uts20_black.jpg','/detail_products_images/soundbar/soundbar_acc/surround_table_stand_uts20/detail_surround_table_stand_uts20.jpg','중국',3333,'2025-04-21 10:00:00','2025-06-30 10:00:00',0,0),
(32,403,'[BOSE] 보스 옴니 주얼 월 브라켓 (서라운드 스피커 700 전용)',66000,'/products_images/soundbar/soundbar_acc/omni_jewel_wall_bracket/omni_jewel_wall_bracket_black.jpg','/detail_products_images/soundbar/soundbar_acc/omni_jewel_wall_bracket/detail_omni_jewel_wall_bracket.jpg','중국',1111,'2025-04-19 10:00:00','2025-06-30 10:00:00',0,0),
(33,403,'[BOSE] 보스 옴니 주얼 실링(천장) 브라켓 (서라운드 스피커 700 전용)',99000,'/products_images/soundbar/soundbar_acc/omni_jewel_ceiling_bracket/omni_jewel_ceiling_bracket_black.jpg','/detail_products_images/soundbar/soundbar_acc/omni_jewel_ceiling_bracket/detail_omni_jewel_ceiling_bracket.jpg','중국',1111,'2025-04-18 10:00:00','2025-06-30 10:00:00',0,0),
(34,403,'[BOSE] 보스 옴니 주얼 테이블 스탠드 (서라운드 스피커 700 정용)',110000,'/products_images/soundbar/soundbar_acc/omni_jewel_table_stand/omni_jewel_table_stand_black.jpg','/detail_products_images/soundbar/soundbar_acc/omni_jewel_table_stand/detail_omni_jewel_table_stand.jpg','한국',777,'2025-04-17 10:00:00','2025-07-04 01:00:23',0,0),
(35,403,'[BOSE] 보스 옴니 주얼 플로어 스탠드 (서라운드 스피커 700 전용)',253000,'/products_images/soundbar/soundbar_acc/omni_jewel_floor_stand/omni_jewel_floor_stand_black.jpg','/detail_products_images/soundbar/soundbar_acc/omni_jewel_floor_stand/detail_omni_jewel_floor_stand.jpg','중국',830,'2025-04-16 10:00:00','2025-06-30 10:00:00',0,0),
(36,501,'[BOSE] 보스 S1 프로 플러스 PA 스피커',880000,'/products_images/professional/s1_pro_plus_pa_speaker/s1_pro_plus_pa_speaker_black.jpg','/detail_products_images/professional/s1_pro_plus_pa_speaker/detail_s1_pro_plus_pa_speaker.jpg','중국',600,'2025-05-30 10:00:00','2025-06-30 10:00:00',0,0),
(37,501,'[BOSE] 보스 F1 Model 812 플렉시블 어레이 액티브 스피커',1650000,'/products_images/professional/f1_model_812_flexible_array_active_speaker/f1_model_812_flexible_array_active_speaker_black.jpg','/detail_products_images/professional/f1_model_812_flexible_array_active_speaker/detail_f1_model_812_flexible_array_active_speaker.jpg','중국',500,'2025-05-28 10:00:00','2025-06-30 10:00:00',0,0),
(38,501,'[BOSE] 보스 S1 프로 플러스 XLR 무선 트랜스미터',149000,'/products_images/professional/s1_pro_plus_xlr_bluetooth_transmitter/s1_pro_plus_xlr_bluetooth_transmitter_black.jpg','/detail_products_images/professional/s1_pro_plus_xlr_bluetooth_transmitter/detail_s1_pro_plus_xlr_bluetooth_transmitter.jpg','중국',700,'2025-05-18 10:00:00','2025-06-30 10:00:00',0,0),
(39,501,'[BOSE] 보스 뮤직 앰프',890000,'/products_images/professional/music_amplifier/music_amplifier_black.jpg','/detail_products_images/professional/music_amplifier/detail_music_amplifier.png','태국',600,'2025-05-29 10:00:00','2025-06-30 10:00:00',0,0),
(40,501,'[BOSE] 보스 251 실외 스피커',499000,'/products_images/professional/251_outdoor_speaker/251_outdoor_speaker_white.jpg','/detail_products_images/professional/251_outdoor_speaker/detail_251_outdoor_speaker.jpg','멕시코',200,'2025-05-17 10:00:00','2025-06-30 10:00:00',0,0),
(41,501,'[BOSE] 보스 F1 서브우퍼',1650000,'/products_images/professional/f1_subwoofer/f1_subwoofer_black.jpg','/detail_products_images/professional/f1_subwoofer/detail_f1_subwoofer.jpg','중국',600,'2025-05-27 10:00:00','2025-06-30 10:00:00',0,0),
(42,501,'[BOSE] 보스 Sub1 파워드 베이스 모듈',1210000,'/products_images/professional/sub1_powered_base_module/sub1_powered_base_module_black.jpg','/detail_products_images/professional/sub1_powered_base_module/detail_sub1_powered_base_module.jpg','중국',600,'2025-05-26 10:00:00','2025-06-30 10:00:00',0,0),
(43,501,'[BOSE] 보스 Sub2 파워드 베이스 모듈',1760000,'/products_images/professional/sub2_powered_base_module/sub2_powered_base_module_black.jpg','/detail_products_images/professional/sub2_powered_base_module/detail_sub2_powered_base_module.jpg','중국',100,'2025-05-25 10:00:00','2025-06-30 10:00:00',0,0),
(44,501,'[BOSE] 보스 T4S 톤매치 믹서',891000,'/products_images/professional/t4s_tone_match_mixer/t4s_tone_match_mixer_black.jpg','/detail_products_images/professional/t4s_tone_match_mixer/detail_t4s_tone_match_mixer.jpg','중국',80,'2025-05-24 10:00:00','2025-06-30 10:00:00',0,0),
(45,501,'[BOSE] 보스 T8S 톤매치 믹서',1320000,'/products_images/professional/t8s_tone_match_mixer/t8s_tone_match_mixer_black.jpg','/detail_products_images/professional/t8s_tone_match_mixer/detail_t8s_tone_match_mixer.jpg','중국',80,'2025-05-23 10:00:00','2025-06-30 10:00:00',0,0),
(46,501,'[BOSE] 보스 L1 Pro8 포터블 라인 어레이 시스템',1650000,'/products_images/professional/l1_pro_8_portable_line_array_system/l1_pro_8_portable_line_array_system_black.jpg','/detail_products_images/professional/l1_pro_8_portable_line_array_system/detail_l1_pro_8_portable_line_array_system.jpg','중국',731,'2025-05-22 10:00:00','2025-06-30 10:00:00',0,0),
(47,501,'[BOSE] 보스 L1 Pro16 포터블 라인 어레이 시스템',2640000,'/products_images/professional/l1_pro_16_portable_line_array_system/l1_pro_16_portable_line_array_system_black.jpg','/detail_products_images/professional/l1_pro_16_portable_line_array_system/detail_l1_pro_16_portable_line_array_system.jpg','중국',500,'2025-05-21 10:00:00','2025-06-30 10:00:00',0,0),
(48,501,'[BOSE] 보스 L1 Pro32 포터블 라인 어레이 시스템',2739000,'/products_images/professional/l1_pro_32_portable_line_array_system/l1_pro_32_portable_line_array_system_black.jpg','/detail_products_images/professional/l1_pro_32_portable_line_array_system/detail_l1_pro_32_portable_line_array_system.jpg','중국',300,'2025-05-20 10:00:00','2025-06-30 10:00:00',0,0),
(49,501,'[BOSE] 보스 S1 프로 플러스 악기 무선 트랜스미터',149000,'/products_images/professional/s1_pro_plus_bluetooth_transmitter/s1_pro_plus_bluetooth_transmitter_black.jpg','/detail_products_images/professional/s1_pro_plus_bluetooth_transmitter/detail_s1_pro_plus_bluetooth_transmitter.jpg','중국',400,'2025-05-19 10:00:00','2025-06-30 10:00:00',0,0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products_colors`
--

DROP TABLE IF EXISTS `products_colors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `products_colors` (
  `product_color_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '상품-색상 관계 일련번호',
  `product_id` int(11) NOT NULL COMMENT '상품 일련번호',
  `color_id` int(11) NOT NULL COMMENT '상품 별 가능 색상 일련번호',
  `product_img` varchar(255) NOT NULL COMMENT '상품 이미지',
  `main_img` tinyint(1) NOT NULL COMMENT '상품별 hover 이미지(true=노출/ false=숨김)',
  `sub_img` tinyint(1) NOT NULL COMMENT '상품별 hover 이미지(true=노출/ false=숨김)',
  `reg_date` datetime NOT NULL COMMENT '색상 정보를 등록한 날짜 시간 정보',
  `edit_date` datetime NOT NULL COMMENT '색상 정보를 수정한 날짜 시간 정보',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '삭제여부(Y/N)',
  PRIMARY KEY (`product_color_id`),
  KEY `products_colors_products_FK` (`product_id`),
  KEY `products_colors_colors_FK` (`color_id`),
  CONSTRAINT `products_colors_colors_FK` FOREIGN KEY (`color_id`) REFERENCES `colors` (`color_id`),
  CONSTRAINT `products_colors_products_FK` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=51402 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products_colors`
--

LOCK TABLES `products_colors` WRITE;
/*!40000 ALTER TABLE `products_colors` DISABLE KEYS */;
INSERT INTO `products_colors` VALUES
(1001,2,4,'/products_images/headphones/headphones/qc_ultra_headphones/qc_ultra_headphones_black.jpg',1,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1002,2,2,'/products_images/headphones/headphones/qc_ultra_headphones/qc_ultra_headphones_whitesmoke.jpg',0,1,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1003,2,13,'/products_images/headphones/headphones/qc_ultra_headphones/qc_ultra_headphones_lunablue.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1004,2,12,'/products_images/headphones/headphones/qc_ultra_headphones/qc_ultra_headphones_deepplum.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1005,2,10,'/products_images/headphones/headphones/qc_ultra_headphones/qc_ultra_headphones_sandstone.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1006,1,5,'/products_images/headphones/headphones/qc_headphones/qc_headphones_tripleblack.jpg',1,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1007,1,2,'/products_images/headphones/headphones/qc_headphones/qc_headphones_whitesmoke.jpg',0,1,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1008,1,8,'/products_images/headphones/headphones/qc_headphones/qc_headphones_cypressgreen.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1009,1,9,'/products_images/headphones/headphones/qc_headphones/qc_headphones_moonstoneblue.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1010,1,6,'/products_images/headphones/headphones/qc_headphones/qc_headphones_bluedusk.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1011,1,10,'/products_images/headphones/headphones/qc_headphones/qc_headphones_sandstone.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1012,1,7,'/products_images/headphones/headphones/qc_headphones/qc_headphones_childlilac.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1013,1,11,'/products_images/headphones/headphones/qc_headphones/qc_headphones_twilightblue.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1014,4,4,'/products_images/headphones/headphones_acc/qc_ultra_headphones_acc/qc_ultra_headphones_acc_black.jpg',1,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1015,4,2,'/products_images/headphones/headphones_acc/qc_ultra_headphones_acc/qc_ultra_headphones_acc_whitesmoke.jpg',0,1,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1016,4,10,'/products_images/headphones/headphones_acc/qc_ultra_headphones_acc/qc_ultra_headphones_acc_sandstone.png',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1017,3,5,'/products_images/headphones/headphones_acc/qc_headphones_acc/qc_headphones_acc_tripleblack.jpg',1,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1018,3,2,'/products_images/headphones/headphones_acc/qc_headphones_acc/qc_headphones_acc_whitesmoke.jpg',0,1,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1019,3,14,'/products_images/headphones/headphones_acc/qc_headphones_acc/qc_headphones_acc_midnightblue.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1020,3,15,'/products_images/headphones/headphones_acc/qc_headphones_acc/qc_headphones_acc_eclipsegray.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1021,5,4,'/products_images/earbuds/earbuds/ultra_open_earbuds/ultra_open_earbuds_black.jpg',1,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1022,5,2,'/products_images/earbuds/earbuds/ultra_open_earbuds/ultra_open_earbuds_whitesmoke.jpg',0,1,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1023,5,9,'/products_images/earbuds/earbuds/ultra_open_earbuds/ultra_open_earbuds_moonstoneblue.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1024,5,13,'/products_images/earbuds/earbuds/ultra_open_earbuds/ultra_open_earbuds_lunablue.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1025,5,7,'/products_images/earbuds/earbuds/ultra_open_earbuds/ultra_open_earbuds_childlilac.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1026,5,17,'/products_images/earbuds/earbuds/ultra_open_earbuds/ultra_open_earbuds_sunsetiridescent.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1027,5,12,'/products_images/earbuds/earbuds/ultra_open_earbuds/ultra_open_earbuds_deepplum.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1028,6,4,'/products_images/earbuds/earbuds/qc_earbuds/qc_earbuds_black.jpg',1,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1029,6,2,'/products_images/earbuds/earbuds/qc_earbuds/qc_earbuds_whitesmoke.jpg',0,1,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1030,6,7,'/products_images/earbuds/earbuds/qc_earbuds/qc_earbuds_childlilac.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1031,6,16,'/products_images/earbuds/earbuds/qc_earbuds/qc_earbuds_petalpink.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1032,6,11,'/products_images/earbuds/earbuds/qc_earbuds/qc_earbuds_twilightblue.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1033,7,28,'/products_images/earbuds/earbuds/ultra_open_earbuds_60th_ed/ultra_open_earbuds_60th_ed_diamond.jpg',1,1,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1034,8,4,'/products_images/earbuds/earbuds/qc_ultra_earbuds/qc_ultra_earbuds_black.jpg',1,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1035,8,2,'/products_images/earbuds/earbuds/qc_ultra_earbuds/qc_ultra_earbuds_whitesmoke.jpg',0,1,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1036,8,13,'/products_images/earbuds/earbuds/qc_ultra_earbuds/qc_ultra_earbuds_lunablue.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1037,9,5,'/products_images/earbuds/earbuds_acc/ultra_open_earbuds_silicone_cover_case/ultra_open_earbuds_silicone_cover_case_tripleblack.jpg',1,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1038,9,2,'/products_images/earbuds/earbuds_acc/ultra_open_earbuds_silicone_cover_case/ultra_open_earbuds_silicone_cover_case_whitesmoke.jpg',0,1,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1039,9,7,'/products_images/earbuds/earbuds_acc/ultra_open_earbuds_silicone_cover_case/ultra_open_earbuds_silicone_cover_case_childlilac.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1040,11,4,'/products_images/earbuds/earbuds_acc/qc_earbuds_silicone_cover_case/qc_earbuds_silicone_cover_case_tripleblack.jpg',1,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1041,11,2,'/products_images/earbuds/earbuds_acc/qc_earbuds_silicone_cover_case/qc_earbuds_silicone_cover_case_soapstone.jpg',0,1,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1042,10,5,'/products_images/earbuds/earbuds_acc/qc_earbuds_silicone_cover_case/qc_earbuds_silicone_cover_case_tripleblack.jpg',1,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1043,10,3,'/products_images/earbuds/earbuds_acc/qc_earbuds_silicone_cover_case/qc_earbuds_silicone_cover_case_soapstone.jpg',0,1,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1044,12,4,'/products_images/speaker/bluetooth_speaker/sound_link_plex_speaker/sound_link_plex_speaker_black.jpg',1,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1045,12,6,'/products_images/speaker/bluetooth_speaker/sound_link_plex_speaker/sound_link_plex_speaker_bluedusk.jpg',0,1,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1046,12,10,'/products_images/speaker/bluetooth_speaker/sound_link_plex_speaker/sound_link_plex_speaker_sandstone.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1047,12,20,'/products_images/speaker/bluetooth_speaker/sound_link_plex_speaker/sound_link_plex_speaker_alpinesage.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1048,12,16,'/products_images/speaker/bluetooth_speaker/sound_link_plex_speaker/sound_link_plex_speaker_petalpink.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1049,12,11,'/products_images/speaker/bluetooth_speaker/sound_link_plex_speaker/sound_link_plex_speaker_twilightblue.jpg',0,0,'2025-01-01 12:13:14','2025-07-09 10:10:10',0),
(1051,13,4,'/products_images/speaker/bluetooth_speaker/sound_link_max_portable_speaker/sound_link_max_portable_speaker_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-14 07:14:01',0),
(1052,13,6,'/products_images/speaker/bluetooth_speaker/sound_link_max_portable_speaker/sound_link_max_portable_speaker_bluedusk.jpg',0,1,'2025-01-01 01:01:01','2025-07-14 07:14:01',0),
(1055,14,4,'/products_images/speaker/bluetooth_speaker/sound_link_micro_speaker/sound_link_micro_speaker_black.jpg',0,0,'2025-01-01 01:01:01','2025-07-14 07:14:01',0),
(1056,14,2,'/products_images/speaker/bluetooth_speaker/sound_link_micro_speaker/sound_link_micro_speaker_whitesmoke.jpg',0,1,'2025-01-01 01:01:01','2025-07-14 07:14:01',0),
(1057,14,19,'/products_images/speaker/bluetooth_speaker/sound_link_micro_speaker/sound_link_micro_speaker_stoneblue.jpg',1,0,'2025-01-01 01:01:01','2025-07-14 07:14:01',0),
(1060,15,18,'/products_images/speaker/bluetooth_speaker/portable_home_speaker/portable_home_speaker_luxsilver.jpg',1,0,'2025-01-01 01:01:01','2025-07-14 07:14:01',0),
(1061,15,5,'/products_images/speaker/bluetooth_speaker/portable_home_speaker/portable_home_speaker_tripleblack.jpg',0,1,'2025-01-01 01:01:01','2025-07-14 07:14:01',0),
(1064,16,18,'/products_images/speaker/bluetooth_speaker/sound_link_revolve_plus_speaker/sound_link_revolve_plus_speaker_luxsilver.jpg',0,1,'2025-01-01 01:01:01','2025-07-14 07:14:01',0),
(1065,16,5,'/products_images/speaker/bluetooth_speaker/sound_link_revolve_plus_speaker/sound_link_revolve_plus_speaker_tripleblack.jpg',1,0,'2025-01-01 01:01:01','2025-07-14 07:14:01',0),
(1068,20,23,'/products_images/speaker/speaker_acc/sound_link_max_rope_handle/sound_link_max_rope_handle_darkskyblue.jpg',0,1,'2025-01-01 01:01:01','2025-07-14 07:14:01',0),
(1069,20,24,'/products_images/speaker/speaker_acc/sound_link_max_rope_handle/sound_link_max_rope_handle_purple.jpg',1,0,'2025-01-01 01:01:01','2025-07-14 07:14:01',0),
(1070,20,25,'/products_images/speaker/speaker_acc/sound_link_max_rope_handle/sound_link_max_rope_handle_permanentred.jpg',0,0,'2025-01-01 01:01:01','2025-07-14 07:14:01',0),
(1071,20,26,'/products_images/speaker/speaker_acc/sound_link_max_rope_handle/sound_link_max_rope_handle_hypercitron.jpg',0,0,'2025-01-01 01:01:01','2025-07-14 07:14:01',0),
(1072,18,4,'/products_images/speaker/speaker_acc/sound_link_max_rope_carrying_strap_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 00:12:12',0),
(1073,18,6,'/products_images/speaker/speaker_acc/sound_link_max_rope_carrying_strap_bluedusk.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 00:12:12',0),
(1074,17,4,'/products_images/speaker/speaker_acc/portable_home_speaker_charging_cradle_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 00:12:12',0),
(1075,17,18,'/products_images/speaker/speaker_acc/portable_home_speaker_charging_cradle_luxsilver.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 00:12:12',0),
(1076,19,4,'/products_images/speaker/speaker_acc/sound_link_revolve_charging_cradle_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 00:12:12',0),
(1077,22,4,'/products_images/soundbar/soundbar/tv_speaker_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 00:56:56',0),
(1078,23,4,'/products_images/soundbar/soundbar/smart_soundbar_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 00:56:56',0),
(1079,21,22,'/products_images/soundbar/soundbar/smart_ultra_soundbar_arcticwhite.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 00:56:56',0),
(1080,21,4,'/products_images/soundbar/soundbar/smart_ultra_soundbar_black.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 00:56:56',0),
(1081,26,4,'/products_images/soundbar/soundbar/base_module_700_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 00:56:56',0),
(1082,26,22,'/products_images/soundbar/soundbar/base_module_700_arcticwhite.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 00:56:56',0),
(1083,24,4,'/products_images/soundbar/soundbar/base_module_500_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 00:56:56',0),
(1084,25,4,'/products_images/soundbar/soundbar/surround_speaker_700_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 00:56:56',0),
(1085,25,1,'/products_images/soundbar/soundbar/surround_speaker_700_white.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 00:56:56',0),
(1086,27,4,'/products_images/soundbar/soundbar/surround_speaker_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 00:56:56',0),
(1087,27,1,'/products_images/soundbar/soundbar/surround_speaker_white.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 00:56:56',0),
(1088,28,4,'/products_images/soundbar/soundbar_acc/soundbar_wall_bracket_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1089,28,1,'/products_images/soundbar/soundbar_acc/soundbar_wall_bracket_white.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1090,30,4,'/products_images/soundbar/soundbar_acc/surround_ceiling_bracket_ub20_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1091,30,1,'/products_images/soundbar/soundbar_acc/surround_ceiling_bracket_ub20_white.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1092,31,4,'/products_images/soundbar/soundbar_acc/surround_table_stand_uts20_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1093,31,1,'/products_images/soundbar/soundbar_acc/surround_table_stand_uts20_white.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1094,29,4,'/products_images/soundbar/soundbar_acc/surround_floor_stand_ufs20_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1095,29,1,'/products_images/soundbar/soundbar_acc/surround_floor_stand_ufs20_white.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1096,32,4,'/products_images/soundbar/soundbar_acc/omni_jewel_wall_bracket_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1097,32,1,'/products_images/soundbar/soundbar_acc/omni_jewel_wall_bracket_white.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1098,33,4,'/products_images/soundbar/soundbar_acc/omni_jewel_ceiling_bracket_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1099,33,1,'/products_images/soundbar/soundbar_acc/omni_jewel_ceiling_bracket_white.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1100,34,4,'/products_images/soundbar/soundbar_acc/omni_jewel_table_stand_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1101,34,1,'/products_images/soundbar/soundbar_acc/omni_jewel_table_stand_white.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1102,35,4,'/products_images/soundbar/soundbar_acc/omni_jewel_floor_stand_black.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1103,35,1,'/products_images/soundbar/soundbar_acc/omni_jewel_floor_stand_white.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 01:32:32',0),
(1104,36,4,'/products_images/professional/s1_pro_plus_pa_speaker_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1105,39,4,'/products_images/professional/music_amplifier_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1106,37,4,'/products_images/professional/f1_model_812_flexible_array_active_speaker_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1107,41,4,'/products_images/professional/f1_subwoofer_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1108,42,4,'/products_images/professional/sub1_powered_base_module_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1109,43,4,'/products_images/professional/sub2_powered_base_module_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1110,44,4,'/products_images/professional/t4s_tone_match_mixer_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1111,45,4,'/products_images/professional/t8s_tone_match_mixer_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1112,46,4,'/products_images/professional/l1_pro_8_portable_line_array_system_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1113,47,4,'/products_images/professional/l1_pro_16_portable_line_array_system_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1114,48,4,'/products_images/professional/l1_pro_32_portable_line_array_system_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1115,49,4,'/products_images/professional/s1_pro_plus_bluetooth_transmitter_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1116,38,4,'/products_images/professional/s1_pro_plus_xlr_bluetooth_transmitter_black.jpg',1,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1117,40,1,'/products_images/professional/251_outdoor_speaker_white.jpg',1,0,'2025-01-01 01:01:01','2025-07-21 01:46:46',0),
(1118,40,4,'/products_images/professional/251_outdoor_speaker_black.jpg',0,1,'2025-01-01 01:01:01','2025-07-21 01:46:46',0);
/*!40000 ALTER TABLE `products_colors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session`
--

DROP TABLE IF EXISTS `spring_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session` (
  `primary_id` char(36) NOT NULL,
  `session_id` char(36) NOT NULL,
  `creation_time` bigint(20) NOT NULL,
  `last_access_time` bigint(20) NOT NULL,
  `max_inactive_interval` int(11) NOT NULL,
  `expiry_time` bigint(20) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`primary_id`),
  UNIQUE KEY `spring_session_ix1` (`session_id`),
  KEY `spring_session_ix2` (`expiry_time`) USING BTREE,
  KEY `spring_session_ix3` (`PRINCIPAL_NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session`
--

LOCK TABLES `spring_session` WRITE;
/*!40000 ALTER TABLE `spring_session` DISABLE KEYS */;
INSERT INTO `spring_session` VALUES
('ca091207-4116-4c02-ae64-d09fb96cc72b','229f23cb-158d-41b9-bad9-a120b20f9012',1754280052253,1754280053236,1800,1754281853236,NULL);
/*!40000 ALTER TABLE `spring_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `spring_session_attributes`
--

DROP TABLE IF EXISTS `spring_session_attributes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `spring_session_attributes` (
  `session_primary_id` char(36) NOT NULL,
  `attribute_name` varchar(200) NOT NULL,
  `attribute_bytes` blob NOT NULL,
  PRIMARY KEY (`session_primary_id`,`attribute_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `spring_session_attributes`
--

LOCK TABLES `spring_session_attributes` WRITE;
/*!40000 ALTER TABLE `spring_session_attributes` DISABLE KEYS */;
INSERT INTO `spring_session_attributes` VALUES
('058a12e7-af00-4d5f-a2fc-06ff522a06b7','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('130e75f7-60e5-49d4-a86a-a0a83afec948','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('1a386a8c-3651-4319-8cf5-b32bdde62099','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('1f811708-6daa-4816-89b0-6185c319560c','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('31a563dd-ece1-4289-bf1b-cf2ec2945199','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('335998b1-f291-4477-9fc9-980aff8b3fbf','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('35d19768-e4ef-4a9f-9ec0-e17ddff579ee','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('3b2422a6-fbfe-4e2e-a245-c8ec6e48dfc7','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('3da21241-7fe7-43c0-ba7f-20b50f8e052b','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0\0\0t\02025-07-16 12:53:34t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0\0'),
('4d6174cb-722b-445b-a381-92bb53bf977d','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('510699b7-78d0-4df2-8a23-1f6cc85758f5','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('54442a2b-5eba-4d0b-a835-6bb0fdde1211','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('5a4cbead-486e-4e1a-8ef4-593ca9363d66','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('5da12f9b-89b1-4ae0-87a4-b0e0484a0aa1','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('620d536d-f7ff-4189-8833-89cd7b2fe3ea','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('6229b76f-055a-4ad2-badb-078a3c6ee918','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('63a00622-88bd-45b8-a215-3d77288a855e','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('63f56fd0-cda5-4fdd-a18c-c12c31eef23c','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('6d6ba0cd-bc04-4aec-9aa5-abafc6add4c8','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('6f0e3609-bb85-45c0-b2de-11c7b793e172','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0\0\0t\02025-07-09 15:09:55t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 cfc092a9a1e05926ef36482624fac88ft\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0\0'),
('6f2ac34c-3457-4148-aa7f-f70780ef65b6','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('70a2ac8b-17f5-4f2d-a69e-c662c856259b','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('710027eb-c6af-415c-bf61-f3e28d95817a','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('725421d9-3ea4-466b-8845-a2b4ca210cf0','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('736fb580-2d15-405f-adaa-1041472b7efb','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('7c6868a4-c52c-4052-aa8c-d89cd85d4378','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('998841b2-c221-4068-b4ed-ef7ef07c875e','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('a06d0090-1a7b-4ed6-8e57-2c8ca801553d','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('a65d1662-63c5-4747-8c6a-099bdbb2d657','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('b100e7ee-bc0f-4ce1-9daa-195100c6389f','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('c06a6289-ba12-4a51-8b82-8681a3d8884e','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('c23c7ed3-8432-4e90-b837-8a4592bfaae9','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('cf41e40d-08ac-4384-97f8-237408932827','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('d038f85e-dabc-47ac-accf-b52b89f39f93','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('d0adaa69-b4de-4823-9d52-b716dcf0dd42','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('d478564d-adba-4046-bcd8-833301b105d4','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('d691546f-eda0-4aee-a08d-3c0dd87fc311','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('d89ceae5-8196-487e-9a47-e14f24186cc4','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0\0\0t\02025-07-09 15:09:55t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 cfc092a9a1e05926ef36482624fac88ft\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0\0'),
('e1daa5a1-220f-44e2-a8fe-d59ba890ec7c','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-17 10:48:25t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity'),
('ecd60b1b-0cd2-42fa-aec6-21ccd9f68fab','memberInfo','��\0sr\0com.clonebose.bose.models.User�.�%�G��\0Z\0	emailTermZ\0isOutI\0orderTotalPriceZ\0requiredTermZ\0smsTermI\0userIdL\0editDatet\0Ljava/lang/String;L\0regDateq\0~\0L\0userAddressq\0~\0L\0\ruserBirthdateq\0~\0L\0	userEmailq\0~\0L\0\nuserGenderq\0~\0L\0userNameq\0~\0L\0userPasswordq\0~\0L\0	userPhoneq\0~\0L\0userPostcodeq\0~\0L\0userProfileImgq\0~\0L\0userSpecificAddressq\0~\0xp\0\0\0\0\0\0\0\0t\02025-07-20 15:44:30t\02025-07-09 15:06:44t\0,서울 동대문구 서울시립대로2길 2t\0\n2019-02-15t\0ybox92@gmail.comt\0남t\0	양지훈t\0 5bacd9f25613659b2fbd2f3a58822e5ct\001012345678t\002594t\06/files/2025/07/09/46db7e44487d4fe5907f7cca8299a84a.jpgt\0ihity');
/*!40000 ALTER TABLE `spring_session_attributes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_categories`
--

DROP TABLE IF EXISTS `sub_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_categories` (
  `sub_category_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '하위 카테고리 일련번호',
  `category_id` int(11) NOT NULL COMMENT '상위 카테고리 일련번호',
  `sub_category_name` varchar(100) NOT NULL COMMENT '하위 카테고리명',
  `reg_date` datetime NOT NULL COMMENT '하위  카테고리 정보를 등록한 날짜 시간 정보',
  `edit_date` datetime NOT NULL COMMENT '하위  카테고리 정보를 수정한 날짜 시간 정보',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '삭제여부(Y/N)',
  PRIMARY KEY (`sub_category_id`),
  KEY `sub_categorys_categories_FK` (`category_id`),
  CONSTRAINT `sub_categorys_categories_FK` FOREIGN KEY (`category_id`) REFERENCES `categories` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_categories`
--

LOCK TABLES `sub_categories` WRITE;
/*!40000 ALTER TABLE `sub_categories` DISABLE KEYS */;
INSERT INTO `sub_categories` VALUES
(101,1,'헤드폰','2024-01-01 10:00:00','2025-07-02 17:31:00',0),
(102,1,'헤드폰 액세서리','2024-01-01 10:00:00','2025-07-02 17:31:00',0),
(201,2,'이어버드','2024-01-01 10:00:00','2025-07-02 17:31:00',0),
(202,2,'이어버드 액세서리','2024-01-01 10:00:00','2025-07-02 17:31:00',0),
(301,3,'무선 스피커','2024-01-01 10:00:00','2025-07-02 17:31:00',0),
(302,3,'홈 스피커','2024-01-01 10:00:00','2025-07-02 17:31:00',0),
(303,3,'스피커 액세서리','2024-01-01 10:00:00','2025-07-02 17:31:00',0),
(401,4,'사운드바','2024-01-01 10:00:00','2025-07-02 17:31:00',0),
(402,4,'홈 시어터','2024-01-01 10:00:00','2025-07-02 17:31:00',0),
(403,4,'사운드바 액세서리','2024-01-01 10:00:00','2025-07-02 17:31:00',0),
(501,5,'프로페셔널','2024-01-01 10:00:00','2025-07-02 17:31:00',0);
/*!40000 ALTER TABLE `sub_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '회원 일련번호',
  `user_email` varchar(320) NOT NULL COMMENT '사용자 로그인 ID 및 이메일',
  `user_password` varchar(255) NOT NULL COMMENT '사용자 로그인 비밀번호 (암호화)',
  `user_name` varchar(50) NOT NULL COMMENT '사용자 이름',
  `user_gender` enum('남','여') NOT NULL COMMENT '사용자 성별',
  `user_phone` varchar(20) NOT NULL COMMENT '사용자 연락처',
  `user_birthdate` date NOT NULL COMMENT '사용자 생년월일',
  `user_address` varchar(255) DEFAULT NULL COMMENT '사용자 주소',
  `user_postcode` varchar(10) DEFAULT NULL COMMENT '사용자 우편번호',
  `user_specific_address` varchar(255) DEFAULT NULL COMMENT '사용자 상세주소',
  `user_profile_img` varchar(255) DEFAULT NULL COMMENT '사용자 프로필 이미지',
  `required_term` tinyint(1) NOT NULL DEFAULT 0 COMMENT '필수 약관 동의 여부',
  `sms_term` tinyint(1) NOT NULL DEFAULT 0 COMMENT 'SMS 수신 동의 여부',
  `email_term` tinyint(1) NOT NULL DEFAULT 0 COMMENT 'E-Mail 수신 동의 여부',
  `is_out` tinyint(1) NOT NULL DEFAULT 0 COMMENT '탈퇴여부(Y/N)',
  `reg_date` datetime NOT NULL COMMENT '가입한 날짜 시간 정보',
  `edit_date` datetime NOT NULL COMMENT '가입 정보를 수정한 날짜 시간 정보',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='회원 정보를 저장하는 테이블';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `visitorCount`
--

DROP TABLE IF EXISTS `visitorCount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `visitorCount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `visitor_ip` varchar(45) NOT NULL,
  `visited_at` timestamp NOT NULL,
  `visit_date` date NOT NULL COMMENT '날짜별 집계용 (YYYY-MM-DD)',
  `visit_hour` tinyint(4) NOT NULL COMMENT '시간별 집계용 (0-23)',
  `visit_year` year(4) NOT NULL COMMENT '연도별 집계용',
  `visit_month` tinyint(4) NOT NULL COMMENT '월별 집계용 (1-12)',
  `visit_week` tinyint(4) NOT NULL COMMENT '주차별 집계용 (1-53)',
  `visit_day_of_week` tinyint(4) NOT NULL COMMENT '요일별 집계용 (1-7, 월-일)',
  `reg_date` timestamp NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `idx_visit_date` (`visit_date`),
  KEY `idx_visit_year_month` (`visit_year`,`visit_month`),
  KEY `idx_visit_year_week` (`visit_year`,`visit_week`),
  KEY `idx_visited_at` (`visited_at`),
  KEY `idx_visitor_ip_date` (`visitor_ip`,`visit_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `visitorCount`
--

LOCK TABLES `visitorCount` WRITE;
/*!40000 ALTER TABLE `visitorCount` DISABLE KEYS */;
/*!40000 ALTER TABLE `visitorCount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wishlists`
--

DROP TABLE IF EXISTS `wishlists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8mb4 */;
CREATE TABLE `wishlists` (
  `wishlist_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '위시리스트 정보 일련번호',
  `user_id` int(11) NOT NULL COMMENT '회원 정보',
  `product_id` int(11) NOT NULL COMMENT '상품 정보',
  `reg_date` datetime NOT NULL COMMENT '위시리스트에 추가한 날짜 시간 정보',
  `edit_date` datetime NOT NULL COMMENT '장바구니 정보를 수정한 날짜 시간 정보',
  `is_delete` tinyint(1) NOT NULL DEFAULT 0 COMMENT '삭제여부(Y/N)',
  PRIMARY KEY (`wishlist_id`),
  KEY `wishlists_users_FK` (`user_id`),
  KEY `wishlists_products_FK` (`product_id`),
  CONSTRAINT `wishlists_products_FK` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `wishlists_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wishlists`
--

LOCK TABLES `wishlists` WRITE;
/*!40000 ALTER TABLE `wishlists` DISABLE KEYS */;
/*!40000 ALTER TABLE `wishlists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'clonebose'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*M!100616 SET NOTE_VERBOSITY=@OLD_NOTE_VERBOSITY */;

-- Dump completed on 2025-08-08 10:25:38
