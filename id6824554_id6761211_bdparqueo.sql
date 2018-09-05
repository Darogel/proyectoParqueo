-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 05-09-2018 a las 02:11:12
-- Versión del servidor: 10.1.31-MariaDB
-- Versión de PHP: 7.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `id6824554_id6761211_bdparqueo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `administrador`
--

CREATE TABLE `administrador` (
  `id_admin` int(11) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `usuario` varchar(45) NOT NULL,
  `clave` varchar(45) NOT NULL,
  `external_id` varchar(100) NOT NULL,
  `estado` tinyint(4) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `administrador`
--

INSERT INTO `administrador` (`id_admin`, `nombres`, `usuario`, `clave`, `external_id`, `estado`, `created_at`, `update_at`) VALUES
(1, 'Byron', 'mors', '1234', '7cc8fa2f-c376-40e2-91b1-f5f6a1ac3678', 1, '2018-07-18 04:10:34', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `parqueadero`
--

CREATE TABLE `parqueadero` (
  `id_parqueadero` int(11) NOT NULL,
  `id_Admin` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `external_id` varchar(100) NOT NULL,
  `coordenada_x` double NOT NULL,
  `coordenada_y` double NOT NULL,
  `precio_hora` double NOT NULL,
  `numero_plazas` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `estado` tinyint(4) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `parqueadero`
--

INSERT INTO `parqueadero` (`id_parqueadero`, `id_Admin`, `nombre`, `external_id`, `coordenada_x`, `coordenada_y`, `precio_hora`, `numero_plazas`, `created_at`, `updated_at`, `estado`) VALUES
(1, 1, 'Parqueadero', 'fba0768b-8afc-4338-82aa-92db19b8b620', -4.008647256, -79.2034037856, 0.25, 10, '2018-07-22 22:40:39', '2018-08-31 05:26:53', 0),
(2, 1, 'Parqueadero Publico', '6f4717a8-30d4-4329-a448-8ab149b9faf3', -4.00351, -79.200831, 0.25, 10, '2018-07-23 04:11:00', '2018-08-31 05:28:04', 1),
(3, 1, 'Azuay', '957aa2ee-0b5d-4087-a320-47892a3fad3d', -4.0048335, -79.211932, 0.25, 10, '2018-07-22 23:15:31', '2018-08-31 05:28:16', 1),
(4, 1, 'San Antonio', '83a6c142-a134-41d9-b461-d37a69c928da', -4.0040273, -79.202404, 0.25, 10, '2018-07-22 23:17:45', '2018-08-31 05:29:08', 1),
(5, 1, 'Patio Village', '777d07d0-8d44-4402-9740-44b82c52df4c', -4.0027127, -79.2114909, 0.25, 10, '2018-08-20 17:20:56', '2018-08-31 05:29:24', 1),
(6, 1, 'San Francisco', '0756e0c7-7d70-4779-92cf-9b2f7f09ec3a', -4.0086472, -79.2034037, 0.25, 10, '2018-08-20 19:26:17', '2018-08-31 05:27:43', 1),
(15, 1, 'UTI', 'e32fa185-239f-4a08-82a7-92ced9eee6de', -4.0335184475407, -79.20301722362638, 2, 3, '2018-08-31 21:02:37', '2018-08-31 21:02:37', 1),
(16, 1, 'Jean', 'ccdfacb7-075f-44b3-a8aa-afa59fba34c6', -3.9891942, -79.2144194, 0.45, 15, '2018-09-02 05:03:16', '2018-09-02 05:03:16', 1),
(18, 1, 'Sauces Parking ', '87aec7fb-284a-48e2-8bd1-0afa740d39d0', -3.93919, -79.22439666666668, 0.25, 10, '2018-09-02 06:38:28', '2018-09-02 06:38:28', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `plaza`
--

CREATE TABLE `plaza` (
  `id_plaza` int(11) NOT NULL,
  `id_Parqueadero` int(11) NOT NULL,
  `numero_puesto` int(11) NOT NULL,
  `external_id` varchar(100) NOT NULL,
  `tipo` varchar(50) DEFAULT NULL,
  `estado` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `plaza`
--

INSERT INTO `plaza` (`id_plaza`, `id_Parqueadero`, `numero_puesto`, `external_id`, `tipo`, `estado`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 'a06b1908-2461-4e38-b041-25718f8aae86', 'con techo', 1, '2018-07-22 23:35:35', '0000-00-00 00:00:00'),
(2, 1, 2, 'caa44d7d-d40e-4f46-aca0-df6b2d801de9', 'Sin Techo', 1, '2018-08-17 23:30:59', '0000-00-00 00:00:00'),
(3, 1, 10, 'd47c532e-b40c-42b0-8a45-1fe94b4c2cfb', 'con techo', 1, '2018-08-20 18:25:56', '2018-08-20 18:25:56'),
(4, 1, 5, 'b8bea6d6-3a97-47dd-abf3-810c8cc76a0a', 'sin techo', 1, '2018-08-20 18:36:37', '2018-08-20 18:36:37'),
(6, 1, 8, '9b881f60-51fa-49a2-a8bd-3fdbe518ae35', 'con techo', 1, '2018-08-27 02:09:29', '2018-08-27 02:09:29'),
(7, 1, 12, '9e0ef3a8-8f49-4c4a-8ded-ac7ea9216507', 'con techo', 1, '2018-08-27 02:58:56', '2018-08-27 02:58:56'),
(8, 1, 24, '0a93b184-0e72-4a12-9230-840c621302de', 'con techo', 1, '2018-08-27 03:01:07', '2018-08-27 03:01:07'),
(9, 1, 6, '7297ef18-1d3d-4c67-a7f7-fb7b288f44d4', 'Con techo', 1, '2018-08-27 03:09:11', '2018-08-27 03:09:11'),
(10, 1, 8, 'd067ca9b-8acb-40a4-a453-5c47ef4bb5c5', 'Con techo', 1, '2018-09-01 01:15:21', '2018-09-01 01:15:21'),
(11, 16, 1, '73edf6d7-7ab9-4d6b-af16-bbae7bae4d4a', 'Con techo', 1, '2018-09-02 04:57:36', '2018-09-02 05:41:49'),
(12, 1, 1, '5d8b8af8-ea51-456b-b6e5-af4458164d16', 'Con techo', 1, '2018-09-02 05:04:40', '2018-09-02 05:04:40'),
(13, 16, 9, 'f5aba2cc-ac91-4870-be43-0e090d4e815b', 'Sin techo', 1, '2018-09-02 05:32:45', '2018-09-02 05:40:57'),
(14, 1, 0, 'a7bbde57-ce7e-40a3-9e6e-5b294bce82ca', 'Seleccionar', 1, '2018-09-02 05:41:18', '2018-09-02 05:41:18'),
(15, 18, 1, 'b79844c2-272e-49c8-a651-d12296b4511b', 'Con techo', 1, '2018-09-02 13:06:52', '2018-09-02 13:06:52'),
(16, 15, 5, '48deb88f-428e-46c8-939f-76ad26663f4b', 'Con techo', 1, '2018-09-03 02:19:35', '2018-09-03 02:19:35'),
(17, 16, 1, '0b07ce8e-ec7c-4561-8675-0aff9529b993', 'Con techo', 1, '2018-09-04 22:18:21', '2018-09-04 22:18:21');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservacion`
--

