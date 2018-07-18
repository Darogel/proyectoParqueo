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
use Illuminate\Http\Request;
use App\Models\Reservacion;
use App\Models\Usuario;

class ReservacionController extends Controller {

    //put your code here
    public function registrarReservacion(Request $request) {
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $usuario = Usuario::where("external_id", $data["clave"])->first();
                if ($usuario) {
                    $usuario = Usuario::find($usuario->id_usuario);
                    $reservacion = new Reservacion();
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

    public function modificarReservacion(Request $request) {

        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $reservacionObjeto = \Models\Parqueadero::where("external_id", $data["external_id"])->first();
                if (isset($data["plaza"])) {
                    $reservacionObjeto->id_plaza = $data["plaza"];
                }
                if (isset($data["hora_entrada"])) {
                    $reservacionObjeto->hora_entrada = $data["hora_entrada"];
                }
                if (isset($data["hora_salida"])) {
                    $reservacionObjeto->hora_salida = $data["hora_salida"];
                }
                $reservacionObjeto->save();
                return response()->json(["mensaje" => "Operacion exitosa", "siglas" => "OE"], 200);
            } catch (Exceptio $ex) {
                return response()->json(["mensaje" => "Faltan Datos", "siglas" => "FD"], 400);
            }
        } else {
            return response()->json(["mensaje" => "La data no tiene el formato deseado", "siglas" => "DNF"], 404);
        }
    }

    public function listarReservaciones() {
        $lista = \App\Models\Reservacion::where('estado', true)->orderBy('created_at', 'desc')->get();
        $data = array();
        foreach ($lista as $item) {
            $data[] = ["vehiculo" => $item->external_id,
                "hora_entrada" => $item->hora_entrada,
                "hora_salida" => $item->hora_salida,
                "fecha" => $item->created_at->format("Y-m-d")];
        }
        return response()->json($data, 200);
    }

    public function listarReservacionesaAdministrador($external_id) {
        $this->external_id = $external_id;
        $lista = \App\Models\Reservacion::whereHas('administrador', function ($q) {
                    $q->where('external_id', $this->external_id);
                })->orderBy('created_at', 'desc')->get();
        $data = array();
        foreach ($lista as $item) {
            $data[] = ["vehiculo" => $item->external_id,
                "hora_entrada" => $item->hora_entrada,
                "hora_salida" => $item->hora_salida,
                "fecha" => $item->created_at->format("Y-m-d")];
        }
        return response()->json($data, 200);
    }
    
    public function eliminarReservacion(Request $request,$external_id){
        $reservacionObjeto= \App\Models\Reservacion::where("external_id",$external_id)-> first();
        if ($reservacionObjeto){
            if($request->isJson()){
                $data=$request->json()->all();
                $reservacion= \App\Models\Reservacion::find($reservacionObjeto->id_reservacion);
               if (isset($data["estado"]))
                    $reservacion->estado = $data["estado"];
                $reservacion->save();
                return response()-> json(["mensaje"=> "Operacion exitosa","siglas"=> "OE"],200);
                
            }else{
                return response()-> json(["mensaje"=> "La data no tiene el formato deseado","siglas"=> "DNF"],400);
            }
        }else{
            return response()-> json(["mensaje"=> "No se encontro ningun dato","siglas"=> "NDE"],204);
        }
    }

}
