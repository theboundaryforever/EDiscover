package com.hyphenate.easeui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.domain.EaseAvatarOptions;
import com.hyphenate.easeui.domain.UserClass2;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseImageView;
import com.hyphenate.util.EMLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\3\29 0029.
 */

public class EaseContactAdapter3 extends ArrayAdapter<UserClass2> implements SectionIndexer {
    private static final String TAG = "ContactAdapter";
    List<String> list;
    List<UserClass2> userList;
    List<UserClass2> copyUserList;
    private LayoutInflater layoutInflater;
    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;
    private int res;
    private MyFilter myFilter;
    private boolean notiyfyByFilter;

    public EaseContactAdapter3(Context context, int resource, List<UserClass2> objects) {
        super(context, resource, objects);
        this.res = resource;
        this.userList = objects;
        copyUserList = new ArrayList<UserClass2>();
        copyUserList.addAll(objects);
        layoutInflater = LayoutInflater.from(context);
    }


    public List<UserClass2> getlistt(){
        List<UserClass2> Ulist2 = new ArrayList<>();
        for (int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getSign().equals("1")) {
                Ulist2.add(userList.get(i));
            }
        }
        return Ulist2;
    }

    public void setList3(List<UserClass2> list){
        userList.removeAll(userList);
        userList.addAll(list);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        ImageView avatar;
        TextView nameView;
        ImageView check;
        TextView headerView;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            if(res == 0)
                convertView = layoutInflater.inflate(com.hyphenate.easeui.R.layout.easr_creategroupchat_item, parent, false);
            else
                convertView = layoutInflater.inflate(res, null);
            holder.avatar = (ImageView) convertView.findViewById(com.hyphenate.easeui.R.id.img_avatar);
            holder.nameView = (TextView) convertView.findViewById(com.hyphenate.easeui.R.id.tv_username);
            holder.check = (ImageView) convertView.findViewById(com.hyphenate.easeui.R.id.img_check);
            holder.headerView = (TextView) convertView.findViewById(com.hyphenate.easeui.R.id.header);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        UserClass2 user = getItem(position);
        if(user == null)
            Log.d("ContactAdapter", position + "");
        String username = user.getUsername();
        String header = user.getInitialLetter();
        if (position == 0 || header != null && !header.equals(getItem(position - 1).getInitialLetter())) {
            if (TextUtils.isEmpty(header)) {
                holder.headerView.setVisibility(View.GONE);
            } else {
                holder.headerView.setVisibility(View.VISIBLE);
                holder.headerView.setText(header);
            }
        } else {
            holder.headerView.setVisibility(View.GONE);
        }

        if(userList.get(position).getSign().equals("0")){
            holder.check.setImageResource(R.drawable.icon_select);
        }else if(userList.get(position).getSign().equals("1")){
            holder.check.setImageResource(R.drawable.icon_select_true);
        }

        EaseAvatarOptions avatarOptions = EaseUI.getInstance().getAvatarOptions();
        if(avatarOptions != null && holder.avatar instanceof EaseImageView) {
            EaseImageView avatarView = ((EaseImageView) holder.avatar);
            if (avatarOptions.getAvatarShape() != 0)
                avatarView.setShapeType(avatarOptions.getAvatarShape());
            if (avatarOptions.getAvatarBorderWidth() != 0)
                avatarView.setBorderWidth(avatarOptions.getAvatarBorderWidth());
            if (avatarOptions.getAvatarBorderColor() != 0)
                avatarView.setBorderColor(avatarOptions.getAvatarBorderColor());
            if (avatarOptions.getAvatarRadius() != 0)
                avatarView.setRadius(avatarOptions.getAvatarRadius());
        }

        EaseUserUtils.setUserNick(username, holder.nameView);
        EaseUserUtils.setUserAvatar(getContext(), username, holder.avatar);


        if(primaryColor != 0)
            holder.nameView.setTextColor(primaryColor);
        if(primarySize != 0)
            holder.nameView.setTextSize(TypedValue.COMPLEX_UNIT_PX, primarySize);
        if(initialLetterBg != null)
            holder.headerView.setBackgroundDrawable(initialLetterBg);
        if(initialLetterColor != 0)
            holder.headerView.setTextColor(initialLetterColor);

        return convertView;
    }

    @Override
    public UserClass2 getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getPositionForSection(int section) {
        return positionOfSection.get(section);
    }

    @Override
    public int getSectionForPosition(int position) {
        return sectionOfPosition.get(position);
    }

    @Override
    public Object[] getSections() {
        positionOfSection = new SparseIntArray();
        sectionOfPosition = new SparseIntArray();
        int count = getCount();
        list = new ArrayList<String>();
        list.add(getContext().getString(com.hyphenate.easeui.R.string.search_header));
        positionOfSection.put(0, 0);
        sectionOfPosition.put(0, 0);
        for (int i = 1; i < count; i++) {

            String letter = getItem(i).getInitialLetter();
            int section = list.size() - 1;
            if (list.get(section) != null && !list.get(section).equals(letter)) {
                list.add(letter);
                section++;
                positionOfSection.put(section, i);
            }
            sectionOfPosition.put(i, section);
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public Filter getFilter() {
        if(myFilter==null){
            myFilter = new MyFilter(userList);
        }
        return myFilter;
    }

    protected class  MyFilter extends Filter{
        List<UserClass2> mOriginalList = null;

        public MyFilter(List<UserClass2> myList) {
            this.mOriginalList = myList;
        }

        @Override
        protected synchronized FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if(mOriginalList==null){
                mOriginalList = new ArrayList<UserClass2>();
            }
            EMLog.d(TAG, "contacts original size: " + mOriginalList.size());
            EMLog.d(TAG, "contacts copy size: " + copyUserList.size());

            if(prefix==null || prefix.length()==0){
                results.values = copyUserList;
                results.count = copyUserList.size();
            }else{

                if (copyUserList.size() > mOriginalList.size()) {
                    mOriginalList = copyUserList;
                }
                String prefixString = prefix.toString();
                final int count = mOriginalList.size();
                final ArrayList<UserClass2> newValues = new ArrayList<UserClass2>();
                for(int i=0;i<count;i++){
                    final UserClass2 user = mOriginalList.get(i);
                    String username = user.getUsername();

                    if(username.startsWith(prefixString)){
                        newValues.add(user);
                    }
                    else{
                        final String[] words = username.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(user);
                                break;
                            }
                        }
                    }
                }
                results.values=newValues;
                results.count=newValues.size();
            }
            EMLog.d(TAG, "contacts filter results size: " + results.count);
            return results;
        }

        @Override
        protected synchronized void publishResults(CharSequence constraint,
                                                   FilterResults results) {
            userList.clear();
            userList.addAll((List<UserClass2>)results.values);
            EMLog.d(TAG, "publish contacts filter results size: " + results.count);
            if (results.count > 0) {
                notiyfyByFilter = true;
                notifyDataSetChanged();
                notiyfyByFilter = false;
            } else {
                notifyDataSetInvalidated();
            }
        }
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(!notiyfyByFilter){
            copyUserList.clear();
            copyUserList.addAll(userList);
        }
    }

    protected int primaryColor;
    protected int primarySize;
    protected Drawable initialLetterBg;
    protected int initialLetterColor;

    public EaseContactAdapter3 setPrimaryColor(int primaryColor) {
        this.primaryColor = primaryColor;
        return this;
    }


    public EaseContactAdapter3 setPrimarySize(int primarySize) {
        this.primarySize = primarySize;
        return this;
    }

    public EaseContactAdapter3 setInitialLetterBg(Drawable initialLetterBg) {
        this.initialLetterBg = initialLetterBg;
        return this;
    }

    public EaseContactAdapter3 setInitialLetterColor(int initialLetterColor) {
        this.initialLetterColor = initialLetterColor;
        return this;
    }

}
