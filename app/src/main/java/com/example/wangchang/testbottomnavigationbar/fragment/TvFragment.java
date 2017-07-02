package com.example.wangchang.testbottomnavigationbar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wangchang.testbottomnavigationbar.R;

/**
 * Created by WangChang on 2016/5/15.
 */
public class TvFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public static TvFragment newInstance(String content) {
        Bundle args = new Bundle();

        TvFragment fragment = new TvFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
