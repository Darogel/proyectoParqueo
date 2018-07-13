<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It is a breeze. Simply tell Lumen the URIs it should respond to
| and give it the Closure to call when that URI is requested.
|
*/

$router->get('/', function () use ($router) {
    return $router->app->version();
});

$router->Post('/admin/login','AdministradorController@inicioSesionAdministrador');
$router->Post('/parqueadero/registrar','ParqueaderoController@registrarParqueadero');
$router->Post('/parqueadero/modificar','ParqueaderoController@ModificarParqueadero');
$router->Post('/plaza/registrar','PlazaController@registrarPlaza');
$router->Post('/reservacion/registrar','ReservacionController@registrarReservacion');
$router->Post('/usuario/login','UsuarioController@inicioSesionUsuario');
$router->Post('/usuario/registrar','UsuarioController@registrarUsuario');
$router->Post('/usuario/modificar','UsuarioController@modificarUsuario');
$router->Post('/vehiculo/registrar','VehiculoController@registrarVehiculo');
$router->Post('/vehiculo/modificar','VehiculoController@modificarVehiculo');

