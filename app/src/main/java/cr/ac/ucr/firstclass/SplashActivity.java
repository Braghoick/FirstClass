package cr.ac.ucr.firstclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import cr.ac.ucr.firstclass.utils.AppPreferences;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        boolean logged_in = AppPreferences.getInstance(this).getBoolean(AppPreferences.Keys.LOGGED_IN, false);

        Intent intent;
        if (logged_in){
            intent = new Intent(this, MainActivity.class);
        } else{
            intent = new Intent(this, LoginActivity.class);
        }

        startActivity(intent);
//        Se utiliza solo si no se quiere volver a esta activity
        finish();
    }
}