package com.example.pnlib.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pnlib.R;
import com.example.pnlib.adapter.adapter_phieu_muon;
import com.example.pnlib.dao.PhieuMuonDao;
import com.example.pnlib.dao.SachDao;
import com.example.pnlib.dao.ThanhVienDao;
import com.example.pnlib.model.PhieuMuon;
import com.example.pnlib.model.Sach;
import com.example.pnlib.model.ThanhVien;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class fragment_phieu_muon extends Fragment {

    RecyclerView rcvPM;
    FloatingActionButton fladd;
    PhieuMuonDao phieuMuonDao;
    adapter_phieu_muon adapter;
    public fragment_phieu_muon() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);

        rcvPM = view.findViewById(R.id.rcv_PM);
        fladd = view.findViewById(R.id.add_PM);
        phieuMuonDao =  new PhieuMuonDao(getContext());
        loadData();

        fladd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddPM();
            }
        });

        return view;
    }

    private void dialogAddPM() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_phieumuon,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        Spinner spnThanhVien = view.findViewById(R.id.spnThanhVien);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        Button PM_add = view.findViewById(R.id.PM_add);
        Button PM_cancel = view.findViewById(R.id.PM_Cancel);
        getDataThanhVien(spnThanhVien);
        getDataSach(spnSach);

        PM_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        PM_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnThanhVien.getSelectedItem();
                int matv = (int) hsTV.get("MaTV");
                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                int masach = (int) hsSach.get("MaSach");
                int tien = (int) hsSach.get("GiaThue");

                AddPM(matv,masach,tien);
                dialog.dismiss();
            }
        });
    }

    private void AddPM(int matv, int masach, int tien) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_File",Context.MODE_PRIVATE);
        String matt = sharedPreferences.getString("Username","");

        Date currenTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currenTime);

        PhieuMuon phieuMuon = new PhieuMuon(matt,matv,masach,tien,0,ngay);
        boolean check = phieuMuonDao.insert(phieuMuon);
        if(check){
            Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
            loadData();
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void getDataThanhVien(Spinner spnThanhVien){
        ThanhVienDao thanhVienDao = new ThanhVienDao(getContext());
        ArrayList<ThanhVien> list = thanhVienDao.getDSThanhVien();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(ThanhVien tv : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MaTV",tv.getMaTV());
            hs.put("HoTen",tv.getHoTen());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"HoTen"},
                new int[]{android.R.id.text1});
                spnThanhVien.setAdapter(simpleAdapter);
    }

    private void getDataSach(Spinner spnSach){
        SachDao sachDao = new SachDao(getContext());
        ArrayList<Sach> list = sachDao.getDSSach();

        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(Sach sc : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MaSach",sc.getMaSach());
            hs.put("TenSach",sc.getTenSach());
            hs.put("GiaThue",sc.getGiaThue());
            listHM.add(hs);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"TenSach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }

    private void loadData(){
        ArrayList<PhieuMuon> list = phieuMuonDao.getDSPhieuMuon();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvPM.setLayoutManager(layoutManager);
        adapter = new adapter_phieu_muon(list,getContext());
        rcvPM.setAdapter(adapter);
    }
}