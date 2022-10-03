package com.example.lw5_oop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        timetable = new Timetable();
        weekDay_sp = findViewById(R.id.dayOfWeek_SP);
        name_et = findViewById(R.id.name_ET);
        firstWeek_rb = findViewById(R.id.firstWeek_RB);
        secondWeek_rb = findViewById(R.id.secondWeek_RB);
        audience_et = findViewById(R.id.audience_ET);
        building_et = findViewById(R.id.building_ET);
        time_et = findViewById(R.id.time_ET);
        teacher_et = findViewById(R.id.teacher_ET);
        isOneTime = findViewById(R.id.oneTime_CB);

        dbHandler = new DatabaseHelper(AddActivity.this);
    }
    Timetable timetable;
    Spinner weekDay_sp;
    RadioButton firstWeek_rb, secondWeek_rb;
    EditText name_et, audience_et, building_et, time_et, teacher_et;
    CheckBox isOneTime;
    private DatabaseHelper dbHandler;

    public void AddTimetable_Btn(View view)
    {
        try {
            String name = name_et.getText().toString();
            String dayOfWeek = weekDay_sp.getSelectedItem().toString();
            String weekDay = "";
            if (firstWeek_rb.isChecked())
                weekDay = firstWeek_rb.getText().toString();
            else if (secondWeek_rb.isChecked())
                weekDay = secondWeek_rb.getText().toString();
            String audience = audience_et.getText().toString();
            String building = building_et.getText().toString();
            String time = time_et.getText().toString();
            String teacher = teacher_et.getText().toString();
            boolean iot;
            if (isOneTime.isChecked())
                iot = true;
            else
                iot = false;

            dbHandler.addNewTimetable(name, dayOfWeek, weekDay, audience, building, time, teacher, iot);

            Toast.makeText(this, "Расписание добавлено в базу", Toast.LENGTH_SHORT).show();
            name_et.setText("");
            audience_et.setText("");
            building_et.setText("");
            time_et.setText("");
            teacher_et.setText("");
            isOneTime.setChecked(false);
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
