package com.example.lw5_oop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Timetables";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Couples";

    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String DAYOFWEEK_COL = "dayOfWeek";
    private static final String WEEK_COL = "week";
    private static final String AUDIENCE_COL = "audience";
    private static final String BUILDING_COL = "building";
    private static final String TIME_COL = "time";
    private static final String TEACHER_COL = "teacher";
    private static final String ISONETIME_COL = "isOneTime";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DAYOFWEEK_COL + " TEXT,"
                + WEEK_COL + " TEXT,"
                + AUDIENCE_COL + " TEXT,"
                + BUILDING_COL + " TEXT,"
                + TIME_COL + " TEXT,"
                + TEACHER_COL + " TEXT,"
                + ISONETIME_COL + " INTEGER)"; // кринж, в лайте нет булиана...
        db.execSQL(query);
    }

    public void addNewTimetable(String ttName, String ttDayOfWeek, String ttWeek, String ttAudience,
                                String ttBuilding, String ttTime, String ttTeacher, boolean ttIsOneTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, ttName);
        values.put(DAYOFWEEK_COL, ttDayOfWeek);
        values.put(WEEK_COL, ttWeek);
        values.put(AUDIENCE_COL, ttAudience);
        values.put(BUILDING_COL, ttBuilding);
        values.put(TIME_COL, ttTime);
        values.put(TEACHER_COL, ttTeacher);
        values.put(ISONETIME_COL, ttIsOneTime);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void changeTimetables(String savedID, String ttName, String ttDayOfWeek, String ttWeek, String ttAudience,
                                 String ttBuilding, String ttTime, String ttTeacher, boolean ttIsOneTime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NAME_COL, ttName);
        values.put(DAYOFWEEK_COL, ttDayOfWeek);
        values.put(WEEK_COL, ttWeek);
        values.put(AUDIENCE_COL, ttAudience);
        values.put(BUILDING_COL, ttBuilding);
        values.put(TIME_COL, ttTime);
        values.put(TEACHER_COL, ttTeacher);
        values.put(ISONETIME_COL, ttIsOneTime);

        db.update(TABLE_NAME, values, "id=?", new String[]{savedID});
        db.close();
    }

    public void deleteTimetable(String savedID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{savedID});
        db.close();
    }

    public ArrayList<Timetable> getTimetables() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<Timetable> tablesList = new ArrayList<>();

        if (cursorCourses.moveToFirst()) {
            do {
                int intValue = cursorCourses.getInt(8);
                boolean boolValue;
                if (intValue >= 1)
                    boolValue = true;
                else
                    boolValue = false;

                tablesList.add(new Timetable(
                        cursorCourses.getInt(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4),
                        cursorCourses.getString(5),
                        cursorCourses.getString(6),
                        cursorCourses.getString(7),
                        boolValue
                ));

            } while (cursorCourses.moveToNext());
        }
        cursorCourses.close();
        return tablesList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
