package com.aplicaciones.resparking.modelo;

/**
 * Clase que maneja la informacion enviada y recibida por el WebService del modelo Parqueadero
 * Con todos los campos usados
 */
public class Parqueadero {
    /**
     * Variable Tipo String implementada para enviar y recibir el nombre del parqueadero
     * Valor que se obtiene de la base de datos del Host
     */
    public String nombre;
    /**
     * Variable Tipo String implementada para enviar y recibir el external_id del administrador
     * Valor que se obtiene de la base de datos del Host
     */
    public String external_id;
    /**
     * Variable Tipo String implementada para enviar y recibir la latitud de la ubicacion del Parqueadero
     * Valor que se obtiene de la base de datos del Host
     */
    public String coordenada_x;
    /**
     * Variable Tipo String implementada para enviar y recibir la longitud de la ubicacion del Parqueadero
     * Valor que se obtiene de la base de datos del Host
     */
    public String coordenada_y;
    /**
     * Variable Tipo String implementada para enviar y recibir el precio hora del parqueadero
     * Valor que se obtiene de la base de datos del Host
     */
    public String precio_hora;
    /**
     * Variable Tipo String implementada para enviar y recibir el numero de plazas que posee el parqueadero
     * Valor que se obtiene de la base de datos del Host
     */
    public String numero_plazas;
    /**
     * Variable Tipo String implementada para enviar y recibir la fecha de creacion del parqueadero
     * Valor que se obtiene de la base de datos del Host
     */
    public String created_at;

    /**
     * Metodo implementado para mostrar informacion completa de un Objeto
     * @return valor del atributo nombre  del Objeto
     */
    @Override
    public String toString(){
        return nombre;
    }

}
