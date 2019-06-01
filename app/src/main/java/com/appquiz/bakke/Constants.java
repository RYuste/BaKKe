package com.appquiz.bakke;

/**
 * Clase para almacenar las constantes
 */
public class Constants {

    public static final String DB_NOMBRE = "DBPedido";
    public static final String DB_CHECKEMPTY = "SELECT count(*) FROM Pedido";
    public static final String DB_LISTAR_PEDIDOS = "SELECT * FROM Pedido";

    public static final String EXTRA_ID = "id_pedido";
    public static final String EXTRA_FECHA = "fecha";
    public static final String EXTRA_NOMBRE = "nombre";
    public static final String EXTRA_LAT_CLIENTE = "latitudCliente";
    public static final String EXTRA_LON_CLIENTE = "longitudCliente";
    public static final String EXTRA_LAT_PRODUCTO = "latitudProducto";
    public static final String EXTRA_LON_PRODUCTO = "longitudProducto";
    public static final String EXTRA_DIRC_CLIENTE = "direccionCliente";
    public static final String EXTRA_DIRC_PRODUCTO = "direccionProducto";
    public static final String EXTRA_OREN = "orden";
    public static final String EXTRA_ESTADO = "estado";

    public static final int CODE_ACCESS_COARSE_LOCATION = 123;
    public static final String MAP_VIEW_BUNDLE_KEY = "google_maps_key";

    public static final String DATA_URL = "https://api.myjson.com/bins/1gtg5k";
}