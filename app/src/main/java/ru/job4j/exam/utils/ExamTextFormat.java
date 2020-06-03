package ru.job4j.exam.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ru.job4j.exam.R;

public final class ExamTextFormat {

    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.US);

    public static String formatDate(Context context, long date) {
        if (date > 0) {
            return dateFormat.format(date);
        } else {
            return context.getResources().getString(R.string.not_passed);
        }
    }

    public static String formatResultToPercent(int correctAnswers, int numberQuestions) {
        return String.format(Locale.US, "%d(%.1f%%)",
                correctAnswers, (correctAnswers * 1.0 / numberQuestions) * 100);
    }

    public static String formatAttachExceptionMessage(String contextClassName, String necessaryImplementName) {
        return String.format(
                "Class %s must implement %s interface", contextClassName, necessaryImplementName);
    }

}
