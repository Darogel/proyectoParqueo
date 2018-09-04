<?php

namespace App\Models;

/*
 * Description of Usuario
 * Clase utilizada para poder conectar la tabla existente
 * en la base de datos con el Servicio Web
 */
use Illuminate\Database\Eloquent\Model;
class Usuario  extends Model{
    
    //tabla que hace referencia a la base de datos 
    protected $table ='usuario';
    //Definicion de la llave primaria dentro de la tabla
    public $primaryKey='id_usuario';
    //para saber si en la tabla usamos created_at y updated_at
    public $timestamps=false; 
    //lista blancas campos publicos
    protected $fillable=['correo'];
    //lista negra campos que no puedan ser encontrados facilmente
    protected $guarded =['id_usuario'];
    //Relacion UNO a MUCHOS
    public function vehiculo() {
        return $this ->hasMany('App\Models\Vehiculo','id_usuario');
    }
    //Relacion multidireccion con los diferentes modelos
    public function reservacion(){
         return $this->hasManyThrough('App\Models\Reservacion', 'App\Models\Vehiculo','id_usuario','id_vehiculo');
    }
}