CREATE TABLE `reservacion` (
  `id_reservacion` int(11) NOT NULL,
  `id_vehiculo` int(11) NOT NULL,
  `id_plaza` int(11) NOT NULL,
  `external_id` varchar(100) NOT NULL,
  `hora_entrada` time NOT NULL,
  `hora_salida` time NOT NULL,
  `estado` tinyint(4) NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `reservacion`
--

INSERT INTO `reservacion` (`id_reservacion`, `id_vehiculo`, `id_plaza`, `external_id`, `hora_entrada`, `hora_salida`, `estado`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 'dc230a23-2bc3-4d36-9b32-f92ed7c42453', '01:05:00', '02:08:00', 1, '2018-08-25 20:11:19', '2018-08-25 20:11:19'),
(2, 1, 1, '6ec73e3d-0cb0-42a6-9795-11282318a250', '01:32:00', '02:28:00', 1, '2018-08-26 02:53:51', '2018-08-26 02:53:51'),
(3, 3, 3, '9a2ad8b7-f372-4728-bec1-71b123721ed6', '12:30:00', '15:30:00', 0, '2018-08-27 01:39:11', '2018-08-27 01:55:54'),
(4, 4, 2, '5dcc9134-9f62-4647-99b5-6700973e5787', '12:00:00', '23:00:00', 1, '2018-08-27 02:01:03', '2018-08-27 02:01:03'),
(5, 8, 15, '1642049d-4891-4b2a-a30f-1eb8cb422e7d', '00:07:00', '15:07:00', 1, '2018-09-02 13:07:31', '2018-09-02 13:07:31'),
(6, 3, 16, '5fdd1fd4-e9ed-4c53-81f6-dabce2027fdc', '03:30:00', '06:00:00', 1, '2018-09-03 15:09:47', '2018-09-03 15:09:47'),
(7, 3, 16, '4e9a08b2-6eea-43e2-975d-f1ba262dc311', '03:30:00', '06:00:00', 1, '2018-09-03 15:09:47', '2018-09-03 15:09:47');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `correo` varchar(150) NOT NULL,
  `external_id` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `estado` int(11) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `correo`, `external_id`, `created_at`, `updated_at`, `estado`) VALUES
