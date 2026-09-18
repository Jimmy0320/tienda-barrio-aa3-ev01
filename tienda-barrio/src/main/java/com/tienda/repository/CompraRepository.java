package com.tienda.repository;

import com.tienda.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
    List<Compra> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Compra> findByProveedor_IdProveedor(Integer idProveedor);
}
