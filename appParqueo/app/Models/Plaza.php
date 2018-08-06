<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

namespace App\Models;

/**
 * Description of Plaza
 *
 * @author Darwin
 */
use Illuminate\Database\Eloquent\Model;
class Plaza extends Model{
    //put your code here
    protected $table ='plaza';
    public $primaryKey='id_plaza';
    public $timestamps=false; 
    protected $fillable=['numero_puesto','estado','tipo'];
    protected $guarded =['id_plaza','id_parqueadero','clave'];
    
    public function parqueadero(){
        return $this->belongsTo('App\Models\Parqueadero','id_parqueadero'); 
    }
    
    public function reservacion(){
        return $this->hasOne('App\Models\Reservacion','id_plaza'); 
    }
}
