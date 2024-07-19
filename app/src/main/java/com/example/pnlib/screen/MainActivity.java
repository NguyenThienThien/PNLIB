package com.example.pnlib.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.pnlib.R;
import com.example.pnlib.dao.SachDao;
import com.example.pnlib.dao.ThuThuDao;
import com.example.pnlib.fragment.fragment_add_user;
import com.example.pnlib.fragment.fragment_change_pass;
import com.example.pnlib.fragment.fragment_doanh_thu;
import com.example.pnlib.fragment.fragment_loai_sach;
import com.example.pnlib.fragment.fragment_phieu_muon;
import com.example.pnlib.fragment.fragment_sach;
import com.example.pnlib.fragment.fragment_thanh_vien;
import com.example.pnlib.fragment.fragment_top;
import com.google.android.material.navigation.NavigationBarMenu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Context context = this;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        NavigationUser();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menuQLPM){
                    fragment_phieu_muon frgPM = new fragment_phieu_muon();
                    relaceFrg(frgPM);
                    toolbar.setTitle("Quản lý phiếu mượn");
                }else if(item.getItemId() == R.id.menuQLLS){
                    fragment_loai_sach frgLS =  new fragment_loai_sach();
                    relaceFrg(frgLS);
                    toolbar.setTitle("Quản lý loại sách");
                }else if(item.getItemId() == R.id.menuQLS){
                    fragment_sach frgS = new fragment_sach();
                    relaceFrg(frgS);
                    toolbar.setTitle("Quản lý sách");
                }else if(item.getItemId() == R.id.menuQLTV){
                    fragment_thanh_vien frgTV = new fragment_thanh_vien();
                    relaceFrg(frgTV);
                    toolbar.setTitle("Quản lý thành viên");
                }else if(item.getItemId() == R.id.menuTop){
                    fragment_top frgt = new fragment_top();
                    relaceFrg(frgt);
                    toolbar.setTitle("Top 10 sách mượn nhiều nhất");
                }else if(item.getItemId() == R.id.menuTND){
                    fragment_add_user frgTND = new fragment_add_user();
                    relaceFrg(frgTND);
                    toolbar.setTitle("Thêm người dùng");
                }else if(item.getItemId() == R.id.menuDMK){
                    fragment_change_pass frgCP = new fragment_change_pass();
                    relaceFrg(frgCP);
                    toolbar.setTitle("Đổi mật khẩu");
                }else if(item.getItemId() == R.id.menuDX){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Đăng Xuất");
                    builder.setMessage("Bạn chắc chăn muướn đăng xuất chứ!");
                    builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(MainActivity.this, Login_Screen.class));
                            finish();
                        }
                    });
                    builder.setNegativeButton("Hủy",null);
                    builder.create().show();
                }else if(item.getItemId() == R.id.menuDT){
                    fragment_doanh_thu frgDT = new fragment_doanh_thu();
                    relaceFrg(frgDT);
                    toolbar.setTitle("Doanh thu");
                }
                drawerLayout.closeDrawer(navigationView);
                return true;
            }
        });
    }

    public void NavigationUser(){
        NavigationView nv = findViewById(R.id.navigationView);
        View headerView = nv.getHeaderView(0);
        TextView tvUser = headerView.findViewById(R.id.NameUser);
        // lấy tên người dùng từ Intent
        Intent i = getIntent();
        String matt = i.getStringExtra("MaTT");
        tvUser.setText("Welcome" + matt);
        // điều hướng
        if(matt.equals("Admin")){
            nv.getMenu().findItem(R.id.menuTND).setVisible(true);
        }
    }

    public void relaceFrg(Fragment frg){
        FragmentManager fg = getSupportFragmentManager();
        fg.beginTransaction().replace(R.id.frameLayout,frg).commit();
    }
}