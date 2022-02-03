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
-- Table structure for table `sanatci`
--

DROP TABLE IF EXISTS `sanatci`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sanatci` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `sanatciAdi` varchar(45) DEFAULT NULL,
  `ulkesi` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sanatci`
--

LOCK TABLES `sanatci` WRITE;
/*!40000 ALTER TABLE `sanatci` DISABLE KEYS */;
INSERT INTO `sanatci` VALUES (1,'Mabel Matiz','TR'),(2,'Hadise','TR'),(10,'Billie Holiday','USA'),(11,'Louis Armstrong','USA'),(12,'Birsen Tezer','TR'),(13,'Ludvig van Beethoven','DE'),(18,'Oğuzhan Koç','TR'),(19,'Sibel Can','TR'),(20,'Yıldız Tilbe','TR'),(21,'İbrahim Tatlıses','TR'),(22,'Inna','RO'),(23,'Katy Perry','USA'),(24,'Adele','GB'),(25,'Miles Davis','USA'),(26,'Johnny Hodges','USA'),(27,'Jülide Özçelik','TR'),(28,'Fazıl Say','TR'),(29,'Bülent Evcil','TR');
/*!40000 ALTER TABLE `sanatci` ENABLE KEYS */;
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
