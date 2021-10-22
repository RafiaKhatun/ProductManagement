package com.example.productmanagement;

public class Items {
    private String productName;
    private String productCategory;
    private String productPrice;
    private String productQuantity;
    private String productCode;


public Items() {

}

public Items(String productName, String productCategory, String productPrice,String productQuantity, String productCode){

    this.productName=productName;
    this.productCategory=productCategory;
    this.productPrice=productPrice;
    this.productQuantity=productQuantity;
    this.productCode= productCode;
}
public String getProductName(){return productName;}
public String getProductCategory(){return productCategory;}
public String getProductPrice(){return productPrice;}
public String getProductQuantity(){return productQuantity;}
public String getProductCode(){return productCode;}

}