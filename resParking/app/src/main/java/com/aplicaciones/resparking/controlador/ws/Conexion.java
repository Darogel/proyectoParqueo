package com.aplicaciones.resparking.controlador.ws;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.aplicaciones.resparking.modelo.Administrador;
import com.aplicaciones.resparking.modelo.Parqueadero;
import com.aplicaciones.resparking.modelo.Plaza;
import com.aplicaciones.resparking.modelo.Reservacion;
import com.aplicaciones.resparking.modelo.Usuario;
import com.aplicaciones.resparking.modelo.Vehiculo;

import java.util.HashMap;

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

    public static  VolleyPeticion<Parqueadero[]> listarParqueadero(
            @NonNull final Context context,
            @NonNull String exIdAdmin,
            @NonNull Response.Listener<Vehiculo[]> responseListener,
            @NonNull Response.ErrorListener errorListener
    ){
        exIdAdmin= exIdAdmin.replace(" ","+");
        final String url = APi_URL +"parqueadero/listar/"+exIdAdmin;
        VolleyPeticion peticion= new VolleyPeticion(context, Request.Method.GET,url,responseListener,errorListener);
        peticion.setResponseClass(Parqueadero[].class);
        return peticion;
    }

    public static VolleyPeticion<Parqueadero> getParqueaderos(
            @NonNull final Context context,
            @NonNull String x,
            @NonNull Response.Listener<Parqueadero> responseListener,
            @NonNull Response.ErrorListener errorListener
    ){
        //id = id.replace( " ", "+");
        final String url = APi_URL + "parqueadero/listar/buscar/"+ x;
        VolleyPeticion peticion = new VolleyPeticion(context, Request.Method.GET,
                url,
                responseListener,
                errorListener);
        peticion.setResponseClass(Parqueadero.class);
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

    public static VolleyPeticion<Parqueadero[]> parqueaderoListar(
            @NonNull final Context context,
            @NonNull Response.Listener<Parqueadero[]> responseListener,
            @NonNull Response.ErrorListener errorListener
    ){
        final String url = APi_URL +"parqueadero/listar";
        VolleyPeticion peticion= new VolleyPeticion(context, Request.Method.GET,url,responseListener,errorListener);
        peticion.setResponseClass(Parqueadero[].class);
        return peticion;
    }



    public static VolleyPeticion<Reservacion[]> listarReservacionUs(
            @NonNull final Context context,
            @NonNull String exIdUsuario,
            @NonNull Response.Listener<Reservacion[]> responseListener,
            @NonNull Response.ErrorListener errorListener
    ){
        exIdUsuario= exIdUsuario.replace(" ","+");
        final String url = APi_URL +"reservacion/listarUs/"+exIdUsuario;
        VolleyPeticion peticion= new VolleyPeticion(context, Request.Method.GET,url,responseListener,errorListener);
        peticion.setResponseClass(Reservacion[].class);
        return peticion;
    }




    public static VolleyPeticion<Administrador> iniciarSesion(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Administrador> responseListener,
            @NonNull Response.ErrorListener errorListener){
        final String url = APi_URL+"admin/login";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener,errorListener);
        request.setResponseClass(Administrador.class);
        return request;
    }

    public static VolleyPeticion<Reservacion> eliminarReservacion(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Reservacion> responseListener,
            @NonNull Response.ErrorListener errorListener){
        final String url = APi_URL+"reservacion/eliminar";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener,errorListener);
        request.setResponseClass(Reservacion.class);
        return request;
    }
    public static VolleyPeticion<Parqueadero> registrarParqueadero(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Parqueadero> responseListener,
            @NonNull Response.ErrorListener errorListener){
        final String url = APi_URL+"parqueadero/registrar";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener,errorListener);
        request.setResponseClass(Parqueadero.class);
        return request;
    }

    public static VolleyPeticion<Vehiculo> registrarVehiculo(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Vehiculo> responseListener,
            @NonNull Response.ErrorListener errorListener){
        final String url = APi_URL+"vehiculo/registrar";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener,errorListener);
        request.setResponseClass(Vehiculo.class);
        return request;
    }

    public static VolleyPeticion<Reservacion> registrarReservacion(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Reservacion> responseListener,
            @NonNull Response.ErrorListener errorListener){
        final String url = APi_URL+"reservacion/registrar";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener,errorListener);
        request.setResponseClass(Reservacion.class);
        return request;
    }

    public static VolleyPeticion<Usuario> loginRegistrar(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Usuario> responseListener,
            @NonNull Response.ErrorListener errorListener){
        final String url = APi_URL+"usuario/loginReg";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener,errorListener);
        request.setResponseClass(Usuario.class);
        return request;
    }

    public static VolleyPeticion<Plaza> registrarPlaza(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Plaza> responseListener,
            @NonNull Response.ErrorListener errorListener){
        final String url = APi_URL+"plaza/registrar";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener,errorListener);
        request.setResponseClass(Plaza.class);
        return request;
    }
}
