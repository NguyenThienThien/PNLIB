package com.example.pnlib.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pnlib.R;
import com.example.pnlib.adapter.adapter_Top10;
import com.example.pnlib.dao.ThongKeDao;
import com.example.pnlib.model.Sach;

import java.util.ArrayList;
public class fragment_top extends Fragment {

    public fragment_top() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top, container, false);
        RecyclerView rcv = view.findViewById(R.id.rcv_Top);

        ThongKeDao thongKeDao = new ThongKeDao(getContext());
        ArrayList<Sach> list = thongKeDao.getTop10();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(layoutManager);
        adapter_Top10 adapter = new adapter_Top10(getContext(),list);
        rcv.setAdapter(adapter);

        return view;
    }
}