package com.sughoshkumar.looptest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.ogaclejapan.arclayout.ArcLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Program fragment that displays the primary program view
 * Created by Sughosh Krishna Kumar on 13/05/16.
 */
public class SubProgramFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton menuButton;
    private View menuLayout;
    private ArcLayout arcLayout;
    private boolean show;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        menuButton = (FloatingActionButton) view.findViewById(R.id.menu_button_);
        arcLayout = (ArcLayout) view.findViewById(R.id.arc_menu_);
        menuLayout = view.findViewById(R.id.menu_layout_);
        show = false;

        for (int i = 0, size = arcLayout.getChildCount(); i < size; i++) {
            arcLayout.getChildAt(i).setOnClickListener(this);
        }

        menuButton.setOnClickListener(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sinking_view, container, false);
        return view;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.menu_button_){
            Log.e("MENU", "Clicked");
            onMenuClicked();
        }
        if (view instanceof FloatingActionButton){
            writeLogOut((FloatingActionButton) view);
        }
    }

    private void writeLogOut(FloatingActionButton button) {
        Log.d("MENU ITEM", "Clicked " + button.getId());
    }

    /**
     * Handling the clicks
     */
    public void onMenuClicked() {
        if (!show){
            show = true;
            showMenu();
        }
        else {
            show = false;
            hideMenu();
        }
    }

    /**
     * Show menu animation function
     */
    private void showMenu() {
        menuLayout.setVisibility(View.VISIBLE);
        List<Animator> animList = new ArrayList<>();

        ViewCompat.animate(menuButton).
                rotation(135.0f)
                .withLayer()
                .setDuration(100)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        for (int i = 0, len = arcLayout.getChildCount(); i < len; i++) {
            animList.add(createShowItemAnimator(arcLayout.getChildAt(i)));
        }


        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(80);
        animSet.setupEndValues();
        animSet.setInterpolator(new OvershootInterpolator(0f));
        animSet.playSequentially(animList);
        animSet.start();
    }

    /**
     * Obtain list of animation in view
     * @param item each view item
     * @return animated view
     */
    private Animator createShowItemAnimator(View item) {
        float dx = menuButton.getX() - item.getX();
        float dy = menuButton.getY() - item.getY();

        item.setRotation(0f);
        item.setTranslationX(dx);
        item.setTranslationY(dy);

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(0f, 720f),
                AnimatorUtils.translationX(dx, 0f),
                AnimatorUtils.translationY(dy, 0f)
        );

        return anim;
    }

    /**
     * Hide menu animation function
     */
    private void hideMenu() {
        List<Animator> animList = new ArrayList<>();

        for (int i = arcLayout.getChildCount() - 1; i >= 0; i--) {
            animList.add(createHideItemAnimator(arcLayout.getChildAt(i)));
        }

        ViewCompat.animate(menuButton).
                rotation(0.0f)
                .withLayer()
                .setDuration(100)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new OvershootInterpolator(0f));
        animSet.playTogether(animList);
        animSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                menuLayout.setVisibility(View.INVISIBLE);
                getFragmentManager().popBackStack("main", 0);
            }
        });
        animSet.start();
    }

    /**
     * Obtain list of animation in view
     * @param item each view item
     * @return animated view
     */
    private Animator createHideItemAnimator(final View item) {
        float dx = menuButton.getX() - item.getX();
        float dy = menuButton.getY() - item.getY();

        Animator anim = ObjectAnimator.ofPropertyValuesHolder(
                item,
                AnimatorUtils.rotation(720f, 0f),
                AnimatorUtils.translationX(0f, dx),
                AnimatorUtils.translationY(0f, dy)
        );

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                item.setTranslationX(0f);
                item.setTranslationY(0f);
            }
        });

        return anim;
    }
}
