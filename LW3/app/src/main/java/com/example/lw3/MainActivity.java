package com.example.lw3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        person = new Person();
        nextIntent = null;

        File file = new File(super.getFilesDir(), fileName);
        try {
            if (!file.exists())
                file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonSerializer js = new JsonSerializer();
        person = js.ConvertFromJson(file);
        EditText et1 = findViewById(R.id.name_ET);
        EditText et2 = findViewById(R.id.surname_ET);
        EditText et3 = findViewById(R.id.patronymic_ET);
        Spinner sp4 = findViewById(R.id.education_spinner);
        CheckBox cb5 = findViewById(R.id.hasExperience);
        et1.setText(person.name);
        et2.setText(person.surname);
        et3.setText(person.patronymic);
        if (person.hasExperience)
            cb5.setChecked(true);
    }
    public Person person;
    public String fileName = "persons.json";
    public Intent nextIntent;

    public void NextLayout_Btn(View view)
    {
        ChangeProgressBar(true);
        TextView tv1 = findViewById(R.id.name_ET);
        TextView tv2 = findViewById(R.id.surname_ET);
        TextView tv3 = findViewById(R.id.patronymic_ET);
        Spinner et4 = findViewById(R.id.education_spinner);
        CheckBox cb5 = findViewById(R.id.hasExperience);
        person.name = tv1.getText().toString();
        person.surname = tv2.getText().toString();
        person.patronymic = tv3.getText().toString();
        person.education = et4.getSelectedItem().toString();
        person.hasExperience = cb5.isChecked();

        JsonSerializer js = new JsonSerializer();
        File file = new File(super.getFilesDir(), fileName);
        js.ConvertToJson(file, person);

        nextIntent = new Intent(this, EducationActivity.class);
        startActivity(nextIntent);
        this.finish();
    }

    public void ChangeProgressBar(boolean crement) // 1 - инкремент, 0 - декремент
    {
        ProgressBar progressBar = findViewById(R.id.currentProgress);
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