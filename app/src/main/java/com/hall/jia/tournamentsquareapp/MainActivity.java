package com.hall.jia.tournamentsquareapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hall.jia.tournamentsquareapp.connection.ApiUtils;
import com.hall.jia.tournamentsquareapp.connection.ServiceLayer;
import com.hall.jia.tournamentsquareapp.model.JsonReplies.JsonEmail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    TextView txtUsername;
    Button removePref;
    Button btnHost;
    Button btnHosted;
    Button btnJoin;
    Button btnJoined;
    Button btnRunning;
    SharedPreferences sharedPreferences;
    ServiceLayer serviceLayer;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            initVariables();
            initButtons();
        }

    private void initButtons() {
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JoinActivity.class);
                startActivity(intent); }});

        btnJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, JoinedActivity.class);
                startActivity(intent);
            }
        });

        btnHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HostActivity.class);
                startActivity(intent);
            }
        });

        btnHosted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HostedActivity.class);
                startActivity(intent);
            }
        });

        btnRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RunningActivity.class);
                startActivity(intent);
            }
        });

        removePref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                String empty = "";
                editor.putString("password", empty);
                editor.apply();

                editor.putString("userName", empty);
                editor.apply();

                editor.putString("email", empty);
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        });
    }
    private void initVariables() {
        sharedPreferences= getSharedPreferences("login", MODE_PRIVATE);
        final String user= sharedPreferences.getString("userName","");
        serviceLayer = ApiUtils.getServiceLayer();
        txtUsername = findViewById(R.id.txtUsername);
        removePref = findViewById(R.id.btnLogout);
        btnHost = findViewById(R.id.btnHost);
        btnHosted = findViewById(R.id.btnHosted);
        btnJoin = findViewById(R.id.btnJoin);
        btnJoined = findViewById(R.id.btnJoined);
        btnRunning = findViewById(R.id.btnrunning);

        Bundle extras = getIntent().getExtras();
        String email = "";
        if(extras != null){
            email = extras.getString("email");
        }

        if(user.equals("")){
            rtnUser(email);
        }else{
            txtUsername.setText("welcome: " + user);
        }
    }

    private void rtnUser(final String email) {
        Call<JsonEmail> call = serviceLayer.getusername(email);
        call.enqueue(new Callback<JsonEmail>() {

            @Override
            public void onResponse(Call<JsonEmail> call, Response<JsonEmail> response) {
                if(response.isSuccessful()){
                    JsonEmail jsonEmail = response.body();

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String rtnUserName = jsonEmail.getUname();
                    editor.putString("userName", rtnUserName);
                    editor.apply();
                    txtUsername.setText("welcome: " + rtnUserName);
                }else{
                    Toast.makeText(getApplicationContext(), "Error! Please try again!", Toast.LENGTH_SHORT).show();
            }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(getApplicationContext(), "gotta figure this out", Toast.LENGTH_SHORT).show();
            }
        });
    }
    }

