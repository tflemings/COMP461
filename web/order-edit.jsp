<%-- 
    Document   : order-add
    Created on : Jun 8, 2013, 9:33:17 AM
    Author     : Tony
--%>

<%@ include file="taglibs.jsp"%>
<s:layout-render name="/layout_main.jsp" title="Edit Order">
    <s:layout-component name="body">
        <div class="body">
        <h1>Order</h1>
        <form method="post" action="#">
            <table id="order">
                <thead>
                    <tr>
                        <th class="name">Item Name</th>
                        <th class="price">Price</th>
                        <th class="qty">Quantity</th>
                        <th class="total">Total</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="name"><a href="details.jsp">1967 Topps 500 Frank Robinson</a></td>
                        <td class="price">$5.29</td>
                        <td class="qty"><input type="text" name="qty" value="1"></td>
                        <td class="total">$5.29</td>
                    </tr>
                    <tr>
                        <td class="name"><a href="#">Some other items</a></td>
                        <td class="price">$2.99</td>
                        <td class="qty"><input type="text" name="qty" value="1"></td>
                        <td class="total">$2.99</td>
                    </tr>
                    <tr>
                        <td><button>Update</button></td>
                    </tr>
                    <tr>
                        <td><a href="order.jsp">Confirm Order</a></td>
                    </tr>
                </tbody>
            </table>
        </form>
        </div>
    </s:layout-component>
</s:layout-render>
