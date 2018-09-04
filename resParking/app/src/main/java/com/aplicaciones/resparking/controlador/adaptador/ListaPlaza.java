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

/**
 * Clase implementada para realizar la lista de Plazas
 * extendible de ArrayAdapter de la clase Plaza
 */
public class ListaPlaza extends ArrayAdapter<Plaza> {
    /**
     * Variabla de tipo List  de la clase Plaza
     */
    private List<Plaza> dataSet;
    /**
     * Varible de tipo Context
     */
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

    /**
     * Metodo implementado para  mostrar la informacion de Plazas
     * @param position variable que permite llamar a un getview por cada posicion
     * @param convertView variable a  Llamar muchas veces inflando una nueva lista
     * @param parent variable que indica que esta vista es un hijo
     * @return variable item con valor de vista
     */
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
