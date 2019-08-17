package vn.mac.gnam.quanlysv.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import vn.mac.gnam.quanlysv.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Quản lý sinh viên");
    }

    public void moQuanLyLop(View view) {
        Intent intent = new Intent(HomeActivity.this, QuanLyLopActivity.class);
        startActivity(intent);
    }

    public void moQuanLySinhVien(View view) {
        Intent intent = new Intent(HomeActivity.this, QuanLySinhVienActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_user) {
            Intent intent = new Intent(HomeActivity.this, UsersListActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
