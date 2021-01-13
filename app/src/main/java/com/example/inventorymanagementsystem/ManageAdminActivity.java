package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.inventorymanagementsystem.API.API;
import com.example.inventorymanagementsystem.API.API_BASE_URL;
import com.example.inventorymanagementsystem.Adapter.Admin.AdminViewAdapter;
import com.example.inventorymanagementsystem.Entity.Admin;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageAdminActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String URL;
    API api;
    private List<Admin> adminList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_admin);

        API_BASE_URL url = new API_BASE_URL();
        URL=url.API_URL();
        recyclerView= findViewById(R.id.manage_user_recycler);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api=retrofit.create(API.class);

        ListAdmins();
    }

    private void ListAdmins() {
        try{
            Call<List<Admin>> call= api.AllAdmins();

            call.enqueue(new Callback<List<Admin>>() {
                @Override
                public void onResponse(Call<List<Admin>> call, Response<List<Admin>> response) {
                    adminList=response.body();
                    AdminViewAdapter adminViewAdapter= new AdminViewAdapter(ManageAdminActivity.this, adminList);
                    recyclerView.setAdapter(adminViewAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager( ManageAdminActivity.this));
                }
                @Override
                public void onFailure(Call<List<Admin>> call, Throwable t) {
                    Toast.makeText(ManageAdminActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
        }catch(Exception e){
            Toast.makeText(ManageAdminActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}