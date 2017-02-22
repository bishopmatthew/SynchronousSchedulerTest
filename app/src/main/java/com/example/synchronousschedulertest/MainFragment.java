package com.example.synchronousschedulertest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by mabishop on 2/22/17.
 */

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView (final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_main, container, false);
        textView = (TextView) root.findViewById(R.id.text_view);
        return root;
    }

    @Override
    public void onViewCreated (final View view, @Nullable final Bundle savedInstanceState) {
        SampleRepository.getInstance()
                .getData("test")
                .subscribe(new Action1<Data>() {
                    @Override
                    public void call (Data data) {
                        bindData(data);

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call (final Throwable throwable) {
                        showError(throwable);
                    }
                });
    }



    private void showError (final Throwable throwable) {
        Toast.makeText(getContext(), "Error=" + throwable, Toast.LENGTH_SHORT).show();
    }

    private void bindData (final Data data) {
        textView.setText(data.value);
    }

    @Override
    public void onViewStateRestored (@Nullable final Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            if (textView.getText().equals("Default Value")) {
                Toast.makeText(getContext(), ":( State was not restored in time", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), ":) State was restored in time!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
