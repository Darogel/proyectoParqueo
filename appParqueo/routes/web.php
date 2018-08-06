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
$router->Post('/parqueadero/modificar','ParqueaderoController@modificarParqueadero');
$router->Post('/parqueadero/eliminar','ParqueaderoController@eliminarParqueadero');

$router->Post('/plaza/registrar','PlazaController@registrarPlaza');
$router->Get('/plaza/listar/{external_id}','PlazaController@listarPlazaParqueadero');

$router->Post('/reservacion/registrar','ReservacionController@registrarReservacion');
$router->Post('/reservacion/modificar','ReservacionController@modificarReservacion');
//$router->Get('/reservacion/listar','ReservacionController@listarReservacion');
$router->Get('/reservacion/listarParq/{id}','ReservacionController@listarReservacionesParqueadero');
$router->Post('/reservacion/eliminar','ReservacionController@eliminarReservacion');

$router->Post('/usuario/registrar','UsuarioController@registrarUsuario');
//$router->Post('/usuario/login','UsuarioController@inicioSesionUsuario');
$router->Post('/usuario/modificar','UsuarioController@modificarUsuario');

$router->Post('/vehiculo/registrar','VehiculoController@registrarVehiculo');
$router->Post('/vehiculo/modificar','VehiculoController@modificarVehiculo');
$router->Post('/vehiculo/eliminar','VehiculoController@eliminarVehiculo');
$router->Get('/vehiculo/listar/{external_id}','VehiculoController@listarVehiculoUsuario');
