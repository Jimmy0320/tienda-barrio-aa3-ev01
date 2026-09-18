package com.tienda.controller;

import com.tienda.dto.CompraRequest;
import com.tienda.model.Compra;
import com.tienda.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/compras")
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('ADMINISTRADOR')")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public ResponseEntity<List<Compra>> listar() {
        return ResponseEntity.ok(compraService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Compra> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(compraService.buscarPorId(id));
    }

    @GetMapping("/por-fechas")
    public ResponseEntity<List<Compra>> porFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(compraService.buscarPorFechas(inicio, fin));
    }

    @PostMapping
    public ResponseEntity<Compra> registrar(@RequestBody CompraRequest request) {
        Compra compra = compraService.registrarCompra(
            request.getCompra(),
            request.getDetalles(),
            request.getIdProveedor()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(compra);
    }
}
