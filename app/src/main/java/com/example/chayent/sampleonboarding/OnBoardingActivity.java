package com.example.chayent.sampleonboarding;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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

    private int mViewPagerTotalPage;
    private Animation animation_slide_up;
    private Animation animation_slide_down;
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

    public void startImageAnimation(int position) {
        onBoardingImage.startAnimation(animation_slide_up);
        onBoardingImage.setImageResource(imageBackground[position]);
    }

    private void setAnimation() {
        animation_slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        animation_slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
    }
}
