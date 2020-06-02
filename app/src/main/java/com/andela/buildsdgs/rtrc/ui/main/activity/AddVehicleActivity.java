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
import com.andela.buildsdgs.rtrc.models.VehicleCategory;
import com.andela.buildsdgs.rtrc.models.VehicleCategoryList;
import com.andela.buildsdgs.rtrc.services.RTRCService;
import com.andela.buildsdgs.rtrc.services.ServiceBuilder;
import com.andela.buildsdgs.rtrc.ui.main.adaptors.VehicleCategorySpinnerAdaptor;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        parent_view = findViewById(android.R.id.content);
        edtLicenseNumber = findViewById(R.id.edt_license_number);
        edtRegistrationNumber = findViewById(R.id.edt_registration_number);
        edtVehicleModel = findViewById(R.id.edt_vehicle_model);
        edtChassisNumber = findViewById(R.id.edt_chassis_number);
        btnSubmitVehicle = findViewById(R.id.btn_submit_vehicle);
        spnCategories = findViewById(R.id.spinner_car_categories);
        //final String[] categpryId = new String[1];
        final Vehicle vehicle = new Vehicle();

        //make API call for vehicle categories
        final String authToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiNjM3M2I0YzQtZjhhMy00N2QwLWI3ZmItOGFiNjQ3NTI0MDdiIiwidXNlcm5hbWUiOiJzYWxtYSIsImV4cCI6MTYyMjE1NTA0MywiZW1haWwiOiJhaWRvb2FtYXR1QGdtYWlsLmNvbSIsIm9yaWdfaWF0IjoxNTkxMDUxMDQzfQ.uv2COMdS9DWq_c4krUTFX9dktvMNWzK98V0OLLnfb4Q";
        RTRCService rtrcService = ServiceBuilder.buildService(RTRCService.class);
        Call<VehicleCategoryList> categoryListCall = rtrcService.getCategoryList("Bearer " + authToken);
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
                            // categpryId[0] = category.getId();
                            vehicle.setCategory(category.getId());
                            Toast.makeText(AddVehicleActivity.this, "Selected : " + vehicle.getCategory(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
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

        btnSubmitVehicle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String licenseNum = edtLicenseNumber.getText().toString().trim();
                final String registrationNum = edtRegistrationNumber.getText().toString().trim();
                final String model = edtVehicleModel.getText().toString().trim();
                final String chasisNumber = edtChassisNumber.getText().toString().trim();

                if (licenseNum.equals("") || registrationNum.equals("") || model.equals("") || chasisNumber.equals("")) {
                    Snackbar.make(parent_view, "All fields must have values", Snackbar.LENGTH_SHORT).show();
                } else {
                    vehicle.setChassis_number(chasisNumber);
                    vehicle.setLicense_number(licenseNum);
                    vehicle.setModel(model);
                    vehicle.setRegistration_number(registrationNum);
                    RTRCService rtrcService = ServiceBuilder.buildService(RTRCService.class);
                    Call<Vehicle> vehicleAddCall = rtrcService.addVehicle("Bearer " + authToken, vehicle);
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
