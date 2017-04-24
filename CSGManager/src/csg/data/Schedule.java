/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Soumya
 */
public class Schedule<E extends Comparable<E>>  implements Comparable<E> {
    
    private StringProperty type;
    private StringProperty date;
    private StringProperty title;
    private StringProperty topic;
    private StringProperty time;
    private StringProperty link;
    private StringProperty criteria;
    
    public Schedule(String initType, Date initDate, String initTitle, String initTopic, String initTime, String initLink, String initCriteria){
        type = new SimpleStringProperty(initType);
        Format df = new SimpleDateFormat("MM/dd/yyyy");
        date = new SimpleStringProperty(df.format(initDate));
        title = new SimpleStringProperty(initTitle);
        topic = new SimpleStringProperty(initTopic);
        time = new SimpleStringProperty(initTime);
        link = new SimpleStringProperty(initLink);
        criteria = new SimpleStringProperty(initCriteria);
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(Date date) {
        Format df = new SimpleDateFormat("MM/dd/yyyy");
        this.date.set(df.format(date));
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getTopic() {
        return topic.get();
    }

    public void setTopic(String topic) {
        this.topic.set(topic);
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(StringProperty time) {
        this.time = time;
    }

    public String getLink() {
        return link.get();
    }

    public void setLink(StringProperty link) {
        this.link = link;
    }

    public String getCriteria() {
        return criteria.get();
    }

    public void setCriteria(StringProperty criteria) {
        this.criteria = criteria;
    }
    
    
    public int compareTo(E otherTA) {
        return getDate().compareTo(((Schedule)otherTA).getDate());
    }
    
}
