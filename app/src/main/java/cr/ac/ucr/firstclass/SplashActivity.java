package cr.ac.ucr.firstclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppTheme_NoActionBar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFERENCES, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean logged_in = sharedPreferences.getBoolean("logged_in", false);
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