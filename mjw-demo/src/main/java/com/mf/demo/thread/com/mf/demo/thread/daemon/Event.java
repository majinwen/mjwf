package com.mf.demo.thread.com.mf.demo.thread.daemon;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * Created by user on 2016/7/23.
 */
public class Event {
    private Date date;
    private String event;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Event{" +
                "date=" + date +
                ", event='" + event + '\'' +
                '}';
    }
}
