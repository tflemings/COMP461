/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.model;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 *
 * @author Tony
 */
public class CartLine implements Serializable {
    
    private int id;
    private int versionId;
    private Product product;
    private int quantity;
    private double net;
    private double sell;
    private double total;
    
    public CartLine() {
        
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getVersionId() {
        return this.versionId;
    }
    
    public void setVersionId(int id) {
        this.versionId = id;
    }
    
    public Product getProduct() {
        return this.product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public int getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getNet() {
        return this.net;
    }
    
    public void setNet(double net) {
        this.net = net;
    }
    
    public double getSell() {
        return this.sell;
    }
    
    public void setSell(double sell) {
        this.sell = sell;
    }
    
    public double calculateSell() {
        return this.quantity * this.sell;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
    
    public double getTotal() {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(this.calculateSell()));
    }
    
    public int hashCode(int cartId) {
        return this.hashCode() + new Integer(cartId).hashCode();
    }
    
    @Override
    public int hashCode() {
        return this.product.hashCode() + new Integer(this.quantity).hashCode() +
                new Double(this.net).hashCode() + new Double(this.sell).hashCode() +
                new Integer(this.id).hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CartLine other = (CartLine) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.versionId != other.versionId) {
            return false;
        }
        if (this.product != other.product && (this.product == null || !this.product.equals(other.product))) {
            return false;
        }
        if (this.quantity != other.quantity) {
            return false;
        }
        if (Double.doubleToLongBits(this.net) != Double.doubleToLongBits(other.net)) {
            return false;
        }
        if (Double.doubleToLongBits(this.sell) != Double.doubleToLongBits(other.sell)) {
            return false;
        }
        return true;
    }
    
}
