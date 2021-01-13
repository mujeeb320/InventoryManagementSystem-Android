package com.example.inventorymanagementsystem.Adapter.Supplier;

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
import com.example.inventorymanagementsystem.DialogBox.DialogBox;
import com.example.inventorymanagementsystem.Entity.Supplier;
import com.example.inventorymanagementsystem.R;
import com.example.inventorymanagementsystem.ViewSupplierActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SupplierViewAdapter extends RecyclerView.Adapter<SupplierViewAdapter.ViewHolder> {

    private Context context;
    private List<Supplier> supplierList;
    API api;
    private String URL;


    public SupplierViewAdapter(Context context, List<Supplier> supplierList){
        this.context=context;
        this.supplierList=supplierList;

        API_BASE_URL baseURL = new API_BASE_URL();
        URL = baseURL.API_URL();
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
    public SupplierViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.supplier_layout,parent, false);

        return  new SupplierViewAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SupplierViewAdapter.ViewHolder holder, int position) {
        Supplier supplier= supplierList.get(position);
        holder.id.setText("Supplier ID: "+ supplier.getSupplierID());
        holder.name.setText(supplier.getFirstName()+" "+supplier.getLastName());
        holder.phoneNumber.setText(supplier.getPhoneNumber());
        holder.type.setText("Supplier");
        holder.nic.setText(supplier.getNic());


        holder.viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, ViewSupplierActivity.class);
                intent.putExtra("user_ID",Integer.toString(supplier.getUserID()));
                context.startActivity(intent);

            }
        });


        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Call<String> call = api.DeleteSupplier(supplier.getUserID());

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String message = response.body();
                            new DialogBox(context, message);
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            new DialogBox(context, "An Error Has Occurred!");
                        }
                    });
                } catch (Exception e) {
                    new DialogBox(context, "An Error Has Occurred!");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return supplierList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id, name, phoneNumber, type, nic;
        public Button viewButton, deleteButton;

            public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.manage_supplier_id);
            name=itemView.findViewById(R.id.manage_supplier_name);
            phoneNumber=itemView.findViewById(R.id.manage_supplier_phoneNumber);
            nic=itemView.findViewById(R.id.manage_supplier_nic);
            type=itemView.findViewById(R.id.manage_supplier_type);
            viewButton=itemView.findViewById(R.id.supplier_view);
            deleteButton=itemView.findViewById(R.id.supplier_delete);

        }
    }
}
