<%-- 
    Document   : index
    Created on : May 28, 2013, 8:06:03 PM
    Author     : Tony
--%>
<%@ include file="taglibs.jsp"%>
<s:layout-render name="/layout_main.jsp" title="Home">
    <s:layout-component name="body">
        <s:messages/>
    <div class="body">
        <h3>
            <s:link beanclass="com.commerce.controller.XMLTestActionBean" event="readXml">
                Click Here To Test The XML Parser.
            </s:link>
        </h3>
        <h3>
            <s:link beanclass="com.commerce.controller.HomeActionBean" event="resetDatabase">
                Click Here To Reset The Database.
            </s:link>
        </h3>
        <p>This operation resets the product table and clears out all orders and shopping carts.</p>
        <div id="featured">
            <h3>Welcome to The Card Depot</h3>
            <p>We offer sports trading cards specializing in Baseball, Football, Basketball,<br />
               and Racing. Card conditions vary</p>
            <input type="button" value="Read more" onClick="parent.location='blog.html'"/>
	</div>
    </div>
    </s:layout-component>
</s:layout-render>
    
