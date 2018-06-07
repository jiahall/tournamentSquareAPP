package com.hall.jia.tournamentsquareapp.connection;


public class ApiUtils {

    private static final String BASE_URL = "http://test-env.ckd8dfqcg3.eu-west-2.elasticbeanstalk.com/";
//    http://10.0.2.2:8080/tournamentSquare/ is for the emulator
    // http://test-env.ckd8dfqcg3.eu-west-2.elasticbeanstalk.com/
    public static ServiceLayer getServiceLayer(){
        return RetrofitClient.getClient(BASE_URL).create(ServiceLayer.class);
    }
}

