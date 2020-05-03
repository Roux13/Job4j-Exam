package ru.job4j.exam.global;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.job4j.exam.entitties.Exam;

public interface ExamContract {

    void addExam(Exam exam);

    LiveData<List<Exam>> getAllExams();

    void updateExam(Exam exam);

    void deleteExam(Exam exam);

    void deleteAllExams();

}
