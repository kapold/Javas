package com.example.lw3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class JobActivity extends AppCompatActivity {
    private Intent nextIntent;
    Person person;
    String fileName = "persons.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        person = new Person();
        nextIntent = null;
        JsonSerializer js = new JsonSerializer();
        File file = new File(super.getFilesDir(), fileName);
        person = js.ConvertFromJson(file);
        EditText et1 = findViewById(R.id.organizationName_ED);
        EditText et2 = findViewById(R.id.position_ED);
        EditText et3 = findViewById(R.id.jobStart_ED);
        EditText et4 = findViewById(R.id.jobEnd_ED);
        et1.setText(person.pJob.organization);
        et2.setText(person.pJob.position);
        et3.setText(person.pJob.jobStart);
        et4.setText(person.pJob.jobEnd);
    }

    public void PreviousLayout_Btn(View view)
    {
        ChangeProgressBar(false);

        nextIntent = new Intent(this, EducationActivity.class);
        startActivity(nextIntent);
        this.finish();
    }

    public void NextLayout_Btn(View view)
    {
        ChangeProgressBar(true);

        JsonSerializer js = new JsonSerializer();
        File file = new File(super.getFilesDir(), fileName);
        person = js.ConvertFromJson(file);

        EditText et1 = findViewById(R.id.organizationName_ED);
        EditText et2 = findViewById(R.id.position_ED);
        EditText et3 = findViewById(R.id.jobStart_ED);
        EditText et4 = findViewById(R.id.jobEnd_ED);
        person.pJob.organization = et1.getText().toString();
        person.pJob.position = et2.getText().toString();
        person.pJob.jobStart = et3.getText().toString();
        person.pJob.jobEnd = et4.getText().toString();
        js.ConvertToJson(file, person);

        nextIntent = new Intent(this, AcceptActivity.class);
        startActivity(nextIntent);
        this.finish();
    }

    public void ChangeProgressBar(boolean crement) // 1 - инкремент, 0 - декремент
    {
        ProgressBar progressBar = findViewById(R.id.currentProgress3);
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
