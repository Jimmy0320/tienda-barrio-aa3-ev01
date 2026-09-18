package com.tienda.dto;

import com.tienda.model.DetalleVenta;
import com.tienda.model.Venta;
import java.util.List;

public class VentaRequest {
    private Venta venta;
    private List<DetalleVenta> detalles;
    private Integer idUsuario;

    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }
    public List<DetalleVenta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }
    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }
}
