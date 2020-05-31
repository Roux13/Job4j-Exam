package ru.job4j.exam.global;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.job4j.exam.entitties.Exam;

public interface ExamContract {

    LiveData<List<Exam>> getAllExams();

}
