package com.tienda.service;

import com.tienda.exception.RecursoNoEncontradoException;
import com.tienda.model.Proveedor;
import com.tienda.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    public List<Proveedor> listarTodos() {
        return proveedorRepository.findAll();
    }

    public Proveedor buscarPorId(Integer id) {
        return proveedorRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Proveedor no encontrado con id: " + id));
    }

    public List<Proveedor> buscarPorNombre(String nombre) {
        return proveedorRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Transactional
    public Proveedor registrar(Proveedor proveedor) {
        if (proveedorRepository.existsByNombre(proveedor.getNombre())) {
            throw new IllegalStateException("Ya existe un proveedor con el nombre: " + proveedor.getNombre());
        }
        return proveedorRepository.save(proveedor);
    }

    @Transactional
    public Proveedor actualizar(Integer id, Proveedor datos) {
        Proveedor existente = buscarPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setDireccion(datos.getDireccion());
        existente.setTelefono(datos.getTelefono());
        return proveedorRepository.save(existente);
    }

    @Transactional
    public void eliminar(Integer id) {
        buscarPorId(id);
        proveedorRepository.deleteById(id);
    }
}
