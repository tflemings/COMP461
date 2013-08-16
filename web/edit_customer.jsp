<%-- 
    Document   : edit_customer
    Created on : Jul 14, 2013, 4:22:54 PM
    Author     : Tony
--%>

<%@ include file="taglibs.jsp"%>
<s:layout-render name="/layout_main.jsp" title="Edit Information">
    <s:layout-component name="body">
        <s:messages/>
        
        <div class="body">
            <div id="form_wrapper">
                <fieldset id="parent">
                    <legend>Edit Information</legend>
                    <s:form beanclass="com.commerce.controller.EditCustomerActionBean" id="login">
                        
                        <fieldset class="form_container">
                            <legend>Personal Information</legend>
                            <fieldset class="form_grouping">
                                <legend>Username</legend>
                                <s:text name="customer.userName" id="username"  class="input"/>
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
                                <s:link beanclass="com.commerce.controller.EditCustomerActionBean"
                                        event="changePassword">
                                    Change Password
                                </s:link>
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
