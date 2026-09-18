# Módulo Productos — Servlets y JSP (GA7-220501096-AA2-EV02)

Este repositorio contiene la codificación y pruebas de un módulo web independiente de gestión de productos, desarrollado como evidencia para el programa de formación **Análisis y Desarrollo de Software (ADSO)** del SENA.

---

## 📌 Datos de la Evidencia

* **Programa:** Tecnólogo en Análisis y Desarrollo de Software (ADSO)
* **Ficha:** 3186654
* **Aprendiz:** Jimmy Ramos Martinez
* **Resultado de Aprendizaje:** 220501096-04 - Codificar el software de acuerdo con el diseño establecido
* **Actividad:** GA7-220501096-AA2 - Aplicar estándares de codificación, de acuerdo con el diseño
* **Evidencia:** GA7-220501096-AA2-EV02

---

## 🚀 Descripción del Proyecto

El módulo implementa las tecnologías base de **Jakarta EE** (Servlets + JSP) para gestionar el registro y la consulta de productos en una base de datos MySQL. Se construyó como un proyecto web independiente con arquitectura MVC simplificada (sin ORM ni frameworks adicionales) para aislar la evaluación técnica.

### Indicadores Cumplidos:
1. **Formulario HTML5:** Captura de datos de nuevos productos (nombre, descripción, precio, stock, stock mínimo).
2. **Jakarta Servlet (`HttpServlet`):** Manejo explícito de peticiones HTTP:
   * `doGet`: Consulta y lista de productos existentes.
   * `doPost`: Procesa el formulario, inserta el registro mediante JDBC y redirige al listado.
3. **Vista JSP + JSTL:** Renderizado dinámico del listado de productos de forma desacoplada de la lógica de negocio.
4. **Control de Versiones:** Historial de commits y versionamiento continuo cargado en Git/GitHub.

---

## 🛠️ Tecnologías y Herramientas

| Componente | Tecnología |
| :--- | :--- |
| **Lenguaje / Plataforma** | Java 17 / Jakarta EE 10 |
| **Servidor de Aplicaciones** | Apache Tomcat 10.1 |
| **Controlador** | Jakarta Servlet (`@WebServlet`) |
| **Vista / Presentación** | JSP + JSTL / HTML5 |
| **Acceso a Datos** | JDBC puro (`PreparedStatement`) |
| **Base de Datos** | MySQL 8.0 (`evidencia_ga7_ev02`) |
| **Gestor de Proyecto** | Apache Maven (`war`) |
| **IDE** | Apache NetBeans 26 |

---

## 🗄️ Estructura del Proyecto

```text
evidencia-servlet-jsp/
├── pom.xml
├── base_de_datos.sql
├── README.md
├── .gitignore
└── src/main/
    ├── java/com/tiendabarrio/
    │   ├── dao/ProductoDAO.java
    │   ├── modelo/Producto.java
    │   ├── servlet/ProductoServlet.java
    │   └── util/ConexionBD.java
    └── webapp/
        ├── producto.html
        └── WEB-INF/
            ├── web.xml
            └── jsp/productos.jsp
