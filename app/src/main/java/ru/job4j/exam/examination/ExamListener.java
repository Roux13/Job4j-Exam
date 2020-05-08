package ru.job4j.exam.examination;

import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.entitties.Question;

public interface ExamListener extends ExaminationContract {

    void toHintScreen(Question question);

    void returnToExams();

    void toResultScreen(Exam exam);

}
