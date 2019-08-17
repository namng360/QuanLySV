package vn.mac.gnam.quanlysv.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.mac.gnam.quanlysv.R;
import vn.mac.gnam.quanlysv.adapters.SpinnerTenLop;
import vn.mac.gnam.quanlysv.model.Lop;
import vn.mac.gnam.quanlysv.model.SinhVien;
import vn.mac.gnam.quanlysv.sql.ClassDAO;
import vn.mac.gnam.quanlysv.sql.DatabaseHelper;
import vn.mac.gnam.quanlysv.sql.StudentDAO;

public class ThemSvActivity extends AppCompatActivity {
    private EditText edtName;
    private Spinner spLopId;
    private EditText edtDiaChi;
    private Button btThem;
    private Button btHuy;
    private StudentDAO studentDAO;
    private DatabaseHelper databaseHelper;
    private SinhVien sinhVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sv);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm sinh viên");
        init();
    }

    private void init() {

        edtName = (EditText) findViewById(R.id.edtName);
        spLopId = (Spinner) findViewById(R.id.spLopId);
        edtDiaChi = (EditText) findViewById(R.id.edtDiaChi);
        btThem = (Button) findViewById(R.id.btThem);
        btHuy = (Button) findViewById(R.id.btHuy);
        databaseHelper = new DatabaseHelper(this);
        studentDAO = new StudentDAO(databaseHelper);
        sinhVien = new SinhVien();
        List<Lop> lops = new ClassDAO(databaseHelper).getAllLop();
        spLopId.setAdapter(new SpinnerTenLop(this, lops));
        btThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (studentDAO.getSvByID(edtName.getText().toString().trim()) == null) {
                    sinhVien.setTen(edtName.getText().toString().trim());
                    //TextView textView = (TextView)spLopId.getSelectedView();
                    Lop selectedLop = (Lop) spLopId.getSelectedItem();
                    String lop = String.valueOf(selectedLop.getId());
                    sinhVien.setLop(lop);
                    sinhVien.setDiaChi(edtDiaChi.getText().toString().trim());
                    studentDAO.addSv(sinhVien);
                    if (edtName.getText().toString().trim().isEmpty() || edtDiaChi.getText().toString().trim().isEmpty()){
                        Toast.makeText(ThemSvActivity.this, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(ThemSvActivity.this, QuanLySinhVienActivity.class);
                        Toast.makeText(ThemSvActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }

                } else {
                    Toast.makeText(ThemSvActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
