/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.model.Order;
import com.commerce.model.OrderLine;
import com.commerce.model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tony
 */
public class OrderDAO {
    
    private static final String INSERTHEADER = "insert into orderheader values ("
            + "?, ?, ?, ?, ?, ?, ?, ?) on duplicate key update versionId = ?, "
            + "customerid = ?, orderdate = ?, ordernet = ?, ordertax = ?, "
            + "ordershipping = ?, ordertotal = ?";
    private static final String INSERTLINE = "insert into orderline values ("
            + "?, ?, ?, ?, ?, ?, ?) on duplicate key update versionId = ?, "
            + "productid = ?, quantity = ?, sellprice = ?, netprice = ?";
    private static final String GETORDER = "select cast(h.orderdate as date) "
            + "as orderdate, h.ordernet, h.ordertax, h.ordershipping, h.ordertotal, "
	    + "l.orderlineid, p.productid, p.productdescription, l.quantity, l.sellprice, "
            + "l.quantity * l.sellprice as total from orderheader h join "
            + "orderline l on h.orderheaderid = l.orderheaderid join product p "
            + "on l.productid = p.productid where h.orderheaderid = ?";
    private static final String GETQUANTITY = "select quantityonhand from product "
            + "where productid = ?";
    private static final String ADJUSTQUANTITIES = "update product set "
            + "quantityonhand = ? where productid = ?";
    private static final String CLEARLINES = "truncate table orderline";
    private static final String CLEARHEADERS = "delete from orderheader where 1 = 1";
    private static final String ORDERCOUNT = "select count(*) as count from orderheader "
            + "where customerid = ?";
    private static final String NEXTID = "select max(orderheaderid) as count from orderheader";
    
    private DatabaseController dbh;
    
    public OrderDAO() {
        try {
            this.dbh = new DatabaseController();
        } catch (SQLException e) {
            
        } 
    }
    
    public void setDbh(DatabaseController dbh) {
        this.dbh = dbh;
    }
    
    public DatabaseController getDbh() {
        return this.dbh;
    }
    
    public String createOrder(Order order, int id) {
        String msg = "";
        ArrayList<OrderLine> ol = order.getOrderLines();
        Connection conn = this.dbh.getCon();
        try {
            conn.setAutoCommit(false);
            ProductDAO p_dao = new ProductDAO();
            int index = p_dao.quantitiesValid(conn, ol);
            if (index >= 0 && index < ol.size()) {
                conn.rollback();
                return ol.get(index).getProduct().getProductDescription().toString() +
                        " is not available in that quantity.";
            } else if (index == -1) {
                conn.rollback();
                return "That product does not exist.";
            }
            int numberOfOrders = this.getNumberOfOrders(conn, id);
            if (numberOfOrders >= 5) {
                order.setShipping(0.00);
            } else {
                order.setShipping(order.calculateShipping());
            }
            int orderid = this.getNextHeaderId(conn);
            order.setId(orderid);
            PreparedStatement o_sth = conn.prepareStatement(INSERTHEADER);
            o_sth.setInt(1, order.getId());
            o_sth.setInt(2, order.getVersionId());
            o_sth.setInt(3, id);
            o_sth.setString(4, order.getDate());
            o_sth.setDouble(5, order.calculateSell());
            o_sth.setDouble(6, order.calculateTax());
            o_sth.setDouble(7, order.getShipping());
            o_sth.setDouble(8, order.calculateTotal());
            o_sth.setInt(9, order.getVersionId());
            o_sth.setInt(10, id);
            o_sth.setString(11, order.getDate());
            o_sth.setDouble(12, order.calculateSell());
            o_sth.setDouble(13, order.calculateTax());
            o_sth.setDouble(14, order.getShipping());
            o_sth.setDouble(15, order.calculateTotal());
            o_sth.executeUpdate();
            PreparedStatement l_sth = conn.prepareStatement(INSERTLINE);
            for (OrderLine line : ol) {
                l_sth.setInt(1, order.getId());
                l_sth.setInt(2, line.getId());
                l_sth.setInt(3, line.getVersionId());
                l_sth.setInt(4, line.getProduct().getProductId());
                l_sth.setInt(5, line.getQuantity());
                l_sth.setDouble(6, line.getSell());
                l_sth.setDouble(7, line.getNet());
                l_sth.setInt(8, line.getVersionId());
                l_sth.setInt(9, line.getProduct().getProductId());
                l_sth.setInt(10, line.getQuantity());
                l_sth.setDouble(11, line.getSell());
                l_sth.setDouble(12, line.getNet());
                l_sth.executeUpdate();
            }
            this.adjustQuantities(ol, conn);
            conn.commit();
            msg = "SUCCESS";
        } catch (SQLException e) {
            try {
                conn.rollback();
                msg = e.getMessage();
            } catch (SQLException ex) {
                msg = ex.getMessage();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException exception) {
                msg = exception.getMessage();
            }            
        }
        return msg;
    }
    
