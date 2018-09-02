package com.aplicaciones.resparking;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class DetallesParqueo implements GoogleMap.InfoWindowAdapter {
    private static final String TAG = "DetallesParqueo";
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

        if (m.equals("marcador")) {
            ((TextView) info.findViewById(R.id.nombre_parqueo)).setText("Lina Cortés");
            ((TextView) info.findViewById(R.id.plaza)).setText("Placas: SRX32");
            ((TextView) info.findViewById(R.id.external)).setText("Estado: Activo");
        }
        if (m.equals("marcador1")) {
            ((TextView) info.findViewById(R.id.nombre_parqueo)).setText("Lina Torres");
            ((TextView) info.findViewById(R.id.plaza)).setText("Placas: SRX32");
            ((TextView) info.findViewById(R.id.external)).setText("Estado: inactivo");
        }

        return info;
    }

}


