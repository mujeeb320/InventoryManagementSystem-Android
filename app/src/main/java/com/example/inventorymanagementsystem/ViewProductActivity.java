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
import com.example.inventorymanagementsystem.Entity.Product;
import com.example.inventorymanagementsystem.Entity.User;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ViewProductActivity extends AppCompatActivity {

    API api;
    private String URL;
    private int product_ID;
    private EditText name, price, quantity;
    private TextView id;
    private Button update;
    private Product viewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        id=findViewById(R.id.view_product_id);
        name= findViewById(R.id.view_product_price);
        quantity=findViewById(R.id.view_product_quantity);
        price=findViewById(R.id.view_product_price);
        update=findViewById(R.id.update_product_btn);

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

        product_ID = Integer.parseInt(getIntent().getStringExtra("product_ID"));

        loadProduct();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString()) ||
                        TextUtils.isEmpty(quantity.getText().toString()) ||
                        TextUtils.isEmpty(price.getText().toString())) {
                    new DialogBox(ViewProductActivity.this, "All Fields Are Mandatory!");
                } else {
                    updateProduct();
                }
            }
        });
    }


    private void loadProduct() {
        try {
            Call<Product> call = api.ViewProductDetails(product_ID);
            call.enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    Product product=response.body();
                    viewProduct= product;
                    id.setText(Integer.toString(product.getId()));
                    name.setText(product.getName());
                    quantity.setText(product.getQuantity());
                    price.setText(product.getPrice());

                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    new DialogBox(ViewProductActivity.this, "Error 1 !");
                }
            });


        }catch(Exception e){
            new DialogBox(ViewProductActivity.this, "Error 2 !");
        }
    }
    private void updateProduct() {
        try{
            viewProduct.setId(Integer.parseInt(id.getText().toString()));
            viewProduct.setName(name.getText().toString());
            viewProduct.setQuantity(quantity.getText().toString());
            viewProduct.setPrice(price.getText().toString());
            Call<String> call= api.updateProduct(viewProduct);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    new DialogBox(ViewProductActivity.this, "Products Details Updated!");
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    new DialogBox(ViewProductActivity.this, "Error 3!");
                }
            });
        }catch (Exception e){
            new DialogBox(ViewProductActivity.this, "Error 4!");
        }
    }
}