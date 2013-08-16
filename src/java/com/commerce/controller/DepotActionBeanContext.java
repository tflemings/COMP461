/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.model.Customer;
import com.commerce.model.Product;
import com.commerce.model.ShoppingCart;
import java.util.ArrayList;
import javax.servlet.http.Cookie;
import net.sourceforge.stripes.action.ActionBeanContext;

/**
 *
 * @author Tony
 */
public class DepotActionBeanContext extends ActionBeanContext {
    private static final String CUSTOMER = "customer";
    private static final String PRODUCTLIST = "productList";
    private static final String SHOPPINGCART = "shoppingCart";
    
    public void setCurrentCustomer(Customer customer) {
        setCurrent(CUSTOMER, customer);
    }
    
    public Customer getCurrentCustomer() {
        Customer customer = null;
        return getCurrent(CUSTOMER, customer);
    }
    
    public void setCurrentShoppingCart(ShoppingCart shoppingCart) {
        setCurrent(SHOPPINGCART, shoppingCart);
    }
    
    public ShoppingCart getCurrentShoppingCart() {
        ShoppingCart shoppingCart = null;
        return getCurrent(SHOPPINGCART, shoppingCart);
    }
    
    public void setCurrentProductList(ArrayList<Product> productList) {
        setCurrent(PRODUCTLIST, productList);
    }
    
    public ArrayList<Product> getCurrentProductList() {
        ArrayList<Product> productList = null;
        return getCurrent(PRODUCTLIST, productList);
    }
    
    protected void setCurrent(String key, Object value) {
        getRequest().getSession().setAttribute(key, value);
    }
    
    public void setCookie(Cookie cookie) {
        cookie.setMaxAge(14400);
        getResponse().addCookie(cookie);
    }
    
    public Cookie[] getCookie() {
        return getRequest().getCookies();
    }
    
    public String getSessionId() {
        return getRequest().getSession().getId();
    }
    
    @SuppressWarnings("unchecked")
    protected <T> T getCurrent(String key, T defaultValue) {
        T value = (T) getRequest().getSession().getAttribute(key);
        if (value == null) {
            value = defaultValue;
            setCurrent(key, value);
        }
        return value;
    }
}
