/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.model;

import java.io.Serializable;

/**
 *
 * @author Tony
 */
public class Product implements Serializable {
    
    private int productId;
    private int versionId;
    private String productDescription;
    private int onHandQuantity;
    private double productCost;
    private double productList;
    private String imageLocation;
    
    public Product() {
        
    }
    
    public int getProductId() {
        return this.productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public int getVersionId() {
        return this.versionId;
    }
    
    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }
    
    public String getProductDescription() {
        return this.productDescription;
    }
    
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    
    public int getOnHandQuantity() {
        return this.onHandQuantity;
    }
    
    public void setOnHandQuantity(int onHandQuantity) {
        this.onHandQuantity = onHandQuantity;
    }
    
    public double getProductCost() {
        return this.productCost;
    }
    
    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }
    
    public double getProductList() {
        return this.productList;
    }
    
    public void setProductList(double productList) {
        this.productList = productList;
    }
    
    public String getImageLocation() {
        return this.imageLocation;
    }
    
    public void setImageLocation(String location) {
        this.imageLocation = location;
    }
    
    public boolean isQuantityAvailable(int qty) {
        return (qty > this.onHandQuantity ? false : true);
    }
    
    @Override
    public int hashCode() {
        return this.productDescription.hashCode() + new Integer(this.onHandQuantity).hashCode() +
                new Double(this.productCost).hashCode() + new Double(this.productList).hashCode() +
                this.imageLocation.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (this.productId != other.productId) {
            return false;
        }
        if (this.versionId != other.versionId) {
            return false;
        }
        if ((this.productDescription == null) ? (other.productDescription != null) : !this.productDescription.equals(other.productDescription)) {
            return false;
        }
        if (this.onHandQuantity != other.onHandQuantity) {
            return false;
        }
        if (Double.doubleToLongBits(this.productCost) != Double.doubleToLongBits(other.productCost)) {
            return false;
        }
        if (Double.doubleToLongBits(this.productList) != Double.doubleToLongBits(other.productList)) {
            return false;
        }
        if ((this.imageLocation == null) ? (other.imageLocation != null) : !this.imageLocation.equals(other.imageLocation)) {
            return false;
        }
        return true;
    }
}
