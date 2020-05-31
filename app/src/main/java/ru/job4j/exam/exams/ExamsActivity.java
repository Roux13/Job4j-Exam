package ru.job4j.exam.exams;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.global.BaseActivity;
import ru.job4j.exam.info.InfoActivity;
import ru.job4j.exam.utils.StringBundleKeys;

public class ExamsActivity extends BaseActivity implements ExamsFragmentListener {

    private ExamsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExamsViewModel.class);
    }

    @Override
    public Fragment loadFrg() {
        return new ExamsFragment();
    }

    @Override
    public LiveData<List<Exam>> getAllExams() {
        return viewModel.getAllExams();
    }


    @Override
    public int getNumberOfQuestions(Exam exam) {
        return viewModel.getAllQuestionsByExam(exam).size();
    }

    @Override
    public void callExamInfoActivity(Exam exam) {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra(StringBundleKeys.SENT_EXAM_KEY, exam);
        startActivity(intent);
    }
}
