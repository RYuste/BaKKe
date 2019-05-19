package com.appquiz.bakke;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
     * Método que contiene los permisos de ubicación
     *
     * @return true o false
     */
    public boolean permisosUbicacion() {
        boolean aceptar = true;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Permiso denegado
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.CODE_ACCESS_COARSE_LOCATION);
                // Una vez que se pide aceptar o rechazar el permiso se ejecuta el método "onRequestPermissionsResult" para manejar la respuesta
                // Si el usuario marca "No preguntar más" no se volverá a mostrar este diálogo
                aceptar = false;
            } else {
                Toast.makeText(this, R.string.permisosNO, Toast.LENGTH_LONG).show();
                aceptar = false;
            }
        }
        return aceptar;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.CODE_ACCESS_COARSE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permiso aceptado
                    Toast.makeText(this, R.string.permisosYES, Toast.LENGTH_SHORT).show();
                } else {
                    // Permiso rechazado
                    Toast.makeText(this, R.string.permisosNO, Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            }
        }
    }

    /**
     * Invoca al servicio REST llamando al JSON
     */
    private void invocarServicio(){
        String DATA_URL = "http://192.168.1.35/BaKKeJSON/pedidos.json"; // http://192.168.102.87:3000/pedidos

        final ProgressDialog loading = ProgressDialog.show(this, "Actualizando pedidos", "Cargando...", false, false);

        // Método de la libreria Volley
        JsonObjectRequest jsonObRq = new JsonObjectRequest(Request.Method.GET,
                DATA_URL, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            loading.dismiss();

                            ArrayList<String> pedidoJSON = new ArrayList<String>();
                            JSONArray lista = response.optJSONArray("pedidos");

                            for (int i=0; i<lista.length(); i++){
                                JSONObject json_data = lista.getJSONObject(i);
                                /*String pedido = json_data.getString("fecha") +" "+ json_data.getString("nombre") +" "+
                                        json_data.getString("direccion") +" "+ json_data.getString("orden");

                                pedidoJSON.add(pedido);*/

                                /*Pedido p = new Pedido(json_data.getString("fecha"), json_data.getString("nombre"),
                                        json_data.getString("direccion"), json_data.getString("orden"));

                                Repositorio.getRepositorio().consultaAñadirPedido(p, myContext);*/
                            }
                        }catch(JSONException error) {
                            Toast.makeText(getApplicationContext(), "Error JSON: " +error.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "Error request: " +error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObRq);
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

        permisosUbicacion(); // Si se niegan los permisos se cierra la app

        // Almacenamos el contexto de la actividad para utilizar en las clases internas
        myContext = this;

        //invocarServicio(); // Invocando al servicio REST

        final ArrayList<Pedido> listaPedidos = Repositorio.getRepositorio().consultaListarPedidos(myContext);

        // Inicializa el RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // Crea el Adaptador con los datos de la lista anterior
        final PedidosAdapter adaptador = new PedidosAdapter(listaPedidos);
        // Si la Base de Datos está vacía devuelve true, sino false
        boolean DB_Vacia = Repositorio.getRepositorio().checkEmpty(myContext);

        if(DB_Vacia == false){
            // Asocia el elemento de la lista con una acción al ser pulsado
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acción al pulsar el elemento
                    int position = recyclerView.getChildAdapterPosition(v);

                    Intent intent = new Intent(MainActivity.this, DetallesPedidoActivity.class);

                    // Creamos la información a pasar entre actividades
                    Bundle b = new Bundle();
                    b.putInt("ID", listaPedidos.get(position).getId());

                    // Añadimos la información al intent
                    intent.putExtras(b);
                    // Iniciamos la actividad
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out); // Traslación de la actividad
                }
            });

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
