-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1:3306
-- Létrehozás ideje: 2018. Már 25. 21:19
-- Kiszolgáló verziója: 5.7.19
-- PHP verzió: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `cegesmunkak`
--
CREATE DATABASE IF NOT EXISTS `cegesmunkak` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_hungarian_ci;
USE `cegesmunkak`;

DELIMITER $$
--
-- Eljárások
--
DROP PROCEDURE IF EXISTS `addOraber`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addOraber` (IN `v_munka` INT(11), IN `v_alany` INT(11), IN `v_oraszam` DECIMAL(10,5))  BEGIN

  INSERT INTO cegesmunkak.koltseg select NULL, v_munka, (select oraber from emberek where id=v_alany)*v_oraszam, (select id from koltsegtipus where nev like 'órabér'), v_alany, timestamp(now());

END$$

DROP PROCEDURE IF EXISTS `addToCsoport`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addToCsoport` (IN `v_csop` INT(11), IN `v_alany` INT(11), IN `v_feladat` VARCHAR(100))  BEGIN

  INSERT INTO cegesmunkak.csoporttagsag select v_csop, v_alany, (select id from feladatok where nev like v_feladat), null;

END$$

DROP PROCEDURE IF EXISTS `delTagsag`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `delTagsag` (IN `v_id` INT(11), IN `v_tag` INT(11))  BEGIN

  DELETE FROM csoporttagsag WHERE id=v_id AND tag=v_tag;

END$$

DROP PROCEDURE IF EXISTS `insertEmber`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertEmber` (IN `veznev` VARCHAR(100), IN `kernev` VARCHAR(100), IN `pozicio` INT(11), IN `oraber` INT(100))  BEGIN

  INSERT INTO cegesmunkak.emberek select NULL, veznev, kernev, pozicio, oraber, DATE(now()), 0;

END$$

DROP PROCEDURE IF EXISTS `insertMsg`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertMsg` (IN `v_csop` INT(11), IN `v_id` INT(11), IN `v_msg` VARCHAR(300))  BEGIN

INSERT INTO uzenetek(csopID, tagID, msg) VALUES(v_csop, v_id, v_msg);

END$$

DROP PROCEDURE IF EXISTS `insertMunka`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertMunka` (IN `v_felvetelIdeje` VARCHAR(100), IN `v_megrendelo` VARCHAR(300), IN `v_megnevezes` VARCHAR(100), IN `v_felelos` INT(11), IN `v_tipus` INT(11), IN `v_uzletkoto` INT(11))  BEGIN

  Insert into munkak(felvetelIdeje, megrendelo, megnevezes, felelos, tipus, uzletkoto) 
  values(v_felvetelIdeje,v_megrendelo,v_megnevezes,v_felelos,v_tipus,v_uzletkoto);

END$$

DROP PROCEDURE IF EXISTS `insertTag`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `insertTag` (IN `v_id` INT(11), IN `v_tag` INT(11), IN `v_feladat` INT(11))  BEGIN

 INSERT INTO `csoporttagsag` (`id`, `tag`, `feladat`) VALUES (v_id,v_tag,v_feladat);

END$$

DROP PROCEDURE IF EXISTS `kiosztMunka`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `kiosztMunka` (IN `v_munka` VARCHAR(300), IN `v_csoport` VARCHAR(300))  BEGIN

  update cegesmunkak.munkak set 
  
  csoport=(select id from csoportok where csoportok.nev like v_csoport)
  
  where concat(munkak.megrendelo,munkak.megnevezes) like v_munka and id >0
  ;

END$$

DROP PROCEDURE IF EXISTS `munkaVallal`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `munkaVallal` (IN `v_felid` INT(11), IN `v_id` INT(11))  BEGIN

  update munkak set felelos=v_felid where id=v_id;

END$$

DROP PROCEDURE IF EXISTS `pwcheck`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `pwcheck` (IN `v_id` INT(11), IN `v_pw` VARCHAR(300))  BEGIN

  SELECT count(*) FROM emberek where id=v_id and hashpw=sha1(v_pw);

END$$

DROP PROCEDURE IF EXISTS `selectCsoport`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectCsoport` ()  BEGIN

SELECT id, nev, leiras FROM csoportok order by nev;

END$$

DROP PROCEDURE IF EXISTS `selectCsoportNevek`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectCsoportNevek` (IN `v_csop` INT(11))  BEGIN

SELECT nev FROM csoportok WHERE id=v_csop;

END$$

DROP PROCEDURE IF EXISTS `selectCsoportTime`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectCsoportTime` (IN `v_csop` INT(11))  BEGIN

SELECT max(times) FROM uzenetek WHERE csopid=v_csop;

