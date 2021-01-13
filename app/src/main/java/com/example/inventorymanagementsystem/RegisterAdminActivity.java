package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.inventorymanagementsystem.API.API;
import com.example.inventorymanagementsystem.API.API_BASE_URL;
import com.example.inventorymanagementsystem.DTO.AdminReg;
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

public class RegisterAdminActivity extends AppCompatActivity {

    TextView firstName, lastName, address, phoneNumber, NIC, username, password;
    Button registerAdminButton;
    API api;
    private String URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        firstName = findViewById(R.id.adminRegister_firstName_input);
        lastName = findViewById(R.id.adminRegister_lastName_input);
        address = findViewById(R.id.adminRegister_address_input);
        phoneNumber = findViewById(R.id.adminRegister_phoneNumber_input);
        NIC = findViewById(R.id.adminRegister_NIC_input);
        username = findViewById(R.id.adminRegister_username_input);
        password = findViewById(R.id.adminRegister_password_input);
        registerAdminButton = findViewById(R.id.adminRegister_Register_buttn);

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

        registerAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(firstName.getText().toString())
                        || TextUtils.isEmpty(lastName.getText().toString())
                        || TextUtils.isEmpty(address.getText().toString())
                        || TextUtils.isEmpty(NIC.getText().toString())
                        || TextUtils.isEmpty(username.getText().toString())
                        || TextUtils.isEmpty(password.getText().toString())) {
                    new DialogBox(RegisterAdminActivity.this, "All fields are mandatory");

                } else {
                    registerAdmin();

                }
            }
        });
    }

    private void registerAdmin() {
        try {
            AdminReg adminReg = new AdminReg();
            adminReg.setFirstName(firstName.getText().toString().trim());
            adminReg.setLastName(lastName.getText().toString().trim());
            adminReg.setAddress(address.getText().toString().trim());
            adminReg.setPhoneNumber(phoneNumber.getText().toString().trim());
            adminReg.setNIC(NIC.getText().toString().trim());
            adminReg.setUsername(username.getText().toString().trim());
            adminReg.setPassword(password.getText().toString().trim());

            Call<String> call = api.registerAdmin(adminReg);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    String check = response.body();
                    new DialogBox(RegisterAdminActivity.this, check);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    new DialogBox(RegisterAdminActivity.this, "An error has occurred!");
                }
            });
        } catch (Exception e) {
            new DialogBox(RegisterAdminActivity.this, "An error has occurred!");
        }

    }
}