package ru.job4j.exam.store;

import java.util.HashMap;
import java.util.Map;

public final class HintStore {

    private Map<Integer, String> answers = new HashMap<>();

    private static final HintStore INS = new HintStore();

    private HintStore() {
        this.answers.put(0, "Hint 1");
        this.answers.put(1, "Hint 2");
        this.answers.put(2, "Hint 3");
    }

    public static HintStore getInstance() {
        return INS;
    }

    public String get(int question) {
        return answers.get(question);
    }

    public int size() {
        return answers.size();
    }

}
