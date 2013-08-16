/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.model.CartLine;
import com.commerce.model.Product;
import com.commerce.model.ShoppingCart;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.Cookie;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;

/**
 *
 * @author Tony
 */
public class ShoppingCartActionBean extends CardDepotAbstractActionBean {
    
    private static final String VERIFY = "/v_cart.jsp";
    private static final String CART = "/cart.jsp";
    private int productId;
    private Product product;
    private int quantity;
    
    
    @DefaultHandler
    public Resolution submit() {
        return new ForwardResolution(CART);
    }
    
    public int getProductId() {
        return this.productId;
    }
    
    public void setProductId(int id) {
        this.productId = id;
    }
    
    public Product getProduct() {
        ProductDAO dao = new ProductDAO();
        Product p = dao.readProductById(this.productId);
        return p;
    }
    
    public void setProduct(Product p) {
        this.product = p;
    }
    
    public int getQuantity() {
        return this.quantity;
    }
    
    public void setQuantity(int q) {
        this.quantity = q;
    }
    
    public Resolution verifyCartItem() {
        this.product = this.getProduct();
        this.setQuantity(1);
        return new ForwardResolution(VERIFY);
    }
    
    public Resolution addItem() {
        this.product = this.getProduct();
        CartLine line = new CartLine();
        line.setProduct(this.product);
        line.setQuantity(this.quantity);
        line.setNet(this.product.getProductCost());
        line.setSell(this.product.getProductList());
        String msg = "";
        ShoppingCartDAO dao = new ShoppingCartDAO();
        if (!line.getProduct().isQuantityAvailable(line.getQuantity())) {
            return this.quantityNotAvailable();
        }
        if (getContext().getCurrentShoppingCart() == null) {
            String session = getContext().getSessionId();
            ShoppingCart cart = new ShoppingCart();
            cart.setCreationDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            cart.addCartLine(line);
            getContext().setCurrentShoppingCart(cart);
            msg = dao.create(cart, session);    
            Cookie cookie = new Cookie("cartheaderid", session);
            getContext().setCookie(cookie);
        } else {  
            CartLine cl = getContext().getCurrentShoppingCart().getCartLineByProductId(this.product.getProductId());
            if (cl == null) {
                getContext().getCurrentShoppingCart().addCartLine(line);
                // Line added needs written to database - line id does not exist - cart id does
                msg = dao.createCartLine(getContext().getCurrentShoppingCart());
            } else {
                int newQuantity = cl.getQuantity() + this.quantity;
                if (!cl.getProduct().isQuantityAvailable(newQuantity)) {
                    return this.quantityNotAvailable();
                }
                cl.setQuantity(cl.getQuantity() + this.quantity);
                // Quantity updated needs written to database - line id and cart id both exist
                msg = dao.updateItems(getContext().getCurrentShoppingCart());
            }                 
        }
        getContext().getMessages().add(new SimpleMessage(msg)); 
        return this.submit();
    }
    
    public Resolution quantityNotAvailable() {
        getContext().getMessages().add(new SimpleMessage("That quantity is not available."));
        return new ForwardResolution(VERIFY);
    }
    
    public Resolution removeCartItem() {
        String msg = "";
        CartLine cl = getContext().getCurrentShoppingCart().getCartLineByProductId(this.productId);
        if (cl != null) {
            getContext().getCurrentShoppingCart().deleteCartLine(cl);
            ShoppingCartDAO c_dao = new ShoppingCartDAO();
            msg = c_dao.deleteCartLine(getContext().getCurrentShoppingCart().getId(), cl.getId());
            if (getContext().getCurrentShoppingCart().getShoppingCartLines().size() == 0) {
                getContext().setCurrentShoppingCart(null);
            }
        }
        getContext().getMessages().add(new SimpleMessage(msg));
        return new ForwardResolution(CART);
    }
    
    public Resolution editCartItem() {
        return this.submit();
    }
    
    
}
