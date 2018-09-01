package com.aplicaciones.resparking;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.aplicaciones.resparking.controlador.adaptador.ListaParqueadero;
import com.aplicaciones.resparking.controlador.adaptador.ListaVehiculo;
import com.aplicaciones.resparking.controlador.ws.Conexion;
import com.aplicaciones.resparking.controlador.ws.VolleyPeticion;
import com.aplicaciones.resparking.controlador.ws.VolleyProcesadorResultado;
import com.aplicaciones.resparking.controlador.ws.VolleyTiposError;
import com.aplicaciones.resparking.modelo.Parqueadero;
import com.aplicaciones.resparking.modelo.Usuario;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;


public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    /**
     * Variables Staticas utilizadas para el desarrollo de la aplicacion
     * Implementadas en metodos De Ingresar, Reservar, Listar
     */
    public static String TOKEN = "";
    public static String ID_EXTERNAL = "";
    public static String ID_EXTERNAL_USER = "";
    public static String ID_PARQUEADERO = "";

    /**
     * Variables Utilizadas en Metodos de Geolocalizacion de GoogleMaps
     * Añadir marcadores y Localización
     */
    private GoogleMap mMap;
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;

    /**
     * Variables De clases Listar Vehiculo y Listar Parqueadero
     */
    private ListaVehiculo listaAdaptador;
    private ListaParqueadero listaAdaptadorP;
    private ListView listView;
    private List<Parqueadero> dataset;

    /**
     * Variables implementadas para hacer uso de los layouts
     * Login con Facebook, Mostrar Informacion de Parqueaderos
     */
    private TextView nombre;
    private TextView correo;
    private ImageView foto;
    private AlertDialog dialog;

    /**
     * Variable utilizada para Utilizacion de base de Datos}
     */
    private RequestQueue requestQueue;

    /**
     * Variable Statica para la respuesta de Localizacion
     */
    private static final int LOCATION_REQUEST = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);



        nombre = (TextView) hView.findViewById(R.id.txtNombreN);
        correo = (TextView) hView.findViewById(R.id.txtCorreoN);
        foto = (ImageView) hView.findViewById(R.id.imageViewN);
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photo = user.getPhotoUrl();
            loginRegistrarUsuario(email);
            nombre.setText(name);
            correo.setText(email);
            Picasso.get().load(photo).into((ImageView) foto);
        } else {
            nombre.setText("Nombre");
            correo.setText("Correo");
        }

    }

    /**
     * Metodo Implementado Para Lammar la Actividad ListarReservacion por Usuario
     */
    private void listaReservacioU() {
        Intent intent = new Intent(this, ListarReservacionU.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Metodo Utilizado para Permisos de GPS de la aplicacion hacia el Usuario
     */
    public void permiso() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        miUbicacion();
        Puntos(googleMap);

        permiso();


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
                break;

        }
    }

    /**
     * Metodo implementado para añadir Los diferentes parqueos Como Marcadores en el mapa
     * Parqueaderos Recividos desde la Base de Datos del Servicio
     * @param googleMap  Variable de Googlemap utilizada Para Agregar Marcadores en el Mapa
     */
    public void Puntos(GoogleMap googleMap) {
        mMap = googleMap;
        final VolleyPeticion<Parqueadero[]> lista = Conexion.parqueaderoListar(
                this,
                new Response.Listener<Parqueadero[]>() {
                    @Override
                    public void onResponse(Parqueadero[] response) {
                        for (int i = 0; i < response.length; i++){
                            double dox = Double.parseDouble(response[i].coordenada_x);
                            double doy = Double.parseDouble(response[i].coordenada_y);
                            final  LatLng marcador = new LatLng(dox, doy);
                            final Marker mard = mMap.addMarker(new MarkerOptions().position(marcador));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
        requestQueue.add(lista);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                double latitu = marker.getPosition().latitude;
                String latitul = String.valueOf(latitu);
                consultarWs(latitul);
                return false;
            }
        });
    }


    /**
     * Metodo implementado en La busqueda de Informacion del Parqueadero
     * Seleccionar el Marcador de un Parqueo y Muestra Informacion de denominado Parqueo
     * @param x Variable Tipo String Que recibe la Coordenada X de del Parqueadero
     */
    private void consultarWs(String x) {
        VolleyPeticion<Parqueadero> films = Conexion.getParqueaderos(
                this,
                x,
                new Response.Listener<Parqueadero>() {
                    @Override
                    public void onResponse(final Parqueadero response) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MapsActivity.this);
                        final View mView = getLayoutInflater().inflate(R.layout.modal_marcador, null);
                        mBuilder.setView(mView);
                        TextView nombre = (TextView) mView.findViewById(R.id.nombre_parqueo);
                        nombre.setText(response.nombre);
                        TextView plaza = (TextView) mView.findViewById(R.id.plaza);
                        plaza.setText(response.numero_plazas);
                        TextView precio = (TextView) mView.findViewById(R.id.external);
                        precio.setText(response.precio_hora);

                        System.out.println(response.external_id);
                        Button reservacion = (Button) mView.findViewById(R.id.dialogButtonEscoger);
                        Button cerrar = (Button) mView.findViewById(R.id.dialogButtonCerrar);

                        dialog = mBuilder.create();
                        dialog.show();

                        reservacion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (AccessToken.getCurrentAccessToken() == null) {
                                    goLoginFB();
                                } else {
                                    ID_PARQUEADERO = response.external_id;
                                    reservacionAdd();
                                }
                            }
                        });

                        cerrar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                    }


                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast1 = Toast.makeText(getApplicationContext(),
                                getApplicationContext().getString(R.string.msg_no_busqueda),
                                Toast.LENGTH_LONG);
                        toast1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        toast1.show();
                    }
                }
        );
        requestQueue.add(films);
    }


    /**
     * Metodo Utilizado para llamar la Actividad de Ingresar Vehiculo
     */
    private void ingresarVehiculo() {
        Intent intent = new Intent(this, IngresarVehiculo.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Metodo Utilizado para llamar la Actividad de Ingresar Reservacion
     */
    private void reservacionAdd() {
        Intent intent = new Intent(this, ReservacionAdd.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    /**
     * Metodo Utilizado para llamar la Actividad de Iniciar Sesion como Administrador
     */
    private void loginAdmin() {
        Intent intent = new Intent(this, LoginAdministrador.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Metodo Utilizado para llamar la Actividad de Iniciar Sesion como Usuario
     * realizado mediante Facebook
     */
    private void goLoginFB() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * Metodo implementado para Mover camara hacia Ubicacion Actual
     * Detecta Localizacion del Usuario y  mueve la camara hacia dicha ubicacion
     * @param lat Variable tipo Double que recibe la Latitud de Nuestra Ubicacion
     * @param lng Variable tipo Double que recibe la Longitud de Nuestra Ubicacion
     */
    private void agregarMarcador(double lat, double lng) {
        LatLng coordenada = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenada, 16);
        if (marcador != null) marcador.remove();
        mMap.animateCamera(miUbicacion);
    }

    /**
     * Metodo implementado para actualizar la ubicacion del usuario
     * Cuando el Usuario explora por el Mapa permite mover el Mapa
     ** @param location Variable de Tipo Location que Recibe Nuestra Ubicacion
     */
    public void actualizarUbicacion(Location location) {
        if (location != null) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            agregarMarcador(lat, lng);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    /**
     * Metodo implementado para Obtener Ubicacion Actual del Usuario
     * Teniendo Permisos de Localizacion Establecido en el Manifest
     * Obtiene la Ubicacion Precisa del Usuario
     * Utiliza el metodo ACTUALIZAR UBICACION para mover la camara hacia dicha Ubicacion
     */
    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        location.getLatitude();
       // location.getLongitude();
        actualizarUbicacion(location);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                10000, 0, locationListener);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            loginAdmin();
        }

        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_placa) {
            ingresarVehiculo();
        } else if (id == R.id.nav_listar) {
            listaReservacioU();
        } else if (id == R.id.nav_logOut) {
            logOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Metodo implementado para Cerrar Sesion del Usuario
     * Cierra sesion de Facebook  en la  aplicacion
     */
    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }

    /**
     * Metodo implementado para Guardar Correo del Usuario en Base de Datos
     * Cuando el Usario Ingresa mediante facebook se guarda su coorreo en base de datos del servicio
     * Para Verificar al momento de su nuevo ingreso si esta aun su cesión iniciada
     * @param correo Variable tipo String que recibe Correo de Facebook del Usuario
     */
    private void loginRegistrarUsuario(String correo) {
        HashMap<String, String> mapa = new HashMap<>();
        mapa.put("correo", correo);

        VolleyPeticion<Usuario> inicio = Conexion.loginRegistrar(
                getApplicationContext(),
                mapa,
                new Response.Listener<Usuario>() {
                    @Override
                    public void onResponse(Usuario response) {
                        if (response != null) {
                            //   MapsActivity.TOKEN = response.token;
                            MapsActivity.ID_EXTERNAL_USER = response.external_id;
                            Toast.makeText(getApplicationContext(), "Bienvenido", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyTiposError errores = VolleyProcesadorResultado.parseErrorResponse(error);


                    }
                }
        );
        requestQueue.add(inicio);
    }

}
