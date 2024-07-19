package com.example.pnlib.fragment;

import android.annotation.SuppressLint;
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
import com.example.pnlib.model.ThuThu;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class fragment_add_user extends Fragment {
    public fragment_add_user() {
        // Required empty public constructor
    }

    TextInputLayout in_MaTT,in_HoTenTT,in_PassTT,in_Repass;
    TextInputEditText ed_txtMaTT, ed_txtHoTenTT, ed_txtPassTT, ed_txtRepass;
    Button btnAdd, btnCancel;
    ArrayList<ThuThu> list;
    ThuThuDao dao;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);
        in_MaTT = view.findViewById(R.id.in_MaTT);
        in_HoTenTT = view.findViewById(R.id.in_HoTenTT);
        in_PassTT = view.findViewById(R.id.in_Pass);
        in_Repass = view.findViewById(R.id.in_repass);
        ed_txtMaTT = view.findViewById(R.id.ed_txtMaTT);
        ed_txtHoTenTT = view.findViewById(R.id.ed_txtHoTenTT);
        ed_txtPassTT = view.findViewById(R.id.ed_txtPass);
        ed_txtRepass = view.findViewById(R.id.ed_txtRepass);
        btnAdd = view.findViewById(R.id.btn_CreateTT);
        btnCancel = view.findViewById(R.id.btn_CancelCreateTT);

        dao = new ThuThuDao(getContext());

        ed_txtMaTT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_MaTT.setError("Vui lòng không để trống mã thủ thư");
                }else{
                    in_MaTT.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed_txtHoTenTT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_HoTenTT.setError("Vui lòng không để trống họ tên");
                }else{
                    in_HoTenTT.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed_txtPassTT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_PassTT.setError("Vui lòng không để trống mật khẩu");
                }else{
                    in_PassTT.setError(null);
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
                    in_Repass.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ed_txtMaTT.setText("");
                ed_txtHoTenTT.setText("");
                ed_txtPassTT.setText("");
                ed_txtRepass.setText("");
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matt = ed_txtMaTT.getText().toString();
                String hoten = ed_txtHoTenTT.getText().toString();
                String pass = ed_txtPassTT.getText().toString();
                String repass = ed_txtRepass.getText().toString();

                ThuThu tt = new ThuThu(matt,hoten,pass);

                if(matt.isEmpty() || hoten.isEmpty() || pass.isEmpty() || repass.isEmpty()){
                    if(matt.equals("")){
                        in_MaTT.setError("Vui lòng không để trống mã thủ thư!");
                    }else{
                        in_MaTT.setError(null);
                    }

                    if(hoten.equals("")){
                        in_HoTenTT.setError("Vui lòng không để trống họ tên");
                    }else{
                        in_HoTenTT.setError(null);
                    }

                    if(pass.equals("")){
                        in_PassTT.setError("Vui lòng không để trống mật khẩu");
                    }else{
                        in_PassTT.setError(null);
                    }

                    if(repass.equals("")){
                        in_Repass.setError("Vui lòng không để trống Repass");
                    }else{
                        in_Repass.setError(null);
                    }
                }else if(dao.checkUser(matt)){
                    in_MaTT.setError("Mã thủ thư đã tồn tại vui lòng nhập mã khác");
                }else if(!pass.equals(repass)){
                    in_PassTT.setError("Mật khẩu không trùng khớp");
                    in_Repass.setError("Mật khẩu không trùng khớp");
                }else if(dao.insert(tt)){
                    Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
}