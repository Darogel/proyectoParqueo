<?php

namespace App\Models;

/*
 * Description of Plaza
 * Clase utilizada para poder conectar la tabla existente
 * en la base de datos con el Servicio Web
 */
use Illuminate\Database\Eloquent\Model;
class Plaza extends Model{
    //tabla que hace referencia a la base de datos 
    protected $table ='plaza';
    //Definicion de la llave primaria dentro de la tabla
    public $primaryKey='id_plaza';
     //para saber si en la tabla usamos created_at y updated_at
    public $timestamps=true; 
    //lista blancas campos publicos
    protected $fillable=['numero_puesto','estado','tipo'];
    //lista negra campos que no puedan ser encontrados facilmente
    protected $guarded =['id_plaza','id_parqueadero','clave'];
    
    //Relacion PERTENECE A
    public function parqueadero(){
        return $this->belongsTo('App\Models\Parqueadero','id_parqueadero'); 
    }
    
    //Relacion UNO a UNO
    public function reservacion(){
        return $this->hasOne('App\Models\Reservacion','id_plaza'); 
    }
}
//tabla que hace referencia a la base de datos 
//Definicion de la llave primaria dentro de la tabla
//para saber si en la tabla usamos created_at y updated_at
//lista blancas campos publicos
//lista negra campos que no puedan ser encontrados facilmente
//Relacion PERTENECE A
//Relacion UNO a UNO