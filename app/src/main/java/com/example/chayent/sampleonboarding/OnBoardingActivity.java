package com.example.chayent.sampleonboarding;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.Scroller;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnBoardingActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.onboarding_indicator)
    CirclePageIndicator circlePageIndicator;

    @BindView(R.id.onboarding_image)
    ImageView onBoardingImage;
    @BindView(R.id.onboarding_next_image)
    ImageView onBoardingNextImage;

    private int mPreviousPosition = 0;
    private Animation mAnimationSlideUp, mAnimationSlideDown, mAnimationFadeIn, mAnimationFadeOut;
    private AnimationSet mAnimationSet, mAnimationNextImageSet, mAnimationFragment;

    private int[] mImageBackground = new int[]{
            R.drawable.onboarding_image_1,
            R.drawable.onboarding_image_2,
            R.drawable.onboarding_image_3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        ButterKnife.bind(this);
        setUpView();
    }

    private void setUpView() {
        mAnimationSet = new AnimationSet(true);
        mAnimationNextImageSet = new AnimationSet(true);
        mAnimationFragment = new AnimationSet(true);

        mAnimationSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        mAnimationSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        mAnimationFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        mAnimationFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        mAnimationSet.addAnimation(mAnimationSlideUp);
        mAnimationSet.addAnimation(mAnimationFadeOut);
        mAnimationSet.setInterpolator(new AccelerateInterpolator());

        mAnimationNextImageSet.addAnimation(mAnimationFadeIn);
        mAnimationNextImageSet.addAnimation(mAnimationSlideDown);
        mAnimationNextImageSet.setInterpolator(new DecelerateInterpolator());

        mAnimationFragment.addAnimation(mAnimationSlideUp);
        mAnimationFragment.addAnimation(mAnimationFadeOut);
        mAnimationFragment.setInterpolator(new CycleInterpolator(0.5f));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new PagerFragment().setViewPagerIndex(0), "page_1");
        viewPagerAdapter.addFrag(new PagerFragment().setViewPagerIndex(1), "page_2");
        viewPagerAdapter.addFrag(new PagerFragment().setViewPagerIndex(2), "page_3");
        viewPager.setAdapter(viewPagerAdapter);

        circlePageIndicator.setViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Scroller scroller = new Scroller(getApplicationContext());
//                int currX = 0, currY = 0;
//                if (scroller.computeScrollOffset()) {
//                    // Get current x and y positions
//                    currX = scroller.getCurrX();
//                    currY = scroller.getCurrY();
//                }
//                Log.d("check x velo", "" +  currX + ":" + currY + ":" + VelocityTracker.obtain().getXVelocity(R.id.view_pager));
            }

            @Override
            public void onPageSelected(int position) {
                if (position != mPreviousPosition) {
                    startImageAnimation(position);
                    setFragmentAnimate(position);
                    mPreviousPosition = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    public void startImageAnimation(final int position) {
        mAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onBoardingImage.setImageResource(mImageBackground[position]);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mAnimationNextImageSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                onBoardingNextImage.setVisibility(View.VISIBLE);
                onBoardingNextImage.setImageResource(mImageBackground[position]);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onBoardingNextImage.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        onBoardingNextImage.startAnimation(mAnimationNextImageSet);
        onBoardingImage.startAnimation(mAnimationSet);
    }

    private void setFragmentAnimate(int position) {
        ImageView fragmentImage = viewPager.getChildAt(position).findViewById(R.id.onboarding_view_pager_image);
        TextView fragmentText = viewPager.getChildAt(position).findViewById(R.id.onboarding_view_pager_description);
        fragmentImage.startAnimation(mAnimationFragment);
        fragmentText.startAnimation(mAnimationFragment);
    }

//    private void startAnimationExpand(int position) {
//        final int viewPagerHeight = viewPager.getMeasuredHeight();
//        final int linearLayoutHeight = viewPager.getChildAt(0).findViewById(R.id.onboarding_linear_layout).getMeasuredHeight();
//        final LinearLayout linearLayout = viewPager.getChildAt(position).findViewById(R.id.onboarding_linear_layout);
//
//        ValueAnimator anim = ValueAnimator.ofInt(linearLayoutHeight, viewPagerHeight);
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                int val = (Integer) valueAnimator.getAnimatedValue();
//                ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
//                layoutParams.height = val;
//                linearLayout.setLayoutParams(layoutParams);
//            }
//        });
//        anim.setDuration(500).start();
//        anim.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                ValueAnimator animEnd = ValueAnimator.ofInt(viewPagerHeight, linearLayoutHeight);
//                animEnd.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                        int val = (Integer) valueAnimator.getAnimatedValue();
//                        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
//                        layoutParams.height = val;
//                        linearLayout.setLayoutParams(layoutParams);
//                    }
//                });
//                animEnd.setDuration(300);
//                animEnd.start();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//                animation.cancel();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });
//
//    }
}
