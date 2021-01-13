package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.inventorymanagementsystem.API.API;
import com.example.inventorymanagementsystem.API.API_BASE_URL;
import com.example.inventorymanagementsystem.Adapter.Product.ProductViewAdapter;
import com.example.inventorymanagementsystem.Adapter.Supplier.SupplierViewAdapter;
import com.example.inventorymanagementsystem.Entity.Product;
import com.example.inventorymanagementsystem.Entity.Supplier;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String URL;
    API api;
    private List<Product> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);

        API_BASE_URL url=new API_BASE_URL();
        URL = url.API_URL();
        recyclerView= findViewById(R.id.manage_Product_recycler);

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api= retrofit.create(API.class);

        ListProduct();
    }

    private void ListProduct() {

        try {
            Call<List<Product>> call = api.viewAllProducts();

            call.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                    productList= response.body();
                    ProductViewAdapter productViewAdapter= new ProductViewAdapter(ManageProductActivity.this, productList);
                    recyclerView.setAdapter(productViewAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ManageProductActivity.this));

                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    Toast.makeText(ManageProductActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(ManageProductActivity.this, "ERROR", Toast.LENGTH_LONG).show();
        }
    }
}