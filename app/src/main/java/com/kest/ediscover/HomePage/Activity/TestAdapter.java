package com.kest.ediscover.HomePage.Activity;

import android.widget.TextView;

import com.kest.ediscover.R;
import com.kest.ediscover.utils.common.BaseArrayRecyclerAdapter;
import com.kest.ediscover.utils.common.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class TestAdapter extends BaseArrayRecyclerAdapter<String> {
    public TestAdapter(List<String> mDatas) {
        super(mDatas);
    }

    @Override
    public int bindView(int viewtype) {
        return R.layout.testitem;
    }

    @Override
    public void onBindHoder(RecyclerHolder holder, String s, int position) {
        TextView tv_content = holder.obtainView(R.id.tv_content);
        tv_content.setText(s);

    }
}
