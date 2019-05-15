package com.appquiz.bakke;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Clase encargada de manejar las operaciones de la Base de Datos
 */
public class Repositorio {

    private String TAG = "Repositorio";
    Constants constants = new Constants();

    // Singleton
    private static Repositorio miRepo = null;
    public static Repositorio getRepositorio(){
        if(miRepo == null){
            miRepo = new Repositorio();
        }
        return miRepo;
    }

    /*-------------------------------------------------------*/

    // ArrayList de Preguntas
    private ArrayList<Pedido> listaPedidos = new ArrayList<Pedido>();

    /*---------------------------------------------------------*/

    /**
     * Comprueba si la Base de Datos está vacía
     *
     * @param myContext
     * @return true o false
     */
    public boolean checkEmpty(Context myContext){
        int count = 0;
        DataBase db = new DataBase(myContext, constants.DB_NOMBRE, null, 1);
        SQLiteDatabase sqldb = db.getWritableDatabase();

        Cursor cursor = sqldb.rawQuery(constants.DB_CHECKEMPTY, null);

        try {
            if(cursor != null)
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    count = cursor.getInt(0);
                }
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        if(count>0)
            return false;
        else
            return true;
    }

    /**
     * Añade todas los pedidos creados de la Base de Datos en un ArrayList y lo devuelve
     *
     * @param myContext
     * @return listaPedidos
     */
    public ArrayList<Pedido> consultaListarPedidos(Context myContext){
        MyLog.d(TAG, "Entrando en consultaListarPedidos...");

        DataBase db = new DataBase(myContext, constants.DB_NOMBRE, null, 1);
        SQLiteDatabase sqldb = db.getWritableDatabase();

        Cursor c = sqldb.rawQuery(constants.DB_LISTAR_PEDIDOS, null);

        /*sqldb.execSQL("INSERT INTO Pedido(fecha, nombre, latitud, longitud, direccion, orden) " +
                "VALUES('02/11/1995', 'Rafael Yuste Jiménez', 37.6479943, -4.7074993, 'c/ Manuel Caracuel - Nº48', 'Esto es una prueba de una orden" +
                "para ver como que de largo y todo eso, to wapo, aver, jaja, xd.')");*/

        // Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            listaPedidos.removeAll(listaPedidos);

            // Recorremos el cursor hasta que no haya más registros
            do {
                int id_pedido = c.getInt(c.getColumnIndex("id_pedido"));
                String fecha = c.getString(c.getColumnIndex("fecha"));
                String cliente = c.getString(c.getColumnIndex("nombre"));
                double latitud = c.getDouble(c.getColumnIndex("latitud"));
                double longitud = c.getDouble(c.getColumnIndex("longitud"));
                String direccion = c.getString(c.getColumnIndex("direccion"));
                String orden = c.getString(c.getColumnIndex("orden"));

                Pedido p = new Pedido(id_pedido, fecha, cliente, latitud, longitud, direccion, orden);
                listaPedidos.add(p);
            } while(c.moveToNext());
        }
        db.close();
        MyLog.d(TAG, "Saliendo del método consultaListarPedidos...");

        return listaPedidos;
    }

    /**
     * Devuelve una pregunta seleccionada por id
     *
     * @param myContext
     * @return p
     */
    public Pedido consultaListarPedidoID(Context myContext, int id){
        MyLog.d(TAG, "Entrando en consultaListarPedidoID...");

        DataBase db = new DataBase(myContext, constants.DB_NOMBRE, null, 1);
        SQLiteDatabase sqldb = db.getWritableDatabase();

        Pedido p = null;
        Cursor c = sqldb.rawQuery(" SELECT * FROM Pedido WHERE id_pedido = '"+id+"'", null);

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            String fecha = c.getString(c.getColumnIndex("fecha"));
            String cliente = c.getString(c.getColumnIndex("nombre"));
            double latitud = c.getDouble(c.getColumnIndex("latitud"));
            double longitud = c.getDouble(c.getColumnIndex("longitud"));
            String direccion = c.getString(c.getColumnIndex("direccion"));
            String orden = c.getString(c.getColumnIndex("orden"));

            p = new Pedido(fecha, cliente, latitud, longitud, direccion, orden);
        }
        db.close();
        MyLog.d(TAG, "Saliendo del método consultaListarPedidoID...");

        return p;
    }

    /**
     * Añade un pedido a la Base de Datos
     *
     * @param p
     * @param myContext
     */
    public void consultaAñadirPedido(Pedido p, Context myContext){
        MyLog.d(TAG, "Entrando en consultaAñadirPedido...");

        DataBase db = new DataBase(myContext, constants.DB_NOMBRE, null, 1);
        SQLiteDatabase sqldb = db.getWritableDatabase();

        if(sqldb != null){
            sqldb.execSQL("INSERT INTO Pedido(fecha, nombre, latitud, longitud, direccion, orden) " +
                    "VALUES('"+p.getFecha()+"', '"+p.getNombre()+"', '"+p.getLatitud()+"', '"+p.getLongitud()+"', " +
                    "'"+p.getDireccion()+"', '"+p.getOrden()+"')");

            MyLog.d(TAG, "Saliendo de consultaAñadirPedido...");
        }else {
            MyLog.d(TAG, "Error null en consultaAñadirPedido...");
        }
        db.close();

        MyLog.d(TAG, "Saliendo del método consultaAñadirPedido...");
    }

}
