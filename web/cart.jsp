<%-- 
    Document   : cart
    Created on : Jun 7, 2013, 11:44:46 PM
    Author     : Tony
--%>

<%@ include file="taglibs.jsp"%>
<s:layout-render name="/layout_main.jsp" title="Cart">
    <s:layout-component name="body">
        <s:messages/>
        <div class="body">
            <h1>Shopping Cart</h1>     
            <c:choose>
                <c:when test="${actionBean.context.currentShoppingCart != null}">
                <table id="cart">
                    <thead>
                        <tr>
                            <th class="id">Cart ID</th>
                            <th class="name">Item Name</th>
                            <th class="price">Price</th>
                            <th class="qty">Quantity</th>
                            <th class="total">Total</th>
                            <th class="remove">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    <form method="post" action="#">
                    <c:forEach var="line" items="${actionBean.context.currentShoppingCart.shoppingCartLines}">
                        <tr>
                            <td>${actionBean.context.currentShoppingCart.id}</td>
                            <td>${line.product.productDescription}</td>
                            <td>${line.sell}</td>
                            <td>${line.quantity}</td>
                            <td>${line.sell * line.quantity}</td>
                            <td>
                                <s:link beanclass="com.commerce.controller.ShoppingCartActionBean"
                                    event="removeCartItem">
                                    <s:param name="productId" value="${line.product.productId}"/>
                                    Remove
                                </s:link>
                                
                            </td>                              
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <h3>You have no items in your shopping cart</h3>
                    
                </c:otherwise>
            </c:choose>                   
                    </form>
                </tbody>
            </table>
            <s:link beanclass="com.commerce.controller.ProductActionBean">
                <h3>Click here to shop</h3>
            </s:link>
            <s:link beanclass="com.commerce.controller.OrderActionBean">
                <h3>Click here to place order</h3>
            </s:link>
                <p>This is the last chance you will have to edit your order</p>
                <p>If you proceed to order the order will be placed.</p>
        <div>
    </s:layout-component>
</s:layout-render>