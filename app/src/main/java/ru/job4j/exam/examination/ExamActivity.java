package ru.job4j.exam.examination;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ru.job4j.exam.hint.HintActivity;
import ru.job4j.exam.R;
import ru.job4j.exam.result.ResultActivity;
import ru.job4j.exam.dialogs.ConfirmDialogListener;
import ru.job4j.exam.dialogs.ConfirmDialogFragment;
import ru.job4j.exam.entitties.Answer;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.entitties.Question;
import ru.job4j.exam.exams.ExamsActivity;
import ru.job4j.exam.exams.ExamsFragment;
import ru.job4j.exam.global.BaseActivity;
import ru.job4j.exam.utils.StringBundleKeys;

public class ExamActivity extends BaseActivity implements ExamListener, ConfirmDialogListener {

    private Bundle args;

    private ExaminationViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExaminationViewModel.class);
        getSupportActionBar().setHomeButtonEnabled(true);
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
        Fragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        Exam exam = (Exam) getIntent().getSerializableExtra(StringBundleKeys.SENT_EXAM_KEY);
        args.putSerializable(StringBundleKeys.SENT_EXAM_KEY, exam);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onPositiveDialogClick(DialogFragment dialog) {
        Intent intent = new Intent(this, HintActivity.class);
        intent.putExtra(StringBundleKeys.HINT_BUNDLE_KEY, this.args);
        startActivity(intent);
    }

    @Override
    public void onNegativeDialogCLick(DialogFragment dialog) {
        Toast.makeText(this, "Well done!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toHintScreen(Question question) {
        this.args = new Bundle();
        this.args.clear();
        this.args.putSerializable(StringBundleKeys.SENT_QUESTION_KEY, question);
        DialogFragment dialog = new ConfirmDialogFragment();
        Bundle dialogArgs = new Bundle();
        dialogArgs.putString(ConfirmDialogFragment.DIALOG_MESSAGE_KEY, getString(R.string.show_hint));
        dialog.setArguments(dialogArgs);
        dialog.show(getSupportFragmentManager(), "dialog_tag");
    }

    @Override
    public List<Question> getAllQuestionsByExam(Exam exam) {
        return viewModel.getAllQuestionsByExam(exam);
    }

    @Override
    public List<Answer> getAnswersByQuestion(Question question) {
        return viewModel.getAnswersByQuestion(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return viewModel.getAllQuestions();
    }

    @Override
    public void updateExamResult(Exam exam) {
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
