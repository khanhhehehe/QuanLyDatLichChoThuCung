package com.example.quanlypet.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.quanlypet.model.BookObj;

import java.util.List;

@Dao
public interface BookDao {
    @Insert
    void insert(BookObj object);

    @Query("SELECT * FROM Book")
    List<BookObj> getAllData();

    @Query("SELECT * FROM Book where id_user =:id")
    List<BookObj> getAllDataFromID(int id);

    @Update
    void edit(BookObj object);

    @Query("SELECT *FROM book where id=:id")
    BookObj getIDBook(String id);

    @Query("SELECT *FROM book where obj_status=:obj_status ")
    List<BookObj> getStatus(int obj_status);

    @Query("SELECT *FROM book where obj_status=:obj_status and id_user =:id ")
    List<BookObj> getStatus2(int obj_status, int id);

    @Query("SELECT *FROM book where obj_status=:obj_status and id_user =:id  LIMIT 3 ")
    List<BookObj> getStatus3(int obj_status, int id);

    @Query("SELECT *FROM book where obj_status=:obj_status LIMIT 3")
    List<BookObj> getStatus4(int obj_status);

    @Query("SELECT *FROM book where time between :dateStat AND :dataEnd")
    List<BookObj> checkBooking(String dateStat,String dataEnd);

    @Query("SELECT *FROM book where timeHold between :dateStat AND :dataEnd")
    List<BookObj> checkBooking3(String dateStat,String dataEnd);
}
