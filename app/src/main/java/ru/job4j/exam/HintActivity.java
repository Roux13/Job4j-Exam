package ru.job4j.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class HintActivity extends AppCompatActivity {

    private Map<Integer, String> answers = new HashMap<>();

    public HintActivity() {
        this.answers.put(0, "Hint 1");
        this.answers.put(1, "Hint 2");
        this.answers.put(2, "Hint 3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);
        String questionText = getIntent().getStringExtra(ExamActivity.QUESTION_TEXT);
        Toast.makeText(this, questionText, Toast.LENGTH_SHORT).show();
        TextView text = findViewById(R.id.hint);
        int question = getIntent().getIntExtra(ExamActivity.HINT_FOR, 0);
        text.setText(this.answers.get(question));
        Button back = findViewById(R.id.back);
        back.setOnClickListener(this::backBtn);

    }

    private void backBtn(View view) {
        onBackPressed();
    }
}
