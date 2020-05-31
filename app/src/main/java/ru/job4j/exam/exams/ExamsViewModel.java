package ru.job4j.exam.exams;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.entitties.Question;
import ru.job4j.exam.global.ExamContract;
import ru.job4j.exam.store.ExamRepository;

public class ExamsViewModel extends AndroidViewModel implements ExamContract {

    private final ExamRepository repository;
    private final LiveData<List<Exam>> allExams;

    public ExamsViewModel(@NonNull Application application) {
        super(application);
        repository = new ExamRepository(application);
        allExams = repository.getAllExams();
    }

    public LiveData<List<Exam>> getAllExams() {
        return this.allExams;
    }

    public void updateExam(Exam exam) {
        repository.updateExam(exam);
    }

    public List<Question> getAllQuestionsByExam(Exam exam) {
        return repository.getAllQuestionsByExam(exam);
    }

}
