package com.tienda.dto;

import com.tienda.model.Compra;
import com.tienda.model.DetalleCompra;
import java.util.List;

public class CompraRequest {
    private Compra compra;
    private List<DetalleCompra> detalles;
    private Integer idProveedor;

    public Compra getCompra() { return compra; }
    public void setCompra(Compra compra) { this.compra = compra; }
    public List<DetalleCompra> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleCompra> detalles) { this.detalles = detalles; }
    public Integer getIdProveedor() { return idProveedor; }
    public void setIdProveedor(Integer idProveedor) { this.idProveedor = idProveedor; }
}
