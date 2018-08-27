package com.aplicaciones.resparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.aplicaciones.resparking.controlador.ws.Conexion;
import com.aplicaciones.resparking.controlador.ws.VolleyPeticion;
import com.aplicaciones.resparking.controlador.ws.VolleyProcesadorResultado;
import com.aplicaciones.resparking.controlador.ws.VolleyTiposError;
import com.aplicaciones.resparking.modelo.Plaza;

import java.util.HashMap;

public class PlazaAdd extends AppCompatActivity {

    private Spinner spinner;
    private Spinner spinner1;

    private Button btn_guardarPl;
    private Button btn_volverPl;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaza_add);

        btn_guardarPl=(Button) findViewById(R.id.btn_guardarPl);
        btn_volverPl=(Button) findViewById(R.id.btn_volverPl);

        spinner = (Spinner) findViewById(R.id.txt_plazaS);
        String[] puesto = {"Seleccionar","1","2","3","4","5","6","7","8","9","10"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, puesto));



        spinner1 = (Spinner) findViewById(R.id.txt_tipoS);
        String[] tipo = {"Seleccionar","Con techo","Sin techo"};
        spinner1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo));

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        oyente();
    }

    private void oyente() {


        this.btn_guardarPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String numero_p= spinner.getSelectedItem().toString();
                final String tipo= spinner1.getSelectedItem().toString();




                HashMap<String,String> mapa= new HashMap<>();
                mapa.put("clave",
                        MapsActivity.ID_EXTERNAL);
                mapa.put("tipo",tipo);
                mapa.put("numero",numero_p);


                VolleyPeticion<Plaza> regPlaz= Conexion.registrarPlaza(
                        getApplicationContext(),
                        mapa,
                        new Response.Listener<Plaza>() {
                            @Override
                            public void onResponse(Plaza response) {
                                Toast.makeText(getApplicationContext(),"Se ha añadido su plaza",Toast.LENGTH_SHORT).show();
                                administrador();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                VolleyTiposError errores= VolleyProcesadorResultado.parseErrorResponse(error);

                            }
                        }
                );
                requestQueue.add(regPlaz);
            }
        });

        this.btn_volverPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                administrador();

            }
        });

    }

    private void administrador() {
        Intent intent=new Intent(this,AdministradorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
