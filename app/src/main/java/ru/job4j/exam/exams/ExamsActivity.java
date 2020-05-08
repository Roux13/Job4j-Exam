package ru.job4j.exam.exams;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ru.job4j.exam.info.InfoActivity;
import ru.job4j.exam.R;
import ru.job4j.exam.dialogs.ConfirmDeletingDialogFragment;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.global.BaseActivity;
import ru.job4j.exam.utils.StringBundleKeys;

public class ExamsActivity extends BaseActivity implements ExamsFragmentListener {

    private ExamsViewModel viewModel;
    private FragmentManager fm = getSupportFragmentManager();

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
    public void addExam(Exam exam) {
        viewModel.addExam(exam);
    }

    @Override
    public LiveData<List<Exam>> getAllExams() {
        return viewModel.getAllExams();
    }

    @Override
    public void updateExam(Exam exam) {
        viewModel.updateExam(exam);
    }

    @Override
    public void deleteExam(Exam exam) {
        viewModel.deleteExam(exam);
    }

    @Override
    public void deleteAllExams() {
        viewModel.deleteAllExams();
    }

    @Override
    public void callExamsFragment() {
        switchFragments(new ExamsFragment());
    }

    @Override
    public void callAddExamFragment() {
        switchFragments(new AddFragment());
    }

    @Override
    public int getNumberOfQuestions(Exam exam) {
        return viewModel.getAllQuestionsByExam(exam).size();
    }

    @Override
    public void callUpdateExamFragment(Exam exam) {
        Bundle args = new Bundle();
        args.putSerializable(StringBundleKeys.SENT_EXAM_KEY, exam);
        Fragment fragment = new UpdateFragment();
        fragment.setArguments(args);
        switchFragments(fragment);
    }

    @Override
    public void callConfirmDeletingDialog() {
        DialogFragment dialog = new ConfirmDeletingDialogFragment();
        dialog.show(fm, "delete_dialog_tag");
    }

    public void switchFragments(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(fragment.getClass().getName())
                .replace(R.id.activity_host, fragment)
                .commit();
    }

    @Override
    public void callExamInfoActivity(Exam exam) {
        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra(StringBundleKeys.SENT_EXAM_KEY, exam);
        startActivity(intent);
    }
}
