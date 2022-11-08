package by.bstu.javalab7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DrawerLayout dl = findViewById(R.id.drawerLayout);
        NavigationView nv = findViewById(R.id.navigationView);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                dl.close();
                if(item.getItemId() == R.id.activityItem){
                    OpenAddActivity(item);
                }
                if(item.getItemId() == R.id.statsItem){
                    OpenStatsActivity(item);
                }
                return false;
            }
        });

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getWritableDatabase();
        //Action.Deserialize(getExternalCacheDir());
        ArrayList<Action> actions = DatabaseHelper.getActions(getBaseContext());

        RecyclerView actionsList = findViewById(R.id.actionsList);
        actionsList.setAdapter(new ActionAdapter(getApplicationContext(),actions, new OnItemClickListener() {
            @Override public void onItemClick(Action item) {
                int position = actions.indexOf(item);
                openEditActivity(position);
            }
        }));

        // DRAWER
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        dl.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete:
                //deleteItem(info.position); //метод, выполняющий действие при удалении пункта меню
                FragmentManager manager = getSupportFragmentManager();
                MyDialog myDialog = new MyDialog(info.position);
                myDialog.show(manager, "myDialog");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    public void OpenAddActivity(MenuItem item) {
        Intent i = new Intent(this, AddActivity.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }

    public void OpenStatsActivity(MenuItem item) {
        Toast.makeText(this, "Material Design\n\nLabaratory Work 8\n" +
                "Developer: Adamovich A.M.", Toast.LENGTH_LONG).show();
    }

    public void ShowFavorites(MenuItem item) {
        ArrayList<Action> favorites = new ArrayList<>();
        for(Action a : DatabaseHelper.getActions(getBaseContext())){
            if(a.IsFavorite == 1){
                favorites.add(a);
            }
        }
        ArrayList<Action> actions = DatabaseHelper.getActions(getBaseContext());

        RecyclerView actionsList = findViewById(R.id.actionsList);
        actionsList.setAdapter(new ActionAdapter(getApplicationContext(),favorites, new OnItemClickListener() {
            @Override public void onItemClick(Action item) {
                int position = actions.indexOf(item);
                openEditActivity(position);
            }
        }));
    }

    public void ShowAll(MenuItem item) {
        ArrayList<Action> actions = DatabaseHelper.getActions(getBaseContext());

        RecyclerView actionsList = findViewById(R.id.actionsList);
        actionsList.setAdapter(new ActionAdapter(getApplicationContext(),actions, new OnItemClickListener() {
            @Override public void onItemClick(Action item) {
                int position = actions.indexOf(item);
                openEditActivity(position);
            }
        }));
    }

    public void openEditActivity(int pos){
        Intent i = new Intent(this, EditActivity.class);
        i.putExtra("selectedIndex", pos);
        //i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
    }

    public void deleteItem(int pos){
        //Action.Collection.remove(pos);
        //Action.Serialize(getExternalCacheDir());
        ArrayList<Action> actions = DatabaseHelper.getActions(getBaseContext());
        int id = actions.get(pos).Id;
        DatabaseHelper.deleteAction(id, getBaseContext());
        Intent intent = new Intent(this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
    }
}