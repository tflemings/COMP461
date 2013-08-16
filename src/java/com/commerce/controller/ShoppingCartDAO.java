/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.model.CartLine;
import com.commerce.model.Product;
import com.commerce.model.ShoppingCart;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tony
 */
public class ShoppingCartDAO {
    private DatabaseController dbh;
    private static final String INSERTLINE = "insert into cartline values ( "
            + "?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERTCART = "insert into cartheader values ( "
            + "?, ?, ?, ?)";
    private static final String UPDATELINES = "insert into cartline values ( "
            + "?, ?, ?, ?, ?, ?, ?) on duplicate key update versionId = ?, "
            + "productid = ?, quantity = ?, netprice = ?, sellprice = ?";
    private static final String UPDATECART = "insert into cartheader values ( "
            + "?, ?, ?) on duplicate key update versionId = ?, cartheaderdate = ?";
    private static final String COOKIECART = "select h.cartheaderid, h.versionId as h_versionId, "
            + "h.cartheaderdate, l.cartlineid, l.versionId as l_versionId, l.productid, "
            + "p.productdescription, p.image, l.quantity, l.netprice, l.sellprice "
            + "from cartheader h join cartline l on h.cartheaderid = l.cartheaderid "
            + "join product p on l.productid = p.productid where h.sessionid = ?";
    private static final String CLEARLINES = "truncate table cartline";
    private static final String CLEARHEADERS = "delete from cartheader where 1 = 1";
    private static final String DELETELINE = "delete from cartline where cartheaderid "
            + "= ? and cartlineid = ?";
    private static final String GETLINES = "select * from cartline where cartheaderid = ?";
    private static final String DELETEHEADER = "delete from cartheader where cartheaderid = ?";
    private static final String DELETELINEBYCARTID = "delete from cartline where "
            + "cartheaderid = ?";
    
    public ShoppingCartDAO() {
        try {
            this.dbh = new DatabaseController();
        } catch (SQLException e) {
            
        } 
    }
    
    public DatabaseController getDbh() {
        return this.dbh;
    }
    
    public String create(ShoppingCart cart, String sessId) {  
        Connection conn = this.dbh.getCon();
        try {           
            conn.setAutoCommit(false);
            cart.setId(this.getNextId(conn));
            CartLine cl = cart.getShoppingCartLines().get(0);
            cl.setId(this.getNextLineId(conn, cart));
            cl.setVersionId(cl.hashCode(cart.getId()));
            cart.setVersionId(cart.hashCode());
            PreparedStatement headerSth = conn.prepareStatement(INSERTCART);
            headerSth.setInt(1, cart.getId());
            headerSth.setInt(2, cart.getVersionId());
            headerSth.setString(3, cart.getCreationDate());
            headerSth.setString(4, sessId);
            if (headerSth.executeUpdate() > 0) {               
                PreparedStatement lineSth = conn.prepareStatement(INSERTLINE);
                lineSth.setInt(1, cart.getId());
                lineSth.setInt(2, cl.getId());
                lineSth.setInt(3, cl.getVersionId());
                lineSth.setInt(4, cl.getProduct().getProductId());
                lineSth.setInt(5, cl.getQuantity());
                lineSth.setDouble(6, cl.getNet());
                lineSth.setDouble(7, cl.getSell());
                if (lineSth.executeUpdate() > 0) {
                    conn.commit();
                    return "Item added to cart.";
                } else {
                    return "Item not added to cart.";
                }              
            } else {
                return "Item not added to cart";
            }    
        } catch (SQLException e) {
            try {
                conn.rollback();
                return e.getMessage();
            } catch (SQLException exception) {
                return exception.getMessage();
            }            
        } finally {   
            try {
                conn.setAutoCommit(true);
                this.dbh.closeConnection();
            } catch (SQLException ex) {
                return ex.getMessage();
            }
        }
    }
    
    public int getNextId(Connection conn) {
        String sql = "select max(cartheaderid) as id from cartheader";
        try {
            PreparedStatement sth = conn.prepareStatement(sql);
            ResultSet result = sth.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                if (id <= 0) {
                    return 100000;
                } else {
                    return id += 1;
                }
            } else {
                return -1;
            }
        } catch (SQLException e) {
            return -1;
        } 
    }
    
