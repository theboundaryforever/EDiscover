package main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kest.ediscover.R;
import com.kest.ediscover.my.account.AccountDetailActivity;
import com.kest.ediscover.my.bindbank.AddBank1Activity;
import com.kest.ediscover.my.person.AuthencationActivity;
import com.kest.ediscover.my.person.PersonInfoActivity;
import com.kest.ediscover.my.settings.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 我的
 * Created by Administrator on 2018/4/7 0007.
 */

public class MyFragment extends Fragment {
    //设置
    @BindView(R.id.tv_settings)
    ImageView tv_settings;
    //实名认证
    @BindView(R.id.rl_authen)
    RelativeLayout rl_authen;
    @BindView(R.id.iv_cancel)
    ImageView iv_cancel;
    //账户明细
    @BindView(R.id.rl_count)
    RelativeLayout rl_count;
    //总资产
    @BindView(R.id.rl_total_assets)
    RelativeLayout rl_total_assets;
    //线下订单记录
    @BindView(R.id.rl_line)
    RelativeLayout rl_line;
    //ED币
    @BindView(R.id.rl_ed)
    RelativeLayout rl_ed;
    //钱包
    @BindView(R.id.rl_wallet)
    RelativeLayout rl_wallet;
    //银行卡
    @BindView(R.id.rl_card)
    RelativeLayout rl_card;
    //个人信息
    @BindView(R.id.rl_person_info)
    RelativeLayout rl_person_info;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=null;
        if(view==null){
            view=inflater.inflate(R.layout.activity_my,null);
        }
        if(null!=view){
            unbinder=  ButterKnife.bind(this,view);
        }
        return view;
    }

    @OnClick({R.id.tv_settings,R.id.rl_authen,R.id.rl_card,R.id.rl_person_info,R.id.rl_count})
    void viewClick(View view){
        switch (view.getId()){
            case R.id.tv_settings:
                startActivity(new Intent(getContext(), SettingsActivity.class));
                break;
            case R.id.rl_authen:
                startActivity(new Intent(getContext(), AuthencationActivity.class));
                break;
            case R.id.rl_card:
                startActivity(new Intent(getContext(), AddBank1Activity.class));
                break;
            case R.id.rl_person_info:
                startActivity(new Intent(getContext(), PersonInfoActivity.class));
                break;
            case R.id.rl_count:
                startActivity(new Intent(getContext(), AccountDetailActivity.class));
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
