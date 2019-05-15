package com.appquiz.bakke;

import java.util.Date;

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

    /**
     * Constructor 1
     *
     * @param id
     * @param fecha
     * @param nombre
     * @param direccion
     * @param orden
     */
    public Pedido(int id, String fecha, String nombre, double latitud, double longitud, String direccion, String orden) {
        this.id = id;
        this.fecha = fecha;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion= direccion;
        this.orden = orden;
    }

    /**
     * Constructor 2
     *
     * @param fecha
     * @param nombre
     * @param direccion
     * @param orden
     */
    public Pedido(String fecha, String nombre, double latitud, double longitud, String direccion, String orden) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.direccion= direccion;
        this.orden = orden;
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

}
