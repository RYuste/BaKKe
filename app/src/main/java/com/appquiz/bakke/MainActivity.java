package com.appquiz.bakke;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private Context myContext;
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

        //PRUEBA DE DATOS
        //Repositorio.getRepositorio().consultaAñadirPedidoPRUEBA(myContext);

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

    @Override
    protected void onStart() {
        MyLog.d(TAG, "Iniciando onStart...");

        super.onStart();

        MyLog.d(TAG, "Cerrando onStart...");
    }

    @Override
    protected void onRestart() {
        MyLog.d(TAG, "Iniciando onRestart...");

        super.onRestart();

        MyLog.d(TAG, "Cerrando onRestart...");
    }

    @Override
    protected void onResume() {
        MyLog.d(TAG, "Iniciando onResume...");
        super.onResume();

        // Almacenamos el contexto de la actividad para utilizar en las clases internas
        myContext = this;

        final ArrayList<Pedido> listaPedidos = Repositorio.getRepositorio().consultaListarPedidos(myContext);

        // Inicializa el RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // Crea el Adaptador con los datos de la lista anterior
        final PedidosAdapter adaptador = new PedidosAdapter(listaPedidos);
        // Si la Base de Datos está vacía devuelve true, sino false
        boolean DB_Vacia = Repositorio.getRepositorio().checkEmpty(myContext);

        if(DB_Vacia == false){
            // Asocia el elemento de la lista con una acción al ser pulsado
            /*adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acción al pulsar el elemento
                    int position = recyclerView.getChildAdapterPosition(v);

                    Intent intent = new Intent(ListadoPreguntasActivity.this, NuevaEditaPreguntaActivity.class);

                    // Creamos la información a pasar entre actividades
                    Bundle b = new Bundle();
                    b.putInt("ID", listaPedidos.get(position).getId());

                    // Añadimos la información al intent
                    intent.putExtras(b);
                    // Iniciamos la actividad
                    startActivity(intent);
                }
            });*/

            // Asocia el Adaptador al RecyclerView
            recyclerView.setAdapter(adaptador);
            // Muestra el RecyclerView en vertical
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }else{
            adaptador.borrarDatos();
        }
        MyLog.d(TAG, "Cerrando onResume...");
    }

    @Override
    protected void onPause() {
        MyLog.d(TAG, "Iniciando onPause...");

        super.onPause();

        MyLog.d(TAG, "Cerrando onPause...");
    }

    @Override
    protected void onStop() {
        MyLog.d(TAG, "Iniciando onStop...");

        super.onStop();

        MyLog.d(TAG, "Cerrando onStop...");
    }

    @Override
    protected void onDestroy() {
        MyLog.d(TAG, "Iniciando onDestroy...");

        super.onDestroy();

        MyLog.d(TAG, "Cerrando onDestroy...");
    }
}
