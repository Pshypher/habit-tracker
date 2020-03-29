package com.example.android.habittracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitTrackerContract.HabitTrackerEntry;
import com.example.android.habittracker.data.HabitTrackerDbHelper;

import java.time.LocalDate;
import java.util.Random;

public class LogActivity extends AppCompatActivity {

    private HabitTrackerDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new HabitTrackerDbHelper(this);
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logs_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_insert_dummy_log) {
            insertHabit();
            displayDatabaseInfo();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void displayDatabaseInfo() {

        String[] projection = new String[] {
                HabitTrackerEntry._ID,
                HabitTrackerEntry.COLUMN_HABIT_NAME,
                HabitTrackerEntry.COLUMN_HABIT_DATE,
                HabitTrackerEntry.COLUMN_HABIT_MONTH,
                HabitTrackerEntry.COLUMN_HABIT_YEAR
        };

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query(
                    HabitTrackerEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            TextView displayTextView = (TextView) findViewById(R.id.habits_text_view);
            displayTextView.setText("There are " + cursor.getCount() +
                    " currently logged habit(s)\n\n");

            final String COLUMN_SEPERATOR = " - ";
            displayTextView.append((HabitTrackerEntry._ID + COLUMN_SEPERATOR +
                    HabitTrackerEntry.COLUMN_HABIT_NAME + COLUMN_SEPERATOR +
                    HabitTrackerEntry.COLUMN_HABIT_DATE + COLUMN_SEPERATOR +
                    HabitTrackerEntry.COLUMN_HABIT_MONTH + COLUMN_SEPERATOR +
                    HabitTrackerEntry.COLUMN_HABIT_YEAR + "\n"));

            while (cursor.moveToNext()) {
                int idColumnIndex = cursor.getColumnIndex(HabitTrackerEntry._ID);
                int taskColumnIndex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_HABIT_NAME);
                int dateColumnIndex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_HABIT_DATE);
                int monthColumnIndex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_HABIT_MONTH);
                int yearColumnIndex = cursor.getColumnIndex(HabitTrackerEntry.COLUMN_HABIT_YEAR);

                int id = cursor.getInt(idColumnIndex);
                String task = cursor.getString(taskColumnIndex);
                int date = cursor.getInt(dateColumnIndex);
                int month = cursor.getInt(monthColumnIndex);
                int year = cursor.getInt(yearColumnIndex);

                displayTextView.append(("\n" + id + COLUMN_SEPERATOR + task + COLUMN_SEPERATOR +
                        date + COLUMN_SEPERATOR + month + COLUMN_SEPERATOR + year));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void insertHabit() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitTrackerEntry.COLUMN_HABIT_NAME, getRandomHabit());

        LocalDate today = LocalDate.now();
        int date = today.getDayOfMonth();
        int month = today.getMonth().getValue();
        int year = today.getYear();

        values.put(HabitTrackerEntry.COLUMN_HABIT_DATE, date);
        values.put(HabitTrackerEntry.COLUMN_HABIT_MONTH, month);
        values.put(HabitTrackerEntry.COLUMN_HABIT_YEAR, year);

        db.insert(HabitTrackerEntry.TABLE_NAME, null, values);
    }

    private String getRandomHabit() {
        Random generator = new Random();
        String[] habits = new String[] {
                HabitTrackerEntry.TASK_FLOSS,
                HabitTrackerEntry.TASK_RAN,
                HabitTrackerEntry.TASK_WRITE,
                HabitTrackerEntry.TASK_COMPOSE_SMS,
                HabitTrackerEntry.TASK_PREPARE_LUNCH,
                HabitTrackerEntry.TASK_READ,
                HabitTrackerEntry.TASK_SORT_DESK_ITEMS,
                HabitTrackerEntry.TASK_MEDITATE
        };

        int selectedTaskIndex = generator.nextInt(habits.length);
        String selectedTask = habits[selectedTaskIndex];
        return selectedTask;
    }
}
