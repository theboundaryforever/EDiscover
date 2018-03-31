package com.kest.ediscover.Listener;

import android.view.View;

/**
 * Created by dk on 2017/12/23.
 */

public abstract class AccountListViewDeleteClickListener implements View.OnClickListener {
    @Override
             public void onClick(View v) {
                     myOnClick((Integer) v.getTag(), v);
                }
          public abstract void myOnClick(int position, View v);
}
