package ru.job4j.exam.exams;

import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.global.ExamContract;

public interface ExamsFragmentListener extends ExamContract {

    void callExamInfoActivity(Exam exam);

    int getNumberOfQuestions(Exam exam);
}
