package com.andela.buildsdgs.rtrc.ui.main.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andela.buildsdgs.rtrc.R;
import com.andela.buildsdgs.rtrc.models.VehicleListResp;
import com.andela.buildsdgs.rtrc.services.RTRCService;
import com.andela.buildsdgs.rtrc.services.ServiceUtil;
import com.andela.buildsdgs.rtrc.ui.main.activity.AddVehicleActivity;
import com.andela.buildsdgs.rtrc.ui.main.adaptors.VehicleRecyclerAdaptor;
import com.andela.buildsdgs.rtrc.utility.Tools;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleFragment extends Fragment {
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_vehicle_layout, container, false);
        final RecyclerView regVehicleRecyView = view.findViewById(R.id.recycler_view_reg_vehicles);
        LinearLayoutManager regRecyLayoutManager = new LinearLayoutManager(mContext);
        Tools serviceTools = new Tools(mContext);
        regVehicleRecyView.setLayoutManager(regRecyLayoutManager);
        FloatingActionButton fab = view.findViewById(R.id.fabtn_add_vehicle);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddVehicleActivity.class);
                startActivity(intent);
            }
        });

        //fetch Vehicles List From API
        final RTRCService rtrcService = ServiceUtil.buildService(RTRCService.class);
        Call<VehicleListResp> vehicleCall = rtrcService.getVehiclesList("Bearer " + serviceTools.retrieveUserProfile().getToken());
        vehicleCall.enqueue(new Callback<VehicleListResp>() {
            @Override
            public void onResponse(Call<VehicleListResp> call, Response<VehicleListResp> response) {

                if (response.isSuccessful()) {
                    VehicleRecyclerAdaptor recyclerAdaptor = new VehicleRecyclerAdaptor(mContext, response.body().getResults());
                    regVehicleRecyView.setAdapter(recyclerAdaptor);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Snackbar.make(view, "Could not retrieve vehicles. Reason : " + jObjError.toString(), Snackbar.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Snackbar.make(view, "Failed; Reason : " + e.toString(), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<VehicleListResp> call, Throwable t) {
                Snackbar.make(view, "Failed; Reason : " + t.toString(), Snackbar.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
