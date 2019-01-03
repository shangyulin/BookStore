package com.shang.admin.bookstore.Bean;

public class SimpleBookBean {
    private String isbn;
    private String title;
    private String subtitle;
    private String image;
    private String pubdate;
    private String price;
    private String author;

    public SimpleBookBean(String isbn, String title, String subtitle, String image, String pubdate, String price, String author) {
        this.isbn = isbn;
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
        this.pubdate = pubdate;
        this.price = price;
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
