package main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kest.ediscover.R;

/**
 * Created by Administrator on 2018/4/7 0007.
 */
//E信
public class ELatterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
      View view=null;
      if(view==null){
          view=inflater.inflate(R.layout.fragment_elatter,null);
      }
        return view;
    }
}
