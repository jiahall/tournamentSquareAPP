package com.hall.jia.tournamentsquareapp;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hall.jia.tournamentsquareapp.connection.ApiUtils;
import com.hall.jia.tournamentsquareapp.connection.ServiceLayer;
import com.hall.jia.tournamentsquareapp.model.JsonReplies.JsonRep;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail;
    EditText edtPassword;
    Button btnLogin;
    TextView btnRegister;
    ServiceLayer serviceLayer;
    CheckBox chkPasswordVisible;
    SharedPreferences sharedPreferences;
    ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initVariables();
        initButtons();
        }


    private void initVariables() {
        sharedPreferences= getSharedPreferences("login", MODE_PRIVATE);
        final String pass, emailer;
        pass=sharedPreferences.getString("password","");
        emailer=sharedPreferences.getString("email","");

        edtEmail = findViewById(R.id.logEmail);
        edtPassword = findViewById(R.id.logPassword);
        btnLogin= findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        serviceLayer = ApiUtils.getServiceLayer();
        chkPasswordVisible = findViewById(R.id.passwordVisible);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.INVISIBLE);

        Bundle extras = getIntent().getExtras();
        String email;

        if(extras != null){
            email = extras.getString("email");
            edtEmail.setText(email);
        }

        if(!pass.equals("") && !emailer.equals("")){
            checkLogin(emailer, pass);
        }
    }
    private void initButtons() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View view){

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            } });

        chkPasswordVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chkPasswordVisible.isChecked()){
                    edtPassword.setTransformationMethod(null);
                }else{
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();

                if(email.trim().length() >=4 && password.trim().length() >=4){
                    spinner.setVisibility(View.VISIBLE);
                    checkLogin(email, password);

                }else{
                    Toast.makeText(getApplicationContext(), "Need atleast 4 characters in both", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        sharedPreferences= getSharedPreferences("login", MODE_PRIVATE);
        final String pass, emailer;
        pass=sharedPreferences.getString("password","");
        emailer=sharedPreferences.getString("email","");

        if(!emailer.equals("")){
            checkLogin(emailer, pass);
        }
    }

    private void checkLogin(final String email, final String password) {
        Call<JsonRep> call = serviceLayer.login(email, password);

        call.enqueue(new Callback<JsonRep>() {
            @Override
            public void onResponse(Call<JsonRep> call, Response<JsonRep> response) {
                if(response.isSuccessful()){

                    JsonRep jsonRep = response.body();

                    if(jsonRep.getSuccess()){
                        if(sharedPreferences.getString("password","").equals("")) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();

                            String pass = edtPassword.getText().toString();
                            editor.putString("password", pass);
                            editor.apply();

                            String email = edtEmail.getText().toString();
                            editor.putString("email", email);
                            editor.apply();
                        }

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("email", email);
                        spinner.setVisibility(View.GONE);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(), jsonRep.getErr(), Toast.LENGTH_SHORT).show();
                        spinner.setVisibility(View.INVISIBLE);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "please try again", Toast.LENGTH_SHORT).show();
                    spinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error! no connection to server or internet", Toast.LENGTH_SHORT).show();
                spinner.setVisibility(View.INVISIBLE);
            }
        });
    }
}
