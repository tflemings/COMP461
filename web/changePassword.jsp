<%-- 
    Document   : changePassword
    Created on : Aug 4, 2013, 5:26:09 PM
    Author     : Tony
--%>

<%@ include file="taglibs.jsp"%>
<s:layout-render name="/layout_main.jsp" title="Edit Information">
    <s:layout-component name="body">
        <s:messages/>   
        <div class="body">
            <div id="form_wrapper">
                <fieldset id="parent">
                    <legend>Change Password</legend>
                    <s:form beanclass="com.commerce.controller.EditCustomerActionBean" id="login">                       
                        <fieldset class="form_container">
                            
                            <fieldset class="form_grouping">
                                <legend>Enter Current Password</legend>
                                <s:password name="customer.password" id="password"  class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>Enter New Password</legend>
                                <s:password name="newPassword" id="newPassword"    class="input"/>
                            </fieldset>
                            <fieldset class="form_grouping">
                                <legend>Re-Enter New Password</legend>
                                <s:password name="newConfirm" id="newConfirm" class="input"/>
                            </fieldset>
                        </fieldset>                                      
                        <fieldset class="form_grouping">
                            <s:submit name="change" id="submit" value="Submit"  class="button"/>
                        </fieldset>                   
                    </s:form>
                </fieldset>             
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
