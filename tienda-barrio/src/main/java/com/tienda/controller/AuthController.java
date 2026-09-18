package com.tienda.controller;

import com.tienda.config.JwtUtil;
import com.tienda.model.Usuario;
import com.tienda.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;
import com.tienda.dto.LoginRequest;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNombreUsuario(request.getNombreUsuario());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Credenciales incorrectas"));
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.getEstado()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Usuario inactivo"));
        }

        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Credenciales incorrectas"));
        }

        String token = jwtUtil.generarToken(
            usuario.getNombreUsuario(),
            usuario.getTipoUsuario().name()
        );

        return ResponseEntity.ok(Map.of(
            "token", token,
            "idUsuario", usuario.getIdUsuario(),
            "nombreUsuario", usuario.getNombreUsuario(),
            "rol", usuario.getTipoUsuario().name()
        ));
    }
    @GetMapping("/generar-hash")
    public ResponseEntity<?> generarHash() {
        String hash = passwordEncoder.encode("admin123");
        return ResponseEntity.ok(Map.of("hash", hash));
    }

    
}
