package com.kest.ediscover;

import android.app.Activity;
import android.view.View;

import com.kest.ediscover.utils.ActivityCollector;

/**
 * Created by Administrator on 2018\4\2 0002.
 */

public class BaseActivitys extends Activity implements View.OnClickListener{


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void onClick(View view) {

    }
}
