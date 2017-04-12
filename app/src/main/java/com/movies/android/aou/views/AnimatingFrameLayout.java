package com.movies.android.aou.views;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.movies.android.aou.R;

/**
 * Created by eltonjhony on 11/04/17.
 */
public class AnimatingFrameLayout extends FrameLayout {

    Context context;
    Animation inAnimation;
    Animation outAnimation;

    public AnimatingFrameLayout(@NonNull Context context) {
        super(context);
        this.context = context;
        initAnimations();
    }

    public AnimatingFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAnimations();
    }

    public AnimatingFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAnimations();
    }


    private void initAnimations() {
        inAnimation = AnimationUtils.loadAnimation(context, R.anim.in_animation);
        outAnimation = AnimationUtils.loadAnimation(context, R.anim.out_animation);
    }

    public void show() {
        if (isVisible()) return;
        show(true);
    }

    public void show(boolean withAnimation) {
        if (withAnimation) this.startAnimation(inAnimation);
        this.setVisibility(View.VISIBLE);
    }

    public void hide() {
        if (!isVisible()) return;
        hide(true);
    }

    public void hide(boolean withAnimation) {
        if (withAnimation) this.startAnimation(outAnimation);
        this.setVisibility(View.GONE);
    }

    public boolean isVisible() {
        return (this.getVisibility() == View.VISIBLE);
    }

    public void overrideDefaultInAnimation(Animation inAnimation) {
        this.inAnimation = inAnimation;
    }

    public void overrideDefaultOutAnimation(Animation outAnimation) {
        this.outAnimation = outAnimation;
    }

}
