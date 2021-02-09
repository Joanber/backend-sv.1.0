INSERT INTO roles(nombre) VALUES ('ROLE_ADMIN');
INSERT INTO roles(nombre) VALUES ('ROLE_USER');

INSERT INTO personas(cedula,nombre,apellido,telefono,direccion,email,fecha) VALUES ('0106385065','ADMIN','ADMIN','4106861','AV ISABEL LA CATÓLICA , CUENCA','ipca_cuenca@hotmail.es','2000-01-03');

INSERT INTO usuarios(activo,username,password,persona_id) VALUES (true,'ADMIN','$2a$10$AZjMeiDltJamehwcFrcIvubqnIZ7Y113CzTHEjVhjuYDuRLWN1aTO',1);

INSERT INTO usuarios_roles(usuario_id,rol_id) VALUES (1,1);
			
INSERT INTO categorias(nombre) VALUES ('SNACKS');
INSERT INTO categorias(nombre) VALUES ('BEBIDAS');
INSERT INTO categorias(nombre) VALUES ('GALLETAS');
INSERT INTO categorias(nombre) VALUES ('LÁCTEOS');
INSERT INTO categorias(nombre) VALUES ('CHICLES');

INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567890','K-CHITOS ORIGINALES GRANDE 76 G','KACHITOS ORIGINALES GRANDE A 30 CENTAVOS',0.30,0.25,10,5,1);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567891','NACHOS DE QUESO PEQUEÑO 45 G','NACHOS DE QUESO PEQUEÑO A 55 CENTAVOS',0.55,0.50,10,5,1);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567892','DORITOS DE QUESO PEQUEÑO 42 G','DORITOS DE QUESO PEQUEÑO A 50 CENTAVOS',0.50,0.45,10,5,1);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567893','TORTOLINES PEQUEÑO 45 G','TORTOLINES PEQUEÑO A 55 CENTAVOS',0.55,0.50,10,5,1);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567894','TORTOLINES GRANDE 150 G','TORTOLINES GRANDE A UN DOLAR CON 50 CENTAVOS',1.50,1.45,10,5,1);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567895','DE TODITO PEQUEÑO 42 G','DE TODITO PEQUEÑO A 50 CENTAVOS',0.50,0.45,10,5,1);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567896','PAPAS GRANDE 76 G','PAPAS GRANDE A 50 CENTAVOS',0.50,0.45,10,5,1);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567897','PAPAS PEQUEÑO 40 G','PAPAS PEQUEÑO A 30 CENTAVOS',0.30,0.25,10,5,1);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567899','GALLETAS SALTICAS ORIGINALES 70 G','GALLETAS SALTICAS ORIGINALES A 40 CENTAVOS',0.40,0.35,10,5,3);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567811','GALLETA RICAS 67 G','GALLETA RICAS A 45 CENTAVOS',0.45,0.40,10,5,3);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567821','GALLETA OREO PEQUEÑA 40 G','GALLETA OREO PEQUEÑA A 35 CENTAVOS',0.35,0.30,10,5,3);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567831','GALLETA NESTLE VAINILLA 135 G','GALLETA NESTLE DE VAINILLA A 75 CENTAVOS',0.75,0.70,10,5,3);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567841','220V ORIGINAL 600 ML','220 V A UN DOLAR',1.00,0.95,10,5,2);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567851','FRUTARIS PEQUEÑO 200 ML','FRUTARIS PEQUEÑO A 25 CENTAVOS',0.25,0.20,10,5,2);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567861','COCA COLA PEQUEÑA SABOR ORIGINAL 300 ML','COCA COLA PEQUEÑA SABOR ORIGINAL A 50 CENTAVOS',0.50,0.45,10,5,2);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567871','COLA FANTA PEQUEÑA 300 ML','CLA FANTA PEQUEÑA A 40 CENTAVOS',0.40,0.35,10,5,2);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567881','SAVILOE 320 ML','SAVILOE A UN DOLAR',1.00,0.95,10,5,2);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567991','LECHE TONI','LECHE TONI A 85 CENTAVOS',0.85,0.80,10,5,4);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567091','YOGURT TONIMIX','YOGURT TONIMIX A 85 CENTAVOS',0.85,0.80,10,5,4);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567191','TRIDENT PACK 3 UNIDADES','TRIDENT PACK 3 UNIDADES A 35 CENTAVOS',0.35,0.30,10,5,5);
INSERT INTO productos(codigo_barras,nombre,descripcion,precio,precio_compra,cantidad_maxima,cantidad_minima,categoria_id) VALUES('1234567291','TRIDENT PACK 5 UNIDADES','TRIDENT PACK 5 UNIDADES A 50 CENTAVOS',0.50,0.45,10,5,5);