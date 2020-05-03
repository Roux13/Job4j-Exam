package ru.job4j.exam;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.global.ExamContract;
import ru.job4j.exam.store.ExamRepository;

public class ExamViewModel extends AndroidViewModel implements ExamContract {

    private ExamRepository repository;
    private LiveData<List<Exam>> allExams;

    public ExamViewModel(@NonNull Application application) {
        super(application);
        repository = new ExamRepository(application);
        allExams = repository.getAllExams();
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


}
