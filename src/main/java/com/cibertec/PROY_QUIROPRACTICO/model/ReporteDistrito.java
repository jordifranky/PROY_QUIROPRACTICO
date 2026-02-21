package com.cibertec.PROY_QUIROPRACTICO.model;

public class ReporteDistrito {
    private String nombreDistrito;
    private long cantidad;

    // Constructor obligatorio para la consulta SELECT NEW
    public ReporteDistrito(String nombreDistrito, long cantidad) {
        this.nombreDistrito = nombreDistrito;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public String getNombreDistrito() { return nombreDistrito; }
    public void setNombreDistrito(String nombreDistrito) { this.nombreDistrito = nombreDistrito; }
    public long getCantidad() { return cantidad; }
    public void setCantidad(long cantidad) { this.cantidad = cantidad; }
}