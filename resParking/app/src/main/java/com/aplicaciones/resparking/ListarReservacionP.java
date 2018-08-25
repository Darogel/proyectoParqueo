package com.aplicaciones.resparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

public class ListarReservacionP extends AppCompatActivity {
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

        btn_volver = (Button)findViewById(R.id.btn_volver);


        requestQueue = Volley.newRequestQueue(this);
        //consultarWs(MapsActivity.ID_EXTERNAL);
        consultarWs("fba0768b-8afc-4338-82aa-92db19b8b620");
        oyente();
    }

    private void oyente() {
        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                administrador();

            }
        });
    }

    private void consultarWs(String extIdAdmin) {
        VolleyPeticion<Reservacion[]> menus = Conexion.listarReservacionParq(
                getApplicationContext(),
                extIdAdmin,

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

                        /*Intent intent = new Intent(ListarReservacionP.this, AdministradorActivity.class);
                        startActivity(intent);*/
                    }

                }
        );
        requestQueue.add(menus);
    }

    private void administrador() {
        Intent intent = new Intent(getApplicationContext(), AdministradorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
