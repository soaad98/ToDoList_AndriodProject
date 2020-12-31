package com.lab.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lab.todolist.Utils.Helpers;

public class LoginActivity extends AppCompatActivity {

    EditText et_Email, et_Password;
    ProgressBar pc_loading;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        et_Email = findViewById(R.id.et_Email);
        et_Password = findViewById(R.id.et_Password);
        pc_loading = findViewById(R.id.pc_loading);

        Button btn_Login = findViewById(R.id.btn_Login);
        btn_Login.setOnClickListener(view -> {
            Helpers.HideKeyboard(LoginActivity.this);

            String email = et_Email.getText().toString().trim();
            String password = et_Password.getText().toString().trim();
            if (email.isEmpty()) {
                et_Email.setError("Please enter email");
                return;
            }
            if (password.isEmpty()) {
                et_Password.setError("Please enter password");
                return;
            }
            pc_loading.bringToFront();
            pc_loading.setVisibility(View.VISIBLE);
            btn_Login.setEnabled(false);
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        pc_loading.setVisibility(View.GONE);
                        btn_Login.setEnabled(true);
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, ListsActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        TextView tv_register = findViewById(R.id.tv_register);
        tv_register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, ListsActivity.class);
            startActivity(intent);
            finish();
        }
    }
}