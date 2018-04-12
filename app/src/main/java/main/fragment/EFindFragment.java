package main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kest.ediscover.R;

/**
 * E发现
 * Created by Administrator on 2018/4/7 0007.
 */
public class EFindFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=null;
        if(view==null){
            view=inflater.inflate(R.layout.fragment_efind,null);
        }
        return view;
    }
}
