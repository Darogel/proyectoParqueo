<?php
namespace App\Models;

use Illuminate\Database\Eloquent\Model;
/*
 * Description of Vehículo
 * Clase utilizada para poder conectar la tabla existente
 * en la base de datos con el Servicio Web
 */
class Vehiculo extends Model{
    
    //tabla que hace referencia a la base de datos 
    protected $table ='vehiculo';
    //Definicion de la llave primaria dentro de la tabla
    public $primaryKey='id_vehiculo';
    //para saber si en la tabla usamos created_at y updated_at
    public $timestamps=true; 
    //lista blancas campos publicos
    protected $fillable=['placa','estado'];
    //lista negra campos que no puedan ser encontrados facilmente
    protected $guarded =['id_vehiculo','id_usuario'];
    //Relacion PERTENECE A
    public function usuario(){
        return $this->belongsTo('App\Models\Usuario','id_usuario'); 
    }
    //Relacion PERTENECE A
    public function reservacion(){
        return $this->hasOne('App\Models\Reservacion','id_vehiculo'); 
    }
}
