package com.andela.buildsdgs.rtrc.ui.main.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.andela.buildsdgs.rtrc.R;
import com.andela.buildsdgs.rtrc.models.WalletResponse;
import com.andela.buildsdgs.rtrc.services.RTRCService;
import com.andela.buildsdgs.rtrc.services.ServiceUtil;
import com.andela.buildsdgs.rtrc.ui.main.activity.AddVehicleActivity;
import com.andela.buildsdgs.rtrc.ui.main.activity.PaymentWebActivity;
import com.andela.buildsdgs.rtrc.utility.Tools;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends Fragment {
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_account_layout, container,false);
        Tools serviceTools = new Tools(mContext);
        final TextView mTxtDriverName = view.findViewById(R.id.txt_account_driver_name);
        final TextView mTxtAccountBalance = view.findViewById(R.id.txt_account_balance);
        final TextView mTxtTotalPayment = view.findViewById(R.id.txt_account_total_payment);
        final TextView mTxtTotalDeposit = view.findViewById(R.id.txt_account_total_deposit);
        TextView mBtnSubmitWallet = view.findViewById(R.id.btn_account_topup_wallet);

        //Fetch User Balance
        RTRCService rtrcService = ServiceUtil.buildService(RTRCService.class);
        Call<WalletResponse> walletResponseCall = rtrcService.getUserWallet("Bearer "+serviceTools.retrieveUserProfile().getToken());
        walletResponseCall.enqueue(new Callback<WalletResponse>() {
            @Override
            public void onResponse(Call<WalletResponse> call, Response<WalletResponse> response) {
                if (response.isSuccessful()){
                    WalletResponse walletBalance = response.body();
                    mTxtDriverName.setText(walletBalance.getResults()[0].getUser().getName());
                    mTxtAccountBalance.setText("GHS " + walletBalance.getResults()[0].getBalance());
                    mTxtTotalPayment.setText("GHS " + walletBalance.getResults()[0].getTotalTransactions());
                    mTxtTotalDeposit.setText("GHS " + walletBalance.getResults()[0].getTotalDeposits());
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Snackbar.make(view, "Could not retrieve wallet balance. Reason : " + jObjError.toString(), Snackbar.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Snackbar.make(view, "Failed to retrieve balance ; Reason : " + e.toString(), Snackbar.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<WalletResponse> call, Throwable t) {
                Snackbar.make(view, "Failed to retrieve balance ; Reason : " + t.toString(), Snackbar.LENGTH_LONG).show();
            }
        });

        //Process Wallet Topup
        mBtnSubmitWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PaymentWebActivity.class);
                startActivity(intent);
              //  Snackbar.make(view, "Implementation in progress....", Snackbar.LENGTH_LONG).show();
            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;

    }
}
