package ru.job4j.exam;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.global.ExamsFragmentListener;

public class AddExamFragment extends Fragment {

    private ExamsFragmentListener listener;

    private EditText examTitleEdit;

    public AddExamFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_add, container, false);

        examTitleEdit = view.findViewById(R.id.exam_title);
        Button saveBtn = view.findViewById(R.id.save);
        saveBtn.setOnClickListener(this::onSaveClick);
        return view;
    }

    public void onSaveClick(View view) {
        Exam exam = new Exam(examTitleEdit.getText().toString());
        listener.addExam(exam);
        listener.callExamsFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.listener = (ExamsFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(String.format(
                    "Class %s must implement %s interface",
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
