<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace App\Http\Controllers;

/**
 * Description of PlazaController
 *
 * @author Darwin
 */
use Illuminate\Database\Eloquent\Model;
class PlazaController extends Model {

    //put your code here
    public function registraPlaza(Request $request) {
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $admin = \App\Models\Administrador::where("external_id", $data["clave"])->first();
                if ($admin) {
                    $usuario = \App\Models\Administrador::find($admin->id_admin);
                    $plaza = new \App\Models\Plaza();
                    $plaza->tipo = $data["tipo"];
                    $plaza->estado = $data["estado"];
                    $plaza->numero_puesto = $data["numero_puesto"];
                    $plaza->external_id = utilidades\UUID::v4();
                    $plaza->parqueadero()->associate($parqueadero);
                    $plaza->save();
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
