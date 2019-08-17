package vn.mac.gnam.quanlysv.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import vn.mac.gnam.quanlysv.R;
import vn.mac.gnam.quanlysv.activities.ThemSvActivity;
import vn.mac.gnam.quanlysv.model.Lop;
import vn.mac.gnam.quanlysv.model.SinhVien;

public class SpinnerTenLop extends BaseAdapter {
    public Context context;
    public List<Lop> lops;

    public SpinnerTenLop(Context context, List<Lop> lops) {
        this.context = context;
        this.lops = lops;
    }

    @Override
    public int getCount() {
        return lops.size();
    }

    @Override
    public Object getItem(int position) {
        return lops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.spinner_lop_sv, parent,false);
        TextView tvName;
        tvName  =  convertView.findViewById(R.id.tvName);
        //tvName.setText(position + 1 + " | " + lops.get(position).maLop);
        //tvName.setText(lops.get(position).id + " | " + lops.get(position).maLop);
        tvName.setText(lops.get(position).lop);
        return convertView;
    }
}
