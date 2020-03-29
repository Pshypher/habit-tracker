package com.example.android.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.habittracker.data.HabitTrackerContract.HabitTrackerEntry;

public class HabitTrackerDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "logs.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_HABIT_ENTRIES =
            "CREATE TABLE " + HabitTrackerEntry.TABLE_NAME + " (" +
                    HabitTrackerEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    HabitTrackerEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, " +
                    HabitTrackerEntry.COLUMN_HABIT_DATE + " INTEGER NOT NULL, " +
                    HabitTrackerEntry.COLUMN_HABIT_MONTH + " INTEGER NOT NULL, " +
                    HabitTrackerEntry.COLUMN_HABIT_YEAR + " INTEGER NOT NULL)";

    private static final String DELETE_HABIT_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitTrackerEntry.TABLE_NAME;

    public HabitTrackerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_HABIT_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_HABIT_ENTRIES);
        onCreate(db);
    }
}
