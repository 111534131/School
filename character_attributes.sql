-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2025-05-01 08:47:35
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
-- 資料表結構 `character_attributes`
--

CREATE TABLE `character_attributes` (
  `id` int(11) NOT NULL,
  `character_id` varchar(255) NOT NULL,
  `strength` int(11) NOT NULL DEFAULT 0,
  `constitution` int(11) NOT NULL DEFAULT 0,
  `agility` int(11) NOT NULL DEFAULT 0,
  `willpower` int(11) NOT NULL DEFAULT 0,
  `intelligence` int(11) NOT NULL DEFAULT 0,
  `luck` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `character_attributes`
--

INSERT INTO `character_attributes` (`id`, `character_id`, `strength`, `constitution`, `agility`, `willpower`, `intelligence`, `luck`) VALUES
(4, 'marcus', 50, 50, 50, 75, 80, 55),
(5, 'badro', 60, 62, 75, 65, 65, 50),
(6, 'johnson', 60, 70, 75, 75, 60, 54);

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `character_attributes`
--
ALTER TABLE `character_attributes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `character_id` (`character_id`);

--
-- 在傾印的資料表使用自動遞增(AUTO_INCREMENT)
--

--
-- 使用資料表自動遞增(AUTO_INCREMENT) `character_attributes`
--
ALTER TABLE `character_attributes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- 已傾印資料表的限制式
--

--
-- 資料表的限制式 `character_attributes`
--
ALTER TABLE `character_attributes`
  ADD CONSTRAINT `character_attributes_ibfk_1` FOREIGN KEY (`character_id`) REFERENCES `characters` (`character_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
