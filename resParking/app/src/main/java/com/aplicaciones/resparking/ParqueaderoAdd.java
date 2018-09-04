package com.aplicaciones.resparking;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.aplicaciones.resparking.controlador.ws.Conexion;
import com.aplicaciones.resparking.controlador.ws.VolleyPeticion;
import com.aplicaciones.resparking.controlador.ws.VolleyProcesadorResultado;
import com.aplicaciones.resparking.controlador.ws.VolleyTiposError;
import com.aplicaciones.resparking.modelo.Parqueadero;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Clase implementada para agregar un nuevo parqueadero por parte de un administrador
 * Extendible de la Super Clase AppCompatActivity
 */
public class ParqueaderoAdd extends AppCompatActivity {

    /**
     * Variables utilizada Para guardar Latitud del parquedero
     */
    private Double LATITUD;
    /**
     * Variable utilizada Para guardar  Longitud del parquedero
     */
    private Double LONGITUD;
    /**
     * Variable static utilizada para almacenar La ubicacion del nuevo parqueadero
     */
    public static LatLng MAKER;
    /**
     * Variable Tipo String utilizada para almacenar La ubicacion del nuevo parqueadero
     */
    private String makr;
    /**
     * Variable para recibir datos del Layaut activity_parqueadero_add
     */
    private EditText txt_nombrePar;
    /**
     * Variable para recibir datos del Layaut activity_parqueadero_add
     */
    private EditText txt_coordenadaX;
    /**
     * Variable para recibir datos del Layaut activity_parqueadero_add
     */
    private EditText txt_precio;
    /**
     * Variable para recibir datos del Layaut activity_parqueadero_add
     */
    private EditText txt_nPlazas;
    /**
     * Variable para recibir datos del Layaut activity_parqueadero_add
     */
    private Button btn_guardarPr;
    /**
     * Variable para recibir datos del Layaut activity_parqueadero_add
     */
    private Button btn_volver;

    /**
     * Variable utilizada en enviar recibir datos desde la Base de datos del Host
     */
    private RequestQueue requestQueue;

    /**
     * Metodo implementado para inicio de la Actividad
     * Se Llama un recurso de Diseño que define su UI
     * Recupera  widgets del Layout activity_parqueadero_add
     * @param savedInstanceState Guarda el estado de la aplicacion
     */
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
     * Metodo implementado para obtener ubicacion actual del administrador
     * Teniendo permisos de localizacion establecido en el Manifest
     * Obtiene la ubicacion precisa del administrador
     * Utilizada para añadir un parqueadero en ubicacion del administrador
     * Asigna valores en variable estatica y, de latitud y longitud
     */
    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        MAKER = new LatLng(location.getLatitude(), location.getLongitude());
        LATITUD = location.getLatitude();
        LONGITUD = location.getLongitude();
        makr = String.valueOf(MAKER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                10000, 0, locationListener);

    }

    /**
     * Metodo utilizado para asignar actividad que realizara:
     * btn_Guardar
     * btn_Volver
     */
    private void oyente() {

        /**
         * Metodo para guardar datos en La Base de datos del Host
         * Comprueba que los campos no esten vacios
         * Llama la peticion registrar Parqueadero Implementado en clase Conexion
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
                mapa.put("coordenada_y", lon);
                mapa.put("precio", precio);
                mapa.put("plazas", nPlazas);

                VolleyPeticion<Parqueadero> regPar = Conexion.registrarParqueadero(
                        getApplicationContext(),
                        mapa,
                        new Response.Listener<Parqueadero>() {
                            @Override
                            public void onResponse(Parqueadero response) {

                                Toast.makeText(getApplicationContext(), "Parqueadero guardado correctamente", Toast.LENGTH_SHORT).show();
                                try {
                                    llamarNotificacion();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
         * Metodo implementado para volver a la actividad de Administrador
         */
        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAdministrar();

            }
        });
    }

    /**
     * Metodo implementado para llamar la actividad Administrado Activity
     * activity donde el administrador maneja su parqueadero
     */
    private void goToAdministrar() {
        Intent intent = new Intent(this, AdministradorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Metodo implementado para llamar la notificacion desde Firebase Cloud Messaging
     * enviando una peticion al servicio de FCM
     * La peticion cuenta con un JSONObject que posee la informacion y headers a usarse en FCM
     * Es ejecutado al momento de realizarse correctamente el ingreso de un parqueadero
     */
    private void llamarNotificacion() throws JSONException {
        String url = "https://fcm.googleapis.com/fcm/send";
        JSONObject jsonBody = new JSONObject("{\n" +
                "  \"to\": \"/topics/cliente\",\n" +
                "  \"notification\": {\n" +
                "    \"title\": \"Parqueadero nuevo\",\n" +
                "    \"body\":\"Se ha ingresado un parqueadero nuevo. Pulse para actualizar\"\n" +
                "   }\n" +
                "}\n");
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

               // Toast.makeText(getApplicationContext(), "Notificacion enviada", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyTiposError errores = VolleyProcesadorResultado.parseErrorResponse(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=AAAAXGI0jYg:APA91bEk72N8kzpnNxY3_WG3-Swg4VLRainu5BOMDGoDLjZI_hsHzbP5S4v2Klg5GO8jOAxALdh0XmSdljwve_gOFdEAdB8pOEVzQAtNJxWi6I71hr8wHw0amAweK44zADwAlvBULGaeKZ_b35t5ji__i34Dbey2qQ");
                params.put("content-type", "application/json");
                return params;
            }
        };

        requestQueue.add(jsonRequest);

    }
}
