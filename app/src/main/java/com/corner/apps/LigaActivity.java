package com.corner.apps;

import android.os.Bundle;
import android.view.MenuItem;

import com.corner.apps.ui.beranda.BerandaFragment;
import com.corner.apps.ui.detailliga.DetailLigaFragment;
import com.corner.apps.ui.detailliga.League;
import com.corner.apps.ui.lastmatchliga.LastMatcLigaFragment;
import com.corner.apps.ui.nextmatchliga.NextMatchLigaFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LigaActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {
    private String id;

    public String getId() {
        Bundle ambil = getIntent().getExtras();
        id = ambil.getString("id");
        return id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liga);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_DetailLiga, R.id.navigation_NextMatch, R.id.navigation_LastMatch)
                .build();
        BottomNavigationView navigationView = findViewById(R.id.nav_view_bottom);
        navigationView.setOnNavigationItemSelectedListener(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_bottom , new DetailLigaFragment()); // Mengatur fragment pertama agar masuk ke DetailLigaFragment.java
        ft.commit();

        getId();

        //Mengatur title action bar
        ambilTitle();;

    }


    public void ambilTitle(){
        getId();
        HashMap<String , String> params = new HashMap<>();
        params.put("id",id);
        Call<RestDetailLeague> getnama = RetrofitGSON.getInstance().api().getListDetailLeague(params);
        getnama.enqueue(new Callback<RestDetailLeague>() {
            @Override
            public void onResponse(Call<RestDetailLeague> call, Response<RestDetailLeague> response) {
                if(response.isSuccessful()){
                    List<League> list = response.body().getLeagues();
                    for(League a : list){
                        getSupportActionBar().setTitle(a.getStrLeague());
                    }
                }
            }

            @Override
            public void onFailure(Call<RestDetailLeague> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int idnya = item.getItemId();
        if (idnya == R.id.navigation_DetailLiga){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_bottom , new DetailLigaFragment());
            ft.commit();
        }
        else if(idnya == R.id.navigation_NextMatch){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_bottom , new NextMatchLigaFragment());
            ft.commit();
        }
        else if(idnya == R.id.navigation_LastMatch){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.nav_host_bottom , new LastMatcLigaFragment());
            ft.commit();
        }

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
