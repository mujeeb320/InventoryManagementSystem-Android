package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.inventorymanagementsystem.API.API;
import com.example.inventorymanagementsystem.API.API_BASE_URL;
import com.example.inventorymanagementsystem.DialogBox.DialogBox;
import com.example.inventorymanagementsystem.Entity.User;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ViewEmployeeActivity extends AppCompatActivity {

    API api;
    private String URL;
    private int user_ID;
    private EditText firstName, lastName, address, nic, phoneNumber;
    private TextView id;
    private Button update;
    private User viewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);

        id = findViewById(R.id.view_employee_user_ID);
        firstName = findViewById(R.id.view_employee_first_name);
        lastName = findViewById(R.id.view_employee_last_name);
        address = findViewById(R.id.view_employee_address);
        phoneNumber = findViewById(R.id.view_employee_phone_number);
        nic = findViewById(R.id.view_employee_nic);
        update = findViewById(R.id.update_employee_btn);

        API_BASE_URL baseURL = new API_BASE_URL();
        URL = baseURL.API_URL();
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(httpClient.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
        user_ID = Integer.parseInt(getIntent().getStringExtra("user_ID"));

        loadEmployee();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(firstName.getText().toString()) ||
                        TextUtils.isEmpty(lastName.getText().toString()) ||
                        TextUtils.isEmpty(address.getText().toString()) ||
                        TextUtils.isEmpty(phoneNumber.getText().toString()) ||
                        TextUtils.isEmpty(nic.getText().toString())) {
                    new DialogBox(ViewEmployeeActivity.this, "All Fields Are Mandatory!");
                } else {

                    updateEmployee();
                }
            }


        });

    }
    private void loadEmployee() {
        try {
            Call<User> call = api.ViewEmployeeUser(user_ID);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    viewUser = user;
                    id.setText(Integer.toString(user.getUserID()));
                    firstName.setText(user.getFirstName());
                    lastName.setText(user.getLastName());
                    address.setText(user.getAddress());
                    phoneNumber.setText(user.getPhoneNumber());
                    nic.setText(user.getNic());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    new DialogBox(ViewEmployeeActivity.this, "Error 1 !");
                }
            });
        }catch(Exception e){
            new DialogBox(ViewEmployeeActivity.this, "Error 2 !");
        }
    }



    private void updateEmployee() {
        try{
            viewUser.setUserID(Integer.parseInt(id.getText().toString()));
            viewUser.setFirstName(firstName.getText().toString());
            viewUser.setLastName(lastName.getText().toString());
            viewUser.setAddress(address.getText().toString());
            viewUser.setPhoneNumber(phoneNumber.getText().toString());
            viewUser.setNic(nic.getText().toString());
            Call<String> call = api.updateEmployee(viewUser);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    new DialogBox(ViewEmployeeActivity.this, "Admin Details Updated!");

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    new DialogBox(ViewEmployeeActivity.this, "Error 3");
                }
            });
        }catch(Exception e ){
            new DialogBox(ViewEmployeeActivity.this, "Error 4!");
        }
    }



}
