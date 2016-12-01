package com.boyz.rho.pass.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.boyz.rho.pass.Activities.EditSiteActivity;
import com.boyz.rho.pass.Activities.MainActivity;
import com.boyz.rho.pass.R;
import com.boyz.rho.pass.Utils.PassDataSource;

import java.sql.SQLException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by rho on 11/16/16.
 */

public class PopupFragment extends DialogFragment {

    private boolean delete;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Bundle bundle = getArguments();
        final String site = bundle.getString("site");
        final String username = bundle.getString("username");
        final String password = bundle.getString("password");
        final String databasePassword = bundle.getString("databasePassword");

        final PassDataSource dataSource = new PassDataSource(getContext());
        try {
            dataSource.open(databasePassword);
        } catch (SQLException e) {
            Toast.makeText(getContext(), "Error opening databse", Toast.LENGTH_SHORT).show();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(site)
                .setPositiveButton("Copy Password", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ClipboardManager manager = (ClipboardManager) getActivity().
                                getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("password", password);
                        manager.setPrimaryClip(clip);
                        dismiss();
                    }
                })
                .setNegativeButton("Delete Entry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dataSource.deletePassword(site, username);
                        delete = true;
                        dismiss();
                    }
                })
                .setNeutralButton("Edit Entry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Activity activity = getActivity();
                        Intent intent = new Intent(activity, EditSiteActivity.class);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, RESULT_OK);
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        Activity activity = getActivity();
        if(activity instanceof MainActivity) {
            ((MainActivity) activity).handleDialogClose(delete);
        }
    }
}
