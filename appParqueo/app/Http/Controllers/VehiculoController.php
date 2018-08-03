<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace App\Http\Controllers;

/**
 * Description of VehiculoController
 *
 * @author Darwin
 */
use Illuminate\Http\Request;
use App\Models\Usuario;
use App\Models\Vehiculo;

class VehiculoController extends Controller {

    //put your code here
    public function registrarVehiculo(Request $request) {
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $usuario = Usuario::where("external_id", $data["clave"])->first();
                if ($usuario) {
                    $usuario = Usuario::find($usuario->id_usuario);
                    $vehiculo = new Vehiculo();
                    $vehiculo->placa = $data["placa"];
                    $vehiculo->external_id = utilidades\UUID::v4();
                    $vehiculo->usuario()->associate($usuario);
                    $vehiculo->save();
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

    public function modificarVehiculo(Request $request) {
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $vehiculoObjeto = Vehiculo::where("external_id", $data["external_id"])->first();
                if (isset($data["placa"])) {
                    $vehiculoObjeto->placa = $data["placa"];
                }
                $vehiculoObjeto->save();
                return response()->json(["mensaje" => "Operacion exitosa", "siglas" => "OE"], 200);
            } catch (Exceptio $ex) {
                return response()->json(["mensaje" => "Faltan Datos", "siglas" => "FD"], 400);
            }
        } else {
            return response()->json(["mensaje" => "La data no tiene el formato deseado", "siglas" => "DNF"], 404);
        }
    }
    
    public function eliminarVehiculo(Request $request){
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $vehiculoObjeto = Vehiculo::where("external_id", $data["external_id"])->first();

                $vehiculoObjeto->estado = 0;
                $vehiculoObjeto->save();
                return response()->json(["mensaje" => "Operacion exitosa", "siglas" => "OE"], 200);
            } catch (Exceptio $ex) {
                return response()->json(["mensaje" => "Faltan Datos", "siglas" => "FD"], 400);
            }
        } else {
            return response()->json(["mensaje" => "La data no tiene el formato deseado", "siglas" => "DNF"], 404);
        }
    }
    
    public function listarVehiculoUsuario($external_id) {
        $this->external_id = $external_id;
        $lista = \App\Models\Vehiculo::whereHas('usuario', function ($q) {
                    $q->where('external_id', $this->external_id);
                })->orderBy('created_at', 'desc')->get();

        $data = array();
        foreach ($lista as $item) {
            $data[] = ["placa" => $item->placa,
                "fecha"=>$item->created_at->format("Y-m-d")];
        }

        return response()->json($data, 200);
    }

}
