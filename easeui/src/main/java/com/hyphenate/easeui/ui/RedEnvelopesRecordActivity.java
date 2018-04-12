package com.hyphenate.easeui.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hyphenate.easeui.R;

/**
 * Created by Administrator on 2018\4\11 0011.
 * 红包记录
 */

public class RedEnvelopesRecordActivity extends Activity implements View.OnClickListener{

    private ListView listView;
    private TextView tv_back,tv_title,tv_right;

    private ScrollView sc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redenvelopesrecord_layout);
        init();
    }

    /**初始化*/
    private void init(){

        this.findViewById(R.id.tv_Time).setOnClickListener(this);
        this.findViewById(R.id.tv_back).setOnClickListener(this);

        tv_back = (TextView)this.findViewById(R.id.tv_back);
        tv_title = (TextView)this.findViewById(R.id.tv_title);
        tv_right = (TextView)this.findViewById(R.id.tv_right);
        listView = (ListView)this.findViewById(R.id.listview);
        sc = (ScrollView)this.findViewById(R.id.scroll);


        assigment();
    }

    /**赋值*/
    private void assigment(){

        tv_back.setText("返回");
        tv_title.setText("收到的红包");
        tv_right.setText("...");

        listView.setAdapter(new MyAdapter());
        sc.smoothScrollTo(0,0);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.tv_back){  //返回
            finish();
            return;
        }else if(view.getId()==R.id.tv_Time){  //选择年份

            return;
        }
    }


    class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return 7;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ViewHolder viewHolder;
            if(view==null){
                view = View.inflate(RedEnvelopesRecordActivity.this,R.layout.text_item,null);
                viewHolder = new ViewHolder();
                viewHolder.tv_style = view.findViewById(R.id.tv_style);
                viewHolder.tv_datetime = view.findViewById(R.id.tv_datetime);
                viewHolder.tv_number = view.findViewById(R.id.tv_number);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }

            return view;
        }
    }

    class ViewHolder {

        TextView tv_style;
        TextView tv_datetime;
        TextView tv_number;

    }

}
