package com.aplicaciones.resparking;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class DetallesParqueo implements GoogleMap.InfoWindowAdapter {
    private Activity context;

    public DetallesParqueo(Activity context) {
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View info = context.getLayoutInflater().inflate(R.layout.informacion_destinos, null);

        TextView view = (TextView)info.findViewById(R.id.nombre);
        view.setText(marker.getTitle());
        view=(TextView)info.findViewById(R.id.plaza);
        view.setText(marker.getSnippet());

        Button res = (Button)info.findViewById(R.id.reservacion);
        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Aqui para llamar al Login",Toast.LENGTH_LONG).show();
            }
        });

        return info;
    }
}

