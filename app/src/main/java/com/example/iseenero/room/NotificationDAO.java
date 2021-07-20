package com.example.iseenero.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.iseenero.NotificationISeeNero;

import java.util.List;

@Dao
public interface NotificationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(NotificationISeeNero tugas);

    @Query("SELECT * FROM dataNotif6 ORDER BY waktu ASC")
    LiveData<List<NotificationISeeNero>> getAlphabetizedTugas();

    @Query("UPDATE dataNotif6 SET status=:Dstatus WHERE waktu = :Dwaktu")
    void update(String Dstatus, String Dwaktu);
}