END$$

DROP PROCEDURE IF EXISTS `selectEmber`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectEmber` ()  BEGIN

SELECT e.id, e.veznev,e.kernev, e.kezdes, p.nev as pozicio, e.oraber FROM emberek as e join poziciok as p on p.id=e.pozicio order by concat(e.veznev,' ',e.kernev);

END$$

DROP PROCEDURE IF EXISTS `selectEmberCreate`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectEmberCreate` ()  BEGIN

  SELECT id, veznev, kernev, pozicio FROM emberek where allowcreate=1;

END$$

DROP PROCEDURE IF EXISTS `selectFeladat`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectFeladat` ()  BEGIN

  SELECT id, nev FROM feladatok;

END$$

DROP PROCEDURE IF EXISTS `selectMsgs`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectMsgs` (IN `v_csop` INT(11))  BEGIN

SELECT concat(tag.veznev,' ',tag.kernev), tag.id, msg.msg, msg.times 
FROM uzenetek AS msg 
JOIN emberek AS tag ON tag.id=msg.tagID 
WHERE msg.csopID=v_csop ORDER BY msg.times ASC;

END$$

DROP PROCEDURE IF EXISTS `selectMunka`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectMunka` ()  BEGIN

  select `munk`.`id` AS `ID`,
  `csop`.`nev` AS `csoport`,
  `munk`.`felvetelIdeje` AS `datum`,
  `munk`.`megrendelo` AS `megrendelo`,
  `munk`.`megnevezes` AS `megnevezes`,
  concat(`vez`.`veznev`,' ',`vez`.`kernev`) AS `felelos`,
  concat(`uzl`.`veznev`,' ',`uzl`.`kernev`) AS `uzletkoto`,
  `mt`.`nev` AS `tipus`,
  `ms`.`nev` AS `statusz`,
  `munk`.`osszkoltseg` AS `osszkoltseg`, 
  `munk`.`felelos` AS `felid`, 
  `munk`.`uzletkoto` AS `uzlid` 
  from `munkak` `munk` left join `csoportok` `csop` on `munk`.`csoport` = `csop`.`id` 
  left join `emberek` `vez` on `munk`.`felelos` = `vez`.`id` 
  left join `emberek` `uzl` on `munk`.`uzletkoto` = `uzl`.`id` 
  left join `munkatipus` `mt` on `munk`.`tipus` = `mt`.`id`
  left join `munkastatus` `ms` on `munk`.`status` = `ms`.`id`;

END$$

DROP PROCEDURE IF EXISTS `selectMyCsop`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectMyCsop` (IN `v_id` INT(11))  BEGIN

 SELECT cs.id, cs.nev, cs.leiras, fel.nev, cst.lastCheck, cs.lastMsg  
 FROM csoporttagsag AS cst 
 LEFT JOIN csoportok AS cs ON cs.id=cst.id 
 LEFT JOIN feladatok AS fel ON cst.feladat=fel.id 
 WHERE cst.tag=v_id;

END$$

DROP PROCEDURE IF EXISTS `selectMyMunka`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectMyMunka` (IN `v_id` INT(11))  BEGIN

  select `munk`.`id` AS `ID`,
  `csop`.`nev` AS `csoport`,
  `munk`.`felvetelIdeje` AS `datum`,
  `munk`.`megrendelo` AS `megrendelo`,
  `munk`.`megnevezes` AS `megnevezes`,
  concat(`vez`.`veznev`,' ',`vez`.`kernev`) AS `felelos`,
  concat(`uzl`.`veznev`,' ',`uzl`.`kernev`) AS `uzletkoto`,
  `mt`.`nev` AS `tipus`,
  `ms`.`nev` AS `statusz`,
  `munk`.`osszkoltseg` AS `osszkoltseg`, 
  `munk`.`felelos` AS `felid`,
  `munk`.`uzletkoto` AS `uzlid` 
  from `munkak` `munk` join `csoportok` `csop` on`munk`.`csoport` = `csop`.`id` 
  join `emberek` `vez` on `munk`.`felelos` = `vez`.`id` 
  join `emberek` `uzl` on `munk`.`uzletkoto` = `uzl`.`id` 
  join `munkatipus` `mt` on `munk`.`tipus` = `mt`.`id`
  join `munkastatus` `ms` on `munk`.`status` = `ms`.`id`
  where `munk`.`id` in (select distinct munk.id from  `munkak` `munk` 
                    left join `csoportok` `csop` on csop.id=munk.csoport 
                    left join csoporttagsag cst on csop.id=cst.id 
where `munk`.`felelos`=v_id or `munk`.`uzletkoto`=v_id or `cst`.`tag`=v_id);

END$$

DROP PROCEDURE IF EXISTS `selectPoz`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectPoz` ()  BEGIN

