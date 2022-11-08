package com.example.ninelab;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(ContactCard cards);

    @Query("SELECT * FROM contactcard")
    LiveData<List<ContactCard>> getAll();

    @Delete
    void Delete(ContactCard card);
}