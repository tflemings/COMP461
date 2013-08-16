<%-- 
    Document   : account
    Created on : Jun 8, 2013, 12:34:04 PM
    Author     : Tony
--%>
<%@ include file="taglibs.jsp"%>
<s:layout-render name="/layout_main.jsp" title="Login">
    <s:layout-component name="body">
        <div class="body">
            <div id="form_wrapper">
                <fieldset id="parent">
                    <legend>Login</legend>
                    <s:form beanclass="com.commerce.controller.LoginActionBean" id="login">
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
                        </fieldset> 
                        <fieldset class="form_grouping">
                            <s:submit name="submit" id="submit" value="Submit"  class="button"/>
                        </fieldset>       
                    </s:form>
                </fieldset>  
                <h3>Not a member?</h3><br />
                <s:link beanclass="com.commerce.controller.RegisterFormActionBean">                   
                    <h3>Register</h3>
                </s:link>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>

