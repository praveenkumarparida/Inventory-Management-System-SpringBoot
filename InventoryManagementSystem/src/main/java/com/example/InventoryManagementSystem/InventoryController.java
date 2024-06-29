package com.example.InventoryManagementSystem;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class InventoryController {
    String jdbcURL="jdbc:mysql://127.0.0.1:3306/invo";
//    ADMIN

    @GetMapping("/registerAdmin")
    public String registerAdmin(){
        return "admin/register_admin";
    }
    @PostMapping("/admin/addDBAdmin")
    public String registerDBAdmin(@RequestParam("button") String button,@RequestParam("fullname") String fullname,@RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("password") String password){
        System.out.println("Admin registered");
        Connection con=null;
        try {
            con= DriverManager.getConnection(jdbcURL,"root","root");
            String sql="INSERT INTO Users VALUES(?,?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,fullname);
            ps.setString(3,email);
            ps.setString(4,password);
            ps.setString(5,"admin");
            ps.execute();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return "user/login";
    }
    @GetMapping("/admin/Admin_dashBoard")
    public String adminDashboard(){
        return "admin/Admin_dashBoard";
    }
    @GetMapping("/admin/user")
    public String viewUsers(Model model){
        System.out.println("View db users");
        List<Map<String,Object>> data=fetchUsers();
        model.addAttribute("dataList",data);
        return "admin/user";
    }
    public List<Map<String,Object>>fetchUsers(){
        List<Map<String,Object>>data=new ArrayList<>();
        Connection con=null;
        try {
            con= DriverManager.getConnection(jdbcURL,"root","root");
            String sql="select * from users where role = 'user'";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Map<String,Object>map=new HashMap<>();
                map.put("username",rs.getString("username"));
                map.put("fullname",rs.getString("fullname"));
                map.put("email",rs.getString("email"));
                data.add(map);
            }
            System.out.println("Users: "+data);
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return data;
    }
    @GetMapping("/admin/viewProd")
    public String viewProduct(Model model){
        System.out.println("View db product");
        List<Map<String,Object>> data=fetchProducts();
        model.addAttribute("dataList",data);
        return "admin/viewProd";
    }
    @ModelAttribute("dataList")
    public List<Map<String,Object>>fetchProducts(){
        List<Map<String,Object>>data=new ArrayList<>();
        Connection con=null;
        try {
            con= DriverManager.getConnection(jdbcURL,"root","root");
            String sql="select * from products";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Map<String,Object>map=new HashMap<>();
                map.put("id",rs.getInt("id"));
                map.put("name",rs.getString("name"));
                map.put("description",rs.getString("description"));
                map.put("quantity",rs.getString("quantity"));
                map.put("price",rs.getDouble("price"));
                data.add(map);
            }
            System.out.println("Products: "+data);
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return data;
    }
    @GetMapping("/admin/addproduct")
    public String addProduct(){
        return "admin/addproduct";
    }
//    @RequestParam("button") String button,@RequestParam("ProdName") String productName,@RequestParam("proprice") int productPrice,@RequestParam("prodec") String productDesc,@RequestParam("proQuantity") int productQuantity
    @PostMapping("/admin/addDBProduct")
    public String addDbPrdouct(@RequestParam("button") String button,@RequestParam("ProdName") String productName,@RequestParam("proprice") int productPrice,@RequestParam("prodec") String productDesc,@RequestParam("proQuantity") int productQuantity){
        System.out.println("ADD db product");
        Connection con=null;
        try {
            con= DriverManager.getConnection(jdbcURL,"root","root");
            String sql="INSERT INTO Products VALUES(?,?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,0);
            ps.setString(2,productName);
            ps.setString(3,productDesc);
            ps.setInt(4,productQuantity);
            ps.setDouble(5,productPrice);
            ps.execute();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return "/admin/addproduct";
    }





// USERS
    @PostMapping("/user/addDBUser")
    public String registerDBUser(@RequestParam("button") String button,@RequestParam("fullname") String fullname,@RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("password") String password){
        System.out.println("User registered");
        Connection con=null;
        try {
            con= DriverManager.getConnection(jdbcURL,"root","root");
            String sql="INSERT INTO Users VALUES(?,?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,fullname);
            ps.setString(3,email);
            ps.setString(4,password);
            ps.setString(5,"user");
            ps.execute();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return "user/login";
    }
    @GetMapping("/")
    public String initial(){
        return "user/login";
    }
    @GetMapping("user/register")
    public String registerUser(){
        return "user/register";
    }

    @GetMapping("/user/dashboard")
    public String getProducts(Model model){
        System.out.println("View db products to the user");
        List<Map<String,Object>> data=fetchProductsForUser();
        model.addAttribute("dataList",data);
        return "user/dashboard";
    }
    @ModelAttribute("dataList")
    public List<Map<String,Object>>fetchProductsForUser(){
        List<Map<String,Object>>data=new ArrayList<>();
        Connection con=null;
        try {
            con= DriverManager.getConnection(jdbcURL,"root","root");
            String sql="select * from products";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Map<String,Object>map=new HashMap<>();
                map.put("id",rs.getInt("id"));
                map.put("name",rs.getString("name"));
                map.put("description",rs.getString("description"));
//                map.put("quantity",rs.getString("quantity"));
                map.put("price",rs.getDouble("price"));
                data.add(map);
            }
            System.out.println("Products: "+data);
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return data;
    }
//    -------------------------------------------------------------------------------------
//    @PostMapping("/user/cartDetailsInput")
//    public String getOrderFromCart(Model model,@RequestParam("qua") int quantity){
//        System.out.println("Cart products to the user");
//        List<Map<String,Object>> data=fetchCartProductsForUser(quantity);
//        model.addAttribute("cartList",data);
//        return "user/order";
//    }
//    @ModelAttribute("cartList")
//    public List<Map<String,Object>>fetchCartProductsForUser(int quantity){
//        List<Map<String,Object>>data=new ArrayList<>();
//        Connection con=null;
//        try {
//            con= DriverManager.getConnection(jdbcURL,"root","root");
//            String sql="select * from carts";
//            PreparedStatement ps=con.prepareStatement(sql);
//            ResultSet rs=ps.executeQuery();
//            while (rs.next()){
//                Map<String,Object>map=new HashMap<>();
//                map.put("id",rs.getInt("id"));
//                map.put("name",rs.getString("name"));
//                map.put("price",rs.getDouble("price"));
//                map.put("quantity",quantity);
//                map.put("totalPrice",quantity*rs.getDouble("price"));
//                data.add(map);
//            }
//            System.out.println("Products: "+data);
//            con.close();
//        }catch (Exception e){
//            System.out.println(e);
//        }
//        return data;
//    }
    @GetMapping("/user/cart")
    public String getCart(Model model){
        System.out.println("View db cart");
        List<Map<String,Object>> data=showCartProducts();
        model.addAttribute("cartList",data);
        return "user/cart";
    }
    @ModelAttribute("cartList")
    public List<Map<String,Object>>showCartProducts(){
        List<Map<String,Object>>data=new ArrayList<>();
        Connection con=null;
        try {
            con= DriverManager.getConnection(jdbcURL,"root","root");
            String sql="select * from carts";
            PreparedStatement ps=con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                Map<String,Object>map=new HashMap<>();
                map.put("id",rs.getInt("id"));
                map.put("name",rs.getString("name"));
                map.put("price",rs.getDouble("price"));
//                map.put("quantity",quantity);
//                map.put("totalPrice",quantity*rs.getDouble("price"));
                data.add(map);
            }
            System.out.println("Carts: "+data);
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return data;
    }
    @PostMapping("/user/checkUser")
    public String checkUser(@RequestParam("username") String username,@RequestParam("password") String password){
        System.out.println("User check "+username);
        Connection con=null;
        try {
            con= DriverManager.getConnection(jdbcURL,"root","root");
            String sql="Select password,role from users where username = ?";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,username);
            ResultSet rs=ps.executeQuery();
            rs.next();
            String dbPassword=rs.getString("password");
            String dbRole=rs.getString("role");
            if(password.equals(dbPassword)){
                if(dbRole.equals("user")){
                    return "/user/dashboard";
                }
                if(dbRole.equals("admin")){
                    return "/admin/Admin_dashboard";
                }
            }
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return "user/login";
    }
    @PostMapping("/user/addToCart")
    public String addToCart(@RequestParam("addToCart") String cartButton){
        System.out.println("Add to cart button: "+cartButton);
        cartButton=cartButton.substring(1,cartButton.length()-1);
        String datas[]=cartButton.split(",");
        int id=0;
        String description="",name="";
        double price=0;
        for(String s:datas){
            String keyValue[]=s.trim().split("=");
            if(keyValue[0].equals("id")){
                id=Integer.parseInt(keyValue[1]);
            }
            else if(keyValue[0].equals("name")){
                name=keyValue[1];
            }
            else if(keyValue[0].equals("description")){
                description=keyValue[1];
            }
            else if(keyValue[0].equals("price")){
                price=Double.parseDouble(keyValue[1]);
            }
        }
        Connection con=null;
        try {
            con= DriverManager.getConnection(jdbcURL,"root","root");
            String sql="INSERT INTO Carts VALUES(?,?,?,?,?)";
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.setString(2,name);
            ps.setDouble(3,price);
            ps.setInt(4,0);
            ps.setDouble(5,0);
            ps.execute();
            con.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return "user/dashboard";
    }
    @PostMapping("/user/cartDetailsInput")
    public String updateCart(@RequestParam("button") String button,@RequestParam("qua") int quantity){
        return "";
    }
    @GetMapping("/user/order")
    public String getOrders(){
        return "user/order";
    }

}
