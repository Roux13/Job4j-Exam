package ru.job4j.exam.store;

import android.provider.BaseColumns;

public final class ExamDbSchema {

    private ExamDbSchema() {
    }

    public abstract static class ExamTable implements BaseColumns {

        public static final String TABLE_NAME = "exams";

        public static final String COLUMN_TITLE = "title";

    }

    public static final String CREATE_ENTRIES =
            "CREATE TABLE " + ExamDbSchema.ExamTable.TABLE_NAME + " ("
            + ExamTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ExamDbSchema.ExamTable.COLUMN_TITLE + " TEXT"
            + ");"
            ;
}
