<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace App\Http\Controllers;

/**
 * Description of ReservacionController
 *
 * @author Darwin
 */
use Illuminate\Database\Eloquent\Model;
class ReservacionController  extends Model{
    //put your code here
    public function registrarReservacion(Request $request) {
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $usuario = \App\Models\Usuario::where("external_id", $data["clave"])->first();
                if ($usuario) {
                    $usuario = \App\Models\Usuario::find($usuario->id_usuario);
                    $reservacion = new \App\Models\Vehiculo();
                    $reservacion->hora_entrada = $data["hora_entrada"];
                    $reservacion->hora_salida = $data["hora_salida"];
                    $reservacion->estado = $data["estado"];
                    $reservacion->external_id = utilidades\UUID::v4();
                    $reservacion->usuario()->associate($usuario);
                    $reservacion->save();
                    return response()->json(["mensaje" => "Operacion exitosa", "siglas" => "OE"], 200);
                } else {
                    return response()->json(["mensaje" => "No se ha encontrado ningun dato", "siglas" => "NDE"], 203);
                }
            } catch (Exception $ex) {
                return response()->json(["mensaje" => "Faltan Datos", "siglas" => "FD"], 400);
            }
        } else {
            return response()->json(["mensaje" => "La data no tiene el formato deseado", "siglas" => "DNF"], 404);
        }
    }
}
