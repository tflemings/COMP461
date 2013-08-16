<%-- 
    Document   : order
    Created on : Jun 8, 2013, 10:01:24 AM
    Author     : Tony
--%>

<%@ include file="taglibs.jsp"%>
<s:layout-render name="/layout_main.jsp" title="Order">
    <s:layout-component name="body">
        <s:messages/>
        <div class="body">
        <h1>Order #${actionBean.order.id}</h1>
        <table id="cart">
            <thead>
                <tr>
                    <th class="name">Item Name</th>
                    <th class="price">Price</th>
                    <th class="qty">Quantity</th>
                    <th class="remove">Total</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${actionBean.order.orderLines}">
                <tr>
                    <td class="name">
                        <s:link beanclass="com.commerce.controller.ItemDetailsActionBean">
                            ${item.product.productDescription}
                        </s:link>
                    </td>
                    <td class="price">${item.sell}</td>
                    <td class="qty">${item.quantity}</td>
                    <td class="remove">${item.total}</td>
                </tr>
                </c:forEach>
                <tr id="subtotal">
                    <td colspan="3">Subtotal:</td>
                    <td>${actionBean.order.net}</td>
                </tr>
                <tr id="tax">
                    <td colspan="3">Tax</td>
                    <td><fmt:formatNumber value="${actionBean.order.tax}" maxFractionDigits="2" /></td>
                </tr>
                <tr id="shipping">
                    <td colspan="3">Shipping</td>
                    <c:choose>
                        <c:when test="${actionBean.context.currentCustomer.customerStatusId == 0}">
                            <td>
                                <fmt:formatNumber value="${actionBean.order.shipping}" maxFractionDigits="2" />
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>0.00</td>
                        </c:otherwise>
                    </c:choose>                    
                </tr>
                <tr id="total">
                    <td colspan="3">Total</td>
                    <c:choose>
                        <c:when test="${actionBean.context.currentCustomer.customerStatusId == 0}">
                            <td>
                                <fmt:formatNumber value="${actionBean.order.total}" maxFractionDigits="2" />
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td>
                                <fmt:formatNumber value="${actionBean.order.total - actionBean.order.shipping}" maxFractionDigits="2" />
                            </td>
                        </c:otherwise>
                    </c:choose>
                </tr>
                
            </tbody>
        </table>
        </div>
    </s:layout-component>
</s:layout-render>
