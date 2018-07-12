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
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id_admin`),
  UNIQUE KEY `id_Admin_UNIQUE` (`id_admin`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
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
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `estado` tinyint(4) NOT NULL,
  PRIMARY KEY (`id_parqueadero`),
  UNIQUE KEY `id_Parqueader_UNIQUE` (`id_parqueadero`),
  KEY `id_Admin_idx` (`id_Admin`),
  CONSTRAINT `id_Admin` FOREIGN KEY (`id_Admin`) REFERENCES `administrador` (`id_admin`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parqueadero`
--

LOCK TABLES `parqueadero` WRITE;
/*!40000 ALTER TABLE `parqueadero` DISABLE KEYS */;
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
  `estado` tinyint(4) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id_plaza`),
  KEY `id_Parqueadero_idx` (`id_Parqueadero`),
  CONSTRAINT `id_Parqueadero` FOREIGN KEY (`id_Parqueadero`) REFERENCES `parqueadero` (`id_parqueadero`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plaza`
--

LOCK TABLES `plaza` WRITE;
/*!40000 ALTER TABLE `plaza` DISABLE KEYS */;
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
  `estado` tinyint(4) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
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
  `usuario` varchar(50) NOT NULL,
  `clave` varchar(50) NOT NULL,
  `external_id` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `id_usuario_UNIQUE` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
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
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id_vehiculo`),
  UNIQUE KEY `id_vehiculo_UNIQUE` (`id_vehiculo`),
  UNIQUE KEY `placa_UNIQUE` (`placa`),
  KEY `id_Usuario_idx` (`id_usuario`),
  CONSTRAINT `id_Usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiculo`
--

LOCK TABLES `vehiculo` WRITE;
/*!40000 ALTER TABLE `vehiculo` DISABLE KEYS */;
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

-- Dump completed on 2018-07-12 15:18:04
