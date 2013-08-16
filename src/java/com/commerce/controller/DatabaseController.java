package com.commerce.controller;

import java.sql.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Tony
 */
public class DatabaseController {
    
    private static final String CONNECTIONSTRING = "jdbc:mysql://localhost/card_depot";
    //?user=root&amp;password=!passw0rd@DB
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String UID = "";
    private static final String PWD = "";
    private Connection con;
    private boolean acquired;
    private CardDepotAbstractActionBean owner;
    
    public DatabaseController() throws SQLException {
        this.con = this.getConnection(CONNECTIONSTRING, DRIVER, UID, PWD);
        this.acquired = false;
        this.owner = null;
    }
    
    public Connection getConnection(String connectionString, String driver, String uid, String pwd) {
        try {
            Class.forName(driver).newInstance();
            Connection con = DriverManager.getConnection(connectionString, uid, pwd);
            return con;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Connection getCon() {
        return this.con;
    }
    
    public boolean closeConnection() {
        try {
            if (this.con != null) {
                if (!this.con.isClosed()) {
                    this.con.close();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public boolean isConnected() {
        return this.con != null;
    }
    
    public boolean isAcquired() {
        return this.acquired;
    }
    
    public boolean acquireResource(CardDepotAbstractActionBean owner) {
        if (this.owner == null && this.isAcquired() == false) {
            this.owner = owner;
            this.acquired = true;
        }
        return this.isAcquired();
    }
    
    public boolean releaseResource() {
        this.acquired = false;
        this.owner = null;
        return !this.isAcquired();
    }
    
    public CardDepotAbstractActionBean getOwner() {
        return this.owner;
    }
}
