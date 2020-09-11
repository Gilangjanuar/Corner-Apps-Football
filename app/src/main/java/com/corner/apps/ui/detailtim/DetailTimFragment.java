package com.corner.apps.ui.detailtim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.corner.apps.Entity.Team;
import com.corner.apps.R;
import com.corner.apps.RestTeam;
import com.corner.apps.RetrofitGSON;
import com.corner.apps.TimActivity;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTimFragment extends Fragment {
    private String id;
    private TextView txtStadium , txtNickname , txtSince , txtDecription ,txtCountry;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TimActivity tim = (TimActivity) getActivity();
        id = tim.getId();
        View root = inflater.inflate(R.layout.fragment_detailtim, container, false);
        txtStadium = root.findViewById(R.id.stadium);
        txtNickname = root.findViewById(R.id.nickname);
        txtSince = root.findViewById(R.id.since);
        txtDecription = root.findViewById(R.id.description);
        txtCountry = root.findViewById(R.id.country);
        getdetail();

        return root;
    }
    public void getdetail(){
        HashMap<String,String> params = new HashMap<>();
        params.put("id",id);
        Call<RestTeam> getTim = RetrofitGSON.getInstance().api().getDetailTeam(params);
        getTim.enqueue(new Callback<RestTeam>() {
            @Override
            public void onResponse(Call<RestTeam> call, Response<RestTeam> response) {
                if (response.isSuccessful()){
                    List<Team> listTim = response.body().getTeams();
                    for (Team a : listTim){
                        txtNickname.setText(a.getStrKeywords());
                        txtStadium.setText(a.getStrStadium());
                        txtSince.setText(a.getIntFormedYear());
                        txtCountry.setText(a.getStrCountry());
                        txtDecription.setText(a.getStrDescriptionEN());
                    }
                }
            }

            @Override
            public void onFailure(Call<RestTeam> call, Throwable t) {

            }
        });
    }
}