SELECT id, nev, leiras FROM poziciok;

END$$

DROP PROCEDURE IF EXISTS `selectTagsag`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectTagsag` (IN `v_id` INT(11))  BEGIN

   SELECT a.id AS csopid,b.id AS tagid,(c.id=3,d.nev,c.nev) as feladat,
	CONCAT(b.veznev, ' ', b.kernev) AS nev,c.nev AS poz
	FROM csoporttagsag a JOIN emberek b ON a.tag = b.id
	JOIN poziciok c ON b.pozicio = c.id JOIN feladatok d ON a.feladat = d.id
    WHERE a.id=v_id;

END$$

DROP PROCEDURE IF EXISTS `selectTipus`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectTipus` ()  BEGIN

  SELECT id, nev, leiras FROM munkatipus;

END$$

DROP PROCEDURE IF EXISTS `selectUjMunka`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `selectUjMunka` ()  BEGIN

  select `munk`.`id` AS `ID`,
  `csop`.`nev` AS `csoport`,
  `munk`.`felvetelIdeje` AS `datum`,
  `munk`.`megrendelo` AS `megrendelo`,
  `munk`.`megnevezes` AS `megnevezes`,
  concat(`vez`.`veznev`,' ',`vez`.`kernev`) AS `felelos`,
  concat(`uzl`.`veznev`,' ',`uzl`.`kernev`) AS `uzletkoto`,
  `mt`.`nev` AS `tipus`,
  `ms`.`nev` AS `statusz`,
  `munk`.`osszkoltseg` AS `osszkoltseg`, 
  `munk`.`felelos` AS `felid`, 
  `munk`.`uzletkoto` AS `uzlid` 
  from `munkak` `munk` left join `csoportok` `csop` on `munk`.`csoport` = `csop`.`id` 
  left join `emberek` `vez` on `munk`.`felelos` = `vez`.`id` 
  left join `emberek` `uzl` on `munk`.`uzletkoto` = `uzl`.`id` 
  left join `munkatipus` `mt` on `munk`.`tipus` = `mt`.`id`
  left join `munkastatus` `ms` on `munk`.`status` = `ms`.`id`
  where `munk`.`felelos`=0;

END$$

DROP PROCEDURE IF EXISTS `updateCsop`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateCsop` (IN `v_nev` VARCHAR(11), IN `v_leiras` VARCHAR(11), IN `v_id` INT(11))  BEGIN

 update csoportok set nev=v_nev, leiras=v_leiras where id=v_id;

END$$

DROP PROCEDURE IF EXISTS `updateEmber`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateEmber` (IN `v_megrendelo` VARCHAR(100), IN `v_megnevezes` VARCHAR(100), IN `v_felid` INT(11), IN `v_tipid` INT(11), IN `v_id` INT(11))  BEGIN

 update munkak set megrendelo=v_megrendelo, megnevezes=v_megnevezes, felelos=v_felid, tipus=v_tipid where id=v_id;

END$$

DROP PROCEDURE IF EXISTS `updateTimecheck`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `updateTimecheck` (IN `v_csop` INT(11), IN `v_id` INT(11))  BEGIN

UPDATE csoporttagsag SET lastCheck = CURRENT_TIMESTAMP 
WHERE id=v_csop AND tag=v_id;

END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `csoportok`
--

