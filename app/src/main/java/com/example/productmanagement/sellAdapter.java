package com.example.productmanagement;

public class sellAdapter {
    private String sproductCode;
    private String sproductName;
    private String sproductCategory;
    private String sproductPrice;
    private String sproductQuantity;
    private String ssellId;
    private String sadminEmail;

    public sellAdapter() {

    }
    public sellAdapter(String sproductCode, String sproductName, String sproductCategory, String sproductPrice, String sproductQuantity, String ssellId, String sadminEmail) {
        this.sproductCode = sproductCode;
        this.sproductName = sproductName;
        this.sproductCategory = sproductCategory;
        this.sproductPrice = sproductPrice;
        this.sproductQuantity = sproductQuantity;
        this.ssellId = ssellId;
        this.sadminEmail =sadminEmail;
    }


    public String getSproductCode() {
        return sproductCode;
    }

    public String getSproductName() {
        return sproductName;
    }

    public String getSproductCategory() {
        return sproductCategory;
    }

    public String getSproductPrice() {
        return sproductPrice;
    }

    public String getSproductQuantity() {
        return sproductQuantity;
    }

    public String getSsellId() {
        return ssellId;
    }

    public String getSadminEmail() {
        return sadminEmail;
    }
}
