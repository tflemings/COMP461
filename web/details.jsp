<%-- 
    Document   : details
    Created on : Jun 7, 2013, 10:34:53 PM
    Author     : Tony
--%>

<%@ include file="taglibs.jsp"%>
<s:layout-render name="/layout_main.jsp" title="Details">
    <s:layout-component name="body">
        <div class="body">
        <div id="details-container">
            <ul>
            <img src="${contextPath}/${actionBean.product.imageLocation}">
            <li>Item Name: ${actionBean.product.productDescription}</li>
            <li>Price: ${actionBean.product.productList}</li>
            <li>Availability: In Stock</li>
            </ul>
            <s:link beanclass="com.commerce.controller.ShoppingCartActionBean"
                    event="verifyCartItem">
                <s:param name="productId" value="${actionBean.product.productId}"/>
                Add to Shopping Cart
            </s:link>
        </div>
        </div>
    </s:layout-component>
</s:layout-render>
