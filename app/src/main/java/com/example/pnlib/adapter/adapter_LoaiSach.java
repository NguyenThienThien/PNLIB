package com.example.pnlib.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.R;
import com.example.pnlib.dao.LoaiSachDao;
import com.example.pnlib.model.LoaiSach;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class adapter_LoaiSach extends RecyclerView.Adapter<adapter_LoaiSach.ViewHolder>{

    private Context context;
    private ArrayList<LoaiSach> list;

    LoaiSachDao dao;

    public adapter_LoaiSach(Context context, ArrayList<LoaiSach> list) {
        this.context = context;
        this.list = list;
        dao = new LoaiSachDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loai_sach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaLoai.setText(String.valueOf(list.get(position).getMaLS()));
        holder.txtTenLoai.setText(String.valueOf(list.get(position).getTenLS()));
        LoaiSach ls = list.get(position);

        holder.LS_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa loại sách");
                builder.setMessage("Bạn có muốn xóa loại sách này không ?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LoaiSachDao dao = new LoaiSachDao(context);
                        int check = dao.deleteLS(list.get(holder.getAdapterPosition()).getMaLS());
                        switch (check){
                            case 1:
                                list.clear();
                                list = dao.getDSLoaiSach();
                                notifyDataSetChanged();
                                Toast.makeText(context, "Xóa loại sách thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Không thể xóa vì đang có sách thuộc thể loại", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa loại sách không thành công", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Cancel",null);
                builder.create().show();
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialogUpdateLS(ls);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaLoai, txtTenLoai;
        ImageView LS_Delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.MaLS);
            txtTenLoai = itemView.findViewById(R.id.TenLS);
            LS_Delete = itemView.findViewById(R.id.LS_Delete);
        }
    }

    @SuppressLint("MissingInflatedId")
    private void dialogUpdateLS(LoaiSach ls){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_loaisach,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputLayout in_TenLS = view.findViewById(R.id.in_updateTenLS);
        TextInputEditText ed_TenLS = view.findViewById(R.id.ed_updateTenLS);
        TextInputLayout in_MaLS = view.findViewById(R.id.in_updateMaLS);
        TextInputEditText ed_MaLS = view.findViewById(R.id.ed_updateMaLS);
        Button UpdateLS = view.findViewById(R.id.LS_update);
        Button CancelLS = view.findViewById(R.id.LS_Cancelupdtae);

        ed_MaLS.setText(String.valueOf(ls.getMaLS()));
        ed_TenLS.setText(ls.getTenLS());

        ed_TenLS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0){
                    in_TenLS.setError("Vui lòng nhập tên loại sách");
                }else{
                    in_TenLS.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        UpdateLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ls.setTenLS(ed_TenLS.getText().toString());

                String TenLS = ed_TenLS.getText().toString();

                if(TenLS.isEmpty()){
                    if(TenLS.equals("")){
                        in_TenLS.setError("Vui Lòng Nhập Tên Loại Sách");
                    }else{
                        in_TenLS.setError(null);
                    }
                }else{
                    if(dao.update(ls)){
                        list.clear();
                        list.addAll(dao.getDSLoaiSach());
                        notifyDataSetChanged();
                        dialog.dismiss();
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Update không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        CancelLS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
