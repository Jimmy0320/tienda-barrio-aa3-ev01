package com.tienda.repository;

import com.tienda.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositorio de acceso a datos para la entidad {@link Producto}.
 * <p>
 * Extiende {@link JpaRepository}, por lo que hereda automáticamente las
 * operaciones CRUD básicas (save, findById, findAll, deleteById, etc.) sin
 * necesidad de implementarlas manualmente. Solo se declaran aquí las
 * consultas específicas del negocio que Spring Data JPA no puede inferir
 * por convención de nombres o que requieren JPQL personalizado.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    /**
     * Busca productos cuyo nombre contenga el texto dado, sin distinguir
     * mayúsculas/minúsculas. Se usa en el endpoint de búsqueda del catálogo.
     *
     * @param nombre texto a buscar dentro del nombre del producto
     * @return lista de productos coincidentes (puede ser vacía)
     */
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Devuelve los productos cuyo stock actual está en o por debajo de su
     * stock mínimo definido, para alimentar las alertas de inventario.
     *
     * @return lista de productos con stock bajo
     */
    @Query("SELECT p FROM Producto p WHERE p.stock <= p.stockMinimo")
    List<Producto> findProductosConStockBajo();

    /**
     * Busca productos cuyo stock coincide exactamente con el valor indicado
     * (usado, por ejemplo, para ubicar productos con stock = 0).
     *
     * @param stock cantidad exacta de stock a buscar
     * @return lista de productos con ese stock
     */
    List<Producto> findByStockEquals(Integer stock);
}
