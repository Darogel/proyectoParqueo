package com.aplicaciones.resparking.controlador.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aplicaciones.resparking.modelo.Reservacion;
import com.aplicaciones.resparking.R;

import java.util.ArrayList;
import java.util.List;

public class ListaReservacion extends ArrayAdapter<Reservacion> {
    private List<Reservacion> dataSet;
    Context mContext;

    /**
     * Constructor de la clase
     *
     * @param data    Lista de reservaciones del servicio actual
     * @param context el contexto actual
     */
    public ListaReservacion(List<Reservacion> data, Context context) {
        super(context, R.layout.informacion_destinos, data);

        this.dataSet = data;
        this.mContext = context;
    }

    /**
     * Constructor de la clase
     *
     * @param context el contexto actual
     */
    public ListaReservacion(Context context) {
        super(context, R.layout.lista_vacia, new ArrayList<Reservacion>());

        this.dataSet = new ArrayList<Reservacion>();
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View item = null;
        if (dataSet.isEmpty()) {
            item = inflater.inflate(R.layout.lista_vacia, null);
        } else {
            item = inflater.inflate(R.layout.informacion_destinos, null);

        }

        TextView vehiculo = (TextView) item.findViewById(R.id.txt_vehiculoL);
        vehiculo.setText(dataSet.get(position).vehiculo);

        TextView hora_e = (TextView) item.findViewById(R.id.txt_eHoraL);
        hora_e.setText(dataSet.get(position).hora_entrada);

        TextView hora_s = (TextView) item.findViewById(R.id.txt_sHoraL);
        hora_s.setText(dataSet.get(position).hora_salida);


        return item;
    }

}
