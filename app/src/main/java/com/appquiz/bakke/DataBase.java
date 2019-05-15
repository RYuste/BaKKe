package com.appquiz.bakke;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * Clase que crea la Base de Datos para almacenar los pedidos finalizados
 */
public class DataBase  extends SQLiteOpenHelper {

    //Sentencia SQL para crear la tabla Pedido
    String sqlCreate = "CREATE TABLE Pedido (id_pedido INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, fecha TEXT, nombre TEXT, " +
            "latitud DOUBLE, longitud DOUBLE, direccion TEXT, orden TEXT)";

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
