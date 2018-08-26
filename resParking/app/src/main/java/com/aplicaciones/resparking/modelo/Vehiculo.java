package com.aplicaciones.resparking.modelo;

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
