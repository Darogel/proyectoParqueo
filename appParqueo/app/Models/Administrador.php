<?php
namespace App\Models;
/*
 * Description of Administrador
 * Clase utilizada para poder conectar la tabla existente
 * en la base de datos con el Servicio Web
 */
use Illuminate\Database\Eloquent\Model;
class Administrador extends Model{
    //tabla que hace referencia a la base de datos 
    protected $table ='administrador';
    //Definicion de la llave primaria dentro de la tabla
    public $primaryKey='id_admin';
    //para saber si en la tabla usamos created_at y updated_at
    public $timestamps=true; 
    //lista blancas campos publicos 
    protected $fillable=['nombres','estado'];
    //lista negra campos que no puedan ser encontrados facilmente
    protected $guarded =['id_admin','usuario','clave',];
    
    //Relacion UNO a MUCHOS de un administrador hacia muchos parqueaderos
    public function parqueadero() {
        return $this ->hasMany('App\Models\Parqueadero','id_admin');
    }
}
