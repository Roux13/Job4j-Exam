package ru.job4j.exam.info;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import ru.job4j.exam.R;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.utils.ExamTextFormat;
import ru.job4j.exam.utils.StringBundleKeys;

public class InfoFragment extends Fragment {

    private InfoListener listener;

    private Exam exam;

    public InfoFragment() {
    }

    public static Fragment getInstance(Bundle args) {
        Fragment fragment = new InfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        TextView toolBarTitle = view.findViewById(R.id.info_toolbar_title);
        Toolbar toolbar = view.findViewById(R.id.toolbar_info);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_black_24dp);
        toolbar.setNavigationOnClickListener((back) -> requireActivity().onBackPressed());

        TextView examDescriptionTv = view.findViewById(R.id.exam_info_description_tv);
        TextView totalQuestionsTv = view.findViewById(R.id.exam_info_total_questions_tv);
        TextView yourResultTv = view.findViewById(R.id.exam_info_your_result_tv);
        TextView completionDateTv = view.findViewById(R.id.exam_info_completion_date);
        Button startBtn = view.findViewById(R.id.exam_info_start_btn);

        exam = (Exam) requireArguments().getSerializable(StringBundleKeys.SENT_EXAM_KEY);
        if (exam == null) {
            exam = new Exam("", "", 0, 0);
        }

        toolBarTitle.setText(exam.getTitle());
        examDescriptionTv.setText(exam.getDesc());
        examDescriptionTv.setMovementMethod(new ScrollingMovementMethod());
        int numberOfQuestions = listener.getNumberOfQuestions(exam);
        totalQuestionsTv.setText(String.valueOf(numberOfQuestions));
        yourResultTv.setText(
                ExamTextFormat.formatResultToPercent(exam.getResult(), numberOfQuestions)
        );
        completionDateTv.setText(ExamTextFormat.formatDate(getActivity(), exam.getTime()));

        startBtn.setOnClickListener((v) -> listener.startExam(exam));
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.listener = (InfoListener) context;
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
