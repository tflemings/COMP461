<%-- 
    Document   : edit_address
    Created on : Jul 14, 2013, 9:08:20 PM
    Author     : Tony
--%>

<%@ include file="taglibs.jsp"%>
<s:layout-render name="/layout_main.jsp" title="Edit Addresses">
    <s:layout-component name="body">
        <s:messages/>
        
        <div class="body">
            <div id="form_wrapper">
                <fieldset id="parent">
                    <legend>Edit Address Information</legend>
                    <s:form beanclass="com.commerce.controller.EditAddressActionBean" id="login">
                        <fieldset class="form_container">
                            <legend>Shipping Address</legend>
                            <fieldset class="form_grouping">
                                <legend>Street Address</legend>
                                <s:text name="customer.shippingAddress.streetAddress" id="s_streetAddress" class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>City</legend>
                                <s:text name="customer.shippingAddress.city" id="s_city" class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>State</legend>
                                <s:text name="customer.shippingAddress.state" id="s_state" class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>Zip</legend>
                                <s:text name="customer.shippingAddress.zipCode" id="s_zip" class="input"/>
                            </fieldset>
                        </fieldset>             
                        <fieldset class="form_container">
                            <legend>Billing Address</legend>
                            <fieldset class="form_grouping">
                                <legend>Street Address</legend>
                                <s:text name="customer.billingAddress.streetAddress" id="b_streetAddress" class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>City</legend>
                                <s:text name="customer.billingAddress.city" id="b_city" class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>State</legend>
                                <s:text name="customer.billingAddress.state" id="b_state" class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>Zip</legend>
                                <s:text name="customer.billingAddress.zipCode" id="b_zip" class="input"/>
                            </fieldset>
                        </fieldset>                 
                        <fieldset class="form_grouping">
                            <s:submit name="submit" id="submit" value="Submit"  class="button"/>
                        </fieldset>                   
                    </s:form>
                </fieldset>             
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
