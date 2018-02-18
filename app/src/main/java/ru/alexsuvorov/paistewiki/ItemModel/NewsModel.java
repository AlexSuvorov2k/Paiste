package ru.alexsuvorov.paistewiki.ItemModel;

public class NewsModel {

    private String Title;
    private String URL;

    public NewsModel(String Title, String URL) {
        this.Title = Title;
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

}
