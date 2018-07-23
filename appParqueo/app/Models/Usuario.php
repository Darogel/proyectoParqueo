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
    protected $guarded =['id_usuario','usuario','clave'];
    
    public function vehiculo() {
        return $this ->hasMany('App\Models\Vehiculo','id_vehiculo');
    }
}
