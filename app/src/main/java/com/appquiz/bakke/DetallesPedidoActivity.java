package com.appquiz.bakke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;

public class DetallesPedidoActivity extends AppCompatActivity {

    public ImageButton btn_atras;

    /**
     * Acción al pulsar el botón del dispositivo keyDown
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        onBackPressed();
        overridePendingTransition(R.anim.zoom_forward_in, R.anim.zoom_forward_out); // Traslación de la actividad
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }
}
