package com.example.pnlib.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.R;
import com.example.pnlib.model.Sach;

import java.util.ArrayList;

public class adapter_Top10 extends RecyclerView.Adapter<adapter_Top10.ViewHodel>{

    private Context context;
    private ArrayList<Sach> list;

    public adapter_Top10(Context context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top,parent,false);
        return new ViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHodel holder, int position) {
        holder.txtMaSach.setText(String.valueOf(list.get(position).getMaSach()));
        holder.txtTenSach.setText(list.get(position).getTenSach());
        holder.txtSoLuong.setText(String.valueOf(list.get(position).getSoLanMuon()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHodel extends RecyclerView.ViewHolder{
        TextView txtMaSach, txtTenSach, txtSoLuong;
        public ViewHodel(@NonNull View itemView) {
            super(itemView);
            txtMaSach = itemView.findViewById(R.id.Top_MS);
            txtTenSach = itemView.findViewById(R.id.Top_TS);
            txtSoLuong = itemView.findViewById(R.id.Top_SL);
        }
    }
}
