package ru.job4j.exam.store;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ExamBaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "exams.db";
    public static final int DB_VERSION = 1;

    public ExamBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + ExamDbSchema.ExamTable.TABLE_NAME + " ("
                        + ExamDbSchema.ExamTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ExamDbSchema.ExamTable.COLUMN_TITLE + " TEXT"
                        + ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
