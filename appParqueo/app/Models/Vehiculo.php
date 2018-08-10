<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace App\Models;
/**
 * Description of Vehiculo
 *
 * @author Darwin
 */
use Illuminate\Database\Eloquent\Model;
class Vehiculo extends Model{
    //put your code here
    protected $table ='vehiculo';
    public $primaryKey='id_vehiculo';
    public $timestamps=false; 
    protected $fillable=['placa','estado'];
    protected $guarded =['id_vehiculo','id_usuario'];
    
     public function usuario(){
        return $this->belongsTo('App\Models\Usuario','id_usuario'); 
    }
    
    public function reservacion(){
        return $this->hasOne('App\Models\Reservacion','id_vehiculo'); 
    }
}
