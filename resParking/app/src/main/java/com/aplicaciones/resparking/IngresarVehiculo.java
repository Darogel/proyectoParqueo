package com.aplicaciones.resparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.aplicaciones.resparking.controlador.ws.Conexion;
import com.aplicaciones.resparking.controlador.ws.VolleyPeticion;
import com.aplicaciones.resparking.controlador.ws.VolleyProcesadorResultado;
import com.aplicaciones.resparking.controlador.ws.VolleyTiposError;
import com.aplicaciones.resparking.modelo.Vehiculo;

import java.util.HashMap;

/**
 * Clase implementada para ingreso de vehiculo por parte del usuario
 * Extendible de la super clase AppCompatActivity
 */
public class IngresarVehiculo extends AppCompatActivity {

    /**
     * Variable implementada para recibir datos del layout activity_ingresar_vehiculo
     */
    private EditText txt_nPlaca;
    /**
     * Variable implementada para recibir datos del layout activity_ingresar_vehiculo
     */
    private Button btn_guardarPr;
    /**
     * Variable implementada para recibir datos del layout activity_ingresar_vehiculo
     */
    private Button btn_volver;

    /**
     * Variable utilizada para crear una cola de peticiones hacia la base de datos del Host
     */
    private RequestQueue requestQueue;

    /**
     * Metodo implementado para inicio de la actividad
     * Se llama un recurso de diseño que define su UI
     * Recupera  widgets del layout activity_ingresar_vehiculo
     * @param savedInstanceState Guarda el estado de la aplicacion
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_vehiculo);
        txt_nPlaca = (EditText) findViewById(R.id.txt_placaV);
        btn_guardarPr = (Button) findViewById(R.id.btn_guardarV);
        btn_volver = (Button) findViewById(R.id.btn_volverV);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        oyente();
    }
    /**
     * Metodo utilizado para asignar la actividad que realizara:
     * btn_guardarPr
     * btn_volver
     */
    private void oyente() {

        /**
         * Metodo para guardar datos en La base de datos del Host
         * Comprueba que los campos no esten vacios
         * Llama la peticion registrarVehiculo implementado en la clase Conexion
         */
        this.btn_guardarPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String external = MapsActivity.ID_EXTERNAL;
                String nPlaca = txt_nPlaca.getText().toString();
                if (nPlaca.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Este Campo No Puede estar Vacio", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> mapa = new HashMap<>();
                mapa.put("clave", MapsActivity.ID_EXTERNAL_USER);
                mapa.put("placa", nPlaca);

                VolleyPeticion<Vehiculo> regPar = Conexion.registrarVehiculo(
                        getApplicationContext(),
                        mapa,
                        new Response.Listener<Vehiculo>() {
                            @Override
                            public void onResponse(Vehiculo response) {

                                Toast.makeText(getApplicationContext(), "Se ha añadido Su Vehiculo", Toast.LENGTH_SHORT).show();

                                limpiarTexto();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                VolleyTiposError errores = VolleyProcesadorResultado.parseErrorResponse(error);

                            }
                        }
                );
                requestQueue.add(regPar);

            }
        });
        /**
         * Metodo para ejecucion de volver a la actividad principal
         */
        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMap();

            }
        });
    }

    /**
     * Metodo implementado para resetear txt_nPlaca
     */
    public void limpiarTexto() {
        txt_nPlaca.getText().clear();
    }

    /**
     * Metodo implementado para regresar a la actividad principal
     */
    private void goToMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
