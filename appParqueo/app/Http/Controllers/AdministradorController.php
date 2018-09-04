<?php

namespace App\Http\Controllers;
use Illuminate\Http\Request;
use \App\Models\Administrador;

/**
 * Description of AdministradorController
 * Clase usada para el control de las funciones del administrador
 */
class AdministradorController extends Controller {

    /**
     * Funcion para el inicio de sesion del administrador
     * @param Request $request 
     * @return response type json
     */
    public function inicioSesionAdministrador(Request $request) {
        if ($request->isJson()) {
            try {
                $data = $request->json()->all();
                $administrador = Administrador::where("usuario", $data["usuario"])->where("clave", $data["clave"])->where("estado", true)->first();
                if ($administrador) {
                    return response()->json(["usuario" => $administrador->usuario,
                                "id" => $administrador->external_id,
                                "nombres" => $administrador->nombres,
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
