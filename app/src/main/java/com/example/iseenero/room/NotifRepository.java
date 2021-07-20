package com.example.iseenero.room;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.iseenero.NotificationISeeNero;

import java.util.List;

class NotifRepository {
    private NotificationDAO notifDAO;
    private LiveData<List<NotificationISeeNero>> allNotif;


    NotifRepository(Application application){
        NotifRoomDatabase dbTugas =NotifRoomDatabase.getDatabase(application);
        notifDAO = dbTugas.notificationDAO();
        allNotif =notifDAO.getAlphabetizedTugas();
    }

    LiveData<List<NotificationISeeNero>> getAllNotif() {
        return allNotif;
    }

    void insert(NotificationISeeNero notif) {
        NotifRoomDatabase.databaseWriteExecutor.execute(() -> {
            notifDAO.insert(notif);
            Log.e("MyListActivity", notif.getNamaSungai() + notif.getStatus() + notif.getArusSungai() + notif.getPhSungai() + notif.getKetinggianSungai() + notif.getTingkatBahaya() + notif.getWaktu());
        });
    }

//    void delete(String Dnama){
//        NotifRoomDatabase.databaseWriteExecutor.execute(()->{
//            notifDAO.delete(Dnama);
//        });
//    }

    void update(String status, String waktu){
        NotifRoomDatabase.databaseWriteExecutor.execute(() -> {
            notifDAO.update(status, waktu);
        });
    }

}
