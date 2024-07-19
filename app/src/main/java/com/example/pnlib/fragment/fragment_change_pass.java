package com.example.pnlib.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pnlib.R;
import com.example.pnlib.dao.ThuThuDao;
import com.example.pnlib.screen.Login_Screen;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class fragment_change_pass extends Fragment {

    TextInputLayout in_CurrentPassword, in_NewPass, in_Repass;
    TextInputEditText ed_txtCurrentPassword, ed_txtNewPass, ed_txtRepass;
    Button btnSaveMK, btnCancelSaveMK;
    private Context context ;
    public fragment_change_pass() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_pass, container, false);
        in_CurrentPassword = view.findViewById(R.id.in_CurrentPassword);
        in_NewPass = view.findViewById(R.id.in_NewPass);
        in_Repass = view.findViewById(R.id.in_RepassTT);
        ed_txtCurrentPassword = view.findViewById(R.id.ed_txtCurrentPassword);
        ed_txtNewPass = view.findViewById(R.id.ed_txtNewPass);
        ed_txtRepass = view.findViewById(R.id.ed_txtRepassTT);
        btnSaveMK = view.findViewById(R.id.btn_SaveMK);
        btnCancelSaveMK = view.findViewById(R.id.btn_CancelSaveMK);

        ed_txtCurrentPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_CurrentPassword.setError("Vui lòng không để trống mật khẩu hiện tại");
                }else{
                    in_CurrentPassword.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed_txtNewPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_NewPass.setError("Vui lòng không để trống mật khẩu");
                }else{
                    in_NewPass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed_txtRepass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_Repass.setError("Vui lòng không để trống nhập lại mật khẩu");
                }else{
                    in_NewPass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnCancelSaveMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_txtCurrentPassword.setText("");
                ed_txtNewPass.setText("");
                ed_txtRepass.setText("");
            }
        });

        btnSaveMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoiMK();
            }
        });
        return view;
    }

    public void DoiMK(){
        String oldPass = ed_txtCurrentPassword.getText().toString();
        String newPass = ed_txtNewPass.getText().toString();
        String repass = ed_txtRepass.getText().toString();
        if(newPass.equals(repass)){
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_File",getContext().MODE_PRIVATE);
            String matt = sharedPreferences.getString("Username","");
            String mk = sharedPreferences.getString("Password","");
            //cập nhật
            ThuThuDao thuThuDao =  new ThuThuDao(getContext());
            boolean check = thuThuDao.updateMK(matt,oldPass,newPass);
            if(oldPass.equals(mk)){
                if(check){
                    Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), Login_Screen.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }else{
                in_CurrentPassword.setError("Mật khẩu hiện tại không đúng");
            }
        }else{
            in_NewPass.setError("Mật Khẩu Không Khớp");
            in_Repass.setError("Mật Khẩu Không Khớp");
        }
    }
}