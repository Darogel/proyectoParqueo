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

public class ListaVehiculo extends ArrayAdapter<Vehiculo> {
    private List<Vehiculo> dataSet;
    Context mContext;

    public ListaVehiculo(List<Vehiculo> data, Context context){
        super(context, R.layout.item_lista,data);
        this.dataSet=data;
        this.mContext=context;
    }

    public ListaVehiculo(Context context){
        super(context, R.layout.lista_vacia, new ArrayList<Vehiculo>());

        this.dataSet = new ArrayList<Vehiculo>();
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View item = null;
        if (dataSet.isEmpty()){
            item=inflater.inflate(R.layout.lista_vacia,null);
        }else {
            item=inflater.inflate(R.layout.item_lista,null);
        }

        TextView placa=(TextView)item.findViewById(R.id.vPlaca);
        placa.setText(dataSet.get(position).placa);


        return item;
    }
}
