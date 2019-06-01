package com.appquiz.bakke.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * Clase que crea la Base de Datos para almacenar los pedidos finalizados
 */
public class DataBase extends SQLiteOpenHelper {

    // Sentencia SQL para crear la tabla Pedido
    String sqlCreate = "CREATE TABLE Pedido (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, id_pedido INTEGER NOT NULL UNIQUE, fecha TEXT, nombre TEXT, " +
            "latitudCliente DOUBLE, longitudCliente DOUBLE, latitudProducto DOUBLE, longitudProducto DOUBLE," +
            " direccionCliente TEXT, direccionProducto TEXT, orden TEXT, estado INTEGER)";

    /**
     * Constructor
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Para el control de versiones
    }
}
