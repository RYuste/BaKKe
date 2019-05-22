package com.appquiz.bakke.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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

import com.appquiz.bakke.Constants;
import com.appquiz.bakke.MyLog;
import com.appquiz.bakke.Model.Pedido;
import com.appquiz.bakke.R;
import com.appquiz.bakke.Model.Repositorio;
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
    private int estado;

    private ImageButton btn_atras;
    private TextView fecha, cliente, direccion, orden;
    private Button aceptarPedido, finalizarPedido, rechazarPedido;

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

            // Dependiendo del estado habilita unos botones u otros
            estado = pedidoID.getEstado();

            aceptarPedido = (Button) findViewById(R.id.button_aceptarPedido);
            finalizarPedido = (Button) findViewById(R.id.button_finalizarPedido);
            rechazarPedido = (Button) findViewById(R.id.button_rechazarPedido);

            Drawable img = myContext.getResources().getDrawable(R.drawable.borrar_disabled);

            // Pedido en Curso
            if(estado == 1){
                aceptarPedido.setEnabled(false);
                aceptarPedido.setVisibility(View.INVISIBLE);

                finalizarPedido.setEnabled(true);
                finalizarPedido.setVisibility(View.VISIBLE);

                rechazarPedido.setEnabled(false);
                rechazarPedido.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisabled));
                rechazarPedido.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

            // Pedido finalizado
            }else if(estado == 2){
                aceptarPedido.setEnabled(false);
                aceptarPedido.setVisibility(View.INVISIBLE);

                finalizarPedido.setEnabled(false);
                finalizarPedido.setVisibility(View.VISIBLE);
                finalizarPedido.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisabled));
                finalizarPedido.setTextColor(R.color.colorDisabledPressed);
                finalizarPedido.setText(R.string.pedFinalizado);

                rechazarPedido.setEnabled(false);
                rechazarPedido.setBackgroundTintList(getResources().getColorStateList(R.color.colorDisabled));
                rechazarPedido.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
            }else{
                aceptarPedido.setEnabled(true);
                aceptarPedido.setVisibility(View.VISIBLE);

                finalizarPedido.setEnabled(false);
                finalizarPedido.setVisibility(View.INVISIBLE);

                rechazarPedido.setEnabled(true);
            }
        }

        /*--------------------------------------------------------*/

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
     * Crea la ubicación en el mapView mediante latitud y longitud
     */
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        gMap.setMinZoomPreference(14);

        LatLng ubicacion = new LatLng(pedidoID.getLatitud(), pedidoID.getLongitud());

        gMap.moveCamera(CameraUpdateFactory.newLatLng(ubicacion));
        gMap.addMarker(new MarkerOptions()
                .position(ubicacion)
                .title(pedidoID.getDireccion())
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));

        gMap.getUiSettings().setZoomControlsEnabled(true);
    }

    /**
     * Muestra un AlerDialog para rechazar un pedido
     */
    public void exit(){
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

                // Modifica el estado del pedido para almacenarlo en Pedidos en Curso (1)
                estado = 1;
                Repositorio.getRepositorio().consultaEstadoPedido(myContext, estado, bundle.getInt("ID"));

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

        // Finalizar Pedido
        finalizarPedido = (Button) findViewById(R.id.button_finalizarPedido);
        finalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Modifica el estado del pedido para almacenarlo en Pedidos Finalizados (2)
                estado = 2;
                Repositorio.getRepositorio().consultaEstadoPedido(myContext, estado, bundle.getInt("ID"));

                Snackbar.make(v, R.string.pedidoFinalizado, Snackbar.LENGTH_INDEFINITE)
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
