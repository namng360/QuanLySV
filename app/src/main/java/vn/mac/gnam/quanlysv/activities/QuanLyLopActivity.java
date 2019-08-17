package vn.mac.gnam.quanlysv.activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import vn.mac.gnam.quanlysv.R;
import vn.mac.gnam.quanlysv.adapters.LopAdapter;
import vn.mac.gnam.quanlysv.model.Lop;
import vn.mac.gnam.quanlysv.model.SinhVien;
import vn.mac.gnam.quanlysv.sql.ClassDAO;
import vn.mac.gnam.quanlysv.sql.DatabaseHelper;
import vn.mac.gnam.quanlysv.sql.StudentDAO;

public class QuanLyLopActivity extends AppCompatActivity implements LopAdapter.OnItemClickListener {
    private RecyclerView rvView;
    private LopAdapter lopAdapter;
    private List<Lop> lops;
    private DatabaseHelper databaseHelper;
    private ClassDAO classDAO;
    private StudentDAO studentDAO;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_lop);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Danh sách lớp");
        init();
    }

    private void init() {
        databaseHelper = new DatabaseHelper(this);
        classDAO = new ClassDAO(databaseHelper);
        studentDAO = new StudentDAO(databaseHelper);
        lops = classDAO.getAllLop();
        rvView = findViewById(R.id.rvLop);
        lopAdapter = new LopAdapter(lops);
        lopAdapter.setOnItemClickListener(this);
        rvView.setAdapter(lopAdapter);

        linearLayoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(linearLayoutManager);

        btnAdd = (FloatingActionButton) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuanLyLopActivity.this, ThemLopActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "Click" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditClick(int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_update_class);
        dialog.setTitle("Sửa thông tin lớp");
        final Lop lp = lops.get(position);
        final EditText edtMaLop;
        final EditText edtLop;
        Button btnUpdate;
        Button btnCancel;

        edtMaLop = dialog.findViewById(R.id.edtMaLop);
        edtLop = dialog.findViewById(R.id.edtLop);
        btnUpdate = dialog.findViewById(R.id.btnUpdate);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        edtMaLop.setText(lp.getMaLop());
        edtLop.setText(lp.getLop());



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Lop lop = new Lop();
                lop.setId(lp.getId());
                lop.setMaLop(edtMaLop.getText() + "");
                lop.setLop(edtLop.getText() + "");
                boolean checkExistLop = false;
                checkExistLop = isClassNameExist(edtMaLop.getText().toString());
                if(checkExistLop){
                    Toast.makeText(QuanLyLopActivity.this, "Mã lớp bị trùng, vui lòng chọn Mã lớp khác!", Toast.LENGTH_SHORT).show();
                }
                else {
                    int result = classDAO.updateLop(lop);
                    if (result > 0) {
                        updateListLop();
                        Toast.makeText(getBaseContext(), "Đã cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
                Log.e("Edit", "Click");
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        Toast.makeText(this, "Edit" + position, Toast.LENGTH_SHORT).show();
    }

    private void updateListLop() {
        lops.clear();
        lops.addAll(classDAO.getAllLop());
        if (lopAdapter != null) {
            lopAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDeleteClick(int position) {
        Lop lop = lops.get(position);
        if(!isStudentExistOfClass(String.valueOf(lop.getId()))){
            classDAO.deleteLop(lop);
            updateListLop();
            Toast.makeText(this, "Xóa lớp thành công!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Lớp này đã có sinh viên. Không thể xóa.", Toast.LENGTH_SHORT).show();
        }

    }

    boolean isClassNameExist(String className) {
        List<Lop> classes = classDAO.getAllLop();
        for (Lop lo : classes) {
            String txtLop = lo.getMaLop();
            if (txtLop.equals(className)) return true;
        }
        return false;
    }


    boolean isStudentExistOfClass(String lopId) {
        List<SinhVien> students = studentDAO.getAllSv();
        for (SinhVien st : students) {
           // String c = " | ";
            String txtLop = st.getLop();
           //int indexOf = txtLop.indexOf(c);
            //String className = txtLop.substring(indexOf + c.length());
            if (txtLop.equals(lopId)) return true;
        }

        return false;
    }

}