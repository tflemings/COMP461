/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.model.OrderLine;
import com.commerce.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tony
 */
public class ProductDAO {
    
    private static final String QOH = "select quantityonhand from product where "
            + "productid = ?";
    private static final String INSERT = "insert into product values ("
            + "?, ?, ?, ?, ?, ?, ?) on duplicate key update versionId = ?, "
            + "productdescription = ?, quantityonhand = ?, netprice = ?, "
            + "sellprice = ?, image = ?";
    
    private DatabaseController dbh;
    
    public ProductDAO() {
        try {
            this.dbh = new DatabaseController();
        } catch (SQLException e) {
            
        } 
    }
    
    public ArrayList<Product> readAllProducts() {
        String sql = "select * from product";
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            PreparedStatement sth = this.getDbh().getCon().prepareStatement(sql);
            ResultSet result = sth.executeQuery();
            while (result.next()) {
                Product product = new Product();
                product.setImageLocation(result.getString("image"));
                product.setProductId(result.getInt("productid"));
                product.setProductDescription(result.getString("productdescription"));
                product.setOnHandQuantity(result.getInt("quantityonhand"));
                product.setProductCost(result.getDouble("netprice"));
                product.setProductList(result.getDouble("sellprice"));
                products.add(product);
            }
        } catch (SQLException e) {
            
        } finally {
            dbh.closeConnection();
        }
        return products;
        
    }
    
    public Product readProductById(int id) {
        String sql = "select * from product where productid = ?";
        try {
            PreparedStatement sth = this.getDbh().getCon().prepareStatement(sql);
            sth.setInt(1, id);
            ResultSet result = sth.executeQuery();
            if (result.next()) {
                Product product = new Product();
                product.setImageLocation(result.getString("image"));
                product.setOnHandQuantity(result.getInt("quantityonhand"));
                product.setProductCost(result.getDouble("netprice"));
                product.setProductList(result.getDouble("sellprice"));
                product.setProductId(result.getInt("productid"));
                product.setVersionId(result.getInt("versionId"));
                product.setProductDescription(result.getString("productdescription"));
                return product;
            }
        } catch(SQLException e) {
            return null;
        }
        return null;
    }
    
    public int quantitiesValid(Connection conn, ArrayList<OrderLine> lines) {
        try {
            PreparedStatement sth = conn.prepareStatement(QOH);
            for (int i = 0; i < lines.size(); i++) {
                
                int orderedQuantity = lines.get(i).getQuantity();
                sth.setInt(1, lines.get(i).getProduct().getProductId());
                ResultSet result = sth.executeQuery();
                if (result.next()) {
                    int qoh = result.getInt("quantityonhand");
                    if (orderedQuantity > qoh) {
                        return i;
                    }
                }
            }
        } catch (SQLException e) {
            return -1;
        }
        return lines.size();
    }
    
    public DatabaseController getDbh() {
        return this.dbh;
    }
    
    public String initProducts(ArrayList<Product> products) {
        Connection conn = this.dbh.getCon();
        try {
            conn.setAutoCommit(false);
            PreparedStatement sth = conn.prepareStatement(INSERT);          
            for (Product p : products) {
                sth.setInt(1, p.getProductId());
                sth.setInt(2, p.getVersionId());
                sth.setString(3, p.getProductDescription());
                sth.setInt(4, p.getOnHandQuantity());
                sth.setDouble(5, p.getProductCost());
                sth.setDouble(6, p.getProductList());
                sth.setString(7, p.getImageLocation());
                sth.setInt(8, p.getVersionId());
                sth.setString(9, p.getProductDescription());
                sth.setInt(10, p.getOnHandQuantity());
                sth.setDouble(11, p.getProductCost());
                sth.setDouble(12, p.getProductList());
                sth.setString(13, p.getImageLocation());
                sth.executeUpdate();
            }
            OrderDAO o_dao = new OrderDAO();
            o_dao.clearOrders(conn);
            ShoppingCartDAO c_dao = new ShoppingCartDAO();
            c_dao.clearCarts(conn);
            conn.commit();
            return "Database Reset";
        } catch (SQLException e) {
            try {
                conn.rollback();
                return "Rolled back. " + e.getMessage();
            } catch (SQLException ex) {
                return ex.getMessage();
            }
            
        } finally {
            try {
                conn.setAutoCommit(true);
                this.dbh.closeConnection();
            } catch (SQLException exception) {
                return exception.getMessage();
            }
        }
        
    }
}
