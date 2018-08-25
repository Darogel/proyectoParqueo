package com.aplicaciones.resparking.controlador.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aplicaciones.resparking.R;
import com.aplicaciones.resparking.controlador.ws.Conexion;
import com.aplicaciones.resparking.controlador.ws.VolleyPeticion;
import com.aplicaciones.resparking.modelo.Parqueadero;
import com.aplicaciones.resparking.modelo.Vehiculo;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ListaParqueadero extends ArrayAdapter<Parqueadero> {
    private List<Parqueadero> dataSet;
    Context mContext;
    private String codigo;
    private ListView listView;





    /**
     * Constructor de la clase
     * @param data Lista de peliculas del servicio actual
     * @param context el contexto actual
     */

    public ListaParqueadero(List<Parqueadero> data,Context context) {
        super(context, R.layout.item_lista, data);

        this.dataSet = data;
        this.mContext = context;
    }

    public ListaParqueadero(Context context){
        super(context, R.layout.lista_vacia, new ArrayList<Parqueadero>());

        this.dataSet = new ArrayList<Parqueadero>();
        this.mContext = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View item = null;
        if(dataSet.isEmpty()){
            item = inflater.inflate(R.layout.lista_vacia,null);
        }else{
            item = inflater.inflate(R.layout.modal_marcador, null);

        }

        TextView nombre=(TextView)item.findViewById(R.id.nombre_parqueo);
        nombre.setText(dataSet.get(position).nombre);


        TextView plaza=(TextView)item.findViewById(R.id.plaza);
        plaza.setText(dataSet.get(position).numero_plazas);

        TextView precio=(TextView)item.findViewById(R.id.external);
        precio.setText(dataSet.get(position).precio_hora);


        return item;
    }

}
