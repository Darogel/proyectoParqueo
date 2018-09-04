package com.aplicaciones.resparking.controlador.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aplicaciones.resparking.R;
import com.aplicaciones.resparking.modelo.Vehiculo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase implementada para realizar la lista de Vehiculo
 * extendible de ArrayAdapter de Clase Vehiculo
 */
public class ListaVehiculo extends ArrayAdapter<Vehiculo> {
    /**
     * Variable de tipo List de la Clase Vehiculo
     */
    private List<Vehiculo> dataSet;
    /**
     * Variable de tipo context
     */
    Context mContext;

    /**
     * Constructor de la clase
     *
     * @param data    Lista de vehiculos del servicio actual
     * @param context el contexto actual
     */
    public ListaVehiculo(List<Vehiculo> data, Context context) {
        super(context, R.layout.item_lista, data);
        this.dataSet = data;
        this.mContext = context;
    }

    /**
     * Constructor de la clase
     *
     * @param context el contexto actual
     */
    public ListaVehiculo(Context context) {
        super(context, R.layout.lista_vacia, new ArrayList<Vehiculo>());

        this.dataSet = new ArrayList<Vehiculo>();
        this.mContext = context;
    }

    /**
     * Metodo implementado para  mostrar la informacion del vehiculo
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
            item = inflater.inflate(R.layout.item_lista, null);
        }

        TextView placa = (TextView) item.findViewById(R.id.vPlaca);
        placa.setText(dataSet.get(position).placa);


        return item;
    }
}
