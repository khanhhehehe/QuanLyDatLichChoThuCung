package com.example.quanlypet.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlypet.model.BillObj;

import java.util.List;
@Dao
public interface BillDao {
    @Insert
    void insertBill(BillObj object);

    @Query("SELECT * FROM Bill")
    List<BillObj> getAllData();

    @Query("SELECT * FROM Bill where id_users =:id")
    List<BillObj> getbyUsers(int id);

    @Update
    void editBill(BillObj object);

    @Query("Select SUM(price) AS 'TongGia' from Bill where date between :dateDT and :dateDT2")
    float getPriceDT(String dateDT,String dateDT2);

    @Query("Select SUM(price) AS 'TuanGia' from Bill where strftime('%d',date) between :day1 and :day2")
    float getPriceTuan(String  day1, String  day2);
    //dung co xoa cua tao
}
