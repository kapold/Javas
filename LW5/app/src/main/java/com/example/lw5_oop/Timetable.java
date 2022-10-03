package com.example.lw5_oop;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;
import java.sql.Time;

public class Timetable implements Serializable {
    public String name;
    public String dayOfWeek;
    public String week;
    public String audience;
    public String building;
    public String time;
    public String teacher;
    public boolean isOneTime;

    public Timetable() {}

    public Timetable(String name, String dayOfWeek, String week, String audience, String building, String time, String teacher, boolean isOneTime)
    {
        this.name = name;
        this.dayOfWeek = dayOfWeek;
        this.week = week;
        this.audience = audience;
        this.building = building;
        this.time = time;
        this.teacher = teacher;
        this.isOneTime = isOneTime;
    }

    @Override
    public String toString()
    {
        String result = String.format("Name: %s\nDay of week: %s\nWeek: %s\nAudience: %s\nBuilding: %s\nTime: %s\nTeacher: %s\n", name, dayOfWeek, week, audience, building, time, teacher);
        if (isOneTime)
            result += "Transferred";
        return result;
    }
}
