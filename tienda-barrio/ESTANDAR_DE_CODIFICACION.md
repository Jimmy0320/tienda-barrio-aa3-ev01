# Estándar de codificación — Tienda Barrio (Backend)

Este documento describe el estándar de codificación aplicado en todo el
proyecto, evidenciado de forma explícita en el módulo **Productos**
(`Producto.java`, `ProductoRepository.java`, `ProductoService.java`,
`ProductoController.java`) para la evidencia GA7-220501096-AA3-EV01.

## Framework y almacenamiento de datos (indicadores 1 y 2)

- **Framework**: Spring Boot 3 (Spring MVC + Spring Data JPA + Spring Security).
- **Almacenamiento**: MySQL 8.0, integrado mediante Spring Data JPA / Hibernate
  (ORM), evitando SQL manual repetitivo — las consultas CRUD básicas las
  provee `JpaRepository` por convención, y solo se escriben a mano las
  consultas específicas del negocio (`@Query`).

## Arquitectura por capas

El proyecto sigue una arquitectura en capas, con una responsabilidad única
por paquete:

| Paquete | Responsabilidad |
|---|---|
| `model` | Entidades JPA (mapeo objeto-relacional). |
| `repository` | Acceso a datos (interfaces `JpaRepository`). |
| `service` | Lógica de negocio y transacciones (`@Transactional`). |
| `controller` | Endpoints REST (`@RestController`), sin lógica de negocio. |
| `dto` | Objetos de transferencia para peticiones que no mapean 1:1 a una entidad. |
| `config` | Configuración transversal (seguridad, JWT). |
| `exception` | Excepciones de negocio y su manejo centralizado. |

## Convenciones de nombres

- **Paquetes**: minúsculas, sin guiones (`com.tienda.controller`).
- **Clases e interfaces**: `PascalCase` (`ProductoService`, `ProductoRepository`).
- **Métodos y variables**: `camelCase` (`buscarPorId`, `stockMinimo`).
- **Constantes**: no aplica en este módulo (no hay constantes estáticas finales).
- **Nombres en español**, consistentes con el dominio del negocio (Tienda Barrio
  es un proyecto en español), salvo las palabras reservadas o convenciones
  propias del framework (`get`/`set`, `find`, `Repository`, `Service`,
  `Controller`).

## Comentarios y documentación (indicadores 3 y 4)

- Toda clase pública lleva un comentario Javadoc de clase explicando su
  responsabilidad dentro de la arquitectura.
- Todo método público de las capas `service` y `controller` lleva Javadoc
  describiendo qué hace, sus parámetros, su valor de retorno y las
  excepciones que puede lanzar.
- Los métodos `get`/`set` (JavaBeans) no se documentan individualmente por
  ser autoexplicativos, siguiendo la práctica estándar de Java.
- Se usan comentarios de línea (`//`) para aclarar decisiones puntuales no
  evidentes por sí solas (por ejemplo, por qué una consulta requiere JPQL
  personalizado en vez de un método derivado).

## Manejo de errores

- Las excepciones de negocio (`RecursoNoEncontradoException`) se lanzan
  desde la capa `service`, nunca desde el `controller`.
- Un único `@RestControllerAdvice` (`GlobalExceptionHandler`) centraliza la
  traducción de excepciones a respuestas HTTP con código y mensaje
  consistentes.
