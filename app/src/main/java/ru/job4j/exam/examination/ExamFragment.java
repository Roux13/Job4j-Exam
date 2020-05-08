package ru.job4j.exam.examination;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import ru.job4j.exam.R;
import ru.job4j.exam.entitties.Answer;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.entitties.Question;
import ru.job4j.exam.exams.ExamsFragment;
import ru.job4j.exam.store.UserAnswersStore;
import ru.job4j.exam.utils.ExamTextFormat;
import ru.job4j.exam.utils.StringBundleKeys;

public class ExamFragment extends Fragment {

    private static final String POSITION_KEY = "position";
    public static final String CORRECT = "correct_answers";
    public static final String USER_ANSWERS = "user_answers";

    private List<Question> questions;
    private List<Answer> answers;
    private UserAnswersStore answersStore;

    private View view;
    private RadioGroup variants;

    private Exam exam;

    private int position = 0;
    private int correctAnswers = 0;

    private ExamListener listener;

    public ExamFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_exam, container, false);
        if (savedInstanceState != null) {
            this.position = savedInstanceState.getInt(POSITION_KEY);
            this.correctAnswers = savedInstanceState.getInt(CORRECT);
            this.answersStore = (UserAnswersStore) savedInstanceState.getSerializable(USER_ANSWERS);
        }

        exam = (Exam) getArguments().getSerializable(StringBundleKeys.SENT_EXAM_KEY);
        questions = listener.getAllQuestionsByExam(exam);
        answersStore = new UserAnswersStore(questions.size());

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
        int id = variants.getCheckedRadioButtonId();
        answersStore.set(position, id);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION_KEY, position);
        outState.putInt(CORRECT, correctAnswers);
        outState.putSerializable(USER_ANSWERS, answersStore);
    }

    private void fillForm() {
        this.view.findViewById(R.id.previous).setEnabled(position != 0);
        Button nextButton = this.view.findViewById(R.id.next);
        if (position == questions.size() - 1) {
            nextButton.setText(R.string.result_button);
        } else {
            nextButton.setText(R.string.next_button);
        }
        final TextView questionTextView = this.view.findViewById(R.id.question);
        Question question = questions.get(this.position);
        answers = listener.getAnswersByQuestion(question);
        questionTextView.setText(question.getText());
        variants = this.view.findViewById(R.id.variants);
        for (int index = 0; index < variants.getChildCount(); index++) {
            RadioButton radioButton = (RadioButton) variants.getChildAt(index);
            Answer answer = answers.get(index);
            radioButton.setId(index + 1);
            radioButton.setText(answer.getText());
            radioButton.setChecked(answersStore.get(position) == radioButton.getId());
        }
    }

    private void showAnswer() {
        final RadioGroup variants = this.view.findViewById(R.id.variants);
        int id = variants.getCheckedRadioButtonId();
        Question question = questions.get(this.position);
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
        if (variants.getCheckedRadioButtonId() != -1) {
            answersStore.set(position, variants.getCheckedRadioButtonId());
            correctAnswers = countCorrectAnswers();
            showAnswer();
            if (position == questions.size() - 1) {
                exam.setResult(correctAnswers);
                exam.setTime(System.currentTimeMillis());
                listener.toResultScreen(exam);
            } else {
                position++;
                variants.clearCheck();
                fillForm();
            }
        } else {
            Toast.makeText(getContext(),
                    "Please, select variant",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void hintBtn(View view) {
        listener.toHintScreen(questions.get(position));
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
            if (answersStore.get(index) == questions.get(index).getAnswer()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.listener = (ExamListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(ExamTextFormat.formatAttachExceptionMessage(
                    context.getClass().getSimpleName(),
                    listener.getClass().getSimpleName()));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.exam, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.to_exams) {
            listener.returnToExams();
            return true;
        } else if (id == android.R.id.home) {
            getActivity().onBackPressed();
            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
