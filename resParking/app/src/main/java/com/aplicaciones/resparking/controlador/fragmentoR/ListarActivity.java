package com.aplicaciones.resparking.controlador.fragmentoR;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.aplicaciones.resparking.AdministradorActivity;
import com.aplicaciones.resparking.R;
import com.aplicaciones.resparking.controlador.adaptador.ListaReservacion;

public class ListarActivity extends Fragment {
    private Button btn_volver;

    private ListaReservacion listaAdaptadorWs;
    private ListView listView;

    public static ListarActivity newInstance(){return  new ListarActivity();}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_listar_reservacion, container,false);
        listView = (ListView) rootView.findViewById(R.id.mi_lista);


        listaAdaptadorWs = new ListaReservacion(getActivity());
        listView.setAdapter(listaAdaptadorWs);
        return rootView;
    }

    private void oyente() {

        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                administrador();

            }
        });
    }

    private void administrador() {
        Intent intent=new Intent(getContext(),AdministradorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    /*
    private  void consultarWs(String exIdAdmin) {
            VolleyPeticion<PeliculasJson[]> films = Conexion.getListaPeliculas(
                getActivity(),
                titulo,
                new Response.Listener<PeliculasJson[]>() {
                    @Override
                    public void onResponse(PeliculasJson[] response) {
                        listaAdaptadorWs = new ListaPeliculasJson(Arrays.asList(response),
                                getActivity());
                        listView.setAdapter(listaAdaptadorWs);
                        dialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast1 = Toast.makeText(getActivity(),
                                getActivity().getString(R.string.msg_no_busqueda),
                                Toast.LENGTH_LONG);
                        toast1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast1.show();
                    }
                }
        );
        requestQueue.add(films);
    }
     */


}
