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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlib.R;
import com.example.pnlib.dao.SachDao;
import com.example.pnlib.model.Sach;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.text.Normalizer;
import java.util.regex.Pattern;

public class adapter_Sach extends RecyclerView.Adapter<adapter_Sach.ViewHolder> implements Filterable {

    private Context context;
    private ArrayList<Sach> list,listOld;
    private ArrayList<HashMap<String, Object>> listHM;
    private SachDao sachDao;

    public adapter_Sach(Context context, ArrayList<Sach> list, ArrayList<HashMap<String, Object>> listHM,SachDao sachDao) {
        this.context = context;
        this.list = list;
        this.listHM = listHM;
        this.listOld = list;
        this.sachDao = sachDao;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaSach.setText(String.valueOf(list.get(position).getMaSach()));
        holder.txtTenSach.setText(list.get(position).getTenSach());
        holder.txtGiaThue.setText(String.valueOf(list.get(position).getGiaThue()));
        holder.txtLoaiSach.setText(String.valueOf(list.get(position).getMaLoai()));
        holder.txtTenLS.setText(list.get(position).getTenLoai());
        holder.txtNXB.setText(String.valueOf(list.get(position).getNgayXuatBan()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialogAddSach(list.get(holder.getAdapterPosition()));
                return true;
            }
        });

        holder.Sach_Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xóa Sách");
                builder.setMessage("Bạn có chắc muốn xóa sách này chứ ?");
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int check = sachDao.delete(list.get(holder.getAdapterPosition()).getMaSach());
                        switch (check){
                            case 1:
                                loadData();
                                Toast.makeText(context, "Xóa thành công sách", Toast.LENGTH_SHORT).show();
                                break;
                            case 0:
                                Toast.makeText(context, "Xóa không thành công sách", Toast.LENGTH_SHORT).show();
                                break;
                            case -1:
                                Toast.makeText(context, "Không xóa được sách này vì đang còn tồn tại trong phiếu mượn", Toast.LENGTH_SHORT).show();
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

    // Trong adapter_Sach.java

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String tk = constraint.toString().toLowerCase(); // Chuyển chuỗi tìm kiếm về chữ thường
                if (tk.isEmpty()) {
                    list = listOld;
                } else {
                    ArrayList<Sach> listTk = new ArrayList<>();
                    for (Sach pr : listOld) {
                        String tkk = removeAccents(pr.getTenSach().toLowerCase());
                        // Chuyển tên sách trong danh sách cơ sở dữ liệu về chữ thường và so sánh với chuỗi tìm kiếm
                        if (tkk.contains(tk)) {
                            listTk.add(pr);
                        }
                    }
                    list = listTk;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<Sach>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static String removeAccents(String input) {
            return Normalizer.normalize(input, Normalizer.Form.NFD)
                    .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaSach, txtTenSach, txtGiaThue, txtLoaiSach, txtTenLS, txtNXB;
        ImageView Sach_Delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaSach = itemView.findViewById(R.id.MaSach);
            txtTenSach = itemView.findViewById(R.id.TenS);
            txtGiaThue = itemView.findViewById(R.id.Sach_GiaThue);
            txtLoaiSach = itemView.findViewById(R.id.Sach_LS);
            txtTenLS = itemView.findViewById(R.id.Sach_TenLS);
            Sach_Delete = itemView.findViewById(R.id.S_Delete);
            txtNXB = itemView.findViewById(R.id.Sach_NXB);
        }
    }

    @SuppressLint("MissingInflatedId")
    private void dialogAddSach(Sach sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.update_sach,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        TextInputLayout in_TenSach = view.findViewById(R.id.in_updateTenS);
        TextInputLayout in_GiaThue = view.findViewById(R.id.in_updateGiaThue);
        TextInputLayout in_NXB = view.findViewById(R.id.in_updateNXB);
        TextInputEditText ed_TenSach = view.findViewById(R.id.ed_updateTenS);
        TextInputEditText ed_GiaThue = view.findViewById(R.id.ed_updateGiaThue);
        TextInputEditText ed_NXB = view.findViewById(R.id.ed_updateNXB);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        Button add = view.findViewById(R.id.S_update);
        Button cancel = view.findViewById(R.id.S_Cancel);

        ed_TenSach.setText(sach.getTenSach());
        ed_GiaThue.setText(String.valueOf(sach.getGiaThue()));
        ed_NXB.setText(String.valueOf(sach.getNgayXuatBan()));

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
                context,
                listHM,
                (android.R.layout.simple_list_item_1),
                new String[]{"TenLoai"},
                new int[]{android.R.id.text1}
        );
        spnSach.setAdapter(simpleAdapter);
        int index = 0;
        int position = -1;

        for(HashMap<String, Object> item : listHM){
            if((int) item.get("MaLoai") == sach.getMaLoai()){
                position = index;
            }
            index ++;
        }
        spnSach.setSelection(position);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tensach = ed_TenSach.getText().toString();
                String checktien = ed_GiaThue.getText().toString();
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
                    boolean check = sachDao.update(sach.getMaSach(),tensach,tien,maloai, Integer.parseInt(NXB));
                    if(check){
                        loadData();
                        Toast.makeText(context, "Cập nhật thành công sách", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(context, "Cập nhật không thành công sách", Toast.LENGTH_SHORT).show();
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

    private void loadData(){
        list.clear();
        list = sachDao.getDSSach();
        notifyDataSetChanged();
    }
}
