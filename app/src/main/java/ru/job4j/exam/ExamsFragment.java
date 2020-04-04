package ru.job4j.exam;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamsFragment extends Fragment {

    private RecyclerView recycler;

    public ExamsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exams, container, false);
        recycler = view.findViewById(R.id.exams);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return view;
    }

    private void updateUI() {
        List<Exam> exams = new ArrayList<>();
        for (int index = 0; index != 100; index++) {
            exams.add(new Exam(index, String.format("Exam %s", index), System.currentTimeMillis(), index));
        }
        this.recycler.setAdapter(new ExamAdapter(exams));
    }

    public class ExamHolder extends RecyclerView.ViewHolder {

        private View view;

        public ExamHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    public class ExamAdapter extends RecyclerView.Adapter<ExamHolder> {

        private final List<Exam> exams;

        public ExamAdapter(List<Exam> exams) {
            this.exams = exams;
        }

        @NonNull
        @Override
        public ExamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.info_exam, parent, false);
            return new ExamHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExamHolder holder, int position) {
            final Exam exam = exams.get(position);
            TextView infoTextView = holder.view.findViewById(R.id.info);
            TextView resultTextView = holder.view.findViewById(R.id.result);
            TextView dateTextView = holder.view.findViewById(R.id.date);
            infoTextView.setText(exam.getName());
            infoTextView.setOnClickListener(this::onClick);
            resultTextView.setText(String.valueOf(exam.getResult()));
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy hh:mm");
            dateTextView.setText(dateFormat.format(exam.getTime()));
        }

        @Override
        public int getItemCount() {
            return exams.size();
        }

        public void onClick(View view) {
            startActivity(new Intent(getContext(), ExamActivity.class));
        }
    }

}
