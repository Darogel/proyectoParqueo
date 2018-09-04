package com.aplicaciones.resparking.modelo;

/**
 * Clase que maneja la informacion enviada y recibida por el WebService del modelo Vehiculo
 * Con todos los campos usados
 */
public class Vehiculo {
    /**
     * Variable Tipo String implementada para enviar y recibir la placa del vehiculo
     * Valor que se obtiene de la base de datos del Host
     */
    public String placa;
    /**
     * Variable Tipo String implementada para enviar y recibir el external_id del vehiculo
     * Valor que se obtiene de la base de datos del Host
     */
    public String external_id;
    /**
     * Variable Tipo String implementada para enviar y recibir la fecha de creacion de un vehiculo
     * Valor que se obtiene de la base de datos del Host
     */
    public String created_at;
    /**
     * Variable Tipo String implementada para enviar y recibir el estado [activo, inactivo] del vehiculo
     * Valor que se obtiene de la base de datos del Host
     */
    public String estado;

    /**
     * Metodo implementado para mostrar informacion completa de un Objeto
     * @return valor del atributo plaza  del Objeto
     */
    @Override
    public String toString(){
        return placa;
    }
}
