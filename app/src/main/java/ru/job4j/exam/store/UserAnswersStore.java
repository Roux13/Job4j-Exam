package ru.job4j.exam.store;

public final class UserAnswersStore {

    private final int[] userAnswers = new int[3];

    private static final UserAnswersStore INST = new UserAnswersStore();

    private UserAnswersStore() {
    }

    public static UserAnswersStore getInstance() {
        return INST;
    }

    public void set(int index, int answer) {
        userAnswers[index] = answer;
    }

    public int get(int index) {
        return userAnswers[index];
    }

    public int size() {
        return userAnswers.length;
    }
}
