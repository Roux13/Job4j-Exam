package ru.job4j.exam;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import java.util.ArrayList;
import java.util.List;

import ru.job4j.exam.models.Exam;
import ru.job4j.exam.store.ExamBaseHelper;
import ru.job4j.exam.store.ExamDbSchema;

public class ExamsFragment extends Fragment implements ConfirmDeletingDialogFragment.DeleteDialogConfirmListener {

//    private final ExamStore store = ExamStore.getInstance();

    private RecyclerView recycler;
    private SQLiteDatabase store;

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
        store = new ExamBaseHelper(getContext()).getWritableDatabase();
        updateUI();

        return view;
    }

    private void updateUI() {
        List<Exam> exams = new ArrayList<>();
        Cursor cursor = this.store.query(
                ExamDbSchema.ExamTable.TABLE_NAME,
                null, null, null,
                null, null, null
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            exams.add(new Exam(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    System.currentTimeMillis(),
                    100
            ));
            cursor.moveToNext();
        }
        cursor.close();
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
            final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            final View view = inflater.inflate(R.layout.exams_item, parent, false);
            return new ExamHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ExamHolder holder, int position) {
            final Exam exam = exams.get(position);
            holder.itemView.setBackgroundColor(getColor(position));
            holder.itemView.setOnClickListener(this::onClick);
            final TextView infoTextView = holder.view.findViewById(R.id.info);
            final TextView resultTextView = holder.view.findViewById(R.id.result);
            final TextView dateTextView = holder.view.findViewById(R.id.date);
            final ImageView editExamImgV = holder.view.findViewById(R.id.edit_exam_btn);
            final ImageView deleteExamImgV = holder.view.findViewById(R.id.delete_exam_btn);

            editExamImgV.setOnClickListener(btn -> {
                Bundle args = new Bundle();
                args.putInt("id", exam.getId());
                args.putString("name", exam.getName());
                Fragment examUpdateFragment = new UpdateExamFragment();
                examUpdateFragment.setArguments(args);
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.host, examUpdateFragment)
                        .addToBackStack("exam_list")
                        .commit();
            });

            deleteExamImgV.setOnClickListener(btn -> {
                store.delete(ExamDbSchema.ExamTable.TABLE_NAME, "id = ?", new String[]{
                        String.valueOf(exam.getId())
                });
                exams.remove(exam);
                notifyItemRemoved(position);
            });


            infoTextView.setText(exam.getName());
            resultTextView.setText(String.valueOf(exam.getResult()));
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
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
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.host, new AddExamFragment())
                        .addToBackStack("exam_list")
                        .commit();
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
        store.delete(ExamDbSchema.ExamTable.TABLE_NAME, null, null);
        updateUI();
    }
}
