<?php

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
namespace Models;
/**
 * Description of Administrador
 *
 * @author Darwin
 */
use Illuminate\Database\Eloquent\Model;
class Administrador extends Model{
    //put your code here
    protected $table ='administrador';
    public $primaryKey='id_admin';
//    public $timestamps=false; 
    protected $fillable=['nombres','estado'];
    protected $guarded =['id_admin','usuario','clave',];
    
    
    public function parqueo() {
        return $this ->hasMany('App\Models\Parqueo','id_admin');
    }
}
