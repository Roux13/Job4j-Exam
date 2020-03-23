package ru.job4j.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

import ru.job4j.exam.store.QuestionStore;
import ru.job4j.exam.store.UserAnswersStore;

public class ExamActivity extends AppCompatActivity {

    private final QuestionStore store = QuestionStore.getInstance();
    private final UserAnswersStore answersStore = UserAnswersStore.getInstance();

    private int position = 0;
    private int correctAnswers = 0;

    private static final String POSITION_KEY = "position";
    public static final String HINT_FOR = "hint_for";
    public static final String QUESTION_TEXT = "question_text";
    public static final String CORRECT = "correct_answers";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.position = savedInstanceState.getInt(POSITION_KEY);
            this.correctAnswers = savedInstanceState.getInt(CORRECT);
        }
        setContentView(R.layout.activity_exam);
        this.fillForm();
        final Button next = findViewById(R.id.next);
        final Button hint = findViewById(R.id.hint);
        final Button previous = findViewById(R.id.previous);
        next.setOnClickListener(this::nextBtn);
        hint.setOnClickListener(this::hintBtn);
        previous.setOnClickListener(this::prevBtn);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        final RadioGroup variants = findViewById(R.id.variants);
        int id = variants.getCheckedRadioButtonId();
        answersStore.set(position, id);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION_KEY, position);
        outState.putInt(CORRECT, correctAnswers);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void fillForm() {
        findViewById(R.id.previous).setEnabled(position != 0);
        final TextView text = findViewById(R.id.question);
        Question question = this.store.get(this.position);
        text.setText(question.getText());
        final RadioGroup variants = findViewById(R.id.variants);
        for (int index = 0; index < variants.getChildCount(); index++) {
            RadioButton button = (RadioButton) variants.getChildAt(index);
            Option option = question.getOptions().get(index);
            button.setId(option.getId());
            button.setText(option.getText());
            button.setChecked(answersStore.get(position) == button.getId());
        }
    }

    private void showAnswer() {
        final RadioGroup variants = findViewById(R.id.variants);
        int id = variants.getCheckedRadioButtonId();
        Question question = this.store.get(this.position);
        Toast.makeText(
                this,
                String.format(
                        Locale.ENGLISH,
                        "Your answer is %d, correct is %d",
                        id, question.getAnswer()),
                Toast.LENGTH_SHORT)
                .show();
    }

    private void nextBtn(View view) {
        final RadioGroup variants = findViewById(R.id.variants);
        if (variants.getCheckedRadioButtonId() != -1) {
            answersStore.set(position, variants.getCheckedRadioButtonId());
            correctAnswers = countCorrectAnswers();
            showAnswer();
            if (position == store.size() - 1) {
                Intent intent = new Intent(ExamActivity.this, ResultActivity.class);
                intent.putExtra(CORRECT, correctAnswers);
//                intent.putExtra(STORE_SIZE, store.size());
                startActivity(intent);
            } else {
                position++;
                variants.clearCheck();
                fillForm();
            }
        }
    }

    private void hintBtn(View view) {
        Intent intent = new Intent(ExamActivity.this, HintActivity.class);
        intent.putExtra(HINT_FOR, position);
        intent.putExtra(QUESTION_TEXT, this.store.get(position).getText());
        startActivity(intent);
    }

    private void prevBtn(View view) {
        final RadioGroup variants = findViewById(R.id.variants);
        if (variants.getCheckedRadioButtonId() != -1) {
            answersStore.set(position, variants.getCheckedRadioButtonId());
            correctAnswers = countCorrectAnswers();
            position--;
            variants.clearCheck();
            fillForm();
        }
    }

    private int countCorrectAnswers() {
        int count = 0;
        for (int index = 0; index < answersStore.size(); index++) {
            if (answersStore.get(index) == store.get(index).getAnswer()) {
                count++;
            }
        }
        return count;
    }
}
