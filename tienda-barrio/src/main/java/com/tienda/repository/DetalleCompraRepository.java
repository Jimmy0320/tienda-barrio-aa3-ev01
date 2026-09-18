package com.tienda.repository;

import com.tienda.model.DetalleCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Integer> {
    List<DetalleCompra> findByCompra_IdCompra(Integer idCompra);
    List<DetalleCompra> findByProducto_IdProducto(Integer idProducto);
}
