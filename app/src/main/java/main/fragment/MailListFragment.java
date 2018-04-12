package main.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;

import android.widget.TextView;

import com.kest.ediscover.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 通讯录
 * Created by Administrator on 2018/4/7 0007.
 */

public class MailListFragment extends Fragment {
    @BindView(R.id.txt_title1_title)
     TextView txt_title;
    @BindView(R.id.framelayout)
    FrameLayout frameLayout;
    Unbinder unbinder;

 @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=null;
       if(view==null){
           view=inflater.inflate(R.layout.friendlist_layout_test,null);
       }
       if(view!=null){
           unbinder=   ButterKnife.bind(this,view);
           initData();
       }
        return view;
    }

    /**初始化*/
    public void initData(){
        txt_title.setText("通讯录");
        FragmentManager manager=getFragmentManager();
        manager.beginTransaction().add(R.id.framelayout,new MailListChildFragment()).commit();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