    public int getNextHeaderId(Connection conn) throws SQLException {
        int id = 0;
        try {
            PreparedStatement sth = conn.prepareStatement(NEXTID);
            ResultSet result = sth.executeQuery();
            if (result.next()) {
                id = result.getInt("count");
            }
            if (id < 300000) {
                id = 300000;
            } else {
                id += 1;
            }
        } catch (SQLException e) {
            throw new SQLException();
        }
        return id;
    }
    
    public int getNumberOfOrders(Connection conn, int id) throws SQLException {
        int count = 0;
        try {
            PreparedStatement sth = conn.prepareStatement(ORDERCOUNT);
            sth.setInt(1, id);
            ResultSet result = sth.executeQuery();
            if (result.next()) {
                count = result.getInt("count");
            }
            return count;
        } catch (SQLException e) {
            throw new SQLException();
        }
    }
    
    public void adjustQuantities(ArrayList<OrderLine> lines, Connection conn) throws SQLException {
        try {
            for (OrderLine line : lines) {
                int currentQuantity = this.getCurrentQuantity(
                        conn, line.getProduct().getProductId());
                PreparedStatement sth = conn.prepareStatement(ADJUSTQUANTITIES);
                sth.setInt(1, currentQuantity - line.getQuantity());
                sth.setInt(2, line.getProduct().getProductId());
                sth.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException();
        } 
    }
    
    public int getCurrentQuantity(Connection conn, int productid) {
        try {
            PreparedStatement sth = conn.prepareStatement(GETQUANTITY);
            sth.setInt(1, productid);
            ResultSet result = sth.executeQuery();
            if (result.next()) {
                return result.getInt("quantityonhand");
            }
        } catch (SQLException e) {
            return -1;
        }
        return 0;
    }
    
    public Order getOrderById(int id) {
        Order order = new Order();
        ArrayList<OrderLine> lines = new ArrayList<OrderLine>();
        order.setId(id);
        Connection conn = this.dbh.getCon();
        try {
            PreparedStatement sth = conn.prepareStatement(GETORDER);
            sth.setInt(1, order.getId());
            ResultSet result = sth.executeQuery();
            int count = 0;
            while (result.next()) {
                if (count == 0) {
                    count++;
                    order.setDate(result.getString("orderdate"));
                    order.setNet(result.getDouble("ordernet"));
                    order.setTax(result.getDouble("ordertax"));
                    order.setShipping(result.getDouble("ordershipping"));
                    order.setTotal(result.getDouble("ordertotal"));
                }
                OrderLine line = new OrderLine();
                Product product = new Product();
                product.setProductId(result.getInt("productid"));
                product.setProductDescription(result.getString("productdescription"));
                line.setId(result.getInt("orderlineid"));
                line.setProduct(product);
                line.setQuantity(result.getInt("quantity"));
                line.setSell(result.getDouble("sellprice"));
                line.setTotal(result.getDouble("total"));
                lines.add(line);
            }
            order.setOrderLines(lines);
        } catch (SQLException e) {
            return null;
        } finally {
            this.dbh.closeConnection();
        }
        return order;
    }
    
    public void clearOrders(Connection conn) throws SQLException {
        try {
            PreparedStatement l_sth = conn.prepareStatement(CLEARLINES);
            l_sth.executeUpdate();
            PreparedStatement h_sth = conn.prepareStatement(CLEARHEADERS);
            h_sth.executeUpdate();
            
        } catch (SQLException e) {
            throw new SQLException();
        }
    }
}
