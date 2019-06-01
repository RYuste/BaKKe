package com.appquiz.bakke.Activities;

import android.Manifest;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
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
import com.appquiz.bakke.Constants;
import com.appquiz.bakke.MyLog;
import com.appquiz.bakke.Model.Pedido;
import com.appquiz.bakke.Adapters.PedidosAdapter;
import com.appquiz.bakke.R;
import com.appquiz.bakke.Model.Repositorio;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements PedidosAdapter.OnItemClickListener {
    private String TAG = "MainActivity";

    FloatingActionMenu fabMenu;
    public ImageButton btn_exit, btn_recarga;

    private RequestQueue requestQueue;

    private ArrayList<Pedido> listaPedidosJSON;
    private RecyclerView recyclerView;
    private PedidosAdapter adaptador;
    private RecyclerView.LayoutManager layoutManager;

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

        btn_recarga = findViewById(R.id.imageButton_recarga);
        btn_recarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaPedidosJSON.clear();
                adaptador = new PedidosAdapter(MainActivity.this, listaPedidosJSON);
                adaptador.notifyDataSetChanged();

                invocarServicioGET();
            }
        });

        // Si se niegan los permisos se cierra la app
        permisosUbicacion();

        layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);

        listaPedidosJSON = new ArrayList<>();
        invocarServicioGET();
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
    private void invocarServicioGET(){
        requestQueue = Volley.newRequestQueue(this);
        final ProgressDialog loading = ProgressDialog.show(this, getString(R.string.actualizarPedidos), getString(R.string.cargando), false, false);

        // Método de la libreria Volley
        JsonObjectRequest jsonObRq = new JsonObjectRequest(Request.Method.GET,
                Constants.DATA_URL, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                       try {
                            JSONArray listaJSON = response.getJSONArray("pedidos");

                            for (int i=0; i<listaJSON.length(); i++){
                                JSONObject json_data = listaJSON.getJSONObject(i);

                                Pedido p = new Pedido(
                                        json_data.getInt("id_pedido"),
                                        json_data.getString("fecha"),
                                        json_data.getString("nombre"),
                                        json_data.getDouble("latitudCliente"),
                                        json_data.getDouble("longitudCliente"),
                                        json_data.getDouble("latitudProducto"),
                                        json_data.getDouble("longitudProducto"),
                                        json_data.getString("direccionCliente"),
                                        json_data.getString("direccionProducto"),
                                        json_data.getString("orden"),
                                        json_data.getInt("estado"));

                                listaPedidosJSON.add(p);
                            }
                            adaptador = new PedidosAdapter(MainActivity.this, listaPedidosJSON);
                            recyclerView.setAdapter(adaptador);
                            adaptador.setOnItemClickListener(MainActivity.this);

                            loading.dismiss();
                       }catch(JSONException error) {
                           loading.dismiss();
                           Toast.makeText(getApplicationContext(), getString(R.string.errorJSON), Toast.LENGTH_SHORT).show();
                       }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.errorRequest), Toast.LENGTH_SHORT).show();
                    }

                });
        requestQueue.add(jsonObRq);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetallesActivity.class);
        Pedido clickedItem = listaPedidosJSON.get(position);

        intent.putExtra(Constants.EXTRA_ID, clickedItem.getId_pedido());
        intent.putExtra(Constants.EXTRA_FECHA, clickedItem.getFecha());
        intent.putExtra(Constants.EXTRA_NOMBRE, clickedItem.getNombre());
        intent.putExtra(Constants.EXTRA_LAT_CLIENTE, clickedItem.getLatitudCliente());
        intent.putExtra(Constants.EXTRA_LON_CLIENTE, clickedItem.getLongitudCliente());
        intent.putExtra(Constants.EXTRA_LAT_PRODUCTO, clickedItem.getLatitudProducto());
        intent.putExtra(Constants.EXTRA_LON_PRODUCTO, clickedItem.getLongitudProducto());
        intent.putExtra(Constants.EXTRA_DIRC_CLIENTE, clickedItem.getDireccionCliente());
        intent.putExtra(Constants.EXTRA_DIRC_PRODUCTO, clickedItem.getDireccionProducto());
        intent.putExtra(Constants.EXTRA_OREN, clickedItem.getOrden());
        intent.putExtra(Constants.EXTRA_ESTADO, clickedItem.getEstado());

        startActivity(intent);
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
