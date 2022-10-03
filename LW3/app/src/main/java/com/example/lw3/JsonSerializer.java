package com.example.lw3;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class JsonSerializer {
    String fileName = "persons.json";

    public Person ConvertFromJson(File file)
    {
        Gson gson = new Gson();
        Person person = new Person();

        try {
            FileReader fr = new FileReader(file);
            person = gson.fromJson(fr, Person.class);
            fr.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return person;
    }

    public void ConvertToJson(File file, Person person)
    {
        Gson gson = new Gson();

        try{
            FileWriter fw = new FileWriter(file);
            gson.toJson(person, fw);
            fw.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
