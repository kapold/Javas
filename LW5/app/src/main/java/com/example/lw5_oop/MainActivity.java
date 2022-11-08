package com.example.lw5_oop;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TimetableListFragment.OnFragmentSendDataListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timetableList = new ArrayList<>();
        listView = findViewById(R.id.main_LW);
        File file = new File(super.getFilesDir(), fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Toast.makeText(this, "Ошибка создания файла", Toast.LENGTH_SHORT).show();
            }
        }

        // FRAGMENTS
        if (getResources().getDisplayMetrics().widthPixels > getResources().getDisplayMetrics().heightPixels){
            try {
                setContentView(R.layout.fragment_land);
                ChangeToFragments();
            }
            catch (Exception ex){
                Log.d("OnCreate Main: ", ex.getMessage());
            }
        }
        else{
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
                timetableList = TimetableSerialize.importFromJSON(this);
                ArrayAdapter<Timetable> adapter = new ArrayAdapter<Timetable>(this,
                        android.R.layout.simple_list_item_1, timetableList);
                listView.setAdapter(adapter);
            } catch (Exception e) {}
        }
    }
    public String fileName = "timeTable.json";
    public List<Timetable> timetableList;
    Timetable timeTableItem;
    ListView listView;

    public void up(MenuItem item){
        setContentView(R.layout.activity_main);

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
            timetableList = TimetableSerialize.importFromJSON(this);
            ArrayAdapter<Timetable> adapter = new ArrayAdapter<Timetable>(this,
                    android.R.layout.simple_list_item_1, timetableList);
            listView.setAdapter(adapter);
        } catch (Exception e) {}
    }

    @Override
    public void onSendData(Timetable timetable) {
        try {
            TimetableInfoFragment fragment = (TimetableInfoFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.lessonDirection);
            if (fragment != null)
                fragment.setSelectedItem(timetable);
        }
        catch (Exception ex){
            Log.d("OnSend main: ", ex.getMessage());
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    public void ChangeToFragments()
    {
        try {
            Fragment TimetableList = new TimetableListFragment();
            Fragment TimetableInfo = new TimetableInfoFragment();

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.lessonList, TimetableList);
                ft.add(R.id.lessonDirection, TimetableInfo);
                ft.commit();
        }
        catch (Exception ex){
            Log.d("ChangeToFr: ", ex.getMessage());
        }
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
                return true;
            case R.id.delete:
                DeleteItem(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteItem(int position) {
        try {
            List<Timetable> tables = TimetableSerialize.importFromJSON(this);
            tables.remove(position);
            TimetableSerialize.exportToJSON(this, tables);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        try {
            timetableList = TimetableSerialize.importFromJSON(this);

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
            timetableList = TimetableSerialize.importFromJSON(this);

            ArrayAdapter<Timetable> adapter = new ArrayAdapter<Timetable>(this,
                    android.R.layout.simple_list_item_1, timetableList);
            listView.setAdapter(adapter);
        } catch (Exception e) {
        }
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
        try {
            List<Timetable> tables = TimetableSerialize.importFromJSON(this);
            Collections.sort(tables, new Comparator<Timetable>() {
                public int compare(Timetable o1, Timetable o2) {
                    return o1.toString().compareTo(o2.toString());
                }
            });
            TimetableSerialize.exportToJSON(this, tables);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        try {
            timetableList = TimetableSerialize.importFromJSON(this);

            ArrayAdapter<Timetable> adapter = new ArrayAdapter<Timetable>(this,
                    android.R.layout.simple_list_item_1, timetableList);
            listView.setAdapter(adapter);
        } catch (Exception e) {
        }
    }
}