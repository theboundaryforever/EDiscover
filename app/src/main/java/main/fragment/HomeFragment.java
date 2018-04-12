package main.fragment;


import android.graphics.Color;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;

import android.support.v7.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kest.ediscover.R;


/**
 * 首页
 * Created by Administrator on 2018/4/7 0007.
 */

public class HomeFragment extends Fragment implements View.OnClickListener,AppBarLayout.OnOffsetChangedListener {
    private AppBarLayout abl_bar;
    private View tl_expand, tl_collapse;
    private View v_expand_mask, v_collapse_mask, v_pay_mask;
    private int mMaskColor;
    RelativeLayout rl_control;
    Toolbar toolbar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=null;
        if(view==null){
            view=inflater.inflate(R.layout.fragment_first_page,null);
        }
        if(view!=null){
            init(view);
        }
        return view;
    }
    /**初始化*/
    private void init(View view){
        mMaskColor = getResources().getColor(R.color.blue_dark);

        abl_bar = view.findViewById(R.id.abl_bar);
        tl_expand =  view.findViewById(R.id.tl_expand);
        tl_collapse =  view.findViewById(R.id.tl_collapse);
        v_expand_mask =  view.findViewById(R.id.v_expand_mask);
        v_collapse_mask =  view.findViewById(R.id.v_collapse_mask);
        v_pay_mask =  view.findViewById(R.id.v_pay_mask);
        rl_control=view.findViewById(R.id.rl_control);
        toolbar=view.findViewById(R.id.toolbar);
        abl_bar.addOnOffsetChangedListener(this);
    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){

        }
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int offset = Math.abs(verticalOffset);
        int total = appBarLayout.getTotalScrollRange();
        int alphaIn = offset;
        int alphaOut = (200-offset)<0?0:200-offset;
            int maskColorIn = Color.argb(alphaIn, Color.red(mMaskColor),
                    Color.green(mMaskColor), Color.blue(mMaskColor));
            int maskColorInDouble = Color.argb(alphaIn*2, Color.red(mMaskColor),
                    Color.green(mMaskColor), Color.blue(mMaskColor));
            int maskColorOut = Color.argb(alphaOut*2, Color.red(mMaskColor),
                    Color.green(mMaskColor), Color.blue(mMaskColor));
            if (offset <= total/3) {
                tl_expand.setVisibility(View.VISIBLE);
                tl_collapse.setVisibility(View.GONE);
                v_expand_mask.setBackgroundColor(maskColorInDouble);
            } else{
                tl_expand.setVisibility(View.GONE);
                tl_collapse.setVisibility(View.VISIBLE);
                v_collapse_mask.setBackgroundColor(maskColorOut);
            }

            v_pay_mask.setBackgroundColor(maskColorIn);

    }
}
