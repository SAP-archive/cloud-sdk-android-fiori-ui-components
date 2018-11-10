package com.sap.cloud.mobile.fiori.demo.basicComponent;

import android.os.Handler;
import android.os.Bundle;

import com.sap.cloud.mobile.fiori.demo.AbstractDemoActivity;
import com.sap.cloud.mobile.fiori.demo.R;
import com.sap.cloud.mobile.fiori.indicator.FioriProgressBar;

public class IndicatorsDemo extends AbstractDemoActivity {

    private FioriProgressBar mHorizontalIndeterminate;
    private FioriProgressBar mHorizontalDeterminate;
    private FioriProgressBar mQueryIndeterminateDeterminate;
    private FioriProgressBar mCircularIndeterminate;
    private FioriProgressBar mCircularDeterminate;
    private FioriProgressBar mProgressIndicator;
    private FioriProgressBar mCheckoutIndicator;
    private int mChange = 1;

    private Handler mHandler;
    Runnable mUpdate;
    private final int[] mCounter = new int[]{0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicators_demo);

        mHorizontalIndeterminate = findViewById(R.id.horizontal_indeterminate);
        mHorizontalDeterminate = findViewById(R.id.horizontal_determinate);
        mQueryIndeterminateDeterminate = findViewById(R.id.horizontal_query_indeterminateDeterminate);
        mCircularIndeterminate = findViewById(R.id.circular_indeterminate);
        mCircularDeterminate = findViewById(R.id.circular_determinate);
        mProgressIndicator = findViewById(R.id.progress_indicator);
        mCheckoutIndicator = findViewById(R.id.checkoutIndicator);
        mProgressIndicator = findViewById(R.id.progress_indicator);



        mHandler = new Handler();

        mUpdate = new Runnable() {
            @Override
            public void run() {
                updateQueryIndeterminate();
                updateDeterminateProgress();
                updateCounter();
                mHandler.postDelayed(mUpdate, 50);
            }
        };
        startRepeatingTask();
    }

    private void startRepeatingTask() {
        mUpdate.run();
    }

    private void stopRepeatingTask() {
        mHandler.removeCallbacks(mUpdate);
    }

    private void updateQueryIndeterminate() {
        if (mChange < 0) {
            if (mQueryIndeterminateDeterminate.isIndeterminate()) {
                mQueryIndeterminateDeterminate.setIndeterminate(false);
                mProgressIndicator.setIndeterminate(false);
                mCheckoutIndicator.setIndeterminate(false);
                mProgressIndicator.setIndeterminate(false);
            }
            mQueryIndeterminateDeterminate.incrementProgressBy(1);
            mProgressIndicator.incrementProgressBy(1);
        } else if (!mQueryIndeterminateDeterminate.isIndeterminate()){
            // set the progress to be 0 and then change to indeterminate mode. Order is important here
            // as in indeterminate mode progress is ignored.
            mQueryIndeterminateDeterminate.setProgress(0);
            mQueryIndeterminateDeterminate.setIndeterminate(true);
            mCheckoutIndicator.setIndeterminate(true);
            mProgressIndicator.setIndeterminate(true);
        }
    }


    private void updateDeterminateProgress() {
        if (mCounter[0] % 50 != 0) {
            mHorizontalDeterminate.incrementProgressBy(2 * Math.abs(mChange));
            mCircularDeterminate.incrementProgressBy(2 * Math.abs(mChange));
        } else {
            mHorizontalDeterminate.setProgress(0);
            mCircularDeterminate.setProgress(0);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    private void updateCounter() {
        mCounter[0] += mChange;
        if (mCounter[0] > 100) {
            mChange = -1;
        } else if (mCounter[0] < 0) {
            mChange = 1;
        }
    }
}
