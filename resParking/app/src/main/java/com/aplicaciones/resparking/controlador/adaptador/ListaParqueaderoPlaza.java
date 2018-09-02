package com.aplicaciones.resparking.controlador.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.aplicaciones.resparking.R;
import com.aplicaciones.resparking.modelo.Parqueadero;
import com.aplicaciones.resparking.modelo.Vehiculo;

import java.util.ArrayList;
import java.util.List;


public class ListaParqueaderoPlaza extends ArrayAdapter<Parqueadero>{
    private List<Parqueadero> dataSet;
    Context mContext;

    public ListaParqueaderoPlaza(List<Parqueadero> data, Context context){
        super(context, R.layout.item_lista_parqueadero,data);
        this.dataSet=data;
        this.mContext=context;
    }

    public ListaParqueaderoPlaza(Context context){
        super(context, R.layout.lista_vacia, new ArrayList<Parqueadero>());

        this.dataSet = new ArrayList<Parqueadero>();
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View item = null;
        if (dataSet.isEmpty()){
            item=inflater.inflate(R.layout.lista_vacia,null);
        }else {
            item=inflater.inflate(R.layout.item_lista_parqueadero,null);
        }

        TextView nombre =(TextView)item.findViewById(R.id.parqueadero);
        nombre.setText(dataSet.get(position).nombre);


        return item;
    }
}