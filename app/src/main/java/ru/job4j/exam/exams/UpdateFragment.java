package ru.job4j.exam.exams;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.job4j.exam.R;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.utils.ExamTextFormat;
import ru.job4j.exam.utils.StringBundleKeys;

public class UpdateFragment extends Fragment {

    private ExamsFragmentListener listener;

    private Exam exam;

    public UpdateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        exam = (Exam) getArguments().getSerializable(StringBundleKeys.SENT_EXAM_KEY);
        final EditText examTitleEdit = view.findViewById(R.id.exam_title);
        examTitleEdit.setText(exam.getTitle());
        Button saveBtn = view.findViewById(R.id.save);
        saveBtn.setOnClickListener(
                btn -> {
                    exam.setTitle(examTitleEdit.getText().toString());
                    listener.updateExam(exam);
                    listener.callExamsFragment();
                }
        );
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.listener = (ExamsFragmentListener) context;
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
}
