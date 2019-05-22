package com.appquiz.bakke;

/**
 * Clase para almacenar las constantes
 */
public class Constants {

    public static final String DB_NOMBRE = "DBPedido";
    public static final String DB_CHECKEMPTY = "SELECT count(*) FROM Pedido";
    public static final String DB_LISTAR_PEDIDOS = "SELECT * FROM Pedido";

    public static final int CODE_ACCESS_COARSE_LOCATION = 123;

    public static final String MAP_VIEW_BUNDLE_KEY = "google_maps_key";

    public static final String DATA_URL = "https://repartotest.herokuapp.com/repartidor";
}
