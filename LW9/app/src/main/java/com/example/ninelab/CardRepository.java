package com.example.ninelab;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CardRepository {
    private ContactCardDao CardDao;
    private LiveData<List<ContactCard>> allCards;

    CardRepository(Application application){
        DataBaseClass db = DataBaseClass.getInstance(application);
        CardDao = db.contactCardDao();
        allCards = CardDao.getAll();
    }

    LiveData<List<ContactCard>> getAllCards(){
        return allCards;
    }
    public void Insert(ContactCard card){
        CardDao.Insert(card);
    }
    public void Delete(ContactCard card){
        CardDao.Delete(card);
    }
}
