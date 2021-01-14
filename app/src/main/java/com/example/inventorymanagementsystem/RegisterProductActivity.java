package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.inventorymanagementsystem.API.API;
import com.example.inventorymanagementsystem.API.API_BASE_URL;
import com.example.inventorymanagementsystem.DTO.ProductReg;
import com.example.inventorymanagementsystem.DialogBox.DialogBox;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegisterProductActivity extends AppCompatActivity {

    TextView name, quantity, price;
    Button addProductButton;
    API api;
    private String URL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_product);

        name=findViewById(R.id.register_product__name_input);
        quantity=findViewById(R.id.register_product__quantity_input);
        price=findViewById(R.id.register_product__price_input);
        addProductButton = findViewById(R.id.register_product_button);


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

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString())
                        || TextUtils.isEmpty(quantity.getText().toString())
                        || TextUtils.isEmpty(price.getText().toString())){
                    new DialogBox(RegisterProductActivity.this, "All fields are mandatory");
                }
                registerProduct();
            }
        });
    }

    private void registerProduct() {
        try{
            ProductReg productReg=new ProductReg();
            productReg.setName(name.getText().toString().trim());
            productReg.setPrice(Double.parseDouble(price.getText().toString().trim()));
            productReg.setPrice(Integer.parseInt(quantity.getText().toString()));

            Call<String> call = api.saveProduct(productReg);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    String check= response.body();
                    new DialogBox(RegisterProductActivity.this, check);

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    new DialogBox(RegisterProductActivity.this, "An error has occurred!");
                }
            });
        }catch (Exception e){
            new DialogBox(RegisterProductActivity.this, "An error has occurred!");
        }
    }
}