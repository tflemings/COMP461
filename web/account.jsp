<%-- 
    Document   : account
    Created on : Jun 8, 2013, 12:34:04 PM
    Author     : Tony
--%>

<%@include file="taglibs.jsp" %>
<s:layout-render name="/layout_main.jsp" title="Account">
    <s:layout-component name="body">
        <s:messages/>
        <div class="body">
        <fieldset id="account">
            <legend>Account</legend>
            <fieldset>
                <legend>Customer Information</legend> 
                <s:link beanclass="com.commerce.controller.EditCustomerActionBean">Edit</s:link>
                <p>Name: ${actionBean.context.currentCustomer.firstName} ${actionBean.context.currentCustomer.lastName}</p>
                <p>Username: ${actionBean.context.currentCustomer.userName}</p>
                <p>Email Address: ${actionBean.context.currentCustomer.emailAddress}</p>
                <c:choose>
                    <c:when test="${actionBean.context.currentCustomer.customerStatusId == 0}">
                        <p>Membership Type: Standard</p>
                    </c:when>
                    <c:otherwise>
                        <p>Membership Type: Premium</p>
                    </c:otherwise>
                </c:choose>
            </fieldset>
            <fieldset>
                <legend>Shipping Information</legend>   
                <s:link beanclass="com.commerce.controller.EditAddressActionBean">Edit</s:link>
                <p>${actionBean.context.currentCustomer.shippingAddress.streetAddress}</p>
                <p>${actionBean.context.currentCustomer.shippingAddress.city}, 
                   ${actionBean.context.currentCustomer.shippingAddress.state} 
                   ${actionBean.context.currentCustomer.shippingAddress.zipCode}</p>               
            </fieldset>
            <fieldset>
                <legend>Billing Information</legend>
                <s:link beanclass="com.commerce.controller.EditAddressActionBean">Edit</s:link>
                <p>${actionBean.context.currentCustomer.billingAddress.streetAddress}</p>
                <p>${actionBean.context.currentCustomer.billingAddress.city}, 
                   ${actionBean.context.currentCustomer.billingAddress.state} 
                   ${actionBean.context.currentCustomer.billingAddress.zipCode}</p>  
            </fieldset>
            <fieldset>
                <legend>Order Information</legend>
                <table id="order_table">
                    <tr>
                        <td>Order Number</td>
                        <td>Order Date</td>
                        <td>Order Total</td>
                    </tr>
                    <c:forEach var="order" items="${actionBean.context.currentCustomer.orders}">
                        <tr>
                            <td>
                                <s:link beanclass="com.commerce.controller.OrderActionBean"
                                    event="getDetails">
                                    <s:param name="id" value="${order.id}" />  
                                    ${order.id}
                                </s:link>                                 
                            </td>                                                   
                            <td>${order.date}</td>
                            <td>${order.total}</td>
                        </tr>
                    </c:forEach>
                </table>
            </fieldset>
        </fieldset>
        </div>
    </s:layout-component>
</s:layout-render>



