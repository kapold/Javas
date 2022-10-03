package com.example.lw3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class AcceptActivity extends AppCompatActivity {
    private Intent nextIntent;
    Person person;
    String fileName = "persons.json";
    TextView info_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept);

        person = new Person();
        nextIntent = null;
        info_tv = findViewById(R.id.personInfo_TV);

        JsonSerializer js = new JsonSerializer();
        File file = new File(super.getFilesDir(), fileName);
        person = js.ConvertFromJson(file);

        info_tv.setText(person.toString());
    }

    public void PreviousLayout_Btn(View view)
    {
        nextIntent = new Intent(this, JobActivity.class);
        startActivity(nextIntent);
        this.finish();
    }

    public void AcceptPerson(View view)
    {
        JsonSerializer js = new JsonSerializer();
        File file = new File(super.getFilesDir(), fileName);
        js.ConvertToJson(file, person);

        Toast.makeText(this, "Человек добавлен!", Toast.LENGTH_SHORT).show();
        nextIntent = new Intent(this, MainActivity.class);
        startActivity(nextIntent);
        this.finish();
    }
}
