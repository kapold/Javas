package by.bstu.javalab7;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "actions.db";
    private static final int SCHEMA = 1;
    static final String TABLE = "actions";
    // названия столбцов
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_ISFAVORITE = "isfavorite";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE actions (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_IMAGE + " TEXT, "
                + COLUMN_CATEGORY + " TEXT, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_ISFAVORITE + " INTEGER);");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE);
        onCreate(db);
    }

    public static ArrayList<Action> getActions(Context baseContext){
        ArrayList<Action> actions = new ArrayList<>();
        SQLiteDatabase db = baseContext.openOrCreateDatabase("actions.db", MODE_PRIVATE, null);
        Cursor query = db.rawQuery("SELECT * FROM actions;", null);
        while(query.moveToNext()){
            int id = query.getInt(0);
            String name = query.getString(1);
            String description = query.getString(2);
            String image = query.getString(3);
            String category = query.getString(4);
            String time = query.getString(5);
            int isFavorite = query.getInt(6);
            Action action = new Action(name, description, image, category, time, isFavorite);
            action.Id = id;
            actions.add(action);
        }
        query.close();
        db.close();
        return  actions;
    }

    public static void insertAction(Action action, Context baseContext){
        SQLiteDatabase db = baseContext.openOrCreateDatabase("actions.db", MODE_PRIVATE, null);
        db.execSQL("INSERT OR IGNORE INTO actions(name, description, image, category, time, isfavorite) VALUES ('" + action.Name + "'," +
                " '" + action.Description + "'," +
                " '" + action.Image + "'," +
                " '" + action.Category + "'," + action.Time + "," + action.IsFavorite +
                ");");
        db.close();
    }

    public static void deleteAction(Action action, Context baseContext){
        SQLiteDatabase db = baseContext.openOrCreateDatabase("actions.db", MODE_PRIVATE, null);
        db.execSQL("DELETE FROM actions WHERE id = " + action.Id + ";");
        db.close();
    }

    public static void deleteAction(int id, Context baseContext){
        SQLiteDatabase db = baseContext.openOrCreateDatabase("actions.db", MODE_PRIVATE, null);
        db.execSQL("DELETE FROM actions WHERE id = " + id + ";");
        db.close();
    }

    public static void updateAction(Action action, Context baseContext){
        SQLiteDatabase db = baseContext.openOrCreateDatabase("actions.db", MODE_PRIVATE, null);
        db.execSQL("UPDATE actions SET " +
                "name = '" + action.Name + "', " +
                "description = '" + action.Description + "', " +
                "image = '" + action.Image + "', " +
                "category = '" + action.Category + "', " +
                "time = " + action.Time + ", " +
                "isfavorite = " + action.IsFavorite + " " +
                "WHERE id = " + action.Id + " ;");
        db.close();
    }
}
