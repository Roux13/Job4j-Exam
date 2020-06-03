package ru.job4j.exam.utils;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.job4j.exam.R;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
@Config(maxSdk = 28)
public class ExamTextFormatTest {


    @Test
    public void formatDateWhenDateIs0ThenExamNotPassed() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();

        assertThat(ExamTextFormat.formatDate(context, 0))
                .isEqualTo(context.getResources().getString(R.string.not_passed));
    }

    @Test
    public void formatDateWhenDateIsCurrentDate() {
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        long timeInMillis = System.currentTimeMillis();
        Date currentDate = new Date(timeInMillis);

        assertThat(ExamTextFormat.formatDate(context, timeInMillis))
                .isEqualTo(new SimpleDateFormat("dd.MM.yy", Locale.US).format(currentDate));
    }

    @Test
    public void formatResultToPercentWhenNoCorrectAnswers() {
        int correctAnswers = 0;
        int numberQuestions = 10;
        String expect = "0(0.0%)";

        assertThat(ExamTextFormat.formatResultToPercent(correctAnswers, numberQuestions))
                .isEqualTo(expect);
    }

    @Test
    public void formatResultToPercentWhenAllCorrectAnswers() {
        int correctAnswers = 10;
        int numberQuestions = 10;
        String expect = "10(100.0%)";

        assertThat(ExamTextFormat.formatResultToPercent(correctAnswers, numberQuestions))
                .isEqualTo(expect);
    }

    @Test
    public void formatResultToPercentWhen_3_Of_7_CorrectAnswers() {
        int correctAnswers = 3;
        int numberQuestions = 7;
        String expect = "3(42.9%)";

        assertThat(ExamTextFormat.formatResultToPercent(correctAnswers, numberQuestions))
                .isEqualTo(expect);
    }

    @Test
    public void formatResultToPercentWhen_0_Questions() {
        int correctAnswers = 0;
        int numberQuestions = 0;
        String expect = "0(NaN%)";

        assertThat(ExamTextFormat.formatResultToPercent(correctAnswers, numberQuestions))
                .isEqualTo(expect);
    }
}