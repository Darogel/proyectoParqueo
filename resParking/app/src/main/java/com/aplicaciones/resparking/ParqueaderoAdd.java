package com.aplicaciones.resparking;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import com.aplicaciones.resparking.modelo.Parqueadero;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

public class ParqueaderoAdd extends AppCompatActivity {

    /**
     * Variable static utiliazada Para almacenar La Ubicacion del Nuevo Parqueadero
     */
    public static  LatLng MAKER;

    /**
     * Variables utilizadas Para Guardar Latitud Longitu del Parquedaro
     */
    private Double LATITUD;
    private Double LONGITUD;
    private String makr;

    /**
     * Variables para recibir datos del Layaut activity_parqueadero_add
     */
    private EditText txt_nombrePar;
    private EditText txt_coordenadaX;
    private EditText txt_precio;
    private EditText txt_nPlazas;
    private Button btn_guardarPr;
    private Button btn_volver;

    /**
     * Variable utilizada en enviar recibir datos desde la Base del Servicio
     */
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parqueadero_add);

        miUbicacion();
        txt_nombrePar = (EditText) findViewById(R.id.txt_nombreP);
        txt_coordenadaX = (EditText) findViewById(R.id.txt_coordenadaX);
        txt_coordenadaX.setText(makr);
        txt_precio = (EditText) findViewById(R.id.txt_pHora);
        txt_nPlazas = (EditText) findViewById(R.id.txt_nPlaza);
        btn_guardarPr = (Button) findViewById(R.id.btn_guardarPr);
        btn_volver = (Button) findViewById(R.id.btn_volverPr);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        oyente();
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    /**
     * Metodo implementado para Obtener Ubicacion Actual del Administrador
     * Teniendo Permisos de Localizacion Establecido en el Manifest
     * Obtiene la Ubicacion Precisa del Administrador
     * Utilizada para añadir un parqueadero en ubicacion del Administrador
     * Asigna Valores en Variable statica y de latitud y longitud
     */
    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        MAKER = new  LatLng(location.getLatitude(),location.getLongitude());
        LATITUD = location.getLatitude();
        LONGITUD = location.getLongitude();
        makr = String.valueOf(MAKER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                10000, 0, locationListener);

    }

    /**
     * Metodo utilizado para Asignar Actividad que realizara:
     * btn_Guardar
     * btn_Volver
     */
    private void oyente() {
        /**
         * Metodo para Guardar Datos en La Base del Servicio
         * Comprueba que los Campos no esten vacios
         * Llama la Peticion registrar Parqueadero Implemtado en Clase Conexion
         */
        this.btn_guardarPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String makr = String.valueOf(MAKER);
                String external = MapsActivity.ID_EXTERNAL;
                String nombre = txt_nombrePar.getText().toString();
                String precio = txt_precio.getText().toString();
                String nPlazas = txt_nPlazas.getText().toString();
                String lat = String.valueOf(LATITUD);
                String lon = String.valueOf(LONGITUD);
                if (nombre.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Falta nombre", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (precio.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Falta precio hora", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (nPlazas.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Falta numero de plazas", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, String> mapa = new HashMap<>();
                mapa.put("clave", external);
                mapa.put("nombre", nombre);
                mapa.put("coordenada_x", lat);
                mapa.put("coordenada_y",lon);
                mapa.put("precio", precio);
                mapa.put("plazas", nPlazas);

                VolleyPeticion<Parqueadero> regPar = Conexion.registrarParqueadero(
                        getApplicationContext(),
                        mapa,
                        new Response.Listener<Parqueadero>() {
                            @Override
                            public void onResponse(Parqueadero response) {

                                Toast.makeText(getApplicationContext(), "Parqueadero guardado correctamente", Toast.LENGTH_SHORT).show();

                                goToAdministrar();
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
         * Metodo Implementado para Volver a la Actividad De Administrador
         */
        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAdministrar();

            }
        });
    }

    /**
     * Metodo implementado para Llamar la actividad Administrado Activity
     * activity donde el administrador maneja su parqueadero
     */
    private void goToAdministrar() {
        Intent intent = new Intent(this, AdministradorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
