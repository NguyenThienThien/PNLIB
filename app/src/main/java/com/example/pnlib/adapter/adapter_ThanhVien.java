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
import com.example.pnlib.dao.ThanhVienDao;
import com.example.pnlib.model.ThanhVien;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class adapter_ThanhVien extends RecyclerView.Adapter<adapter_ThanhVien.ViewHolder>{

    private Context context;
    private ArrayList<ThanhVien> list;
    ThanhVienDao dao;

    public adapter_ThanhVien(Context context, ArrayList<ThanhVien> list) {
        this.context = context;
        this.list = list;
        dao = new ThanhVienDao(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thanh_vien,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaTV.setText(String.valueOf(list.get(position).getMaTV()));
        holder.txtTenTV.setText(list.get(position).getHoTen());
        holder.txtNamSinh.setText(list.get(position).getNamSinh());
        ThanhVien tv = list.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialogUpdateTV(tv);
                return true;
            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete");
                builder.setMessage("Bạn chắc chắn muốn xóa thành viên này chứ");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check = dao.delete(list.get(holder.getAdapterPosition()).getMaTV());
                        switch (check){
                            case 1:
                                loadData();
                                Toast.makeText(context, "Xóa thành viên thành công", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa thành viên thất bại", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Thành viên đang tồn tại phiếu mượn, hiện tại không thể xóa", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Hủy",null);
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaTV, txtTenTV, txtNamSinh;
        ImageView Delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaTV = itemView.findViewById(R.id.MaTV);
            txtTenTV = itemView.findViewById(R.id.TenTV);
            txtNamSinh = itemView.findViewById(R.id.NSTV);
            Delete = itemView.findViewById(R.id.TV_Delete);
        }
    }

    @SuppressLint("MissingInflatedId")
    private void dialogUpdateTV(ThanhVien tv){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_thanhvien,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputEditText ed_txtTenTV = view.findViewById(R.id.ed_updateTenTV);
        TextInputEditText ed_txtNamSinh = view.findViewById(R.id.ed_updateNamSinh);
        TextInputEditText ed_txtMaSV = view.findViewById(R.id.ed_updateMaTV);
        TextInputLayout in_txtTenTV = view.findViewById(R.id.in_updateTenTV);
        TextInputLayout in_txtMaTV = view.findViewById(R.id.in_updateMaTV);
        TextInputLayout in_txtNamSinh = view.findViewById(R.id.in_updateNamSinh);
        Button btn_add = view.findViewById(R.id.TV_Update);
        Button btn_cancel = view.findViewById(R.id.TV_Cancel);

        ed_txtMaSV.setText(String.valueOf(tv.getMaTV()));
        ed_txtTenTV.setText(tv.getHoTen());
        ed_txtNamSinh.setText(tv.getNamSinh());

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
                int id = tv.getMaTV();

                boolean check = dao.update(id,hoten,namsinh);
                if(hoten.isEmpty() || namsinh.isEmpty()){
                    if(hoten.equals("")){
                        in_txtTenTV.setError("Vui lòng không để trống Họ Tên");
                    }else{
                        in_txtTenTV.setError(null);
                    }

                    if(namsinh.equals("")){
                        in_txtNamSinh.setError("Vui lòng không để trống năm sinh");
                    }else{
                        in_txtNamSinh.setError(null);
                    }
                }else{
                    if(check){
                        loadData();
                        Toast.makeText(context, "Cập nhật nhân viên thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(context, "Cập nhật nhân viên thất bại", Toast.LENGTH_SHORT).show();
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

    private void loadData(){
        list.clear();
        list = dao.getDSThanhVien();
        notifyDataSetChanged();
    }
}
