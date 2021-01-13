package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.inventorymanagementsystem.API.API;
import com.example.inventorymanagementsystem.API.API_BASE_URL;
import com.example.inventorymanagementsystem.Adapter.Supplier.SupplierViewAdapter;
import com.example.inventorymanagementsystem.Entity.Supplier;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageSupplierActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String URL;
    API api;
    private List<Supplier> supplierLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_supplier);

        API_BASE_URL url=new API_BASE_URL();
        URL = url.API_URL();
        recyclerView= findViewById(R.id.manage_supplier_recycler);

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api= retrofit.create(API.class);

        ListSupplier();

    }

    private void ListSupplier() {
        try{
            Call<List<Supplier>> call = api.AllSuppliers();

            call.enqueue(new Callback<List<Supplier>>() {
                @Override
                public void onResponse(Call<List<Supplier>> call, Response<List<Supplier>> response) {
                    supplierLists= response.body();
                    SupplierViewAdapter supplierViewAdapter= new SupplierViewAdapter(ManageSupplierActivity.this, supplierLists);
                    recyclerView.setAdapter(supplierViewAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ManageSupplierActivity.this));

                }

                @Override
                public void onFailure(Call<List<Supplier>> call, Throwable t) {
                    Toast.makeText(ManageSupplierActivity.this, "ERROR", Toast.LENGTH_LONG).show();

                }
            });
        }catch (Exception e)
        {
            Toast.makeText(ManageSupplierActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}