package ru.job4j.exam.exams;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
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

import java.util.List;
import java.util.Locale;

import ru.job4j.exam.R;
import ru.job4j.exam.dialogs.ConfirmDeletingDialogFragment;
import ru.job4j.exam.entitties.Exam;
import ru.job4j.exam.utils.ExamTextFormat;

public class ExamsFragment extends Fragment implements ConfirmDeletingDialogFragment.DeleteDialogConfirmListener {

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

        TextView toolbarTitle = view.findViewById(R.id.toolbar_title_text_view);
        toolbarTitle.setText(getString(R.string.app_name));

        recycler = view.findViewById(R.id.exams_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setHasFixedSize(true);
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
            holder.itemView.setOnClickListener((view) -> listener.callExamInfoActivity(exam));

            final TextView idExamTv = holder.view.findViewById(R.id.exam_id);
            final TextView infoTextView = holder.view.findViewById(R.id.info);
            final TextView questionsMasteredTv = holder.view.findViewById(R.id.questions_mastered);
            final ContentLoadingProgressBar progressBar = holder.view.findViewById(R.id.progressBar);

            idExamTv.setText(String.valueOf(exam.getId()));
            infoTextView.setText(exam.getTitle());
            int result = exam.getResult();
            int numberQuestions = listener.getNumberOfQuestions(exam);
            questionsMasteredTv.setText(String.format(Locale.getDefault(),
                    "%d of %d questions mastered",
                    result, numberQuestions));
            progressBar.setProgress(Math.round(result * 1.0f / numberQuestions * 100));
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