DROP TABLE IF EXISTS `csoportok`;
CREATE TABLE IF NOT EXISTS `csoportok` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nev` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `leiras` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `lastMsg` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `csoportok`
--

INSERT INTO `csoportok` (`id`, `nev`, `leiras`, `lastMsg`) VALUES
(0, 'sfdh', 'gsgd', '2017-04-11 16:42:57'),
(2, '1', '1', '2018-03-25 18:58:07'),
(3, 'Trainees', 'gyakorlattalanok', '2016-10-18 11:05:01'),
(5, 'valami3', 'valakikikiksrjhsdfj', '2016-10-18 11:05:01'),
(6, 'csoport4', 'leiras4', '2016-10-18 11:05:01'),
(10, 'adg', 'adg', '2016-10-18 11:05:01'),
(11, 'hf', 'aag', '2016-10-18 11:05:01'),
(12, 'sdg', 'hfsdfhdstfg', '2018-03-23 17:07:50');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `csoporttagsag`
--

DROP TABLE IF EXISTS `csoporttagsag`;
CREATE TABLE IF NOT EXISTS `csoporttagsag` (
  `id` int(11) NOT NULL,
  `tag` int(11) NOT NULL,
  `feladat` int(11) NOT NULL,
  `lastCheck` timestamp NULL DEFAULT NULL,
  KEY `csoportkulcs` (`id`),
  KEY `tagkulcs` (`tag`),
  KEY `feladatkulcs` (`feladat`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `csoporttagsag`
--

INSERT INTO `csoporttagsag` (`id`, `tag`, `feladat`, `lastCheck`) VALUES
(2, 14, 4, NULL),
(2, 16, 4, NULL),
(2, 15, 4, '2018-03-25 18:58:58'),
(2, 22, 4, '2018-03-25 18:59:03'),
(11, 15, 4, '2018-03-23 17:07:39'),
(11, 17, 4, NULL),
(11, 18, 4, NULL),
(12, 15, 4, '2018-03-25 17:54:42');

-- --------------------------------------------------------

--
-- A nézet helyettes szerkezete `csoport_osszestag`
-- (Lásd alább az aktuális nézetet)
--
DROP VIEW IF EXISTS `csoport_osszestag`;
CREATE TABLE IF NOT EXISTS `csoport_osszestag` (
`id` int(11)
,`csoport` varchar(100)
,`tagok` text
);

-- --------------------------------------------------------

--
-- A nézet helyettes szerkezete `csoport_summ`
-- (Lásd alább az aktuális nézetet)
--
DROP VIEW IF EXISTS `csoport_summ`;
CREATE TABLE IF NOT EXISTS `csoport_summ` (
`id` int(11)
,`csoport` varchar(100)
,`tagok` text
,`feladat` text
);

-- --------------------------------------------------------

--
-- A nézet helyettes szerkezete `csoport_tagsag`
-- (Lásd alább az aktuális nézetet)
--
DROP VIEW IF EXISTS `csoport_tagsag`;
CREATE TABLE IF NOT EXISTS `csoport_tagsag` (
`id` int(11)
,`csoport` varchar(100)
,`nev` varchar(201)
,`feladat` varchar(100)
);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `emberek`
--

DROP TABLE IF EXISTS `emberek`;
CREATE TABLE IF NOT EXISTS `emberek` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `veznev` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `kernev` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `pozicio` int(11) NOT NULL,
  `oraber` int(100) NOT NULL,
  `kezdes` date DEFAULT NULL,
  `allowcreate` int(1) NOT NULL DEFAULT '0',
  `hashpw` varchar(300) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`veznev`,`kernev`),
  KEY `poziciokulcs` (`pozicio`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `emberek`
--

INSERT INTO `emberek` (`id`, `veznev`, `kernev`, `pozicio`, `oraber`, `kezdes`, `allowcreate`, `hashpw`) VALUES
(14, 'ysf', 'af', 1, 0, '2016-09-24', 0, '7110eda4d09e062aa5e4a390b0a572ac0d2c0220'),
(15, 'Weinber', 'Róbert', 1, 2500, '2016-09-22', 1, '7110eda4d09e062aa5e4a390b0a572ac0d2c0220'),
(16, 'A', 'Krissz', 2, 1500, '2016-09-24', 0, '7110eda4d09e062aa5e4a390b0a572ac0d2c0220'),
(17, 'Kivagy', 'te', 1, 55, '2016-01-01', 0, '7110eda4d09e062aa5e4a390b0a572ac0d2c0220'),
(18, 'Kis', 'Dávid', 1, 1500, '2016-09-24', 0, '7110eda4d09e062aa5e4a390b0a572ac0d2c0220'),
(19, 'Erős', 'Pista', 1, 3000, '2018-03-21', 0, '7110eda4d09e062aa5e4a390b0a572ac0d2c0220'),
(22, 'Erős', 'István', 1, 3000, '2018-03-22', 0, '7110eda4d09e062aa5e4a390b0a572ac0d2c0220'),
(24, 'dhff', 'dyfj', 1, 7, '2018-03-23', 0, '7110eda4d09e062aa5e4a390b0a572ac0d2c0220'),
(1, 'admin', 'admin', 3, 666, '1999-01-01', 1, '7110eda4d09e062aa5e4a390b0a572ac0d2c0220');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `feladatok`
--

DROP TABLE IF EXISTS `feladatok`;
CREATE TABLE IF NOT EXISTS `feladatok` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nev` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `leiras` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `feladatok`
--

INSERT INTO `feladatok` (`id`, `nev`, `leiras`) VALUES
(4, 'Feljesztés', 'Program írása'),
(5, 'Betanítás', 'Magyarázás');

-- --------------------------------------------------------

--
-- A nézet helyettes szerkezete `fizetesek`
-- (Lásd alább az aktuális nézetet)
--
DROP VIEW IF EXISTS `fizetesek`;
CREATE TABLE IF NOT EXISTS `fizetesek` (
`id` int(11)
,`nev` varchar(201)
,`ev` int(4)
,`honap` int(2)
,`osszeg` decimal(65,0)
);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `koltseg`
--

