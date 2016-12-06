package com.nsu.alexander.countryquiz;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        onPostViewCrated(savedInstanceState);
    }

    protected void showMessage(String message) {
        if (null != message) {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        }
    }

    protected void showMessage(String title, String message, final Runnable runnable) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog
                .Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (null != runnable) {
                            runnable.run();
                        }
                    }
                });

        alertDialogBuilder.show();
    }

    protected abstract void onPostViewCrated(@Nullable final Bundle savedInstanceState);

    protected abstract int getLayout();

}
