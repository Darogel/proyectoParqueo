package com.aplicaciones.resparking.modelo;

/**
 * Clase que maneja la informacion enviada y recibida por el WebService del modelo Parqueadero
 * Con todos los campos usados
 */
public class Parqueadero {
    public String nombre;
    public String external_id;
    public String coordenada_x;
    public String coordenada_y;
    public String precio_hora;
    public String numero_plazas;
    public String created_at;

    @Override
    public String toString(){
        return nombre;
    }

}