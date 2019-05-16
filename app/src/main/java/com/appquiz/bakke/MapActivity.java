package com.appquiz.bakke;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.GoogleMap;

import java.sql.Connection;

public class MapActivity extends AppCompatActivity implements MapasFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Fragment fragmento = new MapasFragment();
        // Coloca el fragment en esta activity
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor, fragmento).commit();
    }

    /*--------------------- MENÚ TOOLBAR ------------------------------------------------*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Log.i("ActionBar", "Finalizar Pedido");

                // GUARDAR EN PEDIDOS FINALIZADOS. CONTROLAR AL PULSAR "ATRÁS"

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
