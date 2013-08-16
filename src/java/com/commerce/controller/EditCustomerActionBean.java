/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.controller.CardDepotAbstractActionBean;
import com.commerce.controller.CustomerDAO;
import com.commerce.model.Customer;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;

/**
 *
 * @author Tony
 */
public class EditCustomerActionBean extends CardDepotAbstractActionBean {
    
    private static final String EDIT = "/edit_customer.jsp";
    private static final String ACCOUNT = "/account.jsp";
    private static final String CHANGE = "/changePassword.jsp";
    private Customer customer;
    private CustomerDAO customerDao;
    private String newPassword;
    private String newConfirm;
    
    
    @DefaultHandler
    public Resolution form() {
        this.customer = this.getCustomer();
        return new ForwardResolution(EDIT);
    }
    
    public Resolution submit() {
        this.customer = this.getCustomer();
        this.customerDao = this.getCustomerDao();
        if (this.customerDao.updateCustomer(this.customer)) {
            return new ForwardResolution(ACCOUNT);
        } else {
            getContext().getMessages().add(new SimpleMessage("Record cannot be updated."));
            return new ForwardResolution(EDIT);
        }
        
    }
    
    public Resolution change() {
        this.customerDao = this.getCustomerDao();
        this.newPassword = this.getNewPassword();
        this.newConfirm = this.getNewConfirm();
        this.customer = this.getCustomer();
        if (!this.newConfirm.equals(this.newPassword) || this.newConfirm == "" ||
                this.newPassword == "") {
            getContext().getMessages().add(new SimpleMessage("The new passwords must"
                    + "match and they cannot be empty."));
            return new ForwardResolution(CHANGE);
        }
        this.customerDao.changePassword(this.customer, this.newPassword);
        getContext().getMessages().add(new SimpleMessage("Password change was successful."));
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
    
    public void setCustomerDao(CustomerDAO cd) {
        this.customerDao = cd;
    }
    
    public String getNewPassword() {
        return this.newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    public String getNewConfirm() {
        return this.newConfirm;
    }
    
    public void setNewConfirm(String newConfirm) {
        this.newConfirm = newConfirm;
    }
    
    public Resolution changePassword() {
        return new ForwardResolution(CHANGE);
    }
}
