<?php

namespace App\Models;
/*
 * Description of Reservación
 * Clase utilizada para poder conectar la tabla existente
 * en la base de datos con el Servicio Web
 */
use Illuminate\Database\Eloquent\Model;
class Reservacion extends Model{    
    //tabla que hace referencia a la base de datos 
    protected $table ='reservacion';
    //Definicion de la llave primaria dentro de la tabla
    public $primaryKey='id_reservacion';
    //para saber si en la tabla usamos created_at y updated_at
    public $timestamps=true;  
    //lista blancas campos publicos
    protected $fillable=['hora_entrada','hora_salida','estado'];
    //lista negra campos que no puedan ser encontrados facilmente
    protected $guarded =['id_vehiculo','id_plaza','clave'];
    //Relacion PERTENECE A
     public function plaza() {
        return $this ->belongsTo('App\Models\Plaza','id_plaza');
    }
    //Relacion PERTENECE A
    public function vehiculo() {
        return $this ->belongsTo('App\Models\Vehiculo','id_vehiculo');
    }
    
}

