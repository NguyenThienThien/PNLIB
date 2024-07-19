package com.example.pnlib.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlib.database.DbHelper;
import com.example.pnlib.model.LoaiSach;
import com.example.pnlib.model.PhieuMuon;

import java.util.ArrayList;

public class LoaiSachDao {
    DbHelper dbHelper;

    public LoaiSachDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<LoaiSach> getDSLoaiSach(){
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from LoaiSach",null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean insert(String tenloai){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HoTen", tenloai);
        long check = db.insert("LoaiSach",null,values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    // xóa loại sách
    // 1: xóa thành công, 0: xóa thất bại, -1 : có sách tồn tại trong loại đó
    public int deleteLS(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Sach where MaLoai = ?", new String[]{String.valueOf(id)});
        if(cursor.getCount() != 0){
            return -1;
        }
        long check = db.delete("LoaiSach","MaLoai = ?", new String[]{String.valueOf(id)});
        if(check == -1){
            return 0;
        }else{
            return 1;
        }
    }

    public boolean update(LoaiSach loaiSach){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HoTen", loaiSach.getTenLS());
        long check = db.update("LoaiSach",values,"MaLoai = ?", new String[]{String.valueOf(loaiSach.getMaLS())});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }
}
