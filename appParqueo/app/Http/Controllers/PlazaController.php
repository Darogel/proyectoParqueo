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
use Illuminate\Http\Request;
use App\Models\Administrador;
use App\Models\Parqueadero;
use App\Models\Plaza;
class PlazaController extends Controller {

    //put your code here
    public function registrarPlaza(Request $request) {
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $admin = Administrador::where("external_id", $data["clave"])->first();
                if ($admin) {
                    $parqueadero = Parqueadero::find($admin->id_admin);
                    $plaza = new Plaza();
                    $plaza->tipo = $data["tipo"];
                    $plaza->numero_puesto = $data["numero"];
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
    
    public function listarPlazaParqueadero($external_id) {
        $this->external_id = $external_id;
        $lista = \App\Models\Plaza::whereHas('parqueadero', function ($q) {
                    $q->where('external_id', $this->external_id);
                    $q->where('estado',true);
                })->orderBy('created_at', 'desc')->get();

        $data = array();
        foreach ($lista as $item) {
            $data[] = ["numero_puesto" => $item->numero_puesto,
                "tipo" => $item->tipo];
        }

        return response()->json($data, 200);
    }

}
