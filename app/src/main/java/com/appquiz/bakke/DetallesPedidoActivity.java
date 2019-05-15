package com.appquiz.bakke;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetallesPedidoActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String TAG = "DetallesPedidoActivity";
    private Context myContext;
    private Pedido pedidoID;

    private ImageButton btn_atras;
    private TextView fecha, cliente, direccion, orden;
    private Button aceptarPedido;

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
        final Bundle bundle = this.getIntent().getExtras();

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

        // Mapa de la ruta
        /*aceptarPedido = (Button) findViewById(R.id.button_aceptarPedido);
        aceptarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetallesPedidoActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });*/

        MyLog.d(TAG, "Cerrando onCreate...");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(15);

        LatLng ubicacion = new LatLng(pedidoID.getLatitud(), pedidoID.getLongitud());
        gMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));
        gMap.addMarker(new MarkerOptions()
                .position(ubicacion)
                .title(pedidoID.getDireccion())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
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
