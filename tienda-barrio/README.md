# Tienda Barrio - Backend (Spring Boot)

Evidencia: **GA7-220501096-AA3-EV01 — Codificación de módulos del software
stand-alone, web y móvil**

## Cómo esta evidencia cumple el instrumento

| Indicador | Cómo se cumple |
|---|---|
| 1. Framework aplicado | Spring Boot 3 (Spring MVC + Spring Security) |
| 2. Almacenamiento de datos | Spring Data JPA / Hibernate + MySQL 8.0 |
| 3. Estándar de codificación + comentarios | Ver `ESTANDAR_DE_CODIFICACION.md`; comentarios Javadoc en el módulo Productos (`model`, `repository`, `service`, `controller`) |
| 4. Versionamiento | Git / GitHub — ver `repositorio.txt` |

## Módulo representativo: Productos

- `src/main/java/com/tienda/model/Producto.java`
- `src/main/java/com/tienda/repository/ProductoRepository.java`
- `src/main/java/com/tienda/service/ProductoService.java`
- `src/main/java/com/tienda/controller/ProductoController.java`

Este es el proyecto real y funcional de Tienda Barrio (incluye también los
módulos de Ventas, Compras, Proveedores, Usuarios y autenticación JWT), pero
la documentación explícita para esta evidencia se concentra en el módulo
Productos como muestra representativa.

## Cómo ejecutarlo

1. Ejecuta `tienda_barrio_aa3.sql` en tu MySQL — crea una base de datos
   independiente (`tienda_barrio_aa3`), con la misma estructura que la real
   `tienda_barrio`, para no mezclar los datos de esta evidencia con tu
   proyecto en uso.
2. En `src/main/resources/application.properties`, coloca tu contraseña real
   de MySQL en `spring.datasource.password` (quedó con un placeholder por
   seguridad, ya que este repositorio es público).
3. Corre la aplicación desde tu IDE (botón Run sobre `TiendaBarrioApplication`)
   o con `mvn spring-boot:run`.
4. La API queda disponible bajo `http://localhost:8080/api/...` (ver
   `server.servlet.context-path` en `application.properties`).

## Pendiente antes de entregar

- [ ] Completar `repositorio.txt` con el enlace real del repositorio.
- [ ] `git init` + `push` (este proyecto aún no tiene Git inicializado).
- [ ] Comprimir como `JIMMY_RAMOS_AA3_EV01.zip`, sin la carpeta `target/`.
