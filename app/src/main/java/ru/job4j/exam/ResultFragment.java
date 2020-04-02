package ru.job4j.exam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import ru.job4j.exam.store.UserAnswersStore;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    private final UserAnswersStore answersStore = UserAnswersStore.getInstance();

    public ResultFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.result, container, false);
        String questions = String.format(Locale.getDefault(),
                "%s: %d",
                getString(R.string.count_questions),
                answersStore.size());
        TextView countQuestions = view.findViewById(R.id.count_questions);
        countQuestions.setText(questions);
        String answers = String.format(Locale.getDefault(),
                "%s: %d",
                getString(R.string.correct_answers),
                getArguments().getInt(ExamFragment.CORRECT, 0));
        TextView correctAnswers = view.findViewById(R.id.correct_answers);
        correctAnswers.setText(answers);
        return view;
    }

    public static ResultFragment instanceOf(int correctAnswer) {
        Bundle args = new Bundle();
        args.putInt(ExamFragment.CORRECT, correctAnswer);
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
