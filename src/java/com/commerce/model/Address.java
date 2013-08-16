/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.model;

import java.io.Serializable;

/**
 *
 * @author Tony
 */
public class Address implements Serializable {
    
    private int id;
    private int versionId;
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private int addressType;
    
    public Address() {
        
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getVersionId() {
        return this.versionId;
    }
    
    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }
    
    public String getStreetAddress() {
        return this.streetAddress;
    }
    
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getZipCode() {
        return this.zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public int getAddressType() {
        return this.addressType;
    }
    
    public void setAddressType(int type) {
        this.addressType = type;
    }
    
    public boolean isComplete() {
        return (this.streetAddress != null && this.city != null &&
                this.state != null && this.zipCode != null);
    }
    
    @Override
    public int hashCode() {
        return new Integer(this.addressType).hashCode() + this.streetAddress.hashCode() +
                this.city.hashCode() + this.state.hashCode() + this.zipCode.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.versionId != other.versionId) {
            return false;
        }
        if ((this.streetAddress == null) ? (other.streetAddress != null) : !this.streetAddress.equals(other.streetAddress)) {
            return false;
        }
        if ((this.city == null) ? (other.city != null) : !this.city.equals(other.city)) {
            return false;
        }
        if ((this.state == null) ? (other.state != null) : !this.state.equals(other.state)) {
            return false;
        }
        if ((this.zipCode == null) ? (other.zipCode != null) : !this.zipCode.equals(other.zipCode)) {
            return false;
        }
        if (this.addressType != other.addressType) {
            return false;
        }
        return true;
    }
    
    public String toString() {
        return "versionId: " + this.getVersionId() + "\n" +
               "Street Address:" + this.getStreetAddress() + "\n" +
               "City: " + this.getCity() + "\n" +
               "State: " + this.getState() + "\n" +
               "Zipcode: " + this.getZipCode();
    }
    
    public boolean isHashable() {
        return (this.id > 0 && this.versionId == 0 && this.streetAddress != null &&
                this.city != null && this.state != null && this.zipCode != null &&
                (this.addressType == 0 || this.addressType == 1));
    }
    
    
}
