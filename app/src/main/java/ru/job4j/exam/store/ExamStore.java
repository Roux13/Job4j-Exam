package ru.job4j.exam.store;

import java.util.ArrayList;
import java.util.List;

import ru.job4j.exam.models.Exam;

public class ExamStore {

    private static ExamStore INSTANCE;

    private final List<Exam> store = new ArrayList<>();

    private ExamStore() {
        for (int index = 0; index != 3; index++) {
            store.add(new Exam(index, String.format("Java %s", index), System.currentTimeMillis(), index));
        }
    }

    public static ExamStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ExamStore();
        }
        return INSTANCE;
    }

    public List<Exam> getList() {
        return this.store;
    }

    public Exam get(int index) {
        return this.store.get(index);
    }

    public void add(Exam exam) {
        this.store.add(exam);
    }

    public void deleteAll() {
        store.clear();
    }

    public int size() {
        return this.store.size();
    }
}
