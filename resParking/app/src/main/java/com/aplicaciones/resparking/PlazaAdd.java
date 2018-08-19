package com.aplicaciones.resparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

public class PlazaAdd extends AppCompatActivity {

    private Spinner spinner;
    private Spinner spinner1;

    private Button btn_guardarPl;
    private Button btn_volverPl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plaza_add);

        btn_guardarPl=(Button) findViewById(R.id.btn_guardarPl);
        btn_volverPl=(Button) findViewById(R.id.btn_volverPl);

        spinner = (Spinner) findViewById(R.id.cbx_puesto);
        String[] puesto = {"Seleccionar","1","2","3","4","5","6","7","8","9","10"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, puesto));



        spinner1 = (Spinner) findViewById(R.id.cbx_tipo);
        String[] tipo = {"Seleccionar","Con techo","Sin techo"};
        spinner1.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo));


        oyente();
    }

    private void oyente() {


        this.btn_guardarPl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        this.btn_volverPl.setOnClickListener(new View.OnClickListener() {
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
