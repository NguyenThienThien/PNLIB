package com.example.pnlib.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    static final String dbName = "PhuongNamLib";
    static final int dbVersion = 19;

    public DbHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng thủ thư
        String tb_ThuThu = "create table ThuThu(" +
                "MaTT text primary key," +
                "HoTen text not null," +
                "MatKhau text not null)";
        db.execSQL(tb_ThuThu);

        // Tạo bảng thành viên
        String tb_ThanhVien = "create table ThanhVien(" +
                "MaTV integer primary key autoincrement," +
                "HoTen text not null," +
                "NamSinh text not null)";
        db.execSQL(tb_ThanhVien);

        // Tạo bảng loại sách
        String tb_LoaiSach = "create table LoaiSach(" +
                "MaLoai integer primary key autoincrement," +
                "HoTen text not null)";
        db.execSQL(tb_LoaiSach);

        // Tạo bảng sách
        String tb_Sach = "create table Sach(" +
                "MaSach integer primary key autoincrement," +
                "TenSach text not null," +
                "GiaThue integer not null," +
                "MaLoai integer references LoaiSach(MaLoai)," +
                "NgayXuatBan integer not null)";
        db.execSQL(tb_Sach);

        // Tạo bảng phiếu mượn
        String tb_PhieuMuon = "create table PhieuMuon(" +
                "MaPM integer primary key autoincrement," +
                "MaTT text references ThuThu(MaTT)," +
                "MaTV integer references ThanhVien(MaTV)," +
                "MaSach integer references Sach(MaSach)," +
                "TienThue integer not null," +
                "TraSach integer not null," +
                "Ngay text not null)";
        db.execSQL(tb_PhieuMuon);

        //data mẫu
        db.execSQL("INSERT INTO LoaiSach VALUES (1, 'Lập trình'),(2,'Thiết kế web'),(3, 'Maketing')");
        db.execSQL("INSERT INTO Sach VALUES (1, 'Lập Trình Android', 2500, 1,2004), (2, 'Lập trình cơ bản', 1000, 1,2005), (3, 'Thiết kế web', 2000, 3,2006)");
        db.execSQL("INSERT INTO ThuThu VALUES ('Admin','Nguyễn Văn Anh','1234'),('thuthu02','Trần Văn Hùng','123abc')");
        db.execSQL("INSERT INTO ThanhVien VALUES (1,'Nguyễn Thiên Thiên','2000'),(2,'Nguyễn Duy Phong','2000')");
        //trả sách: 1: đã trả - 0: chưa trả
        db.execSQL("INSERT INTO PhieuMuon VALUES (1,'thuthu02',1, 1, 2500, 1, '19/03/2022')," +
                "(2,'thuthu02',1, 3, 2000, 0, '19/03/2022')," +
                "(3,'thuthu02',2, 1, 2000, 1, '19/03/2022')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if(i != i1){
            db.execSQL("drop table if exists ThuThu");
            db.execSQL("drop table if exists ThanhVien");
            db.execSQL("drop table if exists LoaiSach");
            db.execSQL("drop table if exists Sach");
            db.execSQL("drop table if exists PhieuMuon");
            onCreate(db);
        }
    }
}
