package ru.job4j.exam.result;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.job4j.exam.R;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.exams.ExamsFragment;
import ru.job4j.exam.utils.ExamTextFormat;
import ru.job4j.exam.utils.StringBundleKeys;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    private ResultListener listener;

    private Exam exam;

    public ResultFragment() {
    }

    public static ResultFragment instanceOf(Bundle args) {
        ResultFragment fragment = new ResultFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        TextView totalQuestionsTv = view.findViewById(R.id.result_total_questions_tv);
        TextView correctTv = view.findViewById(R.id.result_correct_tv);
        TextView completionDateTv = view.findViewById(R.id.result_completion_date);
        Button toExamListBtn = view.findViewById(R.id.result_to_exam_list_btn);
        Button passAgainBtn = view.findViewById(R.id.result_pass_again_btn);

        exam = (Exam) getArguments().getSerializable(StringBundleKeys.SENT_EXAM_KEY);

        int totalQuestions = listener.getNumberOfQuestions(exam);
        totalQuestionsTv.setText(String.valueOf(totalQuestions));
        correctTv.setText(ExamTextFormat.formatResultToPercent(exam.getResult(), totalQuestions));
        completionDateTv.setText(ExamTextFormat.formatDate(getContext(), exam.getTime()));

        toExamListBtn.setOnClickListener((btn -> listener.ToExamList()));
        passAgainBtn.setOnClickListener((btn) -> listener.startExam(exam));
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.listener = (ResultListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    ExamTextFormat.formatAttachExceptionMessage(
                            context.getClass().getSimpleName(),
                            listener.getClass().getSimpleName()));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}
