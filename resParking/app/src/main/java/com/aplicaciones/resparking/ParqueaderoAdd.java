package com.aplicaciones.resparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.aplicaciones.resparking.controlador.ws.Conexion;
import com.aplicaciones.resparking.controlador.ws.VolleyPeticion;
import com.aplicaciones.resparking.controlador.ws.VolleyProcesadorResultado;
import com.aplicaciones.resparking.controlador.ws.VolleyTiposError;
import com.aplicaciones.resparking.modelo.Parqueadero;

import java.util.HashMap;

public class ParqueaderoAdd extends AppCompatActivity {

    private EditText txt_nombrePar;
    private EditText txt_coordenadaX;
    private EditText txt_coordenadaY;
    private EditText txt_precio;
    private EditText txt_nPlazas;
    private Button btn_guardarPr;
    private Button btn_volver;

    private RequestQueue requestQueue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parqueadero_add);

        txt_nombrePar=(EditText)findViewById(R.id.txt_nombrePar);
        txt_coordenadaX=(EditText)findViewById(R.id.txt_coordenadaX);
        txt_coordenadaY=(EditText)findViewById(R.id.txt_coordenadaY);
        txt_precio=(EditText)findViewById(R.id.txt_pHora);
        txt_nPlazas=(EditText)findViewById(R.id.txt_nPlaza);
        btn_guardarPr=(Button) findViewById(R.id.btn_guardarPr);
        btn_volver=(Button) findViewById(R.id.btn_volverPr);

        requestQueue= Volley.newRequestQueue(getApplicationContext());
        oyente();
    }

    private void oyente() {


        this.btn_guardarPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String external=MapsActivity.ID_EXTERNAL;
                String nombre=txt_nombrePar.getText().toString();
                String coordenadax=txt_coordenadaX.getText().toString();
                String coordenaday=txt_coordenadaY.getText().toString();
                String precio=txt_precio.getText().toString();
                String nPlazas=txt_nPlazas.getText().toString();
                if (nombre.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Falta nombre",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (coordenadax.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Falta coordenada X",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (coordenaday.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Falta coordenada Y",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (precio.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Falta precio hora",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (nPlazas.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Falta numero de plazas",Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String,String> mapa= new HashMap<>();
                mapa.put("clave",external);
                mapa.put("nombre",nombre);
                mapa.put("coordenada_x",coordenadax);
                mapa.put("coordenada_y",coordenaday);
                mapa.put("precio",precio);
                mapa.put("plazas",nPlazas);

                VolleyPeticion<Parqueadero> regPar= Conexion.registrarParqueadero(
                        getApplicationContext(),
                        mapa,
                        new Response.Listener<Parqueadero>() {
                            @Override
                            public void onResponse(Parqueadero response) {

                                Toast.makeText(getApplicationContext(),"Parqueadero guardado correctamente",Toast.LENGTH_SHORT).show();

                                goToAdministrar();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                VolleyTiposError errores= VolleyProcesadorResultado.parseErrorResponse(error);

                            }
                        }
                );
                requestQueue.add(regPar);

            }
        });

        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               goToAdministrar();

            }
        });
    }

    private void goToAdministrar() {
        Intent intent=new Intent(this,AdministradorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
