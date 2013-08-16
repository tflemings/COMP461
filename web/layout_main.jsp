<%-- 
    Document   : layout_main
    Created on : Jun 26, 2013, 1:31:54 AM
    Author     : Tony
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" language="java"%>
<%@ include file="taglibs.jsp"%>

<s:layout-definition>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>${title}</title>
	<link rel="stylesheet" href="${contextPath}/css/style.css" type="text/css" />
    </head>
    <body>
        <div class="page">
            <div class="header">
		<a href="${contextPath}/index.jsp" id="logo"><img src="${contextPath}/images/logo.jpg" alt=""/></a>
                    <ul>
			<li>
                            <s:link beanclass="com.commerce.controller.HomeActionBean"
                                    event="goHome">
                                Home
                            </s:link>
                        </li>
                        <li>
                            <s:link beanclass="com.commerce.controller.ProductActionBean">
                                Browse
                            </s:link>
                            
                        </li>
                        <li>
                            <s:link beanclass="com.commerce.controller.ShoppingCartActionBean">
                                Cart
                            </s:link>
                        </li>
                        
			<li>
                            <c:choose>
                                <c:when test="${actionBean.context.currentCustomer != null}">
                                    <s:link beanclass="com.commerce.controller.AccountActionBean">
                                        ${actionBean.context.currentCustomer.firstName}
                                    </s:link>                                  
                                </c:when>
                                <c:otherwise>
                                    <s:link beanclass="com.commerce.controller.LoginActionBean">
                                        Login
                                    </s:link>
                                </c:otherwise>                              
                            </c:choose>
                        </li>
                        <li>
                            <c:choose>
                                <c:when test="${actionBean.context.currentCustomer == null}">
                                    <s:link beanclass="com.commerce.controller.RegisterFormActionBean">
                                        Register
                                    </s:link>
                                </c:when>
                                <c:otherwise>
                                    <s:link beanclass="com.commerce.controller.LogoutActionBean">
                                        Logout
                                    </s:link>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ul>
                </div>
                    
                    <s:layout-component name="body"/>
                
                <div class="footer">
                    <ul>
			<li>
                            <s:link beanclass="com.commerce.controller.HomeActionBean"
                                    event="goHome">
                                Home
                            </s:link>
                        </li>
			<li>
                            <s:link beanclass="com.commerce.controller.ProductActionBean">
                                Browse
                            </s:link>
                            
                        </li>
			<li>
                            <s:link beanclass="com.commerce.controller.ShoppingCartActionBean">
                                Cart
                            </s:link>
                        </li>
			
                        <li>
                            <c:choose>
                                <c:when test="${actionBean.context.currentCustomer != null}">
                                    <s:link beanclass="com.commerce.controller.AccountActionBean">
                                        ${actionBean.context.currentCustomer.firstName}
                                    </s:link>
                                </c:when>
                                <c:otherwise>
                                    <s:link beanclass="com.commerce.controller.LoginActionBean">
                                        Login
                                    </s:link>
                                </c:otherwise>                              
                            </c:choose>
                        </li>
                        <li>
                            <c:choose>
                                <c:when test="${actionBean.context.currentCustomer == null}">
                                    <s:link beanclass="com.commerce.controller.RegisterFormActionBean">
                                        Register
                                    </s:link>
                                </c:when>
                                <c:otherwise>
                                    <s:link beanclass="com.commerce.controller.LogoutActionBean">
                                        Logout
                                    </s:link>
                                </c:otherwise>
                            </c:choose>
                        </li>
                    </ul>
                    <p>&#169; Copyright &#169; 2013. The Card Depot all rights reserved</p>
		</div>
	</div>
    </body>
</html>  
</s:layout-definition>
