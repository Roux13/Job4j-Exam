package ru.job4j.exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ru.job4j.exam.store.HintStore;

public class HintActivity extends BaseActivity {

    @Override
    public Fragment loadFrg() {
        return new HintFragment();
    }
}
