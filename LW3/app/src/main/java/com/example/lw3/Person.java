package com.example.lw3;

import java.io.Serializable;

public class Person implements Serializable {
    public String name;
    public String surname;
    public String patronymic;
    public String education;
    public boolean hasExperience;
    Education pEducation = new Education();
    Job pJob = new Job();

    public Person()
    {
        name = "";
        surname = "";
        patronymic = "";
        education = "";
        hasExperience = false;
    }

    public Person(String name, String surname, String patronymic, String education, boolean hasExperience)
    {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.education = education;
        this.hasExperience = hasExperience;
    }

    public String toString()
    {
        return ("Имя: " + name + "\n" +
                "Фамилия: " + surname + "\n" +
                "Отчество: " + patronymic + "\n\n" +
                "Место учебы: " + pEducation.institution + "\n" +
                "Факультет: " + pEducation.faculty + "\n" +
                "Специальность: " + pEducation.speciality + "\n" +
                "Начало обучения: " + pEducation.startDate + "\n" +
                "Конец обучения: " + pEducation.endDate + "\n\n" +
                "Место работы: " + pJob.organization + "\n" +
                "Должность: " + pJob.position + "\n" +
                "Начало работы: " + pJob.jobStart + "\n" +
                "Конец работы: " + pJob.jobEnd);
    }
}
