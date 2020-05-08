package ru.job4j.exam.dialogs;

import androidx.fragment.app.DialogFragment;

public interface ConfirmDialogListener {

    void onPositiveDialogClick(DialogFragment dialog);

    void onNegativeDialogCLick(DialogFragment dialog);


}
