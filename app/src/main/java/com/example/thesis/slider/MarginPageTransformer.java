package com.example.thesis.slider;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class MarginPageTransformer implements ViewPager2.PageTransformer {

    private final float margin;
    private final float horizontalMargin;

    public MarginPageTransformer(float margin, float horizontalMargin) {
        this.margin = margin;
        this.horizontalMargin = horizontalMargin;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        int pageWidth = page.getWidth();
        float pageOffset = position * -(2 * margin + horizontalMargin);
        if (position < 0) {
            page.setTranslationX(-pageOffset);
        } else {
            page.setTranslationX(pageOffset);
        }
    }
}
