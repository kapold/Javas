package com.example.lw5_oop;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TimetableListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TimetableListFragment() {}

    interface OnFragmentSendDataListener {
        void onSendData(Timetable timetable);
    }

    private OnFragmentSendDataListener fragmentSendDataListener;
    @Override
    public void onAttach(Context context) { // Взаимодействуем с другим фрагментов через Activity
        super.onAttach(context);
        try {
            fragmentSendDataListener = (OnFragmentSendDataListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    public static TimetableListFragment newInstance(String param1, String param2) {
        TimetableListFragment fragment = new TimetableListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timetable_list, container, false);

        List<Timetable> timetableList = new ArrayList<>();
        ListView listView = view.findViewById(R.id.scheduleList);
        try {
            timetableList = TimetableSerialize.importFromJSON(getContext());
        } catch (Exception e) {}

        ArrayAdapter<Timetable> adapter = new ArrayAdapter<Timetable>(getContext(),
                android.R.layout.simple_list_item_1, timetableList);
        listView.setAdapter(adapter);
        registerForContextMenu(listView);

        // Inflate the layout for this fragment
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // получаем выбранный элемент
                Timetable selectedItem = (Timetable) parent.getItemAtPosition(position);
                // Посылаем данные Activity
                fragmentSendDataListener.onSendData(selectedItem);
            }
        });

        return view;
    }
}
