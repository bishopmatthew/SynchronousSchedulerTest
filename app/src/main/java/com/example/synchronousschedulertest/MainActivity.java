package com.example.synchronousschedulertest;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.fragment_main) == null) {
            fm.beginTransaction().add(R.id.fragment_main, new MainFragment()).commit();
        }
    }



}
