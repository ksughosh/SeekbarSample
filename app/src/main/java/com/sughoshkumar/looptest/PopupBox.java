package com.sughoshkumar.looptest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Popup box for poppin out in the slider view
 * Created by Sughosh Krishna Kumar on 12/05/16.
 */
public class PopupBox extends LinearLayout {
    Context mContext;
    TextView mTextView;

    // Initialize constructors
    public PopupBox(Context context) {
        super(context);
        mContext = context;
        layoutInflation();
    }

    public PopupBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        layoutInflation();
    }

    public PopupBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        layoutInflation();
    }

    /**
     * Inflate the layout to be displayed
     */
    private void layoutInflation(){
        LayoutInflater.from(mContext).inflate(R.layout.popup_value,this, true);
        mTextView = (TextView) findViewById(R.id.bubble_box);
    }

    /**
     * Set text value for the textview
     * @param text string
     * @param color color of the string
     */
    public void setTextWithColor(String text, int color){
        mTextView.setText(text);
        mTextView.setTextColor(color);
    }

    /**
     * Set text value
     * @param text string
     */
    public void setText(String text){
        mTextView.setText(text);
    }


}