(1, 'byron1.jim84@gmail.com', 'aa17640e-8c90-46fd-ba8f-06698580467b', '2018-07-22 23:42:56', '2018-08-25 20:18:12', 1),
(8, 'byron.jim84@gmail.com', '3ca0fed1-326f-476a-8134-d491a6865621', '2018-08-26 03:53:05', NULL, 1),
(11, 'darwin_tu_flakito1996@hotmail.com', '9a56faf3-b37c-44fe-a1ca-e25d1a88fb7f', '2018-08-26 07:13:20', NULL, 1),
(12, 'yulisatanis@hotmail.com', '0adc5f3f-d830-45ca-b2ca-82ad65a2d1c7', '2018-09-01 01:18:09', NULL, 1),
(13, 'cjeanmleon@hotmail.com', '9b8c4cfc-8183-45c9-92e3-22de2549bab7', '2018-09-02 04:35:14', NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vehiculo`
--

CREATE TABLE `vehiculo` (
  `id_vehiculo` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `placa` varchar(10) NOT NULL,
  `external_id` varchar(100) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `estado` tinyint(4) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `vehiculo`
--

INSERT INTO `vehiculo` (`id_vehiculo`, `id_usuario`, `placa`, `external_id`, `created_at`, `updated_at`, `estado`) VALUES
(1, 1, 'PCO-4453', '56b99ba1-c6e2-40a3-87e1-cac62802d143', '2018-07-23 05:00:54', '2018-07-23 05:00:54', 1),
(2, 1, 'LBD-5869', 'e205cc58-6cf1-4d5f-bfa1-46b7d66f0aa8', '2018-07-23 00:02:26', '2018-07-23 00:10:09', 0),
(3, 11, 'OPQ-1245', 'b8683e70-dd88-4285-a580-d0a1f709c3e6', '2018-08-26 18:40:48', '2018-08-26 18:40:48', 1),
(4, 11, 'PCA-6789', '1692ce9d-0184-446b-9496-d6c7191b60cb', '2018-08-26 21:10:21', '2018-08-26 21:10:21', 1),
(5, 13, 'PLM-2345', 'a832d525-31e4-4931-a52e-42b42e756ad7', '2018-09-02 04:42:27', '2018-09-02 04:42:27', 1),
(6, 13, 'JAL-1345', '703c5e91-d2e8-431f-a21f-e2f8c67df064', '2018-09-02 05:06:55', '2018-09-02 05:06:55', 1),
(7, 13, 'JCK-1736', '77af2626-968d-444b-80a7-d25308e361ec', '2018-09-02 05:33:49', '2018-09-02 05:33:49', 1),
(8, 8, 'LAG-960', '1a94421d-bc58-462d-9abe-1f82021643d8', '2018-09-02 12:53:42', '2018-09-02 12:53:42', 1),
(9, 12, 'ABC-1244', '38456a87-2b9c-4c30-86f3-35f870650221', '2018-09-04 22:19:17', '2018-09-04 22:19:17', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `administrador`
--
ALTER TABLE `administrador`
  ADD PRIMARY KEY (`id_admin`),
  ADD UNIQUE KEY `id_Admin_UNIQUE` (`id_admin`);

--
-- Indices de la tabla `parqueadero`
--
ALTER TABLE `parqueadero`
  ADD PRIMARY KEY (`id_parqueadero`),
  ADD UNIQUE KEY `id_Parqueader_UNIQUE` (`id_parqueadero`),
  ADD KEY `id_Admin_idx` (`id_Admin`);

--
-- Indices de la tabla `plaza`
--
ALTER TABLE `plaza`
  ADD PRIMARY KEY (`id_plaza`),
  ADD KEY `id_Parqueadero_idx` (`id_Parqueadero`);

--
-- Indices de la tabla `reservacion`
--
ALTER TABLE `reservacion`
  ADD PRIMARY KEY (`id_reservacion`),
  ADD UNIQUE KEY `id_reservacion_UNIQUE` (`id_reservacion`),
  ADD KEY `id_Vehiculo_idx` (`id_vehiculo`),
  ADD KEY `id_Plaza_idx` (`id_plaza`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `id_usuario_UNIQUE` (`id_usuario`),
  ADD UNIQUE KEY `correo` (`correo`);

--
-- Indices de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD PRIMARY KEY (`id_vehiculo`),
  ADD UNIQUE KEY `id_vehiculo_UNIQUE` (`id_vehiculo`),
  ADD UNIQUE KEY `placa_UNIQUE` (`placa`),
  ADD KEY `id_Usuario_idx` (`id_usuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `administrador`
--
ALTER TABLE `administrador`
  MODIFY `id_admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `parqueadero`
--
ALTER TABLE `parqueadero`
  MODIFY `id_parqueadero` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `plaza`
--
ALTER TABLE `plaza`
  MODIFY `id_plaza` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `reservacion`
--
ALTER TABLE `reservacion`
  MODIFY `id_reservacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  MODIFY `id_vehiculo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `parqueadero`
--
ALTER TABLE `parqueadero`
  ADD CONSTRAINT `id_Admin` FOREIGN KEY (`id_Admin`) REFERENCES `administrador` (`id_admin`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `plaza`
--
ALTER TABLE `plaza`
  ADD CONSTRAINT `id_Parqueadero` FOREIGN KEY (`id_Parqueadero`) REFERENCES `parqueadero` (`id_parqueadero`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `reservacion`
--
ALTER TABLE `reservacion`
  ADD CONSTRAINT `id_Plaza` FOREIGN KEY (`id_plaza`) REFERENCES `plaza` (`id_plaza`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `id_Vehiculo` FOREIGN KEY (`id_vehiculo`) REFERENCES `vehiculo` (`id_vehiculo`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `vehiculo`
--
ALTER TABLE `vehiculo`
  ADD CONSTRAINT `id_Usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
