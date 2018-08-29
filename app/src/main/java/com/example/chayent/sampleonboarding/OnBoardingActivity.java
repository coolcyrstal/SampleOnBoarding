package com.example.chayent.sampleonboarding;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.viewpagerindicator.CirclePageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnBoardingActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.onboarding_indicator)
    CirclePageIndicator circlePageIndicator;

    @BindView(R.id.onboarding_image)
    ImageView onBoardingImage;

    private int mPreviousPosition = 0;
    private Animation mAnimationSlideUp;
    private Animation mAnimationSlideDown;
    private Animation mAnimationFadeOut;
    private AnimationSet mAnimationSet;

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
        mAnimationSet = new AnimationSet(false);
        mAnimationSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        mAnimationSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        mAnimationFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new PagerFragment(), "page_1");
        viewPagerAdapter.addFrag(new PagerFragment(), "page_2");
        viewPagerAdapter.addFrag(new PagerFragment(), "page_3");
        viewPager.setAdapter(viewPagerAdapter);

        circlePageIndicator.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != mPreviousPosition) {
                    startImageAnimation(position);
                    mPreviousPosition = position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    public void startImageAnimation(final int position) {
        mAnimationSet.addAnimation(mAnimationSlideUp);
        mAnimationSet.addAnimation(mAnimationFadeOut);
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
        onBoardingImage.startAnimation(mAnimationSet);
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
