package ru.alexsuvorov.paistewiki.model;

public class NewsPost {

    private String Title;
    private String Category;
    private String URL;
    private String Month;

    public NewsPost(String Title, String Category, String URL, String Month) {
        this.Title = Title;
        this.Category = Category;
        this.URL = URL;
        this.Month = Month;
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

    public String getMonth() {
        return Month;
    }

    public void setMonth(String month) {
        Month = month;
    }
}