package com.example.pnlib.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pnlib.R;
import com.example.pnlib.adapter.adapter_ThanhVien;
import com.example.pnlib.dao.ThanhVienDao;
import com.example.pnlib.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class fragment_thanh_vien extends Fragment {

    FloatingActionButton fltAll;
    RecyclerView rcv;
    ThanhVienDao dao;
    ArrayList<ThanhVien> list;

    public fragment_thanh_vien() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        rcv = view.findViewById(R.id.rcv_TV);
        fltAll = view.findViewById(R.id.add_TV);
        dao = new ThanhVienDao(getContext());
        loadData();

        fltAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddTV();
            }
        });
        return view;
    }

    private void loadData(){
        list = dao.getDSThanhVien();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapter_ThanhVien adapter = new adapter_ThanhVien(getContext(),list);
        rcv.setAdapter(adapter);
    }

    @SuppressLint("MissingInflatedId")
    private void dialogAddTV(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_thanhvien,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText ed_txtTenTV = view.findViewById(R.id.ed_addTenTV);
        TextInputEditText ed_txtNamSinh = view.findViewById(R.id.ed_addNamSinh);
        TextInputLayout in_txtTenTV = view.findViewById(R.id.in_addTenTV);
        TextInputLayout in_txtNamSinh = view.findViewById(R.id.in_addNamSinh);
        Button btn_add = view.findViewById(R.id.TV_add);
        Button btn_cancel = view.findViewById(R.id.TV_Cancel);

        ed_txtTenTV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_txtTenTV.setError("Vui lòng nhập tên thành viên");
                }else{
                    in_txtTenTV.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed_txtNamSinh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_txtNamSinh.setError("Vui lòng không để trống năm sinh");
                }else{
                    in_txtNamSinh.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hoten = ed_txtTenTV.getText().toString();
                String namsinh = ed_txtNamSinh.getText().toString();

                if(hoten.isEmpty() || namsinh.isEmpty()){
                    if(hoten.equals("")){
                        in_txtTenTV.setError("Vui lòng nhập đầy đủ tên thành viên");
                    }else{
                        in_txtTenTV.setError(null);
                    }

                    if(namsinh.equals("")){
                        in_txtNamSinh.setError("Vui lòng nhập đầy đủ năm sinh thành viên");
                    }else{
                        in_txtNamSinh.setError(null);
                    }
                }else{
                    boolean check = dao.insert(hoten,namsinh);
                    if(check){
                        loadData();
                        Toast.makeText(getContext(), "Thêm Thành Viên Thành Công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Thêm Thành Viên Không Thành Công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}