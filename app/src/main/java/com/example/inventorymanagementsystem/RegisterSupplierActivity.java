package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.inventorymanagementsystem.API.API;
import com.example.inventorymanagementsystem.API.API_BASE_URL;
import com.example.inventorymanagementsystem.DTO.SupplierReg;
import com.example.inventorymanagementsystem.DialogBox.DialogBox;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterSupplierActivity extends AppCompatActivity {

    TextView supplierFirstName, supplierLastName, supplierType, supplierAddress, supplierPhoneNumber, supplierNIC, supplierUsername, supplierPassword;
    Button registerSupplier;
    API api;
    private String URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_supplier);

        supplierFirstName = findViewById(R.id.supplierRegister_firstName_input);
        supplierLastName = findViewById(R.id.supplierRegister_lastName_input);
        supplierType = findViewById(R.id.supplierRegister_Type_input);
        supplierAddress = findViewById(R.id.supplierRegister_address_input);
        supplierPhoneNumber = findViewById(R.id.supplierRegister_phoneNumber_input);
        supplierNIC = findViewById(R.id.supplierRegister_NIC_input);
        supplierUsername = findViewById(R.id.supplierRegister_username_input);
        supplierPassword = findViewById(R.id.supplierRegister_password_input);
        registerSupplier = findViewById(R.id.Supplier_Register_buttn);


        API_BASE_URL base = new API_BASE_URL();
        URL = base.API_URL();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(httpClient.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);

        registerSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(supplierFirstName.getText().toString())
                        || TextUtils.isEmpty(supplierLastName.getText().toString())
                        || TextUtils.isEmpty(supplierType.getText().toString())
                        || TextUtils.isEmpty(supplierAddress.getText().toString())
                        || TextUtils.isEmpty(supplierPhoneNumber.getText().toString())
                        || TextUtils.isEmpty(supplierNIC.getText().toString())
                        || TextUtils.isEmpty(supplierUsername.getText().toString())
                        || TextUtils.isEmpty(supplierPassword.getText().toString())) {
                    new DialogBox(RegisterSupplierActivity.this, "All fields are mandatory!");
                } else {
                    registerSupplier();
                }
            }

        });
    }

    private void registerSupplier() {
        try {
            SupplierReg supplierReg = new SupplierReg();
            supplierReg.setFirstName(supplierFirstName.getText().toString().trim());
            supplierReg.setLastName(supplierLastName.getText().toString().trim());
            supplierReg.setSupplierType(supplierType.getText().toString().trim());
            supplierReg.setAddress(supplierAddress.getText().toString().trim());
            supplierReg.setPhoneNumber(supplierPhoneNumber.getText().toString().trim());
            supplierReg.setNicNumber(supplierNIC.getText().toString().trim());
            supplierReg.setUsername(supplierUsername.getText().toString().trim());
            supplierReg.setPassword(supplierPassword.getText().toString().trim());


            Call<String> call = api.registerSupplier(supplierReg);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    String check = response.body();
                    new DialogBox(RegisterSupplierActivity.this, check);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    new DialogBox(RegisterSupplierActivity.this, "An error has occurred! 1");
                }
            });
        }
        catch (Exception e)
        {
            new DialogBox(RegisterSupplierActivity.this, "An error has occurred! 2");
        }
    }
}


