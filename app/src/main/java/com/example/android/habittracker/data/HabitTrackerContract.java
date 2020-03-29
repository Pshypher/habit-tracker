package com.example.android.habittracker.data;

import android.provider.BaseColumns;

public final class HabitTrackerContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private HabitTrackerContract() {}

    public static class HabitTrackerEntry implements BaseColumns {

        public static final String TABLE_NAME = "habit_tracker";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "task";
        public static final String COLUMN_HABIT_DATE = "date";
        public static final String COLUMN_HABIT_MONTH = "month";
        public static final String COLUMN_HABIT_YEAR = "year";

        public static final String TASK_FLOSS = "Flossed";
        public static final String TASK_RAN = "Ran a mile";
        public static final String TASK_WRITE = "Journaled";
        public static final String TASK_COMPOSE_SMS = "Texted a friend";
        public static final String TASK_PREPARE_LUNCH = "Packed a lunch";
        public static final String TASK_READ = "Read 30 mins";
        public static final String TASK_SORT_DESK_ITEMS = "Organized desk";
        public static final String TASK_MEDITATE = "Meditated";
    }
}
