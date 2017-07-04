package com.example.wangchang.testbottomnavigationbar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangchang.testbottomnavigationbar.Been.SimpleTaskBeen;

import java.util.List;

/**
 * Created by ACM on 2017/7/2.
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.taskView>{
    private List<SimpleTaskBeen> tasks;
    public  TaskAdapter(List<SimpleTaskBeen> tasks){
        this.tasks = tasks;
    }
    public static OnItemClickListener itemClickListener;
    public void setonItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener= itemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    @Override
    public int getItemCount() {

        return tasks.size();
    }
    public static class taskView extends  RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView textView;


        public taskView(View itemView){
            super(itemView);
            imageView= (ImageView) itemView.findViewById(R.id.istaskcomplete);

            textView= (TextView) itemView.findViewById(R.id.tasktitle);
           textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            if(itemClickListener!=null){
                itemClickListener.onItemClick(v,getPosition());
            }
        }
    }
    @Override
    public taskView onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskitemlayout,parent,false);

        return new taskView(view);
    }

    @Override
    public void onBindViewHolder(taskView holder, int position) {

        holder.imageView.setImageResource(R.drawable.ic_task_finished);
      //  holder.imageView.setVisibility(View.VISIBLE);
       if(tasks.get(position).isCompleted()==true){
            holder.imageView.setVisibility(View.VISIBLE);
        }else{
            holder.imageView.setVisibility(View.INVISIBLE);
        }
        holder.textView.setText(tasks.get(position).getTitle());

    }

}
