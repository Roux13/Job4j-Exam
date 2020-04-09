package ru.job4j.exam;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class ConfirmDelitingDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage("Delete all?")
                .setPositiveButton(android.R.string.ok, this::onPositiveClick)
                .setNegativeButton(android.R.string.cancel,)
        return super.onCreateDialog(savedInstanceState);
    }

    public void onPositiveClick(DialogInterface dialog, ) {

    }
}
