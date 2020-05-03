package ru.job4j.exam;

import androidx.fragment.app.Fragment;

import ru.job4j.exam.global.BaseActivity;

public class HintActivity extends BaseActivity {

    @Override
    public Fragment loadFrg() {
        return HintFragment.getInstance(getIntent().getIntExtra(ExamFragment.HINT_FOR, 0),
                getIntent().getStringExtra(ExamFragment.QUESTION_TEXT));
    }
}
