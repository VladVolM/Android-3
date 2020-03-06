package com.example.a3_volodymyr;

public class DatosParaFirebase {

    String id;
    String metodo;
    String fechaYHora;
    String tiempo;

    public DatosParaFirebase() {

    }

    public DatosParaFirebase(String id,String metodo, String fechaYHora, String tiempo) {
        this.id=id;
        this.metodo = metodo;
        this.fechaYHora = fechaYHora;
        this.tiempo = tiempo;
    }

    public String getMetodo() {
        return metodo;
    }

    public String getFechaYHora() {
        return fechaYHora;
    }

    public String getTiempo() {
        return tiempo;
    }

    public String getId() {
        return id;
    }
}
