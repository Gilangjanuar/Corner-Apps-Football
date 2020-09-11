package com.corner.apps;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiService {
    //TODO(2) Endpoint untuk List Liga
    @GET("all_leagues.php")
    Call<RestLeagues> getListLeagues(@QueryMap Map<String,String> Maps);

    //Mengambil detail Liga
    @GET("lookupleague.php")
    Call<RestDetailLeague> getListDetailLeague(@QueryMap Map<String , String> Maps);

    //Mengambil data jadwal pertandigan
    @GET("eventsnextleague.php")
    Call<RestJadwal> getListJadwal(@QueryMap Map<String,String> Maps);

    //Mengambil List Team
    @GET("lookup_all_teams.php")
    Call<RestTeam> getListTeam(@QueryMap Map<String , String> Maps);

    //Mengambil Klasmen
    @GET("lookuptable.php")
    Call<RestKlasmen> getListKlasmen(@QueryMap Map<String,String> Maps);

    //Mengambil History match Liga
    @GET("eventspastleague.php")
    Call<RestJadwal> getHistoryLeague(@QueryMap Map<String,String> Maps);

    //Mengambil detail team
    @GET("lookupteam.php")
    Call<RestTeam> getDetailTeam(@QueryMap Map<String,String> Maps);

    //Mengambil 5 laga berikutnya berdasarkan tim
    @GET("eventsnext.php")
    Call<RestJadwal> getEventTim(@QueryMap Map<String , String> Maps);

    //Mengambil 5 Laga yang telah lewat dari tim
    @GET("eventslast.php")
    Call<RestResult> getLastEventTeam(@QueryMap Map<String,String> Maps);
}
