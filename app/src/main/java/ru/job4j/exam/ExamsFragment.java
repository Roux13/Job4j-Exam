package ru.job4j.exam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import ru.job4j.exam.dialogs.ConfirmDeletingDialogFragment;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.global.ExamsFragmentListener;

public class ExamsFragment extends Fragment implements ConfirmDeletingDialogFragment.DeleteDialogConfirmListener {

    public static final String SENT_EXAM_KEY = "ru_job4_exam_key";

    private ExamsFragmentListener listener;

    private RecyclerView recycler;
    private ExamAdapter adapter;

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
        View view = inflater.inflate(R.layout.fragment_exams, container, false);
        recycler = view.findViewById(R.id.fragment_exams);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new ExamAdapter();
        listener.getAllExams().observe(getActivity(), exams -> adapter.setExams(exams));

        updateUI();

        return view;
    }

    private void updateUI() {
        this.recycler.setAdapter(adapter);
    }

    public static class ExamHolder extends RecyclerView.ViewHolder {

        private View view;

        public ExamHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }

    }

    public class ExamAdapter extends RecyclerView.Adapter<ExamHolder> {

        private List<Exam> exams;

        public ExamAdapter() {
        }

        @NonNull
        @Override
        public ExamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            final View view = inflater.inflate(R.layout.exams_item, parent, false);
            return new ExamHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExamHolder holder, int position) {
            final Exam exam = exams.get(position);
            holder.itemView.setBackgroundColor(getColor(position));
            holder.itemView.setOnClickListener(this::onItemClick);
            final TextView infoTextView = holder.view.findViewById(R.id.info);
            final TextView resultTextView = holder.view.findViewById(R.id.result);
            final TextView dateTextView = holder.view.findViewById(R.id.date);
            final ImageView editExamImgV = holder.view.findViewById(R.id.edit_exam_btn);
            final ImageView deleteExamImgV = holder.view.findViewById(R.id.delete_exam_btn);

            editExamImgV.setOnClickListener(btn -> listener.callUpdateExamFragment(exam));

            deleteExamImgV.setOnClickListener(btn -> {
                listener.deleteExam(exam);
                notifyItemRemoved(position);
            });


            infoTextView.setText(exam.getTitle());
            resultTextView.setText(String.valueOf(exam.getResult()));
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
            if (exam.getTime() > 0) {
                dateTextView.setText(dateFormat.format(exam.getTime()));
            } else {
                dateTextView.setText(R.string.not_started);
            }
        }

        public void setExams(List<Exam> exams) {
            this.exams = exams;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            if (exams != null) {
                return exams.size();
            } else {
                return 0;
            }
        }

        private void onItemClick(View view) {
            startActivity(new Intent(getContext(), ExamInfoActivity.class));
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
                listener.callAddExamFragment();
                adapter.notifyDataSetChanged();
                return true;
            case R.id.delete_item:
                listener.callConfirmDeletingDialog();
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void delete() {
        listener.deleteAllExams();
        updateUI();
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
