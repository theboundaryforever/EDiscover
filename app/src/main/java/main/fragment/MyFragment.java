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
import com.kest.ediscover.my.MyActivity;
import com.kest.ediscover.my.bindbank.AddBank1Activity;
import com.kest.ediscover.my.person.PersonInfoActivity;
import com.kest.ediscover.my.settings.SettingsActivity;

/**
 * Created by Administrator on 2018/4/7 0007.
 */

//我的
public class MyFragment extends Fragment {
    //设置
    ImageView tv_settings;
    //实名认证
    RelativeLayout rl_authen;
    ImageView iv_cancel;
    //账户明细
    RelativeLayout rl_count;
    //总资产
    RelativeLayout rl_total_assets;
    //线下订单记录
    RelativeLayout rl_line;
    //ED币
    RelativeLayout rl_ed;
    //钱包
    RelativeLayout rl_wallet;
    //银行卡
    RelativeLayout rl_card;
    //个人信息
    RelativeLayout rl_person_info;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=null;
        if(view==null){
            view=inflater.inflate(R.layout.activity_my,null);
        }
        if(null!=view){
            initView(view);
            setListener();
        }
        return view;
    }
    private void initView(View view){
        tv_settings=view.findViewById(R.id.tv_settings);
        iv_cancel=view.findViewById(R.id.iv_cancel);
        rl_card=view.findViewById(R.id.rl_card);
        rl_authen=view.findViewById(R.id.rl_authen);
        rl_person_info=view.findViewById(R.id.rl_person_info);
        rl_count=view.findViewById(R.id.rl_count);
        rl_total_assets=view.findViewById(R.id.rl_total_assets);
        rl_line=view.findViewById(R.id.rl_line);
        rl_ed=view.findViewById(R.id.rl_ed);
        rl_wallet=view.findViewById(R.id.rl_wallet);

    }
    private void setListener(){
        tv_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingsActivity.class));
            }
        });
        rl_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddBank1Activity.class));
            }
        });
        rl_person_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PersonInfoActivity.class));
            }
        });
    }
}
