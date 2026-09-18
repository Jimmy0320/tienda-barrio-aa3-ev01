package com.tienda.service;

import com.tienda.exception.RecursoNoEncontradoException;
import com.tienda.model.*;
import com.tienda.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Venta> listarTodas() {
        return ventaRepository.findAll();
    }

    public Venta buscarPorId(Integer id) {
        return ventaRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada con id: " + id));
    }

    public List<Venta> buscarPorFechas(LocalDateTime inicio, LocalDateTime fin) {
        return ventaRepository.findByFechaBetween(inicio, fin);
    }

    public BigDecimal totalVentasPeriodo(LocalDateTime inicio, LocalDateTime fin) {
        return ventaRepository.sumTotalByFechaBetween(inicio, fin);
    }

    public Long ventasDeHoy() {
        return ventaRepository.countVentasHoy();
    }

    @Transactional
    public Venta registrarVenta(Venta venta, List<DetalleVenta> detalles, Integer idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado"));
        venta.setUsuario(usuario);

        BigDecimal total = BigDecimal.ZERO;
        for (DetalleVenta detalle : detalles) {
            Producto producto = productoService.buscarPorId(detalle.getProducto().getIdProducto());
            if (producto.getStock() < detalle.getCantidad()) {
                throw new IllegalStateException(
                    "Stock insuficiente para: " + producto.getNombre() +
                    ". Disponible: " + producto.getStock() +
                    ", Solicitado: " + detalle.getCantidad()
                );
            }
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setVenta(venta);
            total = total.add(detalle.getSubtotal());
        }

        venta.setTotal(total);
        venta.setDetalles(detalles);
        Venta ventaGuardada = ventaRepository.save(venta);

        for (DetalleVenta detalle : detalles) {
            productoService.actualizarStock(detalle.getProducto().getIdProducto(), -detalle.getCantidad());
        }

        return ventaGuardada;
    }
}
