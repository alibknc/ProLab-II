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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `kullaniciAdi` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `sifre` varchar(45) DEFAULT NULL,
  `abonelik` varchar(45) DEFAULT NULL,
  `ulke` varchar(45) DEFAULT NULL,
  `adminlik` tinyint DEFAULT NULL,
  `takipci` int DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Ali Bakınç','admin@musicly.com','abc123','PREMIUM','TUR',1,0),(2,'Ahmet','ahmet@gmail.com','ahmet_123','PREMIUM','TUR',0,2),(3,'Mehmet','mehmet@hotmail.com','1907mehmet','NORMAL','TUR',0,0),(4,'Ayşe','ayse@gmail.com','ayse!01','PREMIUM','TUR',0,1),(5,'Fatma','fatma@hotmail.com','deneme','NORMAL','TUR',0,0),(6,'Kemal','kemal@hotmail.com','123456','PREMIUM','TUR',0,2),(10,'Murat Kaya','murat@gmail.com','kayit','PREMIUM','TUR',0,2),(11,'Selin','selin@hotmail.com','selinnn','PREMIUM','TUR',0,1),(12,'Fatih','fatih@gmail.com','Fatih65','NORMAL','TUR',0,0),(13,'Hüseyin','huseyin@hotmail.com','ornekSifre','NORMAL','TUR',0,0),(14,'Arda','arda@gmail.com','fenerbahce','NORMAL','TUR',0,0),(15,'Serhat Kaya','serhat@outlook.com','yeni','NORMAL','TUR',0,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
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
