<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Usuario;

/**
 * Description of UsuarioController
 *
 * @author Darwin
 */
class UsuarioController extends Controller {

    //put your code here
    public function inicioSesionUsuario(Request $request) {
        if ($request->isJson()) {
            try {
                $data = $request->json()->all();
                $user = Usuario::where("usuario", $data["usuario"])->where("clave", $data["clave"])->first();
                if ($user) {
                    return response()->json(["usuario" => $user->usuario,
                                "id" => $user->external_id,
                                "nombre" => $user->nombre,
                                "token" => base64_encode($user->external_id . '--' . $user->usuario),
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

    public function registrarUsuario(Request $request) {
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $usuario = new Usuario();
                $usuario->nombre = $data["nombre"];
                $usuario->usuario = $data["usuario"];
                $usuario->clave = $data["clave"];
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

    public function modificarUsuario (Request $request) {

        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $usuarioObjeto = Usuario::where("external_id", $data["external_id"])->first();
                if (isset($data["nombre"])) {
                    $usuarioObjeto->nombre = $data["nombre"];
                }
                if (isset($data["usuario"])) {
                    $usuarioObjeto->precio = $data["precio"];
                }
                if (isset($data["clave"])) {
                    $usuarioObjeto->clave = $data["clave"];
                }
                $usuarioObjeto->save();

                return response()->json(["mensaje" => "Operacion exitosa", "siglas" => "OE"], 200);
            } catch (Exceptio $ex) {
                return response()->json(["mensaje" => "Faltan Datos", "siglas" => "FD"], 400);
            }
        } else {
            return response()->json(["mensaje" => "La data no tiene el formato deseado", "siglas" => "DNF"], 404);
        }
    }

}
