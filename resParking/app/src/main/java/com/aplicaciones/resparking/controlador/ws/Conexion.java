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
    /**
     * Variable estatica que contiene el URL del Host para conectar con el WebService
     */
    private static final String APi_URL = "https://parqueoapp.000webhostapp.com/public/";

    /**
     * Metodo para realizar una VoleyPeticion del tipo Vehiculo[] GET para listar
     * @param context contexto actual
     * @param exIdUsuario External Id del Usuario
     * @param responseListener Listener del tipo Vehiculo[] en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     */
    public static VolleyPeticion<Vehiculo[]> listarVehiculo(
            @NonNull final Context context,
            @NonNull String exIdUsuario,
            @NonNull Response.Listener<Vehiculo[]> responseListener,
            @NonNull Response.ErrorListener errorListener
    ) {
        exIdUsuario = exIdUsuario.replace(" ", "+");
        final String url = APi_URL + "vehiculo/listar/" + exIdUsuario;
        VolleyPeticion peticion = new VolleyPeticion(context, Request.Method.GET, url, responseListener, errorListener);
        peticion.setResponseClass(Vehiculo[].class);
        return peticion;
    }

    /**
     * Metodo para realizar una VoleyPeticion del tipo Parqueadero[] GET para listar
     * @param context contexto actual
     * @param exIdAdmin External Id del Administrador
     * @param responseListener Listener del tipo Parqueadero[] en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     */
    public static VolleyPeticion<Parqueadero[]> listarParqueadero(
            @NonNull final Context context,
            @NonNull String exIdAdmin,
            @NonNull Response.Listener<Parqueadero[]> responseListener,
            @NonNull Response.ErrorListener errorListener
    ) {
        exIdAdmin = exIdAdmin.replace(" ", "+");
        final String url = APi_URL + "parqueadero/listar/" + exIdAdmin;
        VolleyPeticion peticion = new VolleyPeticion(context, Request.Method.GET, url, responseListener, errorListener);
        peticion.setResponseClass(Parqueadero[].class);
        return peticion;
    }

    /**
     * Metodo para realizar una VoleyPeticion del tipo Parqueadero GET para obtener un parqueadero especifico
     * @param context contexto actual
     * @param x External Id del parqueadero a buscar
     * @param responseListener Listener del tipo Parqueadero en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     */
    public static VolleyPeticion<Parqueadero> getParqueaderos(
            @NonNull final Context context,
            @NonNull String x,
            @NonNull Response.Listener<Parqueadero> responseListener,
            @NonNull Response.ErrorListener errorListener
    ) {
        final String url = APi_URL + "parqueadero/listar/buscar/" + x;
        VolleyPeticion peticion = new VolleyPeticion(context, Request.Method.GET,
                url,
                responseListener,
                errorListener);
        peticion.setResponseClass(Parqueadero.class);
        return peticion;
    }

    /**
     * Metodo para realizar una VoleyPeticion del tipo Plaza[] GET para listar
     * @param context contexto actual
     * @param exIdParqueadero External Id del parqueadero
     * @param responseListener Listener del tipo Plaza[] en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     */
    public static VolleyPeticion<Plaza[]> listarPlazas(
            @NonNull final Context context,
            @NonNull String exIdParqueadero,
            @NonNull Response.Listener<Plaza[]> responseListener,
            @NonNull Response.ErrorListener errorListener
    ) {
        exIdParqueadero = exIdParqueadero.replace(" ", "+");
        final String url = APi_URL + "plaza/listar/" + exIdParqueadero;
        VolleyPeticion peticion = new VolleyPeticion(context, Request.Method.GET, url, responseListener, errorListener);
        peticion.setResponseClass(Plaza[].class);
        return peticion;
    }

    /**
     * Metodo para realizar una VoleyPeticion del tipo Reservacion[] GET para listar por parqueadero
     * @param context contexto actual
     * @param exIdParqueadero External Id del parqueadero
     * @param responseListener Listener del tipo Reservacion[] en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     */
    public static VolleyPeticion<Reservacion[]> listarReservacionParq(
            @NonNull final Context context,
            @NonNull String exIdParqueadero,
            @NonNull Response.Listener<Reservacion[]> responseListener,
            @NonNull Response.ErrorListener errorListener
    ) {
        exIdParqueadero = exIdParqueadero.replace(" ", "+");
        final String url = APi_URL + "reservacion/listarParq/" + exIdParqueadero;
        VolleyPeticion peticion = new VolleyPeticion(context, Request.Method.GET, url, responseListener, errorListener);
        peticion.setResponseClass(Reservacion[].class);
        return peticion;
    }

    /**
     * Metodo para realizar una VoleyPeticion del tipo Parqueadero[] GET para listar todos los parquaderos
     * @param context contexto actual
     * @param responseListener Listener del tipo Parqueadero[] en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     */
    public static VolleyPeticion<Parqueadero[]> parqueaderoListar(
            @NonNull final Context context,
            @NonNull Response.Listener<Parqueadero[]> responseListener,
            @NonNull Response.ErrorListener errorListener
    ) {
        final String url = APi_URL + "parqueadero/listar";
        VolleyPeticion peticion = new VolleyPeticion(context, Request.Method.GET, url, responseListener, errorListener);
        peticion.setResponseClass(Parqueadero[].class);
        return peticion;
    }


    /**
     * Metodo para realizar una VoleyPeticion del tipo Reservacion[] GET para listar por usuario
     * @param context contexto actual
     * @param exIdUsuario External Id del Usuario
     * @param responseListener Listener del tipo Reservacion[] en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     */
    public static VolleyPeticion<Reservacion[]> listarReservacionUs(
            @NonNull final Context context,
            @NonNull String exIdUsuario,
            @NonNull Response.Listener<Reservacion[]> responseListener,
            @NonNull Response.ErrorListener errorListener
    ) {
        exIdUsuario = exIdUsuario.replace(" ", "+");
        final String url = APi_URL + "reservacion/listarUs/" + exIdUsuario;
        VolleyPeticion peticion = new VolleyPeticion(context, Request.Method.GET, url, responseListener, errorListener);
        peticion.setResponseClass(Reservacion[].class);
        return peticion;
    }

    /**
     * Metodo para realizar una VoleyPeticion del tipo Administrador POST para iniciar sesion
     * @param context contexto actual
     * @param mapa HashMap que contiene los datos a enviar en el post
     * @param responseListener Listener del tipo Administrador en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     */
    public static VolleyPeticion<Administrador> iniciarSesion(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Administrador> responseListener,
            @NonNull Response.ErrorListener errorListener) {
        final String url = APi_URL + "admin/login";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener, errorListener);
        request.setResponseClass(Administrador.class);
        return request;
    }

    /**
     * Metodo para realizar una VoleyPeticion del tipo Reservacion POST para eliminar una reservacion
     * @param context contexto actual
     * @param mapa HashMap que contiene los datos a enviar en el post
     * @param responseListener Listener del tipo Reservacion en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     */
    public static VolleyPeticion<Reservacion> eliminarReservacion(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Reservacion> responseListener,
            @NonNull Response.ErrorListener errorListener) {
        final String url = APi_URL + "reservacion/eliminar";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener, errorListener);
        request.setResponseClass(Reservacion.class);
        return request;
    }

    /**
     * Metodo para realizar una VoleyPeticion del tipo Parqueadero POST para registrar parqueadero
     * @param context contexto actual
     * @param mapa HashMap que contiene los datos a enviar en el post
     * @param responseListener Listener del tipo Parqueadero en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     */
    public static VolleyPeticion<Parqueadero> registrarParqueadero(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Parqueadero> responseListener,
            @NonNull Response.ErrorListener errorListener) {
        final String url = APi_URL + "parqueadero/registrar";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener, errorListener);
        request.setResponseClass(Parqueadero.class);
        return request;
    }

    /**
     * Metodo para realizar una VoleyPeticion del tipo Vehiculo POST para registrar vehiculo
     * @param context contexto actual
     * @param mapa HashMap que contiene los datos a enviar en el post
     * @param responseListener Listener del tipo Vehiculo en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     */
    public static VolleyPeticion<Vehiculo> registrarVehiculo(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Vehiculo> responseListener,
            @NonNull Response.ErrorListener errorListener) {
        final String url = APi_URL + "vehiculo/registrar";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener, errorListener);
        request.setResponseClass(Vehiculo.class);
        return request;
    }

    /**
     * Metodo para realizar una VoleyPeticion del tipo Reservacion POST para registrar reservacion
     * @param context contexto actual
     * @param mapa HashMap que contiene los datos a enviar en el post
     * @param responseListener Listener del tipo Reservacion en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     */
    public static VolleyPeticion<Reservacion> registrarReservacion(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Reservacion> responseListener,
            @NonNull Response.ErrorListener errorListener) {
        final String url = APi_URL + "reservacion/registrar";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener, errorListener);
        request.setResponseClass(Reservacion.class);
        return request;
    }

    /**
     * Metodo para realizar una VoleyPeticion del tipo Usuario POST para registrar o logear usuario
     * @param context contexto actual
     * @param mapa HashMap que contiene los datos a enviar en el post
     * @param responseListener Listener del tipo Usuario en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     */
    public static VolleyPeticion<Usuario> loginRegistrar(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Usuario> responseListener,
            @NonNull Response.ErrorListener errorListener) {
        final String url = APi_URL + "usuario/loginReg";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener, errorListener);
        request.setResponseClass(Usuario.class);
        return request;
    }

    /**
     * Metodo para realizar una VolleyPeticion del tipo Administrador POST para registrar una nueva plaza
     * @param context contexto actual
     * @param mapa HashMap que contiene los datos a enviar en el post
     * @param responseListener Listener del Tipo Plaza en este se indica que hacer si la peticion es exitosa
     * @param errorListener en este se indica que hacer si la peticion es erronea
     * @return
     */
    public static VolleyPeticion<Plaza> registrarPlaza(
            @NonNull final Context context,
            @NonNull final HashMap mapa,
            @NonNull final Response.Listener<Plaza> responseListener,
            @NonNull Response.ErrorListener errorListener) {
        final String url = APi_URL + "plaza/registrar";
        VolleyPeticion request = new VolleyPeticion(
                context,
                Request.Method.POST,
                url,
                mapa,
                HashMap.class,
                String.class,
                responseListener, errorListener);
        request.setResponseClass(Plaza.class);
        return request;
    }

}
