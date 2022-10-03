package com.example.lw3;

import java.io.Serializable;

public class Job implements Serializable {
    public String organization;
    public String position;
    public String jobStart;
    public String jobEnd;

    public Job()
    {
        organization = "";
        position = "";
        jobStart = "";
        jobEnd = "";
    }

    public Job(String organization, String position, String jobStart, String jobEnd)
    {
        this.organization = organization;
        this.position = position;
        this.jobStart = jobStart;
        this.jobEnd = jobEnd;
    }
}
