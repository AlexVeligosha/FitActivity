package com.example.hatsune.fitactivity.dto;

/**
 * Created by hatsune on 17.12.16.
 */

public class ActivityDTO {
    int duration;

    public ActivityDTO(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
