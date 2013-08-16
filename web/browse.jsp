<%-- 
    Document   : browse
    Created on : Jun 6, 2013, 9:26:29 PM
    Author     : Tony
--%>

<%@ include file="taglibs.jsp"%>
<s:layout-render name="/layout_main.jsp" title="Browse">
    <s:layout-component name="body">
        <s:messages/>
    <div class="body">
        <ul>
           <c:forEach var="product" items="${actionBean.context.currentProductList}">
            <li>
                <div class="featured">
                    <img src="${contextPath}/${product.imageLocation}" alt="${product.productDescription}"/>
                </div>
		<div>
                    <s:link beanclass="com.commerce.controller.ItemDetailsActionBean">
                        <s:param name="productId" value="${product.productId}"/>
                        <h3>${product.productDescription}</h3>
                    </s:link>
                    
                    <p></p>
		</div>
            </li>
            </c:forEach>
	</ul>
    </div>
    </s:layout-component>
</s:layout-render>



