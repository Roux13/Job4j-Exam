package ru.job4j.exam;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ru.job4j.exam.store.ExamBaseHelper;
import ru.job4j.exam.store.ExamDbSchema;

public class AddExamFragment extends Fragment {

    private SQLiteDatabase store;

    private EditText examNameEditText;

    public AddExamFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.exam_add, container, false);
        store = new ExamBaseHelper(getContext()).getWritableDatabase();
        examNameEditText = view.findViewById(R.id.exam_name);
        Button saveBtn = view.findViewById(R.id.save);
        saveBtn.setOnClickListener(this::onSaveClick);
        return view;
    }

    public void onSaveClick(View view) {
        ContentValues values = new ContentValues();
        values.put(ExamDbSchema.ExamTable.COLUMN_TITLE, examNameEditText.getText().toString());
        store.insert(ExamDbSchema.ExamTable.TABLE_NAME, null, values);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .replace(R.id.host, new ExamsFragment())
                .addToBackStack(null)
                .commit();
    }
}
