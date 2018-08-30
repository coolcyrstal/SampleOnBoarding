package com.example.chayent.sampleonboarding;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PagerFragment extends Fragment {

    @BindView(R.id.onboarding_view_pager_image)
    ImageView onBoardingViewPagerImage;
    @BindView(R.id.onboarding_view_pager_description)
    TextView onBoardingViewPagerDescription;
    @BindView(R.id.onboarding_view_pager_skip)
    TextView onBoardingSkip;

    private OnFragmentInteractionListener mListener;
    private int mPagerIndex = 0;
    private int[] mOnBoardingImage = new int[]{
            R.drawable.ic_headset,
            R.drawable.ic_rocket,
            R.drawable.ic_shop
    };

    public PagerFragment() {
        // Required empty public constructor
    }

    public PagerFragment setViewPagerIndex(int pagerIndex){
        mPagerIndex = pagerIndex;
        return this;
    }

    public static PagerFragment newInstance() {
        PagerFragment fragment = new PagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pager, container, false);
        ButterKnife.bind(this, rootView);

        onBoardingSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        onBoardingViewPagerImage.setImageResource(mOnBoardingImage[mPagerIndex]);
        onBoardingViewPagerDescription.setText("Test" + mPagerIndex);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
