/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.model.Customer;
import com.commerce.model.Product;
import java.util.ArrayList;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 *
 * @author Tony
 */
public class LoginActionBean extends CardDepotAbstractActionBean {
    
    private static final String FORM = "/login.jsp";
    private static final String REGISTER = "/register.jsp";
    private static final String HOME = "/index.jsp";
    private Customer customer;
    private CustomerDAO customerDao;
    private ArrayList<Product> productList;
    private ProductDAO productDao;
    
    @DefaultHandler
    public Resolution form() {
        return new ForwardResolution(FORM);
    }
    
    public Resolution submit() {
        this.customer = this.getCustomer();
        this.customerDao = this.getCustomerDao();
        if (this.customer.getUserName() != null && this.customer.getPassword() != null) {
            if (this.customerDao.checkPassword(this.customer)) {
                this.customer.setUserId(this.customerDao.recordExists(this.customer.getUserName()));
                this.customer.setOrders(this.customerDao.readOrdersByUserId(this.customer.getUserId()));
                getContext().setCurrentCustomer(this.customer);
                getContext().setCurrentProductList(this.productList);
                return new ForwardResolution(HOME);
            }
        }
        return new ForwardResolution(REGISTER);
    }
    
    public Customer getCustomer() {
        return this.customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public ArrayList<Product> getProductList() {
        return this.getProductDao().readAllProducts();
    }
    
    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }
    
    public CustomerDAO getCustomerDao() {
        return new CustomerDAO();
    }
    
    public void setCustomerDao(CustomerDAO customerDao) {
        this.customerDao = customerDao;
    }
    
    public ProductDAO getProductDao() {
        return new ProductDAO();
    }
    
    public void setProductDao(ProductDAO productDao) {
        this.productDao = productDao;
    }
}
