package com.corner.apps;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.corner.apps.Entity.Team;
import com.corner.apps.ui.detailtim.DetailTimFragment;
import com.corner.apps.ui.lastmatchtim.LastMatchTimFragment;
import com.corner.apps.ui.nextmatchtim.NextMatchTimFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

public class TimActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {
    private String id;
    private ImageView banner , logo;

    public String getId() {
        Bundle ambil = getIntent().getExtras();
        id = ambil.getString("id");
        return id;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim);
        BottomNavigationView navView = findViewById(R.id.nav_view_tim);
        banner = findViewById(R.id.bannerTim);
        logo = findViewById(R.id.logonya);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_DetailTim, R.id.navigation_LastMatchTim, R.id.navigation_NextMatchTim)
                .build();
        navView.setOnNavigationItemSelectedListener(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameTim , new DetailTimFragment());
        ft.commit();

        getLogoTitle();

    }
    public void getLogoTitle(){
        getId();

        HashMap<String,String> params = new HashMap<>();
        params.put("id",id);
        Call<RestTeam> getlogotitle = RetrofitGSON.getInstance().api().getDetailTeam(params);
        getlogotitle.enqueue(new Callback<RestTeam>() {
            @Override
            public void onResponse(Call<RestTeam> call, Response<RestTeam> response) {
                if(response.isSuccessful()){
                    List<Team> getlgtl = response.body().getTeams();
                    for(Team timnya : getlgtl){
                        Picasso.with(TimActivity.this).load(timnya.getStrTeamBanner())
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_background)
                                .into(banner);

                        Picasso.with(TimActivity.this).load(timnya.getStrTeamBadge())
                                .placeholder(R.drawable.ic_launcher_background)
                                .error(R.drawable.ic_launcher_background)
                                .into(logo);

                        getSupportActionBar().setTitle(timnya.getStrTeam());
                    }
                }
            }

            @Override
            public void onFailure(Call<RestTeam> call, Throwable t) {

            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int idnya = item.getItemId();
        if (idnya == R.id.navigation_DetailLiga){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameTim , new DetailTimFragment());
            ft.commit();
        }
        else if(idnya == R.id.navigation_NextMatch){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameTim , new NextMatchTimFragment());
            ft.commit();
        }
        else if(idnya == R.id.navigation_LastMatch){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameTim , new LastMatchTimFragment());
            ft.commit();
        }

        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
