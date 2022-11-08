package com.example.lw5_oop;

import java.io.Serializable;

public class Timetable implements Serializable {
    public int id;
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

    public Timetable(int id, String name, String dayOfWeek, String week, String audience, String building, String time, String teacher, boolean isOneTime)
    {
        this.id = id;
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
        return String.format("Name: %s\nDay of week: %s\nWeek: %s\n", name, dayOfWeek, week);
    }

    public String getInfo()
    {
        String result = String.format("Name: %s\nDay of week: %s\nWeek: %s\nAudience: %s\nBuilding: %s\nTime: %s\nTeacher: %s\n", name, dayOfWeek, week, audience, building, time, teacher);
        if (isOneTime)
            result += "Transferred";
        return result;
    }
}
