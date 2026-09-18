-- ========================================================
-- Base de datos para la evidencia GA7-220501096-AA3-EV01
-- Misma estructura que `tienda_barrio` (proyecto real),
-- pero en una base independiente para no tocar tus datos reales.
-- ========================================================

CREATE DATABASE IF NOT EXISTS `tienda_barrio_aa3`
    DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `tienda_barrio_aa3`;

-- --------------------------------------------------------
-- Tabla: usuarios
-- --------------------------------------------------------
DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `nombre_usuario` VARCHAR(50) NOT NULL,
  `contrasena` VARCHAR(255) NOT NULL,
  `tipo_usuario` ENUM('ADMINISTRADOR','VENDEDOR') NOT NULL DEFAULT 'VENDEDOR',
  `estado` TINYINT(1) DEFAULT '1',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `nombre_usuario` (`nombre_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Usuario admin de prueba (contraseña: admin123, ya encriptada con BCrypt)
INSERT INTO `usuarios` (`nombre_usuario`, `contrasena`, `tipo_usuario`, `estado`) VALUES
('admin', '$2a$10$tkFcIytutlsyhqjXfQ5Wqedux4X1YVLSEn3o.35yxm7WUFU1xh216', 'ADMINISTRADOR', 1),
('vendedor1', '$2a$10$Kd5mbqCwPCcw94JJ0LzfjuTjxb1JJbzbTnunp59XWF48j7rRHOkYW', 'VENDEDOR', 1);

-- --------------------------------------------------------
-- Tabla: proveedores
-- --------------------------------------------------------
DROP TABLE IF EXISTS `proveedores`;
CREATE TABLE `proveedores` (
  `id_proveedor` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `direccion` TEXT,
  `telefono` VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (`id_proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `proveedores` (`nombre`, `direccion`, `telefono`) VALUES
('Bodega Mayorista Granero El Campesino', 'Arjona Plaza Principal', '3003054137'),
('Bodega Mayorista El Cazador', 'Arjona Barrio Las Margaritas Carretera Troncal', '3148468990'),
('Supertiendas Olimpica', 'Cartagena Bolivar Barrio La Castellana', '3114564576');

-- --------------------------------------------------------
-- Tabla: productos
-- --------------------------------------------------------
DROP TABLE IF EXISTS `productos`;
CREATE TABLE `productos` (
  `id_producto` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NOT NULL,
  `descripcion` TEXT,
  `precio` DECIMAL(10,2) NOT NULL,
  `stock` INT DEFAULT '0',
  `stock_minimo` INT DEFAULT '5',
  PRIMARY KEY (`id_producto`),
  KEY `idx_producto_nombre` (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `productos` (`nombre`, `descripcion`, `precio`, `stock`, `stock_minimo`) VALUES
('Arroz Diana', 'Bolsa de Arroz de 500 gramos', 2000.00, 508, 30),
('Aceite Medalla De Oro 3 Litros', 'Botella Aceite de 3 litros', 20000.00, 84, 10),
('Frijol Rojo', 'Bolsa de 500 gramos', 2000.00, 196, 10),
('Cafe Sachet Bolsa', 'Bolsa por noventa papeletas', 300.00, 879, 10),
('Atun Bari', 'Lata de atun de 250 gramos', 5000.00, 86, 5),
('Salsa de Tomate', 'Bolsa de 250 gramos', 2000.00, 13, 5);

-- --------------------------------------------------------
-- Tabla: ventas
-- --------------------------------------------------------
DROP TABLE IF EXISTS `ventas`;
CREATE TABLE `ventas` (
  `id_venta` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `total` DECIMAL(10,2) DEFAULT NULL,
  `id_usuario` INT DEFAULT NULL,
  PRIMARY KEY (`id_venta`),
  KEY `id_usuario` (`id_usuario`),
  CONSTRAINT `ventas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Tabla: detalle_ventas
-- --------------------------------------------------------
DROP TABLE IF EXISTS `detalle_ventas`;
CREATE TABLE `detalle_ventas` (
  `id_detalle_venta` INT NOT NULL AUTO_INCREMENT,
  `id_venta` INT DEFAULT NULL,
  `id_producto` INT DEFAULT NULL,
  `cantidad` INT DEFAULT NULL,
  `precio_unitario` DECIMAL(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_detalle_venta`),
  KEY `id_venta` (`id_venta`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `detalle_ventas_ibfk_1` FOREIGN KEY (`id_venta`) REFERENCES `ventas` (`id_venta`),
  CONSTRAINT `detalle_ventas_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Tabla: compras
-- --------------------------------------------------------
DROP TABLE IF EXISTS `compras`;
CREATE TABLE `compras` (
  `id_compra` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `id_proveedor` INT DEFAULT NULL,
  PRIMARY KEY (`id_compra`),
  KEY `id_proveedor` (`id_proveedor`),
  CONSTRAINT `compras_ibfk_1` FOREIGN KEY (`id_proveedor`) REFERENCES `proveedores` (`id_proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------
-- Tabla: detalle_compras
-- --------------------------------------------------------
DROP TABLE IF EXISTS `detalle_compras`;
CREATE TABLE `detalle_compras` (
  `id_detalle_compra` INT NOT NULL AUTO_INCREMENT,
  `id_compra` INT DEFAULT NULL,
  `id_producto` INT DEFAULT NULL,
  `cantidad` INT DEFAULT NULL,
  `precio_unitario` DECIMAL(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_detalle_compra`),
  KEY `id_compra` (`id_compra`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `detalle_compras_ibfk_1` FOREIGN KEY (`id_compra`) REFERENCES `compras` (`id_compra`),
  CONSTRAINT `detalle_compras_ibfk_2` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
