/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.controller.CardDepotAbstractActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 *
 * @author Tony
 */
public class LogoutActionBean extends CardDepotAbstractActionBean {
    private static final String FORM = "/index.jsp";
    
    @DefaultHandler
    public Resolution form() {
        getContext().setCurrentCustomer(null);
        return new ForwardResolution(FORM);
    }
}
