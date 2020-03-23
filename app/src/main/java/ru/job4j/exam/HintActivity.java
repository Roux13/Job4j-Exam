package ru.job4j.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ru.job4j.exam.store.HintStore;

public class HintActivity extends AppCompatActivity {

    private final HintStore store = HintStore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        String questionText = getIntent().getStringExtra(ExamActivity.QUESTION_TEXT);
        Toast.makeText(this, questionText, Toast.LENGTH_SHORT).show();
        TextView text = findViewById(R.id.hint);
        int question = getIntent().getIntExtra(ExamActivity.HINT_FOR, 0);
        text.setText(this.store.get(question));
        Button back = findViewById(R.id.back);
        back.setOnClickListener(this::backBtn);

    }

    private void backBtn(View view) {
        onBackPressed();
    }
}
