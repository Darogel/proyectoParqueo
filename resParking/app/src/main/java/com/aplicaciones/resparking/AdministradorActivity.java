package com.aplicaciones.resparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


public class AdministradorActivity extends AppCompatActivity {
    /**
     * Variables utilizadas para utilizar datos del Layout activity_administrador
     */
    private ImageButton btn_parqueadero;
    private ImageButton btn_plaza;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        btn_parqueadero = (ImageButton) findViewById(R.id.btn_parqueadero);
        btn_plaza = (ImageButton) findViewById(R.id.btn_plaza);
        oyente();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings1) {
            cerrarSesion();
        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * Metodo implementado en llamar la actividad ParqueaderoAdd
     * Permite registrar un nuevo parqueadero al administrador
     */
    private void parqueaderoAdd() {
        Intent intent = new Intent(this, ParqueaderoAdd.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Metodo implementado en llamar la actividad PlazaAdd
     * Permite registrar un nueva plaza a un parqueadero del administrador
     */
    private void plazaAdd() {
        Intent intent = new Intent(this, PlazaAdd.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    /**
     * Metodo utilizado para asignar actividad que realizara:
     * btn_parqueadero
     * btn_plaza
     * btn_reservacion
     */
    private void oyente() {
        /**
         * Metodo utilizado llamar metodo parqueaderoAdd
         */
        this.btn_parqueadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parqueaderoAdd();

            }
        });
        /**
         * Metodo utilizado llamar metodo plazaAdd
         */
        this.btn_plaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plazaAdd();

            }
        });
    }
    /**
     * Metodo implementado para que administrador cierre sesion
     * Se declaran como vacias las variables staticas
     * Se llama la actividad LoginAdministrador
     */
    private void cerrarSesion() {
        Toast toast1 = Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.error_busqueda)
                , Toast.LENGTH_SHORT);
        MapsActivity.TOKEN = "";
        MapsActivity.ID_EXTERNAL = "";
        Intent intent = new Intent(this, LoginAdministrador.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Metodo implementado para regresar a la actividad principal
     */
    private void goToMaps() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
