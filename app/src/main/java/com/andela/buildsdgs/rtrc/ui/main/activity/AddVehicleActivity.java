package com.andela.buildsdgs.rtrc.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.andela.buildsdgs.rtrc.MainActivity;
import com.andela.buildsdgs.rtrc.R;
import com.andela.buildsdgs.rtrc.models.Vehicle;
import com.andela.buildsdgs.rtrc.models.VehicleAddRequest;
import com.andela.buildsdgs.rtrc.models.VehicleCategory;
import com.andela.buildsdgs.rtrc.models.VehicleCategoryList;
import com.andela.buildsdgs.rtrc.services.RTRCService;
import com.andela.buildsdgs.rtrc.services.ServiceUtil;
import com.andela.buildsdgs.rtrc.ui.main.adaptors.VehicleCategorySpinnerAdaptor;
import com.andela.buildsdgs.rtrc.utility.ServiceContants;
import com.andela.buildsdgs.rtrc.utility.Tools;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVehicleActivity extends AppCompatActivity {

    private EditText edtLicenseNumber;
    private EditText edtRegistrationNumber;
    private EditText edtVehicleModel;
    private EditText edtChassisNumber;
    private Button btnSubmitVehicle;
    private Spinner spnCategories;
    private View parent_view;
    private Tools serviceTools = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        serviceTools =  new Tools(AddVehicleActivity.this);
        parent_view = findViewById(android.R.id.content);
        edtLicenseNumber = findViewById(R.id.edt_license_number);
        edtRegistrationNumber = findViewById(R.id.edt_registration_number);
        edtVehicleModel = findViewById(R.id.edt_vehicle_model);
        edtChassisNumber = findViewById(R.id.edt_chassis_number);
        btnSubmitVehicle = findViewById(R.id.btn_submit_vehicle);
        spnCategories = findViewById(R.id.spinner_car_categories);
        final VehicleAddRequest vehicle = new VehicleAddRequest();

        //make API call for vehicle categories
        RTRCService rtrcService = ServiceUtil.buildService(RTRCService.class);
        Call<VehicleCategoryList> categoryListCall = rtrcService.getCategoryList("Bearer " + serviceTools.retrieveUserProfile().getToken());
        categoryListCall.enqueue(new Callback<VehicleCategoryList>() {
            @Override
            public void onResponse(Call<VehicleCategoryList> call, Response<VehicleCategoryList> response) {
                if (response.isSuccessful()) {
                    final List<VehicleCategory> categoryList = response.body().getResults();
                    VehicleCategorySpinnerAdaptor vehicleCategorySpinnerAdaptor = new VehicleCategorySpinnerAdaptor(AddVehicleActivity.this, R.layout.item_spinner_category, categoryList);
                    spnCategories.setAdapter(vehicleCategorySpinnerAdaptor);
                    spnCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            VehicleCategory category = categoryList.get(position);
                            vehicle.setCategory(category.getId());
                          //  Toast.makeText(AddVehicleActivity.this, "Selected : " + vehicle.getCategory(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            Snackbar.make(parent_view, "Category not selected ", Snackbar.LENGTH_SHORT).show();

                        }
                    });
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Snackbar.make(parent_view, "Could not retrieve categories. Reason : " + jObjError.toString(), Snackbar.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        Snackbar.make(parent_view, "Failed; Reason : " + e.toString(), Snackbar.LENGTH_SHORT).show();
                    }
                }

            }
            @Override
            public void onFailure(Call<VehicleCategoryList> call, Throwable t) {
                Snackbar.make(parent_view, "Request Failed ; " + t.toString(), Snackbar.LENGTH_SHORT).show();
            }
        });

        //send vehicle registration request
        submitVehicle(vehicle);

    }

    public void submitVehicle(VehicleAddRequest vehicleParam){
        final VehicleAddRequest vehicleRequest = vehicleParam;

        btnSubmitVehicle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String licenseNum = edtLicenseNumber.getText().toString().trim();
                final String registrationNum = edtRegistrationNumber.getText().toString().trim();
                final String model = edtVehicleModel.getText().toString().trim();
                final String chasisNumber = edtChassisNumber.getText().toString().trim();

                if ( "".equals(licenseNum)  || "".equals(registrationNum) || "".equals(model) || "".equals(chasisNumber)) {
                    Snackbar.make(parent_view, "All fields must have values", Snackbar.LENGTH_SHORT).show();
                } else {
                    vehicleRequest.setChassisNumber(chasisNumber);
                    vehicleRequest.setLicenseNumber(licenseNum);
                    vehicleRequest.setModel(model);
                    vehicleRequest.setRegistrationNumber(registrationNum);
                    RTRCService rtrcService = ServiceUtil.buildService(RTRCService.class);
                    Call<Vehicle> vehicleAddCall = rtrcService.addVehicle("Bearer " + serviceTools.retrieveUserProfile().getToken(), vehicleRequest);
                    vehicleAddCall.enqueue(new Callback<Vehicle>() {
                        @Override
                        public void onResponse(Call<Vehicle> call, Response<Vehicle> response) {
                            if (response.code() == 201 || response.code() == 200){
                                Toast.makeText(AddVehicleActivity.this, "New vehicle added successfully ", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(AddVehicleActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else {
                                Snackbar.make(parent_view, "Failed to add a new vehicle ", Snackbar.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<Vehicle> call, Throwable t) {
                            Snackbar.make(parent_view, "Failed to add a new vehicle ; " + t.toString(), Snackbar.LENGTH_SHORT).show();
                            System.out.println("Failed to add a new vehicle ; " + t.toString());
                        }
                    });

                }
            }
        });
    }

}
