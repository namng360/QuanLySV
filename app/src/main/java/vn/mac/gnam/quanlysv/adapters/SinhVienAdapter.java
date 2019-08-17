package vn.mac.gnam.quanlysv.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.mac.gnam.quanlysv.R;
import vn.mac.gnam.quanlysv.model.Lop;
import vn.mac.gnam.quanlysv.model.SinhVien;

public class SinhVienAdapter extends RecyclerView.Adapter<SinhVienAdapter.ViewHolder> {
    private List<SinhVien> sinhViens;
    private OnItemClickListener mListener;

    public SinhVienAdapter(List<SinhVien> sinhViens) {
        this.sinhViens = sinhViens;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_sinh_vien, viewGroup, false);

        return new SinhVienAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvTen.setText(sinhViens.get(i).getTen());
        //viewHolder.tvLop.setText(sinhViens.get(i).getLop() + " | " + sinhViens.get(i).getTenLop());
        viewHolder.tvLop.setText(sinhViens.get(i).getTenLop());
        viewHolder.tvDiaChi.setText(sinhViens.get(i).getDiaChi());
    }

    @Override
    public int getItemCount() {
        return sinhViens.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView tvTen;
        public TextView tvLop;
        public TextView tvDiaChi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvLop = itemView.findViewById(R.id.tvLop);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mListener != null) mListener.onEditClick(getAdapterPosition());
//                }
//            });
//            itemView.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    if (mListener != null) mListener.onDeleteClick(getAdapterPosition());
//                    return false;
//                }
//            });

            itemView.setOnCreateContextMenuListener(this);


        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {

                    switch (item.getItemId()) {
                        case 1:
                            mListener.onEditClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteClick(position);
                            return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(itemView.getContext(), "Clicked!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Lựa chọn");
            MenuItem edit = menu.add(Menu.NONE, 1, 1, "Sửa");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Xoá");

            edit.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }
    }


    public interface OnItemClickListener {
        void onItemClick(int position);

        void onEditClick(int position);

        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
}
