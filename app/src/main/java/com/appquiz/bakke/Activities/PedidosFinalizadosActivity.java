package com.appquiz.bakke.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;

import com.appquiz.bakke.Adapters.PedidosAdapter;
import com.appquiz.bakke.Adapters.PedidosFinalizadosAdapter;
import com.appquiz.bakke.Model.Pedido;
import com.appquiz.bakke.Model.Repositorio;
import com.appquiz.bakke.MyLog;
import com.appquiz.bakke.R;

import java.util.ArrayList;

public class PedidosFinalizadosActivity extends AppCompatActivity {

    private String TAG = "PedidosFinalizadosActivity";
    private Context myContext;

    public ImageButton btn_atras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos_finalizados);

        btn_atras = findViewById(R.id.imageButton_atras);
        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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

        final ArrayList<Pedido> listaPedidosFinalizados = Repositorio.getRepositorio().consultaListarPedidosFinalizados(myContext);

        // Inicializa el RecyclerView
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView_finalizados);
        // Crea el Adaptador con los datos de la lista anterior
        final PedidosFinalizadosAdapter adaptador = new PedidosFinalizadosAdapter(listaPedidosFinalizados);
        // Si la Base de Datos está vacía devuelve true, sino false
        boolean DB_Vacia = Repositorio.getRepositorio().checkEmpty(myContext);

        if(DB_Vacia == false){
            // Asocia el elemento de la lista con una acción al ser pulsado
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Acción al pulsar el elemento
                    int position = recyclerView.getChildAdapterPosition(v);

                    Intent intent = new Intent(PedidosFinalizadosActivity.this, DetallesPedidoActivity.class);

                    // Creamos la información a pasar entre actividades
                    Bundle b = new Bundle();
                    b.putInt("ID", listaPedidosFinalizados.get(position).getId());

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
