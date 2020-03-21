package ru.job4j.exam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ExamActivity extends AppCompatActivity {

    private static final String TAG = "ExamActivity";
    private static final String KEY_COUNT = "rotateCounter";
    private int rotateCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        Log.d(TAG, "onCreate");

        if (savedInstanceState != null) {
            rotateCounter = savedInstanceState.getInt(KEY_COUNT);
            Log.d(TAG, String.format("rotateCounter is %d, after rotating", rotateCounter));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        rotateCounter++;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, String.format("rotateCounter is %d, before rotating", rotateCounter));
        outState.putInt(KEY_COUNT, rotateCounter);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }
}
