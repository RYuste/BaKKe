package com.appquiz.bakke.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.appquiz.bakke.Adapters.PedidosAdapter;
import com.appquiz.bakke.Constants;
import com.appquiz.bakke.Model.Pedido;
import com.appquiz.bakke.Model.Repositorio;
import com.appquiz.bakke.MyLog;
import com.appquiz.bakke.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetallesActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String TAG = "DetallesActivity";
    private Context myContext;
    private RequestQueue requestQueue;

    private Intent intent;
    private int id_pedido;
    private String fecha;
    private String nombre;
    private double latCliente;
    private double lonCliente;
    private double latProducto;
    private double lonProducto;
    private String dirCliente;
    private String dirProdcuto;
    private String orden;
    private int estado;

    private ImageButton btn_atras;
    private TextView fechaTextView, clienteTextView, direccionTextView, ordenTextView;
    private Button aceptarPedido, rechazarPedido;

    private MapView mapa;
    private GoogleMap gMap;

    /**
     * Acción al pulsar el botón del dispositivo keyDown
     *
     * @param keyCode
     * @param event
     * @return onKeyDown
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        onBackPressed();
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out); // Traslación de la actividad
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyLog.d(TAG, "Iniciando onCreate...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        btn_atras = findViewById(R.id.imageButton_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out); // Traslación de la actividad
            }
        });

        /*----------------------------------------------------------------------*/

        // Almacenamos el contexto de la actividad para utilizar en las clases internas
        myContext = this;

        // Recuperamos el intent que lo llamó
        intent = getIntent();
        id_pedido = intent.getIntExtra(Constants.EXTRA_ID, 0);
        fecha = intent.getStringExtra(Constants.EXTRA_FECHA);
        nombre = intent.getStringExtra(Constants.EXTRA_NOMBRE);
        latCliente = intent.getDoubleExtra(Constants.EXTRA_LAT_CLIENTE, 0);
        lonCliente = intent.getDoubleExtra(Constants.EXTRA_LON_CLIENTE, 0);
        latProducto = intent.getDoubleExtra(Constants.EXTRA_LAT_PRODUCTO, 0);
        lonProducto = intent.getDoubleExtra(Constants.EXTRA_LON_PRODUCTO, 0);
        dirCliente = intent.getStringExtra(Constants.EXTRA_DIRC_CLIENTE);
        dirProdcuto = intent.getStringExtra(Constants.EXTRA_DIRC_PRODUCTO);
        orden = intent.getStringExtra(Constants.EXTRA_OREN);

        /*-------------------------------------------------------------------------------------------------------*/

        fechaTextView = (TextView) findViewById(R.id.textView_fechaPedidoText);
        clienteTextView = (TextView) findViewById(R.id.textView_clienteText);
        direccionTextView = (TextView) findViewById(R.id.textView_direccionText);
        ordenTextView = (TextView) findViewById(R.id.textView_ordenText);

        mapa = (MapView) findViewById(R.id.mapView);
        /*-----------------------------------------------------------------*/
        fechaTextView.setText(fecha);
        clienteTextView.setText(nombre);
        direccionTextView.setText(dirCliente);
        ordenTextView.setText(orden);

        /*--------------------------------------------------------------------------------*/

        // Crea el mapView para mostrar el mapa
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Constants.MAP_VIEW_BUNDLE_KEY);
        }
        mapa.onCreate(mapViewBundle);
        mapa.getMapAsync(this);

        MyLog.d(TAG, "Cerrando onCreate...");
    }

    @Override
    /**
     * Crea las ubicaciones en el mapView mediante latitud y longitud
     */
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(12);

        LatLng ubicacionCliente = new LatLng(latCliente, lonCliente);
        LatLng ubicacionProducto = new LatLng(latProducto, lonProducto);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacionCliente));

        gMap.addMarker(new MarkerOptions()
                .position(ubicacionCliente)
                .title("Cliente: "+dirCliente)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        gMap.addMarker(new MarkerOptions()
                .position(ubicacionProducto)
                .title("Producto: "+dirProdcuto)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

        // Botón de zoom
        gMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    protected void onStart() {
        MyLog.d(TAG, "Iniciando onStart...");

        super.onStart();
        mapa.onStart();

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
        mapa.onResume();

        final Pedido pedido = new Pedido(id_pedido, fecha, nombre, latCliente, lonCliente, latProducto, lonProducto,
                dirCliente, dirProdcuto, orden, estado);

        // Aceptar Pedido
        aceptarPedido = (Button) findViewById(R.id.button_aceptarPedido);
        aceptarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Repositorio.getRepositorio().consultaAñadirPedido(pedido, myContext);

                // Modifica el estado del pedido para almacenarlo en Pedidos en Curso (1)
                estado = 1;
                Repositorio.getRepositorio().consultaEstadoPedido(myContext, estado, id_pedido);

                Snackbar.make(v, R.string.pedidoAceptado, Snackbar.LENGTH_INDEFINITE)
                        .setActionTextColor(getResources().getColor(R.color.colorSecond))
                        .setAction("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out); // Traslación de la actividad
                            }
                        }).show();
            }
        });

        MyLog.d(TAG, "Cerrando onResume...");
    }

    @Override
    protected void onPause() {
        MyLog.d(TAG, "Iniciando onPause...");

        super.onPause();
        mapa.onPause();

        MyLog.d(TAG, "Cerrando onPause...");
    }

    @Override
    protected void onStop() {
        MyLog.d(TAG, "Iniciando onStop...");

        super.onStop();
        mapa.onStop();

        MyLog.d(TAG, "Cerrando onStop...");
    }

    @Override
    protected void onDestroy() {
        MyLog.d(TAG, "Iniciando onDestroy...");

        super.onDestroy();
        mapa.onDestroy();

        MyLog.d(TAG, "Cerrando onDestroy...");
    }

}