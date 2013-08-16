/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.commerce.controller;

import com.commerce.controller.CardDepotAbstractActionBean;
import com.commerce.model.Product;
import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.SimpleMessage;
//import net.sourceforge.stripes.util.bean.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/**
 *
 * @author Tony
 */
public class XMLTestActionBean extends CardDepotAbstractActionBean {
    
    private static final String FORM = "/XMLTest.jsp";
    private Document document;
    private String rootNode;
    private NodeList nodeList;
    private ArrayList<Product> products = new ArrayList<Product>();
    
    @DefaultHandler
    public Resolution form() {
        return new ForwardResolution(FORM);
    }
    
    public Resolution readXml() {
        try {
            File file = new File("webapps/COMP461/xml/product.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            this.document = builder.parse(file);
            this.initializeTree();
            this.initializeProducts();
            //getContext().getMessages().add(new SimpleMessage("File can be parsed."));
            return new ForwardResolution(FORM);
        } catch (Exception e) {
            getContext().getMessages().add(new SimpleMessage(e.getMessage()));
            return new ForwardResolution(FORM);
        }
    }
    
    public void setDocument(Document document) {
        this.document = document;
    }
    
    public Document getDocument() {
        return this.document;
    }
    
    public void initializeTree() {
        this.rootNode = this.document.getDocumentElement().getNodeName();
        this.nodeList = this.document.getElementsByTagName("product");
    }
    
    public void setRootNode(String rootNode) {
        this.rootNode = rootNode;
    }
    
    public String getRootNode() {
        return this.rootNode;
    }
    
    public void setNodeList(NodeList nodeList) {
        this.nodeList = nodeList;
    }
    
    public NodeList getNodeList() {
        return this.nodeList;
    }
    
    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
    
    public ArrayList<Product> getProducts() {
        return this.products;
    }
    
    public void initializeProducts() {
        if (this.document == null) {
            this.products = null;
            
        } else {
            for (int i = 0; i < this.nodeList.getLength(); i++) {
                Product p = new Product();
                Node node = this.nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;
                    p.setProductId(new Integer(e.getElementsByTagName("productid").item(0).getTextContent()));
                    p.setProductDescription(e.getElementsByTagName("productdescription").item(0).getTextContent());
                    p.setOnHandQuantity(new Integer(e.getElementsByTagName("quantityonhand").item(0).getTextContent()));
                    p.setProductCost(new Double(e.getElementsByTagName("netprice").item(0).getTextContent()));
                    p.setProductList(new Double(e.getElementsByTagName("sellprice").item(0).getTextContent()));
                    p.setImageLocation(e.getElementsByTagName("image").item(0).getTextContent());
                    p.setVersionId(p.hashCode());
                    this.products.add(p);
                }
                
            }
            
        }
    }
    
}
