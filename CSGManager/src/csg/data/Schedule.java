/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import java.util.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Soumya
 */
public class Schedule<E extends Comparable<E>>  implements Comparable<E> {
    
    private StringProperty type;
    private Date date;
    private StringProperty title;
    private StringProperty topic;
    private StringProperty time;
    private StringProperty link;
    private StringProperty criteria;
    
    public Schedule(String initType, Date initDate, String initTitle, String initTopic, String initTime, String initLink, String initCriteria){
        type = new SimpleStringProperty(initType);
        date = initDate;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public StringProperty getTime() {
        return time;
    }

    public void setTime(StringProperty time) {
        this.time = time;
    }

    public StringProperty getLink() {
        return link;
    }

    public void setLink(StringProperty link) {
        this.link = link;
    }

    public StringProperty getCriteria() {
        return criteria;
    }

    public void setCriteria(StringProperty criteria) {
        this.criteria = criteria;
    }
    
    
    public int compareTo(E otherTA) {
        return getDate().compareTo(((Schedule)otherTA).getDate());
    }
    
}
