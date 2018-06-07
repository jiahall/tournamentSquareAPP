package com.hall.jia.tournamentsquareapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.hall.jia.tournamentsquareapp.connection.ApiUtils;
import com.hall.jia.tournamentsquareapp.connection.ServiceLayer;

import com.hall.jia.tournamentsquareapp.model.JsonReplies.JsonRep;
import com.hall.jia.tournamentsquareapp.model.Tournament;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostActivity extends AppCompatActivity {

    EditText edtLocation;
    EditText edtGame;
    EditText edtPostcode;
    EditText edtTournamentName;
    EditText edtMaxEntrant;
    EditText edtEntryFee;
    EditText edt1st;
    EditText edt2nd;
    EditText edt3rd;

    TextView tvDate;
    TextView tvTime;
    CheckBox chkSaveDetails;
    Button   btnCreate;
    Switch swchPrize;
    ServiceLayer serviceLayer;
    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        edtLocation = findViewById(R.id.hstLocation);
        edtGame = findViewById(R.id.hstTournamentGame);
        edtPostcode = findViewById(R.id.hstPostcode);
        edtTournamentName = findViewById(R.id.hstTournamentName);
        edtMaxEntrant = findViewById(R.id.hstMaxEntrants);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        swchPrize = findViewById(R.id.hstPrize);
        edt1st= findViewById(R.id.edt1st);
        edt2nd= findViewById(R.id.edt2nd);
        edt3rd= findViewById(R.id.edt3rd);
        edtEntryFee = findViewById(R.id.hstEntryFee);
        chkSaveDetails = findViewById(R.id.hstTournamentDetails);
        btnCreate = findViewById(R.id.hstCreateTournament);

        sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        final String userName = sharedPreferences.getString("userName","");
        serviceLayer = ApiUtils.getServiceLayer();

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int hour= cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(HostActivity.this,
                         timeSetListener,
                        hour,minute, true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.d("onDateSet: hh/mm", hour+":"+minute);
                String time = hour+":"+minute;
                tvTime.setText(time);

            }
        };

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        HostActivity.this,android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                Log.d("onDateSet: mm/dd/yyyy", month+"/"+day+"/"+year);
                String date = month+"/"+day+"/"+year;
                tvDate.setText(date);
            }
        };

        edt1st.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)){
                    edt1st.clearFocus();
                    edt2nd.requestFocus();

                    edt2nd.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.showSoftInput(edt2nd, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }, 1);
                    return true;

                }
                return false;
            }
        });

        edt2nd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == keyEvent.ACTION_DOWN && (i == keyEvent.KEYCODE_ENTER)) {
                    edt2nd.clearFocus();
                    edt3rd.requestFocus();

                    edt3rd.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputMethodManager.showSoftInput(edt3rd, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }, 1);
                    return true;
                }
                return false;
            }
        });

        swchPrize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if(checked){
                    swchPrize.setThumbResource(R.drawable.ic_attach_money_black_24dp);
                }else{
                    swchPrize.setThumbResource(R.drawable.ic_percent);
                }

            }
        });


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tName= edtTournamentName.getText().toString();
                String maxEntrant= edtMaxEntrant.getText().toString();
                int max=Integer.parseInt(maxEntrant);
                String pstCde = edtPostcode.getText().toString();
                String loc = edtLocation.getText().toString();
                String game = edtGame.getText().toString();
                int entryFee;
                String prize;
                String symbol;
                if (swchPrize.isChecked()){
                    symbol = "$";
                }else{
                    symbol ="%";
                }


                if(edtEntryFee.getText().toString().equals("")) {
                    entryFee= 0;
                }else {
                    entryFee = Integer.parseInt(edtEntryFee.getText().toString());

                }

                if(edt1st.getText().toString().equals("0")) {
                     prize= "None";
                }else if(symbol.equals("$")){
                    prize ="First: " + symbol + edt1st.getText().toString() +
                            " Second: " + symbol + edt2nd.getText().toString() +
                            " Third: " + symbol + edt3rd.getText().toString();

                }else{
                    prize ="First: " + edt1st.getText().toString() + symbol +
                            " Second: " +  edt2nd.getText().toString() + symbol +
                            " Third: " +  edt3rd.getText().toString() + symbol;
                }

                String signIn = tvDate.getText().toString() + " " +tvTime.getText().toString();
                Log.d("prize", prize);


                if (tName.trim().length() >= 4 && max  < 33 && pstCde.trim().length() >= 4 && loc.trim().length() >= 4
                        && game.trim().length() >= 4 && signIn.trim().length() >= 4 ) {
                    Log.i("button clicked", "char 4 or over");
                    Toast.makeText(getApplicationContext(), "uploading details", Toast.LENGTH_SHORT).show();


                    Tournament hosted= new Tournament(tName, max, pstCde, loc, "Y", game, 0,
                    userName, entryFee, prize, signIn);

                    Log.i("testing uname from json", hosted.toString());

                    addTournament(hosted);
                } else {
                    Toast.makeText(getApplicationContext(), "Need atleast 4 characters in all but prize/entryfee", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addTournament(Tournament tournament){
        ///IT WORKS
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", tournament.getName());
        jsonObject.addProperty("host", tournament.getHost());
        jsonObject.addProperty("prize",  tournament.getPrize());
        jsonObject.addProperty("entryFee",  tournament.getEntryFee());
        jsonObject.addProperty("currEntrants", 0);
        jsonObject.addProperty("location",  tournament.getLocation());
        jsonObject.addProperty("game",  tournament.getGame());
        jsonObject.addProperty("enterAble",  tournament.getEnterAble());
        jsonObject.addProperty("signIn",  tournament.getSignIn());
        jsonObject.addProperty("postcode",  tournament.getPostcode());
        jsonObject.addProperty("maxEntrants",  tournament.getMaxEntrants());

        //does just adding the tournament not work and changing the servicelayer to turnament not work??

        Call<JsonRep> call = serviceLayer.addTournament(jsonObject);
        call.enqueue(new Callback<JsonRep>() {
            @Override
            public void onResponse(Call<JsonRep> call, Response<JsonRep> response) {
                if (response.isSuccessful()) {
                    Log.i("success", "got response");
                    JsonRep jsonRep = response.body();

                    Log.i("check1", jsonRep.getAction());
                    Log.i("check2", jsonRep.getSuccess().toString());
                    Log.i("check", jsonRep.getErr());
                    if (jsonRep.getSuccess()) {
                        Toast.makeText(getApplicationContext(),"Tournament successfully hosted", Toast.LENGTH_LONG).show();


                    } else {
                        Toast.makeText(getApplicationContext(), jsonRep.getErr(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Error! Please try again.", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Error! no connection to server or internet", Toast.LENGTH_SHORT).show();
            }
        });

    }



    }

