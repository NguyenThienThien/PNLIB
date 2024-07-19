package com.example.pnlib.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlib.database.DbHelper;
import com.example.pnlib.model.Sach;

import java.util.ArrayList;

public class ThongKeDao {
    DbHelper dbHelper;

    public ThongKeDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Sach> getTop10(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT pm.MaSach, sc.TenSach, COUNT(pm.MaSach) AS SoLanMuon FROM PhieuMuon pm, Sach sc WHERE pm.MaSach = sc.MaSach GROUP by pm.MaSach, sc.TenSach ORDER by COUNT(pm.MaSach) DESC LIMIT 10",null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int getDoanhThu(String Start, String End){
        Start = Start.replace("/","");
        End = End.replace("/","");
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(TienThue) FROM PhieuMuon WHERE substr(Ngay,7) || substr(Ngay,4,2) || substr(Ngay,1,2) BETWEEN ? and ?",new String[]{Start,End});
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
}
