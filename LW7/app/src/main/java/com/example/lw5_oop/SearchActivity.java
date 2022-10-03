package com.example.lw5_oop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dbHandler = new DatabaseHelper(SearchActivity.this);
    }
    DatabaseHelper dbHandler;

    public void search(View view) {
        try {
            EditText et = findViewById(R.id.search);
            TextView out = findViewById(R.id.list);
            String text = et.getText().toString();
            out.setText("");
            String outtext = "";
            List<Timetable> lp = openFromExt();
            for (Timetable itVar : lp) {
                if (text.equals(itVar.name))
                    outtext += itVar.toString() + "\n";
            }
            out.setText(outtext);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public List<Timetable> openFromExt() {
        List<Timetable> tables = new ArrayList<Timetable>();
        try {
            tables = dbHandler.getTimetables();
            TextView out = (TextView) findViewById(R.id.list);
            String text = new String(tables != null ? tables.toString() : "");
            out.setText(text);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return tables;
    }

    public void prevAct(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
