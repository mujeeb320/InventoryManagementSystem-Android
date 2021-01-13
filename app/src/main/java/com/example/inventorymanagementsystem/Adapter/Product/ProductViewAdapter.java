package com.example.inventorymanagementsystem.Adapter.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.inventorymanagementsystem.Adapter.Supplier.SupplierViewAdapter;
import com.example.inventorymanagementsystem.Entity.Product;
import com.example.inventorymanagementsystem.Entity.Supplier;
import com.example.inventorymanagementsystem.R;

import java.util.List;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ViewHolder> {

    private Context context;
    private List<Product> productList;

    public ProductViewAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
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

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, quantity, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.product_id);
            name=itemView.findViewById(R.id.product_name);
            price=itemView.findViewById(R.id.product_price);
            quantity=itemView.findViewById(R.id.product_quantity);
        }
    }
}
