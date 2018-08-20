package com.aplicaciones.resparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aplicaciones.resparking.controlador.adaptador.ListaVehiculo;
import com.aplicaciones.resparking.controlador.ws.Conexion;
import com.aplicaciones.resparking.controlador.ws.VolleyPeticion;
import com.aplicaciones.resparking.modelo.Vehiculo;

import java.util.Arrays;

public class ListarActivity extends AppCompatActivity {
    private Button btn_volver;

    private ListaVehiculo listaAdaptador;
    private ListView listView;

    private RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        btn_volver = (Button) findViewById(R.id.btn_volver);
        oyente();

    }
    private void oyente() {

        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMaps();

            }
        });
    }

    private void goToMaps() {
        Intent intent=new Intent(this,MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

 /*   private void listarVehiculo(String exID_usuario){
        VolleyPeticion<Vehiculo[]> films= Conexion.listarVehiculo(
                getApplicationContext(), exID_usuario, new Response.Listener<PeliculasJson[]>() {
                    @Override
                    public void onResponse(PeliculasJson[] response) {
                        listaAdaptador=new ListaVehiculo(Arrays.asList(response),);
                        listView.setAdapter(listaAdaptador);
                        //dialog();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast1 =Toast.makeText(getApplicationContext(),getApplicationContext()).getString(R.string.msg_no_busqueda)
                                ,Toast.LENGTH_SHORT);
                    }
                }
        );
        requestQueue.add(films);
    }*/
}
