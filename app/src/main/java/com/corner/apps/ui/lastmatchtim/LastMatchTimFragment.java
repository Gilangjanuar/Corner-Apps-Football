package com.corner.apps.ui.lastmatchtim;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corner.apps.Adapter.LastMatchAdapter;
import com.corner.apps.Adapter.LastMatchTimAdater;
import com.corner.apps.RestResult;
import com.corner.apps.Entity.Result;
import com.corner.apps.R;
import com.corner.apps.RestJadwal;
import com.corner.apps.RetrofitGSON;
import com.corner.apps.TimActivity;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LastMatchTimFragment extends Fragment {
    private String id;
    private RecyclerView lastmatch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TimActivity tim = (TimActivity) getActivity();
        id = tim.getId();
        View root = inflater.inflate(R.layout.fragment_lastmatchtim, container, false);
        lastmatch = root.findViewById(R.id.recyclerLastMatchTim);
        getdata();

        return root;
    }
    public void getdata(){
        HashMap<String,String> params = new HashMap<>();
        params.put("id",id);



        Call<RestResult> lastmatchteam = RetrofitGSON.getInstance().api().getLastEventTeam(params);
        lastmatchteam.enqueue(new Callback<RestResult>() {
            @Override
            public void onResponse(Call<RestResult> call, Response<RestResult> response) {
                if (response.isSuccessful()){
                    List<Result> listlastmatch = response.body().getResults();
                    lastmatch.setLayoutManager(new LinearLayoutManager(getContext()));
                    LastMatchTimAdater adapter = new LastMatchTimAdater(listlastmatch);
                    lastmatch.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RestResult> call, Throwable t) {

            }

        });
    }
}
