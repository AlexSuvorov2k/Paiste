package ru.alexsuvorov.paistewiki.model;

import java.util.List;

public class NewsMonth {

    private String mMonthName;
    private String mMonthURL;
    private List<NewsPost> mMonthPosts;

    public NewsMonth() {
    }

    public List<NewsPost> getPosts() {
        return mMonthPosts;
    }

    public void setMonthPosts(List<NewsPost> mMonthPosts) {
        this.mMonthPosts = mMonthPosts;
    }

    public void setMonthURL(String mMonthURL) {
        this.mMonthURL = mMonthURL;
    }

    public void setMonthName(String mMonthName) {
        this.mMonthName = mMonthName;
    }

    public String getMonthName() {
        return mMonthName;
    }

    public String getMonthURL() {
        return mMonthURL;
    }

}