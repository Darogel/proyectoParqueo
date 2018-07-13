<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace App\Http\Controllers;

use Illuminate\Http\Request;

/**
 * Description of ParqueoController
 *
 * @author Darwin
 */
class ParqueoController extends Controller {

    //put your code here
    public function registrarParqueo(Request $request) {
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $admin = \App\Models\Administrador::where("external_id", $data["clave"])->first();
                if ($admin) {
                    $administrador = \App\Models\Administrador::find($admin->id_admin);
                    $parqueo = new \Models\Parqueo();
                    $parqueo->nombre = $data["nombre"];
                    $parqueo->coordenadas = $data["coordenadas"];
                    $parqueo->precio = $data["precio"];
                    $parqueo->plazas = $data["plazas"];
                    $parqueo->external_id = utilidades\UUID::v4();
                    $parqueo->administrador()->associate($administrador);
                    $parqueo->save();
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

    public function modificarParqueo(Request $request) {

        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $parqueoObjeto = \Models\Parqueo::where("external_id", $data["external_id"])->first();
                if (isset($data["nombre"])) {
                    $parqueoObjeto->nombre = $data["nombre"];
                }
                if (isset($data["precio"])) {
                    $parqueoObjeto->precio = $data["precio"];
                }
                if (isset($data["plazas"])) {
                    $parqueoObjeto->plazas = $data["plazas"];
                }
                if (isset($data["coordenadas"])) {
                    $parqueoObjeto->coordenadas = $data["coordenadas"];
                }
                $parqueoObjeto->save();

                return response()->json(["mensaje" => "Operacion exitosa", "siglas" => "OE"], 200);
            } catch (Exceptio $ex) {
                return response()->json(["mensaje" => "Faltan Datos", "siglas" => "FD"], 400);
            }
        } else {
            return response()->json(["mensaje" => "La data no tiene el formato deseado", "siglas" => "DNF"], 404);
        }
    }

}
