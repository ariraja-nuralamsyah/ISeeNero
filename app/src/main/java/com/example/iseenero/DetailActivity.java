package com.example.iseenero;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iseenero.room.NotifViewModel;
import com.example.iseenero.ui.notifikasi.FragmentNotifikasi;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.json.JSONException;
import org.json.JSONObject;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailActivity extends AppCompatActivity{
    private String TAG = "ANTARES-API";
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private ArrayList<data_sungai> dataSungai;
    private TextView arusSungai;
    private TextView phSungai;
    private TextView lokasiSungai;
    private TextView tinggiSungai;
    private TextView namaSungai;
    private TextView tanggal;
    private HorizontalScrollView horizontalScrollView;
    private LineChart lineChart;
    private Spinner spinner;
    private String tg;
    private boolean change = true;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        auth = FirebaseAuth.getInstance();
        arusSungai = findViewById(R.id.arusSungai);
        phSungai = findViewById(R.id.PHair);
        lokasiSungai = findViewById(R.id.lokasi);
        tinggiSungai = findViewById(R.id.tinggiAir);
        lineChart = findViewById(R.id.lineChart);
        namaSungai = findViewById(R.id.textView);
        tanggal = findViewById(R.id.tanggal);
        horizontalScrollView = findViewById(R.id.hsvChart);
        spinner = findViewById(R.id.spinner_tanggal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView toolbarText = (TextView) findViewById(R.id.toolbar_text);
        if(toolbarText!=null && toolbar!=null) {
            toolbar.setTitle("");
            toolbarText.setText("iSeeNero!");
            setSupportActionBar(toolbar);
        }

        Intent intent = getIntent();
        if(intent.getStringExtra("NAMASUNGAI_KEY").equals("Sungai Citarum")) {
            Thread myThread = null;

            Runnable runnable = new UpdateSungai();
            myThread = new Thread(runnable);
            myThread.start();

            GetData();
        }else{
            namaSungai.setText(intent.getStringExtra("NAMASUNGAI_KEY"));
            arusSungai.setText(intent.getStringExtra("KECEPATANARUS_KEY") + " m3/detik");
            phSungai.setText(intent.getStringExtra("PH_KEY"));
            lokasiSungai.setText("Memuat...");
            tanggal.setText("Alat Tidak Tersedia");
            tinggiSungai.setText(intent.getStringExtra("KETINGGIAN_KEY") + " CM");
        }

    }

    public void doWork() {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    Response response;
                    String strBody;
                    JSONObject content;
                    String arus, ph, lokasi, ketinggian;
                    AntaresAPIASync antaresAPIASync = new AntaresAPIASync();
                    antaresAPIASync.execute();
                    if(change == true){
                        setGrafik();
                    }
                }catch (Exception ignored) {}
            }
        });
    }
    class UpdateSungai implements Runnable{
        // @Override
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork();
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }

    private class AntaresAPIASync extends AsyncTask<Void, Void, Void> {
        private Response response;
        private String strBody;
        private JSONObject content;
        private String arus, ph, lokasi, ketinggian, nama;

        protected Void doInBackground(Void... voids) {
            try {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://platform.antares.id:8443/~/antares-cse/antares-id/TestArduino/Arduino/la")
                        .get()
                        .addHeader("X-M2M-Origin", "02e878afacff7fd2:9cd80e42eb8bb12a")
                        .addHeader("Accept", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("Postman-Token", "a84486c4-9c68-46da-98e4-513e6bf76081")
                        .build();

                response = client.newCall(request).execute();
                strBody = response.body().string();
                JSONObject body = new JSONObject(strBody);
                content = new JSONObject(body.getJSONObject("m2m:cin").getString("con").replaceAll("NaN", "-1"));
                nama = "Sungai Citarum";
                arus = content.getString("Arus");
                ph = content.getString("pH");
                lokasi = "Dusun Bakung Utara";
                ketinggian = content.getString("Jarak");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(arus != null && ph != null && lokasi != null && ketinggian != null){
                namaSungai.setText(nama);
                arusSungai.setText(arus + " m3/detik");
                phSungai.setText(ph);
                lokasiSungai.setText(lokasi);
                tanggal.setText(tg);
                tinggiSungai.setText(ketinggian + " CM");
            }
        }
    }
    public void GetData(){
        //Mendapatkan Referensi Database
        reference = FirebaseDatabase.getInstance("https://myfirebase-f1ae3-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
        reference.child("Admin").child("Zx6GjwzHlHUIBZJFsaWDiKQlJmh1").child("Sungai")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        dataSungai = new ArrayList<>();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                            data_sungai sungai = snapshot.getValue(data_sungai.class);

                            //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                            sungai.setKey(snapshot.getKey());
                            dataSungai.add(sungai);
                        }
                        namaSungai.setText(dataSungai.get(dataSungai.size()-1).getNama_sungai());
                        arusSungai.setText(dataSungai.get(dataSungai.size()-1).getArus_sungai() + " m3/detik");
                        phSungai.setText(dataSungai.get(dataSungai.size()-1).getPh_sungai());
                        lokasiSungai.setText("Dusun Bakung Utara");
                        tinggiSungai.setText(dataSungai.get(dataSungai.size()-1).getKetinggian_sungai() + " CM");
                        ArrayList<String> Item = new ArrayList<>();
                        for(int x = 0; x < dataSungai.size(); x++) {
                            if(x != 0){
                                if(!(dataSungai.get(x-1).getTanggal().equals(dataSungai.get(x).getTanggal()))){
                                    Item.add(dataSungai.get(x).getTanggal());
                                }
                            }else{
                                Item.add(dataSungai.get(x).getTanggal());
                            }
                        }

                        Calendar c1 = Calendar.getInstance();
                        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, Item);
                        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        spinner.setSelection(Item.size()-1);
                        tg = spinner.getSelectedItem().toString();
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                                tg = parent.getItemAtPosition(pos).toString();
                                change = true;
                            }
                            public void onNothingSelected(AdapterView<?> parent) {
                                tg = sdf1.format(c1.getTime());
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
              /*
                Kode ini akan dijalankan ketika ada error dan
                pengambilan data error tersebut lalu memprint error nya
                ke LogCat
               */
                        Toast.makeText(getApplicationContext(),"Data Gagal Dimuat" + dataSungai, Toast.LENGTH_LONG).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });
    }

    public void setGrafik(){
        int y;
        String tanggals = "", times;
        List<Entry> kasus = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        ArrayList<String> date = new ArrayList<>();
        ArrayList<String> time = new ArrayList<>();
        Date convertDate = null;
        Date convertDate_dump = null;
        Timestamp ts_dump = null;
        Long tsNew = null;
        Long tsOld = null;
        int grafikCount = 0;

        //TimeZone.setDefault(TimeZone.getTimeZone(TimeZone.getTimeZone("GMT+7").getID()));
        try {
            tanggals = dataSungai.get(0).getTanggal();
            times = dataSungai.get(0).getJam();
            convertDate_dump = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tanggals + " " + times);
            ts_dump = new Timestamp(convertDate_dump.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long reference_timestamp = ts_dump.getTime();
        for(int x = 0; x < dataSungai.size(); x++) {
            y = Integer.valueOf(dataSungai.get(x).getKetinggian_sungai());
            tanggals = dataSungai.get(x).getTanggal();
            times = dataSungai.get(x).getJam();
            try {
                convertDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tanggals + " " + times);
                Timestamp ts = new Timestamp(convertDate.getTime());
                tsOld = ts.getTime();
                tsNew = tsOld - reference_timestamp;
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (tanggals.equals(tg)) {
                date.add(tanggals);
                time.add(times);
                kasus.add(new Entry(tsNew, y));
                grafikCount = grafikCount + 1;
                if (y >= 100) {
                    colors.add(Color.BLUE);
                } else if (y > 50 && y < 100) {
                    colors.add(Color.YELLOW);
                } else if (y <= 50) {
                    colors.add(Color.RED);
                }
            }
        }
        float dip;
        ViewGroup.LayoutParams layoutParams = lineChart.getLayoutParams();
        if(grafikCount > 7){
            dip = ( grafikCount * 30) + 180;
        }else{
            dip = 390f;
        }
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dip,
                r.getDisplayMetrics()
        );
        layoutParams.width = (int) px;
        lineChart.setLayoutParams(layoutParams);
        tanggal.setText(tg);
        LineDataSet kasusLineDataSet = new LineDataSet(kasus, "Jarak air ke alat (cm)");
        kasusLineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        kasusLineDataSet.setCircleRadius(5f);
        kasusLineDataSet.setCircleColors(colors);
        kasusLineDataSet.setColors(Color.RED);

        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);

        lineChart.getDescription().setEnabled(false);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.setData(new LineData(kasusLineDataSet));
        lineChart.animateXY(100, 500);
        lineChart.getAxisLeft().setAxisMinimum(0);
        lineChart.getAxisLeft().setAxisMaximum(lineChart.getYMax() + 100);
        lineChart.getAxisRight().setAxisMinimum(0);
        lineChart.getAxisRight().setAxisMaximum(lineChart.getYMax() + 100);
        lineChart.getXAxis().setLabelCount(grafikCount);

        HourAxisValueFormatter waktu = new HourAxisValueFormatter(reference_timestamp);
        lineChart.getXAxis().setValueFormatter(waktu);

        IMarker marker = new LineChartMarkerView(getApplicationContext(), lineChart, R.layout.markerview_three_item, reference_timestamp);
        lineChart.setMarker(marker);
        change = false;
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}