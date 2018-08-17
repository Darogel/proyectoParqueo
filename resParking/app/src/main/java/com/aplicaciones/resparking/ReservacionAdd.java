package com.aplicaciones.resparking;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class ReservacionAdd extends AppCompatActivity implements View.OnClickListener{
    private int hora,minutos;

    private EditText etxt_hEntrada;
    private EditText etxt_hSalida;

    private TextView txt_plazaR;
    private TextView txt_vehiculoR;

    private ImageButton  btn_hEntradaR;
    private ImageButton btn_hSalidaR;
    private ImageButton btn_guardarR;
    private ImageButton btn_volverR;

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
        btn_guardarR=(ImageButton) findViewById(R.id.btn_guardarR);
        btn_volverR=(ImageButton) findViewById(R.id.btn_volverR);

        btn_hEntradaR.setOnClickListener(this);
        btn_hSalidaR.setOnClickListener(this);

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
}
