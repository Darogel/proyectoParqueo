package com.aplicaciones.resparking.controlador.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Clase extendida de FirebaseInstanceIdService que maneja la informacion del token de Firebase para notificaciones
 * Mediante una variable TAG
 */
public class MiFirebaseInstanceIdService extends FirebaseInstanceIdService {

    /**
     * Variable estatica del tipo string usada como TAG
     */
    public static final String TAG = "NOTICIAS";
    /**
     * Metodo sobreescrito para obtener el token de firebase cada vez que este cambia
     */
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d(TAG, "Token: " + token);

    }

}
