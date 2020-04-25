package ru.job4j.exam;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.job4j.exam.store.ExamBaseHelper;
import ru.job4j.exam.store.ExamDbSchema;

public class UpdateExamFragment extends Fragment {

    private SQLiteDatabase store;

    public UpdateExamFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exam_add, container, false);
        store = new ExamBaseHelper(getContext()).getWritableDatabase();
        Bundle args = getArguments();
        final EditText examNameEditText = view.findViewById(R.id.exam_name);
        examNameEditText.setText(args.getString("name"));
        Button saveBtn = view.findViewById(R.id.save);
        saveBtn.setOnClickListener(
                btn -> {
                    ContentValues values = new ContentValues();
                    values.put(
                            ExamDbSchema.ExamTable.COLUMN_TITLE,
                            examNameEditText.getText().toString());
                    store.update(ExamDbSchema.ExamTable.TABLE_NAME, values, "id = ?",
                            new String[]{String.valueOf(args.getInt("id"))});
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.host, new ExamsFragment())
                            .addToBackStack("exam_update_fragment")
                            .commit();
                }
        );
        return view;
    }
}
