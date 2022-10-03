package com.example.lw5_oop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DatabaseHelper(MainActivity.this);

        timetableList = new ArrayList<>();
        listView = findViewById(R.id.main_LW);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) {
                TextView textView = (TextView) itemClicked;
                String strText = textView.getText().toString(); // получаем текст нажатого элемента

                timeTableItem = timetableList.get(position);
                info();
            }
        });
        registerForContextMenu(listView);

        try {
            timetableList = dbHandler.getTimetables();
            ArrayAdapter<Timetable> adapter = new ArrayAdapter<Timetable>(this,
                    android.R.layout.simple_list_item_1, timetableList);
            listView.setAdapter(adapter);
        } catch (Exception e) {}
    }
    public List<Timetable> timetableList;
    Timetable timeTableItem;
    ListView listView;
    DatabaseHelper dbHandler;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    public void EditItem(int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Вы уверены что хотите изменить элемент?");
        alertDialog.setMessage("Восстановить элемент будет невозможно");

        alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                editItem(position);
            }
        });

        alertDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    public void DeleteItem(int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Вы уверены что хотите удалить элемент?");
        alertDialog.setMessage("Восстановить элемент будет невозможно");

        alertDialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteItem(position);
            }
        });

        alertDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {}
        });
        alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit:
                EditItem(info.position);
                // метод, выполняющий действие при редактировании пункта меню
                return true;
            case R.id.delete:
                DeleteItem(info.position); //метод, выполняющий действие при удалении пункта меню
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteItem(int position) {
        try {
            List<Timetable> tables = dbHandler.getTimetables();
            Timetable timetable = tables.get(position);
            dbHandler.deleteTimetable(String.valueOf(timetable.id));
        } catch (Exception e) { Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show(); }

        try {
            timetableList = dbHandler.getTimetables();
            ArrayAdapter<Timetable> adapter = new ArrayAdapter<Timetable>(this,
                    android.R.layout.simple_list_item_1, timetableList);
            listView.setAdapter(adapter);
        } catch (Exception e) {}
    }

    private void editItem(int position) {
        Intent intent = new Intent(this, ChangeActivity.class);
        intent.putExtra("changeItem", position);
        startActivity(intent);

        try {
            timetableList = dbHandler.getTimetables();
            ArrayAdapter<Timetable> adapter = new ArrayAdapter<Timetable>(this,
                    android.R.layout.simple_list_item_1, timetableList);
            listView.setAdapter(adapter);
        } catch (Exception e) {}
    }

    private void info() {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra("infoItem", timeTableItem);
        startActivity(intent);
    }

    public void create(MenuItem item) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

    public void search(MenuItem item) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void sort(MenuItem item) {
        List<Timetable> tables = dbHandler.getTimetables();
        try {
            Collections.sort(tables, new Comparator<Timetable>() {
                public int compare(Timetable o1, Timetable o2) {
                    return o1.toString().compareTo(o2.toString());
                }
            });
        } catch (Exception e) { Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show(); }

        try {
            ArrayAdapter<Timetable> adapter = new ArrayAdapter<Timetable>(this,
                    android.R.layout.simple_list_item_1, tables);
            listView.setAdapter(adapter);
        } catch (Exception e) { Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show(); }
    }
}