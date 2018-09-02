package com.aplicaciones.resparking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class ListarActivity extends AppCompatActivity {

    /**
     * Variable utilizada para recibir datos del Layout Activity Listar
     */
    private Button btn_volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        btn_volver = (Button) findViewById(R.id.btn_volver);
        oyente();

    }

    /**
     * Metodo implementado para asignar que actividad realizara
     * btn_volver
     */
    private void oyente() {
        /**
         * Metodo implementado para llamar la actividad Administrado Activity
         * activity donde el administrador maneja su parqueadero
         */
        this.btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                administrador();

            }
        });
    }

    /**
     * Metodo implementado para volver a la actividad de Administrador
     */
    private void administrador() {
        Intent intent = new Intent(this, AdministradorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
