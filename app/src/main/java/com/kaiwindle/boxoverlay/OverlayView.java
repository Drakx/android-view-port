/*
 * Kai Windle Copyright (c) 19/06/17 12:31
 * OverlayView.java
 * Boxoverlay
 */

package com.kaiwindle.boxoverlay;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.kaiwindle.boxoverlay.model.DPSize;
import com.kaiwindle.boxoverlay.overlays.CodeOverlay;
import com.kaiwindle.boxoverlay.overlays.XMLOverlay;

/**
 * Created by Kai on 19/06/2017.
 * Boxoverlay
 */

public class OverlayView extends AppCompatActivity {
    private FrameLayout rootView;
    private View mainView;

    private DPSize dpSize;
    private boolean isCode = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_overlay_view);
        mainView = this.getWindow().getDecorView().findViewById(android.R.id.content);
        rootView = (FrameLayout) findViewById(android.R.id.content);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.containsKey(getString(R.string.bundle_key))) {
                String value = (String) b.get(getString(R.string.bundle_key));
                if (value != null && value.equals(getString(R.string.bundle_key_xml))) {
                    showViewViaXML();
                } else {
                    isCode = true;
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // This is optional but I found it useful for debugging
        // the screen size values
        mainView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainView.invalidate();
//                Log.d(TAG, "ScreenHeight: " + mainView.getHeight());
//                Log.d(TAG, "ScreenWidth: " + mainView.getWidth());
                dpSize = getScreenDimensionsDIP((float) mainView.getHeight());

                if (isCode) {
                    showViewViaCode();
                }
            }
        }, 100);

    }

    @Override
    public void onBackPressed() {
        int childCount = rootView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View cv = rootView.getChildAt(i);
            if (cv instanceof CodeOverlay) {
                rootView.removeView(cv);
                return;
            }
        }

        finish();
    }

    private void showViewViaXML() {
//        View view = View.inflate(MainActivity.this, R.layout.overlay, rootView);
        final XMLOverlay xmlOverlay = new XMLOverlay(this);
        xmlOverlay.setClickable(false);
        xmlOverlay.setButtonListener(new XMLOverlay.ButtonListener() {
            @Override
            public void closeView() {
                rootView.removeView(xmlOverlay);
            }
        });

        rootView.addView(xmlOverlay);
    }

    private void showViewViaCode() {
        CodeOverlay codeOverlay = new CodeOverlay(OverlayView.this, dpSize);

        rootView.addView(codeOverlay);
        codeOverlay.setClickable(false);
        codeOverlay.invalidate();
    }

    /**
     * Get the screen size of the current device
     * Left in just in case at some point I want to
     * use it again
     *
     * @return Point screen size
     */
    @SuppressWarnings("unused")
    private Point getScreenSize() {
        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = window.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    /**
     * Get the screen size of the current device
     * Left in just in case at some point I want to
     * use it again
     *
     * @return Point screen size
     */
    @SuppressWarnings("unused")
    private Point getScreenDimensionsPixels() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Point displaySize = new Point();
            getWindowManager().getDefaultDisplay().getRealSize(displaySize);

            return displaySize;
        }

        return new Point(width, height);
    }

    /**
     * Finds the screen size with DP as the unit of measurement
     *
     * @return DPSize custom model that holds width, height and density
     */
    private DPSize getScreenDimensionsDIP(Float viewHeight) {

        // Because this calculates the whole screen and not just the view
        // we'll be using the view height we passed in rather than
        // the calculated screen height

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
//        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth = outMetrics.widthPixels / density;

        return new DPSize(dpWidth, viewHeight, density);
    }
}




