package ru.job4j.exam;

import androidx.fragment.app.Fragment;

public class ExamsActivity extends BaseActivity {

    @Override
    public Fragment loadFrg() {
        return new ExamsFragment();
    }

}
