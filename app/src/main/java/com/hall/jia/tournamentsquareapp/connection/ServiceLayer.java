package com.hall.jia.tournamentsquareapp.connection;

import com.google.gson.JsonObject;
import com.hall.jia.tournamentsquareapp.model.JsonReplies.JsonEmail;
import com.hall.jia.tournamentsquareapp.model.JsonReplies.JsonRep;
import com.hall.jia.tournamentsquareapp.model.Tournament;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceLayer {

    @PUT("registerUser/{email}/{userName}/{password}.json")
    Call<JsonRep> registerUser(@Path("email") String email, @Path("userName") String userName, @Path("password") String password);

    @GET("login/{email}/{password}.json")
    Call<JsonRep> login(@Path("email") String email, @Path("password") String password);

    @GET("getusername/{email}.json")
    Call<JsonEmail> getusername(@Path("email") String email);

    @GET("getAllTournaments.json")
    Call<List<Tournament>> getAllTournaments();


    @POST("addTournament")
    Call<JsonRep> addTournament(@Body JsonObject locationPost);

}