CREATE DATABASE  IF NOT EXISTS `parqueo` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `parqueo`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: parqueo
-- ------------------------------------------------------
-- Server version	5.7.18-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrador` (
  `id_admin` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(100) NOT NULL,
  `usuario` varchar(45) NOT NULL,
  `clave` varchar(45) NOT NULL,
  `external_id` varchar(100) NOT NULL,
  `estado` tinyint(4) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_admin`),
  UNIQUE KEY `id_Admin_UNIQUE` (`id_admin`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
INSERT INTO `administrador` VALUES (1,'Byron','mors','1234','7cc8fa2f-c376-40e2-91b1-f5f6a1ac3678',1,'2018-07-18 04:10:34','0000-00-00 00:00:00');
/*!40000 ALTER TABLE `administrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parqueadero`
--

DROP TABLE IF EXISTS `parqueadero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parqueadero` (
  `id_parqueadero` int(11) NOT NULL AUTO_INCREMENT,
  `id_Admin` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `external_id` varchar(100) NOT NULL,
  `coordenada_x` double NOT NULL,
  `coordenada_y` double NOT NULL,
  `precio_hora` double NOT NULL,
  `numero_plazas` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `estado` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_parqueadero`),
  UNIQUE KEY `id_Parqueader_UNIQUE` (`id_parqueadero`),
  KEY `id_Admin_idx` (`id_Admin`),
  CONSTRAINT `id_Admin` FOREIGN KEY (`id_Admin`) REFERENCES `administrador` (`id_admin`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parqueadero`
--

LOCK TABLES `parqueadero` WRITE;
/*!40000 ALTER TABLE `parqueadero` DISABLE KEYS */;
INSERT INTO `parqueadero` VALUES (1,1,'Parqueadero1_editado4','fba0768b-8afc-4338-82aa-92db19b8b620',1.1,1.1,0.25,10,'2018-07-22 22:40:39','2018-07-22 23:15:49',0),(2,1,'parqueadero2','6f4717a8-30d4-4329-a448-8ab149b9faf3',1.1,1.1,0.25,10,'2018-07-23 04:11:00','2018-07-23 04:11:00',1),(3,1,'parqueadero3','957aa2ee-0b5d-4087-a320-47892a3fad3d',1.1,1.1,0.25,10,'2018-07-22 23:15:31','0000-00-00 00:00:00',1),(4,1,'parqueadero4','83a6c142-a134-41d9-b461-d37a69c928da',1.1,1.1,0.25,10,'2018-07-22 23:17:45','0000-00-00 00:00:00',1);
/*!40000 ALTER TABLE `parqueadero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plaza`
--

DROP TABLE IF EXISTS `plaza`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `plaza` (
  `id_plaza` int(11) NOT NULL AUTO_INCREMENT,
  `id_Parqueadero` int(11) NOT NULL,
  `numero_puesto` int(11) NOT NULL,
  `external_id` varchar(100) NOT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `estado` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_plaza`),
  KEY `id_Parqueadero_idx` (`id_Parqueadero`),
  CONSTRAINT `id_Parqueadero` FOREIGN KEY (`id_Parqueadero`) REFERENCES `parqueadero` (`id_parqueadero`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plaza`
--

LOCK TABLES `plaza` WRITE;
/*!40000 ALTER TABLE `plaza` DISABLE KEYS */;
INSERT INTO `plaza` VALUES (1,1,1,'a06b1908-2461-4e38-b041-25718f8aae86','con techo',1,'2018-07-22 23:35:35','0000-00-00 00:00:00');
/*!40000 ALTER TABLE `plaza` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservacion`
--

DROP TABLE IF EXISTS `reservacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reservacion` (
  `id_reservacion` int(11) NOT NULL AUTO_INCREMENT,
  `id_vehiculo` int(11) NOT NULL,
  `id_plaza` int(11) NOT NULL,
  `external_id` varchar(100) NOT NULL,
  `hora_entrada` time NOT NULL,
  `hora_salida` time NOT NULL,
  `estado` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_reservacion`),
  UNIQUE KEY `id_reservacion_UNIQUE` (`id_reservacion`),
  KEY `id_Vehiculo_idx` (`id_vehiculo`),
  KEY `id_Plaza_idx` (`id_plaza`),
  CONSTRAINT `id_Plaza` FOREIGN KEY (`id_plaza`) REFERENCES `plaza` (`id_plaza`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_Vehiculo` FOREIGN KEY (`id_vehiculo`) REFERENCES `vehiculo` (`id_vehiculo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservacion`
--

LOCK TABLES `reservacion` WRITE;
/*!40000 ALTER TABLE `reservacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombres` varchar(100) NOT NULL,
  `external_id` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `estado` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `id_usuario_UNIQUE` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Byron J','aa17640e-8c90-46fd-ba8f-06698580467b','2018-07-22 23:42:56','2018-07-22 23:52:57',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehiculo`
--

DROP TABLE IF EXISTS `vehiculo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehiculo` (
  `id_vehiculo` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `placa` varchar(10) NOT NULL,
  `external_id` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `estado` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_vehiculo`),
  UNIQUE KEY `id_vehiculo_UNIQUE` (`id_vehiculo`),
  UNIQUE KEY `placa_UNIQUE` (`placa`),
  KEY `id_Usuario_idx` (`id_usuario`),
  CONSTRAINT `id_Usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiculo`
--

LOCK TABLES `vehiculo` WRITE;
/*!40000 ALTER TABLE `vehiculo` DISABLE KEYS */;
INSERT INTO `vehiculo` VALUES (1,1,'PCO-4453','56b99ba1-c6e2-40a3-87e1-cac62802d143','2018-07-23 05:00:54','2018-07-23 05:00:54',1),(2,1,'LBD-5869','e205cc58-6cf1-4d5f-bfa1-46b7d66f0aa8','2018-07-23 00:02:26','2018-07-23 00:10:09',0);
/*!40000 ALTER TABLE `vehiculo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'parqueo'
--

--
-- Dumping routines for database 'parqueo'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-08-05 17:20:18