    public int getNextLineId(Connection conn, ShoppingCart cart) {
        String sql = "select max(cartlineid) as id from cartline where cartheaderid = ?";
        try {
            PreparedStatement sth = conn.prepareStatement(sql);
            sth.setInt(1, cart.getId());
            ResultSet result = sth.executeQuery();
            if (result.next()) {
                int id = result.getInt("id");
                if (id <= 0) {
                    return 1;
                } else {
                    return ++id;
                }
            } else {
                return -1;
            }
        } catch (SQLException e) {
            return -1;
        }
    }
    
    public String createCartLine(ShoppingCart cart) {
        Connection conn = this.dbh.getCon();
        String msg = "";
        try {
            conn.setAutoCommit(false);
            for (CartLine cl : cart.getShoppingCartLines()) {
                if (cl.getId() == 0) {
                    int id = this.getNextLineId(conn, cart);
                    cl.setId(id);
                    cl.setVersionId(cl.hashCode(cart.getId()));
                    cart.setVersionId(cart.hashCode());
                    if (this.updateCart(conn, cart)) {
                        msg = "Shopping Cart has been updated.";
                        conn.commit();
                    }
                }
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
                return e.getMessage();
            } catch (SQLException exception) {
                return exception.getMessage();
            }        
        } finally {
            try {
                conn.setAutoCommit(true);
                this.dbh.closeConnection();
            } catch (SQLException ex) {
                return ex.getMessage();
            }
        }
        return msg;
    }
    
