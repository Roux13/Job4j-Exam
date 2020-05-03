package ru.job4j.exam.other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import ru.job4j.exam.R;

public class DateTimeActivity extends AppCompatActivity implements DatePickerFragment.DatePickerFragmentListener,
        TimePickerFragment.TimePickerFragmentListener {

    private TextView showDateTimeTv;

    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm    dd.MM.yy");

    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        Button dateTimeBtn = findViewById(R.id.date_time_btn);
        showDateTimeTv = findViewById(R.id.show_date_time_tv);
        dateTimeBtn.setOnClickListener(this::dateTimeOnClick);
    }

    public void dateTimeOnClick(View view) {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void setDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        DialogFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    @Override
    public void setTime(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        showDateTimeTv.setText(dateFormat.format(new Date(year, month, day, hour, minute)));
    }
}
