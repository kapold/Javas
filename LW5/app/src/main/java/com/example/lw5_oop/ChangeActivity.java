package com.example.lw5_oop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ChangeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        timetableList = new ArrayList<>();
        timetable = new Timetable();
        Bundle arguments = getIntent().getExtras();
        position = (int) arguments.get("changeItem");

        try {
            timetableList = TimetableSerialize.importFromJSON(this);
        } catch (Exception e) {}
        timetable = timetableList.get(position);

        name_et = findViewById(R.id.name_change);
        audience_et = findViewById(R.id.audience_change);
        time_et = findViewById(R.id.time_change);
        building_et = findViewById(R.id.building_change);
        teacher_et = findViewById(R.id.teacher_change);
        dayOfWeek_sp = findViewById(R.id.dayOfWeek_C);
        isOneTime_cb = findViewById(R.id.oneTime_change);
        first_rb = findViewById(R.id.firstWeek_RBChange);
        second_rb = findViewById(R.id.secondWeek_RBChange);

        name_et.setText(timetable.name);
        audience_et.setText(timetable.audience);
        time_et.setText(timetable.time);
        teacher_et.setText(timetable.teacher);
        building_et.setText(timetable.building);
        if (timetable.isOneTime)
            isOneTime_cb.setChecked(true);

        Toast.makeText(this, timetable.week, Toast.LENGTH_SHORT).show();
        if (timetable.week == "Первая")
            first_rb.setChecked(true);
        else if (timetable.week == "Вторая")
            second_rb.setChecked(true);
        else
            first_rb.setChecked(true);
    }
    List<Timetable> timetableList;
    public int position;
    Timetable timetable;
    EditText name_et, audience_et, building_et, time_et, teacher_et;
    RadioButton first_rb, second_rb;
    Spinner dayOfWeek_sp;
    CheckBox isOneTime_cb;

    public void SetInfo()
    {
        timetable.name = name_et.getText().toString();
        timetable.audience = audience_et.getText().toString();
        timetable.time = time_et.getText().toString();
        timetable.building = building_et.getText().toString();
        timetable.teacher = teacher_et.getText().toString();
        timetable.dayOfWeek = dayOfWeek_sp.getSelectedItem().toString();
        if (first_rb.isChecked())
            timetable.week = "Первая";
        else if (second_rb.isChecked())
            timetable.week = "Вторая";

        if (isOneTime_cb.isChecked())
            timetable.isOneTime = true;
        else
            timetable.isOneTime = false;
    }

    public void changeBtn(View view) {
        SetInfo();
        timetableList.set(position, timetable);
        boolean result = TimetableSerialize.exportToJSON(this, timetableList);
        if (result) {
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
