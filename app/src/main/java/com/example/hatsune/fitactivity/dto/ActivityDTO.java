package com.example.hatsune.fitactivity.dto;

import java.util.Date;

/**
 * Created by hatsune on 17.12.16.
 */

public class ActivityDTO {

    private String id;
    private String start;
    private String duration;
    private String user;

    public ActivityDTO() {
    }

    public ActivityDTO(String duration) {
        this.duration = duration;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
