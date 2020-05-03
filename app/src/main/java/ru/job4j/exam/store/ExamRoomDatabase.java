package ru.job4j.exam.store;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.job4j.exam.dao.AnswerDao;
import ru.job4j.exam.dao.ExamDao;
import ru.job4j.exam.dao.HintDao;
import ru.job4j.exam.dao.QuestionDao;
import ru.job4j.exam.entitties.Answer;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.entitties.Hint;
import ru.job4j.exam.entitties.Question;

@Database(entities = {Exam.class, Question.class, Answer.class, Hint.class},
        version = 1, exportSchema = false)
public abstract class ExamRoomDatabase extends RoomDatabase {

    public abstract ExamDao examDao();

    public abstract QuestionDao questionDao();

    public abstract AnswerDao answerDao();

    public abstract HintDao hintDao();

    private static final String DB_NAME = "exam_db";

    private static volatile ExamRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ExamRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExamRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ExamRoomDatabase.class, DB_NAME)
                            .addCallback(examDBCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback examDBCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
//                            ExamDao examDao = INSTANCE.examDao();
//                            examDao.deleteAll();
//                            examDao.add(new Exam("Java Basic Exam"));
//                            examDao.add(new Exam("Android Basic Exam"));
//                            examDao.add(new Exam("SQL Fundamental Exam"));
//                            examDao.add(new Exam("Regular Expression Exam"));
//                            examDao.add(new Exam("Looooooooooooooonng Name Exam"));
                    }
            );
        }
    };
}
