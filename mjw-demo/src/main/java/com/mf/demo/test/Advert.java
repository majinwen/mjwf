package com.mf.demo.test;

/**
 * Created by user on 2016/8/2.
 */
public class Advert {

    private  String city ;
    private  String title;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private  String label;
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
                ", label='" + label + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
