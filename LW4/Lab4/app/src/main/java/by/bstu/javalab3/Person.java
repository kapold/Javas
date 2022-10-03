package by.bstu.javalab3;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;

public class Person {
    public String Name;
    public String Surname;
    public String Phone;
    public String Email;
    public String Vk;
    public String Image;

    public Person(String surname, String name, String phone, String email, String vk, String image){
        Name = name;
        Surname = surname;
        Phone = phone;
        Email = email;
        Vk = vk;
        Image = image;
    }

    @Override
    public String toString() {
        return Surname + " " + Name + "\n" + Phone + "\n" + Email + "\n" + Vk;
    }

    public static ArrayList<Person> collection = new ArrayList<Person>();

    public static void Serialize(File dir){
        try{
            Gson gson = new Gson();
            FileWriter fw = new FileWriter(new File(dir, "json.json"));
            String json = gson.toJson(Person.collection);
            gson.toJson(Person.collection, fw);
            fw.close();
        }
        catch(Exception ex){

        }

    }

    public static ArrayList<Person> Deserialize(File dir){
        ArrayList<Person> newCollection = new ArrayList<Person>();
        try{
            Gson gson = new Gson();
            FileReader fr = new FileReader(new File(dir, "json.json"));
            newCollection = gson.fromJson(fr, new TypeToken<ArrayList<Person>>(){}.getType());
            Person.collection = newCollection;
        }
        catch(Exception ex){

        }
        return newCollection;
    }
}
