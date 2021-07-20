package com.example.iseenero;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.iseenero.room.NotifViewModel;
import com.example.iseenero.ui.home.FragmentHome;
import com.example.iseenero.ui.notifikasi.FragmentNotifikasi;
import com.example.iseenero.ui.search.FragmentSearch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    static boolean active = false;
    private AppBarConfiguration mAppBarConfiguration;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Boolean dapatNotifikasi = false;
    private static final String CHANNEL_ID = "1";
    private NotifViewModel notifViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_notifikasi, R.id.nav_search)
                .setDrawerLayout(drawerLayout)
                .build();


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Thread myThread = null;

        Runnable runnable = new CekSungai();
        myThread = new Thread(runnable);
        myThread.start();
    }
    public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ISeeNero";
            String description = "Sistem Peringatan Banjir";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
    // Get the layouts to use in the custom notification
    public void tampilNotifikasi(String namaSungai, String tingkatBahaya, String ketinggian, String ph, String arus){
        createNotificationChannel();

        Bitmap bitmapIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        String waktu = java.text.DateFormat.getDateTimeInstance().format(new Date());


        notifViewModel = new ViewModelProvider(this,new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(NotifViewModel.class);
        NotificationISeeNero notif = new NotificationISeeNero(namaSungai,"0", tingkatBahaya, waktu, ketinggian, ph, arus);
        notifViewModel.insert(notif);

        Intent intent = new Intent(this, detailNotif.class);
        Bundle bundle = new Bundle();
        bundle.putString("namaSungai",namaSungai);
        bundle.putString("tingkatBahaya",tingkatBahaya);
        bundle.putString("waktu",waktu);
        bundle.putString("ketinggian",ketinggian);
        bundle.putString("ph",ph);
        bundle.putString("arus",arus);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)

                .setSmallIcon(R.drawable.logo)
                .setContentTitle(namaSungai)
                .setContentText(tingkatBahaya)
                .setLargeIcon(bitmapIcon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setLights(Color.RED, 3000, 3000)
                ;

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
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
                }catch (Exception ignored) {}
            }
        });
    }
    class CekSungai implements Runnable{
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
                if ((Integer.valueOf(ketinggian) < 50 && Integer.valueOf(arus) > 150) || Integer.valueOf(ketinggian) < 10) {
                    if(dapatNotifikasi == false){
//                        Toast.makeText(getApplicationContext(),"Berbahaya", Toast.LENGTH_LONG).show();
                        tampilNotifikasi(nama, "Berbahaya",ketinggian, ph, arus);
                        dapatNotifikasi = true;
                    }
                }else{
                    dapatNotifikasi = false;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        switch(item.getItemId())
        {
            case R.id.nav_home:
//                loadedFragment = new HomeFragment();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.placeholder, new FragmentHome());
                fragmentTransaction.commit();
                break;

            case R.id.nav_notifikasi:
                //   loadedFragment = new FragmentTugas();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.placeholder, new FragmentNotifikasi());
                fragmentTransaction.commit();
                break;

            case R.id.nav_search:
                //   loadedFragment = new FragmentKalendar();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.placeholder, new FragmentSearch());
                fragmentTransaction.commit();
                break;

            default:
                return false;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}