package com.example.chayent.sampleonboarding;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

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
    }

    private void setCirclePageIndicator() {
        circlePageIndicator.setViewPager(viewPager);
    }

    private void setViewPagerListener(){
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onBoardingImage.setImageResource(imageBackground[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
