package ru.job4j.exam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ru.job4j.exam.store.HintStore;


/**
 * A simple {@link Fragment} subclass.
 */
public class HintFragment extends Fragment {

    private final HintStore store = HintStore.getInstance();

    public HintFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hint, container, false);
        String questionText = getArguments().getString(ExamFragment.QUESTION_TEXT);
        Toast.makeText(getContext(), questionText, Toast.LENGTH_SHORT).show();
        TextView text = view.findViewById(R.id.hint);
        int question = getArguments().getInt(ExamFragment.HINT_FOR, 0);
        text.setText(this.store.get(question));
        Button back = view.findViewById(R.id.back);
        back.setOnClickListener(this::backBtn);
        return view;
    }

    private void backBtn(View view) {
        getActivity().onBackPressed();
    }

    public static Fragment instanceOf(int index, String questionText) {
        HintFragment fragment = new HintFragment();
        Bundle args = new Bundle();
        args.putString(ExamFragment.QUESTION_TEXT, questionText);
        args.putInt(ExamFragment.HINT_FOR, index);
        fragment.setArguments(args);
        return fragment;
    }
}
