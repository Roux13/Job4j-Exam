package ru.job4j.exam.result;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.examination.ExamActivity;
import ru.job4j.exam.exams.ExamsViewModel;
import ru.job4j.exam.exams.ExamsActivity;
import ru.job4j.exam.global.BaseActivity;
import ru.job4j.exam.utils.StringBundleKeys;

public class ResultActivity extends BaseActivity implements ResultListener {

    private ExamsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExamsViewModel.class);
    }

    @Override
    public Fragment loadFrg() {
        return ResultFragment.getInstance(getIntent().getExtras());
    }

    @Override
    public void startExam(Exam exam) {
        Intent intent = new Intent(this, ExamActivity.class);
        exam.setResult(0);
        exam.setTime(0);
        intent.putExtra(StringBundleKeys.SENT_EXAM_KEY, exam);
        startActivity(intent);
    }

    @Override
    public int getNumberOfQuestions(Exam exam) {
        return viewModel.getAllQuestionsByExam(exam).size();
    }

    @Override
    public void ToExamList() {
        Intent intent = new Intent(this, ExamsActivity.class);
        startActivity(intent);
    }
}
