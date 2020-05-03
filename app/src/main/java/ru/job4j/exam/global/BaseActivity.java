package ru.job4j.exam.global;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ru.job4j.exam.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.activity_host) == null) {
            fm.beginTransaction()
                    .add(R.id.activity_host, loadFrg())
                    .commit();
        }
    }

    public abstract Fragment loadFrg();
}
