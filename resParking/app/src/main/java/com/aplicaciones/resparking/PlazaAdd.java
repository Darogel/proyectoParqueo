package com.aplicaciones.resparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

public class PlazaAdd extends AppCompatActivity {

    private Spinner spinner;
    private Spinner spinner1;

    private ImageButton btn_guardarR;
    private ImageButton btn_volverR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaza_add);

        spinner = (Spinner) findViewById(R.id.cbx_puesto);
        String[] puesto = {"1","2","3","4","5","6","7","8","9","10"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, puesto));



        spinner1 = (Spinner) findViewById(R.id.cbx_puesto);
        String[] tipo = {"Con techo","Sin techo"};
        spinner1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo));


        oyente();
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