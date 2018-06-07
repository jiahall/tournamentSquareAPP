package com.hall.jia.tournamentsquareapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.hall.jia.tournamentsquareapp.connection.ApiUtils;
import com.hall.jia.tournamentsquareapp.connection.ServiceLayer;
import com.hall.jia.tournamentsquareapp.listadapter.TournamentList;
import com.hall.jia.tournamentsquareapp.model.Tournament;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinedActivity extends AppCompatActivity {
    ExpandableListView expandableListView;


    SharedPreferences sharedPreferences;
    ServiceLayer serviceLayer;
    Button btngetall;
    List<String> Headings = new ArrayList<>();
    List<String> L1 = new ArrayList<>();
    HashMap<String, List<String>> ChildList = new HashMap<String, List<String>>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined);
        serviceLayer = ApiUtils.getServiceLayer();
        btngetall = findViewById(R.id.btnFind);

        expandableListView = findViewById(R.id.exp_listview);
        getAllTournaments();

        sharedPreferences= getSharedPreferences("login", MODE_PRIVATE);

        TournamentList tournamentList = new TournamentList(this,Headings, ChildList);
        expandableListView.setAdapter(tournamentList);


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String tournamentjoin= Headings.get(groupPosition);
                editor.putString("tournamentjoin", tournamentjoin);
                editor.apply();
                Toast.makeText(getBaseContext(),Headings.get(groupPosition)+ " is expanded",Toast.LENGTH_SHORT).show();

            }
        });
    }

    protected void getAllTournaments() {
        Call<List<Tournament>> call = serviceLayer.getAllTournaments();
        Log.i("onResponse", "test");
        call.enqueue(new Callback<List<Tournament>>() {

            @Override
            public void onResponse(Call<List<Tournament>> call, Response<List<Tournament>> response) {
                List<Tournament> tournamentListing = response.body();

                for (int i = 0; i < tournamentListing.size(); i++) {
                    Headings.add(tournamentListing.get(i).getName());
                    L1.add("Game: "+ tournamentListing.get(i).getGame());
                    L1.add("Host: " + tournamentListing.get(i).getHost());
                    L1.add("Address: " + tournamentListing.get(i).getLocation());
                    L1.add("Postcode: " + tournamentListing.get(i).getPostcode());
                    L1.add("Sign in at: " + tournamentListing.get(i).getSignIn());
                    L1.add("Entry fee: " + String.valueOf(tournamentListing.get(i).getEntryFee()));
                    L1.add("Prize: " + String.valueOf(tournamentListing.get(i).getPrize()));
                    L1.add("Player limit: " + String.valueOf(tournamentListing.get(i).getCurrEntrants())+"/"+String.valueOf(tournamentListing.get(i).getMaxEntrants()));
                    ChildList.put(Headings.get(i), L1);

                    L1 = new ArrayList<String>();
                }
            }

            @Override
            public void onFailure(Call<List<Tournament>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
