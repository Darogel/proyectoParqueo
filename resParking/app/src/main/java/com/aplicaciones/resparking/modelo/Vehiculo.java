package com.aplicaciones.resparking.modelo;

/**
 * Clase que maneja la informacion enviada y recibida por el WebService del modelo Vehiculo
 * Con todos los campos usados
 */
public class Vehiculo {
    public String placa;
    public String external_id;
    public String created_at;
    public String estado;
    @Override
    public String toString(){
        return placa;
    }
}
