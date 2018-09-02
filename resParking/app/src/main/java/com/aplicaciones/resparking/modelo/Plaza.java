package com.aplicaciones.resparking.modelo;

/**
 * Clase que maneja la informacion enviada y recibida por el WebService del modelo Plaza
 * Con todos los campos usados
 */
public class Plaza {
    public String numero_puesto;
    public String external_id;
    public String tipo;
    public String estado;
    public String created_at;

    @Override
    public String toString(){
        return numero_puesto +" - "+ tipo;
    }



}
