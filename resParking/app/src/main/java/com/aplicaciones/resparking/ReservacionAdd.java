package com.aplicaciones.resparking;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.aplicaciones.resparking.controlador.adaptador.ListaPlaza;
import com.aplicaciones.resparking.controlador.adaptador.ListaVehiculo;
import com.aplicaciones.resparking.controlador.ws.Conexion;
import com.aplicaciones.resparking.controlador.ws.VolleyPeticion;
import com.aplicaciones.resparking.modelo.Plaza;
import com.aplicaciones.resparking.modelo.Vehiculo;

import java.util.Arrays;
import java.util.Calendar;

public class ReservacionAdd extends AppCompatActivity implements View.OnClickListener{
    private int hora,minutos;

    private EditText etxt_hEntrada;
    private EditText etxt_hSalida;

    private TextView txt_plazaR;
    private TextView txt_vehiculoR;

    private ImageButton  btn_hEntradaR;
    private ImageButton btn_hSalidaR;
    private Button btn_guardarR;
    private Button btn_volverR;

    private Spinner spinnerVehiculo;
    private Spinner spinnerPlaza;

    private ListaVehiculo listaVehiculo;
    private ListaPlaza listaPlaza;
    private RequestQueue requestQueue;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservacion_add);

        txt_plazaR=(TextView)findViewById(R.id.txt_plazaR);
        txt_vehiculoR=(TextView)findViewById(R.id.txt_vehiculoR);

        etxt_hEntrada=(EditText)findViewById(R.id.etxt_hEntrada);
        etxt_hSalida=(EditText)findViewById(R.id.etxt_hSalida);

        btn_hEntradaR=(ImageButton) findViewById(R.id.btn_hEntradaR);
        btn_hSalidaR=(ImageButton) findViewById(R.id.btn_hSalidaR);
        btn_guardarR=(Button) findViewById(R.id.btn_guardarR);
        btn_volverR=(Button) findViewById(R.id.btn_volverR);

        btn_hEntradaR.setOnClickListener(this);
        btn_hSalidaR.setOnClickListener(this);

        spinnerPlaza=(Spinner) findViewById(R.id.cbx_plaza);
        spinnerVehiculo=(Spinner) findViewById(R.id.cbx_vehiculo);
        /*String[] puesto = consultaPlaza();
        spinnerVehiculo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, puesto));*/
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        consultaPlaza();
        consultaVehiculo();

        oyente();



    }

    @Override
    public void onClick(View v) {

        if (v==btn_hEntradaR){
            final Calendar c= Calendar.getInstance();
            hora=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (minute<10){
                        String s="0"+minute;
                        etxt_hEntrada.setText(hourOfDay+":"+s);
                    }else{
                        etxt_hEntrada.setText(hourOfDay+":"+minute);
                    }

                }
            },hora,minutos,false);
            timePickerDialog.show();
        }
        if (v==btn_hSalidaR){
            final Calendar c= Calendar.getInstance();
            hora=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (minute<10){
                        String s="0"+minute;
                        etxt_hSalida.setText(hourOfDay+":"+s);
                    }else{
                        etxt_hSalida.setText(hourOfDay+":"+minute);
                    }
                }
            },hora,minutos,false);
            timePickerDialog.show();
        }
    }

    private void oyente() {

        this.btn_guardarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        this.btn_volverR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               goToMaps();
            }
        });

    }

    private void goToMaps() {
        Intent intent=new Intent(this,MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void consultaVehiculo(){
        VolleyPeticion<Vehiculo[]> vehiculo = Conexion.listarVehiculo(
                getApplicationContext(),
                //MapsActivity.ID_EXTERNAL
                "aa17640e-8c90-46fd-ba8f-06698580467b",
                new Response.Listener<Vehiculo[]>() {
                    @Override
                    public void onResponse(Vehiculo[]response) {

                        listaVehiculo = new ListaVehiculo(Arrays.asList(response),getApplicationContext());
                        spinnerVehiculo.setAdapter(listaVehiculo);
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toas1 = Toast.makeText(getApplicationContext(),getString(R.string.msg_no_busqueda),Toast.LENGTH_SHORT);
                        toas1.setGravity(Gravity.CENTER_VERTICAL,0,0);
                        toas1.show();
                        return ;
                    }
                }
        );
        requestQueue.add(vehiculo);
    }


    private void consultaPlaza(){
        VolleyPeticion<Plaza[]> plaza= Conexion.listarPlazas(
                getApplicationContext(),
                //MapsActivity.ID_EXTERNAL
                "fba0768b-8afc-4338-82aa-92db19b8b620",
                new Response.Listener<Plaza[]>() {
                    @Override
                    public void onResponse(Plaza[] response) {
                        listaPlaza = new ListaPlaza(Arrays.asList(response),getApplicationContext());
                        spinnerPlaza.setAdapter(listaPlaza);
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toas1 = Toast.makeText(getApplicationContext(),getString(R.string.msg_no_busqueda),Toast.LENGTH_SHORT);
                        toas1.setGravity(Gravity.CENTER_VERTICAL,0,0);
                        toas1.show();
                        return ;
                    }
                }
        );
        requestQueue.add(plaza);
    }

}
