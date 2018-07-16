package ru.alexsuvorov.paistewiki.model;

import java.util.ArrayList;
import java.util.List;

public class NewsMonth {

    private String mMonthURL;
    private String mMonthName;
    private List<NewsPost> mPost;

    public NewsMonth() {
        mPost = new ArrayList<NewsPost>();
    }

    public NewsMonth(List<NewsPost> posts, String monthURL) {
        mPost = posts;
        mMonthURL = monthURL;
    }

    public List<NewsPost> getPosts() {
        return mPost;
    }

    public void addPost(List<NewsPost> mPost) {
        this.mPost = mPost;
    }

    public String getMonthName() {
        return mMonthName;
    }

    public String getMonthURL() {
        return mMonthURL;
    }

}
