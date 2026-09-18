package com.tienda.controller;

import com.tienda.dto.VentaRequest;
import com.tienda.model.Venta;
import com.tienda.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/ventas")
@CrossOrigin(origins = "*")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> listar() {
        return ResponseEntity.ok(ventaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ventaService.buscarPorId(id));
    }

    @GetMapping("/por-fechas")
    public ResponseEntity<List<Venta>> porFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(ventaService.buscarPorFechas(inicio, fin));
    }

    @GetMapping("/total-periodo")
    public ResponseEntity<BigDecimal> totalPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(ventaService.totalVentasPeriodo(inicio, fin));
    }

    @GetMapping("/hoy")
    public ResponseEntity<Long> ventasHoy() {
        return ResponseEntity.ok(ventaService.ventasDeHoy());
    }

    @PostMapping
    public ResponseEntity<Venta> registrar(@RequestBody VentaRequest request) {
        Venta venta = ventaService.registrarVenta(
            request.getVenta(),
            request.getDetalles(),
            request.getIdUsuario()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(venta);
    }
}
