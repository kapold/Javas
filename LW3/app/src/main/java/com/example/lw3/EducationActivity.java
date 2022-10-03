package com.example.lw3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class EducationActivity extends AppCompatActivity {
    private Intent nextIntent;
    Person person;
    String fileName = "persons.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        person = new Person();
        nextIntent = null;

        JsonSerializer js = new JsonSerializer();
        File file = new File(super.getFilesDir(), fileName);
        person = js.ConvertFromJson(file);
        EditText et1 = findViewById(R.id.institutionName_ET);
        EditText et2 = findViewById(R.id.facultyName_ET);
        EditText et3 = findViewById(R.id.specialityName_ET);
        EditText et4 = findViewById(R.id.startDate_ET);
        EditText et5 = findViewById(R.id.endDate_ET);
        et1.setText(person.pEducation.institution);
        et2.setText(person.pEducation.faculty);
        et3.setText(person.pEducation.speciality);
        et4.setText(person.pEducation.startDate);
        et5.setText(person.pEducation.endDate);
    }

    public void PreviousLayout_Btn(View view)
    {
        ChangeProgressBar(false);

        nextIntent = new Intent(this, MainActivity.class);
        startActivity(nextIntent);
        this.finish();
    }

    public void NextLayout_Btn(View view)
    {
        ChangeProgressBar(true);

        JsonSerializer js = new JsonSerializer();
        File file = new File(super.getFilesDir(), fileName);
        person = js.ConvertFromJson(file);

        EditText et1 = findViewById(R.id.institutionName_ET);
        EditText et2 = findViewById(R.id.facultyName_ET);
        EditText et3 = findViewById(R.id.specialityName_ET);
        EditText et4 = findViewById(R.id.startDate_ET);
        EditText et5 = findViewById(R.id.endDate_ET);
        person.pEducation.institution = et1.getText().toString();
        person.pEducation.faculty = et2.getText().toString();
        person.pEducation.speciality = et3.getText().toString();
        person.pEducation.startDate = et4.getText().toString();
        person.pEducation.endDate = et5.getText().toString();
        js.ConvertToJson(file, person);

        nextIntent = new Intent(this, JobActivity.class);
        startActivity(nextIntent);
        this.finish();
    }

    public void ChangeProgressBar(boolean crement) // 1 - инкремент, 0 - декремент
    {
        ProgressBar progressBar = findViewById(R.id.currentProgress2);
        if (crement)
        {
            progressBar.incrementProgressBy(1);
        }
        else if (!crement)
        {
            progressBar.incrementProgressBy(-1);
        }
    }
}
