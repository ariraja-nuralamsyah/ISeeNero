package com.example.iseenero;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iseenero.room.NotifViewModel;

import static android.view.View.INVISIBLE;

public class NotificationAdapter extends ListAdapter<NotificationISeeNero, NotificationAdapter.ViewHolder> {
    NotifViewModel notifViewModel;
    Context context;
    public NotificationAdapter(@NonNull DiffUtil.ItemCallback<NotificationISeeNero> diffCallback, Context ct) {
        super(diffCallback);
        this.context = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_notification, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NotificationISeeNero notificationISeeNero = getItem(position);

        holder.namaSungai.setText(notificationISeeNero.getNamaSungai());
        holder.tingkatBahaya.setText(notificationISeeNero.getTingkatBahaya());
        holder.waktu.setText(notificationISeeNero.getWaktu());
        String status = notificationISeeNero.getStatus();
        if (status.equals("1")){
            holder.namaSungai.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.tingkatBahaya.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.waktu.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.tandaSeru.setVisibility(View.GONE);
        }


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  String nSungai = holder.namaSungai.getText().toString();
                  String tBahaya = holder.tingkatBahaya.getText().toString();
                  String wktu = holder.waktu.getText().toString();
                  String tKetinggian = notificationISeeNero.getKetinggianSungai();
                  String tPH = notificationISeeNero.getPhSungai();
                  String tArus = notificationISeeNero.getArusSungai();

                Bundle bundle = new Bundle();
                bundle.putString("namaSungai",nSungai);
                bundle.putString("tingkatBahaya",tBahaya);
                bundle.putString("waktu",wktu);
                bundle.putString("ketinggian",tKetinggian);
                bundle.putString("ph",tPH);
                bundle.putString("arus",tArus);

                Intent intent = new Intent(v.getContext(), detailNotif.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    public static class WordDiff extends DiffUtil.ItemCallback<NotificationISeeNero> {

        @Override
        public boolean areItemsTheSame(@NonNull NotificationISeeNero oldItem, @NonNull NotificationISeeNero newItem) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(@NonNull NotificationISeeNero oldItem, @NonNull NotificationISeeNero newItem) {
            return false;
        }
    }
//    public void update(String namaSungai, String tingkatBahaya, String waktu, int tandaSeru){
//        databaseWriteExecutor.execute(() -> {
//            // Populate the database in the background.
//            // If you want to start with more words, just add them.
//            NotificationDAO dao = INSTANCE.notificationDAO();
//            String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
//
//            NotificationISeeNero tugas1 = new NotificationISeeNero(namaSungai,tingkatBahaya,waktu,tandaSeru);
//            dao.update(tugas1);
//        });
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView namaSungai;
        public TextView tingkatBahaya;
        public TextView waktu;
        public ImageView tandaSeru;
        public ImageView next;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.namaSungai = (TextView) itemView.findViewById(R.id.namaSungai);
            this.tingkatBahaya = (TextView) itemView.findViewById(R.id.tingkatBahaya);
            this.waktu = (TextView) itemView.findViewById(R.id.waktu);
            this.next = (ImageView) itemView.findViewById(R.id.next);
            this.tandaSeru = (ImageView) itemView.findViewById(R.id.tandaSeru);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.constraintLayout);
        }
    }
}
