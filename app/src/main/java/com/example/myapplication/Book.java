package com.example.myapplication;

public class Book {

    private String name;
    private String description;
    private String image_url;
    private String book_url;
    private int book_price;

    public Book() {
    }

    public Book(String name, String description, String image_url, String book_url, int book_price) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.book_url = book_url;
        this.book_price = book_price;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getBook_url() {
        return book_url;
    }

    public void setBook_url(String book_url) {
        this.book_url = book_url;
    }

    public int getBook_price() {
        return book_price;
    }

    public void setBook_price(int book_price) {
        this.book_price = book_price;
    }
}
