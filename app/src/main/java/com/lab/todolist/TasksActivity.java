package com.lab.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lab.todolist.Adapters.TaskSearchAdapter;
import com.lab.todolist.Adapters.TasksRecyclerAdapter;
import com.lab.todolist.Models.TODOList;
import com.lab.todolist.Models.TODOTask;
import com.lab.todolist.Utils.Helpers;
import com.lab.todolist.Utils.ListPaddingDecoration;

public class TasksActivity extends AppCompatActivity {

    EditText et_task_create;
    TextView tv_listTitle;
    RecyclerView taskRecycler;
    TasksRecyclerAdapter tasksRecyclerAdapter;
    TaskSearchAdapter taskSearchAdapter;
    TODOList todoList = new TODOList();
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private DatabaseReference listRef;
    private String uid;
    private ProgressBar pc_loading;
    private TextView tv_delete_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(TasksActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        uid = currentUser.getUid();

        String listId = getIntent().getStringExtra("listId");
        listRef = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("lists").child(listId);

        taskRecycler = findViewById(R.id.taskRecycler);
        ListPaddingDecoration dividerItemDecoration = new ListPaddingDecoration(this);
        taskRecycler.addItemDecoration(dividerItemDecoration);
        taskRecycler.setLayoutManager(new LinearLayoutManager(this));
        tasksRecyclerAdapter = new TasksRecyclerAdapter(TasksActivity.this, todoList, listRef);
        taskRecycler.setAdapter(tasksRecyclerAdapter);

        et_task_create = findViewById(R.id.et_task_create);
        et_task_create.setOnEditorActionListener((view, actionId, event) -> {
            if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_SEND) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                Helpers.HideKeyboard(TasksActivity.this);
                String titleText = et_task_create.getText().toString().trim();
                if (titleText.isEmpty()) {
                    et_task_create.setError("please enter title");
                    return false;
                } else {
                    AddTODOTask(titleText);
                    et_task_create.getText().clear();
                }
            }
            return true;
        });

        tv_listTitle = findViewById(R.id.tv_listTitle);
        pc_loading = findViewById(R.id.pc_loading);
        pc_loading.bringToFront();
        pc_loading.setVisibility(View.VISIBLE);
        listRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pc_loading.setVisibility(View.VISIBLE);
                if (!dataSnapshot.exists()) return;
                todoList.setId((String) dataSnapshot.child("id").getValue());
                todoList.setTitle((String) dataSnapshot.child("title").getValue());
                todoList.getTasks().clear();
                if (dataSnapshot.child("tasks").exists()) {
                    for (DataSnapshot tasksSnapshot : dataSnapshot.child("tasks").getChildren()) {
                        todoList.getTasks().add(tasksSnapshot.getValue(TODOTask.class));
                    }
                }
                tv_listTitle.setText(todoList.getTitle() + " List");
                pc_loading.setVisibility(View.GONE);
                tasksRecyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        tv_delete_list = findViewById(R.id.tv_delete_list);
        tv_delete_list.setOnClickListener(view -> {
            listRef.removeValue();
            onBackPressed();
        });
        ImageButton btn_back = findViewById(R.id.btn_back);
        ImageButton btn_search = findViewById(R.id.btn_search);
        btn_back.setOnClickListener(view -> onBackPressed());
        btn_search.setOnClickListener(view -> onBackPressed());
    }

    private void AddTODOTask(String titleText) {
        String taskId = listRef.child("tasks").push().getKey();
        TODOTask newTask = new TODOTask(taskId, titleText, "2020-20-20 : 20:20", "desc", false);
        listRef.child("tasks").child(taskId).setValue(newTask);
        Toast.makeText(TasksActivity.this, "to-do task has been added successfully", Toast.LENGTH_SHORT).show();
    }
}