package com.example.jpmc;

public class Bookk {
    private String Title;
    private String Author;
    private String Description;
    private String ImageUrl;

    public Bookk(String title, String author, String description, String imageUrl) {
        Title = title;
        Author = author;
        Description = description;
        ImageUrl = imageUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
