package ru.job4j.exam.models;

import java.util.List;

public class Question {

    private int id;
    private String text;
    private List<Option> options;
    private int answer;

    public Question(int id, String text, List<Option> options, int answer) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public List<Option> getOptions() {
        return options;
    }

    public int getAnswer() {
        return answer;
    }
}
