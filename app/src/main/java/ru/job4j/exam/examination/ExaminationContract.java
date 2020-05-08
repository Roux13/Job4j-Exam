package ru.job4j.exam.examination;

import java.util.List;

import ru.job4j.exam.entitties.Answer;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.entitties.Question;

public interface ExaminationContract {

    List<Question> getAllQuestionsByExam(Exam exam);

    List<Answer> getAnswersByQuestion(Question question);

    void updateExamResult(Exam exam);

    List<Question> getAllQuestions();

}
