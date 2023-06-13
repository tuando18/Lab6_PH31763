package com.dovantuan.lab6_ph31763;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Activity_Login extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private CheckBox chkGhiNho;
    Button btnLogin;
    Button btnSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edt_User);
        edtPassword = findViewById(R.id.edt_pass);

        btnLogin = findViewById(R.id.btn_login);
        btnSign = findViewById(R.id.btn_dk);
        chkGhiNho = findViewById(R.id.chk_luu);

        checkRemember();

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Login.this, Activity_Register.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<User> listUser = new ArrayList<>();
                listUser = writeUser.readUsers(Activity_Login.this);

                if (listUser != null && listUser.size() > 0) {
                    boolean loggedIn = false;
                    for (User user : listUser) {
                        if (user.getUsername().equals(edtUsername.getText().toString()) &&
                                user.getPassword().equals(edtPassword.getText().toString())) {
                            loggedIn = true;
                            break;
                        }
                    }

                    if (loggedIn) {
                        remember(edtUsername.getText().toString(), edtPassword.getText().toString(), chkGhiNho.isChecked());
                        Toast.makeText(Activity_Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Activity_Login.this, Activity_DSSV.class));
                    } else {
                        Toast.makeText(Activity_Login.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Activity_Login.this, "Bạn chưa tạo tài khoản", Toast.LENGTH_SHORT).show();
                }

                // Remember me functionality
                remember(edtUsername.getText().toString(), edtPassword.getText().toString(), chkGhiNho.isChecked());
            }
        });
    }

    private void remember(String username, String password, boolean isChecked) {
        SharedPreferences sharedPreferences = getSharedPreferences("remember", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("isChecked", isChecked);
        editor.apply();
    }

    public void checkRemember() {
        SharedPreferences sharedPreferences = getSharedPreferences("remember", MODE_PRIVATE);
        String user = sharedPreferences.getString("username", "");
        String pass = sharedPreferences.getString("password", "");
        boolean chkRemember1 = sharedPreferences.getBoolean("isChecked", false);
        chkGhiNho.setChecked(chkRemember1);
        if (chkGhiNho.isChecked()) {
            edtUsername.setText(user);
            edtPassword.setText(pass);
        }
    }

}
