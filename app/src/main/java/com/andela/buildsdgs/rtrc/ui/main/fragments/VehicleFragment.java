package com.andela.buildsdgs.rtrc.ui.main.fragments;

import android.content.Context;
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
import com.andela.buildsdgs.rtrc.controller.VehicleController;
import com.andela.buildsdgs.rtrc.models.Vehicle;
import com.andela.buildsdgs.rtrc.ui.main.adaptors.VehicleRecyclerAdaptor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class VehicleFragment extends Fragment {
    private static final String TAG = "VehicleFragment";
    private Context mContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vehicle_layout,container,false);
        RecyclerView regVehicleRecyView =  view.findViewById(R.id.recycler_view_reg_vehicles);
        LinearLayoutManager regRecyLayoutManager = new LinearLayoutManager(mContext);
        regVehicleRecyView.setLayoutManager(regRecyLayoutManager);
        List<Vehicle> vehicles = new VehicleController().getVehicleList();
        VehicleRecyclerAdaptor recyclerAdaptor = new VehicleRecyclerAdaptor(mContext,vehicles);
        regVehicleRecyView.setAdapter(recyclerAdaptor);
        FloatingActionButton fab = view.findViewById(R.id.fabtn_add_vehicle);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "We're coming to add floating", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        this.mContext=context;
    }
}
