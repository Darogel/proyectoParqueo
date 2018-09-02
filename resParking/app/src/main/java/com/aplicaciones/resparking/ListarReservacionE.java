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

public class ListarReservacionE extends AppCompatActivity {
    private static String EXTERNAL_ID_RESERVACION = "";
    private ListView mi_lista;
    private ListaReservacion listarReservacion;
    private RequestQueue requestQueue;
    private Button btn_volver;


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

    private void oyente() {
        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMaps();

            }
        });
    }


    private void consultarWs(String extIdAdmin) {
        VolleyPeticion<Reservacion[]> menus = Conexion.listarReservacionUs(
                getApplicationContext(),
                extIdAdmin,
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

    private void goToMaps() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

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
