package ru.job4j.exam.info;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
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

public class InfoFragment extends Fragment {

    private InfoListener listener;

    private Exam exam;

    public InfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        TextView examTitleTv = view.findViewById(R.id.exam_info_title_tv);
        TextView examDescriptionTv = view.findViewById(R.id.exam_info_description_tv);
        TextView totalQuestionsTv = view.findViewById(R.id.exam_info_total_questions_tv);
        TextView yourResultTv = view.findViewById(R.id.exam_info_your_result_tv);
        TextView completionDateTv = view.findViewById(R.id.exam_info_completion_date);
        Button startBtn = view.findViewById(R.id.exam_info_start_btn);

        exam = (Exam) getArguments().getSerializable(StringBundleKeys.SENT_EXAM_KEY);
        examTitleTv.setText(exam.getTitle());
        examDescriptionTv.setText(exam.getDesc());
        examDescriptionTv.setMovementMethod(new ScrollingMovementMethod());
        int numberOfQuestions = listener.getNumberOfQuestions(exam);
        totalQuestionsTv.setText(String.valueOf(numberOfQuestions));
        yourResultTv.setText(
                ExamTextFormat.formatResultToPercent(exam.getResult(), numberOfQuestions)
        );
        completionDateTv.setText(ExamTextFormat.formatDate(getActivity(), exam.getTime()));

        startBtn.setOnClickListener((v) -> {
            listener.startExam(exam);
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.listener = (InfoListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    String.format(ExamTextFormat.formatAttachExceptionMessage(
                            context.getClass().getSimpleName(),
                            listener.getClass().getSimpleName())
                            ));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}
