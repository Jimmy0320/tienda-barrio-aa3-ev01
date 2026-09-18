package com.tienda.dto;

public class LoginRequest {
    private String nombreUsuario;
    private String contrasena;

    // Constructor vacío obligatorio para Jackson
    public LoginRequest() {}

    public String getNombreUsuario() { 
        return nombreUsuario; 
    }
    
    public void setNombreUsuario(String nombreUsuario) { 
        this.nombreUsuario = nombreUsuario; 
    }
    
    public String getContrasena() { 
        return contrasena; 
    }
    
    public void setContrasena(String contrasena) { 
        this.contrasena = contrasena; 
    }
}