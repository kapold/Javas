package com.example.ninelab;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ContactCard.class},version = 1)
public abstract class DataBaseClass extends RoomDatabase {
    private static final String DB_NAME = "ContactCardDataBase.db";
    private static DataBaseClass instance;

    public static synchronized DataBaseClass getInstance(Context context){
        if(instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), DataBaseClass.class, DB_NAME)
                    .allowMainThreadQueries().build();
        return instance;
    }

    public abstract ContactCardDao contactCardDao();
}
