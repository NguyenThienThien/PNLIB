package com.example.pnlib.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlib.database.DbHelper;
import com.example.pnlib.model.PhieuMuon;
import com.example.pnlib.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDao {
    DbHelper dbHelper;

    public ThanhVienDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<ThanhVien> getDSThanhVien(){
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ThanhVien",null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean insert(String hoten, String namsinh){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HoTen",hoten);
        values.put("NamSinh",namsinh);
        long check = db.insert("ThanhVien",null,values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean update(int matv,String hoten, String namsinh){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("HoTen",hoten);
        values.put("NamSinh",namsinh);
        long check = db.update("ThanhVien",values,"MaTV = ?",new String[]{String.valueOf(matv)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    // int 1: xóa than công ; int 0: thất bại; int -1 : tìm thấy thành viên có phiếu mượn
    public int delete(int matv){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from PhieuMuon where MaTV = ?",new String[]{String.valueOf(matv)});
        if(cursor.getCount() != 0){
            return  -1;
        }

        long check = db.delete("ThanhVien","MaTV = ?",new String[]{String.valueOf(matv)});
        if(check == -1){
            return 0;
        }else{
            return 1;
        }
    }
}
