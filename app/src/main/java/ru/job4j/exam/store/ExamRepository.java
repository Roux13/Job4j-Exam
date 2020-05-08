package ru.job4j.exam.store;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.job4j.exam.entitties.Answer;
import ru.job4j.exam.entitties.Hint;
import ru.job4j.exam.entitties.Question;
import ru.job4j.exam.examination.ExaminationContract;
import ru.job4j.exam.global.ExamContract;
import ru.job4j.exam.dao.AnswerDao;
import ru.job4j.exam.dao.ExamDao;
import ru.job4j.exam.dao.HintDao;
import ru.job4j.exam.dao.QuestionDao;
import ru.job4j.exam.entitties.Exam;

public class ExamRepository implements ExamContract, ExaminationContract{

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

    @Override
    public void addExam(Exam exam) {
        ExamRoomDatabase.databaseWriteExecutor.execute(() -> examDao.add(exam));
    }

    @Override
    public LiveData<List<Exam>> getAllExams() {
        return examDao.getAll();
    }

    @Override
    public void updateExam(Exam exam) {
        ExamRoomDatabase.databaseWriteExecutor.execute(() -> examDao.update(exam));
    }

    @Override
    public void deleteExam(Exam exam) {
        ExamRoomDatabase.databaseWriteExecutor.execute(() -> examDao.deleteById(exam));
    }

    @Override
    public void deleteAllExams() {
        ExamRoomDatabase.databaseWriteExecutor.execute(() -> examDao.deleteAll());
    }

    @Override
    public List<Question> getAllQuestionsByExam(Exam exam) {
        return questionDao.getQuestionsByExamId(exam.getId());
    }

    @Override
    public List<Answer> getAnswersByQuestion(Question question) {
        return answerDao.getAnswersByQuestionId(question.getId());
    }

    @Override
    public void updateExamResult(Exam exam) {
        updateExam(exam);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionDao.getAllQuestion();
    }

    public Hint getHintByQuestion(Question question) {
        return hintDao.getHintByQuestionId(question.getId());
    }
}
