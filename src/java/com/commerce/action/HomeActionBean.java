/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.action;

import com.commerce.controller.CardDepotAbstractActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

/**
 *
 * @author Tony
 */
public class HomeActionBean extends CardDepotAbstractActionBean {
    
    private static final String HOME = "/index.jsp";
    
    @DefaultHandler
    public Resolution form() {
        return new ForwardResolution(HOME);
    }
}
