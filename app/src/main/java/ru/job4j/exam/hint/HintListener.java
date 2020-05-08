package ru.job4j.exam.hint;

import ru.job4j.exam.entitties.Hint;
import ru.job4j.exam.entitties.Question;

public interface HintListener {

    Hint getHintByQuestion(Question question);

}
