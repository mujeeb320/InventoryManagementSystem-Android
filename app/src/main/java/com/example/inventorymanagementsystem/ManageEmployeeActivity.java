package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.inventorymanagementsystem.API.API;
import com.example.inventorymanagementsystem.API.API_BASE_URL;
import com.example.inventorymanagementsystem.Adapter.Employee.EmployeeViewAdapter;
import com.example.inventorymanagementsystem.Entity.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageEmployeeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String URL;
    API api;
    private List<Employee> employeeLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_employee);

        API_BASE_URL url = new API_BASE_URL();
        URL = url.API_URL();
        recyclerView = findViewById(R.id.manage_user_recycler);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);


        ListEmployees();
    }

    private void ListEmployees() {
        try{
            Call<List<Employee>> call = api.AllEmployees();

            call.enqueue(new Callback<List<Employee>>() {
                @Override
                public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                    employeeLists = response.body();
                    EmployeeViewAdapter employeeViewAdapter = new EmployeeViewAdapter(ManageEmployeeActivity.this, employeeLists);
                    recyclerView.setAdapter(employeeViewAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ManageEmployeeActivity.this));
                }
                @Override
                public void onFailure(Call<List<Employee>> call, Throwable t) {
                    Toast.makeText(ManageEmployeeActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                }
            });}
        catch (Exception e)
        {
            Toast.makeText(ManageEmployeeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}