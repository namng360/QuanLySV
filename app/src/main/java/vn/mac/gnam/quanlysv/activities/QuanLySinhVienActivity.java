package vn.mac.gnam.quanlysv.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import vn.mac.gnam.quanlysv.R;
import vn.mac.gnam.quanlysv.adapters.SinhVienAdapter;
import vn.mac.gnam.quanlysv.adapters.SpinnerTenLop;
import vn.mac.gnam.quanlysv.model.Lop;
import vn.mac.gnam.quanlysv.model.SinhVien;
import vn.mac.gnam.quanlysv.sql.ClassDAO;
import vn.mac.gnam.quanlysv.sql.DatabaseHelper;
import vn.mac.gnam.quanlysv.sql.StudentDAO;

public class QuanLySinhVienActivity extends AppCompatActivity implements SinhVienAdapter.OnItemClickListener {
    private RecyclerView rvSv;
    private FloatingActionButton btnAdd;
    private List<SinhVien> sinhViens;
    private SinhVienAdapter sinhVienAdapter;
    private DatabaseHelper databaseHelper;
    private StudentDAO studentDAO;
    private ClassDAO classDAO;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_sinh_vien);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Danh sách sinh viên");
        init();
    }

    /*private void init() {
        rvSv = (RecyclerView) findViewById(R.id.rvSv);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);

        databaseHelper = new DatabaseHelper(this);
        studentDAO = new StudentDAO(databaseHelper);
        sinhViens = studentDAO.getAllSv();
        sinhVienAdapter = new SinhVienAdapter(sinhViens);
        linearLayoutManager = new LinearLayoutManager(this);
        rvSv.setLayoutManager(linearLayoutManager);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLySinhVienActivity.this, ThemSvActivity.class);
                startActivity(intent);
            }
        });
    }*/


    private void init() {
        rvSv = (RecyclerView) findViewById(R.id.rvSv);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);

        databaseHelper = new DatabaseHelper(this);
        studentDAO = new StudentDAO(databaseHelper);
        classDAO = new ClassDAO(databaseHelper);
        sinhViens = studentDAO.getAllSv();
        rvSv = findViewById(R.id.rvSv);
        sinhVienAdapter = new SinhVienAdapter(sinhViens);
        sinhVienAdapter.setOnItemClickListener(this);
        rvSv.setAdapter(sinhVienAdapter);


        linearLayoutManager = new LinearLayoutManager(this);
        rvSv.setLayoutManager(linearLayoutManager);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Lop> lopItems = classDAO.getAllLop();
                if(lopItems.size() > 0) {
                    Intent intent = new Intent(QuanLySinhVienActivity.this, ThemSvActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getBaseContext(), "Chưa có lớp nào, vui lòng tạo lớp trước.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onEditClick(int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_update_student);
        dialog.setTitle("Sửa thông tin sinh viên");
        final SinhVien sv = sinhViens.get(position);
        final EditText edtName;
        final EditText edtAddress;
        final Spinner spLop;
        Button btnUpdate;
        Button btnCancel;

        edtName = dialog.findViewById(R.id.etName);
        edtAddress = dialog.findViewById(R.id.etAddress);
        spLop = dialog.findViewById(R.id.spStudent);
        btnUpdate = dialog.findViewById(R.id.btnUpdate);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        edtName.setText(sv.getTen());
        edtAddress.setText(sv.getDiaChi());
        final List<Lop> lops = new ClassDAO(databaseHelper).getAllLop();
        spLop.setAdapter(new SpinnerTenLop(this, lops));
        final String[] textSpinnerChoice = {""};

        for (int i = 0; i < lops.size(); i++) {
            String c = " | ";
            String txtLop = sv.getLop();

            //int indexOf = txtLop.indexOf(c);
            ////String className ="";// txtLop.substring(indexOf + c.length());
            //if (className.equals(lops.get(i).getMaLop())) spLop.setSelection(i);
            String lopId =String.valueOf(lops.get(i).id);
            if(lopId.equals(txtLop)){
                spLop.setSelection(i);
            }
        }

        spLop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textSpinnerChoice[0] = String.valueOf(lops.get(position).id);// position + 1 + " | " + lops.get(position).maLop;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SinhVien st = new SinhVien();
                st.setId(sv.getId());
                st.setTen(edtName.getText().toString().trim());
                st.setDiaChi(edtAddress.getText().toString().trim());
                st.setLop(textSpinnerChoice[0]);

                int result = studentDAO.updateSv(st);
                if (result > 0) {
                    updateListSv();
                    Log.e(getClass().getSimpleName(), "Đã cập nhật thành công!");
                    Toast.makeText(getBaseContext(), "Đã cập nhật thành công!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onDeleteClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa sinh viên không?");
        builder.setCancelable(false);
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteStudent(position);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void deleteStudent(int position) {
        SinhVien sv = sinhViens.get(position);
        studentDAO.deleteSv(sv);
        updateListSv();
        Toast.makeText(this, "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
    }


    private void updateListSv() {
        sinhViens.clear();
        sinhViens.addAll(studentDAO.getAllSv());
        if (sinhVienAdapter != null) {
            sinhVienAdapter.notifyDataSetChanged();
        }
    }

}
