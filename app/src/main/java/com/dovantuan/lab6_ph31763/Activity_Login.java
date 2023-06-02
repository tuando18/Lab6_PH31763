package com.dovantuan.lab6_ph31763;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edtUsername = findViewById(R.id.edt_User);
        EditText edtPassword = findViewById(R.id.edt_pass);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        edtUsername.setText(data.getString("username"));
//        edtPassword.setText(data.getString("password"));
//        Intent intent = getIntent();
//        String name = intent.getStringExtra("username");
//        String pass = intent.getStringExtra("password");
//        edtUsername.setText(name);
//        edtPassword.setText(pass);

        Button btnLogin = findViewById(R.id.btn_login);
        Button btnSign = findViewById(R.id.btn_dk);

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
                if (bundle != null) {
                    String user = bundle.getString("username");
                    String pass = bundle.getString("password");

                    boolean u = user.equals(edtUsername.getText().toString());
                    boolean p = pass.equals(edtPassword.getText().toString());

                    if (u && p) {
                        Toast.makeText(Activity_Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Activity_Login.this, Activity_DSSV.class));
                    } else {
                        Toast.makeText(Activity_Login.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Activity_Login.this, "Bạn chưa tạo tài khoản", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}