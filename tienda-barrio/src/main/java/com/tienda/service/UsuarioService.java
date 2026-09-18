package com.tienda.service;

import com.tienda.exception.RecursoNoEncontradoException;
import com.tienda.model.Usuario;
import com.tienda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
    }

    public Usuario buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario)
            .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + nombreUsuario));
    }

    @Transactional
    public Usuario registrar(Usuario usuario) {
        if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
            throw new IllegalStateException("El nombre de usuario ya existe: " + usuario.getNombreUsuario());
        }
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario actualizar(Integer id, Usuario datos) {
        Usuario existente = buscarPorId(id);
        existente.setNombreUsuario(datos.getNombreUsuario());
        existente.setTipoUsuario(datos.getTipoUsuario());
        existente.setEstado(datos.getEstado());
        if (datos.getContrasena() != null && !datos.getContrasena().isEmpty()) {
            existente.setContrasena(passwordEncoder.encode(datos.getContrasena()));
        }
        return usuarioRepository.save(existente);
    }

    @Transactional
    public void cambiarEstado(Integer id, Boolean estado) {
        Usuario usuario = buscarPorId(id);
        usuario.setEstado(estado);
        usuarioRepository.save(usuario);
    }
}
