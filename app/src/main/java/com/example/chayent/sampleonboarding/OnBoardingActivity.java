package com.example.chayent.sampleonboarding;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
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

    private int mLinearLayoutHeight;
    private int mViewPagerTotalPage;
    private int mViewPagerHeight;
    private int mPreviousPosition = 0;
    private Animation animation_slide_up;
    private Animation animation_slide_down;
    private Animation animation_fade_out;
    private LinearLayout mLinearLayout;
    private int[] imageBackground = new int[]{
            R.drawable.onboarding_image_1,
            R.drawable.onboarding_image_2,
            R.drawable.onboarding_image_3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        ButterKnife.bind(this);

        setAnimation();
        createOnBoardingViewPager();
        setCirclePageIndicator();
        setViewPagerListener();
    }

    private void createOnBoardingViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new PagerFragment(), "page_1");
        viewPagerAdapter.addFrag(new PagerFragment(), "page_2");
        viewPagerAdapter.addFrag(new PagerFragment(), "page_3");
        viewPager.setAdapter(viewPagerAdapter);
        mViewPagerTotalPage = viewPager.getAdapter().getCount();
    }

    private void setCirclePageIndicator() {
        circlePageIndicator.setViewPager(viewPager);
    }

    private void setViewPagerListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                onBoardingImage.setAlpha(1 - positionOffset);
//                Log.d("checkposition", "" + position);
//                onBoardingImage.startAnimation(animation_fade_out);
            }

            @Override
            public void onPageSelected(int position) {
                startImageAnimation(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void startImageAnimation(final int position) {
        startExpandViewAnimation(position);
        animation_slide_up.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                onBoardingImage.setImageResource(imageBackground[position]);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        onBoardingImage.startAnimation(animation_slide_up);
    }

    private void startExpandViewAnimation(int position) {
        mViewPagerHeight = viewPager.getMeasuredHeight();
        mLinearLayoutHeight = viewPager.getChildAt(0).findViewById(R.id.onboarding_linear_layout).getMeasuredHeight();
        mLinearLayout = viewPager.getChildAt(position).findViewById(R.id.onboarding_linear_layout);
        if(position != mPreviousPosition){
            setViewHeight(mLinearLayout, mViewPagerHeight);
            mPreviousPosition = position;
        }
    }

    private void setViewHeight(final View view, final int viewHeight) {
        ValueAnimator anim = ValueAnimator.ofInt(mLinearLayoutHeight, viewHeight);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = val;
                view.setLayoutParams(layoutParams);
            }
        });
        anim.setDuration(500);
        anim.start();
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ValueAnimator animEnd = ValueAnimator.ofInt(viewHeight, mLinearLayoutHeight);
                animEnd.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int val = (Integer) valueAnimator.getAnimatedValue();
                        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                        layoutParams.height = val;
                        view.setLayoutParams(layoutParams);
                    }
                });
                animEnd.setDuration(300);
                animEnd.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                animation.cancel();
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void setAnimation() {
        animation_slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        animation_slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animation_fade_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
    }
}
