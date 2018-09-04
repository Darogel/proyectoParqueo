<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use \App\Models\Administrador;
use \App\Models\Parqueadero;

/**
 * Description of ParqueaderoController
 * Clase usada para el control de las funciones del parqueadero
 */
class ParqueaderoController extends Controller {

    /**
     * Función para registrar un nuevo parqueadero
     * @param Request $request
     * @return mensaje type json
     */
    public function registrarParqueadero(Request $request) {
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $admin = Administrador::where("external_id", $data["clave"])->first();
                if ($admin) {
                    $administrador = Administrador::find($admin->id_admin);
                    $parqueadero = new Parqueadero();
                    $parqueadero->nombre = $data["nombre"];
                    $parqueadero->coordenada_x = $data["coordenada_x"];
                    $parqueadero->coordenada_y = $data["coordenada_y"];
                    $parqueadero->precio_hora = $data["precio"];
                    $parqueadero->numero_plazas = $data["plazas"];
                    $parqueadero->external_id = utilidades\UUID::v4();
                    $parqueadero->administrador()->associate($administrador);
                    $parqueadero->save();
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
     * Función para modificar un parquedero
     * @param Request $request
     * @return mensaje type json
     */
    public function modificarParqueadero(Request $request) {

        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $parqueaderoObjeto = Parqueadero::where("external_id", $data["external_id"])->first();
                if (isset($data["nombre"])) {
                    $parqueaderoObjeto->nombre = $data["nombre"];
                }
                if (isset($data["coordenada_x"])) {
                    $parqueaderoObjeto->coordenada_x = $data["coordenada_x"];
                }
                if (isset($data["coordenada_y"])) {
                    $parqueaderoObjeto->coordenada_y = $data["coordenada_y"];
                }
                if (isset($data["precio"])) {
                    $parqueaderoObjeto->precio_hora = $data["precio"];
                }
                if (isset($data["plazas"])) {
                    $parqueaderoObjeto->numero_plazas = $data["plazas"];
                }
                $parqueaderoObjeto->save();
                return response()->json(["mensaje" => "Operacion exitosa", "siglas" => "OE"], 200);
            } catch (Exceptio $ex) {
                return response()->json(["mensaje" => "Faltan Datos", "siglas" => "FD"], 400);
            }
        } else {
            return response()->json(["mensaje" => "La data no tiene el formato deseado", "siglas" => "DNF"], 404);
        }
    }

    /**
     * Función para eliminar un parqueadero
     * @param Request $request
     * @return mensaje type json
     */
    public function eliminarParqueadero(Request $request) {
        if ($request->isJson()) {
            $data = $request->json()->all();
            try {
                $parqueaderoObjeto = Parqueadero::where("external_id", $data["external_id"])->first();

                $parqueaderoObjeto->estado = 0;
                $parqueaderoObjeto->save();
                return response()->json(["mensaje" => "Operacion exitosa", "siglas" => "OE"], 200);
            } catch (Exceptio $ex) {
                return response()->json(["mensaje" => "Faltan Datos", "siglas" => "FD"], 400);
            }
        } else {
            return response()->json(["mensaje" => "La data no tiene el formato deseado", "siglas" => "DNF"], 404);
        }
    }
    
    /**
     * Función para listar los parqueadero por administrador
     * @param type $external_id
     * @return array con una lista de parqueaderos
     */
    public function listarParqueadero($external_id) {
        $this->external_id = $external_id;
        $lista =Parqueadero::whereHas('administrador', function ($q) {
                    $q->where('external_id', $this->external_id);
                })->orderBy('created_at', 'desc')->get();

        $data = array();
        foreach ($lista as $item) {
            $data[] = ["nombre" => $item->nombre,
            "coordenada_x"=> $item->coordenada_x,
            "coordenada_y"=> $item->coordenada_y,
            "numero_plazas"=> $item->numero_plazas,
            "precio_hora"=>$item->precio_hora];
        }

        return response()->json($data, 200);
    }
    
    /**
     * Función para listar parqueaderos Buscado
     * @param  $coordenada_x type String 
     * @return type array con los datos del parqueadero buscado
     */
    public function listarParqueaderoB(String $coordenada_x) {
        $resultado = new Parqueadero();
        $this->coordenada_x = $coordenada_x;
        $lista = Parqueadero::where('coordenada_x', $this->coordenada_x)->OrderBy('created_at','asc')->get();

        $data = array();
        foreach ($lista as $item) {
            $data[] = [$resultado->nombre = $item->nombre,
                $resultado->coordenada_x = $item->coordenada_x,
                $resultado->coordenada_y=$item->coordenada_y,
                $resultado->numero_plazas=$item->numero_plazas,
                $resultado->precio_hora=$item->precio_hora,
                $resultado->external_id=$item->external_id];
        }
        //return response()->json($data, 200);
        return response()->json($resultado, 200);
    }
    /**
     * Funcion para listar todos los parqueaderos existentes en la base de datos
     * @return type array de parqueaderos con su respectiva informacion
     */
    public function listarP(){
        $lista= Parqueadero::where('estado',true)->orderBy('created_at','desc')->get();
        $data=array();
        foreach ($lista as $item){
            $data[]=["nombre"=>$item->nombre,
            "coordenada_x"=>$item->coordenada_x,
            "coordenada_y"=>$item->coordenada_y,
            "external_id"=>$item->external_id];
        }
        return response()->json($data,200);
    }

}
