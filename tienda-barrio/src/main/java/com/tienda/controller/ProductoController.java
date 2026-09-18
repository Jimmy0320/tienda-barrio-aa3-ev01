package com.tienda.controller;

import com.tienda.model.Producto;
import com.tienda.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST del módulo de Productos.
 * <p>
 * Expone los endpoints HTTP bajo la ruta base {@code /productos} (más el
 * context-path {@code /api} configurado a nivel de aplicación). Las
 * operaciones de escritura (crear, actualizar, eliminar) están restringidas
 * al rol ADMINISTRADOR mediante {@link PreAuthorize}; las de solo lectura
 * están disponibles para cualquier usuario autenticado.
 */
@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    /**
     * GET /productos — Lista todos los productos registrados.
     *
     * @return 200 OK con la lista completa de productos
     */
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    /**
     * GET /productos/{id} — Consulta un producto puntual por su id.
     *
     * @param id identificador del producto
     * @return 200 OK con el producto encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(productoService.buscarPorId(id));
    }

    /**
     * GET /productos/buscar?nombre=... — Búsqueda de productos por nombre
     * (coincidencia parcial, usada por el buscador del catálogo).
     *
     * @param nombre texto a buscar dentro del nombre del producto
     * @return 200 OK con los productos coincidentes
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    /**
     * GET /productos/stock-bajo — Lista los productos que requieren
     * reabastecimiento (stock en o por debajo del mínimo configurado).
     *
     * @return 200 OK con los productos en alerta de inventario
     */
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> stockBajo() {
        return ResponseEntity.ok(productoService.obtenerProductosConStockBajo());
    }

    /**
     * POST /productos — Registra un nuevo producto. Requiere rol ADMINISTRADOR.
     *
     * @param producto datos del nuevo producto (validados con Bean Validation)
     * @return 201 Created con el producto ya registrado (incluye su id generado)
     */
    @PostMapping
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Producto> registrar(@Valid @RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.registrar(producto));
    }

    /**
     * PUT /productos/{id} — Actualiza los datos de un producto existente.
     * Requiere rol ADMINISTRADOR.
     *
     * @param id       identificador del producto a actualizar
     * @param producto nuevos valores del producto
     * @return 200 OK con el producto ya actualizado
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id,
                                               @Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizar(id, producto));
    }

    /**
     * DELETE /productos/{id} — Elimina un producto del inventario.
     * Requiere rol ADMINISTRADOR.
     *
     * @param id identificador del producto a eliminar
     * @return 204 No Content si la eliminación fue exitosa
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRADOR')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
