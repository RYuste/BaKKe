package com.appquiz.bakke.Model;

/**
 * Clase Pedido
 */
public class Pedido {

    private int id;
    private String fecha;
    private String nombre;
    private double latitudCliente;
    private double longitudCliente;
    private double latitudProducto;
    private double longitudProducto;
    private String direccionCliente;
    private String direccionProducto;
    private String orden;
    private int estado; // 0 por defecto, 1 en curso, 2 finalizado, 3 rechazado

    /**
     * Constructor 1
     *
     * @param id
     * @param fecha
     * @param nombre
     * @param latitudCliente
     * @param longitudCliente
     * @param latitudProducto
     * @param longitudProducto
     * @param direccionCliente
     * @param direccionProducto
     * @param orden
     * @param estado
     */
    public Pedido(int id, String fecha, String nombre, double latitudCliente, double longitudCliente,
                  double latitudProducto, double longitudProducto, String direccionCliente, String direccionProducto, String orden, int estado) {
        this.id = id;
        this.fecha = fecha;
        this.nombre = nombre;
        this.latitudCliente = latitudCliente;
        this.longitudCliente = longitudCliente;
        this.latitudProducto = latitudProducto;
        this.longitudProducto = longitudProducto;
        this.direccionCliente= direccionCliente;
        this.direccionProducto= direccionProducto;
        this.orden = orden;
        this.estado = estado;
    }

    /**
     * Constructor 2
     *
     * @param fecha
     * @param nombre
     * @param latitudCliente
     * @param longitudCliente
     * @param latitudProducto
     * @param longitudProducto
     * @param direccionCliente
     * @param direccionProducto
     * @param orden
     * @param estado
     */
    public Pedido(String fecha, String nombre, double latitudCliente, double longitudCliente,
                  double latitudProducto, double longitudProducto, String direccionCliente, String direccionProducto, String orden, int estado) {
        this.fecha = fecha;
        this.nombre = nombre;
        this.latitudCliente = latitudCliente;
        this.longitudCliente = longitudCliente;
        this.latitudProducto = latitudProducto;
        this.longitudProducto = longitudProducto;
        this.direccionCliente= direccionCliente;
        this.direccionProducto= direccionProducto;
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

    public double getLatitudCliente() {
        return latitudCliente;
    }
    public void setLatitudCliente(double latitudCliente) {
        this.latitudCliente = latitudCliente;
    }

    public double getLongitudCliente() {
        return longitudCliente;
    }
    public void setLongitudCliente(double longitudCliente) {
        this.longitudCliente = longitudCliente;
    }

    public double getLatitudProducto() {
        return latitudProducto;
    }
    public void setLatitudProducto(double latitudProducto) {
        this.latitudProducto = latitudProducto;
    }

    public double getLongitudProducto() {
        return longitudProducto;
    }
    public void setLongitudProducto(double longitudProducto) {
        this.longitudProducto = longitudProducto;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }
    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public String getDireccionProducto() {
        return direccionProducto;
    }
    public void setDireccionProducto(String direccionProducto) {
        this.direccionProducto = direccionProducto;
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
