package com.example.productmanagement;

public class saleMonthlyAdapter {
    private String dateOfcreate;
    private String nameOfproduct;
    private String priceOfproduct;
    private String quantityOfproduct;

    public saleMonthlyAdapter() {

    }
    public saleMonthlyAdapter(String dateOfcreate, String nameOfproduct, String priceOfproduct, String quantityOfproduct){
        this.dateOfcreate = dateOfcreate;
        this.nameOfproduct = nameOfproduct;
        this.priceOfproduct = priceOfproduct;
        this.quantityOfproduct = quantityOfproduct;
    }


    public String getDateOfcreate() {
        return dateOfcreate;
    }

    public String getNameOfproduct() {
        return nameOfproduct;
    }

    public String getPriceOfproduct() {
        return priceOfproduct;
    }

    public String getQuantityOfproduct() {
        return quantityOfproduct;
    }
}

