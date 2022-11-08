package com.example.lw5_oop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class TimetableInfoFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TimetableInfoFragment() {}

    public static TimetableInfoFragment newInstance(String param1, String param2) {
        TimetableInfoFragment fragment = new TimetableInfoFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timetable_info, container, false);
    }

    TextView subName, subDayOfWeek, subWeek, subAudience, subBuilding, subTime, subTeacher, subTransferred;
    public void setSelectedItem(Timetable currentTimetable) {
        subName = getView().findViewById(R.id.name_info2);
        subDayOfWeek = getView().findViewById(R.id.dayOfWeek_info2);
        subWeek = getView().findViewById(R.id.week_info2);
        subAudience = getView().findViewById(R.id.audience_info2);
        subBuilding = getView().findViewById(R.id.building_info2);
        subTime = getView().findViewById(R.id.time_info2);
        subTeacher = getView().findViewById(R.id.teacher_info2);
        subTransferred = getView().findViewById(R.id.transferred_info2);

        subName.setText("Название: " + currentTimetable.name);
        subDayOfWeek.setText("День недели: " + currentTimetable.dayOfWeek);
        subWeek.setText("Неделя: " + currentTimetable.week);
        subAudience.setText("Аудитория: " + currentTimetable.audience);
        subBuilding.setText("Корпус: " + currentTimetable.building);
        subTime.setText("Время: " + currentTimetable.time);
        subTeacher.setText("Преподаватель: " + currentTimetable.teacher);
        if (currentTimetable.isOneTime)
            subTransferred.setText("Перенесена: Да");
        else
            subTransferred.setText("Перенесена: Нет");
    }
}
