package com.example.ninelab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Entity
public class ContactCard implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int ID;

    public String Name;
    public String SecondName;
    public String Workplace;
    public String Phone;
    public String URL;
    public String Photo;

    @Override
    public String toString() {
        return String.format(
                "Name: %s\n" +
                "Surname: %s\n" +
                "Work: %s\n" +
                "Phone: %s\n" +
                "Email: %s\n", Name, SecondName, Workplace, Phone, URL);
    }
}
