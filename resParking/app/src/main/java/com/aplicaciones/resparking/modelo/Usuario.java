package com.aplicaciones.resparking.modelo;

/**
 * Clase que maneja la informacion enviada y recibida por el WebService del modelo Usuario
 * Con todos los campos usados
 */
public class Usuario {
    /**
     * Variable Tipo String implementada para enviar y recibir el correo del usuario
     * Valor que se obtiene de la base de datos del Host
     */
    public String correo;
    /**
     * Variable Tipo String implementada para enviar y recibir el external_id del usuario
     * Valor que se obtiene de la base de datos del Host
     */
    public String external_id;
    /**
     * Variable Tipo String implementada para enviar y recibir la fecha de creacion del usuario
     * Valor que se obtiene de la base de datos del Host
     */
    public String created_at;
    /**
     * Variable Tipo String implementada para enviar y recibir el estado [activo, inactivo] del vehiculo
     * Valor que se obtiene de la base de datos del Host
     */
    public String estado;

}
