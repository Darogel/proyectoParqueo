package com.aplicaciones.resparking;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import com.aplicaciones.resparking.modelo.Plaza;
import com.aplicaciones.resparking.modelo.Reservacion;
import com.aplicaciones.resparking.modelo.Vehiculo;


import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;


/**
 * Clase implementada para agregar una nueva Reservacion a un parqueadero por parte de un usuario
 * Extendible de la super clase AppCompatActivity
 */
public class ReservacionAdd extends AppCompatActivity implements View.OnClickListener {

    /**
     * Variable estatica implemetada para guardar el external_id de Plaza
     */
    public static String ID_EXTERNAL_PLAZA = "";
    /**
     * Variable estatica implemetada para guardar el external_id de Vehiculo
     */
    public static String ID_EXTERNAL_VEHICULO = "";

    /**
     * Variables de tipo entero para utilizacion del reloj de Hora entrada y salida
     */
    private int hora, minutos;

    /**
     * Variable para recibir datos desde el layout de añadir Reservacion
     */
    private EditText etxt_hEntrada;
    /**
     * Variable para recibir datos desde el layout de añadir Reservacion
     */
    private EditText etxt_hSalida;
    /**
     * Variable para recibir datos desde el layout de añadir Reservacion
     */
    private TextView txt_plazaR;
    /**
     * Variable para recibir datos desde el layout de añadir Reservacion
     */
    private TextView txt_vehiculoR;
    /**
     * Variable para recibir datos desde el layout de añadir Reservacion
     */
    private ImageButton btn_hEntradaR;
    /**
     * Variable para recibir datos desde el layout de añadir Reservacion
     */
    private ImageButton btn_hSalidaR;
    /**
     * Variable para recibir datos desde el layout de añadir Reservacion
     */
    private Button btn_guardarR;
    /**
     * Variable para recibir datos desde el layout de añadir Reservacion
     */
    private Button btn_volverR;
    /**
     * Variable para recibir datos desde el layout de añadir Reservacion
     */
    private Spinner spinnerVehiculo;
    /**
     * Variable para recibir datos desde el layout de añadir Reservacion
     */
    private Spinner spinnerPlaza;

    /**
     * Variable de tipo listar Vehiculo
     */
    private ListaVehiculo listaVehiculo;

    /**
     * Variable de tipo Listar Plaza
     */
    private ListaPlaza listaPlaza;

    /**
     * Variable utilizada para crear una cola de peticiones hacia la base de datos del Host
     */
    private RequestQueue requestQueue;

    /**
     * Metodo implementado para inicio de la actividad
     * Se llama un recurso de diseño que define su UI
     * Recupera  widgets del layout activity_reservacion_add
     * @param savedInstanceState Guarda el estado de la aplicacion
     */
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
        return super.onOptionsItemSelected(item);

    }

    /**
     * Metodo implementado para llamar la actividad listar Reservacion del Usuario
     */
    private void listaReservacioU() {
        Intent intent = new Intent(this, ListarReservacionE.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Metodo sobreescrito para al ser presionado un boton ejecute una accion
     * btn_hEntradaR obtener de un timePikerDialog la hora seleccionada y presentar en etxt_hEntrada
     * btn_hSalidaR obtener de un timePikerDialog la hora seleccionada y presentar en etxt_hSalida
     */
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

    /**
     * Metodo implementado para asignar la actividad que realizara
     * btn_guardarR
     * btn_volverR
     */
    private void oyente() {
        /**
         * Metodo implemtado registrar un reservacion
         * Comprueba que los datos no esten vacios
         * Llama metodo registrar reservacion de la clase Conexion
         */
        this.btn_guardarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String external = MapsActivity.ID_EXTERNAL;
                String entrada = etxt_hEntrada.getText().toString();
                String salida = etxt_hSalida.getText().toString();
                String plaza = ID_EXTERNAL_PLAZA;
                String vehiculo = ID_EXTERNAL_VEHICULO;
                if (entrada.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Este Campo No Puede estar Vacio", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (salida.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Este Campo No Puede estar Vacio", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (plaza.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Este Campo No Puede estar Vacio", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (vehiculo.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Este Campo No Puede estar Vacio", Toast.LENGTH_SHORT).show();
                    return;
                }
                HashMap<String, String> mapa = new HashMap<>();
                mapa.put("idP", plaza);
                mapa.put("idV", vehiculo);
                mapa.put("entrada", entrada);
                mapa.put("salida", salida);

                VolleyPeticion<Reservacion> regPar = Conexion.registrarReservacion(
                        getApplicationContext(),
                        mapa,
                        new Response.Listener<Reservacion>() {
                            @Override
                            public void onResponse(Reservacion response) {
                                Toast.makeText(getApplicationContext(), "Se ha añadido Su Reservacion", Toast.LENGTH_SHORT).show();
                                limpiarTexto();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyTiposError errores = VolleyProcesadorResultado.parseErrorResponse(error);
                            }
                        }
                );
                requestQueue.add(regPar);


            }
        });

        /**
         * Metodo implemetado para reiniciar el la variable estatica Id parqueadero
         * Volver a la actividad principal
         */
        this.btn_volverR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapsActivity.ID_PARQUEADERO = "";
                goToMaps();
            }
        });

    }

    /**
     * Metodo implementado para Limpiar texto despues de agregar una reservacion
     */
    public void limpiarTexto() {
        etxt_hEntrada.getText().clear();
        etxt_hSalida.getText().clear();
    }

    /**
     * Metodo implementado para llamar la actividad MapsActivity
     * activity de inicio
     */
    private void goToMaps() {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Metodo implementado para llenar el spinner con los diferentes vehiculos del usuario
     * Llama el metodo Listar Vehiculo de la Clase Conexion
     */
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

                                Vehiculo vehiculo = (Vehiculo) adapterView.getItemAtPosition(i);
                                ID_EXTERNAL_VEHICULO = vehiculo.external_id;
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

    /**
     * Metodo implementado para Llenar el spinner con los diferentes plazas de un Parqueadero
     * Llama el metodo Listar Plazas de la Clase Conexion
     */
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

                                Plaza plaza = (Plaza) adapterView.getItemAtPosition(i);
                                ID_EXTERNAL_PLAZA = plaza.external_id;
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
