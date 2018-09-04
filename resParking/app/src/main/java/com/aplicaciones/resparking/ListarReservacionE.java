package com.aplicaciones.resparking;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
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
import com.aplicaciones.resparking.controlador.ws.VolleyProcesadorResultado;
import com.aplicaciones.resparking.controlador.ws.VolleyTiposError;
import com.aplicaciones.resparking.modelo.Reservacion;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Clase implemetada para listar reservaciones de parqueaderos por parte del usuario
 * Eliminacion de las reservaciones por parte del usuario
 * Extendible de la Super clase AppCompatActivity
 */
public class ListarReservacionE extends AppCompatActivity {
    /**
     * Variable estatica utilizada para guardar el externaL_id de reservacion
     */
    private static String EXTERNAL_ID_RESERVACION = "";

    /**
     * Variable utilizada para recibir datos del layout activity listar reservacion
     */
    private ListView mi_lista;
    /**
     * Variable utilizada para recibir datos del layout activity listar reservacion
     */
    private Button btn_volver;

    /**
     * Variable de tipo listarReservacion del paquete Adaptador
     */
    private ListaReservacion listarReservacion;

    /**
     * Variable utilizada para crear una cola de peticiones hacia la base de datos del Host
     */
    private RequestQueue requestQueue;

    /**
     * Metodo implementado para inicio de la Actividad
     * Se Llama un recurso de Diseño que define su UI
     * Recupera  widgets del Layout activity_listar_reservacion
     * @param savedInstanceState Guarda el estado de la aplicacion
     */
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
     * Metodo utilizado para asginar la actividad qeu realizara btn_volver
     */
    private void oyente() {
        /**
         * Metodo Utilizado para volver a la actividad del principal
         */
        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMaps();
            }
        });
    }

    /**
     * Metodo implementado para consulta de busqueda de reservaciones de usuario
     * @param extIdUsu Varible tipo string que recibe el external_id del usuario
     */
    private void consultarWs(String extIdUsu) {
        VolleyPeticion<Reservacion[]> menus = Conexion.listarReservacionUs(
                getApplicationContext(),
                extIdUsu,
                new Response.Listener<Reservacion[]>() {
                    @Override
                    public void onResponse(final Reservacion[] response) {

                        listarReservacion = new ListaReservacion(Arrays.asList(response), getApplicationContext());
                        mi_lista.setAdapter(listarReservacion);
                        mi_lista.setClickable(true);

                        mi_lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                final Dialog dialog = new Dialog(ListarReservacionE.this);
                                dialog.setContentView(R.layout.modal_confirmacion);
                                Reservacion reser = (Reservacion) adapterView.getItemAtPosition(i);
                                EXTERNAL_ID_RESERVACION = reser.external_id;

                                TextView nombre = (TextView) dialog.findViewById(R.id.titulo);
                                nombre.setText("ELIMINAR RESERVACION!");
                                TextView plaza = (TextView) dialog.findViewById(R.id.mensaje);
                                plaza.setText("¿Desea eliminar la reservacion?");
                                Button reservacion = (Button) dialog.findViewById(R.id.dialogButtonGuardar);
                                Button cerrar = (Button) dialog.findViewById(R.id.dialogButtonCerrar);

                                reservacion.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        consultarWsE(EXTERNAL_ID_RESERVACION);

                                        dialog.dismiss();
                                    }
                                });

                                cerrar.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();
                            }
                        });
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

    /**
     * Metodo implemetado para la eliminacion de una reservacion de usuario
     * @param idRes Variable tipo string que recibe el external_id de la reservacion
     */
    private void consultarWsE(String idRes) {
        HashMap<String, String> mapa = new HashMap<>();
        mapa.put("external_id", idRes);
        VolleyPeticion<Reservacion> eliminar = Conexion.eliminarReservacion(
                getApplicationContext(),
                mapa,
                new Response.Listener<Reservacion>() {
                    @Override
                    public void onResponse(Reservacion response) {
                        if (response != null) {
                            Toast.makeText(getApplicationContext(), "Se Elimino Correctamente", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "No Se pudo Eliminar", Toast.LENGTH_SHORT).show();
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
        requestQueue.add(eliminar);
    }


}
