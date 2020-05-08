package ru.job4j.exam.exams;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.job4j.exam.entitties.Answer;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.entitties.Question;
import ru.job4j.exam.global.ExamContract;
import ru.job4j.exam.store.ExamRepository;

public class ExamsViewModel extends AndroidViewModel implements ExamContract {

    private ExamRepository repository;
    private LiveData<List<Exam>> allExams;
    private LiveData<List<Question>> questionLiveData;
    private LiveData<List<Answer>> answerLiveData;

    public ExamsViewModel(@NonNull Application application) {
        super(application);
        repository = new ExamRepository(application);
        allExams = repository.getAllExams();
//        questionLiveData = repository.getAllQuestions();
    }

    @Override
    public void addExam(Exam exam) {
        repository.addExam(exam);
    }

    public LiveData<List<Exam>> getAllExams() {
        return this.allExams;
    }

    @Override
    public void updateExam(Exam exam) {
        repository.updateExam(exam);
    }

    @Override
    public void deleteExam(Exam exam) {
        repository.deleteExam(exam);
    }

    @Override
    public void deleteAllExams() {
        repository.deleteAllExams();
    }

    public List<Question> getAllQuestionsByExam(Exam exam) {
        return repository.getAllQuestionsByExam(exam);
    }

    public List<Question> getAllQuestions() {
        return repository.getAllQuestions();
    }

}
