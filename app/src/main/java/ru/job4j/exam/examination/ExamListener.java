package ru.job4j.exam.examination;

import ru.job4j.exam.entitties.Exam;

public interface ExamListener extends ExaminationContract {

    void returnToExams();

    void toResultScreen(Exam exam);

}
