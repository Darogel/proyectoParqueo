<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Models\Administrador;
use App\Models\Parqueadero;
use App\Models\Plaza;

/**
 * Description of PlazaController
 * Clase usada para el control de las funciones de plaza
 */
class PlazaController extends Controller {
    /**
     * Función para registrar una plaza
     * @param Request $request
     * @return type mensaje json
     */
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
    
    /**
     * Función para listar las plazas por parqueadero
     * @param type $external_id
     * @return type array con las plazas que posee el parqueadero
     */
    public function listarPlazaParqueadero($external_id) {
        $this->external_id = $external_id;
        $lista = Plaza::whereHas('parqueadero', function ($q) {
                    $q->where('external_id', $this->external_id);
                   
                })->where('estado', '1')->OrderBy('numero_puesto','asc')->get();

        $data = array();
        foreach ($lista as $item) {
            $data[] = ["numero_puesto" => $item->numero_puesto,
                "tipo" => $item->tipo,
                "external_id" => $item->external_id];
        }

        return response()->json($data, 200);
    }

}
