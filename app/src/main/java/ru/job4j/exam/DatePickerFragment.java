package ru.job4j.exam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerFragmentListener callback;

    private boolean firstShowing = true;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        if (firstShowing) {
            firstShowing = false;
            callback.setDate(year, month, day);
        }
    }

    public interface DatePickerFragmentListener {

        void setDate(int year, int month, int dat);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.callback = (DatePickerFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    String.format(
                            "class %s must implement DatePickerFragmentListener",
                            context.toString()
                    )
            );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }
}
