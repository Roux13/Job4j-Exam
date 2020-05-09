package ru.job4j.exam.info;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.job4j.exam.examination.ExamActivity;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.exams.ExamsViewModel;
import ru.job4j.exam.global.BaseActivity;
import ru.job4j.exam.utils.StringBundleKeys;

public class InfoActivity extends BaseActivity implements InfoListener {

    private ExamsViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExamsViewModel.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Fragment loadFrg() {
        Fragment fragment = new InfoFragment();
        fragment.setArguments(getIntent().getExtras());
        return fragment;
    }

    @Override
    public void startExam(Exam exam) {
        Intent intent = new Intent(this, ExamActivity.class);
        exam.setResult(0);
        exam.setTime(0);
        viewModel.updateExam(exam);
        intent.putExtra(StringBundleKeys.SENT_EXAM_KEY, exam);
        startActivity(intent);
    }

    @Override
    public int getNumberOfQuestions(Exam exam) {
        return viewModel.getAllQuestionsByExam(exam).size();
    }
}
