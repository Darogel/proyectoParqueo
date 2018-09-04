<?php

namespace App\Models;

/*
 * Description of Parqueadero
 * Clase utilizada para poder conectar la tabla existente
 * en la base de datos con el Servicio Web
 */
use Illuminate\Database\Eloquent\Model;
class Parqueadero extends Model{
    //tabla que hace referencia a la base de datos 
    protected $table ='parqueadero';
    //Definicion de la llave primaria dentro de la tabla
    public $primaryKey='id_parqueadero';
    //para saber si en la tabla usamos created_at y updated_at
    public $timestamps=true; // formato de fecha 
    //lista blancas campos publicos
    protected $fillable=['nombre','coordenada_x','coordenada_y','precio_hora','numero_plazas','estado'];
    //lista negra campos que no puedan ser encontrados facilmente
    protected $guarded =['id_admin','id_parqueadero'];
    
    //Relacion PERTENECE A
    public function administrador(){
        return $this->belongsTo('App\Models\Administrador','id_admin'); 
    }
    //Relacion UNO a MUCHOS 
    public function plaza() {
        return $this ->hasMany('App\Models\Plaza','id_parqueadero');
    }
    
    //Relacion multidireccion con los diferentes modelos
    public function reservacion(){
         return $this->hasManyThrough('App\Models\Reservacion', 'App\Models\Plaza','id_parqueadero','id_plaza');
    }
}
