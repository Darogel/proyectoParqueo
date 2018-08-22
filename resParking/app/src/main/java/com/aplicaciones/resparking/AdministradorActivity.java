package com.aplicaciones.resparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class AdministradorActivity extends AppCompatActivity {
    private ImageButton btn_parqueadero;
    private ImageButton btn_plaza;
    private ImageButton btn_reservacion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        btn_parqueadero = (ImageButton) findViewById(R.id.btn_parqueadero);
        btn_plaza = (ImageButton) findViewById(R.id.btn_plaza);
        btn_reservacion = (ImageButton) findViewById(R.id.btn_reservacion);
        oyente();

    }
    private void parqueaderoAdd() {
        Intent intent=new Intent(this,ParqueaderoAdd.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void plazaAdd() {
        Intent intent=new Intent(this,PlazaAdd.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void listarActivity() {
        Intent intent=new Intent(this,ListarActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void oyente() {

        this.btn_parqueadero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parqueaderoAdd();

            }
        });

        this.btn_plaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plazaAdd();

            }
        });
        this.btn_reservacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listarActivity();

            }
        });
    }

    private void goToMaps() {
        Intent intent=new Intent(this,MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
