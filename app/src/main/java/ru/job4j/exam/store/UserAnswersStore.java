package ru.job4j.exam.store;

import java.io.Serializable;

public final class UserAnswersStore implements Serializable {

    private int[] userAnswers;

//    private static final UserAnswersStore INST = new UserAnswersStore();

    public UserAnswersStore(int size) {
        userAnswers = new int[size];
    }

//    public static UserAnswersStore getInstance() {
//        return INST;
//    }

    public void set(int index, int answer) {
        userAnswers[index] = answer;
    }

    public int get(int index) {
        return userAnswers[index];
    }

    public int size() {
        return userAnswers.length;
    }

//    public void clear() {
//        for (int answer : userAnswers) {
//            answer = 0;
//        }
//    }
}
