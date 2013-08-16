/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.model.CartLine;
import com.commerce.model.Product;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 *
 * @author Tony
 */
public class AddCartItemActionBean extends CardDepotAbstractActionBean {
    
    private static final String VERIFY = "/v_cart.jsp";
    private int productId;
    private CartLine cartLine;
    private ProductDAO productDao;
    private int quantity;
    
    @DefaultHandler
    public Resolution form() {
        this.productDao = this.getProductDao();
        Product p = this.productDao.readProductById(this.productId);
        this.cartLine = this.getCartLine();
        this.cartLine.setProduct(p);
        this.cartLine.setQuantity(1);
        this.cartLine.setNet(p.getProductCost());
        this.cartLine.setSell(p.getProductList());
        return new ForwardResolution(VERIFY);
    }
    
    public int getProductId() {
        return this.productId;
    }
    
    public void setProductId(int id) {
        this.productId = id;
    }
    
    public CartLine getCartLine() {
        return new CartLine();
    }
    
    public void setCartLine(CartLine cl) {
        this.cartLine = cl;
    }
    
    public ProductDAO getProductDao() {
        return new ProductDAO();
    }
    
    public void setProductDao(ProductDAO dao) {
        this.productDao = dao;
    }
    
    public int getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(int qty) {
        this.quantity = qty;
    }
}
