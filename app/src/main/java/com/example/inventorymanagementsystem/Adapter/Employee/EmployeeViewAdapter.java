package com.example.inventorymanagementsystem.Adapter.Employee;

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
import com.example.inventorymanagementsystem.Entity.Employee;
import com.example.inventorymanagementsystem.R;
import com.example.inventorymanagementsystem.ViewEmployeeActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EmployeeViewAdapter extends RecyclerView.Adapter<EmployeeViewAdapter.ViewHolder> {

    private Context context;
    private List<Employee> employeeLists;
    API api;
    private String URL;

    public EmployeeViewAdapter(Context context, List<Employee> employeeLists){
        this.context=context;
        this.employeeLists=employeeLists;

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.employee_layout, parent, false);

        return new EmployeeViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewAdapter.ViewHolder holder, int position) {

        Employee employee= employeeLists.get(position);
        holder.id.setText("Employee ID : " + employee.getEmployeeID());
        holder.name.setText(employee.getFirstName() + " " + employee.getLastName());
        holder.type.setText("Employee");
        holder.phoneNumber.setText(employee.getPhoneNumber());
        holder.nic.setText(employee.getNIC());

        holder.viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(context, ViewEmployeeActivity.class);
                intent.putExtra("user_ID", Integer.toString(employee.getUserID()));
                context.startActivity(intent);
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Call<String> call = api.DeleteEmployee(employee.getUserID());

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String message = response.body();
                            new DialogBox(context, message);
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            new DialogBox(context, "Error ! 3 ");
                        }
                    });
                }catch (Exception e){
                    new DialogBox(context, "An Error Has Occurred! 4 ");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return employeeLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView id, name, type, phoneNumber,nic;
        Button viewBtn, deleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.manage_employee_id);
            name = itemView.findViewById(R.id.manage_employee_name);
            phoneNumber = itemView.findViewById(R.id.manage_employee_phoneNumber);
            type = itemView.findViewById(R.id.manage_employee_type);
            nic=itemView.findViewById(R.id.manage_employee_nic);
            viewBtn = itemView.findViewById(R.id.employee_view);
            deleteBtn = itemView.findViewById(R.id.employee_delete);
        }
    }
}
