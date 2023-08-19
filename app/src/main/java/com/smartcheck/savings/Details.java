package com.smartcheck.savings;

public class Details {
    public Details() {
    }

    String amount;
    String det;
    String date;
    String type;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Details(String amount, String det, String date, String type, String id) {

        this.amount = amount;
        this.det = det;
        this.date = date;
        this.type = type;
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDet() {
        return det;
    }

    public void setDet(String det) {
        this.det = det;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
