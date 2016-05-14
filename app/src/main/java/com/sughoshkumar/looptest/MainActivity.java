package com.sughoshkumar.looptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // Initialize the main view
        mainFragment = new MainFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout, mainFragment)
                .addToBackStack("main")
                .setCustomAnimations(0,0)
                .commit();

    }
}
