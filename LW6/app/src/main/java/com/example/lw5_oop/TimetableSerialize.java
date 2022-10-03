package com.example.lw5_oop;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

class TimetableSerialize {
    public static String fileName = "timeTable.json";

    static boolean exportToJSON(Context context, List<Timetable> dataList) {

        Gson gson = new Gson();
        DataItems dataItems = new DataItems();
        dataItems.setTables(dataList);
        String jsonString = gson.toJson(dataItems);

        FileOutputStream fileOutputStream = null;

        try {
            fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    Toast.makeText(context, "Ошибка экспорта", Toast.LENGTH_SHORT).show();
                }
            }
        }

        return false;
    }

    static List<Timetable> importFromJSON(Context context) {

        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(fileName);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            DataItems dataItems = gson.fromJson(streamReader, DataItems.class);
            return dataItems.getTables();
        } catch (IOException ex) {
            Toast.makeText(context, "Ошибка импорта", Toast.LENGTH_SHORT).show();
        } finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    private static class DataItems {
        private List<Timetable> tables;
        List<Timetable> getTables() {
            return tables;
        }
        void setTables(List<Timetable> tables) {
            this.tables = tables;
        }
    }
}
