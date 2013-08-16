/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.controller.DepotActionBeanContext;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

/**
 *
 * @author Tony
 */
public class CardDepotAbstractActionBean implements ActionBean {
    //private ActionBeanContext actionBeanContext;
    private DepotActionBeanContext context;
    
    public DepotActionBeanContext getContext() {
        //return this.actionBeanContext;
        return this.context;
    }
    
    public void setContext(ActionBeanContext actionBeanContext) {
        //this.actionBeanContext = actionBeanContext;
        this.context = (DepotActionBeanContext) actionBeanContext;
    }
}
