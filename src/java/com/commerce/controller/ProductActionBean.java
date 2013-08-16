/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.controller.CardDepotAbstractActionBean;
import com.commerce.controller.ProductDAO;
import com.commerce.model.Product;
import java.util.ArrayList;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;

/**
 *
 * @author Tony
 */
public class ProductActionBean extends CardDepotAbstractActionBean {
    private static final String FORM = "/browse.jsp";
    private Product product;
    private ProductDAO productDao;
    private Integer productId;
    private ArrayList<Product> productList;
    
    
    @DefaultHandler
    public Resolution readProducts() {
        this.product = getProduct();
        this.productDao = this.getProductDao();
        this.productList = this.getProductList();
        getContext().setCurrentProductList(this.productList);
        return new ForwardResolution(FORM);
    }
    
    public Product getProduct() {
        if (this.productId == null) {
            return new Product();
        }
        return new Product();
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public ProductDAO getProductDao() {
        return new ProductDAO();
    }
    
    public Integer getProductId() {
        return this.productId;
    }
    
    public void setProductId(Integer id) {
        this.productId = id;
    }
    
    public ArrayList<Product> getProductList() {
        if (getContext().getCurrentProductList() == null) {
            return this.productDao.readAllProducts();
        }
        return getContext().getCurrentProductList();
    }
}
