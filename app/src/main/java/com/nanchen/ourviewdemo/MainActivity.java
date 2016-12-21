package com.nanchen.ourviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private ClockView mClockView;
    private TitleTextView mTitleTextView;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mClockView = (ClockView) findViewById(R.id.clockView);

        mTitleTextView = (TitleTextView) findViewById(R.id.titleTextView);

        mLinearLayout = (LinearLayout) findViewById(R.id.imageLayout);
    }

    public void btnClick(View view) {
        mClockView.setVisibility(View.VISIBLE);
        mTitleTextView.setVisibility(View.GONE);
        mLinearLayout.setVisibility(View.GONE);
    }

    public void btnClick1(View view) {
        mClockView.setVisibility(View.GONE);
        mTitleTextView.setVisibility(View.VISIBLE);
        mLinearLayout.setVisibility(View.GONE);
    }

    public void btnClick2(View view) {
        mClockView.setVisibility(View.GONE);
        mTitleTextView.setVisibility(View.GONE);
        mLinearLayout.setVisibility(View.VISIBLE);
    }
}
