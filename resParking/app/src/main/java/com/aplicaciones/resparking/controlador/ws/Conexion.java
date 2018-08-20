package com.aplicaciones.resparking.controlador.ws;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.aplicaciones.resparking.modelo.Plaza;
import com.aplicaciones.resparking.modelo.Reservacion;
import com.aplicaciones.resparking.modelo.Vehiculo;

public class Conexion {
    private static final String APi_URL = "https://parqueoapp.000webhostapp.com/public/";

    public static VolleyPeticion<Vehiculo[]> listarVehiculo(
            @NonNull final Context context,
            @NonNull String exIdUsuario,
            @NonNull Response.Listener<Vehiculo[]> responseListener,
            @NonNull Response.ErrorListener errorListener
    ){
        exIdUsuario= exIdUsuario.replace(" ","+");
        final String url = APi_URL +"vehiculo/listar/"+exIdUsuario;
        VolleyPeticion peticion= new VolleyPeticion(context, Request.Method.GET,url,responseListener,errorListener);
        peticion.setResponseClass(Vehiculo[].class);
        return peticion;
    }

    public static VolleyPeticion<Plaza[]> listarPlazas(
            @NonNull final Context context,
            @NonNull String exIdParqueadero,
            @NonNull Response.Listener<Plaza[]> responseListener,
            @NonNull Response.ErrorListener errorListener
    ){
        exIdParqueadero= exIdParqueadero.replace(" ","+");
        final String url = APi_URL +"plaza/listar/"+exIdParqueadero;
        VolleyPeticion peticion= new VolleyPeticion(context, Request.Method.GET,url,responseListener,errorListener);
        peticion.setResponseClass(Plaza[].class);
        return peticion;
    }

    public static VolleyPeticion<Reservacion[]> listarReservacionParq(
            @NonNull final Context context,
            @NonNull String exIdParqueadero,
            @NonNull Response.Listener<Reservacion[]> responseListener,
            @NonNull Response.ErrorListener errorListener
    ){
        exIdParqueadero= exIdParqueadero.replace(" ","+");
        final String url = APi_URL +"reservacion/listarParq/"+exIdParqueadero;
        VolleyPeticion peticion= new VolleyPeticion(context, Request.Method.GET,url,responseListener,errorListener);
        peticion.setResponseClass(Reservacion[].class);
        return peticion;
    }

    public static VolleyPeticion<Reservacion[]> listarReservacionUs(
            @NonNull final Context context,
            @NonNull String exIdUsuario,
            @NonNull Response.Listener<Reservacion[]> responseListener,
            @NonNull Response.ErrorListener errorListener
    ){
        exIdUsuario= exIdUsuario.replace(" ","+");
        final String url = APi_URL +"plaza/listar/"+exIdUsuario;
        VolleyPeticion peticion= new VolleyPeticion(context, Request.Method.GET,url,responseListener,errorListener);
        peticion.setResponseClass(Reservacion[].class);
        return peticion;
    }



   /* public static VolleyPeticion<PeliculasJson> getPelicula(
            @NonNull final Context context,
            @NonNull String imbd,
            @NonNull Response.Listener<PeliculasJson> responseListener,
            @NonNull Response.ErrorListener errorListener
    ){
        final String url = APi_URL +"&i="+imbd+"&plot=full";
        VolleyPeticion peticion= new VolleyPeticion(context, Request.Method.GET,url,responseListener,errorListener);
        peticion.setResponseClass(PeliculasJson.class);
        return peticion;
    }*/
}
