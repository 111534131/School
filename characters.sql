-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2025-05-01 08:47:40
-- 伺服器版本： 10.4.27-MariaDB
-- PHP 版本： 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `trpg`
--

-- --------------------------------------------------------

--
-- 資料表結構 `characters`
--

CREATE TABLE `characters` (
  `character_id` varchar(50) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `profession` varchar(255) DEFAULT NULL,
  `education` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `birthplace` varchar(255) DEFAULT NULL,
  `residence` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `characters`
--

INSERT INTO `characters` (`character_id`, `name`, `file_path`, `profession`, `education`, `gender`, `age`, `birthplace`, `residence`, `description`) VALUES
('badro', '巴德羅', 'badro.png', '間諜', '大學', 'M', 32, '阿爾瑪星', '垂直之城暮光塔', '他身著一襲深色西裝，剪裁合身的外套下是淺色的襯衫，黑色的領帶一絲不苟地繫於頸間。與周遭血腥的場景形成強烈對比，卻又詭異地相得益彰。在昏黃的天色下閃爍著冷冽的光澤。'),
('johnson', '強森', 'johnson.png', '探險家', '召喚峽谷大學畢業', 'M', 35, '阿爾瑪星', '星球聯邦', '服裝，背後經常帶著一個舊皮包。性格：堅毅冷靜，擁有沉著的領導能力，面對突發狀況總能保持冷靜並作出果斷決策。'),
('marcus', '馬庫斯', 'marcus.png', '神祕學家', '聖洛夫基金會附屬大學博士學位', 'F', 17, '羅馬尼亞', '弗蘭南群島', '深藍色的長外套，搭配白色連身裙，外套上有銀色的拉鍊與紐扣，細節精緻。圍著一條厚實的白色圍巾並戴著一頂別著隻藍色小魚，帽簷帶有金屬拉鍊裝飾的漂亮報童帽。');

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `characters`
--
ALTER TABLE `characters`
  ADD PRIMARY KEY (`character_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
