package main.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.domain.UserClass2;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.widget.EaseContactList;
import com.hyphenate.easeui.widget.EaseSidebar;
import com.kest.ediscover.ChatPage.Activity.GroupChatActivity;
import com.kest.ediscover.FriendPage2.Activity.NewFriendsActivity;
import com.kest.ediscover.FriendPage2.Activity.SearchActivity;
import com.kest.ediscover.FriendPage2.Activity.SelectContactActivity;
import com.kest.ediscover.MyApplication;
import com.kest.ediscover.MyHttputils.Conntent;
import com.kest.ediscover.MyHttputils.HttpUtils;
import com.kest.ediscover.R;
import com.kest.ediscover.base.BaseResult;
import com.kest.ediscover.utils.SharePreferenceUtil;
import com.kest.ediscover.widget.SideBar;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/7 0007.
 */

//通讯录
public class MailListFragment extends EaseContactListFragment implements HttpUtils.ICallback,View.OnClickListener{
    private List<UserClass2> Ulist = new ArrayList<>();
    private LinearLayout ll_search;
    private TextView txt_title;
    private SharePreferenceUtil sp;
    protected ListView listView;
    private TextView tv_show;
    SideBar sidebar;
/* @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=null;
       if(view==null){
           view=inflater.inflate(R.layout.friendlist_layout_test,null);
       }
       if(view!=null){
           initView(view);
           initData();
       }
        return view;
    }*/


    @Override
    protected void initView() {
        super.initView();
        @SuppressLint("InflateParams")
        View headerView = View.inflate(getContext(),R.layout.fragment_maillist_headview, null);

        headerView.findViewById(R.id.layout_newfriends).setOnClickListener(this);
        headerView.findViewById(R.id.layout_creategroup).setOnClickListener(this);
        headerView.findViewById(R.id.layout_megroup).setOnClickListener(this);
        init(headerView);
        initData();
        listView=getListView();
        if(listView!=null){
            listView.addHeaderView(headerView);
        }

        sp = SharePreferenceUtil.getInstance(getActivity());
        getSelectFriendlist();
    }
    /**初始化*/

    public void init(View view){
        txt_title = view.findViewById(R.id.txt_title1_title);
        ll_search=view.findViewById(R.id.ll_search);

        view.findViewById(R.id.layout_newfriends).setOnClickListener(this);
        view.findViewById(R.id.layout_creategroup).setOnClickListener(this);
        view.findViewById(R.id.layout_megroup).setOnClickListener(this);

    }
    private void initData(){
        txt_title.setText("通讯录");
        sp = SharePreferenceUtil.getInstance(getActivity());
        getSelectFriendlist();
    }

/**查寻通讯录*/

    private void getSelectFriendlist(){
        Map<String,Object> map = new HashMap<>();
        map.put("token",sp.getToken());
        map.put("action","user_contacts");
        HttpUtils.getInstance().post(Conntent.HTTPURL+Conntent.EASEMOBFRIENDLIST,map,this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_search:  //搜索好友
                startActivity(new Intent(getContext(),SearchActivity.class));
                break;
            case R.id.layout_newfriends:  //新朋友
                startActivity(new Intent(getContext(), NewFriendsActivity.class));
                break;
            case R.id.layout_creategroup:  //创建群聊
                startActivity(new Intent(getContext(), SelectContactActivity.class));
                break;
            case R.id.layout_megroup:  //我加入的群
                startActivity(new Intent(getContext(), GroupChatActivity.class));
                break;
        }
    }


    @Override
    public void onSuccess(String url, String result) {
        if(url.equals(Conntent.HTTPURL+Conntent.EASEMOBFRIENDLIST)){
            if(result.length()>0){
                Log.d("通讯录返回值",""+result);
                    Gson gson=new Gson();
                    BaseResult<UserClass2> baseResult=gson.fromJson(result,BaseResult.class);

                   if(baseResult.getReturnCode()==1000){
                     if("user_contacts".equals(baseResult.getAction())){
                         baseResult.getUserList().get(0).getInitialLetter();
                     }else{
                         MyApplication.setToast(baseResult.getReturnMsg());
                     }

                 }

            }
        }
    }


}
