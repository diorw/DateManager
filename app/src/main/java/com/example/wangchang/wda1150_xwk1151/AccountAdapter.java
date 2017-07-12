package com.example.wangchang.wda1150_xwk1151;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangchang.wda1150_xwk1151.Been.AccountBeen;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by dell-pc on 2017/7/11.
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.accountView> {

    private List<AccountBeen>  accounts;
    public AccountAdapter(List<AccountBeen> accounts){
        this.accounts =accounts;
    }
    public static OnItemClickListener itemClickListener;
    public void setonItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener= itemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    @Override
    public accountView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.accountitem,parent,false);

        return new accountView(view);
    }

    @Override
    public void onBindViewHolder(accountView holder, int position) {
        holder.accountname.setText(accounts.get(position).getName());
        holder.accountintro.setText(accounts.get(position).getIntroduce());
        DecimalFormat decimalFormat=new DecimalFormat("##0.00");
        if (accounts.get(position).getType().equals("收入")){

            holder.accountmoney.setText("+"+decimalFormat.format(accounts.get(position).getMoney()));
        }
        else if (accounts.get(position).getType().equals("支出")){
            holder.accountmoney.setText("-"+decimalFormat.format(accounts.get(position).getMoney()));
        }
        holder.accounttime.setText(accounts.get(position).getDate());
    }


    @Override
    public int getItemCount() {
        return accounts.size();
    }


    public static class accountView extends  RecyclerView.ViewHolder implements View.OnClickListener{

        TextView accountname;
        TextView accountintro;
        TextView accountmoney;
        TextView accounttime;

        public accountView(View itemView){
            super(itemView);
            accountname = (TextView) itemView.findViewById(R.id.accountname);
            accountintro = (TextView) itemView.findViewById(R.id.accountintroduce);
            accountmoney = (TextView) itemView.findViewById(R.id.accountmoney);
            accounttime = (TextView) itemView.findViewById(R.id.accounttime);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            if(itemClickListener!=null){
                itemClickListener.onItemClick(v,getPosition());
            }
        }
    }
}
