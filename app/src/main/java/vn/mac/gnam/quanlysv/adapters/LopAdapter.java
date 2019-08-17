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

import java.util.List;

import vn.mac.gnam.quanlysv.R;
import vn.mac.gnam.quanlysv.model.Lop;

public class LopAdapter extends RecyclerView.Adapter<LopAdapter.LopViewHolder> {
    private List<Lop> lops;
    private OnItemClickListener mListener;

    public LopAdapter(List<Lop> lops) {
        this.lops = lops;
    }

    @NonNull
    @Override
    public LopViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lop, viewGroup, false);

        return new LopAdapter.LopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull LopViewHolder lopViewHolder, int i) {
        lopViewHolder.tvMaLop.setText(lops.get(i).getMaLop());
        lopViewHolder.tvLop.setText(lops.get(i).getLop());
    }

    @Override
    public int getItemCount() {
        return lops.size();
    }


    public class LopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView tvMaLop;
        public TextView tvLop;


        public LopViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMaLop = itemView.findViewById(R.id.tvMaLop);
            tvLop = itemView.findViewById(R.id.tvLop);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Lựa trọn");
            MenuItem edit = menu.add(Menu.NONE, 1, 1, "Sửa");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "Xoá");

            edit.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
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
