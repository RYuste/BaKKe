package com.appquiz.bakke;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.github.clans.fab.FloatingActionMenu;

public class MainActivity extends AppCompatActivity {

    FloatingActionMenu fabMenu;
    public ImageButton btn_exit;

    @Override
    /**
     * Muestra el AlertDialog al pulsar el botón del dispositivo keyDown
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        exit();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar toolbar = findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        fabMenu = (FloatingActionMenu) findViewById(R.id.fabmenu);
        fabMenu.setClosedOnTouchOutside(true); // Cierra el fabMenu al pulsar fuera de él

        btn_exit = findViewById(R.id.imageButton_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });
    }

    /**
     * Inicia la actividad PedidosEnCursoActivity
     *
     * @param view
     */
    public void openEnCurso(View view){
        fabMenu.close(true); // Cierra el fabMenu
        startActivity(new Intent(MainActivity.this, PedidosEnCursoActivity.class));
    }
    /**
     * Inicia la actividad PedidosFinalizadosActivity
     *
     * @param view
     */
    public void openFinalizados(View view){
        fabMenu.close(true); // Cierra el fabMenu
        startActivity(new Intent(MainActivity.this, PedidosFinalizadosActivity.class));
    }

    /**
     * Muestra un AlerDialog para salir de la aplicación
     */
    public void exit(){
        Log.i("ActionBar", "Salir de la aplicación");

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.exit);

        builder.setPositiveButton(R.string.confirmacion, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        }).show(); //show alert dialog
    }
}
