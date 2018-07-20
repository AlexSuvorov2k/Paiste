package ru.alexsuvorov.paistewiki.model;

public class NewsPost {

    private String Title;
    private String Category;
    private String URL;

    public NewsPost(String Title, String Category, String URL) {
        this.Title = Title;
        this.Category = Category;
        this.URL = URL;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

}