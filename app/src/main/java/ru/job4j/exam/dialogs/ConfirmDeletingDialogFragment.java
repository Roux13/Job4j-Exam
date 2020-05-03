package ru.job4j.exam.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.job4j.exam.R;

public class ConfirmDeletingDialogFragment extends DialogFragment {

    DeleteDialogConfirmListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage("Delete all?")
                .setPositiveButton(android.R.string.ok, this::onPositiveClick)
                .setNegativeButton(android.R.string.cancel, this::onNegativeClick)
                .create();
        return dialog;
    }

    public void onPositiveClick(DialogInterface dialog, int i) {
        listener.delete();
    }

    public void onNegativeClick(DialogInterface dialog, int i) {
        dialog.dismiss();
    }

    public interface DeleteDialogConfirmListener {
        void delete();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            this.listener = (DeleteDialogConfirmListener) getFragmentManager()
                    .findFragmentById(R.id.activity_host);
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    String.format(
                            "Class %s must implement DeleteDialogConfirmListener interface",
                            context.toString()
                    )
            );
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }
}
