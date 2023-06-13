package com.dovantuan.lab6_ph31763;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Register extends AppCompatActivity {

    //    ActivityResultLauncher<Intent> getData = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    Intent intent = result.getData();
//
//                    int number = intent.getIntExtra("number", 0);
//
//                    Toast.makeText(getApplicationContext(), number + "", Toast.LENGTH_SHORT).show();
//                }
//            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText edtUsername = findViewById(R.id.edt_User_dki);
        EditText edtPassword = findViewById(R.id.edt_pass_dki);
        EditText edtRetypePass = findViewById(R.id.edt_repass);

        Button btnRegister = findViewById(R.id.btn_sign);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sUserName = edtUsername.getText().toString().trim();
                String sPassword = edtPassword.getText().toString().trim();
                String sRetypePass = edtRetypePass.getText().toString().trim();

                if (sUserName.equals("")) {
                    Toast.makeText(Activity_Register.this, "Username không được để trống!", Toast.LENGTH_SHORT).show();
                } else if (sPassword.equals("")) {
                    Toast.makeText(Activity_Register.this, "Password không được để trống!", Toast.LENGTH_SHORT).show();
                } else if (sRetypePass.equals("")) {
                    Toast.makeText(Activity_Register.this, "Password nhập lại không được để trống!", Toast.LENGTH_SHORT).show();
                } else if (!sRetypePass.equals(sPassword)) {
                    Toast.makeText(Activity_Register.this, "Password nhập lại chưa đúng!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), Activity_Login.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("username", sUserName);
                    bundle.putString("password", sPassword);

                    User user = new User(sUserName, sPassword);
                    writeUser.writeUser(Activity_Register.this, user);

                    intent.putExtras(bundle);

                    startActivity(intent);

                    Toast.makeText(Activity_Register.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}