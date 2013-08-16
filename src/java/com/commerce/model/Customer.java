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
public class Customer implements Serializable {
    
    private int userId;
    private int versionId;
    private String userName;
    private String password;
    private String confirm;
    private Address shippingAddress;
    private Address billingAddress;
    private String emailAddress;
    private String firstName;
    private String lastName;
    private int customerStatusId;
    private ArrayList<Order> orders;
    
    public Customer() {
        
    }
    
    public int getUserId() {
        return this.userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getVersionId() {
        return this.versionId;
    }
    
    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getConfirm() {
        return this.confirm;
    }
    
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
    
    public Address getShippingAddress() {
        return this.shippingAddress;
    }
    
    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public Address getBillingAddress() {
        return this.billingAddress;
    }
    
    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
    
    public String getEmailAddress() {
        return this.emailAddress;
    }
    
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public int getCustomerStatusId() {
        return this.customerStatusId;
    }
    
    public void setCustomerStatusId(int id) {
        this.customerStatusId = id;
    }
    
    public ArrayList<Order> getOrders() {
        return this.orders;
    }
    
    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
    
    public void addOrder(Order order) {
        this.orders.add(order);
    }
    
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
    
    public boolean validatePassword() {
        return this.password.equals(this.confirm);
    }
    
    public boolean isComplete() {
        return this.userName != null && this.firstName != null && 
                this.lastName != null && this.emailAddress != null &&
                this.password != null && this.confirm != null;
    }
    
    
    
    @Override
    public int hashCode() {
        int hashCode = 0;
        for (Order o : this.orders) {
            hashCode += o.hashCode();
        }
        return this.userName.hashCode() + this.password.hashCode() + 
                this.shippingAddress.hashCode() + this.billingAddress.hashCode() +
                this.emailAddress.hashCode() + this.firstName.hashCode() +
                this.lastName.hashCode() + new Integer(this.customerStatusId).hashCode() +
                hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (this.userId != other.userId) {
            return false;
        }
        if (this.versionId != other.versionId) {
            return false;
        }
        if ((this.userName == null) ? (other.userName != null) : !this.userName.equals(other.userName)) {
            return false;
        }
        if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
            return false;
        }
        if ((this.confirm == null) ? (other.confirm != null) : !this.confirm.equals(other.confirm)) {
            return false;
        }
        if (this.shippingAddress != other.shippingAddress && (this.shippingAddress == null || !this.shippingAddress.equals(other.shippingAddress))) {
            return false;
        }
        if (this.billingAddress != other.billingAddress && (this.billingAddress == null || !this.billingAddress.equals(other.billingAddress))) {
            return false;
        }
        if ((this.emailAddress == null) ? (other.emailAddress != null) : !this.emailAddress.equals(other.emailAddress)) {
            return false;
        }
        if ((this.firstName == null) ? (other.firstName != null) : !this.firstName.equals(other.firstName)) {
            return false;
        }
        if ((this.lastName == null) ? (other.lastName != null) : !this.lastName.equals(other.lastName)) {
            return false;
        }
        if (this.orders != other.orders && (this.orders == null || !this.orders.equals(other.orders))) {
            return false;
        }
        return true;
    }
}
