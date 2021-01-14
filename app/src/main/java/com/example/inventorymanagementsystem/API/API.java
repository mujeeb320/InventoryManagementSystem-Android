package com.example.inventorymanagementsystem.API;

import com.example.inventorymanagementsystem.DTO.AdminReg;
import com.example.inventorymanagementsystem.DTO.EmployeeReg;
import com.example.inventorymanagementsystem.DTO.ProductReg;
import com.example.inventorymanagementsystem.DTO.SupplierReg;
import com.example.inventorymanagementsystem.Entity.Admin;
import com.example.inventorymanagementsystem.Entity.Employee;
import com.example.inventorymanagementsystem.Entity.Login;
import com.example.inventorymanagementsystem.Entity.Product;
import com.example.inventorymanagementsystem.Entity.Supplier;
import com.example.inventorymanagementsystem.Entity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface API {
    //=======================================ADMIN FUNCTIONS=======================================\\

    @GET("api/admin/allEmployees")
    Call<List<Employee>> AllEmployees();      //View All Employee

    @GET("api/admin/allAdmins")
    Call<List<Admin>> AllAdmins();                                 //View All admins

    @GET("api/admin/allSuppliers")
    Call<List<Supplier>> AllSuppliers();                             //View All Suppliers

    @GET("api/product/allproducts")
    Call<List<Product>> viewAllProducts();                             //View All Products

    @POST("api/user/login")
    Call<String> userLogin(@Body Login login);                        //Login

    @POST("api/user/saveSupplier")
    Call<String> registerSupplier(@Body SupplierReg supplierReg);      //Register Supplier

    @POST("api/user/saveAdmin")
    Call<String> registerAdmin(@Body AdminReg adminReg);                //Register Admin

    @POST("api/user/registerNewEmployee")
    Call<String> registerEmployee(@Body EmployeeReg employeeReg);       //Register Employee

    @GET("api/admin/viewUserDetails/{user_ID}")
    Call<User>ViewUser(@Path("user_ID")int id);                          //View Admin User Details

    @POST("api/admin/editAdmin")
    Call<String>updateAdmin(@Body User user);                           //Update Admin Details


    @GET("api/admin/deleteUser/{user_ID}")
    Call<String>deleteUser(@Path("user_ID")int id);                           //Delete Admin

    @GET("api/admin/viewSupplierUserDetails/{user_ID}")
    Call<User> ViewSupplierUser(@Path("user_ID") int id);                     //View Admin User Details

    @POST("api/admin/updateSupplier")
    Call<String>updateSupplier(@Body User user);                           //Update Supplier Details

    @GET("api/admin/deleteSupplier/{user_ID}")
    Call<String>DeleteSupplier(@Path("user_ID")int id);                           //Delete Supplier

    @GET("api/admin/viewEmployeeUserDetails/{user_ID}")
    Call<User> ViewEmployeeUser(@Path("user_ID") int id);                     //View Employee User Details

    @POST("api/admin/updateEmployee")
    Call<String>updateEmployee(@Body User user);                           //Update Employee Details

    @GET("api/admin/deleteSupplier/{user_ID}")
    Call<String>DeleteEmployee(@Path("user_ID")int id);                           //Delete Employee

    @GET("api/product/viewProductDetails/{id}")
    Call<Product> ViewProductDetails(@Path("id") int id);                     //View Product Details

    @POST("api/product/updateProduct")
    Call<String>updateProduct(@Body Product product);                           //Update Product Details

    @GET("api/product/deleteProduct/{id}")
    Call<String>deleteProduct(@Path("id")int id);                           //Delete Product Details

    @POST("api/product/saveProduct")
    Call<String> saveProduct(@Body ProductReg productReg);               //Register Product



}
