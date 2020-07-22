package com.example.myapplication;

public class cart {

    private String book_name, quantity, price;

    public cart() {
    }

    public cart(String book_name, String quantity, String Price) {
        this.book_name = book_name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