    public boolean updateCart(Connection conn, ShoppingCart cart) {
        try {
            PreparedStatement c_sth = conn.prepareStatement(UPDATECART);
            c_sth.setInt(1, cart.getId());
            c_sth.setInt(2, cart.getVersionId());
            c_sth.setString(3, cart.getCreationDate());
            c_sth.setInt(4, cart.getVersionId());
            c_sth.setString(5, cart.getCreationDate());
            c_sth.executeUpdate();
            PreparedStatement l_sth = conn.prepareStatement(UPDATELINES);
            for (CartLine cl : cart.getShoppingCartLines()) {
                l_sth.setInt(1, cart.getId());
                l_sth.setInt(2, cl.getId());
                l_sth.setInt(3, cl.getVersionId());
                l_sth.setInt(4, cl.getProduct().getProductId());
                l_sth.setInt(5, cl.getQuantity());
                l_sth.setDouble(6, cl.getNet());
                l_sth.setDouble(7, cl.getSell());
                l_sth.setInt(8, cl.getVersionId());
                l_sth.setInt(9, cl.getProduct().getProductId());
                l_sth.setInt(10, cl.getQuantity());
                l_sth.setDouble(11, cl.getNet());
                l_sth.setDouble(12, cl.getSell());  
                l_sth.executeUpdate();
            }
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    public String updateItems(ShoppingCart cart) {
        String msg = "";
        for (CartLine cl : cart.getShoppingCartLines()) {
            cl.setVersionId(cart.getId());
        }
        cart.setVersionId(cart.hashCode());
        Connection conn = this.dbh.getCon();
        try {
            conn.setAutoCommit(false);
            if (!this.updateCart(conn, cart)) {
                return "Shopping Cart could not be updated.";
            }
            msg = "Shopping Cart has been updated.";
            conn.commit();
        } catch (SQLException e) {
            msg = e.getMessage();
            try {
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                msg = ex.getMessage();
            }
        }
        return msg;
    }
    
    public boolean createCartLines(ArrayList<CartLine> cartLines) {
        String sql = "insert into ";
        return false;
    }
    
    public ShoppingCart readById(int id) {
        String sql = "select cartheaderdate from cartheader where cartheaderid = ?";
        ShoppingCart cart = new ShoppingCart();
        cart.setId(id);
        try {
            Connection myConnection = this.dbh.getCon();
            PreparedStatement sth = myConnection.prepareStatement(sql);
            sth.setInt(1, id);
            ResultSet result = sth.executeQuery();
            if (result.next()) {
                cart.setCreationDate(result.getString("cartheaderdate"));
            }
            return cart;
        } catch (SQLException e) {
            return null;
        } finally {
            this.dbh.closeConnection();
        }
    }
    
    public ArrayList<CartLine> getCartLines(int cartHeaderId) {
        ArrayList<CartLine> cartLines = new ArrayList<CartLine>();
        String sql = "select * from cartline where cartheaderid = ?";
        try {
            Connection myConnection = this.dbh.getCon();
            PreparedStatement sth = myConnection.prepareStatement(sql);
            sth.setInt(1, cartHeaderId);
            ResultSet result = sth.executeQuery();
            while (result.next()) {
                CartLine cartLine = new CartLine();
                cartLine.setNet(result.getDouble("netprice"));
                cartLine.setSell(result.getDouble("sellprice"));
                cartLine.setQuantity(result.getInt("quantity"));
            }
        } catch (SQLException e) {
            return null;
        } finally {
            this.dbh.closeConnection();
        }
        return cartLines;
    }
    
    public ShoppingCart initializeShoppingCartFromCookie(String cookieValue) {
        Connection conn = this.dbh.getCon();
        ShoppingCart cart = new ShoppingCart();
        ArrayList<CartLine> lines = new ArrayList<CartLine>();
        try {
            PreparedStatement sth = conn.prepareStatement(COOKIECART);
            sth.setString(1, cookieValue);
            ResultSet result = sth.executeQuery();
            int count = 0;
            while (result.next()) {
                if (count == 0) {
                    count++;
                    cart.setId(result.getInt("cartheaderid"));
                    cart.setVersionId(result.getInt("h_versionId"));
                    cart.setCreationDate(result.getString("cartheaderdate"));                   
                }
                CartLine line = new CartLine();
                Product product = new Product();
                product.setProductId(result.getInt("productid"));
                product.setProductDescription(result.getString("productdescription"));
                product.setImageLocation(result.getString("image"));
                line.setProduct(product);
                line.setId(result.getInt("cartlineid"));
                line.setVersionId(result.getInt("l_versionId"));
                line.setQuantity(result.getInt("quantity"));
                line.setNet(result.getDouble("netprice"));
                line.setSell(result.getDouble("sellprice"));
                line.setTotal(line.getQuantity() * line.getSell());
                lines.add(line);
            }
            if (count == 0) {
                return null;
            }
            cart.setShoppingCartLines(lines);
            return cart;
            
        } catch (SQLException e) {
            return null;
        } finally {
            this.dbh.closeConnection();
        }
    }
    
    public void clearCarts(Connection conn) throws SQLException {
        try {
            PreparedStatement l_sth = conn.prepareStatement(CLEARLINES);
            l_sth.executeUpdate();
            PreparedStatement h_sth = conn.prepareStatement(CLEARHEADERS);
            h_sth.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException();
        }
    }
    
    public String deleteCartLine(int headerid, int lineid) {
        Connection conn =  this.dbh.getCon();
        try {
            conn.setAutoCommit(false);
            PreparedStatement sth = conn.prepareStatement(DELETELINE);
            sth.setInt(1, headerid);
            sth.setInt(2, lineid);
            sth.executeUpdate();
            PreparedStatement c_sth = conn.prepareStatement(GETLINES);
            c_sth.setInt(1, headerid);
            ResultSet result = c_sth.executeQuery();
            if (!result.next()) {
                PreparedStatement h_sth = conn.prepareStatement(DELETEHEADER);
                h_sth.setInt(1, headerid);
                h_sth.executeUpdate();
            }
            conn.commit();
            return "SUCCESS";
        } catch (SQLException e) {
            try {
                conn.rollback();
                return "Rolled back";
            } catch(SQLException ex) {
                return ex.getMessage();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
                this.dbh.closeConnection();
            } catch(SQLException exception) {
                return exception.getMessage();
            }           
        }
    }
    
    public void deleteCart(int id) {
        Connection conn = this.dbh.getCon();
        try {
            PreparedStatement l_sth = conn.prepareStatement(DELETELINEBYCARTID);
            l_sth.setInt(1, id);
            l_sth.executeUpdate();
            PreparedStatement h_sth = conn.prepareStatement(DELETEHEADER);
            h_sth.setInt(1, id);
            h_sth.executeUpdate();
        } catch (SQLException e) {
            
        } finally {
            this.dbh.closeConnection();
        }
    }
}
