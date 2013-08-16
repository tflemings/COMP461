/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Tony
 */
public class Order implements Serializable{
    
    private int id;
    private int versionId;
    private String date;
    private static final double TAXRATE = .065;
    private static final double SHIPPINGRATE = .1;
    private ArrayList<OrderLine> orderLines;
    
    private double net;
    private double tax;
    private double shipping;
    private double total;
    
    public Order() {
        
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
    
    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }
    
    public String getDate() {
        return this.date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }
    
    public ArrayList<OrderLine> getOrderLines() {
        return this.orderLines;
    }
    
    public void setOrderLines(ArrayList<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }
    
    public void setNet(double net) {
        this.net = net;
    }
    
    public double getNet() {
        return this.net;
    }
    
    public void setTax(double tax) {
        this.tax = tax;
    }
    
    public double getTax() {
        return this.tax;
    }
    
    public void setShipping(double shipping) {
        this.shipping = shipping;
    }
    
    public double getShipping() {
        return this.shipping;
    }
    
    public void setTotal(double total) {
        this.total = total;
    }
    
    public double getTotal() {
        return this.total;
    }
    
    public void createOrderFromShoppingCart(ShoppingCart cart) {
        this.id = cart.getId();
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.orderLines = new ArrayList<OrderLine>();
        for (CartLine cl : cart.getShoppingCartLines()) {
            OrderLine ol = new OrderLine();
            ol.setId(cl.getId());
            ol.setNet(cl.getNet());
            ol.setProduct(cl.getProduct());
            ol.setQuantity(cl.getQuantity());
            ol.setSell(cl.getSell());
            ol.setTotal(ol.calculateTotal());
            ol.setVersionId(ol.hashCode(this.id));
            this.addOrderLine(ol);
        }
        this.setVersionId(this.hashCode());
        this.setNet(this.calculateSell());
        this.setTax(this.calculateTax());
        this.setShipping(this.calculateShipping());
        this.setTotal(this.calculateTotal());
    }
    
    public double calculateSell() {
        double sum = 0;
        for (int i = 0; i < this.orderLines.size(); i++) {
            sum += this.orderLines.get(i).getQuantity() * 
                    this.orderLines.get(i).getSell();
        }
        return sum;
    }
    /*
    public double calculateNet() {
        double net = 0;
        for (int i = 0; i < this.orderLines.size(); i++) {
            net += this.orderLines.get(i).getQuantity() * 
                    this.orderLines.get(i).getNet();
        }
        return net;
    }*/
    
    public double calculateTax() {
        return this.calculateSell() * TAXRATE;
    }
    
    public double calculateShipping() {
        return this.calculateSell() * SHIPPINGRATE;
    }
    
    public double calculateTotal() {
        return this.calculateSell() + this.calculateShipping() + this.calculateTax();
    }
    
    public void addOrderLine(OrderLine line) {
        this.orderLines.add(line);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.versionId != other.versionId) {
            return false;
        }
        if ((this.date == null) ? (other.date != null) : !this.date.equals(other.date)) {
            return false;
        }
        if (this.orderLines != other.orderLines && (this.orderLines == null || !this.orderLines.equals(other.orderLines))) {
            return false;
        }
        return true;
    }
    
    public int hashCode(int customerid) {
        return this.hashCode() + new Integer(customerid).hashCode();
    }
    
    @Override
    public int hashCode() {
        int hashCode = 0;
        for (OrderLine ol : orderLines) {
            hashCode += ol.hashCode();
        }
        return this.date.hashCode() + hashCode;
    }
}
