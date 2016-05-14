package com.sughoshkumar.looptest;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Main fragment that populates the main view
 * Created by Sughosh Krishna Kumar on 12/05/16.
 */
public class MainFragment extends Fragment {
    private final static String TAG = "MAIN";
    private FloatingActionButton floatingButtonOne;

    private FloatingActionButton floatingButtonTwo;

    private TextView buttonTextTwo;
    private FloatingActionButton floatingButtonThree;

    private TextView seekBarValue;


    private CircularSeekBar mCircularSeekBar;

    private float progressValue = 10;

    private boolean mEcoClicked, mComfortButtonClicked;


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            progressValue = savedInstanceState.getFloat("value");
            Log.d(TAG, "Progress value " + progressValue);
        }

        // initialize circular seek bar
        mCircularSeekBar = (CircularSeekBar) view.findViewById(R.id.circular_seek);

        floatingButtonOne = (FloatingActionButton) view.findViewById(R.id.floating_button_left);

        buttonTextTwo = (TextView) view.findViewById(R.id.button_text_center);
        floatingButtonTwo = (FloatingActionButton) view.findViewById(R.id.floating_button_center);

        floatingButtonThree = (FloatingActionButton) view.findViewById(R.id.floating_button_right);


        mEcoClicked = false;
        mComfortButtonClicked = false;

        // Add click listeners to each of the buttons
        floatingButtonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ecoButtonClicked();
            }
        });
        floatingButtonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerButtonClicked();
            }
        });
        floatingButtonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comfortButtonClicked();
            }
        });

        // Init textview for seek bar value
        seekBarValue = (TextView) view.findViewById(R.id.textView);

        // Setting the circular menu parameters
        mCircularSeekBar.setDrawMarkings(true);
        mCircularSeekBar.setDotMarkers(true);
        mCircularSeekBar.setRoundedEdges(true);
        mCircularSeekBar.setIsGradient(true);
        mCircularSeekBar.setPopup(true);
        mCircularSeekBar.setSweepAngle(270);
        mCircularSeekBar.setArcRotation(225);
        mCircularSeekBar.setArcThickness(30);
        mCircularSeekBar.setMin(10);
        mCircularSeekBar.setMax(30);
        mCircularSeekBar.setProgress(progressValue);
        mCircularSeekBar.setIncreaseCenterNeedle(20);
        mCircularSeekBar.setValueStep(2);
        mCircularSeekBar.setNeedleFrequency(0.5f);
        mCircularSeekBar.setNeedleDistanceFromCenter(32);
        mCircularSeekBar.setNeedleLengthInDP(12);
        mCircularSeekBar.setIncreaseCenterNeedle(24);
        mCircularSeekBar.setNeedleThickness(1);
        mCircularSeekBar.setHeightForPopupFromThumb(10);

        // Setting textview with the seek bar value
        seekBarValue.setText(String.valueOf(progressValue) + "\u00B0");


        // On progress changed listener
        // Obtain progress and manipulate accordingly
        mCircularSeekBar.setOnCircularSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar CircularSeekBar, float progress, boolean fromUser) {
                mCircularSeekBar.setMinimumAndMaximumNeedleScale(progress - 2.5f, progress + 2.5f);
                seekBarValue.setText(String.valueOf(progress) + "\u00B0");
                if (progress < (mCircularSeekBar.getRealMax() / 2)) {
                    floatingButtonOne.setImageResource(R.drawable.eco_pressed);
                    floatingButtonThree.setImageResource(R.drawable.eco_normal);
                    mEcoClicked = true;
                }
                else if (progress > (mCircularSeekBar.getRealMax() / 2) + 5) {
                    floatingButtonThree.setImageResource(R.drawable.comfort_pressed);
                    floatingButtonOne.setImageResource(R.drawable.eco_normal);
                    mComfortButtonClicked = true;
                }
                else{
                    floatingButtonOne.setImageResource(R.drawable.eco_normal);
                    floatingButtonThree.setImageResource(R.drawable.eco_normal);
                    mComfortButtonClicked = mEcoClicked = false;
                }
                progressValue = progress;
            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar CircularSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar CircularSeekBar) {

            }
        });

        // Textview onclick open program fragment
        seekBarValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TextClick", "clicked");
                mCircularSeekBar.setClickable(false);
                ProgramFragment program = new ProgramFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_layout, program)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("value", progressValue);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_layout, container, false);
        return view;
    }


    private boolean isNull (Object o){
        return o == null;
    }

    /**
     * Set progress value for the slider arc
     * @param value
     */
    public void setProgressValue(float value){
        progressValue = value;
    }

    /**
     * On Eco (left) button clicked
     */
    private void ecoButtonClicked() {
        if (!mEcoClicked || mComfortButtonClicked) {
            floatingButtonOne.setImageResource(R.drawable.eco_pressed);
            floatingButtonThree.setImageResource(R.drawable.eco_normal);
            mEcoClicked = true;
        }else {
            floatingButtonOne.setImageResource(R.drawable.eco_normal);
            mEcoClicked = false;
        }
        Log.d(TAG, "Eco pressed");
        mCircularSeekBar.setProgress(mCircularSeekBar.getMin());

    }

    /**
     * On middle button clicked
     */
    private void centerButtonClicked(){
        Log.d(TAG, "Sinking is pressed");
        if (mComfortButtonClicked){
            floatingButtonThree.setImageResource(R.drawable.eco_normal
            );
            buttonTextTwo.setText("Sinking");
            mComfortButtonClicked = false;
            mCircularSeekBar.setProgress(15);
            return;
        }
        SubProgramFragment program = new SubProgramFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_layout, program)
                .addToBackStack(null)
                .commit();
    }

    /**
     * On comfort (right) button clicked
     */
    private void comfortButtonClicked(){
        Log.d(TAG, "Comfort pressed");
        if (!mComfortButtonClicked || mEcoClicked) {
            floatingButtonThree.setImageResource(R.drawable.comfort_pressed);
            floatingButtonOne.setImageResource(R.drawable.eco_normal);
            mCircularSeekBar.setProgress(23);
            buttonTextTwo.setText("Reduce\nMode");
            mComfortButtonClicked = true;
        } else {
            floatingButtonThree.setImageResource(R.drawable.eco_normal);
            buttonTextTwo.setText("Sinking");
            mComfortButtonClicked = false;
            mCircularSeekBar.setProgress(15);
        }
    }
}
