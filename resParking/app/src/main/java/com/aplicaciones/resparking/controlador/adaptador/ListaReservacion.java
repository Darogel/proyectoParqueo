package com.aplicaciones.resparking.controlador.adaptador;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.aplicaciones.resparking.modelo.Reservacion;
import com.aplicaciones.resparking.R;

import java.util.ArrayList;
import java.util.List;

public class ListaReservacion extends ArrayAdapter<Reservacion> {
    private List<Reservacion> dataSet;
    Context mContext;

    public ListaReservacion(List<Reservacion> data, Context context) {
        super(context, R.layout.item_lista, data);

        this.dataSet = data;
        this.mContext = context;
    }

    public ListaReservacion(Context context){
        super(context, R.layout.lista_vacia, new ArrayList<Reservacion>());

        this.dataSet = new ArrayList<Reservacion>();
        this.mContext = context;
    }

}
