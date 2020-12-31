package com.lab.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.lab.todolist.Utils.Helpers;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText et_Email, et_Name, et_Password;
    ProgressBar pc_loading;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        et_Email = findViewById(R.id.et_Email);
        et_Name = findViewById(R.id.et_Name);
        et_Password = findViewById(R.id.et_Password);
        pc_loading = findViewById(R.id.pc_loading);

        Button btn_Register = findViewById(R.id.btn_Register);
        btn_Register.setOnClickListener(view -> {
            Helpers.HideKeyboard(RegisterActivity.this);

            String email = et_Email.getText().toString().trim();
            String name = et_Name.getText().toString().trim();
            String password = et_Password.getText().toString().trim();
            if (email.isEmpty()) {
                et_Email.setError("Please enter email");
                return;
            }
            if (name.isEmpty()) {
                et_Name.setError("Please enter name");
                return;
            }
            if (password.isEmpty()) {
                et_Password.setError("Please enter password");
                return;
            }
            pc_loading.bringToFront();
            pc_loading.setVisibility(View.VISIBLE);
            btn_Register.setEnabled(false);
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        pc_loading.setVisibility(View.GONE);
                        btn_Register.setEnabled(true);
                        if (task.isSuccessful()) {

                            currentUser = firebaseAuth.getCurrentUser();
                            String uid = currentUser.getUid();
                            Map<String, Object> data = new HashMap<>();
                            data.put("uid", uid);
                            data.put("name", name);
                            FirebaseDatabase.getInstance().getReference("Users").child(uid).setValue(data)
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnSuccessListener(aVoid -> {
                                        Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this, ListsActivity.class);
                                        startActivity(intent);
                                        finish();
                                    });

                        } else {
                            Toast.makeText(RegisterActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        TextView tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(RegisterActivity.this, ListsActivity.class);
            startActivity(intent);
            finish();
        }
    }
}