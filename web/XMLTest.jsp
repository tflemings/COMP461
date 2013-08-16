<%-- 
    Document   : XMLTest
    Created on : Jul 8, 2013, 9:30:51 PM
    Author     : Tony
--%>

<%@include file="taglibs.jsp" %>
<s:layout-render name="/layout_main.jsp" title="XML Test">
    <s:layout-component name="body">
        <s:messages/>
        <div class="body">
            <h1>Root Node is ${actionBean.rootNode}</h1>
            <table id="cart">
                <tr>
                    <th>Product ID</th>
                    <th>Product Description</th>
                    <th>Quantity</th>
                    <th>Cost</th>
                    <th>List</th>
                    <th>Image Location</th>
                    <th>Serial ID</th>
                </tr>
                <c:forEach var="pro" items="${actionBean.products}">
                    <tr>
                        <td>${pro.productId}</td>
                        <td>${pro.productDescription}</td>
                        <td>${pro.onHandQuantity}</td>
                        <td>${pro.productCost}</td>
                        <td>${pro.productList}</td>
                        <td>${pro.imageLocation}</td>
                        <td>${pro.versionId}</td>
                    </tr>
                </c:forEach>
            </table>
            
        </div>
    </s:layout-component>
</s:layout-render>
