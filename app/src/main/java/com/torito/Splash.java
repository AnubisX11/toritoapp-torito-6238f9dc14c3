package com.torito;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * Esta es la clase que maneja el Splash screen de la aplicación
 * Created by Wall on 05/12/2015.
 */
public class Splash extends AppCompatActivity{

    //Duración del splash
    private final int SPLASH_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle guardado) {
        super.onCreate(guardado);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this,MainActivity.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_LENGTH);

    }
}
