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
use App\Models\Vehiculo;
use App\Models\Plaza;


class ReservacionController extends Controller {
    private $external_id;

    //put your code here
    public function registrarReservacion(Request $request) {
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
               $vehiculo=Vehiculo::where('external_id', $data["idV"])->first();
               $plaza=Plaza::where('external_id',$data["idP"])->first();
               $vehiculo= Vehiculo::find($vehiculo->id_vehiculo);
               $plaza= Plaza::find($plaza->id_plaza);
                    $reservacion = new Reservacion();
                    $reservacion->hora_entrada = $data["entrada"];
                    $reservacion->hora_salida = $data["salida"];
                    $reservacion->external_id = utilidades\UUID::v4();
                    $reservacion->vehiculo()->associate($vehiculo);
                    $reservacion->plaza()->associate($plaza);
                    $reservacion->save();
                    return response()->json(["mensaje" => "Operacion exitosa", "siglas" => "OE"], 200);
                
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
                $reservacionObjeto = Reservacion::where("external_id", $data["external_id"])->first();
                if (isset($data["plaza"])) {
                    $reservacionObjeto->id_plaza = $data["plaza"];
                }
                if (isset($data["entrada"])) {
                    $reservacionObjeto->hora_entrada = $data["entrada"];
                }
                if (isset($data["salida"])) {
                    $reservacionObjeto->hora_salida = $data["salida"];
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

  /*  public function listarReservaciones() {
        $lista = Reservacion::where('estado', true)->orderBy('created_at', 'desc')->get();
        $data = array();
        foreach ($lista as $item) {
            $data[] = ["vehiculo" => $item->external_id,
                "hora_entrada" => $item->hora_entrada,
                "hora_salida" => $item->hora_salida,
                "fecha" => $item->created_at->format("Y-m-d")];
        }
        return response()->json($data, 200);
    }*/

   /* public function listarReservacionesParqueadero($external_id) {
        $this->external_id = $external_id;
        $lista = Reservacion::whereHas('parqueadero', function ($q) {
                    $q->where('external_id', $this->external_id);
                })->orderBy('created_at', 'desc')->get();
        $data = array();
        foreach ($lista as $item) {
            $data[] = ["vehiculo" => $item->external_id,
                "hora_entrada" => $item->hora_entrada,
                "hora_salida" => $item->hora_salida];
        }
        return response()->json($data, 200);
    }*/
    
    public function eliminarReservacion(Request $request){
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $ReservacionObjeto = Reservacion::where("external_id", $data["external_id"])->first();

                $ReservacionObjeto->estado = 0;
                $ReservacionObjeto->save();
                return response()->json(["mensaje" => "Operacion exitosa", "siglas" => "OE"], 200);
            } catch (Exceptio $ex) {
                return response()->json(["mensaje" => "Faltan Datos", "siglas" => "FD"], 400);
            }
        } else {
            return response()->json(["mensaje" => "La data no tiene el formato deseado", "siglas" => "DNF"], 404);
        }
    }
    public function listarReservacionesaParqueadero($external_id) {
        $this->external_id = $external_id;
        $lista = \App\Models\Reservacion::whereHas('parqueadero', function ($q) {
                    $q->where('external_id', $this->external_id);
                })->where('estado','1')->orderBy('created_at', 'desc')->get();
        $data = array();
        foreach ($lista as $item) {
            $data[] = ["vehiculo" => $item->external_id,
                "hora_entrada" => $item->hora_entrada,
                "hora_salida" => $item->hora_salida,
                "fecha" => $item->created_at->format("Y-m-d")];
        }
        return response()->json($data, 200);
    }

    public function listarReservacionesaUsuario($external_id) {
        $this->external_id = $external_id;
        $lista = \App\Models\Reservacion::whereHas('usuario', function ($q) {
                    $q->where('external_id', $this->external_id);
                })->where('estado','1')->orderBy('created_at', 'desc')->get();
        $data = array();
        foreach ($lista as $item) {
            $data[] = ["vehiculo" => $item->external_id,
                "hora_entrada" => $item->hora_entrada,
                "hora_salida" => $item->hora_salida,
                "fecha" => $item->created_at->format("Y-m-d")];
        }
        return response()->json($data, 200);
    }

}
