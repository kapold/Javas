package com.example.ninelab;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CardViewModel extends AndroidViewModel {
    private CardRepository cardRepository;
    private LiveData<List<ContactCard>> allCards;

    public CardViewModel(Application application){
        super(application);
        cardRepository = new CardRepository(application);
        allCards = cardRepository.getAllCards();
    }

    public ContactCard getByPosition(int position){
        return allCards.getValue().get(position);
    }
    public LiveData<List<ContactCard>> getAllCards(){
        return allCards;
    }
    public void Insert(ContactCard card){
        cardRepository.Insert(card);
    }
    public void Delete(ContactCard card){
        cardRepository.Delete(card);
    }
}
