<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace App\Models;

/**
 * Description of Reservacion
 *
 * @author Darwin
 */
use Illuminate\Database\Eloquent\Model;
class Reservacion extends Model{
    //put your code here
    protected $table ='reservacion';
    public $primaryKey='id_reservacion';
    public $timestamps=false;  
    protected $fillable=['hora_entrada','hora_salida','estado'];
    protected $guarded =['id_vehiculo','id_plaza','clave'];

     public function plaza() {
        return $this ->belongsTo('App\Models\Plaza','id_plaza');
    }
    
    public function vehiculo() {
        return $this ->belongsTo('App\Models\Vehiculo','id_vehiculo');
    }
    
}

