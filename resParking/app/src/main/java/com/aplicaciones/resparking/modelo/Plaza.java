package com.aplicaciones.resparking.modelo;

/**
 * Clase que maneja la informacion enviada y recibida por el WebService del modelo Plaza
 * Con todos los campos usados
 */
public class Plaza {
    /**
     * Variable Tipo String implementada para enviar y recibir el nombre de puesto  de la  plaza
     * Valor que se obtiene de la base de datos del Host
     */
    public String numero_puesto;
    /**
     * Variable Tipo String implementada para enviar y recibir el external_id  de la plaza
     * Valor que se obtiene de la base de datos del Host
     */
    public String external_id;
    /**
     * Variable Tipo String implementada para enviar y recibir el tipo [con techo, sin techo] de la plaza
     * Valor que se obtiene de la base de datos del Host
     */
    public String tipo;
    /**
     * Variable Tipo String implementada para enviar y recibir el estado [activo, inactivo] de la plaza
     * Valor que se obtiene de la base de datos del Host
     */
    public String estado;
    /**
     * Variable Tipo String implementada para enviar y recibir la fecha de creacion de la plaza
     * Valor que se obtiene de la base de datos del Host
     */
    public String created_at;

    /**
     * Metodo implementado para mostrar informacion completa de un Objeto
     * @return valor del atributo numero_puesto del Objeto
     * @return valor del atributo tipo  del Objeto
     */
    @Override
    public String toString(){
        return numero_puesto +" - "+ tipo;
    }



}
