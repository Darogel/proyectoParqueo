package com.aplicaciones.resparking.controlador.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aplicaciones.resparking.R;
import com.aplicaciones.resparking.modelo.Plaza;

import java.util.ArrayList;
import java.util.List;

public class ListaPlaza extends ArrayAdapter<Plaza> {
    private List<Plaza> dataSet;
    Context mContext;

    /**
     * Constructor de la clase
     *
     * @param data    Lista de plazas del servicio actual
     * @param context el contexto actual
     */
    public ListaPlaza(List<Plaza> data, Context context) {
        super(context, R.layout.item_lista_plaza, data);
        this.dataSet = data;
        this.mContext = context;
    }

    /**
     * Constructor de la clase
     *
     * @param context el contexto actual
     */
    public ListaPlaza(Context context) {
        super(context, R.layout.lista_vacia, new ArrayList<Plaza>());
        this.dataSet = new ArrayList<Plaza>();
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View item = null;
        if (dataSet.isEmpty()) {
            item = inflater.inflate(R.layout.lista_vacia, null);
        } else {
            item = inflater.inflate(R.layout.item_lista_plaza, null);
        }

        TextView numeroP = (TextView) item.findViewById(R.id.puesto);
        numeroP.setText(dataSet.get(position).numero_puesto);
        return item;
    }
}
