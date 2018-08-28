package com.aplicaciones.resparking;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.aplicaciones.resparking.controlador.ws.VolleyProcesadorResultado;
import com.aplicaciones.resparking.controlador.ws.VolleyTiposError;
import com.aplicaciones.resparking.modelo.ListarReservacionE;
import com.aplicaciones.resparking.modelo.Plaza;
import com.aplicaciones.resparking.modelo.Reservacion;
import com.aplicaciones.resparking.modelo.Vehiculo;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class ReservacionAdd extends AppCompatActivity implements View.OnClickListener {
    public static String ID_EXTERNAL_PLAZA = "";
    public static String ID_EXTERNAL_VEHICULO = "";

    private int hora, minutos;

    private EditText etxt_hEntrada;
    private EditText etxt_hSalida;

    private EditText plaza;
    private EditText vehiculo;

    private TextView txt_plazaR;
    private TextView txt_vehiculoR;

    private ImageButton btn_hEntradaR;
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        txt_plazaR = (TextView) findViewById(R.id.txt_plazaN);
        txt_vehiculoR = (TextView) findViewById(R.id.txt_VehiculoN);

        etxt_hEntrada = (EditText) findViewById(R.id.etxt_hEntrada);
        etxt_hSalida = (EditText) findViewById(R.id.etxt_hSalida);

        btn_hEntradaR = (ImageButton) findViewById(R.id.btn_hEntradaR);
        btn_hSalidaR = (ImageButton) findViewById(R.id.btn_hSalidaR);
        btn_guardarR = (Button) findViewById(R.id.btn_guardarRs);
        btn_volverR = (Button) findViewById(R.id.btn_volverRs);

        btn_hEntradaR.setOnClickListener(this);
        btn_hSalidaR.setOnClickListener(this);

        spinnerPlaza = (Spinner) findViewById(R.id.cbx_plaza);
        spinnerVehiculo = (Spinner) findViewById(R.id.cbx_vehiculo);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        consultaPlaza();
        consultaVehiculo();

        oyente();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reservacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_eliminar) {
            listaReservacioU();
        }
        if (id == R.id.action_modificar) {

        }

        return super.onOptionsItemSelected(item);

    }

    private void listaReservacioU() {
        Intent intent = new Intent(this, ListarReservacionE.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_hEntradaR) {
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (minute < 10) {
                        String s = "0" + minute;
                        etxt_hEntrada.setText(hourOfDay + ":" + s);
                    } else {
                        etxt_hEntrada.setText(hourOfDay + ":" + minute);
                    }

                }
            }, hora, minutos, false);
            timePickerDialog.show();
        }
        if (v == btn_hSalidaR) {
            final Calendar c = Calendar.getInstance();
            hora = c.get(Calendar.HOUR_OF_DAY);
            minutos = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (minute < 10) {
                        String s = "0" + minute;
                        etxt_hSalida.setText(hourOfDay + ":" + s);
                    } else {
                        etxt_hSalida.setText(hourOfDay + ":" + minute);
                    }
                }
            }, hora, minutos, false);
            timePickerDialog.show();
        }
    }

    private void oyente() {

        this.btn_guardarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String external=MapsActivity.ID_EXTERNAL;
                String entrada=etxt_hEntrada.getText().toString();
                String salida=etxt_hSalida.getText().toString();
                String plaza=ID_EXTERNAL_PLAZA;
                String vehiculo=ID_EXTERNAL_VEHICULO;
                if (entrada.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Este Campo No Puede estar Vacio",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (salida.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Este Campo No Puede estar Vacio",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (plaza.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Este Campo No Puede estar Vacio",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (vehiculo.trim().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Este Campo No Puede estar Vacio",Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String,String> mapa= new HashMap<>();
                mapa.put("idP",plaza);
                mapa.put("idV",vehiculo);
                mapa.put("entrada",entrada);
                mapa.put("salida",salida);

                VolleyPeticion<Reservacion> regPar= Conexion.registrarReservacion(
                        getApplicationContext(),
                        mapa,
                        new Response.Listener<Reservacion>() {
                            @Override
                            public void onResponse(Reservacion response) {

                                Toast.makeText(getApplicationContext(),"Se ha añadido Su Reservacion",Toast.LENGTH_SHORT).show();

                                limpiarTexto();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                VolleyTiposError errores= VolleyProcesadorResultado.parseErrorResponse(error);

                            }
                        }
                );
                requestQueue.add(regPar);


            }
        });

        this.btn_volverR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMaps();
            }
        });

    }
    public void limpiarTexto(){
        etxt_hEntrada.getText().clear();
        etxt_hSalida.getText().clear();
    }

    private void goToMaps() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    private void consultaVehiculo() {
        final VolleyPeticion<Vehiculo[]> vehiculo = Conexion.listarVehiculo(
                getApplicationContext(),
                MapsActivity.ID_EXTERNAL_USER,
                new Response.Listener<Vehiculo[]>() {
                    @Override
                    public void onResponse(Vehiculo[] response) {

                        listaVehiculo = new ListaVehiculo(Arrays.asList(response), getApplicationContext());
                        listaVehiculo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerVehiculo.setAdapter(listaVehiculo);
                        spinnerVehiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                Vehiculo vehiculo= (Vehiculo) adapterView.getItemAtPosition(i);
                                ID_EXTERNAL_VEHICULO=vehiculo.external_id;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toas1 = Toast.makeText(getApplicationContext(), getString(R.string.msg_no_busqueda), Toast.LENGTH_SHORT);
                        toas1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toas1.show();
                        return;
                    }
                }
        );
        requestQueue.add(vehiculo);
    }


    private void consultaPlaza() {
        VolleyPeticion<Plaza[]> plaza = Conexion.listarPlazas(
                getApplicationContext(),
                MapsActivity.ID_PARQUEADERO,
                new Response.Listener<Plaza[]>() {
                    @Override
                    public void onResponse(Plaza[] response) {
                        listaPlaza = new ListaPlaza(Arrays.asList(response), getApplicationContext());
                        listaPlaza.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerPlaza.setAdapter(listaPlaza);
                        spinnerPlaza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                Plaza plaza= (Plaza) adapterView.getItemAtPosition(i);
                                ID_EXTERNAL_PLAZA=plaza.external_id;
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toas1 = Toast.makeText(getApplicationContext(), getString(R.string.msg_no_busqueda), Toast.LENGTH_SHORT);
                        toas1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toas1.show();
                        return;
                    }
                }
        );
        requestQueue.add(plaza);
    }

}
