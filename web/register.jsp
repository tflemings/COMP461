<%-- 
    Document   : register.jsp
    Created on : Jun 8, 2013, 12:18:56 PM
    Author     : Tony
--%>

<%@ include file="taglibs.jsp"%>
<s:layout-render name="/layout_main.jsp" title="Register">
    <s:layout-component name="body">
        <s:messages/>
        
        <div class="body">
            <div id="form_wrapper">
                <fieldset id="parent">
                    <legend>Register (All Fields Required)</legend>
                    <s:form beanclass="com.commerce.controller.RegisterFormActionBean" id="login">
                        <fieldset class="form_container">
                            <legend>Personal Information</legend>
                            <fieldset class="form_grouping">
                                <legend>Username</legend>
                                <s:text name="customer.userName" id="username"  class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>Password</legend>
                                <s:password name="customer.password" id="password"  class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>Confirm Password</legend>
                                <s:password name="customer.confirm" id="confirm"    class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>First Name</legend>
                                <s:text name="customer.firstName" id="fname"    class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>Last Name</legend>
                                <s:text name="customer.lastName" id="lname" class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>Email Address</legend>
                                <s:text name="customer.emailAddress" id="email" class="input"/>
                            </fieldset>
                        </fieldset>
                        <fieldset class="form_container">
                            <legend>Shipping Address</legend>
                            <fieldset class="form_grouping">
                                <legend>Street Address</legend>
                                <s:text name="shippingAddress.streetAddress" id="s_streetAddress" class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>City</legend>
                                <s:text name="shippingAddress.city" id="s_city" class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>State</legend>
                                <s:text name="shippingAddress.state" id="s_state" class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>Zip</legend>
                                <s:text name="shippingAddress.zipCode" id="s_zip" class="input"/>
                            </fieldset>
                        </fieldset>             
                        <fieldset class="form_container">
                            <legend>Billing Address</legend>
                            <fieldset class="form_grouping">
                                <legend>Street Address</legend>
                                <s:text name="billingAddress.streetAddress" id="b_streetAddress" class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>City</legend>
                                <s:text name="billingAddress.city" id="b_city" class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>State</legend>
                                <s:text name="billingAddress.state" id="b_state" class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>Zip</legend>
                                <s:text name="billingAddress.zipCode" id="b_zip" class="input"/>
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
