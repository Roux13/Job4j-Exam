package ru.job4j.exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

public class ExamsActivity extends BaseActivity {

    @Override
    public Fragment loadFrg() {
        return new ExamsFragment();
    }
}
