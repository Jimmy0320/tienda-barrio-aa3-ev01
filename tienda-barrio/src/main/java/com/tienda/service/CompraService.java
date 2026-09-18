package com.tienda.service;

import com.tienda.exception.RecursoNoEncontradoException;
import com.tienda.model.*;
import com.tienda.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private ProductoService productoService;

    public List<Compra> listarTodas() {
        return compraRepository.findAll();
    }

    public Compra buscarPorId(Integer id) {
        return compraRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Compra no encontrada con id: " + id));
    }

    public List<Compra> buscarPorFechas(LocalDateTime inicio, LocalDateTime fin) {
        return compraRepository.findByFechaBetween(inicio, fin);
    }

    @Transactional
    public Compra registrarCompra(Compra compra, List<DetalleCompra> detalles, Integer idProveedor) {
        Proveedor proveedor = proveedorService.buscarPorId(idProveedor);
        compra.setProveedor(proveedor);
        compra.setDetalles(detalles);

        for (DetalleCompra detalle : detalles) {
            detalle.setCompra(compra);
            productoService.buscarPorId(detalle.getProducto().getIdProducto());
        }

        Compra compraGuardada = compraRepository.save(compra);

        for (DetalleCompra detalle : detalles) {
            productoService.actualizarStock(detalle.getProducto().getIdProducto(), detalle.getCantidad());
        }

        return compraGuardada;
    }
}
