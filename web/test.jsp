<%-- 
    Document   : test
    Created on : Jul 27, 2013, 8:22:35 PM
    Author     : Tony
--%>

<%@ include file="taglibs.jsp"%>
<s:layout-render name="/layout_main.jsp" title="Test">
    <s:layout-component name="body">
        <s:messages/>
    <div class="body">
        <ul>
            <li>Product ID: ${actionBean.cartLine.productId}</li>
            <li>Product Description: ${actionBean.cartLine.product.productDescription}</li>
            <li>Quantity ordered: ${actionBean.cartLine.quantity}</li>
        </ul>
        
        </div>
    </s:layout-component>
</s:layout-render>
