package vn.mac.gnam.quanlysv.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import vn.mac.gnam.quanlysv.R;
import vn.mac.gnam.quanlysv.model.Lop;
import vn.mac.gnam.quanlysv.sql.ClassDAO;
import vn.mac.gnam.quanlysv.sql.DatabaseHelper;

public class ThemLopActivity extends AppCompatActivity {
    private EditText edMaLop;
    private EditText edTenLop;
    private Button btThem;
    private Button btHuy;
    private ClassDAO classDAO;
    private DatabaseHelper databaseHelper;
    private Lop lop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_lop);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm lớp");
        init();
    }

    private void init() {
        edMaLop = (EditText) findViewById(R.id.edMaLop);
        edTenLop = (EditText) findViewById(R.id.edTenLop);
        btThem = (Button) findViewById(R.id.btThem);
        btHuy = (Button) findViewById(R.id.btHuy);
        databaseHelper = new DatabaseHelper(this);
        classDAO = new ClassDAO(databaseHelper);
        lop = new Lop();
        btThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (classDAO.getLopByID(edTenLop.getText().toString().trim()) == null) {
                    lop.setLop(edTenLop.getText().toString().trim());
                    lop.setMaLop(edMaLop.getText().toString().trim());
                    boolean checkExistLop = false;
                    checkExistLop = isClassNameExist(edMaLop.getText().toString());
                    if(checkExistLop){
                        Toast.makeText(ThemLopActivity.this, "Mã lớp bị trùng, vui lòng chọn Mã lớp khác!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        classDAO.insertLop(lop);
                        if (edTenLop.getText().toString().trim().isEmpty() || edMaLop.getText().toString().trim().isEmpty()){
                            Toast.makeText(ThemLopActivity.this, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                        }else {
                            Intent intent = new Intent(ThemLopActivity.this, QuanLyLopActivity.class);
                            Toast.makeText(ThemLopActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }
                    }
                //} else {
                //    Toast.makeText(ThemLopActivity.this, "Tên lớp đã tồn tại", Toast.LENGTH_SHORT).show();
                //}
            }
        });
        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    boolean isClassNameExist(String className) {
        List<Lop> classes = classDAO.getAllLop();
        for (Lop lo : classes) {
            String txtLop = lo.getMaLop();
            if (txtLop.equals(className)) return true;
        }

        return false;
    }
}
