package ru.job4j.exam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ru.job4j.exam.R;

public class ExamInfoFragment extends Fragment {

    public ExamInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_info, container, false);
        TextView examNameTv = view.findViewById(R.id.exam_info_name_tv);
        TextView examDescriptionTv = view.findViewById(R.id.exam_info_description_tv);
        TextView totalQuestionsTv = view.findViewById(R.id.exam_info_total_questions_tv);
        TextView yourResultTb = view.findViewById(R.id.exam_info_your_result_tv);
        TextView completionDateTv = view.findViewById(R.id.exam_info_completion_date);
        Button startBtn = view.findViewById(R.id.exam_info_start_btn);
        Button resetBtn = view.findViewById(R.id.exam_info_reset_btn);
        return view;
    }
}
