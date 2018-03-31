package com.kest.ediscover.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kest.ediscover.Bean.AccountBean;
import com.kest.ediscover.Listener.AccountListViewDeleteClickListener;
import com.kest.ediscover.R;
import com.kest.ediscover.WebAppActivity;
import com.kest.ediscover.utils.DebugLog;

import java.util.List;

/**
 * Created by dk on 2017/12/22.
 */

public class AccountListViewAdapter extends BaseAdapter {
    private Context context;
    private List<AccountBean> dataList;
    private LayoutInflater inflater;
    //private String id;
    //private String url;
    private AccountListViewDeleteClickListener mListener;

    public AccountListViewAdapter(Context context, List<AccountBean> dataList,AccountListViewDeleteClickListener mListener) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.dataList = dataList;
        this.mListener = mListener;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final AccountListViewAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.smalls_listview_item, parent, false);
            viewHolder = new AccountListViewAdapter.ViewHolder();
            viewHolder.platformName = (TextView) convertView.findViewById(R.id.platform_name_item);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.account_name_list_item);
            viewHolder.delete = (TextView) convertView.findViewById(R.id.delete_btn_list_item);
            viewHolder.update = (TextView) convertView.findViewById(R.id.in_btn_list_item);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (AccountListViewAdapter.ViewHolder) convertView.getTag();
        }
        final AccountBean account = dataList.get(position);
        if (account != null) {
            viewHolder.platformName.setText(account.getPlatformName());
            viewHolder.userName.setText(account.getName());
            final String id = account.getId();

            final String url = account.getUrl();
            //final String addressUrl = url+"/urlAct/autoLogin/ptoken/" + account.getPtoken();
            DebugLog.i("------------------------------"+url);
           final String addressUrl = url +"&ptoken=" + account.getPtoken();

            viewHolder.delete.setOnClickListener(mListener);
            viewHolder.delete.setTag(position);

            viewHolder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (url == "" || url =="null"){
                        return;
                    }
                    Intent intent = new Intent(context, WebAppActivity.class);
                    intent.putExtra("address", addressUrl);
                    context.startActivity(intent);
                }
            });
        }
        return convertView;
    }


    private static class ViewHolder {
        TextView platformName;
        TextView userName;
        TextView delete;
        TextView update;
    }

    public void remove(int position){
        dataList.remove(position);
        notifyDataSetChanged();
    }

}
