<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace App\Models;

/**
 * Description of Usuario
 *
 * @author Darwin
 */
use Illuminate\Database\Eloquent\Model;
class Usuario  extends Model{
    //put your code here
    protected $table ='usuario';
    public $primaryKey='id_usuario';
    public $timestamps=false; 
    protected $fillable=['nombres'];
    protected $guarded =['id_usuario'];
    
    public function vehiculo() {
        return $this ->hasMany('App\Models\Vehiculo','id_usuario');
    }
    
    public function reservacion(){
         return $this->hasManyThrough('App\Models\Reservacion', 'App\Models\Vehiculo','id_usuario','id_vehiculo');
    }
}
