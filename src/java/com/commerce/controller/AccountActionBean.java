/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.model.Customer;
import com.commerce.model.Order;
import java.util.ArrayList;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 *
 * @author Tony
 */
public class AccountActionBean extends CardDepotAbstractActionBean  {
    
    private static final String ACCOUNT = "/account.jsp";
    private Customer customer;
    private CustomerDAO customerDao;
    
    
    @DefaultHandler
    public Resolution form () {
        this.customer = this.getCustomer();
        this.customerDao = this.getCustomerDao();
        if (this.customer.getBillingAddress() == null) {
            this.customer.setBillingAddress(this.customerDao.readAddressByCustomerIdAndType(
                    0, this.customer.getUserId()));
        }
        if (this.customer.getShippingAddress() == null) {
            this.customer.setShippingAddress(this.customerDao.readAddressByCustomerIdAndType(
                    1, this.customer.getUserId()));
        }
        this.customer.setOrders(this.customerDao.readOrdersByUserId(this.customer.getUserId()));
        return new ForwardResolution(ACCOUNT);
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
