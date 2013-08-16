/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.controller.CardDepotAbstractActionBean;
import com.commerce.controller.CustomerDAO;
import com.commerce.model.Address;
import com.commerce.model.Customer;
import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;

/**
 *
 * @author Tony
 */
public class RegisterFormActionBean extends CardDepotAbstractActionBean {
    private static final String FORM = "/register.jsp";
    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private CustomerDAO customerDao;
    private Integer userId;
    
    @DefaultHandler
    public Resolution form() {
        return new ForwardResolution(FORM);
    }
    
    public Resolution submit() {
        this.customer = this.getCustomer();
        if (this.customer == null || !this.customer.isComplete()) {
            getContext().getMessages().add(new SimpleMessage("Customer information needs to be completed."));
            return new ForwardResolution(FORM);
        }
        if (!this.customer.validatePassword()) {
            getContext().getMessages().add(new SimpleMessage("Passwords do not match."));
            return new ForwardResolution(FORM);
        }
        this.shippingAddress = this.getShippingAddress();
        if (this.shippingAddress == null || !this.shippingAddress.isComplete()) {
            getContext().getMessages().add(new SimpleMessage("Shipping address information needs to be completed."));
            return new ForwardResolution(FORM);
        }
        this.shippingAddress.setAddressType(1);
        this.billingAddress = this.getBillingAddress();
        if (this.billingAddress == null || !this.billingAddress.isComplete()) {
            getContext().getMessages().add(new SimpleMessage("Billing address information needs to be completed."));
            return new ForwardResolution(FORM);
        }
        this.billingAddress.setAddressType(0);
        this.customer.setBillingAddress(this.billingAddress);
        this.customer.setShippingAddress(this.shippingAddress);
        this.customerDao = this.getCustomerDao();
        //int result = 
        int result = this.customerDao.create(this.customer);
        
        getContext().setCurrentCustomer(customer);
        
        if (result > 0) {
            getContext().getMessages().add(new SimpleMessage("Inserted all records"));
            return new ForwardResolution(FORM);
        } else {
            getContext().getMessages().add(new SimpleMessage("Insert result is " + result));
            return new ForwardResolution(FORM);
        }
        
        //return new  RedirectResolution(RegisterFormActionBean.class);
    }
    
    public Customer getCustomer() {
        
        return this.customer;
    }
    
    public Address getShippingAddress() {
        return this.shippingAddress;
    }
    
    public Address getBillingAddress() {
        return this.billingAddress;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public CustomerDAO getCustomerDao() {
        return new CustomerDAO();
    }
    
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer id) {
        this.userId = id;
    }
    
    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
}
