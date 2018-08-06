package com.aplicaciones.nemesisaplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button ubicarme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ubicarme = (Button)findViewById(R.id.ubicarme);
        ubicarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View agr0) {
                Intent intent = new Intent(MainActivity.this, MapsNemesisParking.class);
                startActivity(intent);
            }
        });
    }
}
