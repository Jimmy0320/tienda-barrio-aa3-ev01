package com.tienda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.List;

/**
 * Entidad que representa un producto del inventario de Tienda Barrio.
 * <p>
 * Se mapea directamente a la tabla {@code productos} mediante JPA/Hibernate.
 * Cada producto puede aparecer en múltiples detalles de venta y de compra,
 * relación que se modela con las listas {@link #detallesVenta} y
 * {@link #detallesCompra}.
 */
@Entity
@Table(name = "productos")
public class Producto {

    /** Identificador único autogenerado por la base de datos. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    /** Nombre comercial del producto. Obligatorio, máximo 100 caracteres. */
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /** Descripción libre del producto (opcional). */
    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    /** Precio de venta al público. Obligatorio. */
    @NotNull(message = "El precio es obligatorio")
    @Column(name = "precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    /** Cantidad actual disponible en inventario. Obligatorio. */
    @NotNull(message = "El stock es obligatorio")
    @Column(name = "stock", nullable = false)
    private Integer stock;

    /** Umbral bajo el cual se considera que el producto tiene stock bajo (alerta). */
    @Column(name = "stock_minimo")
    private Integer stockMinimo;

    /** Detalles de venta en los que ha participado este producto (relación inversa). */
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("producto")
    private List<DetalleVenta> detallesVenta;

    /** Detalles de compra en los que ha participado este producto (relación inversa). */
    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("producto")
    private List<DetalleCompra> detallesCompra;

    /**
     * Indica si el stock actual está en o por debajo del stock mínimo definido.
     * Se usa para generar alertas de inventario (ver {@code /productos/stock-bajo}).
     *
     * @return {@code true} si hay que reabastecer el producto; {@code false} en caso
     *         contrario o si no se definió un stock mínimo.
     */
    @Transient
    public boolean isStockBajo() {
        if (stockMinimo == null) return false;
        return stock <= stockMinimo;
    }

    // --- Getters y setters (estándar JavaBean) ---

    public Integer getIdProducto() { return idProducto; }
    public void setIdProducto(Integer idProducto) { this.idProducto = idProducto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    public Integer getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(Integer stockMinimo) { this.stockMinimo = stockMinimo; }
    public List<DetalleVenta> getDetallesVenta() { return detallesVenta; }
    public void setDetallesVenta(List<DetalleVenta> detallesVenta) { this.detallesVenta = detallesVenta; }
    public List<DetalleCompra> getDetallesCompra() { return detallesCompra; }
    public void setDetallesCompra(List<DetalleCompra> detallesCompra) { this.detallesCompra = detallesCompra; }
}
