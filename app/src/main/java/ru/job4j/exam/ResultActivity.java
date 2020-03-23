package ru.job4j.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Locale;

import ru.job4j.exam.store.UserAnswersStore;

public class ResultActivity extends AppCompatActivity {

    private final UserAnswersStore answersStore = UserAnswersStore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String questions = String.format(Locale.getDefault(),
                "%s: %d",
                getString(R.string.count_questions),
                answersStore.size());
        TextView countQuestions = findViewById(R.id.count_questions);
        countQuestions.setText(questions);
        String answers = String.format(Locale.getDefault(),
                "%s: %d",
                getString(R.string.correct_answers),
                getIntent().getIntExtra(ExamActivity.CORRECT, 0));
        TextView correctAnswers = findViewById(R.id.correct_answers);
        correctAnswers.setText(answers);
    }
}
