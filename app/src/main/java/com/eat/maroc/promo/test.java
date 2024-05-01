package com.eat.maroc.promo;
import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class test extends AppCompatActivity {
    LinearLayout bottomSheet;
    BottomSheetBehavior<LinearLayout> bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);

        // Set the peek height
        bottomSheetBehavior.setPeekHeight(200);

        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                // Handle bottom sheet state changes
            }

            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
                // Handle bottom sheet slide
                if (slideOffset > 0) {
                    // Show your LinearLayout
                } else {
                    // Hide your LinearLayout
                }
            }
        });
    }
}
