package cr.ac.ucr.firstclass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cr.ac.ucr.firstclass.utils.AppPreferences;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etName;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.btn_register:
                register();
                break;
            case R.id.btn_go_to_log_in:
                goToLogIn();
                break;
            default:
                break;
        }

    }

    private void register() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(name.isEmpty()){
            etName.setError(getString(R.string.error_name));
            return;
        }

        if(email.isEmpty()){
            etEmail.setError(getString(R.string.error_email));
            return;
        }

        if(password.isEmpty()){
            etPassword.setError(getString(R.string.error_password));
            return;
        }

        //TODO: Debe añadirse el ingreso de los datos a una BDc

        AppPreferences.getInstance(this).put(AppPreferences.Keys.LOGGED_IN, true);

        Toast.makeText(this, R.string.registered, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToLogIn() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}