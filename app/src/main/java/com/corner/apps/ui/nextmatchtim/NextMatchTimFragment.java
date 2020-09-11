package com.corner.apps.ui.nextmatchtim;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.corner.apps.Adapter.JadwalAdapter;
import com.corner.apps.Entity.Event;
import com.corner.apps.R;
import com.corner.apps.RestJadwal;
import com.corner.apps.RetrofitGSON;
import com.corner.apps.TimActivity;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NextMatchTimFragment extends Fragment {
    private String id;
    private RecyclerView nextmatch;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TimActivity tim = (TimActivity) getActivity();
        View root = inflater.inflate(R.layout.fragment_nextmatchtim, container, false);
        id = tim.getId();
        nextmatch = root.findViewById(R.id.recyeclerNextMatchTim);
        getdata();

        return root;
    }
    public void getdata(){
        HashMap<String,String> params = new HashMap<>();
        params.put("id",id);
        Call<RestJadwal> getmatch = RetrofitGSON.getInstance().api().getEventTim(params);
        getmatch.enqueue(new Callback<RestJadwal>() {
            @Override
            public void onResponse(Call<RestJadwal> call, Response<RestJadwal> response) {
                if (response.isSuccessful()){
                    List<Event> list = response.body().getEvents();
                    nextmatch.setLayoutManager(new LinearLayoutManager(getContext()));
                    JadwalAdapter adapter = new JadwalAdapter(list);
                    nextmatch.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<RestJadwal> call, Throwable t) {

            }
        });
    }
}
