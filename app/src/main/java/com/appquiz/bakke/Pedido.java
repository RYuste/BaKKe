package com.appquiz.bakke;

import java.util.Date;

/**
 * Clase Pedido
 */
public class Pedido {

    private int id;
    private String nombre;
    private String fecha;
    private String direccion;
    private String orden;

    /**
     * Constructor
     *
     * @param id
     * @param nombre
     * @param fecha
     * @param direccion
     * @param orden
     */
    public Pedido(int id, String nombre, String fecha, String direccion, String orden) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.direccion= direccion;
        this.orden = orden;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDirecciond(String direccion) {
        this.direccion = direccion;
    }

    public String getOrden() {
        return orden;
    }
    public void setOrden(String orden) {
        this.orden = orden;
    }
}
