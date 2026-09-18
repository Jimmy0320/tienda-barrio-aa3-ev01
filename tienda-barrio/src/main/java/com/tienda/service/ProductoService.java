package com.tienda.service;

import com.tienda.exception.RecursoNoEncontradoException;
import com.tienda.model.Producto;
import com.tienda.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Capa de servicio (lógica de negocio) para la gestión de productos.
 * <p>
 * Actúa como intermediaria entre {@link com.tienda.controller.ProductoController}
 * y {@link ProductoRepository}: aquí se aplican las reglas de negocio
 * (validación de stock, orquestación de actualizaciones) antes de delegar
 * la persistencia al repositorio.
 */
@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    /**
     * Obtiene el listado completo de productos registrados.
     *
     * @return lista de todos los productos
     */
    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    /**
     * Busca un producto por su identificador.
     *
     * @param id identificador del producto
     * @return el producto encontrado
     * @throws RecursoNoEncontradoException si no existe un producto con ese id
     */
    public Producto buscarPorId(Integer id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con id: " + id));
    }

    /**
     * Busca productos cuyo nombre contenga el texto indicado.
     *
     * @param nombre texto a buscar
     * @return lista de productos coincidentes
     */
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Obtiene los productos cuyo stock está en o por debajo del mínimo
     * definido, para alimentar las alertas de inventario del dashboard.
     *
     * @return lista de productos con stock bajo
     */
    public List<Producto> obtenerProductosConStockBajo() {
        return productoRepository.findProductosConStockBajo();
    }

    /**
     * Registra un nuevo producto en el inventario.
     *
     * @param producto datos del producto a registrar (ya validados por el controlador)
     * @return el producto guardado, con su id generado
     */
    @Transactional
    public Producto registrar(Producto producto) {
        return productoRepository.save(producto);
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param id                  identificador del producto a actualizar
     * @param productoActualizado objeto con los nuevos valores
     * @return el producto ya actualizado y persistido
     * @throws RecursoNoEncontradoException si el producto no existe
     */
    @Transactional
    public Producto actualizar(Integer id, Producto productoActualizado) {
        Producto existente = buscarPorId(id);
        existente.setNombre(productoActualizado.getNombre());
        existente.setDescripcion(productoActualizado.getDescripcion());
        existente.setPrecio(productoActualizado.getPrecio());
        existente.setStock(productoActualizado.getStock());
        existente.setStockMinimo(productoActualizado.getStockMinimo());
        return productoRepository.save(existente);
    }

    /**
     * Ajusta el stock de un producto sumando (o restando, si la cantidad es
     * negativa) la cantidad indicada. Se usa al confirmar ventas y compras.
     *
     * @param idProducto identificador del producto
     * @param cantidad   cantidad a sumar al stock actual (negativa para restar)
     * @throws RecursoNoEncontradoException si el producto no existe
     * @throws IllegalStateException        si el ajuste dejaría el stock en negativo
     */
    @Transactional
    public void actualizarStock(Integer idProducto, Integer cantidad) {
        Producto producto = buscarPorId(idProducto);
        int nuevoStock = producto.getStock() + cantidad;
        if (nuevoStock < 0) {
            throw new IllegalStateException("Stock insuficiente para el producto: " + producto.getNombre());
        }
        producto.setStock(nuevoStock);
        productoRepository.save(producto);
    }

    /**
     * Elimina un producto del inventario.
     *
     * @param id identificador del producto a eliminar
     * @throws RecursoNoEncontradoException si el producto no existe
     */
    @Transactional
    public void eliminar(Integer id) {
        buscarPorId(id);
        productoRepository.deleteById(id);
    }
}
