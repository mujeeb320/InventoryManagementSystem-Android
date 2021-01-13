package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.inventorymanagementsystem.API.API;
import com.example.inventorymanagementsystem.API.API_BASE_URL;
import com.example.inventorymanagementsystem.DTO.EmployeeReg;
import com.example.inventorymanagementsystem.DialogBox.DialogBox;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterEmployeeActivity extends AppCompatActivity {

    TextView firstName, lastName, address, phoneNumber, nic, username, password;
    Button registerEmployeeButton;
    API api;
    private String URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);

        firstName=findViewById(R.id.employeeRegister_firstName_input);
        lastName=findViewById(R.id.employeeRegister_lastName_input);
        address= findViewById(R.id.employeeRegister_address_input);
        phoneNumber= findViewById(R.id.employeeRegister_phoneNumber_input);
        nic=findViewById(R.id.employeeRegister_NIC_input);
        username=findViewById(R.id.employeeRegister_username_input);
        password=findViewById(R.id.employeeRegister_password_input);
        registerEmployeeButton=findViewById(R.id.Employee_Register_buttn);

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

        registerEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(firstName.getText().toString())
                        || TextUtils.isEmpty(lastName.getText().toString())
                        || TextUtils.isEmpty(address.getText().toString())
                        || TextUtils.isEmpty(nic.getText().toString())
                        || TextUtils.isEmpty(username.getText().toString())
                        || TextUtils.isEmpty(password.getText().toString())) {
                    new DialogBox(RegisterEmployeeActivity.this, "All fields are mandatory");

                } else {
                    registerEmployee();
                }
            }
        });
    }

    private void registerEmployee() {
        try{
            EmployeeReg employeeReg= new EmployeeReg();
            employeeReg.setFirstName(firstName.getText().toString().trim());
            employeeReg.setLastName(lastName.getText().toString().trim());
            employeeReg.setAddress(address.getText().toString().trim());
            employeeReg.setPhoneNumber(phoneNumber.getText().toString().trim());
            employeeReg.setNic(nic.getText().toString().trim());
            employeeReg.setUsername(username.getText().toString().trim());
            employeeReg.setPassword(password.getText().toString().trim());

            Call<String> call = api.registerEmployee(employeeReg);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    String check = response.body();
                    new DialogBox(RegisterEmployeeActivity.this, check);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    new DialogBox(RegisterEmployeeActivity.this, "An error has occurred!");
                }
            });
        }catch(Exception e ){
            new DialogBox(RegisterEmployeeActivity.this, "An error has occurred!");
        }
    }
}