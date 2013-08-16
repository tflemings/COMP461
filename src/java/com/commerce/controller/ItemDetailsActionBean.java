/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.model.Product;
import java.util.ArrayList;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 *
 * @author Tony
 */
public class ItemDetailsActionBean extends CardDepotAbstractActionBean {
    
    private static final String FORM = "/details.jsp";
    private ArrayList<Product> productList;
    private ProductDAO productDao;
    private Product product;
    private int productId;
    
    @DefaultHandler
    public Resolution getProductDetails() {
        this.productList = this.getProductList();
        this.product = this.getProduct();
        return new ForwardResolution(FORM);
    }
    
    public ArrayList<Product> getProductList() {
        return getContext().getCurrentProductList();
    }
    
    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }
    
    public ProductDAO getProductDao() {
        return new ProductDAO();
    }
    
    public void setProductDao(ProductDAO productDao) {
        this.productDao = productDao;
    }
    
    public Product getProduct() {
        this.productDao = this.getProductDao();
        Product product = this.productDao.readProductById(this.productId);
        return product;
        /*
        for (Product p : this.productList) {
            if (p.getProductId() == this.productId) {
                return p;
            }
        }
        return null;*/
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public int getProductId() {
        return this.productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
}
