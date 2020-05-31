package ru.job4j.exam.examination;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ru.job4j.exam.entitties.Answer;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.entitties.Question;
import ru.job4j.exam.exams.ExamsActivity;
import ru.job4j.exam.global.BaseActivity;
import ru.job4j.exam.result.ResultActivity;
import ru.job4j.exam.utils.StringBundleKeys;

public class ExamActivity extends BaseActivity implements ExamListener {

    private ExaminationViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExaminationViewModel.class);
    }

    @Override
    public Fragment loadFrg() {
        Exam exam = (Exam) getIntent().getSerializableExtra(StringBundleKeys.SENT_EXAM_KEY);
        return ExamFragment.getInstance(exam);
    }

    @Override
    public List<Question> getAllQuestionsByExam(Exam exam) {
        return viewModel.getAllQuestionsByExam(exam);
    }

    @Override
    public List<Answer> getAnswersByQuestion(Question question) {
        return viewModel.getAnswersByQuestion(question);
    }

    private void updateExamResult(Exam exam) {
        viewModel.updateExamResult(exam);
    }

    @Override
    public void returnToExams() {
        Intent intent = new Intent(this, ExamsActivity.class);
        startActivity(intent);
    }

    @Override
    public void toResultScreen(Exam exam) {
        updateExamResult(exam);
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(StringBundleKeys.SENT_EXAM_KEY, exam);
        startActivity(intent);
    }

}