DROP TABLE IF EXISTS `koltseg`;
CREATE TABLE IF NOT EXISTS `koltseg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `munka` int(11) NOT NULL,
  `osszeg` int(100) NOT NULL,
  `tipus` int(11) NOT NULL,
  `alany` int(11) DEFAULT NULL,
  `timestamp` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tipuskulcs` (`tipus`),
  KEY `munkakulcs` (`munka`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `koltseg`
--

INSERT INTO `koltseg` (`id`, `munka`, `osszeg`, `tipus`, `alany`, `timestamp`) VALUES
(5, 2, 2500, 3, NULL, NULL),
(6, 2, 5500, 4, NULL, NULL),
(7, 2, 3000, 3, 19, '2018-03-21 21:45:27'),
(12, 2, 3000, 3, 19, '2018-03-21 21:54:31'),
(13, 2, 3000, 3, 19, '2018-03-21 21:56:27'),
(14, 2, 7500, 3, 19, '2018-03-21 22:26:17'),
(15, 7, 7500, 3, 15, '2018-03-23 17:54:37'),
(16, 8, 5000, 3, 15, '2018-03-23 17:54:47'),
(17, 9, 27500, 3, 15, '2018-03-23 17:54:51');

--
-- Eseményindítók `koltseg`
--
DROP TRIGGER IF EXISTS `koltseg_delete`;
DELIMITER $$
CREATE TRIGGER `koltseg_delete` AFTER DELETE ON `koltseg` FOR EACH ROW UPDATE munkak a left join (SELECT munka,sum(osszeg) as osszeg FROM koltseg GROUP BY munka) b on a.id=b.munka
SET a.osszkoltseg = IFNULL(b.osszeg,0) WHERE a.id>0
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `koltseg_insert`;
DELIMITER $$
CREATE TRIGGER `koltseg_insert` AFTER INSERT ON `koltseg` FOR EACH ROW UPDATE munkak a left join (SELECT munka,sum(osszeg) as osszeg FROM koltseg GROUP BY munka) b on a.id=b.munka
SET a.osszkoltseg = IFNULL(b.osszeg,0) WHERE a.id>0
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `koltseg_update`;
DELIMITER $$
CREATE TRIGGER `koltseg_update` AFTER UPDATE ON `koltseg` FOR EACH ROW UPDATE munkak a left join (SELECT munka,sum(osszeg) as osszeg FROM koltseg GROUP BY munka) b on a.id=b.munka
SET a.osszkoltseg = IFNULL(b.osszeg,0) WHERE a.id>0
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- A nézet helyettes szerkezete `koltsegek`
-- (Lásd alább az aktuális nézetet)
--
DROP VIEW IF EXISTS `koltsegek`;
CREATE TABLE IF NOT EXISTS `koltsegek` (
`ID` int(11)
,`munka_ID` int(11)
,`munka` varchar(402)
,`tipus` varchar(100)
,`osszeg` int(100)
);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `koltsegtipus`
--

DROP TABLE IF EXISTS `koltsegtipus`;
CREATE TABLE IF NOT EXISTS `koltsegtipus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nev` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `leiras` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `koltsegtipus`
--

INSERT INTO `koltsegtipus` (`id`, `nev`, `leiras`) VALUES
(3, 'órabér', 'Fizetés'),
(4, 'reklám', 'Marketing');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `munkak`
--

DROP TABLE IF EXISTS `munkak`;
CREATE TABLE IF NOT EXISTS `munkak` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `csoport` int(11) DEFAULT NULL,
  `felvetelIdeje` date NOT NULL,
  `megrendelo` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `felelos` int(11) NOT NULL,
  `tipus` int(11) NOT NULL,
  `uzletkoto` int(11) NOT NULL,
  `osszkoltseg` int(100) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '1',
  `megnevezes` varchar(300) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `csopkulcs` (`csoport`),
  KEY `tipkulcs` (`tipus`),
  KEY `statuskulcs_idx` (`status`),
  KEY `uzletkotokulcs_idx` (`uzletkoto`),
  KEY `felkulcs_idx` (`felelos`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `munkak`
--

INSERT INTO `munkak` (`id`, `csoport`, `felvetelIdeje`, `megrendelo`, `felelos`, `tipus`, `uzletkoto`, `osszkoltseg`, `status`, `megnevezes`) VALUES
(2, 2, '2016-09-23', 'NASA', 15, 3, 16, 24500, 2, 'feladat'),
(7, 2, '2017-01-01', 'vkik', 15, 3, 16, 7500, 2, 'feladat2'),
(8, 11, '2017-10-07', 'qGF', 19, 3, 16, 5000, 2, 'ASG'),
(9, 3, '2018-03-23', 'AvenD', 15, 3, 18, 27500, 2, 'viewbox építés'),
(10, 10, '2018-02-23', 'gstg', 15, 3, 15, 0, 2, 'asdgd');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `munkastatus`
--

DROP TABLE IF EXISTS `munkastatus`;
CREATE TABLE IF NOT EXISTS `munkastatus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nev` varchar(45) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `leiras` varchar(45) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `munkastatus`
--

INSERT INTO `munkastatus` (`id`, `nev`, `leiras`) VALUES
(1, 'Kiosztásra vár', NULL),
(2, 'Folyamatban', NULL),
(3, 'Tesztelés', NULL),
(4, 'Kész', NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `munkatipus`
--

DROP TABLE IF EXISTS `munkatipus`;
CREATE TABLE IF NOT EXISTS `munkatipus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nev` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `leiras` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `munkatipus`
--

INSERT INTO `munkatipus` (`id`, `nev`, `leiras`) VALUES
(3, 'Új szoftver', 'Tervezés, kivitelezés.'),
(4, 'Ellenőrzés', 'Felülvizsgálat.');

-- --------------------------------------------------------

--
-- A nézet helyettes szerkezete `munka_bontott`
-- (Lásd alább az aktuális nézetet)
--
DROP VIEW IF EXISTS `munka_bontott`;
CREATE TABLE IF NOT EXISTS `munka_bontott` (
`id` int(11)
,`megrendelo` varchar(100)
,`megnevezes` varchar(300)
,`csoport` varchar(100)
,`csoporttagok` text
,`felelos` varchar(201)
,`tipus` varchar(100)
,`uzletkoto` varchar(201)
,`osszkoltseg` int(100)
,`status` varchar(45)
,`bontott_koltseg` varchar(191)
);

-- --------------------------------------------------------

--
-- A nézet helyettes szerkezete `munka_bontott_reszletek`
-- (Lásd alább az aktuális nézetet)
--
DROP VIEW IF EXISTS `munka_bontott_reszletek`;
CREATE TABLE IF NOT EXISTS `munka_bontott_reszletek` (
`id` int(11)
,`megrendelo` varchar(100)
,`megnevezes` varchar(300)
,`csoport` varchar(100)
,`csoporttagok` text
,`felelos` varchar(201)
,`tipus` varchar(100)
,`uzletkoto` varchar(201)
,`osszkoltseg` int(100)
,`status` varchar(45)
,`bontott_koltseg_reszletek` text
);

-- --------------------------------------------------------

--
-- A nézet helyettes szerkezete `munka_reszletek`
-- (Lásd alább az aktuális nézetet)
--
DROP VIEW IF EXISTS `munka_reszletek`;
CREATE TABLE IF NOT EXISTS `munka_reszletek` (
`ID` int(11)
,`csoport` varchar(100)
,`datum` date
,`megrendelo` varchar(100)
,`felelos` varchar(201)
,`uzletkoto` varchar(201)
,`tipus` varchar(100)
,`osszkoltseg` int(100)
,`megnevezes` varchar(300)
);

-- --------------------------------------------------------

--
-- A nézet helyettes szerkezete `munka_summ`
-- (Lásd alább az aktuális nézetet)
--
DROP VIEW IF EXISTS `munka_summ`;
CREATE TABLE IF NOT EXISTS `munka_summ` (
`id` int(11)
,`megrendelo` varchar(100)
,`megnevezes` varchar(300)
,`csoport` varchar(100)
,`csoporttagok` text
,`felelos` varchar(201)
,`tipus` varchar(100)
,`uzletkoto` varchar(201)
,`osszkoltseg` int(100)
,`status` varchar(45)
);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `poziciok`
--

DROP TABLE IF EXISTS `poziciok`;
CREATE TABLE IF NOT EXISTS `poziciok` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nev` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `leiras` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `poziciok`
--

INSERT INTO `poziciok` (`id`, `nev`, `leiras`) VALUES
(1, 'Fejlesztő', 'Csinál valamit'),
(2, 'Kereskedő', 'Munkát szerez'),
(3, 'Admin', 'Adminisztrátor');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `uzenetek`
--

DROP TABLE IF EXISTS `uzenetek`;
CREATE TABLE IF NOT EXISTS `uzenetek` (
  `csopID` int(11) NOT NULL,
  `tagID` int(11) NOT NULL,
  `msg` varchar(500) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
  `times` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `uzenetek`
--

INSERT INTO `uzenetek` (`csopID`, `tagID`, `msg`, `times`) VALUES
(2, 4, 'teszt', '2016-10-18 10:38:04'),
(2, 4, 'valami', '2016-10-18 10:38:04'),
(2, 5, 'ezmegaz', '2016-10-18 10:57:20'),
(2, 5, 'xxcnycf', '2016-10-18 11:09:55'),
(2, 5, 'fgjmfhkkmfhgfggm', '2016-10-18 11:27:39'),
(2, 5, 'jzfmjgg', '2016-10-18 11:34:43'),
(2, 4, 'djjhjhdgjdgj', '2016-10-18 11:43:24'),
(2, 4, 'gghaehghr', '2016-10-18 11:44:05'),
(2, 4, 'asgdf', '2016-10-18 11:58:08'),
(2, 4, 'qaef', '2016-10-18 11:58:48'),
(2, 4, 'sg', '2016-10-18 12:00:24'),
(2, 4, 'sfhfs', '2016-10-18 12:00:40'),
(2, 4, 'vissza', '2017-04-11 08:52:43'),
(2, 4, 'xfn', '2017-04-11 09:02:40'),
(2, 4, 'jggjgj', '2017-04-11 09:06:45'),
(2, 4, 'd', '2017-04-11 09:07:50'),
(2, 4, 'dj', '2017-04-11 09:12:42'),
(2, 4, 'fk', '2017-04-11 09:12:46'),
(2, 4, 'dfj', '2017-04-11 09:13:24'),
(2, 4, 'cvnh', '2017-04-11 09:14:05'),
(2, 4, 'swz', '2017-04-11 09:20:08'),
(2, 4, 'fh', '2017-04-11 09:20:11'),
(2, 4, 'kúkúp', '2017-09-30 14:53:13'),
(2, 15, 'cdgfjdts', '2018-03-23 16:56:31'),
(2, 15, 'bgmcgj', '2018-03-23 16:56:33'),
(2, 15, 'dgjdfhd', '2018-03-23 16:56:35'),
(2, 15, 'dfjhdfgh', '2018-03-23 16:56:36'),
(2, 15, 'dfhdfghd', '2018-03-23 16:56:37'),
(2, 15, 'dgjdghdf', '2018-03-23 16:56:40'),
(2, 15, 'dfhdfghdf', '2018-03-23 16:56:42'),
(2, 15, 'dfjdfgdfgdsfg', '2018-03-23 16:56:44'),
(2, 15, 'dfgdf', '2018-03-23 16:56:57'),
(12, 15, 'hkghkhk', '2018-03-23 17:07:50'),
(2, 15, 'fdgj', '2018-03-25 18:46:10'),
(2, 22, 'valami', '2018-03-25 18:50:58'),
(2, 22, 'hmm', '2018-03-25 18:54:55'),
(2, 15, '123', '2018-03-25 18:57:59'),
(2, 22, 'valami', '2018-03-25 18:58:07');

--
-- Eseményindítók `uzenetek`
--
DROP TRIGGER IF EXISTS `lastMsgUpdate`;
DELIMITER $$
CREATE TRIGGER `lastMsgUpdate` AFTER INSERT ON `uzenetek` FOR EACH ROW UPDATE csoportok SET lastMsg = new.times WHERE id = NEW.csopID
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- `csoport_osszestag` nézet struktúrája táblaként exportálva
--
DROP TABLE IF EXISTS `csoport_osszestag`;
CREATE TABLE IF NOT EXISTS `csoport_osszestag`(
    `id` int(11) DEFAULT '0',
    `csoport` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `tagok` text COLLATE utf8mb4_hungarian_ci DEFAULT NULL
);

-- --------------------------------------------------------

--
-- `csoport_summ` nézet struktúrája táblaként exportálva
--
DROP TABLE IF EXISTS `csoport_summ`;
CREATE TABLE IF NOT EXISTS `csoport_summ`(
    `id` int(11) DEFAULT '0',
    `csoport` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `tagok` text COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `feladat` text COLLATE utf8mb4_hungarian_ci DEFAULT NULL
);

-- --------------------------------------------------------

--
-- `csoport_tagsag` nézet struktúrája táblaként exportálva
--
DROP TABLE IF EXISTS `csoport_tagsag`;
CREATE TABLE IF NOT EXISTS `csoport_tagsag`(
    `id` int(11) DEFAULT '0',
    `csoport` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `nev` varchar(201) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `feladat` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL
);

-- --------------------------------------------------------

--
-- `fizetesek` nézet struktúrája táblaként exportálva
--
DROP TABLE IF EXISTS `fizetesek`;
CREATE TABLE IF NOT EXISTS `fizetesek`(
    `id` int(11) NOT NULL DEFAULT '0',
    `nev` varchar(201) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `ev` int(4) DEFAULT NULL,
    `honap` int(2) DEFAULT NULL,
    `osszeg` decimal(65,0) DEFAULT NULL
);

-- --------------------------------------------------------

--
-- `koltsegek` nézet struktúrája táblaként exportálva
--
DROP TABLE IF EXISTS `koltsegek`;
CREATE TABLE IF NOT EXISTS `koltsegek`(
    `ID` int(11) NOT NULL DEFAULT '0',
    `munka_ID` int(11) NOT NULL DEFAULT '0',
    `munka` varchar(402) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `tipus` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `osszeg` int(100) NOT NULL
);

-- --------------------------------------------------------

--
-- `munka_bontott` nézet struktúrája táblaként exportálva
--
DROP TABLE IF EXISTS `munka_bontott`;
CREATE TABLE IF NOT EXISTS `munka_bontott`(
    `id` int(11) DEFAULT '0',
    `megrendelo` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `megnevezes` varchar(300) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `csoport` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `csoporttagok` text COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `felelos` varchar(201) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `tipus` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `uzletkoto` varchar(201) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `osszkoltseg` int(100) NOT NULL DEFAULT '0',
    `status` varchar(45) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `bontott_koltseg` varchar(191) COLLATE utf8mb4_hungarian_ci DEFAULT NULL
);

-- --------------------------------------------------------

--
-- `munka_bontott_reszletek` nézet struktúrája táblaként exportálva
--
DROP TABLE IF EXISTS `munka_bontott_reszletek`;
CREATE TABLE IF NOT EXISTS `munka_bontott_reszletek`(
    `id` int(11) DEFAULT '0',
    `megrendelo` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `megnevezes` varchar(300) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `csoport` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `csoporttagok` text COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `felelos` varchar(201) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `tipus` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `uzletkoto` varchar(201) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `osszkoltseg` int(100) NOT NULL DEFAULT '0',
    `status` varchar(45) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `bontott_koltseg_reszletek` text COLLATE utf8mb4_hungarian_ci DEFAULT NULL
);

-- --------------------------------------------------------

--
-- `munka_reszletek` nézet struktúrája táblaként exportálva
--
DROP TABLE IF EXISTS `munka_reszletek`;
CREATE TABLE IF NOT EXISTS `munka_reszletek`(
    `ID` int(11) NOT NULL DEFAULT '0',
    `csoport` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `datum` date NOT NULL,
    `megrendelo` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `felelos` varchar(201) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `uzletkoto` varchar(201) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `tipus` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `osszkoltseg` int(100) NOT NULL DEFAULT '0',
    `megnevezes` varchar(300) COLLATE utf8mb4_hungarian_ci DEFAULT NULL
);

-- --------------------------------------------------------

--
-- `munka_summ` nézet struktúrája táblaként exportálva
--
DROP TABLE IF EXISTS `munka_summ`;
CREATE TABLE IF NOT EXISTS `munka_summ`(
    `id` int(11) NOT NULL DEFAULT '0',
    `megrendelo` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `megnevezes` varchar(300) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `csoport` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `csoporttagok` text COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `felelos` varchar(201) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `tipus` varchar(100) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `uzletkoto` varchar(201) COLLATE utf8mb4_hungarian_ci DEFAULT NULL,
    `osszkoltseg` int(100) NOT NULL DEFAULT '0',
    `status` varchar(45) COLLATE utf8mb4_hungarian_ci DEFAULT NULL
);

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `csoporttagsag`
--
ALTER TABLE `csoporttagsag`
  ADD CONSTRAINT `csoportkulcs` FOREIGN KEY (`id`) REFERENCES `csoportok` (`id`),
  ADD CONSTRAINT `feladatkulcs` FOREIGN KEY (`feladat`) REFERENCES `feladatok` (`id`);

--
-- Megkötések a táblához `koltseg`
--
ALTER TABLE `koltseg`
  ADD CONSTRAINT `munkakulcs` FOREIGN KEY (`munka`) REFERENCES `munkak` (`id`),
  ADD CONSTRAINT `tipuskulcs` FOREIGN KEY (`tipus`) REFERENCES `koltsegtipus` (`id`);

--
-- Megkötések a táblához `munkak`
--
ALTER TABLE `munkak`
  ADD CONSTRAINT `csopkulcs` FOREIGN KEY (`csoport`) REFERENCES `csoportok` (`id`),
  ADD CONSTRAINT `statuskulcs` FOREIGN KEY (`status`) REFERENCES `munkastatus` (`id`),
  ADD CONSTRAINT `tipkulcs` FOREIGN KEY (`tipus`) REFERENCES `munkatipus` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
