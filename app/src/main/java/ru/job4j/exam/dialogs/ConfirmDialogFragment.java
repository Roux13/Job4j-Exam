package ru.job4j.exam.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.job4j.exam.R;
import ru.job4j.exam.utils.ExamTextFormat;

public class ConfirmDialogFragment extends DialogFragment {

    public static final String DIALOG_MESSAGE_KEY = "dialog_message_key";

    private ConfirmDialogListener callback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(getArguments().getString(DIALOG_MESSAGE_KEY))
                .setPositiveButton(android.R.string.ok, this::onPositiveClick)
                .setNegativeButton(android.R.string.cancel, this::onNegativeClick)
                .create();
        return dialog;
    }

    public void onPositiveClick(DialogInterface dialog, int i) {
        callback.onPositiveDialogClick(ConfirmDialogFragment.this);
    }

    public void  onNegativeClick(DialogInterface dialog, int i) {
        callback.onNegativeDialogCLick(ConfirmDialogFragment.this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.callback = (ConfirmDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(ExamTextFormat.formatAttachExceptionMessage(
                    context.getClass().getSimpleName(),
                    callback.getClass().getSimpleName())
            );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }
}
