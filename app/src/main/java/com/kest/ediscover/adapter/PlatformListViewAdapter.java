package com.kest.ediscover.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kest.ediscover.Bean.PlatformBean;
import com.kest.ediscover.Listener.AccountListViewDeleteClickListener;
import com.kest.ediscover.Listener.PlatformListViewDeleteClickListener;
import com.kest.ediscover.R;
import com.kest.ediscover.account.AddAccountActivity;

import java.util.List;

import static com.kest.ediscover.R.id.head;


/**
 * Created by dk on 2017/12/21.
 */

public class PlatformListViewAdapter extends BaseAdapter {

    private Context context;
    private List<PlatformBean> dataList;
    private LayoutInflater inflater;
    private String id;
    private PlatformListViewDeleteClickListener mListener;

    public PlatformListViewAdapter(Context context, List<PlatformBean> dataList, PlatformListViewDeleteClickListener mListener) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.platform_listview_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) convertView.findViewById(R.id.paltform_name);
            viewHolder.add = (LinearLayout) convertView.findViewById(R.id.add);
            viewHolder.head = (LinearLayout) convertView.findViewById(R.id.head);
            viewHolder.delete = (TextView) convertView.findViewById(R.id.delete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final PlatformBean platform = dataList.get(position);

        if (platform != null) {
            viewHolder.name.setText(platform.getName());
            id = platform.getBid();
            if (position > 0) {
                viewHolder.head.setVisibility(View.GONE);
            }
            viewHolder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AddAccountActivity.class);
                    intent.putExtra("platformId", platform.getBid());
                    context.startActivity(intent);
                }
            });
            viewHolder.delete.setOnClickListener(mListener);
            viewHolder.delete.setTag(position);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        LinearLayout add;
        TextView delete;
        LinearLayout head;
    }

    public void remove(int position){
        dataList.remove(position);
        notifyDataSetChanged();
    }

}
