package com.example.lw5_oop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

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
        recyclerView = findViewById(R.id.main_LW);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
//                                    long id) {
//                TextView textView = (TextView) itemClicked;
//                String strText = textView.getText().toString(); // получаем текст нажатого элемента
//
//                timeTableItem = timetableList.get(position);
//                info();
//            }
//        });
        registerForContextMenu(recyclerView);

        TimetableAdapter.OnTableClickListener tableAdapter = new TimetableAdapter.OnTableClickListener() {
            @Override
            public void onTableClick(Timetable task, int position) {
                timeTableItem = task;
                info();
            }
        };

        try {
            timetableList = dbHandler.getTimetables();
//            ArrayAdapter<Timetable> adapter = new ArrayAdapter<Timetable>(this,
//                    android.R.layout.simple_list_item_1, timetableList);
//            listView.setAdapter(adapter);
            TimetableAdapter adapter = new TimetableAdapter(this, dbHandler.getTimetables(), tableAdapter);
            recyclerView.setAdapter(adapter);
        } catch (Exception e) {}

        // DRAWER
        drawerLayout = findViewById(R.id.DrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView nav = findViewById(R.id.NavMenu);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.AppInfoDrawer:{
                        Toast.makeText(MainActivity.this, "Версия: v.1.3\nСоздатель: Адамович Антон", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
                return true;
            }
        });
    }
    public List<Timetable> timetableList;
    Timetable timeTableItem;
    //ListView listView;
    RecyclerView recyclerView;
    DatabaseHelper dbHandler;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

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
//            ArrayAdapter<Timetable> adapter = new ArrayAdapter<Timetable>(this,
//                    android.R.layout.simple_list_item_1, timetableList);
//            listView.setAdapter(adapter);
        } catch (Exception e) {}
    }

    private void editItem(int position) {
        Intent intent = new Intent(this, ChangeActivity.class);
        intent.putExtra("changeItem", position);
        startActivity(intent);

        try {
            timetableList = dbHandler.getTimetables();
//            ArrayAdapter<Timetable> adapter = new ArrayAdapter<Timetable>(this,
//                    android.R.layout.simple_list_item_1, timetableList);
//            listView.setAdapter(adapter);
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
//            ArrayAdapter<Timetable> adapter = new ArrayAdapter<Timetable>(this,
//                    android.R.layout.simple_list_item_1, tables);
//            listView.setAdapter(adapter);
        } catch (Exception e) { Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show(); }
    }
}