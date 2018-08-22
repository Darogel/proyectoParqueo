package com.aplicaciones.resparking;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ParqueaderoAdd extends AppCompatActivity {

    private Button btn_guardarPr;
    private Button btn_volver;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parqueadero_add);

        btn_guardarPr=(Button) findViewById(R.id.btn_guardarPr);
        btn_volver=(Button) findViewById(R.id.btn_volverPr);
        oyente();
    }

    private void oyente() {


        this.btn_guardarPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                administrador();

            }
        });
    }

    private void administrador() {
        Intent intent=new Intent(this,AdministradorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
