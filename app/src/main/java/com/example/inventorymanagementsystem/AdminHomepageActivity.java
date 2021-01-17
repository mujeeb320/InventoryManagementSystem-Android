package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AdminHomepageActivity extends AppCompatActivity {

    ImageView viewadmin, viewemployee, viewsupplier, addadmin, addsupplier, addemployee, viewproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_homepage);

        viewadmin=(ImageView) findViewById(R.id.view_admin_icon);
        viewemployee=(ImageView) findViewById(R.id.view_employee_icon);
        viewsupplier=(ImageView) findViewById(R.id.view_supplier_icon);
        addadmin=(ImageView) findViewById(R.id.add_admin_icon);
        addsupplier=(ImageView) findViewById(R.id.add_supplier_icon);
        addemployee=(ImageView) findViewById(R.id.add_Employee_icon);
        viewproduct=(ImageView) findViewById(R.id.view_product_icon);

        viewadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ManageAdminActivity.class);
                startActivity(intent);
            }
        });

        viewemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ManageEmployeeActivity.class);
                startActivity(intent);
            }
        });

        viewsupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ManageSupplierActivity.class);
                startActivity(intent);
            }
        });

        addadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterAdminActivity.class);
                startActivity(intent);
            }
        });

        addemployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterEmployeeActivity.class);
                startActivity(intent);
            }
        });

        addsupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterSupplierActivity.class);
                startActivity(intent);
            }
        });
        viewproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ManageProductActivity.class);
                startActivity(intent);
            }
        });

    }

}