package ru.job4j.exam.store;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.job4j.exam.entitties.Answer;
import ru.job4j.exam.entitties.Question;

public final class QuestionStore {

    private List<Question> questions = new ArrayList<Question>();

    private static final QuestionStore INST = new QuestionStore();

    private QuestionStore() {
        questions.add(new Question(
                1, "How many primitive variables does Java have?",
                Arrays.asList(
                        new Answer(1, "1.1"), new Answer(2, "1.2"),
                        new Answer(3, "1.3"), new Answer(4, "1.4")
                ), 4
        ));
        questions.add(new Question(
                2, "What is Java Virtual Machine?",
                Arrays.asList(
                        new Answer(1, "2.1"), new Answer(2, "2.2"),
                        new Answer(3, "2.3"), new Answer(4, "2.4")
                ), 4
        ));
        questions.add(new Question(
                3, "What is happen if we try unboxing null?",
                Arrays.asList(
                        new Answer(1, "3.1"), new Answer(2, "3.2"),
                        new Answer(3, "3.3"), new Answer(4, "3.4")
                ), 4
        ));
    }

    public static QuestionStore getInstance() {
        return INST;
    }

    public void add(Question question) {
        questions.add(question);
    }

    public Question get(int position) {
        return questions.get(position);
    }

    public int size() {
        return questions.size();
    }
}

