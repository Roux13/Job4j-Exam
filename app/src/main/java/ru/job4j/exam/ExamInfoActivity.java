package ru.job4j.exam;

import androidx.fragment.app.Fragment;

import ru.job4j.exam.global.BaseActivity;

public class ExamInfoActivity extends BaseActivity {

    @Override
    public Fragment loadFrg() {
        return new ExamInfoFragment();
    }
}
