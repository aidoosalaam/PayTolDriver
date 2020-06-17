package com.andela.buildsdgs.rtrc.ui.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.andela.buildsdgs.rtrc.R;
import com.andela.buildsdgs.rtrc.models.DepositListResponse;
import com.andela.buildsdgs.rtrc.services.RTRCService;
import com.andela.buildsdgs.rtrc.services.ServiceUtil;
import com.andela.buildsdgs.rtrc.ui.main.adaptors.DepositRecyclerAdaptor;
import com.andela.buildsdgs.rtrc.utility.Tools;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepositActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        final View parentView = findViewById(android.R.id.content);
        initToolbar();
        final RecyclerView recyclerView = findViewById(R.id.recycle_view_deposit);
        LinearLayoutManager regRecyLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(regRecyLayoutManager);

        Tools serviceTools = new Tools(this);
        //fetch Deposits List From API
        final RTRCService rtrcService = ServiceUtil.buildService(RTRCService.class);
        Call<DepositListResponse> depositCall = rtrcService.getDeposit("Bearer " + serviceTools.retrieveUserProfile().getToken());
        depositCall.enqueue(new Callback<DepositListResponse>() {
            @Override
            public void onResponse(Call<DepositListResponse> call, Response<DepositListResponse> response) {
                if (response.isSuccessful()) {
                    DepositRecyclerAdaptor recyclerAdaptor = new DepositRecyclerAdaptor(DepositActivity.this, response.body().getResults());
                    recyclerView.setAdapter(recyclerAdaptor);
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Snackbar.make(parentView, "Could not retrieve deposits. Reason : " + jObjError.toString(), Snackbar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(parentView, "Failed; Reason : " + e.toString(), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<DepositListResponse> call, Throwable t) {
                Snackbar.make(parentView, "Failed; Reason : " + t.toString() , Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Deposits");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this);
    }
}
