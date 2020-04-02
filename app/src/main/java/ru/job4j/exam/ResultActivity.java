package ru.job4j.exam;

import androidx.fragment.app.Fragment;

public class ResultActivity extends BaseActivity {

    @Override
    public Fragment loadFrg() {
        return ResultFragment.instanceOf(getIntent().getIntExtra(ExamFragment.CORRECT, 0));
    }
}
