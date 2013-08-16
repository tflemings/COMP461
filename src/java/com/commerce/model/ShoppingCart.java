/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Tony
 */
public class ShoppingCart implements Serializable {
    
    private int id;
    private int versionId;
    private ArrayList<CartLine> shoppingCartLines;
    private String creationDate;
    private double net;
    
    public ShoppingCart() {
        this.shoppingCartLines = new ArrayList<CartLine>();
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
    
    public ArrayList<CartLine> getShoppingCartLines() {
        return this.shoppingCartLines;
    }
    
    public void setShoppingCartLines(ArrayList<CartLine> lines) {
        this.shoppingCartLines = lines;
    }
    
    public String getCreationDate() {
        return this.creationDate;
    }
    
    public void setCreationDate(String date) {
        this.creationDate = date;
    }
    
    public double calculateSell() {
        double sell = 0;
        for (int i = 0; i < this.shoppingCartLines.size(); i++) {
            sell += this.shoppingCartLines.get(i).calculateSell();
        }
        return sell;
    }
    
    public void addCartLine(CartLine cartLine) {
        this.shoppingCartLines.add(cartLine);
    }
    
    public void deleteCartLine(CartLine cartLine) {
        this.shoppingCartLines.remove(cartLine);
    }
    
    public CartLine getCartLineByProductId(int id) {
        for (CartLine cl : this.shoppingCartLines) {
            if (cl.getProduct().getProductId() == id) {
                return cl;
            }
        }
        return null;
    }
    
    public void setNet(Double net) {
        this.net = net;
    }
    
    public double getNet() {
        return this.calculateSell();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ShoppingCart other = (ShoppingCart) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.versionId != other.versionId) {
            return false;
        }
        if (this.shoppingCartLines != other.shoppingCartLines && (this.shoppingCartLines == null || !this.shoppingCartLines.equals(other.shoppingCartLines))) {
            return false;
        }
        if ((this.creationDate == null) ? (other.creationDate != null) : !this.creationDate.equals(other.creationDate)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hashCode = 0;
        for (CartLine cl : this.shoppingCartLines) {
            hashCode += cl.hashCode();
        }
        return this.creationDate.hashCode() + hashCode + 
                new Integer(this.id).hashCode();
    }
}
