<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace App\Models;

/**
 * Description of Parqueadero
 *
 * @author Darwin
 */
use Illuminate\Database\Eloquent\Model;
class Parqueadero extends Model{
    //put your code here
    protected $table ='parqueadero';
    public $primaryKey='id_parqueadero';
    public $timestamps=false; // formato de fecha 
    protected $fillable=['nombre','coordenada_x','coordenada_y','precio_hora','numero_plazas','estado'];
    protected $guarded =['id_admin','id_parqueadero'];
    
    public function administrador(){
        return $this->belongsTo('App\Models\Administrador','id_admin'); 
    }
    public function plaza() {
        return $this ->hasMany('App\Models\Plaza','id_parqueadero');
    }
    
    public function reservacion(){
         return $this->hasManyThrough('App\Models\Reservacion', 'App\Models\Plaza','id_parqueadero','id_plaza');
    }
}
