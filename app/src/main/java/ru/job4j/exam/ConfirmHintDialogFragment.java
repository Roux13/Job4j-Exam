package ru.job4j.exam;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ConfirmHintDialogFragment extends DialogFragment {

    private ConfirmHintDialogListener callback;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage("Show hint?")
                .setPositiveButton(android.R.string.ok, this::onPositiveClick)
                .setNegativeButton(android.R.string.cancel, this::onNegativeClick)
                .create();
        return dialog;
    }

    public interface ConfirmHintDialogListener {

        void onPositiveDialogClick(DialogFragment dialog);

        void onNegativeDialogCLick(DialogFragment dialog);

    }

    public void onPositiveClick(DialogInterface dialog, int i) {
        callback.onPositiveDialogClick(ConfirmHintDialogFragment.this);
    }

    public void  onNegativeClick(DialogInterface dialog, int i) {
        callback.onNegativeDialogCLick(ConfirmHintDialogFragment.this);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.callback = (ConfirmHintDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    String.format("%s must implement ConfirmHintDialogListener",
                            context.toString())
            );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.callback = null;
    }
}
