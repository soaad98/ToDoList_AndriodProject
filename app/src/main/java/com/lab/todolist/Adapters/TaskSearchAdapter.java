package com.lab.todolist.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.lab.todolist.ListsActivity;
import com.lab.todolist.Models.TODOTaskSearch;
import com.lab.todolist.R;
import com.lab.todolist.ViewTaskActivity;

import java.util.ArrayList;

public class TaskSearchAdapter extends RecyclerView.Adapter<TaskSearchAdapter.searchItemViewHolder> {

    Activity activity;
    ArrayList<TODOTaskSearch> data = new ArrayList<TODOTaskSearch>();
    DatabaseReference listsRef;

    public TaskSearchAdapter(Activity activity, ArrayList<TODOTaskSearch> data, DatabaseReference listsRef) {
        this.activity = activity;
        this.data = data;
        this.listsRef = listsRef;
    }

    @NonNull
    @Override
    public searchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.item_todotask_search, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        root.setLayoutParams(lp);
        return new searchItemViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull searchItemViewHolder holder, int position) {
        TODOTaskSearch search_task = data.get(position);

        holder.tv_list_title.setText(search_task.getListTitle());
        holder.tv_task_title.setText(search_task.getTaskTitle());
        holder.cb_task_check.setChecked(search_task.isChecked());

        if(search_task.isChecked()){
            holder.tv_task_title.setTextColor(activity.getColor(R.color.check_text));
            holder.tv_task_title.setBackground(activity.getDrawable(R.drawable.checket_text_line));
        }

        holder.cb_task_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            listsRef.child(search_task.getListId()).child("tasks").child(search_task.getTaskId()).child("checked").setValue(isChecked);
            if(isChecked){
                holder.tv_task_title.setTextColor(activity.getColor(R.color.check_text));
                holder.tv_task_title.setBackground(activity.getDrawable(R.drawable.checket_text_line));
            } else {
                holder.tv_task_title.setTextColor(activity.getColor(R.color.uncheck_text));
                holder.tv_task_title.setBackground(null);
            }
        });

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(activity, ViewTaskActivity.class);
            intent.putExtra("listId", search_task.getListId());
            intent.putExtra("taskId", search_task.getTaskId());
            activity.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class searchItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_task_title;
        private final TextView tv_list_title;
        private final CheckBox cb_task_check;

        public searchItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_task_title = itemView.findViewById(R.id.tv_task_title);
            tv_list_title = itemView.findViewById(R.id.tv_list_title);
            cb_task_check = itemView.findViewById(R.id.cb_task_check);
        }
    }
}
