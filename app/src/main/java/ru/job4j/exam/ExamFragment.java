package ru.job4j.exam;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ru.job4j.exam.store.QuestionStore;
import ru.job4j.exam.store.UserAnswersStore;


/**
 * A simple {@link Fragment} subclass.
 */
public class ExamFragment extends Fragment {

    private final QuestionStore store = QuestionStore.getInstance();
    private final UserAnswersStore answersStore = UserAnswersStore.getInstance();

    private View view;

    private int position = 0;
    private int correctAnswers = 0;

    private static final String POSITION_KEY = "position";
    public static final String HINT_FOR = "hint_for";
    public static final String QUESTION_TEXT = "question_text";
    public static final String CORRECT = "correct_answers";

    public ExamFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.exam, container, false);
        if (savedInstanceState != null) {
            this.position = savedInstanceState.getInt(POSITION_KEY);
            this.correctAnswers = savedInstanceState.getInt(CORRECT);
        }

        this.fillForm();
        final Button nextButton = view.findViewById(R.id.next);
        final Button hintButton = view.findViewById(R.id.hint);
        final Button previousButton = view.findViewById(R.id.previous);
        nextButton.setOnClickListener(this::nextBtn);
        hintButton.setOnClickListener(this::hintBtn);
        previousButton.setOnClickListener(this::prevBtn);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        final RadioGroup variants = this.view.findViewById(R.id.variants);
        int id = variants.getCheckedRadioButtonId();
        answersStore.set(position, id);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION_KEY, position);
        outState.putInt(CORRECT, correctAnswers);
    }

    private void fillForm() {
        this.view.findViewById(R.id.previous).setEnabled(position != 0);
        Button nextButton = this.view.findViewById(R.id.next);
        if (position == store.size() - 1) {

            nextButton.setText(R.string.result_button);
        } else {
            nextButton.setText(R.string.next_button);
        }
        final TextView questionTextView = this.view.findViewById(R.id.question);
        Question question = this.store.get(this.position);
        questionTextView.setText(question.getText());
        final RadioGroup variantsRadioGroup = this.view.findViewById(R.id.variants);
        for (int index = 0; index < variantsRadioGroup.getChildCount(); index++) {
            RadioButton radioButton = (RadioButton) variantsRadioGroup.getChildAt(index);
            Option option = question.getOptions().get(index);
            radioButton.setId(option.getId());
            radioButton.setText(option.getText());
            radioButton.setChecked(answersStore.get(position) == radioButton.getId());
        }
    }

    private void showAnswer() {
        final RadioGroup variants = this.view.findViewById(R.id.variants);
        int id = variants.getCheckedRadioButtonId();
        Question question = this.store.get(this.position);
        Toast.makeText(
                getContext(),
                String.format(
                        Locale.ENGLISH,
                        "Your answer is %d, correct is %d",
                        id, question.getAnswer()),
                Toast.LENGTH_SHORT)
                .show();
    }

    private void nextBtn(View view) {
        final RadioGroup variants = this.view.findViewById(R.id.variants);
        if (variants.getCheckedRadioButtonId() != -1) {
            answersStore.set(position, variants.getCheckedRadioButtonId());
            correctAnswers = countCorrectAnswers();
            showAnswer();
            if (position == store.size() - 1) {
                Intent intent = new Intent(getContext(), ResultActivity.class);
                intent.putExtra(CORRECT, correctAnswers);
                startActivity(intent);
            } else {
                position++;
                variants.clearCheck();
                fillForm();
            }
        }
    }

    private void hintBtn(View view) {
        Intent intent = new Intent(getContext(), HintActivity.class);
        intent.putExtra(HINT_FOR, position);
        intent.putExtra(QUESTION_TEXT, this.store.get(position).getText());
        startActivity(intent);
    }

    private void prevBtn(View view) {
        final RadioGroup variants = this.view.findViewById(R.id.variants);
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
