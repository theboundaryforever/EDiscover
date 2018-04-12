package com.kest.ediscover.my.account.adapter;

import android.widget.TextView;

import com.kest.ediscover.R;
import com.kest.ediscover.utils.common.BaseArrayRecyclerAdapter;
import com.kest.ediscover.utils.common.BaseRecyclerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class AccountTypeAdapter extends BaseArrayRecyclerAdapter<String> {
    public AccountTypeAdapter(List<String> mDatas) {
        super(mDatas);
    }

    @Override
    public int bindView(int viewtype) {
        return R.layout.fragment_settings_account_type_item;
    }

    @Override
    public void onBindHoder(RecyclerHolder holder, String s, int position) {
        TextView tv_type=holder.obtainView(R.id.tv_type);
        if(s!=null){
            tv_type.setText(s);
        }else{
            tv_type.setText("");
        }


    }
}
