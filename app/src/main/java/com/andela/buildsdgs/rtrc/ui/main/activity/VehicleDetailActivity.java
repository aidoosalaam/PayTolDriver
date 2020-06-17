package com.andela.buildsdgs.rtrc.ui.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andela.buildsdgs.rtrc.R;
import com.andela.buildsdgs.rtrc.models.Vehicle;
import com.andela.buildsdgs.rtrc.services.RTRCService;
import com.andela.buildsdgs.rtrc.services.ServiceUtil;
import com.andela.buildsdgs.rtrc.utility.Tools;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehicleDetailActivity extends AppCompatActivity {

    private TextView mTextModelName;
    private TextView mTextRegistrationNumber;
    private TextView mTextLicenseNumber;
    private TextView mTextUserCreated;
    private TextView mTextDateModified;
    private TextView mTextChasisNumber;
    private ImageView mImgQRCode;
    private View parentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);
        Tools serviceTools =  new Tools(VehicleDetailActivity.this);
        parentView = findViewById(android.R.id.content);
        mTextModelName = findViewById(R.id.txt_vehicle_det_model);
        mTextRegistrationNumber = findViewById(R.id.txt_vehicle_det_registration_number);
        mTextLicenseNumber = findViewById(R.id.txt_vehicle_det_license_number);
        mTextUserCreated = findViewById(R.id.txt_vehicle_det_user_created);
        mTextDateModified = findViewById(R.id.txt_vehicle_det_datetime_modified);
        mTextChasisNumber = findViewById(R.id.txt_vehicle_det_chasis_num);
        mImgQRCode = findViewById(R.id.img_vehicle_qr);

        Intent intent = getIntent();
        String categoryId = intent.getStringExtra("position");
        initToolbar();

        //make API call the vehicle detail
        RTRCService rtrcService = ServiceUtil.buildService(RTRCService.class);
        Call<Vehicle> vehicleCall = rtrcService.getVehicleDetail("Bearer " + serviceTools.retrieveUserProfile().getToken(), categoryId);
        vehicleCall.enqueue(new Callback<Vehicle>() {
            @Override
            public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                if (response.isSuccessful()) {
                    final Vehicle vehicleDetail = response.body();
                    assert vehicleDetail != null;
                    mTextModelName.setText(vehicleDetail.getModel());
                    mTextRegistrationNumber.setText(vehicleDetail.getRegistrationNumber());
                    mTextLicenseNumber.setText(vehicleDetail.getLicenseNumber());
                    mTextUserCreated.setText(vehicleDetail.getUser().getName());
                    mTextDateModified.setText(vehicleDetail.getUpdatedAt());
                    mTextChasisNumber.setText(vehicleDetail.getChassisNumber());
                    Picasso.with(VehicleDetailActivity.this).load(vehicleDetail.getQrCode()).into(mImgQRCode);

                    mImgQRCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (downloadQRImage(vehicleDetail.getQrCode(),vehicleDetail.getChassisNumber())){
                                //Snackbar.make(parentView, "Downloaded to path : " + getExternalFilesDir(null) +
                                        //File.separator + vehicleDetail.getChassisNumber()+ "_qr_code.jpg", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Snackbar.make(parentView, "Error fetching details ; " + jObjError.toString(), Snackbar.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Snackbar.make(parentView, "Error fetching details ; " + e.toString(), Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Vehicle> call, Throwable t) {

            }
        });
    }

    //method to download QR image
    private boolean downloadQRImage(String imgUrl, String chasis) {
        System.out.println("Downloading QR.....");
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = new ByteArrayInputStream(imgUrl.getBytes());
            out = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + File.separator + chasis+ "_qr_code.jpg");
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Snackbar.make(parentView, "Error downloading QR ; " + ex.toString(), Snackbar.LENGTH_SHORT).show();
            return false;
        }  finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_vehicle_det);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
