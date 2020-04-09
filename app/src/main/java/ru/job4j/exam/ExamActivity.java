package ru.job4j.exam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import static ru.job4j.exam.ExamFragment.HINT_FOR;
import static ru.job4j.exam.ExamFragment.QUESTION_TEXT;

public class ExamActivity extends BaseActivity implements ConfirmHintDialogFragment.ConfirmHintDialogListener,
        ExamFragment.HintButtonClickListener {

    private Bundle examArgs;

    @Override
    public Fragment loadFrg() {
        return new ExamFragment();
    }

    @Override
    public void onPositiveDialogClick(DialogFragment dialog) {
        Intent intent = new Intent(this, HintActivity.class);
        intent.putExtra(HINT_FOR, examArgs.getInt(HINT_FOR));
        intent.putExtra(QUESTION_TEXT, examArgs.getString(QUESTION_TEXT));
        startActivity(intent);
    }

    @Override
    public void onNegativeDialogCLick(DialogFragment dialog) {
        Toast.makeText(this, "Well done!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hintButtonClicked(Bundle examArgs) {
        this.examArgs = examArgs;
        DialogFragment dialog = new ConfirmHintDialogFragment();
        dialog.show(getSupportFragmentManager(), "dialog_tag");
    }

}
