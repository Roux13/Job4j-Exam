package ru.job4j.exam.store;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.job4j.exam.global.ExamContract;
import ru.job4j.exam.dao.AnswerDao;
import ru.job4j.exam.dao.ExamDao;
import ru.job4j.exam.dao.HintDao;
import ru.job4j.exam.dao.QuestionDao;
import ru.job4j.exam.entitties.Exam;

public class ExamRepository implements ExamContract {

    private ExamDao examDao;
    private QuestionDao questionDao;
    private AnswerDao answerDao;
    private HintDao hintDao;

    public ExamRepository(Application application) {
        ExamRoomDatabase db = ExamRoomDatabase.getDatabase(application);
        examDao = db.examDao();
        questionDao = db.questionDao();
        answerDao = db.answerDao();
        hintDao = db.hintDao();
    }

    public void addExam(Exam exam) {
        ExamRoomDatabase.databaseWriteExecutor.execute(() -> examDao.add(exam));
    }

    public LiveData<List<Exam>> getAllExams() {
        return examDao.getAll();
    }

    public void updateExam(Exam exam) {
        ExamRoomDatabase.databaseWriteExecutor.execute(() -> examDao.update(exam));
    }

    public void deleteExam(Exam exam) {
        ExamRoomDatabase.databaseWriteExecutor.execute(() -> examDao.deleteById(exam));
    }

    public void deleteAllExams() {
        ExamRoomDatabase.databaseWriteExecutor.execute(() -> examDao.deleteAll());
    }
}
