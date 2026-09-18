package com.tienda.repository;

import com.tienda.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {

    // Ventas por rango de fechas (para reportes)
    List<Venta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);

    // Ventas de un usuario específico
    List<Venta> findByUsuario_IdUsuario(Integer idUsuario);

    // Total de ventas en un período
    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE v.fecha BETWEEN :inicio AND :fin")
    BigDecimal sumTotalByFechaBetween(@Param("inicio") LocalDateTime inicio,
                                      @Param("fin") LocalDateTime fin);

    // Cantidad de ventas por día (para dashboard)
    @Query("SELECT COUNT(v) FROM Venta v WHERE DATE(v.fecha) = CURRENT_DATE")
    Long countVentasHoy();
}
