package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.inventorymanagementsystem.API.API;
import com.example.inventorymanagementsystem.API.API_BASE_URL;
import com.example.inventorymanagementsystem.Adapter.Admin.AdminViewAdapter;
import com.example.inventorymanagementsystem.DialogBox.DialogBox;
import com.example.inventorymanagementsystem.Entity.Login;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Button test, loginBtn;
    private EditText username,password;
    private String URL;
    API api;
    public SharedPreferences sharedPreferences;
    String remembermepreference = "remembermepreference";
    CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn= (Button)findViewById(R.id.login_buttn);
        test = (Button)findViewById(R.id.test_buttn);
        username= (EditText)findViewById(R.id.login_username_input);
        password= (EditText)findViewById(R.id.login_password_input);
        sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        checkbox= findViewById(R.id.remember_me);

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

        sharedPreferences=getSharedPreferences(remembermepreference,0);
        boolean rememberMe=sharedPreferences.getBoolean("rememberMe", false);

        if(rememberMe== true){
            String userlogin= sharedPreferences.getString("login", null);
            String rememberpass= sharedPreferences.getString("password",null);

            if(userlogin != null && rememberpass !=null ){
                username= (EditText)findViewById(R.id.login_username_input);
                password= (EditText)findViewById(R.id.login_password_input);
                username.setText(userlogin);
                password.setText(rememberpass);
                CheckBox box = findViewById(R.id.remember_me);
                box.setChecked(true);


            }
        }


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isChecked=checkbox.isChecked();

                if(TextUtils.isEmpty(username.getText().toString())||TextUtils.isEmpty(password.getText().toString()))
                {
                    new DialogBox(LoginActivity.this,"Please enter credentials !!");
                }else if(isChecked) {
                    saveDetails();
                    Login();
                } else if(isChecked == false) {
                    deleteDetails();
                    Login();
                } else {
                    Login();
                }
            }
        });

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), ManageProductActivity.class);
                startActivity(intent);
            }
        });
    }

    private void saveDetails() {
        username = findViewById(R.id.login_username_input);
        password = findViewById(R.id.login_password_input);
        String userlogin = username.getText().toString();
        String rememberpass = password.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("rememberMe", true);
        editor.putString("login", userlogin);
        editor.putString("password", rememberpass);
        editor.commit();
    }

    private void deleteDetails() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("rememberMe", false);
        editor.remove("login");
        editor.remove("password");
        editor.commit();
    }

    private void Login() {
        try {
            Login login = new Login(username.getText().toString().trim(), password.getText().toString().trim());

            Call<String> call = api.userLogin(login);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String check = response.body();

                    String[] data = check.split(" ");

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    if (data[0].equals("Invalid")) {
                        new DialogBox(LoginActivity.this, "Invalid Login Credentials!");
                    } else if (data[0].equals("Admin")) {
                        editor.putInt("accountID", Integer.parseInt(data[1]));
                        editor.putString("role", data[0]);
                        editor.putString("username", data[2]);
                        editor.commit();
                        Intent intent = new Intent(getBaseContext(), AdminHomepageActivity.class);
                        startActivity(intent);
                    } else if (data[0].equals("Supplier")) {
                        editor.putInt("accountID", Integer.parseInt(data[1]));
                        editor.putString("role", data[0]);
                        editor.putString("username", data[2]);
                        editor.commit();
                        Intent intent = new Intent(getBaseContext(), SupplierHomepageActivity.class);
                        startActivity(intent);
                    } else if (data[0].equals("Employee")) {
                        editor.putInt("accountID", Integer.parseInt(data[1]));
                        editor.putString("role", data[0]);
                        editor.putString("username", data[2]);
                        editor.commit();
                        Intent intent = new Intent(getBaseContext(), EmployeeHomepageActivity.class);
                        startActivity(intent);
                    } else {
                        new DialogBox(LoginActivity.this, "An Error Has Occurred!");
                    }


                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    new DialogBox(LoginActivity.this, "An Error Has Occurred!");
                }
            });
        } catch (Exception e) {
            new DialogBox(LoginActivity.this, "An Error Has Occurred!");
        }
    }
}