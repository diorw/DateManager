package com.example.wangchang.wda1150_xwk1151;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by dell-pc on 2017/7/5.
 */

public class MonthAdapter extends BaseAdapter {

    private ArrayList<String> mMonthList = new ArrayList<>();
    private ArrayList<String> mInList = new ArrayList<>();
    private ArrayList<String> mOutList = new ArrayList<>();
    private ArrayList<String> mAllList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;



    public MonthAdapter(FragmentActivity context,
                        ArrayList<String> monthList,
                        ArrayList<String> inList,
                        ArrayList<String> outList,
                        ArrayList<String> allList){
        mMonthList = monthList;
        mInList = inList;
        mOutList = outList;
        mAllList = allList;
        mContext = context;
        mInflater = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return mMonthList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMonthList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewTag viewTag;

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.account_month,null);

            viewTag = new ItemViewTag((TextView) convertView.findViewById(R.id.month_tittle),
                    (TextView) convertView.findViewById(R.id.month_in),
                    (TextView) convertView.findViewById(R.id.month_out),
                    (TextView) convertView.findViewById(R.id.month_all));
            convertView.setTag(viewTag);
        }
        else {

            viewTag = (ItemViewTag) convertView.getTag();

        }
        BigDecimal b   =   new   BigDecimal(mInList.get(position));
//        DecimalFormat decimalFormat=new DecimalFormat(".00");
        Float in =  b.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();

        viewTag.mMonth.setText(mMonthList.get(position));
        viewTag.mInsum.setText(mInList.get(position));
        viewTag.mOutsum.setText(mOutList.get(position));
        viewTag.mAllsum.setText(mAllList.get(position));


        return convertView;

    }

    private class ItemViewTag {
        protected TextView mMonth;
        protected TextView mInsum;
        protected TextView mOutsum;
        protected TextView mAllsum;

        public ItemViewTag(TextView month,TextView insum,TextView outsum,TextView allsum){
            this.mMonth = month;
            this.mInsum = insum;
            this.mOutsum = outsum;
            this.mAllsum = allsum;
        }


    }
}
