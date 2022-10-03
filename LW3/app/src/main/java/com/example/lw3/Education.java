package com.example.lw3;

import java.io.Serializable;

public class Education implements Serializable {
    public String institution;
    public String faculty;
    public String speciality;
    public String startDate;
    public String endDate;

    public Education()
    {
        institution = "";
        faculty = "";
        speciality = "";
        startDate = "";
        endDate = "";
    }

    public Education(String institution, String faculty, String speciality, String startDate, String endDate)
    {
        this.institution = institution;
        this.faculty = faculty;
        this.speciality = speciality;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
