package com.example.pnlib.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.pnlib.R;
import com.example.pnlib.dao.SachDao;
import com.example.pnlib.dao.ThuThuDao;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Login_Screen extends AppCompatActivity {
    TextInputEditText ed_txtuser, ed_txtpass;
    TextInputLayout in_user, in_pass;
    Button btnLogin, btnCancel;
    CheckBox chkSave;
    ThuThuDao thuThuDao = new ThuThuDao(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        ed_txtuser = findViewById(R.id.ed_txtUser);
        ed_txtpass = findViewById(R.id.ed_txtPass);
        in_user = findViewById(R.id.in_User);
        in_pass = findViewById(R.id.in_Pass);
        btnLogin = findViewById(R.id.btn_LogIn);
        btnCancel = findViewById(R.id.btn_Cancel);
        chkSave = findViewById(R.id.chkSave);

        SharedPreferences pref = getSharedPreferences("User_File",MODE_PRIVATE);
        ed_txtuser.setText(pref.getString("Username",""));
        ed_txtpass.setText(pref.getString("Password",""));
        chkSave.setChecked(pref.getBoolean("Remember",false));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

    }

    public void login(){
        String user = ed_txtuser.getText().toString();
        String pass = ed_txtpass.getText().toString();
        if(user.isEmpty() || pass.isEmpty()){
            if(user.equals("")){
                in_user.setError("Không được để trông tên đăng nhập");
            }else{
                in_user.setError(null);
            }

            if(pass.equals("")){
                in_pass.setError("Không được để trông mật khẩu");
            }else{
                in_pass.setError(null);
            }
        }else{
            if(thuThuDao.checkLogin(user,pass)){
                Toast.makeText(this, "Login thành công", Toast.LENGTH_SHORT).show();
                rememberUser(user,pass,chkSave.isChecked());
                Intent i = new Intent(Login_Screen.this, MainActivity.class);
                i.putExtra("MaTT",user);
                startActivity(i);
                finish();
            }else{
                in_user.setError("Tên đăng nhập hoặc mật khẩu không đúng");
                in_pass.setError("Tên đăng nhập hoặc mật khẩu không đúng");
            }
        }
    }

    private void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("User_File",MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if(!status){
            //xóa trắng dữ liệu trước đó
            edit.clear();
        }else{
            //lưu dữ liệu
            edit.putString("Username",u);
            edit.putString("Password",p);
            edit.putBoolean("Remember",status);
        }
        //lưu lại toàn bộ
        edit.commit();
    }
}