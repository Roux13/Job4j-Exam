package ru.job4j.exam.hint;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.job4j.exam.R;
import ru.job4j.exam.entitties.Question;
import ru.job4j.exam.utils.ExamTextFormat;
import ru.job4j.exam.utils.StringBundleKeys;

public class HintFragment extends Fragment {

    private HintListener listener;

    public static Fragment getInstance(Bundle args) {
        HintFragment fragment = new HintFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public HintFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hint, container, false);
        TextView text = view.findViewById(R.id.hint);
        Button back = view.findViewById(R.id.back);

        Question question = (Question) getArguments().getSerializable(StringBundleKeys.SENT_QUESTION_KEY);

        String questionText = question.getText();
        Toast.makeText(getContext(), questionText, Toast.LENGTH_SHORT).show();
        text.setText(listener.getHintByQuestion(question).getText());
        back.setOnClickListener(this::backBtn);
        return view;
    }

    private void backBtn(View view) {
        getActivity().onBackPressed();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.listener = (HintListener) context;
        } catch (ClassCastException e) {
            throw  new ClassCastException(ExamTextFormat.formatAttachExceptionMessage(
                    context.getClass().getSimpleName(),
                    listener.getClass().getSimpleName()
            ));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}
