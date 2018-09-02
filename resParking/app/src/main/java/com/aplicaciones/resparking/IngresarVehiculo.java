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

public class IngresarVehiculo extends AppCompatActivity {

    private EditText txt_nPlaca;
    private Button btn_guardarPr;
    private Button btn_volver;

    private RequestQueue requestQueue;

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

    private void oyente() {


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

        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMap();

            }
        });
    }

    public void limpiarTexto() {
        txt_nPlaca.getText().clear();
    }

    private void goToMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
