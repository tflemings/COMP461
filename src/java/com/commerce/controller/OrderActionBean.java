/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.model.Customer;
import com.commerce.model.Order;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;

/**
 *
 * @author Tony
 */
public class OrderActionBean extends CardDepotAbstractActionBean {
    
    private static final String ORDER = "/order.jsp";
    private static final String RECEIPT = "/receipt.jsp";
    private static final String DETAILS = "/v_order.jsp";
    
    private Order order;
    private int id;
    
    @DefaultHandler
    public Resolution placeOrder() {
        if (getContext().getCurrentCustomer() == null) {
            getContext().getMessages().add(new SimpleMessage("You must login to place an order."));
            return new RedirectResolution("/Login.action");
        }
        if (getContext().getCurrentShoppingCart() == null) {
            getContext().getMessages().add(new SimpleMessage("There are no items to order. Place items "
                    + "inside the shopping cart to order them."));
            return new RedirectResolution("/com/commerce/controller/Product.action");
        }
        this.order = new Order();
        this.order.createOrderFromShoppingCart(getContext().getCurrentShoppingCart());
        int cartid = this.order.getId();
        ShoppingCartDAO cartdao = new ShoppingCartDAO();
        cartdao.deleteCart(cartid);
        if (this.order.getId() == 0) {
            getContext().getMessages().add(new SimpleMessage("Order object is not initialized in the Order class"));
            return new ForwardResolution(ORDER);
        }
        OrderDAO dao = new OrderDAO();
        String msg = dao.createOrder(this.order, getContext().getCurrentCustomer().getUserId());
        if (msg.equals("SUCCESS")) {
            Customer c = getContext().getCurrentCustomer();
            getContext().setCurrentShoppingCart(null);
            c.addOrder(this.order);
            if (c.getOrders().size() >= 5) {
                c.setCustomerStatusId(1);
            }
            CustomerDAO c_dao = new CustomerDAO();
            c_dao.updateCustomer(c);
        }
        getContext().getMessages().add(new SimpleMessage(msg));
        return new ForwardResolution(ORDER);
    }
    
    public Resolution getDetails() {
        OrderDAO dao = new OrderDAO();
        this.id = this.getId();
        this.order = dao.getOrderById(this.id);
        return new ForwardResolution(DETAILS);
    }
    
    public void setOrder(Order order) {
        this.order = order;
    }
    
    public Order getOrder() {
        return this.order;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
}
