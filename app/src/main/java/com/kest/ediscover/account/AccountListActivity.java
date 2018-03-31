package com.kest.ediscover.account;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.kest.ediscover.BaseActivity;
import com.kest.ediscover.Bean.AccountBean;
import com.kest.ediscover.Bean.PlatformBean;
import com.kest.ediscover.Listener.AccountListViewDeleteClickListener;
import com.kest.ediscover.Listener.PlatformListViewDeleteClickListener;
import com.kest.ediscover.R;
import com.kest.ediscover.adapter.AccountListViewAdapter;
import com.kest.ediscover.adapter.PlatformListViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountListActivity extends BaseActivity {

    List<PlatformBean> paltformList;
    List<AccountBean> accountList;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.left_drawer)
    ListView paltformListView;

    @BindView(R.id.myaccountlist)
    ListView accountListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @OnClick(R.id.back)
    void back(){
        finish();
    }

    @OnClick(R.id.show_platform_list)
    void AddAccount(){
        //startActivity(new Intent(AccountListActivity.this, AddAccountActivity.class));
        drawerLayout.openDrawer(Gravity.LEFT);

    }

    private void init(){
        final AccountBiz accountBiz = new AccountBiz(AccountListActivity.this);
        paltformList = new ArrayList<PlatformBean>();
        new Thread() {
            public void run() {
                Message message = Message.obtain();

                message.what = 1;
                paltformList= accountBiz.getPlatformtList("","1","50");

                handler.sendMessage(message);
            };
        }.start();

        accountList = new ArrayList<>();
        new Thread() {
            public void run() {
                Message message = Message.obtain();
                message.what = 2;
                accountList = accountBiz.getAccountList("","1","50");
                handler.sendMessage(message);
            };
        }.start();

    }


    AccountListViewAdapter listViewAdapter;
    PlatformListViewAdapter platformlistViewAdapter;
    private Handler handler = new Handler() {
        public void dispatchMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    if (paltformList  != null) {
                        platformlistViewAdapter = new PlatformListViewAdapter(
                                AccountListActivity.this, paltformList, new PlatformListViewDeleteClickListener() {
                            @Override
                            public void myOnClick(final int position, View v) {
                                new Thread() {
                                    public void run() {
                                        Message message = Message.obtain();
                                        AccountBiz accountBiz = new AccountBiz(AccountListActivity.this);
                                        if(accountBiz.deletePlatform(paltformList.get(position).getId())) {
                                            message.obj = position;
                                            message.what =5; //取消关注成功
                                        }else message.what = 6;
                                        handler.sendMessage(message);

                                    }
                                }.start();
                            }
                        });

                        //DebugLog.i("list size= " + paltformList.size());
                        paltformListView.setAdapter(platformlistViewAdapter);

                    }
                    break;
                case 2:
                    if (accountList  != null) {
                        listViewAdapter = new AccountListViewAdapter(
                                AccountListActivity.this, accountList, new AccountListViewDeleteClickListener() {
                            @Override
                            public void myOnClick(final int position, View v) {
                                new Thread() {
                                    public void run() {
                                        Message message = Message.obtain();
                                        AccountBiz accountBiz = new AccountBiz(AccountListActivity.this);
                                        if(accountBiz.deleteAccount(accountList.get(position).getId())) {
                                           message.obj = position;
                                                message.what =3;
                                        }else message.what = 4;
                                        handler.sendMessage(message);

                                    }
                                }.start();
                            }
                        });

                        //DebugLog.i("list size= " + accountList.size());
                        accountListView.setAdapter(listViewAdapter);

                    }
                    break;
                case 3:
                    listViewAdapter.remove((int)msg.obj);
                    Toast.makeText(AccountListActivity.this, "删除平台成功", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    Toast.makeText(AccountListActivity.this, "删除平台失败", Toast.LENGTH_SHORT).show();
                    //(new ShowDialogHelper(LoginActivity.this)).showDialog("提示", "当前网络不可用");
                case 5:
                    platformlistViewAdapter.remove((int)msg.obj);
                    Toast.makeText(AccountListActivity.this, "删除平台成功", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(AccountListActivity.this, "删除平台失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        };
    };


}
