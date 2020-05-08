package ru.job4j.exam.hint;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ru.job4j.exam.entitties.Hint;
import ru.job4j.exam.entitties.Question;
import ru.job4j.exam.examination.ExamActivity;
import ru.job4j.exam.examination.ExaminationViewModel;
import ru.job4j.exam.global.BaseActivity;
import ru.job4j.exam.utils.StringBundleKeys;

public class HintActivity extends BaseActivity implements HintListener {

    private ExaminationViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ExaminationViewModel.class);
    }

    @Override
    public Fragment loadFrg() {
        Bundle args = getIntent().getBundleExtra(StringBundleKeys.HINT_BUNDLE_KEY);
        return HintFragment.getInstance(args);
    }

    @Override
    public Hint getHintByQuestion(Question question) {
        return viewModel.getHintByQuestion(question);
    }
}
