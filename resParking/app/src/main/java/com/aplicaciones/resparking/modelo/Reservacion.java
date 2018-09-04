package com.aplicaciones.resparking.modelo;

/**
 * Clase que maneja la informacion enviada y recibida por el WebService del modelo Reservacion
 * Con todos los campos usados
 */
public class Reservacion {
    /**
     * Variable Tipo String implementada para enviar y recibir la placa del vehiculo en esa reservacion
     * Valor que se obtiene de la base de datos del Host
     */
    public String vehiculo;
    /**
     * Variable Tipo String implementada para enviar y recibir el external_id de la reservacion
     * Valor que se obtiene de la base de datos del Host
     */
    public String external_id;
    /**
     * Variable Tipo String implementada para enviar y recibir la Hora de ingreso de un vehiculo a reservacion
     * Valor que se obtiene de la base de datos del Host
     */
    public String hora_entrada;
    /**
     * Variable Tipo String implementada para enviar y recibir la Hora de salida de un vehiculo a reservacion
     * Valor que se obtiene de la base de datos del Host
     */
    public String hora_salida;
    /**
     * Variable Tipo String implementada para enviar y recibir el estado [activo, inactivo] del vehiculo de la reservacion
     * Valor que se obtiene de la base de datos del Host
     */
    public String estado;
    /**
     * Variable Tipo String implementada para enviar y recibir la fecha de creacion de la reservacion
     * Valor que se obtiene de la base de datos del Host
     */
    public String created_at;
}
