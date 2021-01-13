package com.example.inventorymanagementsystem.Adapter.Admin;


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
import com.example.inventorymanagementsystem.Entity.Admin;
import com.example.inventorymanagementsystem.R;
import com.example.inventorymanagementsystem.ViewAdminActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AdminViewAdapter extends RecyclerView.Adapter<AdminViewAdapter.ViewHolder> {

   private Context context;
   private List<Admin> adminList;
    API api;
    private String URL;

    public AdminViewAdapter(Context context, List<Admin> adminList){
        this.context=context;
        this.adminList=adminList;

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
    public AdminViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.admin_layout, parent, false);

        return new AdminViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewAdapter.ViewHolder holder, int position) {
        Admin admin= adminList.get(position);
        holder.id.setText("Admin ID: "+ admin.getAdminID());
        holder.name.setText(admin.getFirstName() + " "+ admin.getLastName());
        holder.type.setText("Admin");

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, ViewAdminActivity.class);
                intent.putExtra("user_ID", Integer.toString(admin.getUserID()));
                context.startActivity(intent);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Call<String> call = api.deleteUser(admin.getUserID());

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
        return adminList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id, name, type;
        Button editBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.manage_admin_id);
            name=itemView.findViewById(R.id.manage_admin_name);
            type = itemView.findViewById(R.id.manage_user_type);
            editBtn = itemView.findViewById(R.id.admin_edit_btn);
            deleteBtn = itemView.findViewById(R.id.admin_delete_btn);
        }
    }
}
