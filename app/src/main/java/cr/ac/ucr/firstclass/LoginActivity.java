package cr.ac.ucr.firstclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cr.ac.ucr.firstclass.utils.AppPreferences;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText etEmail;
    private EditText etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_go_to_register:
                goToRegister();
                break;
            default:
                break;
        }
    }


    private void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(email.isEmpty()){
            etEmail.setError(getString(R.string.error_email));
            return;
        }

        if (password.isEmpty()){
            etPassword.setError(getString(R.string.error_password));
            return;
        }

        // TODO: Debe sustituirse con la logica de auth de la app
        if(email.equalsIgnoreCase("admin@email.com") && "123123".equalsIgnoreCase(password)){

            //TODO: almacenar en el storage usuario logged
            AppPreferences.getInstance(this).put(AppPreferences.Keys.LOGGED_IN, true);

            Toast.makeText(this, R.string.logged, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, R.string.no_match, Toast.LENGTH_SHORT).show();
        }
    }

    private void goToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();

    }


}