package com.mf.demo.test;

/**
 * Created by user on 2016/8/2.
 */
public class Advert {

    private  String city ;
    private  String title;
    private  String lable;
    private  String url;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Advert{" +
                "city='" + city + '\'' +
                ", title='" + title + '\'' +
                ", lable='" + lable + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
