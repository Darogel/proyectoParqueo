package com.aplicaciones.resparking;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.aplicaciones.resparking.controlador.adaptador.ListaParqueadero;
import com.aplicaciones.resparking.controlador.ws.Conexion;
import com.aplicaciones.resparking.controlador.ws.VolleyPeticion;
import com.aplicaciones.resparking.modelo.Parqueadero;
import com.aplicaciones.resparking.modelo.Vehiculo;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DetallesParqueo implements GoogleMap.InfoWindowAdapter {
    private static  final String TAG = "DetallesParqueo";
    private LayoutInflater inflater;

    public DetallesParqueo(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker m) {
        View info = inflater.inflate(R.layout.modal_marcador, null);

        /*String[] v = m.getTitle().split("&");
        String url = m.getSnippet();*/
        if (m.equals("marcador")){
            ((TextView) info.findViewById(R.id.nombre_parqueo)).setText("Lina Cortés");
            ((TextView) info.findViewById(R.id.plaza)).setText("Placas: SRX32");
            ((TextView) info.findViewById(R.id.external)).setText("Estado: Activo");
        }
        if (m.equals("marcador1")){
            ((TextView) info.findViewById(R.id.nombre_parqueo)).setText("Lina Torres");
            ((TextView) info.findViewById(R.id.plaza)).setText("Placas: SRX32");
            ((TextView) info.findViewById(R.id.external)).setText("Estado: inactivo");
        }

       /* return v;
        TextView view = (TextView)info.findViewById(R.id.nombre);
        view.setText(marker.getTitle());
        view=(TextView)info.findViewById(R.id.plaza);
        view.setText(marker.getSnippet());*/

        /*Button res = (Button)info.findViewById(R.id.reservacion);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Aqui para llamar al Login",Toast.LENGTH_LONG).show();
            }
        });*/

        return info;
    }

}


