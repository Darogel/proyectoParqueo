package com.aplicaciones.resparking.modelo;

/**
 * Clase que maneja la informacion enviada y recibida por el WebService del modelo Administrador
 * Con todos los campos usados
 */
public class Administrador {
    /**
     * Variable Tipo String implementada para enviar y recibir el nombre del administrador
     * Valor que se obtiene de la base de datos del Host
     */
    public String nombres;
    /**
     * Variable Tipo String implementada para enviar y recibir el usuario del administrador
     * Valor que se obtiene de la base de datos del Host
     */
    public String usuario;
    /**
     * Variable Tipo String implementada para enviar y recibir el clave del administrador
     * Valor que se obtiene de la base de datos del Host
     */
    public String clave;
    /**
     * Variable Tipo String implementada para enviar y recibir el external_id del administrador
     * Valor que se obtiene de la base de datos del Host
     */
    public String id;
    /**
     * Variable Tipo String implementada para recibir nombre el estado [activo, inactivo] del administrador
     * Valor que se obtiene de la base de datos del Host
     */
    public String estado;
    /**
     * Variable Tipo String implementada para enviar y recibir la fecha de creacion del administrador
     * Valor que se obtiene de la base de datos del Host
     */
    public String created_at;
    /**
     * Variable Tipo String implementada para enviar y recibir el Token del administrador
     * Valor que se obtiene de la base de datos del Host
     */
    public String token;

}
