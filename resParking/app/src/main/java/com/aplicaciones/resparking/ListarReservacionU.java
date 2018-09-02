package com.aplicaciones.resparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.aplicaciones.resparking.controlador.adaptador.ListaReservacion;
import com.aplicaciones.resparking.controlador.ws.Conexion;
import com.aplicaciones.resparking.controlador.ws.VolleyPeticion;
import com.aplicaciones.resparking.modelo.Reservacion;

import java.util.Arrays;

public class ListarReservacionU extends AppCompatActivity {

    /**
     * Variables para recibir datos del Layout Listar Reservacion
     */
    private Button btn_volver;
    private ListView mi_lista;

    /**
     * Variable de tipo Listar Reservacion
     */
    private ListaReservacion listarReservacion;

    /**
     * Variable implementada para enviar y recibir datos desde la base de datos del Host
     */
    private RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_reservacion);

        mi_lista = (ListView) findViewById(R.id.mi_lista);
        mi_lista.setEmptyView(findViewById(android.R.id.empty));

        listarReservacion = new ListaReservacion(this);
        mi_lista.setAdapter(listarReservacion);

        TextView mensaje = (TextView) findViewById(R.id.reserva);
        mensaje.setText("Reservacion Del Usuario");
        btn_volver = (Button) findViewById(R.id.btn_volver);


        requestQueue = Volley.newRequestQueue(this);
        consultarWs(MapsActivity.ID_EXTERNAL_USER);
        oyente();
    }

    /**
     * Metodo para asignar la Actividad que realizara
     * btn_volver
     */
    private void oyente() {
        /**
         * Metodo Utilizado para volver a la actividad del Mapa
         */
        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMaps();

            }
        });
    }

    /**
     * Metodo Implementado para listar Reservaciones por Usuario
     * @param extIdUsu variable Tipo String que recibe el external_id del usuario
     */
    private void consultarWs(String extIdUsu) {
        VolleyPeticion<Reservacion[]> menus = Conexion.listarReservacionUs(
                getApplicationContext(),
                extIdUsu,
                new Response.Listener<Reservacion[]>() {
                    @Override
                    public void onResponse(Reservacion[] response) {

                        listarReservacion = new ListaReservacion(Arrays.asList(response), getApplicationContext());
                        mi_lista.setAdapter(listarReservacion);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast1 = Toast.makeText(getApplicationContext(),
                                getApplicationContext().getString(R.string.msg_no_busqueda),
                                Toast.LENGTH_SHORT);

                        toast1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast1.show();

                    }

                }
        );
        requestQueue.add(menus);
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


