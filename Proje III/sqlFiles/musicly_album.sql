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
-- Table structure for table `album`
--

DROP TABLE IF EXISTS `album`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `album` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `albumAdi` varchar(45) DEFAULT NULL,
  `sanatciID` int DEFAULT NULL,
  `tarih` varchar(45) DEFAULT NULL,
  `tur` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album`
--

LOCK TABLES `album` WRITE;
/*!40000 ALTER TABLE `album` DISABLE KEYS */;
INSERT INTO `album` VALUES (1,'Gök Nerede',1,'2015','Pop'),(2,'Şampiyon',2,'2017','Pop'),(3,'The Night Clubs',11,'2017','Jazz'),(4,'İkinci Cihan',12,'2013','Jazz'),(5,'Solitude',10,'1956','Jazz'),(7,'Beethoven Seçmeler',13,'2021','Klasik'),(8,'Aşka Kapandım',2,'2021','Pop'),(11,'Jazz İstanbul',27,'2008','Jazz'),(12,'Kind of Blue',25,'1959','Jazz'),(13,'Jazz Masters 35',26,'1994','Jazz'),(14,'4 Şehir',28,'2012','Klasik'),(15,'The Virtuoso Flute',29,'2016','Klasik');
/*!40000 ALTER TABLE `album` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-19 12:32:25
