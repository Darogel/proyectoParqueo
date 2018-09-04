package com.aplicaciones.resparking.controlador.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aplicaciones.resparking.R;
import com.aplicaciones.resparking.modelo.Parqueadero;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase implementada para realizar la lista de Parqueaderos
 * extendible de ArrayAdapter de la clase Parqueadero
 */
public class ListaParqueadero extends ArrayAdapter<Parqueadero> {
    /**
     * Variable de tipo List de la clase Parqueadero
     */
    private List<Parqueadero> dataSet;
    /**
     * Variable de tipo Contex
     */
    Context mContext;
    /**
     * variable de tipo ListView
     */
    private ListView listView;


    /**
     * Constructor de la clase
     *
     * @param data    Lista de parqueaderos del servicio actual
     * @param context el contexto actual
     */

    public ListaParqueadero(List<Parqueadero> data, Context context) {
        super(context, R.layout.item_lista, data);

        this.dataSet = data;
        this.mContext = context;
    }

    /**
     * Constructor de la clase
     *
     * @param context el contexto actual
     */
    public ListaParqueadero(Context context) {
        super(context, R.layout.lista_vacia, new ArrayList<Parqueadero>());

        this.dataSet = new ArrayList<Parqueadero>();
        this.mContext = context;
    }


    /**
     * Metodo implementado para  mostrar la informacion de Parqueaderos
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
            item = inflater.inflate(R.layout.modal_marcador, null);

        }

        TextView nombre = (TextView) item.findViewById(R.id.nombre_parqueo);
        nombre.setText(dataSet.get(position).nombre);


        TextView plaza = (TextView) item.findViewById(R.id.plaza);
        plaza.setText(dataSet.get(position).numero_plazas);

        TextView precio = (TextView) item.findViewById(R.id.external);
        precio.setText(dataSet.get(position).precio_hora);


        return item;
    }

}
