package ru.job4j.exam.examination;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.job4j.exam.R;
import ru.job4j.exam.entitties.Answer;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.entitties.Question;
import ru.job4j.exam.store.UserAnswersStore;
import ru.job4j.exam.utils.ExamTextFormat;
import ru.job4j.exam.utils.StringBundleKeys;

public class ExamFragment extends Fragment {

    private static final String POSITION_KEY = "position";
    public static final String CORRECT_KEY = "correct_answers";
    public static final String USER_ANSWERS_KEY = "user_answers";
    public static final String CHANGED_ANSWER_KEY = "changed_answer";

    private List<Question> questions;
    private List<Answer> answers;
    private UserAnswersStore answersStore;

    private View view;

    private ContentLoadingProgressBar progressBar;
    private TextView questionTextView;
    private Map<Integer, TextView> answerViews = new HashMap<>();
    private ViewGroup questionStatusLayout;
    private TextView questionStatus;
    private Button nextBtn;

    private Exam exam;
    private Question question;

    private int position = 0;
    private int correctAnswers = 0;
    private int progressStep;
    private int changedAnswer = -1;
    private int currentCorrectAnswer;

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
            this.correctAnswers = savedInstanceState.getInt(CORRECT_KEY);
            this.answersStore = (UserAnswersStore) savedInstanceState.getSerializable(USER_ANSWERS_KEY);
            this.changedAnswer = savedInstanceState.getInt(CHANGED_ANSWER_KEY);
        }

        exam = (Exam) getArguments().getSerializable(StringBundleKeys.SENT_EXAM_KEY);
        TextView toolbarTitle = view.findViewById(R.id.toolbar_title_examination);
        toolbarTitle.setText(exam.getTitle());
        Toolbar toolbar = view.findViewById(R.id.examination_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        toolbar.setNavigationOnClickListener(back -> listener.returnToExams());

        progressBar = view.findViewById(R.id.examination_progress_bar);
        progressBar.setProgress(0);
        progressBar.setSecondaryProgress(0);


        questionTextView = view.findViewById(R.id.question);
        answerViews.put(1, view.findViewById(R.id.answer_text_view1));
        answerViews.put(2, view.findViewById(R.id.answer_text_view2));
        answerViews.put(3, view.findViewById(R.id.answer_text_view3));
        answerViews.put(4, view.findViewById(R.id.answer_text_view4));
        for (TextView answerView : answerViews.values()) {
            answerView.setOnClickListener(this::onAnswerClickListener);
        }
        questionStatusLayout = view.findViewById(R.id.question_status_layout);
        questionStatus = view.findViewById(R.id.question_status);
        nextBtn = view.findViewById(R.id.next_btn);

        questions = listener.getAllQuestionsByExam(exam);
        question = questions.get(position);
        answersStore = new UserAnswersStore(questions.size());
        progressStep = (int) Math.ceil(100.0 / questions.size());

        this.fillForm();

        nextBtn.setOnClickListener(this::nextBtn);

        return view;
    }

    public void onAnswerClickListener(View view) {
        for (Map.Entry<Integer, TextView> pair : answerViews.entrySet()) {
            pair.getValue().setClickable(false);
            if (pair.getValue().equals(view)) {
                changedAnswer = pair.getKey();
            }
        }
        nextBtn.setEnabled(true);
        progressBar.setSecondaryProgress(progressBar.getSecondaryProgress() + progressStep);
        if (changedAnswer != currentCorrectAnswer) {
            incorrectState();
        } else {
            correctAnswers++;
            progressBar.setProgress(progressBar.getProgress() + progressStep);
            correctState();
        }
        showAnswer(question);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION_KEY, position);
        outState.putInt(CORRECT_KEY, correctAnswers);
        outState.putSerializable(USER_ANSWERS_KEY, answersStore);
        outState.putInt(CHANGED_ANSWER_KEY, changedAnswer);
    }

    private void fillForm() {
        if (position == questions.size() - 1) {
            nextBtn.setText(R.string.result_button);
        }
        switchState();
        question = questions.get(position);
        answers = listener.getAnswersByQuestion(question);
        currentCorrectAnswer = questions.get(position).getCorrectAnswerId();
        questionTextView.setText(question.getText());
        for (Map.Entry<Integer, TextView> pair : answerViews.entrySet()) {
            pair.getValue().setText(answers.get(pair.getKey() - 1).getText());
        }
    }

    private void switchState() {
        if (changedAnswer == -1) {
            notSelectedState();
        } else if (changedAnswer == currentCorrectAnswer) {
            correctState();
            showAnswer(question);
        } else {
            incorrectState();
            showAnswer(question);
        }
    }

    private void notSelectedState() {
        nextBtn.setEnabled(false);
        questionStatusLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        questionStatus.setTextColor(getResources().getColor(R.color.light_text_color));
        questionStatus.setText("");
        for (TextView answerView : answerViews.values()) {
            answerView.setClickable(true);
            answerView.setBackgroundColor(getResources().getColor(R.color.white));
            answerView.setTextColor(getResources().getColor(R.color.default_text_color));
        }
        nextBtn.setBackgroundColor(getResources().getColor(R.color.new_question_status));

    }

    private void incorrectState() {
        TextView answerTextView = answerViews.get(changedAnswer);
        questionStatus.setText(R.string.incorrect);
        questionStatus.setTextColor(getResources().getColor(R.color.incorrect));
        questionStatusLayout.setBackgroundColor(
                getResources().getColor(R.color.incorrect_background));
        answerTextView.setTextColor(getResources().getColor(R.color.white));
        answerTextView.setBackgroundColor(
                getResources().getColor(R.color.incorrect));
        nextBtn.setBackgroundColor(getResources().getColor(R.color.incorrect));
    }

    private void correctState() {
        questionStatus.setText(R.string.correct);
        questionStatus.setTextColor(getResources().getColor(R.color.correct));
        questionStatusLayout.setBackgroundColor(
                getResources().getColor(R.color.correct_background));
        nextBtn.setBackgroundColor(getResources().getColor(R.color.correct));
    }

    private void showAnswer(Question question) {
        int correctIndex = question.getCorrectAnswerId();
        answerViews.get(correctIndex)
                .setTextColor(getResources().getColor(R.color.white));
        answerViews.get(correctIndex).setBackgroundColor(
                getResources().getColor(R.color.correct));

    }

    private void nextBtn(View view) {
            if (position == questions.size() - 1) {
                exam.setResult(correctAnswers);
                exam.setTime(System.currentTimeMillis());
                listener.toResultScreen(exam);
            } else {
                position++;
                changedAnswer = -1;
                currentCorrectAnswer = questions.get(position).getCorrectAnswerId();
                fillForm();
            }
    }


//    private void hintBtn(View view) {
//        listener.toHintScreen(questions.get(position));
//    }

//    private void prevBtn(View view) {
//        final RadioGroup variants = this.view.findViewById(R.id.variants);
//        if (variants.getCheckedRadioButtonId() != -1) {
//            answersStore.set(position, variants.getCheckedRadioButtonId());
//            correctAnswers = countCorrectAnswers();
//            position--;
//            variants.clearCheck();
//            fillForm();
//        }
//    }

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
