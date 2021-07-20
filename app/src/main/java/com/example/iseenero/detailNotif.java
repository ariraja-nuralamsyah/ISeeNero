package com.example.iseenero;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.iseenero.room.NotifViewModel;

import org.json.JSONObject;


public class detailNotif extends AppCompatActivity {
    public ImageView back;
    private JSONObject content;
//    private String arus, ph, ketinggian;
    private NotifViewModel notifViewModel;
    String namaSungai, waktu, tingkatBahaya, ketinggian, phAir, kecepatanAir, tandaSeru;
    private TextView tnamaSungai, twaktu, ttingkatBahaya, tketinggian, tphAir, tkecepatanAir;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_notification);

        tnamaSungai = findViewById(R.id.tnamaSungai);
        twaktu = findViewById(R.id.twaktu);
        ttingkatBahaya = findViewById(R.id.tingkatWaspada);
        tketinggian = findViewById(R.id.ketinggian_air);
        tphAir = findViewById(R.id.ph_air);
        tkecepatanAir = findViewById(R.id.kecepatan_air);
        back = findViewById(R.id.back);


        getData();
        setData();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onStop() {
        super.onStop();
        notifViewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(NotifViewModel.class);
        notifViewModel.update("1",waktu);
        if(MainActivity.active == false){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
    public void getData(){
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        if(getIntent().hasExtra("namaSungai")
                && getIntent().hasExtra("tingkatBahaya")
                && getIntent().hasExtra("waktu")){

            namaSungai = intent.getStringExtra("namaSungai");
            tingkatBahaya = bundle.getString("tingkatBahaya");
            waktu = bundle.getString("waktu");
            tandaSeru = bundle.getString("tandaSeru");
            ketinggian = bundle.getString("ketinggian");
            phAir = bundle.getString("ph");
            kecepatanAir = bundle.getString("arus");

        }

    }

    public void setData(){
            tnamaSungai.setText(namaSungai);
            twaktu.setText(waktu);
            ttingkatBahaya.setText(tingkatBahaya);
            tketinggian.setText(ketinggian);
            tphAir.setText(phAir);
            tkecepatanAir.setText(kecepatanAir);
        }

}
