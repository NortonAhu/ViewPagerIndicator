package com.bluecup.hongyu.viewpagerindicator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Des:
 * Created by hongyu
 * Date:16/3/17_上午9:09
 */
public class ViewPagerFragment extends Fragment {

    private static final String TITLE = "title";

    public static ViewPagerFragment newInstance(String title) {
        
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());

        textView.setGravity(Gravity.CENTER);

        Bundle bundle = getArguments();
        if (bundle != null) {
            textView.setText(bundle.getString(TITLE));
        }
        return textView;
    }
}
