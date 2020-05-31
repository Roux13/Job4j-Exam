package ru.job4j.exam.store;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.job4j.exam.dao.AnswerDao;
import ru.job4j.exam.dao.ExamDao;
import ru.job4j.exam.dao.QuestionDao;
import ru.job4j.exam.entitties.Answer;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.entitties.Question;
import ru.job4j.exam.examination.ExaminationContract;
import ru.job4j.exam.global.ExamContract;

public class ExamRepository implements ExamContract, ExaminationContract{

    private final ExamDao examDao;
    private final QuestionDao questionDao;
    private final AnswerDao answerDao;

    public ExamRepository(Application application) {
        ExamRoomDatabase db = ExamRoomDatabase.getDatabase(application);
        examDao = db.examDao();
        questionDao = db.questionDao();
        answerDao = db.answerDao();
    }

    @Override
    public LiveData<List<Exam>> getAllExams() {
        return examDao.getAll();
    }

    public void updateExam(Exam exam) {
        ExamRoomDatabase.databaseWriteExecutor.execute(() -> examDao.update(exam));
    }

    @Override
    public List<Question> getAllQuestionsByExam(Exam exam) {
        return questionDao.getQuestionsByExamId(exam.getId());
    }

    @Override
    public List<Answer> getAnswersByQuestion(Question question) {
        return answerDao.getAnswersByQuestionId(question.getId());
    }

    public void updateExamResult(Exam exam) {
        updateExam(exam);
    }

}
