package com.example.pnlib.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlib.database.DbHelper;
import com.example.pnlib.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonDao {
    DbHelper dbHelper;

    public PhieuMuonDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<PhieuMuon> getDSPhieuMuon(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT pm.MaPM, tv.HoTen, sc.TenSach, pm.TienThue, pm.TraSach, pm.Ngay  From PhieuMuon pm, ThanhVien tv, Sach sc, ThuThu tt where pm.MaTV = tv.MaTV and pm.MaSach = sc.MaSach and pm.MaTT = tt.MaTT ORDER BY pm.MaPM DESC",null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuon(cursor.getInt(0),cursor.getString(1),cursor.getString(2), cursor.getInt(3), cursor.getInt(4),cursor.getString(5)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean update(int mapm, int matv, int masach, int chkcheck){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaTV", matv);
        values.put("MaSach",masach);
        values.put("TraSach",chkcheck);

        long check = db.update("PhieuMuon",values,"MaPM = ?",new String[]{String.valueOf(mapm)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long check = db.delete("PhieuMuon","MaPM = ?",new String[]{String.valueOf(id)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateTrangThai(int mapm){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TraSach",1);
        long check = db.update("PhieuMuon",values,"MaPM = ?",new String[]{String.valueOf(mapm)});
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean insert(PhieuMuon pm){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("MaTT",pm.getMaTT());
        values.put("MaTV", pm.getMaTV());
        values.put("MaSach",pm.getMaSach());
        values.put("TienThue",pm.getTienThue());
        values.put("TraSach",pm.getTrangThai());
        values.put("Ngay",pm.getNgayThue());

        long check = db.insert("PhieuMuon",null,values);
        if(check == -1){
            return false;
        }else{
            return true;
        }
    }
}
