<%-- 
    Document   : v_cart
    Created on : Jul 22, 2013, 9:17:54 PM
    Author     : Tony
--%>

<%@ include file="taglibs.jsp"%>
<s:layout-render name="/layout_main.jsp" title="Verify Item">
    <s:layout-component name="body">
        <s:messages/>
    <div class="body">
            <div id="form_wrapper">
                <fieldset id="parent">
                    <legend>Add To Cart</legend> 
                    <s:form beanclass=
                            "com.commerce.controller.ShoppingCartActionBean">
                    <ul>
                        <li>Product Description: 
                            ${actionBean.product.productDescription}</li>
                        <li>Price: 
                            ${actionBean.product.productList}</li>
                        <c:choose>
                            <c:when test="${actionBean.product.onHandQuantity > 0}">
                                <li>Availability: In Stock</li>
                            </c:when>
                            <c:otherwise>
                                <li>Availability: Out Of Stock</li>
                            </c:otherwise>
                        </c:choose>
                        <li>
                            <s:label for="quantity">Quantity Ordered</s:label>
                            <s:text id="quantity" name="quantity">${actionBean.quantity}</s:text>
                        </li>
                        <s:hidden name="productId" value="${actionBean.productId}"/>
                        <li>
                        <s:submit name="addItem" value="Add Item To Cart"/>
                        
                        </li>
                        
                        <li>
                            <s:link beanclass="com.commerce.controller.ProductActionBean">
                                Cancel
                            </s:link>
                        </li>
                    </ul>
                    </s:form>
                </fieldset>             
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
