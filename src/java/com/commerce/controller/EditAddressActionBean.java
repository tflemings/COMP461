/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.model.Address;
import com.commerce.model.Customer;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;

/**
 *
 * @author Tony
 */
public class EditAddressActionBean extends CardDepotAbstractActionBean {
    
    private static final String EDIT = "/edit_address.jsp";
    private static final String ACCOUNT = "/account.jsp";
    private Customer customer;
    private CustomerDAO customerDao;
    
    @DefaultHandler
    public Resolution form() {
        this.customer = this.getCustomer();
        return new ForwardResolution(EDIT);
    }
    
    public Resolution submit() {
        this.customer = this.getCustomer();
        this.customerDao = this.getCustomerDao();
        if (!this.customer.getBillingAddress().isComplete() ||
                !this.customer.getShippingAddress().isComplete()) {
            getContext().getMessages().add(new SimpleMessage("All fields are required"));
            return new ForwardResolution(EDIT);
        }
        this.customer.getBillingAddress().setVersionId(0);
        this.customer.getBillingAddress().setVersionId(this.customer.getBillingAddress().hashCode());
        this.customer.getShippingAddress().setVersionId(0);
        this.customer.getShippingAddress().setVersionId(this.customer.getShippingAddress().hashCode());
        Address savedBillingAddress = this.customerDao.readAddressByCustomerIdAndType(
                this.customer.getBillingAddress().getAddressType(), this.customer.getUserId());
        Address savedShippingAddress = this.customerDao.readAddressByCustomerIdAndType(
                this.customer.getShippingAddress().getAddressType(), this.customer.getUserId());
        if (this.customer.getBillingAddress().equals(savedBillingAddress) &&
                this.customer.getShippingAddress().equals(savedShippingAddress)) {
            getContext().getMessages().add(new SimpleMessage("Addresses are equal"));
            return new ForwardResolution(EDIT);
        }
        String message = "Saved Shipping Address: \n" + savedShippingAddress.toString() + "\n" +
                "New Shipping Address: \n" + this.customer.getShippingAddress().toString() + "\n" +
                "Saved Billing Address: \n" + savedBillingAddress.toString() + "\n" +
                "New Billing Address: \n" + this.customer.getBillingAddress().toString();
        getContext().getMessages().add(new SimpleMessage(message));
        return new ForwardResolution(EDIT);
    }
    
    public Customer getCustomer() {
        return getContext().getCurrentCustomer();
    }
    
    public void setCustomer(Customer c) {
        this.customer = c;
    }
    
    public CustomerDAO getCustomerDao() {
        return new CustomerDAO();
    }
    
    public void setCustomerDao(CustomerDAO customerDao) {
        this.customerDao = customerDao;
    }
}
