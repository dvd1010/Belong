package com.belonginterview.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.belonginterview.enums.AnimationEnum;

/*Class sets animation to dropdown list*/
public class DropdownListAnimation extends Animation {
    private View mAnimatedView;
    private int mEndHeight;
    private AnimationEnum mType;

    public DropdownListAnimation(View view, int duration, AnimationEnum type) {
        setDuration(duration);
        mAnimatedView = view;
        mEndHeight = mAnimatedView.getLayoutParams().height;
        mType = type;
        if(mType.equals(AnimationEnum.EXPAND)) {
            mAnimatedView.getLayoutParams().height = 0;
            mAnimatedView.setVisibility(View.VISIBLE);
            mAnimatedView.bringToFront();
        }
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        if (interpolatedTime < 1.0f) {
            if(mType.equals(AnimationEnum.EXPAND)) {
                mAnimatedView.getLayoutParams().height = (int) (mEndHeight * interpolatedTime);
            } else {
                mAnimatedView.getLayoutParams().height = mEndHeight - (int) (mEndHeight * interpolatedTime);
            }
            mAnimatedView.requestLayout();
        } else {
            if(mType.equals(AnimationEnum.EXPAND)) {
                mAnimatedView.getLayoutParams().height = mEndHeight;
                mAnimatedView.requestLayout();
            } else {
                mAnimatedView.getLayoutParams().height = 0;
                mAnimatedView.setVisibility(View.GONE);
                mAnimatedView.requestLayout();
                mAnimatedView.getLayoutParams().height = mEndHeight;
            }
        }
    }
}
