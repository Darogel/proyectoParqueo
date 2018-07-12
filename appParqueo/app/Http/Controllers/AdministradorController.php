<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace App\Http\Controllers;
use Illuminate\Http\Request;
use \Models\Administrador;
/**
 * Description of AdministradorController
 *
 * @author Darwin
 */
class AdministradorController extends Controller {

    //put your code here
    public function inicioSesionAdministrador(Request $request) {
        if ($request->isJson()) {
            try {
                $data = $request->json()->all();
                $administrador = Administrador::where("usuario", $data["usuario"])->where("clave", $data["clave"])->where("estado", true)->first();
                if ($administrador) {
                    return response()->json(["usuario" => $administrador->usuario,
                                "id" => $administrador->external_id,
                                "nombre" => $administrador->nombre,
                                "token" => base64_encode($administrador->external_id . '--' . $administrador->usuario),
                                "mensaje" => "Operacion exitosa", "siglas" => "OE"], 200);
                } else {
                    return response()->json(["mensaje" => "No se encontro ningun dato", "siglas" => "NDE"], 204);
                }
            } catch (\Exception $exc) {
                return response()->json(["mensaje" => "Faltan Datos", "siglas" => "FD"], 400);
            }
        } else {
            return response()->json(["mensaje" => "La data no tiene el formato deseado", "siglas" => "DNF"], 400);
        }
    }
    
    

}
