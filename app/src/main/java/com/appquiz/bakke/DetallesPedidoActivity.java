package com.appquiz.bakke;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetallesPedidoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String TAG = "DetallesPedidoActivity";
    private Context myContext;
    private Bundle bundle;
    private Pedido pedidoID;

    private ImageButton btn_atras;
    private TextView fecha, cliente, direccion, orden;
    private Button aceptarPedido, rechazarPedido;

    private MapView mapa;
    private GoogleMap gMap;
    private static final String MAP_VIEW_BUNDLE_KEY = "google_maps_key";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyLog.d(TAG, "Iniciando onCreate...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pedido);

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

        // Recuperamos la información pasada en el intent
        bundle = this.getIntent().getExtras();

        fecha = (TextView) findViewById(R.id.textView_fechaPedidoText);
        cliente = (TextView) findViewById(R.id.textView_clienteText);
        direccion = (TextView) findViewById(R.id.textView_direccionText);
        orden = (TextView) findViewById(R.id.textView_ordenText);
        mapa = (MapView) findViewById(R.id.mapView);

        // Si el bundle NO es null, rellena los campos de TextView con los datos del pedido
        if (bundle != null) {
            pedidoID = Repositorio.getRepositorio().consultaListarPedidoID(myContext, bundle.getInt("ID"));

            fecha.setText(pedidoID.getFecha());
            cliente.setText(pedidoID.getNombre());
            direccion.setText(pedidoID.getDireccion());
            orden.setText(pedidoID.getOrden());
        }

        /*--------------------------------------------------------*/

        // Crea el mapView para mostrar el mapa
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapa.onCreate(mapViewBundle);
        mapa.getMapAsync(this);

        MyLog.d(TAG, "Cerrando onCreate...");
    }

    @Override
    /**
     * Crea la ubicación en el mapView mediante latitud y longitud
     */
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(15);

        LatLng ubicacion = new LatLng(pedidoID.getLatitud(), pedidoID.getLongitud());

        gMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));
        gMap.addMarker(new MarkerOptions()
                .position(ubicacion)
                .title(pedidoID.getDireccion())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        gMap.getUiSettings().setZoomControlsEnabled(true);
    }

    /**
     * Muestra un AlerDialog para salir de la aplicación
     */
    public void exit(){
        Log.i("ActionBar", "Salir de la aplicación");

        AlertDialog.Builder builder = new AlertDialog.Builder(DetallesPedidoActivity.this);
        builder.setMessage(R.string.rechazarPedido);

        builder.setPositiveButton(R.string.confirmacion, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {

                /*
                ENVIAR PETICIÓN PUT RECHAZANDO EL PEDIDO
                 */

                // Elimina el pedido de la base de datos
                Repositorio.getRepositorio().consultaEliminarPedido(myContext, bundle.getInt("ID"));

                finish();
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out); // Traslación de la actividad
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

        // Aceptar Pedido
        aceptarPedido = (Button) findViewById(R.id.button_aceptarPedido);
        aceptarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* ELIMINAR DE LA BASE DE DATOS EL PEDIDO SELECCIONADO. AGREGARLO A UN NUEVO ARRAY PARA
                    ALMACENARLO EN OTRO RECYVLERVIEW DE PEDIDOS EN CURSO */

                finish();
                overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out); // Traslación de la actividad
            }
        });

        // Rechazar Pedido
        rechazarPedido = (Button) findViewById(R.id.button_rechazarPedido);
        rechazarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
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
