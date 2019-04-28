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
        MyLog.d(TAG, "Entrando en ListarPedidos...");

        DataBase db = new DataBase(myContext, constants.DB_NOMBRE, null, 1);
        SQLiteDatabase sqldb = db.getWritableDatabase();

        Cursor c = sqldb.rawQuery(constants.DB_LISTAR_PEDIDOS, null);

        /*sqldb.execSQL("INSERT INTO Pedido(nombre, fecha, direccion, orden) " +
                "VALUES('Rafael Yuste Jiménez', '02/11/1995', 'c/ Manuel Caracuel - Nº48', 'Esto es una prueba de una orden" +
                "para ver como que de largo y todo eso, to wapo, aver, jaja, xd.')");*/

        //Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            listaPedidos.removeAll(listaPedidos);

            //Recorremos el cursor hasta que no haya más registros
            do {
                int id_pedido= c.getInt(c.getColumnIndex("id_pedido"));
                String nombre= c.getString(c.getColumnIndex("nombre"));
                String fecha = c.getString(c.getColumnIndex("fecha"));
                String direccion = c.getString(c.getColumnIndex("direccion"));
                String orden = c.getString(c.getColumnIndex("orden"));

                Pedido p = new Pedido(id_pedido, nombre, fecha, direccion, orden);
                listaPedidos.add(p);
            } while(c.moveToNext());
        }
        db.close();
        MyLog.d(TAG, "Saliendo del método ListarPreguntas...");

        return listaPedidos;
    }

    /**
     * Añade un pedido a la Base de Datos
     *
     * @param p
     * @param myContext
     * @return true o false
     */
    public boolean consultaAñadirPedido(Pedido p, Context myContext){
        MyLog.d(TAG, "Entrando en AñadirPedido...");

        boolean correcto;
        DataBase db = new DataBase(myContext, constants.DB_NOMBRE, null, 1);
        SQLiteDatabase sqldb = db.getWritableDatabase();

        if(sqldb != null){
            sqldb.execSQL("INSERT INTO Pedido(nombre, fecha, direccion, orden) " +
                    "VALUES('"+p.getNombre()+"', '"+p.getFecha()+"', '"+p.getDireccion()+"', '"+p.getOrden()+"')");

            correcto = true;

            MyLog.d(TAG, "Saliendo de AñadirPedido...");
        }else {
            correcto = false;

            MyLog.d(TAG, "Error null en AñadirPedido...");
        }
        db.close();

        MyLog.d(TAG, "Saliendo del método AñadirPedido...");
        return correcto;
    }


/*-----------------------------------------------------------------------------------*/


    public void consultaAñadirPedidoPRUEBA(Context myContext){
        MyLog.d(TAG, "Entrando en AñadirPedido...");

        DataBase db = new DataBase(myContext, constants.DB_NOMBRE, null, 1);
        SQLiteDatabase sqldb = db.getWritableDatabase();

        if(sqldb != null){
            sqldb.execSQL("INSERT INTO Pedido(nombre, fecha, direccion, orden) " +
                    "VALUES('Rafael Yuste Jiménez', '02/11/1995', 'c/ Manuel Caracuel - Nº48', 'Esto es una prueba de una orden" +
                    "para ver como que de largo y todo eso, to wapo, aver, jaja, xd.')");

            MyLog.d(TAG, "Saliendo de AñadirPedido...");
        }else {
            MyLog.d(TAG, "Error null en AñadirPedido...");
        }
        db.close();
        MyLog.d(TAG, "Saliendo del método AñadirPedido...");
    }

}
