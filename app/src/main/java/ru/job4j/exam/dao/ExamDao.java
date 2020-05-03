package ru.job4j.exam.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.job4j.exam.entitties.Exam;

@Dao
public interface ExamDao {

    @Query("SELECT * FROM exams")
    LiveData<List<Exam>> getAll();

    @Query("SELECT * FROM exams WHERE is_selected = 1")
    LiveData<List<Exam>> getSelectedExams();

    @Query("SELECT * FROM exams WHERE _id = :id")
    LiveData<Exam> getById(int id);

    @Insert
    long add(Exam exam);

    @Update
    void update(Exam exam);

    @Query("DELETE FROM exams")
    void deleteAll();

    @Delete()
    void deleteById(Exam exam);
}
