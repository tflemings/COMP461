/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.model.Address;
import com.commerce.model.Customer;
import com.commerce.model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tony
 */
public class CustomerDAO {
    
    private static final String GETORDERS = "select h.orderheaderid, "
            + "cast(orderdate as date) as orderdate, h.ordertotal from "
            + "orderheader h where h.customerid = ?";
    private static final String CHANGE = "update customer set password = ? "
            + "where customerid = ?";
    
    private DatabaseController dbh;
    
    public CustomerDAO() {
        try {
            this.dbh = new DatabaseController();
        } catch (SQLException e) {
            
        }
        
    }
        
    public int recordExists(String username) {
        String sql = "select customerid from customer where username = ?";
        ArrayList<Integer> ids = new ArrayList<Integer>();
        try {
            Connection myConnection = this.dbh.getCon();
            PreparedStatement sth = myConnection.prepareStatement(sql);
            sth.setString(1, username);
            ResultSet result = sth.executeQuery();
            int count = 0;
            while (result.next()) {
                ids.add(result.getInt("customerid"));
                count++;
            }
            if (count == 1) {
                return ids.get(0);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            return -1;
        } 
    }
    
    public boolean recordExists(Customer customer) {
        String sql = "select customerid from customer where username = ? and customerid <> ?";
        boolean exists = false;
        try {
            Connection myConnection = this.dbh.getCon();
            PreparedStatement sth = myConnection.prepareStatement(sql);
            sth.setString(1, customer.getUserName());
            sth.setInt(2, customer.getUserId());
            ResultSet result = sth.executeQuery();
            if (result.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            exists = true;
        }
        return exists;
    }
    
    public int create(Customer customer) {        
        try {
            int recordExists = this.recordExists(customer.getUserName());
            if (recordExists != -1) {
                return 0;
            }
            String sql = "insert into customer (firstname, lastname, email, " +
                "username, `password`, versionId) values (?, ?, ?, ?, ?, ?)"; 
            customer.setVersionId(customer.hashCode());
            Connection myConnection = this.dbh.getCon();
            PreparedStatement sth = myConnection.prepareStatement(sql);
            sth.setString(1, customer.getFirstName());
            sth.setString(2, customer.getLastName());
            sth.setString(3, customer.getEmailAddress());
            sth.setString(4, customer.getUserName());
            sth.setString(5, customer.getPassword());
            sth.setInt(6, customer.getVersionId());
            sth.executeUpdate();
            customer.setUserId(this.recordExists(customer.getUserName()));
            this.createAddress(customer.getBillingAddress(), customer.getUserId());
            this.createAddress(customer.getShippingAddress(), customer.getUserId());
            return customer.getUserId();
        } catch (SQLException e) {
            return -45;
        }  
    }
    
    public int createAddress(Address address, int customerId) {
        PreparedStatement sth = null;
        String sql = "insert into address (versionId, customerid, addresstypeid, " +
                "address, city, state, zip) values (?, ?, ?, ?, ?, ?, ?)";
        address.setVersionId(address.hashCode());
        try {
            Connection myConnection = this.dbh.getCon();
            sth = myConnection.prepareStatement(sql);
            sth.setInt(1, address.getVersionId());
            sth.setInt(2, customerId);
            sth.setInt(3, address.getAddressType());
            sth.setString(4, address.getStreetAddress());
            sth.setString(5, address.getCity());
            sth.setString(6, address.getState());
            sth.setString(7, address.getZipCode());
            sth.executeUpdate();
            address.setId(this.readAddressIdByCustomerAndType(customerId, address.getAddressType()));
            return address.getId();
        } catch (SQLException e) {
            return -1;
        } 
    }
    
    public int readAddressIdByCustomerAndType(int customerId, int addressType) {
        String sql = "select addressid from address where customerid = ? and addresstypeid = ?";
        try {
            Connection myConnection = this.dbh.getCon();
            PreparedStatement sth = myConnection.prepareStatement(sql);
            sth.setInt(1, customerId);
            sth.setInt(2, addressType);
            ResultSet result = sth.executeQuery();
            if (result.next()) {
                return result.getInt("addressid");
            } else {
                return -1;
            }
        } catch (SQLException e) {
            return -1;
        }
    }
    
    public ArrayList<Customer> readAllCustomers() {
        String sql = "select * from customer";
        ArrayList<Customer> customerList = new ArrayList<Customer>();
        try {
            Connection myConnection = this.dbh.getCon();
            PreparedStatement sth = myConnection.prepareStatement(sql);
            ResultSet result = sth.executeQuery();
            while (result.next()) {
                Customer c = new Customer();
                c.setFirstName(result.getString("firstname"));
                c.setLastName(result.getString("lastname"));
                c.setUserId(result.getInt("customerid"));
                c.setUserName(result.getString("username"));
                c.setVersionId(result.getInt("versionId"));
                c.setEmailAddress(result.getString("email"));
                c.setCustomerStatusId(result.getInt("customerstatusid"));
                customerList.add(c);
            }
            return customerList;
        } catch (SQLException e) {
            return null;
        } 
    }
    
    public Customer readCustomerById(int id) {
        String sql = "select * from customer where customerid = ?";
        try {
            Connection myConnection = this.dbh.getCon();
            PreparedStatement sth = myConnection.prepareStatement(sql);
            sth.setInt(1, id);
            ResultSet result = sth.executeQuery();
            if (result.next()) {
                Customer c = new Customer();
                c.setFirstName(result.getString("firstname"));
                c.setLastName(result.getString("lastname"));
                c.setUserId(id);
                c.setUserName(result.getString("username"));
                c.setVersionId(result.getInt("versionId"));
                c.setEmailAddress(result.getString("email"));
                c.setCustomerStatusId(result.getInt("customerstatusid"));
                return c;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        } 
    }
    
    public Address readAddressByCustomerIdAndType(int type, int id) {
        String sql = "select * from address where addresstypeid = ? and customerid = ?";
        try {
            Connection myConnection = this.dbh.getCon();
            PreparedStatement sth = myConnection.prepareStatement(sql);
            sth.setInt(1, type);
            sth.setInt(2, id);
            ResultSet result = sth.executeQuery();
            if (result.next()) {
                Address a = new Address();
                a.setId(result.getInt("addressid"));
                a.setVersionId(result.getInt("versionId"));
                a.setStreetAddress(result.getString("address"));
                a.setCity(result.getString("city"));
                a.setState(result.getString("state"));
                a.setZipCode(result.getString("zip"));
                return a;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        } 
    }
    
    public DatabaseController getDbh() {
        return this.dbh;
    }
    
    public boolean checkPassword(Customer c) {
        String sql = "select * from customer where username = ? and password = ?";
        try {
            Connection myConnection = this.dbh.getCon();
            PreparedStatement sth = myConnection.prepareStatement(sql);
            sth.setString(1, c.getUserName());
            sth.setString(2, c.getPassword());
            ResultSet result = sth.executeQuery();
            if (result.next()) {
                c.setCustomerStatusId(result.getInt("customerstatusid"));
                c.setFirstName(result.getString("firstname"));
                c.setLastName(result.getString("lastname"));
                c.setVersionId(result.getInt("versionId"));
                c.setUserId(result.getInt("customerid"));
                c.setEmailAddress(result.getString("email"));
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean updateCustomer(Customer c) {
        String sql = "update customer set username = ?, firstname = ?, " +
                "lastname = ?, email = ? where customerid = ?";
        try {
            Connection myConnection = this.dbh.getCon();
            PreparedStatement sth = myConnection.prepareStatement(sql);
            sth.setString(1, c.getUserName());
            sth.setString(2, c.getFirstName());
            sth.setString(3, c.getLastName());
            sth.setString(4, c.getEmailAddress());
            sth.setInt(5, c.getUserId());
            if (!this.recordExists(c)) {
                sth.executeUpdate();
                return true;
            } else {
                this.revertCustomer(c);
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean updateAddress(Customer c) {
        return false;
    }
    
    public boolean revertCustomer(Customer c) {
        String sql = "select username, firstname, lastname, email from customer " +
                "where customerid = ?";
        try {
            Connection myConnection = this.dbh.getCon();
            PreparedStatement sth = myConnection.prepareStatement(sql);
            sth.setInt(1, c.getUserId());
            ResultSet result = sth.executeQuery();
            if (result.next()) {
                c.setUserName(result.getString("username"));
                c.setFirstName(result.getString("firstname"));
                c.setLastName(result.getString("lastname"));
                c.setEmailAddress(result.getString("email"));
                return true;
            }
            return false;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public ArrayList<Order> readOrdersByUserId(int id) {
        ArrayList<Order> orders = new ArrayList<Order>();
        Connection conn = this.dbh.getCon();
        try {
            PreparedStatement sth = conn.prepareStatement(GETORDERS);
            sth.setInt(1, id);
            ResultSet result = sth.executeQuery();
            while (result.next()) {
                Order order = new Order();
                order.setId(result.getInt("orderheaderid"));
                order.setDate(result.getString("orderdate"));
                order.setTotal(result.getDouble("ordertotal"));
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            return null;
        } finally {
            this.dbh.closeConnection();
        }
    }
    
    public void changePassword(Customer c, String password) {
        try {
            Connection conn = this.dbh.getCon();
            PreparedStatement sth = conn.prepareStatement(CHANGE);
            sth.setString(1, password);
            sth.setInt(2, c.getUserId());
            sth.executeUpdate();
        } catch (SQLException e) {
            
        } finally {
            this.dbh.closeConnection();
        }
    }
}
