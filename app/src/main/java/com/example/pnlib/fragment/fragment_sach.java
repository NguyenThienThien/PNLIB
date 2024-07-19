package com.example.pnlib.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pnlib.R;
import com.example.pnlib.adapter.adapter_Sach;
import com.example.pnlib.dao.LoaiSachDao;
import com.example.pnlib.dao.SachDao;
import com.example.pnlib.model.LoaiSach;
import com.example.pnlib.model.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class fragment_sach extends Fragment {

    public fragment_sach() {
        // Required empty public constructor
    }

    RecyclerView rcv;
    FloatingActionButton fltAdd, sort;
    ArrayList<Sach> list;
    ArrayList<Sach> listS = new ArrayList<>();
    SachDao sachDao;
    adapter_Sach adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sach, container, false);
        rcv = view.findViewById(R.id.rcv_S);
        fltAdd = view.findViewById(R.id.add_S);
        sort = view.findViewById(R.id.sort_sach);
        setHasOptionsMenu(true);

        sachDao = new SachDao(getContext());
        list = sachDao.getDSSach();
        listS = sachDao.getDSSach();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapter = new adapter_Sach(getContext(),list, getDSLoaiSach(), sachDao);
        rcv.setAdapter(adapter);

        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAllSach();
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Showfilter();
            }
        });

        return view;
    }

    // sắp xếp
    private void Showfilter() {
        PopupMenu popupMenu = new PopupMenu(getContext(),sort);
        popupMenu.getMenuInflater().inflate(R.menu.sort_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId() == R.id.item_tangdan){
                    sachDao = new SachDao(getContext());
                    list = sachDao.getDSSachTang();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    rcv.setLayoutManager(layoutManager);
                    adapter = new adapter_Sach(getContext(),list, getDSLoaiSach(), sachDao);
                    rcv.setAdapter(adapter);
                }

                if(menuItem.getItemId() == R.id.item_giamdan){
                    sachDao = new SachDao(getContext());
                    list = sachDao.getDSSachGiam();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    rcv.setLayoutManager(layoutManager);
                    adapter = new adapter_Sach(getContext(),list, getDSLoaiSach(), sachDao);
                    rcv.setAdapter(adapter);
                }

                if(menuItem.getItemId() == R.id.item_ten){
                    sachDao = new SachDao(getContext());
                    list = sachDao.getDSSachTheoTen();
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    rcv.setLayoutManager(layoutManager);
                    adapter = new adapter_Sach(getContext(),list, getDSLoaiSach(), sachDao);
                    rcv.setAdapter(adapter);
                }
                return false;
            }
        });
        popupMenu.show();
    }

    // tìm kiếm có dấu

    private String removeDiacritics(String text){
        return Normalizer.normalize(text,Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                list.clear();
                String search = removeDiacritics(newText).toLowerCase();
                for (Sach s: listS){
                    String tensach = removeDiacritics(s.getTenSach().toLowerCase());
                    if(tensach.contains(search)){
                        list.add(s);
                    }
                    adapter.notifyDataSetChanged();
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("MissingInflatedId")
    private void dialogAllSach(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.add_sach,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputLayout in_TenSach = view.findViewById(R.id.in_addTenS);
        TextInputLayout in_GiaThue = view.findViewById(R.id.in_addGiaThue);
        TextInputLayout in_NXB = view.findViewById(R.id.in_addNXB);
        TextInputEditText ed_TenSach = view.findViewById(R.id.ed_addTenS);
        TextInputEditText ed_GiaThue = view.findViewById(R.id.ed_addGiaThue);
        TextInputEditText ed_NXB = view.findViewById(R.id.ed_addNXB);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        Button add = view.findViewById(R.id.S_add);
        Button cancel = view.findViewById(R.id.S_Cancel);

        ed_NXB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_NXB.setError("Vui lòng không để trống tên sách");
                }else{
                    in_NXB.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed_TenSach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_TenSach.setError("Vui lòng không để trống tên sách");
                }else{
                    in_TenSach.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ed_GiaThue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_GiaThue.setError("Vui lòng không để trống giá thuê");
                }else{
                    in_GiaThue.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDSLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"TenLoai"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensach = ed_TenSach.getText().toString();
                String checktien = ed_GiaThue.getText().toString();;
                HashMap<String, Object> hs = (HashMap<String, Object>) spnSach.getSelectedItem();
                int maloai = (int) hs.get("MaLoai");
                String NXB = ed_NXB.getText().toString();


                if(tensach.isEmpty() || checktien.isEmpty() || NXB.isEmpty()){
                    if(tensach.equals("")){
                        in_TenSach.setError("Vui lòng không để trống tên sách");
                    }else{
                        in_TenSach.setError(null);
                    }

                    if(checktien.equals("")){
                        in_GiaThue.setError("Vui lòng không để trống giá thuê");
                    }else{
                        in_GiaThue.setError(null);
                    }

                    if(NXB.equals("")){
                        in_NXB.setError("Vui lòng không để trống ngày xuất bản");
                    }else{
                        in_NXB.setError(null);
                    }
                }else{
                    int tien = Integer.parseInt(checktien);
                    boolean check = sachDao.insert(tensach,tien,maloai, Integer.parseInt(NXB));
                    if(check){
                        list.clear();
                        list.addAll(sachDao.getDSSach());
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Thêm thành công sách", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(getContext(), "Thêm không thành công sách", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private ArrayList<HashMap<String , Object>> getDSLoaiSach(){
        LoaiSachDao loaisach = new LoaiSachDao(getContext());
        ArrayList<LoaiSach> list1 = loaisach.getDSLoaiSach();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (LoaiSach ls : list1){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("MaLoai", ls.getMaLS());
            hs.put("TenLoai", ls.getTenLS());
            listHM.add(hs);
        }
        return listHM;
    }

}