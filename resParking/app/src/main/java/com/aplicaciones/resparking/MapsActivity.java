package com.aplicaciones.resparking;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.aplicaciones.resparking.modelo.Vehiculo;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
//import com.google.android.gms.common.api.Response;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

//import static com.aplicaciones.resparking.R.id.page_content;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    public static String TOKEN = "";
    public static String ID_EXTERNAL = "";
    public static String ID_EXTERNAL_USER = "";
    public static String ID_PARQUEADERO = "";

    private GoogleMap mMap;
    private Marker marcador;
    double lat = 0.0;
    double lng = 0.0;

    private ListaVehiculo listaAdaptador;
    private ListaParqueadero listaAdaptadorP;
    private ListView listView;

    private List<Parqueadero> dataset;

    private TextView nombre;
    private TextView correo;
    private ImageView foto;

    private RequestQueue requestQueue;

    private MarkerOptions marker;

    private AlertDialog dialog;

    private ViewPager mViewPager;

    private TabLayout mTabLayout;


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

    private void listaReservacioU() {
        Intent intent = new Intent(this, ListarReservacionU.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

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

    public void Puntos(GoogleMap googleMap) {
        mMap = googleMap;
        final LatLng parqueaderoSanFrancisco = new LatLng(-4.0086472, -79.2034037);
        final LatLng parqueoPublico = new LatLng(-4.003510, -79.200831);
        final LatLng parqueoAzuay = new LatLng(-4.0048335, -79.211932);
        final LatLng parqueoPublio1 = new LatLng(-4.0040273, -79.202404);
        final LatLng parqueoPatioVillage = new LatLng(-4.0027127, -79.2114909);
        final Marker marcador = mMap.addMarker(new MarkerOptions().position(parqueaderoSanFrancisco).title(""));
        final Marker marcador1 = mMap.addMarker(new MarkerOptions().position(parqueoPublico).title(""));
        final Marker marcador2 = mMap.addMarker(new MarkerOptions().position(parqueoAzuay).title(""));
        final Marker marcador3 = mMap.addMarker(new MarkerOptions().position(parqueoPublio1).title(""));
        final Marker marcador4 = mMap.addMarker(new MarkerOptions().position(parqueoPatioVillage).title(""));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                double latitu = marcador.getPosition().latitude;
                String latitul = String.valueOf(latitu);
                double latitud = marcador1.getPosition().latitude;
                String latitud1 = String.valueOf(latitud);
                double latitud2 = marcador2.getPosition().latitude;
                String latitud3 = String.valueOf(latitud2);
                double latitud4 = marcador3.getPosition().latitude;
                String latitud5 = String.valueOf(latitud4);
                double latitud6 = marcador4.getPosition().latitude;
                String latitud7 = String.valueOf(latitud6);
                if (marker.equals(marcador)) {
                    consultarWs("7cc8fa2f-c376-40e2-91b1-f5f6a1ac3678", latitul);
                }
                if (marker.equals(marcador1)) {
                    consultarWs("7cc8fa2f-c376-40e2-91b1-f5f6a1ac3678", latitud1);
                }
                if (marker.equals(marcador2)) {
                    consultarWs("7cc8fa2f-c376-40e2-91b1-f5f6a1ac3678", latitud3);
                }
                if (marker.equals(marcador3)) {
                    consultarWs("7cc8fa2f-c376-40e2-91b1-f5f6a1ac3678", latitud5);
                }
                if (marker.equals(marcador4)) {
                    consultarWs("7cc8fa2f-c376-40e2-91b1-f5f6a1ac3678", latitud7);
                }
                return false;
            }
        });
    }

    private void consultarWs(String exIdAdmin, String x) {
        VolleyPeticion<Parqueadero> films = Conexion.getParqueaderos(
                this,
                exIdAdmin,
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
                        Button reservacion = (Button) mView.findViewById(R.id.dialogButtonEscoger);
                        Button cerrar = (Button) mView.findViewById(R.id.dialogButtonCerrar);

                        dialog = mBuilder.create();
                        dialog.show();

                        reservacion.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (AccessToken.getCurrentAccessToken() == null) {
                                    MapsActivity.ID_PARQUEADERO = response.external_id;
                                    goLoginFB();
                                } else {
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


    private void ingresarVehiculo() {
        Intent intent = new Intent(this, IngresarVehiculo.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void reservacionAdd() {
        Intent intent = new Intent(this, ReservacionAdd.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void loginAdmin() {
        Intent intent = new Intent(this, LoginAdministrador.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goLoginFB() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void agregarMarcador(double lat, double lng) {
        LatLng coordenada = new LatLng(lat, lng);
        CameraUpdate miUbicacion = CameraUpdateFactory.newLatLngZoom(coordenada, 16);
        if (marcador != null) marcador.remove();
        mMap.animateCamera(miUbicacion);
    }

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

    private void miUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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

        if (id == R.id.nav_parqueadero) {
        } else if (id == R.id.nav_placa) {
            ingresarVehiculo();
        } else if (id == R.id.nav_listar) {
            // listarActivity();
            listaReservacioU();
            //listarVehiculo("aa17640e-8c90-46fd-ba8f-06698580467b");
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_logOut) {
            logOut();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void listarVehiculo(String exID_usuario) {
        VolleyPeticion<Vehiculo[]> vehiculo = Conexion.listarVehiculo(
                this, exID_usuario, new Response.Listener<Vehiculo[]>() {
                    @Override
                    public void onResponse(Vehiculo[] response) {
                        listaAdaptador = new ListaVehiculo(Arrays.asList(response), getApplicationContext());
                        listView.setAdapter(listaAdaptador);
                        //dialog();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast1 = Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.error_busqueda)
                                , Toast.LENGTH_SHORT);
                    }
                }
        );
        requestQueue.add(vehiculo);
    }

    private void logOut() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
    }

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
