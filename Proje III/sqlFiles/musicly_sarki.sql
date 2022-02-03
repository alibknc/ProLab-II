-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: musicly
-- ------------------------------------------------------
-- Server version	8.0.23

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

--
-- Table structure for table `sarki`
--

DROP TABLE IF EXISTS `sarki`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sarki` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `sarkiAdi` varchar(45) DEFAULT NULL,
  `tarih` varchar(45) DEFAULT NULL,
  `sanatciID` int DEFAULT NULL,
  `albumID` int DEFAULT NULL,
  `tur` varchar(45) DEFAULT NULL,
  `sure` varchar(45) DEFAULT NULL,
  `dinlenme` int DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sarki`
--

LOCK TABLES `sarki` WRITE;
/*!40000 ALTER TABLE `sarki` DISABLE KEYS */;
INSERT INTO `sarki` VALUES (2,'Gel','2015',1,1,'Pop','04:47',12),(3,'Sarışın','2015',1,1,'Pop','05:01',3),(4,'Bir Hadise Var','2015',1,1,'Pop','04:40',13),(5,'Ahu','2015',1,1,'Pop','03:34',3),(6,'Şampiyon','2017',2,2,'Pop','04:07',2),(7,'Sıfır Tolerans','2017',2,2,'Pop','03:47',6),(8,'Farkımız Var','2017',2,2,'Pop','03:24',4),(9,'Blue Moon','1956',10,5,'Jazz','03:27',3),(10,'Royal Garden Blues','2017',11,3,'Jazz','04:22',6),(11,'Kuş Masalı','2013',12,4,'Jazz','04:15',6),(12,'Gök Nerede','2015',1,1,'Pop','05:11',0),(13,'Für Elise','1867',13,7,'Klasik','02:55',0),(14,'Turkish March','1809',13,7,'Klasik','03:47',2),(17,'Moonlight Sonata','1801',13,7,'Klasik','15:00',0),(18,'Egmont','1787',13,7,'Klasik','08:06',12),(19,'Olsun','2021',2,8,'Pop','04:27',0),(22,'Küçük Bir Yol','2021',2,8,'Pop','04:41',1),(23,'Yalan Dünya','2008',27,11,'Jazz','06:13',0),(24,'Sıradan Bir Gün','2008',27,11,'Jazz','04:22',0),(25,'Geçti Dost Kervanı','2008',27,11,'Jazz','02:55',0),(26,'So What','1959',25,12,'Jazz','09:05',0),(27,'Blue in Green','1959',25,12,'Jazz','05:29',0),(28,'Jeep\'s Blues','1994',26,13,'Jazz','02:55',0),(29,'Castle Rock','1994',26,13,'Jazz','02:50',1),(30,'I. Sivas','2012',28,14,'Klasik','05:32',0),(31,'II. Hopa','2012',28,14,'Klasik','04:33',0),(32,'III. Ankara','2012',28,14,'Klasik','10:01',0),(33,'Carmen Fantasy','2016',29,15,'Klasik','10:57',1),(34,'Csárdás','2016',29,15,'Klasik','04:46',0),(35,'Concertino op.107','2016',29,15,'Klasik','07:47',0);
/*!40000 ALTER TABLE `sarki` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-19 12:32:26
