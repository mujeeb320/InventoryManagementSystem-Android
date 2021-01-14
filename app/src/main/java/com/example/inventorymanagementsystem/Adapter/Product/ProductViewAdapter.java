package com.example.inventorymanagementsystem.Adapter.Product;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagementsystem.API.API;
import com.example.inventorymanagementsystem.API.API_BASE_URL;
import com.example.inventorymanagementsystem.Adapter.Supplier.SupplierViewAdapter;
import com.example.inventorymanagementsystem.DialogBox.DialogBox;
import com.example.inventorymanagementsystem.Entity.Product;
import com.example.inventorymanagementsystem.Entity.Supplier;
import com.example.inventorymanagementsystem.R;
import com.example.inventorymanagementsystem.ViewAdminActivity;
import com.example.inventorymanagementsystem.ViewProductActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ViewHolder> {

    private Context context;
    private List<Product> productList;
    API api;
    private String URL;

    public ProductViewAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;

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
    }

    @NonNull
    @Override
    public ProductViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.product_layout,parent, false);

        return  new ProductViewAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewAdapter.ViewHolder holder, int position) {

        Product product=productList.get(position);
        holder.id.setText("Product ID: " + product.getId());
        holder.name.setText("Product Name: "+product.getName());
        holder.quantity.setText("Product Quantity: "+ product.getQuantity());
        holder.price.setText("Product price: "+ product.getPrice());

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, ViewProductActivity.class);
                intent.putExtra("product_ID", Integer.toString(product.getId()));
                context.startActivity(intent);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Call<String> call = api.deleteProduct(product.getId());

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String message = response.body();
                            new DialogBox(context, message);
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            new DialogBox(context, "Error 1 !");
                        }
                    });
                }catch (Exception e){
                    new DialogBox(context, "Error 2 !");
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, quantity, price;
        Button editBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.product_id);
            name=itemView.findViewById(R.id.product_name);
            price=itemView.findViewById(R.id.product_price);
            quantity=itemView.findViewById(R.id.product_quantity);
            editBtn = itemView.findViewById(R.id.product_view);
            deleteBtn = itemView.findViewById(R.id.product_delete);
        }
    }
}
