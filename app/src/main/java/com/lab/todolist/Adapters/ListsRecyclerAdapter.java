package com.lab.todolist.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lab.todolist.Models.TODOList;
import com.lab.todolist.R;
import com.lab.todolist.TasksActivity;

import java.util.ArrayList;

public class ListsRecyclerAdapter extends RecyclerView.Adapter<ListsRecyclerAdapter.TODOListViewHolder> {

    Activity activity;
    ArrayList<TODOList> data;

    public ListsRecyclerAdapter(Activity activity, ArrayList<TODOList> data) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public TODOListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.item_todolist, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        root.setLayoutParams(lp);
        return new TODOListViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull TODOListViewHolder holder, int position) {
        TODOList list = data.get(position);
        holder.tv_list_title.setText(list.getTitle());
        holder.tv_task_count.setText(list.getTasks().size() + " Tasks");

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(activity, TasksActivity.class);
            intent.putExtra("listId", list.getId());
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TODOListViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_list_title;
        public TextView tv_task_count;

        public TODOListViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_list_title = itemView.findViewById(R.id.tv_list_title);
            tv_task_count = itemView.findViewById(R.id.tv_task_count);
        }
    }
}
