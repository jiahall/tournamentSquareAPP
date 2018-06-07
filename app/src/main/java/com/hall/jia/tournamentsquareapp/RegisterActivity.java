package com.hall.jia.tournamentsquareapp;

import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.hall.jia.tournamentsquareapp.connection.ApiUtils;
import com.hall.jia.tournamentsquareapp.connection.ServiceLayer;
import com.hall.jia.tournamentsquareapp.model.JsonReplies.JsonRep;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText edtEmail;
    EditText edtPassword;
    EditText edtUsername;
    Button btnRegister;
    ProgressBar spinner;
    ServiceLayer serviceLayer;
    CheckBox chkPasswordVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initVariables();
        initButtons();
    }

    private void initVariables() {
        edtEmail = findViewById(R.id.regEmail);
        edtPassword = findViewById(R.id.regPassword);
        edtUsername = findViewById(R.id.regUsername);
        btnRegister = findViewById(R.id.btnRegister);
        serviceLayer = ApiUtils.getServiceLayer();
        chkPasswordVisible = findViewById(R.id.passwordVisible);
        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.INVISIBLE);

    }

    private void initButtons() {
        chkPasswordVisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chkPasswordVisible.isChecked()) {
                    edtPassword.setTransformationMethod(null);
                } else {
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString();
                String password = edtPassword.getText().toString();
                String userName = edtUsername.getText().toString();

                if (email.trim().length() >= 4 && password.trim().length() >= 4 && userName.trim().length() >= 4) {
                    Log.i("button clicked", "char 4 or over");
                    Toast.makeText(getApplicationContext(), "checking details", Toast.LENGTH_SHORT).show();
                    spinner.setVisibility(View.VISIBLE);
                    checkRegister(email, userName, password);

                } else {
                    Toast.makeText(getApplicationContext(), "Need atleast 4 characters in both", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkRegister(final String email, final String userName, final String password) {
        Call<JsonRep> call = serviceLayer.registerUser(email, userName, password);
        call.enqueue(new Callback<JsonRep>() {

            @Override
            public void onResponse(Call<JsonRep> call, Response<JsonRep> response) {
                if (response.isSuccessful()) {
                    JsonRep jsonRep = response.body();

                    if (jsonRep.getSuccess()) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("email", email);

                        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        String user = edtUsername.getText().toString();
                        editor.putString("userName", user);
                        editor.commit();

                        spinner.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), jsonRep.getErr(), Toast.LENGTH_SHORT).show();
                        spinner.setVisibility(View.INVISIBLE);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error! Please try again.", Toast.LENGTH_SHORT).show();
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