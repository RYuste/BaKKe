package com.appquiz.bakke;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DetallesPedidoActivity extends AppCompatActivity {

    private String TAG = "DetallesPedidoActivity";
    private Context myContext;

    private ImageButton btn_atras;
    private TextView fecha, cliente, direccion, orden;

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

        // Si el bundle NO es null, rellena los campos de TextView con los datos del pedido
        if (bundle != null) {
            Pedido pedidoID = Repositorio.getRepositorio().consultaListarPedidoID(myContext, bundle.getInt("ID"));
            fecha.setText(pedidoID.getFecha());
            cliente.setText(pedidoID.getNombre());
            direccion.setText(pedidoID.getDireccion());
            orden.setText(pedidoID.getOrden());
        }

        MyLog.d(TAG, "Cerrando onCreate...");
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
