package com.sughoshkumar.looptest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ogaclejapan.arclayout.ArcLayout;

import java.util.ArrayList;
import java.util.List;

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
                .commit();

    }
}
