package com.aplicaciones.resparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.aplicaciones.resparking.modelo.Administrador;

import java.util.HashMap;

/**
 * Clase implementada para autenticar un administrador registrado en la aplicacion
 * Extendible de la super clase AppCompatActivity
 */
public class LoginAdministrador extends AppCompatActivity {

    /**
     * Variable utilizada para recibir datos del layout del login de administrador
     */
    private EditText usuario;
    /**
     * Variable utilizada para recibir datos del layout del login de administrador
     */
    private EditText clave;
    /**
     * Variable utilizada para recibir datos del layout del login de administrador
     */
    private Button btn_inicio;
    /**
     * Variable utilizada para recibir datos del layout del login de administrador
     */
    private Button btn_volver;

    /**
     * Variable utilizada para crear una cola de peticiones hacia la base de datos del Host
     */
    private RequestQueue requestQueue;

    /**
     * Metodo implementado para limpiar campo de texto de variables del layout
     */
    public void limpiarTexto() {
        usuario.getText().clear();
        clave.getText().clear();
    }

    /**
     * Metodo implementado para inicio de la actividad
     * Se llama un recurso de diseño que define su UI
     * Recupera  widgets del layout activity_login_administrador
     * @param savedInstanceState Guarda el estado de la aplicacion
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_administrador);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        usuario = (EditText) findViewById(R.id.usuario);
        clave = (EditText) findViewById(R.id.clave);
        btn_inicio = (Button) findViewById(R.id.btn_login_admin);
        btn_volver = (Button) findViewById(R.id.btn_volver);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        oyente();

    }

    /**
     * Metodo utilizado para asignar la actividad que realizara:
     * btn_inicio
     * btn_volver
     */
    private void oyente() {
        /**
         * Metodo implementado para enviar datos a La Base de Datos
         * Comprueba si los campos de usuario y clave no esten vacios
         * Llama la peticion iniciar sesion de administrador
         */
        btn_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = usuario.getText().toString();
                String password = clave.getText().toString();
                if (user.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Falta usuario", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Falta clave", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, String> mapa = new HashMap<>();
                mapa.put("usuario", user);
                mapa.put("clave", password);

                VolleyPeticion<Administrador> inicio = Conexion.iniciarSesion(
                        getApplicationContext(),
                        mapa,
                        new Response.Listener<Administrador>() {
                            @Override
                            public void onResponse(Administrador response) {
                                if (response != null) {
                                    MapsActivity.TOKEN = response.token;
                                    MapsActivity.ID_EXTERNAL = response.id;
                                    Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();

                                    goToAdministrar();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Usuario o Contrasña Incorrecto", Toast.LENGTH_SHORT).show();
                                    limpiarTexto();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyTiposError errores = VolleyProcesadorResultado.parseErrorResponse(error);


                            }
                        }
                );
                requestQueue.add(inicio);
            }
        });

        /**
         * Metodo implementado para volver a la actividad principal
         */
        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMaps();

            }
        });
    }

    /**
     * Metodo implementado para llamar la actividad MapsActivity
     * Activity de inicio
     */
    private void goToMaps() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Metodo implementado para llamar la actividad AdministradorActivity
     * Activity donde el administrador maneja su parqueadero
     */
    private void goToAdministrar() {
        Intent intent = new Intent(this, AdministradorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
