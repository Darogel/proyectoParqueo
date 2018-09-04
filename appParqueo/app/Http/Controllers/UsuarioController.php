<?php

namespace App\Http\Controllers;
use Illuminate\Http\Request;
use App\Models\Usuario;

/**
 * Description of UsuarioController
 * Clase usada para el control de las funciones del usuario
 */
class UsuarioController extends Controller {

    /**
     * Función para registrar un nuevo usuario
     * @param Request $request
     * @return type mensaje json
     */
     public function registrarUsuario(Request $request) {
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $usuario = new Usuario();
                $usuario->correo = $data["correo"];
                $usuario->external_id = utilidades\UUID::v4();
                $usuario->save();
                return response()->json(["mensaje" => "Operacion exitosa", "siglas" => "OE"], 200);
            } catch (Exception $ex) {
                return response()->json(["mensaje" => "Faltan Datos", "siglas" => "FD"], 400);
            }
        } else {
            return response()->json(["mensaje" => "La data no tiene el formato deseado", "siglas" => "DNF"], 404);
        }
    }
    
    /**
     * Función para el inicio de sesión del usuario
     * @param Request $request
     * @return type mensaje json
     */
    public function inicioSesionUsuario(Request $request) {
        if ($request->isJson()) {
            try {
                $data = $request->json()->all();
                $user = Usuario::where("correo", $data["correo"])->first();
                if ($user) {
                    return response()->json(["usuario" => $user->usuario,
                                "id" => $user->external_id,
                                "nombre" => $user->nombres,
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

  /**
   * Función para login de Facebook y Firebase
   * @param Request $request
   * @return type mensaje json
   */
  public function loginRegistrarUsuario(Request $request) {
        if ($request->isJson()) {
            try {
                $data = $request->json()->all();
                $user = Usuario::where("correo", $data["correo"])->first();
                if ($user) {
                    return response()->json([
                        "external_id" => $user->external_id,
                        "mensaje" => 
                        "Operacion exitosa", 
                        "siglas" => "OE"], 200);
                } else {
                    try {
                $usuario = new Usuario();
                $usuario->correo = $data["correo"];
                $usuario->external_id = utilidades\UUID::v4();
                $usuario->save();
                return response()->json(["mensaje" => "Operacion exitosa", "siglas" => "OE"], 200);
            } catch (\Exception $ex) {
                return response()->json(["mensaje" => "Faltan Datos", "siglas" => "FD"], 400);
            }
                }
            } catch (Exception $exc) {
                return response()->json(["mensaje" => "Faltan Datos2", "siglas" => "FD"], 400);
            }
        } else {
            return response()->json(["mensaje" => "La data no tiene el formato deseado", "siglas" => "DNF"], 400);
        }
    }


}
