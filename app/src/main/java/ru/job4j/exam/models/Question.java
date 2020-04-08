package ru.job4j.exam.models;

import java.util.List;

import ru.job4j.exam.models.Option;

public class Question {

    private int id;
    private String text;
    private List<Option> options;
    private int Answer;

    public Question(int id, String text, List<Option> options, int answer) {
        this.id = id;
        this.text = text;
        this.options = options;
        Answer = answer;
    }

    public String getText() {
        return text;
    }

    public List<Option> getOptions() {
        return options;
    }

    public int getAnswer() {
        return Answer;
    }
}
