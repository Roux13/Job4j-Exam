package ru.job4j.exam.global;

import ru.job4j.exam.entitties.Exam;

public interface ExamsFragmentListener extends ExamContract {

    void callExamsFragment();

    void callAddExamFragment();

    void callUpdateExamFragment(Exam exam);

    void callConfirmDeletingDialog();
}
