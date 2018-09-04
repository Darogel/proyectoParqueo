package com.aplicaciones.resparking;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
/**
 * Clase implementada para autenticar usuario mediante Facebook y Firebase
 * Extendible de la super clase AppCompatActivity
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Variable tipo LoginButton de facebook para manejar acciones de autenticacion
     */
    private LoginButton loginButton;
    /**
     * Variable CallbackManager encargado de manejar el resultado del inicio de sesion de facebook
     */
    private CallbackManager callbackManager;
    /**
     * Variable del tipo firebaseAuth de Firebase usada para logear mediante Firebase
     */
    private FirebaseAuth firebaseAuth;
    /**
     * Variable del tipo firebaseAuth.AuthStateListener de Firebase usada para escuchar el inicio de sesion con firebase
     */
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    /**
     * Variable tipo progressBar para presentarse en el layout de inicio de sesion
     */
    private ProgressBar progressBar;

    /**
     * Metodo implementado para inicio de la actividad
     * Se llama un recurso de diseño que define su UI
     * Recupera  widgets del layout activity_login
     * El boton loginButton lee el permiso de correo electronico, especifica las acciones a seguirse en caso satisfactorio o de error
     * FirebaseAuthListener comprueba si un usuario se encuentra logeado para ejecutar el metodo reservacionAdd
     * @param savedInstanceState Guarda el estado de la aplicacion
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progressbar_login);
        callbackManager = CallbackManager.Factory.create();


        loginButton = (LoginButton) findViewById(R.id.loginButton);
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    reservacionAdd();

                }
            }
        };

    }

    /**
     * Metodo implementado para llamar la actividad de reservacion
     */
    private void reservacionAdd() {
        Intent intent = new Intent(this, ReservacionAdd.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    /**
     * Metodo que utiliza el token obtenido de un logeo exitoso de facebook
     * Crea una credencial con dicho token que es enviado a Firebase
     * @param accessToken Guarda el estado de la aplicacion
     */
    private void handleFacebookAccessToken(AccessToken accessToken) {
        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), R.string.error_login_firebase, Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
                loginButton.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Metodo que añade un AuthStateListener al empezar para que empiece a escuchar
     */
    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }
    /**
     * Metodo que remueve un AuthStateListener al terminar para que deje de escuchar
     */
    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
