-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: localhost    Database: itstornei
-- ------------------------------------------------------
-- Server version	9.7.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
SET @MYSQLDUMP_TEMP_LOG_BIN = @@SESSION.SQL_LOG_BIN;
SET @@SESSION.SQL_LOG_BIN= 0;

--
-- GTID state at the beginning of the backup 
--

SET @@GLOBAL.GTID_PURGED=/*!80000 '+'*/ 'd86f9c1c-c619-11f1-9178-2800af4a2531:1-36';

--
-- Table structure for table `componente_squadra`
--

DROP TABLE IF EXISTS `componente_squadra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `componente_squadra` (
  `id_componente_squadra` int NOT NULL AUTO_INCREMENT,
  `id_squadra` int NOT NULL,
  `id_utente` int NOT NULL,
  `data_inserimento` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_componente_squadra`),
  UNIQUE KEY `uq_componente_squadra` (`id_squadra`,`id_utente`),
  KEY `fk_componente_utente` (`id_utente`),
  CONSTRAINT `fk_componente_squadra` FOREIGN KEY (`id_squadra`) REFERENCES `squadra` (`id_squadra`) ON DELETE CASCADE,
  CONSTRAINT `fk_componente_utente` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id_utente`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `impostazione`
--

DROP TABLE IF EXISTS `impostazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `impostazione` (
  `id_impostazione` int NOT NULL AUTO_INCREMENT,
  `valore` int NOT NULL,
  PRIMARY KEY (`id_impostazione`),
  CONSTRAINT `chk_valore` CHECK ((`valore` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `iscrizione`
--

DROP TABLE IF EXISTS `iscrizione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `iscrizione` (
  `id_iscrizione` int NOT NULL AUTO_INCREMENT,
  `id_torneo` int NOT NULL,
  `id_utente` int NOT NULL,
  `data_iscrizione` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_iscrizione`),
  UNIQUE KEY `uq_iscrizione_utente_torneo` (`id_torneo`,`id_utente`),
  KEY `fk_iscrizione_utente` (`id_utente`),
  CONSTRAINT `fk_iscrizione_torneo` FOREIGN KEY (`id_torneo`) REFERENCES `torneo` (`id_torneo`) ON DELETE CASCADE,
  CONSTRAINT `fk_iscrizione_utente` FOREIGN KEY (`id_utente`) REFERENCES `utente` (`id_utente`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `squadra`
--

DROP TABLE IF EXISTS `squadra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `squadra` (
  `id_squadra` int NOT NULL AUTO_INCREMENT,
  `id_torneo` int NOT NULL,
  `nome` varchar(100) NOT NULL,
  `id_creatore` int NOT NULL,
  `data_creazione` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_squadra`),
  UNIQUE KEY `uq_nome_squadra_torneo` (`id_torneo`,`nome`),
  KEY `fk_squadra_creatore` (`id_creatore`),
  CONSTRAINT `fk_squadra_creatore` FOREIGN KEY (`id_creatore`) REFERENCES `utente` (`id_utente`),
  CONSTRAINT `fk_squadra_torneo_comune` FOREIGN KEY (`id_torneo`) REFERENCES `torneo` (`id_torneo`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `tornei_con_stato`
--

DROP TABLE IF EXISTS `tornei_con_stato`;
/*!50001 DROP VIEW IF EXISTS `tornei_con_stato`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `tornei_con_stato` AS SELECT 
 1 AS `id_torneo`,
 1 AS `nome`,
 1 AS `descrizione`,
 1 AS `data_inizio_iscrizioni`,
 1 AS `data_fine_iscrizioni`,
 1 AS `data_inizio_torneo`,
 1 AS `data_fine_torneo`,
 1 AS `cancellato`,
 1 AS `modalita`,
 1 AS `max_partecipanti`,
 1 AS `max_squadre`,
 1 AS `componenti`,
 1 AS `numero_squadre`,
 1 AS `numero_partecipanti`,
 1 AS `stato`,
 1 AS `iscrizioni_aperte`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `tornei_disponibilita`
--

DROP TABLE IF EXISTS `tornei_disponibilita`;
/*!50001 DROP VIEW IF EXISTS `tornei_disponibilita`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `tornei_disponibilita` AS SELECT 
 1 AS `id_torneo`,
 1 AS `nome`,
 1 AS `descrizione`,
 1 AS `modalita`,
 1 AS `iscrizioni_aperte`,
 1 AS `posti_residui`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `torneo`
--

DROP TABLE IF EXISTS `torneo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `torneo` (
  `id_torneo` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `descrizione` varchar(2000) DEFAULT NULL,
  `data_inizio_iscrizioni` date NOT NULL,
  `data_fine_iscrizioni` date NOT NULL,
  `data_inizio_torneo` date NOT NULL,
  `data_fine_torneo` date NOT NULL,
  `cancellato` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_torneo`),
  CONSTRAINT `chk_iscr_torneo` CHECK ((`data_fine_iscrizioni` <= `data_inizio_torneo`)),
  CONSTRAINT `chk_iscrizioni` CHECK ((`data_inizio_iscrizioni` <= `data_fine_iscrizioni`)),
  CONSTRAINT `chk_torneo` CHECK ((`data_inizio_torneo` <= `data_fine_torneo`))
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `torneo_individuale`
--

DROP TABLE IF EXISTS `torneo_individuale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `torneo_individuale` (
  `id_torneo_individuale` int NOT NULL AUTO_INCREMENT,
  `id_torneo` int NOT NULL,
  `max_partecipanti` int NOT NULL,
  PRIMARY KEY (`id_torneo_individuale`),
  UNIQUE KEY `id_torneo` (`id_torneo`),
  CONSTRAINT `fk_individuale_torneo` FOREIGN KEY (`id_torneo`) REFERENCES `torneo` (`id_torneo`) ON DELETE CASCADE,
  CONSTRAINT `chk_max_partecipanti` CHECK ((`max_partecipanti` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `torneo_squadra`
--

DROP TABLE IF EXISTS `torneo_squadra`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `torneo_squadra` (
  `id_torneo_squadra` int NOT NULL AUTO_INCREMENT,
  `id_torneo` int NOT NULL,
  `max_squadre` int NOT NULL,
  `componenti` int NOT NULL,
  PRIMARY KEY (`id_torneo_squadra`),
  UNIQUE KEY `id_torneo` (`id_torneo`),
  CONSTRAINT `fk_squadra_torneo` FOREIGN KEY (`id_torneo`) REFERENCES `torneo` (`id_torneo`) ON DELETE CASCADE,
  CONSTRAINT `chk_componenti` CHECK ((`componenti` > 0)),
  CONSTRAINT `chk_max_squadre` CHECK ((`max_squadre` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `id_utente` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password_hash` varchar(255) NOT NULL,
  `ruolo` enum('UTENTE','ADMIN') NOT NULL DEFAULT 'UTENTE',
  `data_registrazione` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_utente`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `tornei_con_stato`
--

/*!50001 DROP VIEW IF EXISTS `tornei_con_stato`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `tornei_con_stato` AS select `t`.`id_torneo` AS `id_torneo`,`t`.`nome` AS `nome`,`t`.`descrizione` AS `descrizione`,`t`.`data_inizio_iscrizioni` AS `data_inizio_iscrizioni`,`t`.`data_fine_iscrizioni` AS `data_fine_iscrizioni`,`t`.`data_inizio_torneo` AS `data_inizio_torneo`,`t`.`data_fine_torneo` AS `data_fine_torneo`,`t`.`cancellato` AS `cancellato`,'INDIVIDUALE' AS `modalita`,`ti`.`max_partecipanti` AS `max_partecipanti`,NULL AS `max_squadre`,NULL AS `componenti`,NULL AS `numero_squadre`,(select count(0) from `iscrizione` `i` where (`i`.`id_torneo` = `t`.`id_torneo`)) AS `numero_partecipanti`,(case when (`t`.`cancellato` = true) then 'cancellato' when (curdate() > `t`.`data_fine_torneo`) then 'chiuso' when (curdate() >= `t`.`data_inizio_torneo`) then 'in_corso' else 'aperto' end) AS `stato`,(case when ((`t`.`cancellato` = false) and (curdate() between `t`.`data_inizio_iscrizioni` and `t`.`data_fine_iscrizioni`) and ((select count(0) from `iscrizione` `i` where (`i`.`id_torneo` = `t`.`id_torneo`)) < `ti`.`max_partecipanti`)) then true else false end) AS `iscrizioni_aperte` from (`torneo` `t` join `torneo_individuale` `ti` on((`ti`.`id_torneo` = `t`.`id_torneo`))) union all select `t`.`id_torneo` AS `id_torneo`,`t`.`nome` AS `nome`,`t`.`descrizione` AS `descrizione`,`t`.`data_inizio_iscrizioni` AS `data_inizio_iscrizioni`,`t`.`data_fine_iscrizioni` AS `data_fine_iscrizioni`,`t`.`data_inizio_torneo` AS `data_inizio_torneo`,`t`.`data_fine_torneo` AS `data_fine_torneo`,`t`.`cancellato` AS `cancellato`,'SQUADRE' AS `modalita`,NULL AS `max_partecipanti`,`ts`.`max_squadre` AS `max_squadre`,`ts`.`componenti` AS `componenti`,(select count(0) from `squadra` `s` where (`s`.`id_torneo` = `t`.`id_torneo`)) AS `numero_squadre`,(select count(0) from (`componente_squadra` `cs` join `squadra` `s` on((`s`.`id_squadra` = `cs`.`id_squadra`))) where (`s`.`id_torneo` = `t`.`id_torneo`)) AS `numero_partecipanti`,(case when (`t`.`cancellato` = true) then 'cancellato' when (curdate() > `t`.`data_fine_torneo`) then 'chiuso' when (curdate() >= `t`.`data_inizio_torneo`) then 'in_corso' else 'aperto' end) AS `stato`,(case when ((`t`.`cancellato` = false) and (curdate() between `t`.`data_inizio_iscrizioni` and `t`.`data_fine_iscrizioni`) and ((select count(0) from `squadra` `s` where (`s`.`id_torneo` = `t`.`id_torneo`)) < `ts`.`max_squadre`)) then true else false end) AS `iscrizioni_aperte` from (`torneo` `t` join `torneo_squadra` `ts` on((`ts`.`id_torneo` = `t`.`id_torneo`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `tornei_disponibilita`
--

/*!50001 DROP VIEW IF EXISTS `tornei_disponibilita`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `tornei_disponibilita` AS select `t`.`id_torneo` AS `id_torneo`,`t`.`nome` AS `nome`,`t`.`descrizione` AS `descrizione`,`t`.`modalita` AS `modalita`,`t`.`iscrizioni_aperte` AS `iscrizioni_aperte`,(case when (`t`.`modalita` = 'INDIVIDUALE') then greatest((`t`.`max_partecipanti` - `t`.`numero_partecipanti`),0) when (`t`.`modalita` = 'SQUADRE') then greatest((`t`.`max_squadre` - `t`.`numero_squadre`),0) end) AS `posti_residui` from `tornei_con_stato` `t` where (`t`.`cancellato` = 0) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
SET @@SESSION.SQL_LOG_BIN = @MYSQLDUMP_TEMP_LOG_BIN;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-14 14:36:08
