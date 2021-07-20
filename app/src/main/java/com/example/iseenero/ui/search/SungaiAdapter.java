package com.example.iseenero.ui.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.iseenero.DetailActivity;
import com.example.iseenero.MainActivity;
import com.example.iseenero.R;

import java.util.ArrayList;

public class SungaiAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sungai> sungaiArrayList;

//    public SungaiAdapter(@NonNull Context context, ArrayList<Sungai> resource, @NonNull ArrayList<ArrayList<Sungai>> objects) {
//        super(context, resource, objects);
//    }

    public SungaiAdapter(Context context, ArrayList<Sungai> sungaiArrayList) {
        this.context = context;
        this.sungaiArrayList = sungaiArrayList;
    }



    @Override
    public int getCount() {
        return sungaiArrayList.size();
    }

    @Override
    public Sungai getItem(int position) {
        return sungaiArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_sungai, parent, false);
        }
        TextView namaSungaiText = (TextView) convertView.findViewById(R.id.tvNamaSungai);
        /*TextView statusText = (TextView) convertView.findViewById(R.id.txtTingkatWaspada);
        TextView ketinggianText = (TextView) convertView.findViewById(R.id.txtketinggianAir);
        TextView pHText = (TextView) convertView.findViewById(R.id.txtpH);
        TextView kecepatanArusText = (TextView) convertView.findViewById(R.id.txtArusAir);*/

        final Sungai sungai = (Sungai) this.getItem(position);

        namaSungaiText.setText(sungai.getNamaSungai());
//        statusText.setText(sungai.getStatusWaspada());
//        ketinggianText.setText((int)sungai.getKetinggian());
//        pHText.setText((int) sungai.getpH());
//        kecepatanArusText.setText((int)sungai.getKecepatanArus());

        String ketinggian = String.valueOf(sungai.getKetinggian());
        String pH = String.valueOf(sungai.getpH());
        String kecepatanArus = String.valueOf(sungai.getKecepatanArus());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataSungai(sungai.getNamaSungai(), sungai.getStatusWaspada(), ketinggian, pH, kecepatanArus);
            }
        });

        return convertView;
    }

    private void openDataSungai(String...details)
    {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("NAMASUNGAI_KEY", details[0]);
        intent.putExtra("STATUS_KEY", details[1]);
        intent.putExtra("KETINGGIAN_KEY", details[2]);
        intent.putExtra("PH_KEY", details[3]);
        intent.putExtra("KECEPATANARUS_KEY", details[4]);

        context.startActivity(intent);
        ((MainActivity)context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}
