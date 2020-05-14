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
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback examDBCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                        ExamDao examDao = INSTANCE.examDao();
                        QuestionDao questionDao = INSTANCE.questionDao();
                        AnswerDao answerDao = INSTANCE.answerDao();
                        HintDao hintDao = INSTANCE.hintDao();
                        if (questionDao.getById(1) == null) {
                            answerDao.deleteAll();
                            questionDao.deleteAll();
                            examDao.deleteAll();
                            hintDao.deleteAll();
                            examDao.add(new Exam(1, "Java Basic Exam",
                                    "Java is a high-level programming language originally" +
                                            " developed by Sun MicroSystems and released in 1995." +
                                            " Java runs on a variety of platforms, such as Windows," +
                                            " Mac OS, and the various versions of UNIX." +
                                            " This exam checks a basic understanding of Java."
                            ));
                            examDao.add(new Exam(2, "Android Basic Exam",
                                    "Android is a operating system on mobile phones" +
                                            " (and now it is also used on HD player, TV)," +
                                            " developed by Google and based on the Linux platform." +
                                            " Previously, Android was developed by Android  Company" +
                                            " (thereafter Google acquired in 2005).\n" +
                                            "\n" +
                                            "The developers write applications for Android based " +
                                            "on Java language." + "\n" +
                                            " This exam checks a basic understanding of Android(Java)."

                            ));
                            examDao.add(new Exam(3, "SQL Fundamental Exam",
                                    "The Structured Query Language is one of the fundamental" +
                                            " building blocks of modern database architecture." +
                                            " SQL defines the methods used to create and manipulate" +
                                            " relational databases on all major platforms." +
                                            " At first glance, the language may seem intimidating" +
                                            " and complex, but it's not all that difficult. " + "\n" +
                                            " This exam checks fundamental knowledge of SQL"
                            ));

                            //Java Exam
                            questionDao.add(new Question(1,
                                    "How many primitive variables does Java have?",
                                    1, 4, 0
                            ));
                            questionDao.add(new Question(2,
                                    "What is Java Virtual Machine?",
                                    1, 4, 1));
                            questionDao.add(new Question(3,
                                    "What is happen if we try unboxing null?",
                                    1, 2, 2));

                            answerDao.add(new Answer(1, "7", 1));
                            answerDao.add(new Answer(2, "5", 1));
                            answerDao.add(new Answer(3, "10", 1));
                            answerDao.add(new Answer(4, "8", 1));
                            answerDao.add(new Answer(5, "2.1", 2));
                            answerDao.add(new Answer(6, "2.2", 2));
                            answerDao.add(new Answer(7, "2.3", 2));
                            answerDao.add(new Answer(8, "2.4 Correct", 2));
                            answerDao.add(new Answer(9, "3.1", 3));
                            answerDao.add(new Answer(10, "3.2 Correct", 3));
                            answerDao.add(new Answer(11, "3.3", 3));
                            answerDao.add(new Answer(12, "3.4", 3));

                            hintDao.add(new Hint(1, "8", 1));
                            hintDao.add(new Hint(2, "Hint for second question", 2));
                            hintDao.add(new Hint(3, "Hint for third question", 3));

                            //Android Exam
                            questionDao.add(new Question(4,
                                    "Android Question 1?",
                                    2, 4, 0
                            ));
                            questionDao.add(new Question(5,
                                    "Android Question 2?",
                                    2, 4, 1));
                            questionDao.add(new Question(6,
                                    "Android Question 3?",
                                    2, 2, 2));

                            answerDao.add(new Answer(13, "1.1", 4));
                            answerDao.add(new Answer(14, "1.2", 4));
                            answerDao.add(new Answer(15, "1.3", 4));
                            answerDao.add(new Answer(16, "1.4 Correct", 4));
                            answerDao.add(new Answer(17, "2.1", 5));
                            answerDao.add(new Answer(18, "2.2", 5));
                            answerDao.add(new Answer(19, "2.3", 5));
                            answerDao.add(new Answer(20, "2.4 Correct", 5));
                            answerDao.add(new Answer(21, "3.1", 6));
                            answerDao.add(new Answer(22, "3.2 Correct", 6));
                            answerDao.add(new Answer(23, "3.3", 6));
                            answerDao.add(new Answer(24, "3.4", 6));

                            hintDao.add(new Hint(4, "Hint for first Android question", 4));
                            hintDao.add(new Hint(5, "Hint for second Android question", 5));
                            hintDao.add(new Hint(6, "Hint for third Android question", 6));

                            //SQL Exam
                            questionDao.add(new Question(7,
                                    "SQL Question 1?",
                                    3, 4, 0
                            ));
                            questionDao.add(new Question(8,
                                    "SQL Question 2?",
                                    3, 4, 1));
                            questionDao.add(new Question(9,
                                    "SQL Question 3?",
                                    3, 2, 2));

                            answerDao.add(new Answer(25, "1.1", 7));
                            answerDao.add(new Answer(26, "1.2", 7));
                            answerDao.add(new Answer(27, "1.3", 7));
                            answerDao.add(new Answer(28, "1.4 Correct", 7));
                            answerDao.add(new Answer(29, "2.1", 8));
                            answerDao.add(new Answer(30, "2.2", 8));
                            answerDao.add(new Answer(31, "2.3", 8));
                            answerDao.add(new Answer(32, "2.4 Correct", 8));
                            answerDao.add(new Answer(33, "3.1", 9));
                            answerDao.add(new Answer(34, "3.2 Correct", 9));
                            answerDao.add(new Answer(35, "3.3", 9));
                            answerDao.add(new Answer(36, "3.4", 9));

                            hintDao.add(new Hint(7, "Hint for first SQL question", 7));
                            hintDao.add(new Hint(8, "Hint for second SQL question", 8));
                            hintDao.add(new Hint(9, "Hint for third SQL question", 9));
                        }
                    }
            );
        }
    };
}
