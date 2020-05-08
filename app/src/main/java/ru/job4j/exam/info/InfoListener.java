package ru.job4j.exam.info;

import ru.job4j.exam.entitties.Exam;

public interface InfoListener {

    void startExam(Exam exam);

    int getNumberOfQuestions(Exam exam);

}
