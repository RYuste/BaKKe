package com.appquiz.bakke.Model;

/**
 * Clase Pedido
 */
public class Pedido {

    private int id;
    private String fecha;
    private String nombre;
    private double latitud;
    private double longitud;
    private String direccion;
    private String orden;
    private int estado; // 0 por defecto, 1 en curso, 2 finalizado, 3 rechazado

    /**
     * Constructor 1
     *
     * @param id
     * @param fecha
     * @param nombre
     * @param direccion
     * @param orden
     * @param estado
     */
    public Pedido(int id, String fecha, String nombre, double latitud, double longitud, String direccion, String orden, int estado) {
        this.id = id;
        this.fecha = fecha;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion= direccion;
        this.orden = orden;
        this.estado = estado;
    }

    /**
     * Constructor 2
     *
     * @param fecha
     * @param nombre
     * @param direccion
     * @param orden
     * @param estado
     */
    public Pedido(String fecha, String nombre, double latitud, double longitud, String direccion, String orden, int estado) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion= direccion;
        this.orden = orden;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getLatitud() {
        return latitud;
    }
    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getOrden() {
        return orden;
    }
    public void setOrden(String orden) {
        this.orden = orden;
    }

    public int getEstado() {
        return estado;
    }
    public void setEstado(int estado) {
        this.estado = estado;
    }
}
