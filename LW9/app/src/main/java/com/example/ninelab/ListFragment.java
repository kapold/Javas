package com.example.ninelab;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.ninelab.AddFragment;
import com.example.ninelab.CardViewModel;
import com.example.ninelab.ContactCard;
import com.example.ninelab.R;
import com.example.ninelab.ContactCardAdapter;

import java.util.List;

public class ListFragment extends Fragment {
    ContactCardAdapter adapter;
    private CardViewModel cardViewModel;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        Filler();
        return rootView;
    }

    public void Filler(){
        RecyclerView recyclerView = rootView.findViewById(R.id.TaskRecycler);
        ContactCardAdapter.OnContactCardClickListener listener=new ContactCardAdapter.OnContactCardClickListener() {
            @Override
            public void onContactCardClick(ContactCard card, int position) {
                Fragment fr = new AddFragment();
                Bundle args = new Bundle();
                args.putSerializable("Card", cardViewModel.getByPosition(position));
                fr.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, fr).addToBackStack(null).commit();
            }
        };
        adapter = new ContactCardAdapter(getContext(),listener);
        recyclerView.setAdapter(adapter);
        cardViewModel=new ViewModelProvider(this).get(CardViewModel.class);
        cardViewModel.getAllCards().observe(getViewLifecycleOwner(), new Observer<List<ContactCard>>() {
            @Override
            public void onChanged(List<ContactCard> contactCards) {
                adapter.setAllCards(contactCards);
                Log.d("ExceptionLog", "onChanged: " + "asd");
            }
        });
        registerForContextMenu(recyclerView);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        int position = -1;
        try {
            position = adapter.getPosition();
        } catch (Exception e) {
            Log.d("ExceptionLog", e.getLocalizedMessage(), e);
            return super.onContextItemSelected(item);
        }
        cardViewModel.Delete(cardViewModel.getByPosition(position));
        return true;
    }
}