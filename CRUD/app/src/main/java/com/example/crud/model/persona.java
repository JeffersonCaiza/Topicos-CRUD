package com.example.crud.model;

public class persona {
    private String id;
    private String Nombre;
    private String Apellido;
    private String Correos;
    private String Password;

    public persona(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String apellido) {
        Apellido = apellido;
    }

    public String getCorreos() {
        return Correos;
    }

    public void setCorreos(String correos) {
        Correos = correos;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }



    @Override
    public String toString(){
        return Nombre;
    }
}


