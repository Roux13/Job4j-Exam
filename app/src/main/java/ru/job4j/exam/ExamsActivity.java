package ru.job4j.exam;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ru.job4j.exam.dialogs.ConfirmDeletingDialogFragment;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.global.BaseActivity;
import ru.job4j.exam.global.ExamContract;
import ru.job4j.exam.global.ExamsFragmentListener;

public class ExamsActivity extends BaseActivity implements ExamsFragmentListener {

    private ExamContract viewModel;
    private FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExamViewModel.class);
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
        switchFragments(new AddExamFragment());
    }

    @Override
    public void callUpdateExamFragment(Exam exam) {
        Bundle args = new Bundle();
        args.putSerializable(ExamsFragment.SENT_EXAM_KEY, exam);
        Fragment fragment = new UpdateExamFragment();
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
}
