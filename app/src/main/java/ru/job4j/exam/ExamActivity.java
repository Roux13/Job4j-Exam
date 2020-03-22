package ru.job4j.exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import ru.job4j.exam.store.QuestionStore;

public class ExamActivity extends AppCompatActivity {

    private static final String TAG = "ExamActivity";

    private final QuestionStore store = QuestionStore.getInstance();
    private final String userAnswersKey = "userAnswers";
    private int[] userAnswers = new int[store.size()];

    private final String positionKey = "key";
    private int position = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.userAnswers = savedInstanceState.getIntArray(userAnswersKey);
            this.position = savedInstanceState.getInt(positionKey);
        }
        setContentView(R.layout.activity_exam);
        this.fillForm();
        final Button next = findViewById(R.id.next);
        final Button previous = findViewById(R.id.previous);
        next.setOnClickListener(this::nextBtn);
        previous.setOnClickListener(this::prevBtn);
        Log.d(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        final RadioGroup variants = findViewById(R.id.variants);
        int id = variants.getCheckedRadioButtonId();
        this.userAnswers[position] = id;
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray(userAnswersKey, userAnswers);
        outState.putInt(positionKey, position);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    private void fillForm() {
        findViewById(R.id.previous).setEnabled(position != 0);
        findViewById(R.id.next).setEnabled(position != store.size() - 1);
        final TextView text = findViewById(R.id.question);
        Question question = this.store.get(this.position);
        text.setText(question.getText());
        RadioGroup variants = findViewById(R.id.variants);
        for (int index = 0; index < variants.getChildCount(); index++) {
            RadioButton button = (RadioButton) variants.getChildAt(index);
            Option option = question.getOptions().get(index);
            button.setId(option.getId());
            button.setText(option.getText());
            button.setChecked(userAnswers[position] == button.getId());
        }
    }

    private void showAnswer() {
        RadioGroup variants = findViewById(R.id.variants);
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
            userAnswers[position] = variants.getCheckedRadioButtonId();
            showAnswer();
            position++;
            variants.clearCheck();
            fillForm();
        }
    }

    private void prevBtn(View view) {
        final RadioGroup variants = findViewById(R.id.variants);
        if (variants.getCheckedRadioButtonId() != -1) {
            userAnswers[position] = variants.getCheckedRadioButtonId();
            position--;
            variants.clearCheck();
            fillForm();
        }
    }
}
