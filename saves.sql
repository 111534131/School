-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2025-05-01 08:47:17
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
-- 資料表結構 `saves`
--

CREATE TABLE `saves` (
  `save_id` bigint(20) NOT NULL,
  `player_id` int(11) DEFAULT NULL,
  `character_id` varchar(50) DEFAULT NULL,
  `save_name` varchar(255) DEFAULT NULL,
  `save_time` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `saves`
--

INSERT INTO `saves` (`save_id`, `player_id`, `character_id`, `save_name`, `save_time`) VALUES
(13, 1, 'marcus', '1', '2025-03-16T11:06:18.958379200');

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `saves`
--
ALTER TABLE `saves`
  ADD PRIMARY KEY (`save_id`),
  ADD KEY `player_id` (`player_id`),
  ADD KEY `character_id` (`character_id`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `saves`
--
ALTER TABLE `saves`
  MODIFY `save_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- 已傾印資料表的限制式
--

--
-- 資料表的限制式 `saves`
--
ALTER TABLE `saves`
  ADD CONSTRAINT `saves_ibfk_1` FOREIGN KEY (`player_id`) REFERENCES `players` (`player_id`),
  ADD CONSTRAINT `saves_ibfk_2` FOREIGN KEY (`character_id`) REFERENCES `characters` (`character_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
