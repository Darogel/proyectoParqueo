package com.aplicaciones.resparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.aplicaciones.resparking.controlador.adaptador.ListaParqueaderoPlaza;
import com.aplicaciones.resparking.controlador.ws.Conexion;
import com.aplicaciones.resparking.controlador.ws.VolleyPeticion;
import com.aplicaciones.resparking.controlador.ws.VolleyProcesadorResultado;
import com.aplicaciones.resparking.controlador.ws.VolleyTiposError;
import com.aplicaciones.resparking.modelo.Parqueadero;
import com.aplicaciones.resparking.modelo.Plaza;


import java.util.Arrays;
import java.util.HashMap;

public class PlazaAdd extends AppCompatActivity {

    /**
     * Variable estatica implementada para Obtener  External id del Parqueadero
     */
    public static String ID_EXTERNAL_PARQUEADERO = "";

    /**
     * Variables implementadas para recibir datos del Layout activity_plaza_add
     */
    private Spinner spinner;
    private Spinner spinner1;
    private Button btn_guardarPl;
    private Button btn_volverPl;
    private Spinner spinnerParqueadero;

    /**
     * Variable del tipo Lista Parqueadero plaza
     */
    private ListaParqueaderoPlaza listaParqueaderoPlaza;

    /**
     * Variable implementada para enviar y revibir datos de la base de datos
      */
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaza_add);

        btn_guardarPl = (Button) findViewById(R.id.btn_guardarPl);
        btn_volverPl = (Button) findViewById(R.id.btn_volverPl);

        spinner = (Spinner) findViewById(R.id.txt_plazaS);
        String[] puesto = {"Seleccionar", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, puesto));


        spinner1 = (Spinner) findViewById(R.id.txt_tipoS);
        String[] tipo = {"Seleccionar", "Con techo", "Sin techo"};
        spinner1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo));

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        spinnerParqueadero = (Spinner) findViewById(R.id.cbx_parq);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        consultaParqueadero();

        oyente();
    }

    /**
     * Metodo implementado para asignar la actividad que realizara:
     * btn_guardarPl
     * btn_volverPl
     */
    private void oyente() {

        /**
         * Metodo implementado para guardar los datos en la base de datos del Host
         * Comprueba que reciba todos los datos
         * LLama el metodo Registrar plaza implemeentado en la clase Conexion
         */
        this.btn_guardarPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String numero_p = spinner.getSelectedItem().toString();
                final String tipo = spinner1.getSelectedItem().toString();


                HashMap<String, String> mapa = new HashMap<>();
                mapa.put("clave",
                        MapsActivity.ID_EXTERNAL);
                mapa.put("tipo", tipo);
                mapa.put("numero", numero_p);
                mapa.put("idP",ID_EXTERNAL_PARQUEADERO);


                VolleyPeticion<Plaza> regPlaz = Conexion.registrarPlaza(
                        getApplicationContext(),
                        mapa,
                        new Response.Listener<Plaza>() {
                            @Override
                            public void onResponse(Plaza response) {
                                Toast.makeText(getApplicationContext(), "Se ha añadido su plaza", Toast.LENGTH_SHORT).show();
                                administrador();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                VolleyTiposError errores = VolleyProcesadorResultado.parseErrorResponse(error);

                            }
                        }
                );
                requestQueue.add(regPlaz);
            }
        });

        /**
         * Metodo implementado para volver a la Actividad Administrador
         */
        this.btn_volverPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                administrador();

            }
        });

    }

    /**
     * Metodo implementado para llamar la Actividad Administrador¿
     * activity donde el administrador maneja su parqueo
     */
    private void administrador() {
        Intent intent = new Intent(this, AdministradorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Metodo implementado para Realizar una VolleyPeticion de tipo Get Parqueadero
     * el cual llama al metodo listarParqueadero de la clase Conexion
     */
    private void consultaParqueadero() {
        final VolleyPeticion<Parqueadero[]> parqueadero = Conexion.listarParqueadero(
                getApplicationContext(),
                MapsActivity.ID_EXTERNAL,
                new Response.Listener<Parqueadero[]>() {
                    @Override
                    public void onResponse(Parqueadero[] response) {

                        listaParqueaderoPlaza = new ListaParqueaderoPlaza(Arrays.asList(response), getApplicationContext());
                        listaParqueaderoPlaza.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerParqueadero.setAdapter(listaParqueaderoPlaza);
                        spinnerParqueadero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                Parqueadero parqueaderoon = (Parqueadero) adapterView.getItemAtPosition(i);
                                ID_EXTERNAL_PARQUEADERO = parqueaderoon.external_id;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toas1 = Toast.makeText(getApplicationContext(), getString(R.string.msg_no_busqueda), Toast.LENGTH_SHORT);
                        toas1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toas1.show();
                        return;
                    }
                }
        );
        requestQueue.add(parqueadero);
    }
}
