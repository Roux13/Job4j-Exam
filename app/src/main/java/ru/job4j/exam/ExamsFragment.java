package ru.job4j.exam;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.job4j.exam.models.Exam;
import ru.job4j.exam.store.ExamStore;

public class ExamsFragment extends Fragment implements ConfirmDeletingDialogFragment.DeleteDialogConfirmListener {

    private final ExamStore store = ExamStore.getInstance();

    private RecyclerView recycler;

    public ExamsFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
        this.recycler.setAdapter(new ExamAdapter(store.getList()));
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
            holder.itemView.setBackgroundColor(getColor(position));
            holder.itemView.setOnClickListener(this::onClick);
            TextView infoTextView = holder.view.findViewById(R.id.info);
            TextView resultTextView = holder.view.findViewById(R.id.result);
            TextView dateTextView = holder.view.findViewById(R.id.date);

            infoTextView.setText(exam.getName());
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

        private int getColor(int position) {
            int color = 0;
            if (position % 2 == 0) {
                color = getResources().getColor(R.color.grey_item);
            }
            if (position % 2 != 0) {
                color = getResources().getColor(R.color.white);
            }
            return color;
        }

    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.exams, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                store.add(new Exam(store.size(), "Added Exam", System.currentTimeMillis(), store.size()));
                updateUI();
                return true;
            case R.id.delete_item:
                DialogFragment dialog = new ConfirmDeletingDialogFragment();
                dialog.show(getFragmentManager(), "delete_dialog_tag");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void delete() {
        store.deleteAll();
        updateUI();
    }
}
