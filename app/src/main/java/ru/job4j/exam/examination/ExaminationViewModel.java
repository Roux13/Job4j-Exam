package ru.job4j.exam.examination;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;

import ru.job4j.exam.entitties.Answer;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.entitties.Hint;
import ru.job4j.exam.entitties.Question;
import ru.job4j.exam.store.ExamRepository;

public class ExaminationViewModel extends AndroidViewModel implements ExaminationContract {

    private ExamRepository repository;

    public ExaminationViewModel(@NonNull Application application) {
        super(application);
        repository = new ExamRepository(application);
    }

    @Override
    public List<Question> getAllQuestionsByExam(Exam exam) {
        return repository.getAllQuestionsByExam(exam);
    }

    @Override
    public List<Answer> getAnswersByQuestion(Question question) {
        return repository.getAnswersByQuestion(question);
    }

    @Override
    public void updateExamResult(Exam exam) {
        repository.updateExamResult(exam);
    }

    @Override
    public List<Question> getAllQuestions() {
        return repository.getAllQuestions();
    }

    public Hint getHintByQuestion(Question question) {
        return repository.getHintByQuestion(question);
    }

}
