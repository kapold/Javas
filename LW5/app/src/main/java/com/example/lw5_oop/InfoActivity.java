package com.example.lw5_oop;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class InfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        try {
            timetable = new Timetable();
            Bundle arguments = getIntent().getExtras();
            timetable = (Timetable) arguments.get("infoItem");

            name_tv = findViewById(R.id.name_info);
            dayOfWeek_tv = findViewById(R.id.dayOfWeek_info);
            week_tv = findViewById(R.id.week_info);
            audience_tv = findViewById(R.id.audience_info);
            building_tv = findViewById(R.id.building_info);
            time_tv = findViewById(R.id.time_info);
            teacher_tv = findViewById(R.id.teacher_info);
            transferred_tv = findViewById(R.id.transferred_info);
            SetInfo(timetable);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    Timetable timetable;
    TextView name_tv, dayOfWeek_tv, week_tv, audience_tv, building_tv, time_tv, teacher_tv, transferred_tv;

    public void SetInfo(Timetable timetable)
    {
        name_tv.setText(String.format("Название: %s", timetable.name));
        dayOfWeek_tv.setText(String.format("День недели: %s", timetable.dayOfWeek));
        week_tv.setText(String.format("Неделя: %s", timetable.week));
        audience_tv.setText(String.format("Аудитория: %s", timetable.audience));
        building_tv.setText(String.format("Корпус: %s", timetable.building));
        time_tv.setText(String.format("Время: %s", timetable.time));
        teacher_tv.setText(String.format("Преподаватель: %s", timetable.teacher));
        if (timetable.isOneTime)
            transferred_tv.setText("Перенесено: Да");
        else
            transferred_tv.setText("Перенесено: Нет");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }
}
