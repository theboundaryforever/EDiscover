package main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kest.ediscover.BaseActivity;
import com.kest.ediscover.R;
import com.kest.ediscover.widget.ResizableRadioButton;

import java.util.ArrayList;
import java.util.List;
import main.fragment.EFindFragment;
import main.fragment.ELatterFragment;
import main.fragment.HomeFragment;
import main.fragment.MailListFragment;
import main.fragment.MyFragment;

/**
 * Created by Administrator on 2018\3\28 0028.
 *
 * 首页
 */

public class MainHomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    private FrameLayout content_layout;
    //首页
    RadioButton rb_home;
    //E发现
    RadioButton rb_e_find;
    //E信
    RadioButton rb_e_latter;
    //通讯录
    RadioButton rb_mail_list;
    //我的
    RadioButton rb_my;
    private RadioGroup radiogroup;

    private HomeFragment homeFragment;
    private EFindFragment eFindFragment;
    private MailListFragment mailListFragment;
    private ELatterFragment eLatterFragment;
    private MyFragment myFragment;
    private FragmentManager fragmentManager;
    private List<Fragment>fragments;
    private FragmentTransaction transaction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        initView();
    }

    /**初始化*/
    private void initView(){
        content_layout=findViewById(R.id.content_layout);
        rb_home=findViewById(R.id.rb_home);
        rb_e_find=findViewById(R.id.rb_e_find);
        rb_e_latter=findViewById(R.id.rb_e_latter);
        rb_mail_list=findViewById(R.id.rb_mail_list);
        rb_my=findViewById(R.id.rb_my);
        radiogroup=findViewById(R.id.radiogroup);
        radiogroup.setOnCheckedChangeListener(this);
        fragmentManager=getSupportFragmentManager();
        fragments=new ArrayList<>();
        addFragments();
        radiogroup.check(R.id.rb_home);
    }
    private  void addFragments(){
        homeFragment=new HomeFragment();
        eFindFragment=new EFindFragment();
        mailListFragment=new MailListFragment();
        myFragment=new MyFragment();
        eLatterFragment=new ELatterFragment();
        fragments.add(homeFragment);
        fragments.add(eFindFragment);
        fragments.add(eLatterFragment);
        fragments.add(mailListFragment);
        fragments.add(myFragment);
        switchFragment(0);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        switch (checkedId){
            case R.id.rb_home:
               switchFragment(0);
                break;
            case R.id.rb_e_find:
                switchFragment(1);

                break;
            case R.id.rb_e_latter:
                switchFragment(2);

                break;
            case R.id.rb_mail_list:
                switchFragment(3);

                break;
            case R.id.rb_my:
             switchFragment(4);
                break;
        }
    }

    private void switchFragment(int position){
        transaction= fragmentManager.beginTransaction();
        for(int i=0;i<fragments.size();i++){
            Fragment fragment=fragments.get(i);
            if(i==position){
                if(fragment.isAdded()){
                    transaction.show(fragment);
                }else{
                    transaction.add(R.id.content_layout,fragment);
                }
            }else{
                if(fragment.isAdded()){
                    transaction.hide(fragment);
                }
            }

        }
        transaction.commit();

    }
}
